package net.datasa.web5.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    private static final String[] PUBLIC_URS = {
            "/",
            "/security",
            "/img/**",
            "/css/**",
            "/js/**",
            "/member/checkId",
            "/member/join",
            "/member/findId",
            "/member/test"
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        // authorizeHttpRequests 는 그냥 허용할 부분을 설정
        // formLogin 는 로그인 form 을 설정 ( 로그인 경로를 설정해주는 것 디폴트는 /login 이나 이걸 아래처럼 경로 설정 가능 ) 
        // logout 은 로그아웃 관련하여 정의
        httpSecurity.authorizeHttpRequests((requests) ->
                requests.requestMatchers(PUBLIC_URS).permitAll().anyRequest().authenticated()
        ).formLogin((form) ->
                form.loginPage("/member/login")
                        .usernameParameter("userId")
                        .passwordParameter("password")
                        .loginProcessingUrl("/member/login")
                        .defaultSuccessUrl("/", true)
                        .permitAll()
        ).logout((logout) -> logout
                .logoutUrl("/member/logout")
                .invalidateHttpSession(true) // 세션 무효화
                .clearAuthentication(true) // 인증 정보 제거
                .logoutSuccessUrl("/member/login"));

        return httpSecurity.build();
    }

    @Bean
    public BCryptPasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
