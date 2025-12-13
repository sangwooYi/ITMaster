package net.datasa.web5.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.datasa.web5.entity.MemberEntity;
import net.datasa.web5.repository.MemberRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticatedUserDetailsService  implements UserDetailsService {

    private final MemberRepository memberRepository;

    //
    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        log.info("로그인 시도 : {}", userId);

        // 예외 던지면 밑에서 Null 관련 예외처리 추가로 할 필요 X
        MemberEntity memberEntity = memberRepository.findById(userId).orElseThrow(() -> new UsernameNotFoundException("엔티티 없음"));

        log.info("member : {}" , memberEntity);

        // 인증정보 생성 ( AuthenticatedUser 는 UserDetails 인터페이스의 구현체임 )
        AuthenticatedUser user = AuthenticatedUser.builder()
                .userId(memberEntity.getUserId())
                .password(memberEntity.getPassword())
                .userName(memberEntity.getUserName())
                .isActive(memberEntity.getIsActive() == 1 ? true : false)
                .roleName(memberEntity.getRoleName())
                .build();

        return user;
    }
}
