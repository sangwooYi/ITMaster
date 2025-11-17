package net.datasa.test;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/test")
public class FrontController {

    // 끝에 / 으로 끝낼때랑 아닐때랑 구분된다.
    @RequestMapping("")
    public String goTest() {
        return "test";
    }
}
