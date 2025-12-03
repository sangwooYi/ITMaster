package net.datasa.web2.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;

@Slf4j
@RequestMapping("/cookie")
@Controller
public class CookieController {

    @RequestMapping("/cookie-test1")
    public String saveCookie(HttpServletRequest request,
                             HttpServletResponse response) {

        Cookie cookie = new Cookie("loginId", "okqwaszx");
        Cookie cookie2 = new Cookie("num", "1");
        cookie.setMaxAge(60*10); // 쿠키 유효기간 설정 (초 단위)
        cookie2.setMaxAge(60*10);

        log.info("cookie Path = {}", cookie.getPath());
        // 경로 설정 안해주면 /cookie 이하 경로에서만 사용 가능하다 (주의!)
        cookie.setPath("/");    // 이건 모든 경로에서 사용하겠다는 의미
        cookie2.setPath("/");

        // 쿠키 add는 response에다가 해야 함
        response.addCookie(cookie);
        response.addCookie(cookie2);

        return "redirect:/";
    }

    @RequestMapping("/cookie-test2")
    public String removeAllCookie(HttpServletRequest request,
                               HttpServletResponse response) {

        Cookie[] cookies = request.getCookies();

        for (Cookie cookie : cookies) {
            // 쿠키는 삭제가아니라 이렇게 파기한다 ( 시간을 0으로 설정 ) 별도로 삭제는 없다
            // 동일한 Name 값의 쿠키를 새로 생성해서 MaxAge 0 초로 설정한 후 덮어쓰면 됨
            // maxAge, path 설정도 당연히 동일하게 해줘야 함
            Cookie nCookie = new Cookie(cookie.getName(), null);
            nCookie.setMaxAge(0);
            nCookie.setPath("/");
            response.addCookie(nCookie);
        }

        return "redirect:/";
    }

    @RequestMapping("/cookie-test3")
    public String readCookie(HttpServletRequest request,
                             HttpServletResponse response) {
        
        // 이렇게 돌리거나 위에처럼 for each 문 돌리거나
        Arrays.stream(request.getCookies()).toList().forEach((cookie) -> {
            if (cookie.getName().equals("loginId")) {
                log.info("현재 cookie Name = {}", cookie.getName());
                log.info("현재 cookie Val = {}", cookie.getValue());
            }
        });

        return "redirect:/";
    }

    // Cookie 값은 @CookieValue로 가져올 수 있다.
    @RequestMapping("/cookie-test4")
    public String readCookie2(@CookieValue("loginId") String loginId,
                              @CookieValue(value = "num", defaultValue = "5") Integer num) {

        log.info("loginId = {}", loginId);
        log.info("num = {}", num);

        return "redirect:/";

    }

    @GetMapping("/session")
    public String sessionTest() {
        return "local";
    }
}
