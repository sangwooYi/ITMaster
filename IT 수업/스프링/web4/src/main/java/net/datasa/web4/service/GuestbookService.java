package net.datasa.web4.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.datasa.web4.dto.GuestBookDto;
import net.datasa.web4.entity.GuestbookEntity;
import net.datasa.web4.repository.GuestbookRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class GuestbookService {

    private final GuestbookRepository guestbookRepository;

    public GuestBookDto findGuestBookByBoardNum(Integer boardNum) {

        GuestbookEntity guestbookEntity = guestbookRepository.findById(boardNum).orElse(null);

        if (ObjectUtils.isEmpty(guestbookEntity)) {
            return new GuestBookDto();
        }

        GuestBookDto guestBookDto = this.convertToDto(guestbookEntity);

        return guestBookDto;
    }

    public GuestBookDto saveGuestBook(GuestBookDto guestBookDto) {


        //GuestbookEntity guestbookEntity = this.convertToEntity(guestBookDto);

        // 빌더 패턴 활용
        GuestbookEntity guestbookEntity = GuestbookEntity.builder()
                .userName(guestBookDto.getUserName())
                .password(guestBookDto.getPassword())
                .message(guestBookDto.getMessage())
                .build();

        GuestbookEntity resultEntity = guestbookRepository.save(guestbookEntity);

        GuestBookDto resultDto = this.convertToDto(resultEntity);

        log.info("결과 : {}", resultDto);

        return resultDto;
    }

    /**
     *
     * @param boardNum
     * @throws EntityNotFoundException : 해당 엔티티가 없을때 이 예외 던져짐
     */
    public void delete(Integer boardNum, String password) {

        GuestbookEntity entity = guestbookRepository.findById(boardNum)
                .orElseThrow(() -> new EntityNotFoundException("해당 글이 없습니다."));

        if (!password.equals(entity.getPassword())) {
            throw new RuntimeException("비밀번호가 다릅니다.");
        }

        guestbookRepository.deleteById(boardNum);
    }

    public List<GuestBookDto> findAll() {

        List<GuestbookEntity> guestbookEntityList = guestbookRepository.findAll(Sort.by(Sort.Direction.DESC, "inputDate"));

        List<GuestBookDto> guestBookDtoList = new ArrayList<>();

        guestbookEntityList.forEach((entity) -> {
            GuestBookDto guestBookDto = this.convertToDto(entity);
            guestBookDtoList.add(guestBookDto);
        });

        return guestBookDtoList;
    }

    public void updateBoard(GuestBookDto guestBookDto) {

        GuestbookEntity entity = guestbookRepository.findById(guestBookDto.getBoardNum()).orElseThrow(() -> new EntityNotFoundException("해당 값이 없습니다."));

        GuestbookEntity guestbookEntity = GuestbookEntity.builder()
                .boardNum(guestBookDto.getBoardNum())
                .userName(guestBookDto.getUserName())
                .message(guestBookDto.getMessage())
                .password(guestBookDto.getPassword())
                .build();

        guestbookRepository.save(guestbookEntity);

    }

    public GuestBookDto convertToDto(GuestbookEntity guestbookEntity) {

        GuestBookDto guestBookDto = new GuestBookDto();

        guestBookDto.setBoardNum(guestbookEntity.getBoardNum());
        guestBookDto.setPassword(guestbookEntity.getPassword());
        guestBookDto.setUserName(guestbookEntity.getUserName());
        guestBookDto.setMessage(guestbookEntity.getMessage());
        guestBookDto.setInputDate(guestbookEntity.getInputDate());

        return guestBookDto;
    }

    public GuestbookEntity convertToEntity(GuestBookDto guestBookDto) {

        GuestbookEntity guestbookEntity = new GuestbookEntity();

        // 존재하는 값만 넣어주기 (save 때 영속성 에러 방지 )
        if (!ObjectUtils.isEmpty(guestBookDto.getBoardNum())) {
            guestbookEntity.setBoardNum(guestBookDto.getBoardNum());
        }
        if (StringUtils.hasText(guestBookDto.getPassword())) {
            guestbookEntity.setPassword(guestBookDto.getPassword());
        }
        if (StringUtils.hasText(guestBookDto.getUserName())) {
            guestbookEntity.setUserName(guestBookDto.getUserName());
        }
        if (StringUtils.hasText(guestBookDto.getMessage())) {
            guestbookEntity.setMessage(guestBookDto.getMessage());
        }
        if (!ObjectUtils.isEmpty(guestBookDto.getInputDate())) {
            guestbookEntity.setInputDate(guestBookDto.getInputDate());
        }

        return guestbookEntity;

    }

}
