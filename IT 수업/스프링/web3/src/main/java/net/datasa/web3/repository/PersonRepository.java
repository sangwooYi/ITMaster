package net.datasa.web3.repository;


import net.datasa.web3.entity.PersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;

// JPA 사용을 위해서는 JpaRepository 를 우선 상속 받는다. 인자로는 어떤 테이블에 대한건지, PK 값의 타입을 전달해 줘야 함
public interface PersonRepository  extends JpaRepository<PersonEntity, String> {

    //
}
