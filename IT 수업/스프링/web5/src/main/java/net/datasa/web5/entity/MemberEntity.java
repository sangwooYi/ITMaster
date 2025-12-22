package net.datasa.web5.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="member")
@EntityListeners(value = { AuditingEntityListener.class})
public class MemberEntity {

    @Id
    @Column(name="user_id")
    private String userId;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<BoardEntity> boardList = new ArrayList<>();

    public void addBoard(BoardEntity board) {
        board.setMember(this);
        boardList.add(board);
    }

    @OneToMany(mappedBy = "reply", cascade = CascadeType.ALL)
    private List<ReplyEntity> replyList = new ArrayList<>();

    private void addReply(ReplyEntity reply) {
        reply.setMember(this);
        replyList.add(reply);
    }

    @Column
    private String password;

    @Column(name="user_name")
    private String userName;

    @Column(name="mail_address")
    private String mailAddress;

    @Column(name="phone_number")
    private String phoneNumber;

    @Column
    private String address;

    // 이렇게 쿼리형태로 columnDefinition 세팅도 가능
    @Column(name="role_name", columnDefinition = "default 'role_normal' check ( role_name in ('role_normal', 'role_admin'))")
    @ColumnDefault("role_normal")
    private String roleName;

    @Column(name="is_active")
    @ColumnDefault("1")
    private Byte isActive;

    @CreatedDate
    @Column(name="register_date", updatable = false)
    private LocalDateTime registerDate;

}
