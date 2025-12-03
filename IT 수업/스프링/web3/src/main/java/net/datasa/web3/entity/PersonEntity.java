package net.datasa.web3.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
// 테이블 명과 동일해야함, 생략하면 클래스명 기준으로 매핑 함
@Table(name="person")
public class PersonEntity {

    @Id         // PK 의미
    @Column(name="user_id", nullable = false, length = 30)    // 테이블 컬럼과 매핑 + NOT NULL 설정 + MAX 길이 설정
    private String userId;

    @Column(name="user_name")
    private String userName;

    @Column
    private Integer age;
}
