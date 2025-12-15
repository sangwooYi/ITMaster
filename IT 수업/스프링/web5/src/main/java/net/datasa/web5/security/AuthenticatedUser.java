package net.datasa.web5.security;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.Locale;

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
    
        // 대문자로 바꿔줘야 비교 가능
        String dbRole = roleName.toUpperCase();

        return Collections.singletonList(new SimpleGrantedAuthority(dbRole));
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
        return this.roleName;
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
