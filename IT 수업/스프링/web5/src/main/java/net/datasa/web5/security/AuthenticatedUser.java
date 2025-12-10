package net.datasa.web5.security;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AuthenticatedUser implements UserDetails {

    private String userId;
    private String password;
    private String userName;
    private String roleName;
    private boolean isActive;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(roleName));
    }

    // 이게 아이디 체크
    @Override
    public String getUsername() {
        return this.userId;
    }

    // 비밀번호 체크
    @Override
    public String getPassword() {
        return this.password;
    }

    // 유저 이름
    public String getName() {
        return this.userName;
    }
    // 역할
    public String getRoleName() {
        return this.getRoleName();
    }

    // 아래 주석처리한 애들은 어차피 디폴트 값이 return true 임
//    @Override
//    public boolean isAccountNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return true;
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return true;
//    }

    @Override
    public boolean isEnabled(){
        return this.isActive;
    }
}
