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
@Table(name="board")
@EntityListeners(value = { AuditingEntityListener.class})
public class BoardEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_num")
    private Integer boardNum;

    // 하나의 유저가 여러개 글 작성 가능 따라서 1:다
    @ManyToOne(fetch = FetchType.LAZY)      // 디폴트는 EAGER (즉시로딩) 근데 @ManyToOne 은
                                            // 왠만하면 LAZY 사용 (지연로딩, 실제로 쓰일때 맞춰서 로딩 )
    // name이 Board 쪽 외래키 , referencedColumnName 는 상대 테이블 PK 값
    @JoinColumn(name="user_id", referencedColumnName = "user_id", nullable = false)
    private MemberEntity member;

    // title VARCHAR(1000) NOT NULL
    @Column(nullable = false, length = 1000)
    private String title;

    // contents TEXT NOT NULL
    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    // view_count INT DEFAULT 0
    // DB의 DEFAULT 0을 따르려면 @ColumnDefault("0") (Hibernate) 사용
    @ColumnDefault("0")
    private Integer viewCount;

    // like_count INT DEFAULT 0
    @ColumnDefault("0")
    private Integer likeCount;

    // original_name VARCHAR(500)
    @Column(length = 500)
    private String originalName;

    // file_name VARCHAR(300)
    @Column(length = 300)
    private String fileName;

    // created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    @CreatedDate
    @Column(name="created_date", updatable = false) // 최초 저장 후 수정 불가
    private LocalDateTime createdDate;

    @LastModifiedDate
    @Column(name="update_date")
    private LocalDateTime updateDate;
}
