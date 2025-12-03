package net.datasa.web3.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.datasa.web3.dto.PersonDto;
import net.datasa.web3.dto.StudentDto;
import net.datasa.web3.entity.PersonEntity;
import net.datasa.web3.entity.StudentEntity;
import net.datasa.web3.repository.PersonRepository;
import net.datasa.web3.repository.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Optional;

// 서비스도 이거 꼭 붙여 줘야 함
@Slf4j
@Transactional
@Service
@RequiredArgsConstructor
public class StudentService {

    // 의존성 주입 @RequiredArgsConstructor 에 의해 구현체를 자동으로 생성자 주입 해 줌
    private final StudentRepository studentRepository;

    public void saveStudent(StudentDto student) {

        //log.info("student={}", student);
        StudentEntity studentEntity = new StudentEntity();

        studentEntity.setNumber(student.getNumber());
        studentEntity.setName(student.getName());
        studentEntity.setKorean(student.getKorean());
        studentEntity.setMath(student.getMath());
        studentEntity.setEnglish(student.getEnglish());

        studentRepository.save(studentEntity);

    }

    public StudentDto getStudentByNumber(String number) {

        StudentEntity studentEntity = studentRepository.findByNumber(number);


        StudentDto std = new StudentDto();
        // 비어있지 않으면
        if (!ObjectUtils.isEmpty(studentEntity)) {
            std.setNumber(studentEntity.getNumber());
            std.setName(studentEntity.getName());
            std.setKorean(studentEntity.getKorean());
            std.setMath(studentEntity.getMath());
            std.setEnglish(studentEntity.getEnglish());
        }

        return std;
    }
}
