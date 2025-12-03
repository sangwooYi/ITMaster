package net.datasa.web2.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import net.datasa.Person;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/th")
public class ThymeleafController {

    @GetMapping("/thymeleaf1")
    public String thymeleafTest1(Model model) {

        String str = "abc";

        Person person = new Person("okqwaszx", "123123", "아이묭", "도쿄시");
        String tag = "<marquee>html 태그</marquee>";
        String url = "https://google.com";

        model.addAttribute("str", str);
        model.addAttribute("person", person);
        model.addAttribute("tag", tag);
        model.addAttribute("url", url);
        model.addAttribute("num", 10);

        int largeNum = 10000000;
        double doubleNum = 123.4567;
        LocalDate curDate = LocalDate.now();
        LocalDateTime curDateTime = LocalDateTime.now();

        model.addAttribute("largeNum", largeNum);
        model.addAttribute("doubleNum", doubleNum);
        model.addAttribute("curDate", curDate);
        model.addAttribute("curDateTime", curDateTime);
        model.addAttribute("perNum", 0.05);

        return "/thymeleaf/test1";
    }

    @GetMapping("/thymeleaf2")
    public String thymeleafTest2(Model model) {

        Person person1 = new Person("okqwaszx", "123123", "아이묭", "도쿄시");
        Person person2 = new Person("okqwaszx2", "33333", "이상우", "도쿄시");
        Person person3 = new Person("okqwaszx3", "5555", "염지선", "도쿄시");
        Person person4 = new Person("okqwaszx4", "7777", "김기기", "도쿄시");

        List<Person> personList = new ArrayList<>();
        personList.add(person1);
        personList.add(person2);
        personList.add(person3);
        personList.add(person4);

        Map<String, String> tmpMap = new HashMap<>();
        tmpMap.put("kimchi", "김치");
        tmpMap.put("food", "고기");
        tmpMap.put("uta", "아이묭");

        model.addAttribute("tmpMap", tmpMap);
        model.addAttribute("personList", personList);

        model.addAttribute("name", "abc");
        model.addAttribute("age", 10);

        return "/thymeleaf/test2";
    }

    @GetMapping("/example")
    public String goToExample(HttpServletRequest request,
                              HttpServletResponse response,
                              // 없으면 그냥 1로
                              @CookieValue(name = "visitCount", defaultValue = "1") Integer visitCount,
                              Model model) {

        if (visitCount != null) {
            visitCount++;
        }

        // 쿠키 갱신하려면 그냥 새로 만들어서 add 해줘야 한다.
        Cookie cookie = new Cookie("visitCount", Integer.toString(visitCount));
        cookie.setPath("/");        // 전체 적용
        cookie.setMaxAge(24*60*60); // 유효기간 1일
        response.addCookie(cookie);


        model.addAttribute("visitCount", visitCount);
        return "/thymeleaf/example";
    }
}
