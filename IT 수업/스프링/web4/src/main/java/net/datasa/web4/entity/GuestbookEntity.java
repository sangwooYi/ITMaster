package net.datasa.web4.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;



@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "guestbook")
@EntityListeners(value = { AuditingEntityListener.class})   // 엔티티 클래스에 EntityListeners 붙여 주기
public class GuestbookEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="board_num")
    private Integer boardNum;

    @Column(name="user_name")
    private String userName;

    @Column
    private String password;

    @Column
    private String message;

    @Column
    @ColumnDefault("0")
    private Integer recommend;

    @Column(name="report_count")
    @ColumnDefault("0")
    private Integer reportCount;

    @Column(name="user_ip")
    private String userIp;

    // 이거 쓰려면 @EnableJpaAuditing, @EntityListeners 가 사전에 세팅되어있어야 한다.
    @CreatedDate
    @Column(name="input_date", updatable = false)
    private LocalDateTime inputDate;

    // 업데이트 할때마다 업데이트
    @LastModifiedDate
    @Column(name="update_date")
    private LocalDateTime updateDate;

}
