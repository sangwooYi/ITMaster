package net.datasa.web2.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

// 주의할점! 항상 Web2Application depth 레벨 이하에 존재해야 빈컨테이너 관리를 받을 수 있다!
// @ComponentScan 영향을 받는게 ComponentScan 설정된 위치 이하 depth 전체임
@Slf4j
@RequestMapping("/param")
@Controller
public class ParamController {
    
    // 이거랑 @GetMapping("/view1") 이랑 동일
    @RequestMapping( path = "/view1", method = RequestMethod.GET)
    public String goView() {
        return "param/view1";
    }

    @RequestMapping( path = "/view2", method = RequestMethod.GET)
    public String goView2() {
        return "param/view2";
    }

}
