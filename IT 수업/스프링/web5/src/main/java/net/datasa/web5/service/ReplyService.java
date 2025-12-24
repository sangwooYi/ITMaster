package net.datasa.web5.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.datasa.web5.dto.ReplyDto;
import net.datasa.web5.entity.BoardEntity;
import net.datasa.web5.entity.MemberEntity;
import net.datasa.web5.entity.ReplyEntity;
import net.datasa.web5.repository.BoardRepository;
import net.datasa.web5.repository.MemberRepository;
import net.datasa.web5.repository.ReplyRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class ReplyService {

    private final BoardRepository boardRepository;
    private final ReplyRepository replyRepository;
    private final MemberRepository memberRepository;

    public ReplyDto saveReply(ReplyDto replyDto) {

        BoardEntity boardEntity = boardRepository.findById(replyDto.getBoardNum()).orElseThrow(() -> new EntityNotFoundException("게시판이 존재하지 않습니다."));
        MemberEntity memberEntity = memberRepository.findById(boardEntity.getMember().getUserId()).orElseThrow(
                () -> new EntityNotFoundException("해당 사용자가 없습니다."));

        ReplyEntity replyEntity = ReplyEntity.builder()
                .board(boardEntity)
                .member(memberEntity)
                .content(replyDto.getContent())
                .build();

        ReplyEntity result = replyRepository.save(replyEntity);

        return this.convertEntityToReplyDto(result);
    }

    public List<ReplyDto> findAll() {

        List<ReplyEntity> replyEntityList = replyRepository.findAll();
        List<ReplyDto> replyDtoList = new ArrayList<>();

        replyEntityList.forEach(entity -> replyDtoList.add(this.convertEntityToReplyDto(entity)));

        return replyDtoList;
    }

    public List<ReplyDto> findAllByBoardNum(Integer boardNum) {

        BoardEntity boardEntity = boardRepository.findById(boardNum).orElseThrow(() -> new EntityNotFoundException("해당 게시판 정보가 없습니다."));

        List<ReplyEntity> replyEntityList = replyRepository.findAllByBoard(boardEntity);
        List<ReplyDto> replyDtoList = new ArrayList<>();
        replyEntityList.forEach(entity -> replyDtoList.add(this.convertEntityToReplyDto(entity)));

        return replyDtoList;
    }

    public void deleteReply(Integer replyNum) {

        ReplyEntity replyEntity = replyRepository.findById(replyNum).orElseThrow(() -> new EntityNotFoundException("댓글 없어"));

        replyRepository.delete(replyEntity);

    }

    public ReplyDto convertEntityToReplyDto(ReplyEntity replyEntity) {

        ReplyDto replyDto = new ReplyDto();

        if (!ObjectUtils.isEmpty(replyEntity.getReplyNum())) {
            replyDto.setReplyNum(replyEntity.getReplyNum());
        }
        if (!ObjectUtils.isEmpty(replyEntity.getContent())) {
            replyDto.setContent(replyEntity.getContent());
        }
        if (!ObjectUtils.isEmpty(replyEntity.getCreateDate())) {
            replyDto.setCreateTime(replyEntity.getCreateDate());
        }
        return replyDto;
    }
}
