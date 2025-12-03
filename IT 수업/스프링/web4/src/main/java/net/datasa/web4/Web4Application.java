package net.datasa.web4;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing // @CreatedDate 같은 일자 자동입력 사용하고 싶으면 이거 반드시 등록해 줘야 한다!!
                  // 생성일자 관련 공통 엔티티를 만들어주는 어노테이션
@SpringBootApplication
public class Web4Application {

	public static void main(String[] args) {
		SpringApplication.run(Web4Application.class, args);
	}

}
