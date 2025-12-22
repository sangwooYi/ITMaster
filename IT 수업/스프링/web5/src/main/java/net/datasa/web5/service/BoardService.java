package net.datasa.web5.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.datasa.web5.dto.BoardDto;
import net.datasa.web5.entity.BoardEntity;
import net.datasa.web5.entity.MemberEntity;
import net.datasa.web5.repository.BoardRepository;
import net.datasa.web5.repository.MemberRepository;
import net.datasa.web5.util.FileManager;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class BoardService {

    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;
    private final FileManager fileManager;


    public BoardDto saveBoard(BoardDto boardDto, String path, MultipartFile file) throws Exception {

        MemberEntity memberEntity = memberRepository.findById(boardDto.getUserId()).orElseThrow(()
                -> new EntityNotFoundException("멤버 정보가 없어요"));

        String fileName = null;
        String originalFileName = null;

        //첨부파일이 있으면 지정된 위치에 저장하고 파일명을 Entity에 저장
        if (file != null && !file.isEmpty()) {
            fileName = fileManager.saveFile(path, file);
            originalFileName = file.getOriginalFilename();
        }

        BoardEntity boardEntity = BoardEntity.builder()
                .member(memberEntity)
                .title(boardDto.getTitle())
                .content(boardDto.getContent())
                .viewCount(0)
                .likeCount(0)
                .fileName(fileName)
                .originalName(originalFileName)
                .build();

        BoardEntity result = boardRepository.save(boardEntity);

        BoardDto resultDto = this.convertEntityToDto(result);

        return resultDto;
    }

    public void download(Integer boardNum, HttpServletResponse response, String uploadPath) {
        //전달된 글 번호로 글 정보 조회
        BoardEntity boardEntity = boardRepository.findById(boardNum)
                .orElseThrow(() -> new EntityNotFoundException("게시글이 없습니다."));

        //원래의 파일명
        try {
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(boardEntity.getOriginalName(), "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        //저장된 파일 경로
        String fullPath = uploadPath + "/" + boardEntity.getFileName();

        //서버의 파일을 읽을 입력 스트림과 클라이언트에게 전달할 출력스트림
        FileInputStream filein = null;
        ServletOutputStream fileout = null;

        try {
            filein = new FileInputStream(fullPath);
            fileout = response.getOutputStream();

            //Spring의 파일 관련 유틸 이용하여 출력
            FileCopyUtils.copy(filein, fileout);

            filein.close();
            fileout.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<BoardDto> findAll() {

        List<BoardEntity> boardEntities = boardRepository.findAll(Sort.by("boardNum"));

        List<BoardDto> boardDtoList = new ArrayList<>();

        boardEntities.forEach(entity -> boardDtoList.add(this.convertEntityToDto(
                entity)));

        return boardDtoList;
    }

    public void delete(Integer boardNum, String userId) {
        BoardEntity boardEntity = boardRepository.findById(boardNum)
                .orElseThrow(() -> new EntityNotFoundException("게시글이 없습니다."));

        if (!boardEntity.getMember().getUserId().equals(userId)) {
            throw new RuntimeException("삭제 권한 없음요");
        }
        boardRepository.delete(boardEntity);
    }

    public BoardDto getBoard(Integer boardNum) {
        BoardEntity entity = boardRepository.findById(boardNum).orElseThrow(() -> new EntityNotFoundException("해당번호 글 없음"));

        BoardDto boardDto = this.convertEntityToDto(entity);

        return boardDto;
    }

    public BoardDto convertEntityToDto(BoardEntity boardEntity) {

        BoardDto boardDto = new BoardDto();

        if (!ObjectUtils.isEmpty(boardEntity.getBoardNum())) {
            boardDto.setBoardNum(boardEntity.getBoardNum());
        }

        if (!ObjectUtils.isEmpty(boardEntity.getTitle() )) {
            boardDto.setTitle(boardEntity.getTitle());
        }

        if (!ObjectUtils.isEmpty(boardEntity.getContent())) {
            boardDto.setContent(boardEntity.getContent());
        }

        if (!ObjectUtils.isEmpty(boardEntity.getViewCount())) {
            boardDto.setViewCount(boardEntity.getViewCount());
        }

        if (!ObjectUtils.isEmpty(boardEntity.getLikeCount())) {
            boardDto.setLikeCount(boardEntity.getLikeCount());
        }

        if (!ObjectUtils.isEmpty(boardEntity.getFileName())) {
            boardDto.setFileName(boardEntity.getFileName());
        }
        if (!ObjectUtils.isEmpty(boardEntity.getOriginalName())) {
            boardDto.setOriginalName(boardEntity.getOriginalName());
        }
        if (!ObjectUtils.isEmpty(boardEntity.getCreatedDate())) {
            boardDto.setCreatedDate(boardEntity.getCreatedDate());
        }

        if (!ObjectUtils.isEmpty(boardEntity.getUpdateDate())) {
            boardDto.setUpdateDate(boardEntity.getUpdateDate());
        }
        MemberEntity member = boardEntity.getMember();
        boardDto.setUserId(member.getUserId());
        boardDto.setUserName(member.getUserName());

        return boardDto;
    }

}
