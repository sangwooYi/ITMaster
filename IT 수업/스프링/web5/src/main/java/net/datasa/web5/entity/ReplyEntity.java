package net.datasa.web5.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="reply")
@EntityListeners(value = { AuditingEntityListener.class})
public class ReplyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reply_num")
    private Integer replyNum;

    @Column(name = "board_num")
    private Integer boardNum;

    @Column(name="user_id")
    private String userId;
    // 하나의 유저가 여러개 글 작성 가능 따라서 1:다
    @ManyToOne(fetch = FetchType.LAZY)      // 디폴트는 EAGER (즉시로딩) 근데 @ManyToOne 은
                                            // 왠만하면 LAZY 사용 (지연로딩, 실제로 쓰일때 맞춰서 로딩 )
    // name이 Board 쪽 외래키 , referencedColumnName 는 상대 테이블 PK 값
    @JoinColumn(name="user_id", referencedColumnName = "user_id", nullable = false)
    private MemberEntity member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="board_num", referencedColumnName = "board_num", nullable = false)
    private BoardEntity board;

    // contents TEXT NOT NULL
    @Column(nullable = false)
    private String content;

    // created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    @CreatedDate
    @Column(name="create_date", updatable = false) // 최초 저장 후 수정 불가
    private LocalDateTime createDate;

}
