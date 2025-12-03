package net.datasa.web3.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.datasa.web3.dto.PersonDto;
import net.datasa.web3.entity.PersonEntity;
import net.datasa.web3.repository.PersonRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

// 서비스도 이거 꼭 붙여 줘야 함
@Slf4j
@Transactional
@Service
@RequiredArgsConstructor
public class PersonService {

    // 의존성 주입 @RequiredArgsConstructor 에 의해 구현체를 자동으로 생성자 주입 해 줌
    private final PersonRepository personRepository;

    public String test() {
        log.info("오니 여기?? ");
        PersonEntity entity = new PersonEntity();
        entity.setUserId("aim3636");
        entity.setUserName("아이묭");
        entity.setAge(30);

        personRepository.save(entity);

        return "redirect:/";
    }

    /**
     * userId 통해서 1명의 회원정보 조회
     * @param userId
     * @return PersonDto 타입으로 반환, 없으면 null 반환
     */
    public PersonDto findPersonById(String userId) {

        PersonEntity personEntity = personRepository.findById(userId).orElse(null);

        PersonDto personDto = null;
        if (!ObjectUtils.isEmpty(personEntity)) {
            personDto = new PersonDto();

            personDto.setUserId(personEntity.getUserId());
            personDto.setUserName(personEntity.getUserName());
            personDto.setAge(personEntity.getAge());

        }


        return personDto;
    }

    public List<PersonDto> findPersonAll() {

        List<PersonEntity> personEntityList = personRepository.findAll();

        List<PersonDto> personDtoList = new ArrayList<>();

        personEntityList.forEach((personEntity) -> {

            PersonDto personDto = new PersonDto();
            personDto.setUserId(personEntity.getUserId());
            personDto.setUserName(personEntity.getUserName());
            personDto.setAge(personEntity.getAge());
            personDtoList.add(personDto);
        });

        return personDtoList;
    }

    public void insertPerson(PersonDto person) {
        PersonEntity entity = new PersonEntity();

        entity.setUserId(person.getUserId());
        entity.setUserName(person.getUserName());
        entity.setAge(person.getAge());

        // null 가능성 있는건 Optional 로 받기! ( null 상태값을 처리 가능하도록 도와주는 객체 )
        Optional<PersonEntity> curPerson = personRepository.findById(person.getUserId());
        log.info("curPerson={}", curPerson);
        if (ObjectUtils.isEmpty(curPerson)) {
            personRepository.save(entity);
        }
    }

    public void deletePerson(String userId) {

        // 그냥 deleteById써도 됨
        //personRepository.deleteById(userId);

        // 이건 엔티티 전체를 넘기는 삭제 방식
        Optional<PersonEntity> curPerson = personRepository.findById(userId);
        
        log.info("curPerson={} 조회 되면 삭제", curPerson);
        if (!ObjectUtils.isEmpty(curPerson)) {
            personRepository.delete(curPerson.get());
        }
    }

    // 주의! update는 기존 껏 가져와서 => 수정 후 => 재저장 로직임
    public void update(PersonDto person) {

        // 기존
        // orElseThrow 로 해주면 null 인 경우 알아서 Throw 통해 예외처리 됨
        PersonEntity personEntity = personRepository.findById(person.getUserId()).orElseThrow(() -> new EntityNotFoundException("수정할 정보 없음"));
    
        // 따라서 여기서 굳이 null 체크를 할 필요가 없음
        // findById 로 받아온 엔티티는 영속성 관리 대상임 주의!
        // 따라서 이렇게 수정만 해도, 실제 DB 도 같이 바뀐다!
        // 또 save 를 할 필요 없음!
        personEntity.setUserName(person.getUserName());
        personEntity.setAge(person.getAge());

        // PK 값이 겹치면 알아서 update 해주는게 save 메서드
        // 이렇게 save 이후 뱉어내는 엔티티는 JPA 가 관리해주는 엔티티임
        //PersonEntity nPersonEntity = personRepository.save(personEntity);    // save 후 결과를 뱉어낸다.

    }

}
