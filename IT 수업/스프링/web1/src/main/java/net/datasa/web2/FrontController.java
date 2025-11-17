package net.datasa.web2;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class FrontController {

    // 그냥 Controller 면 자동으로 /templates/home.html 우로 매핑해 줌
    // 근데 @RestController 를 해버리면 그냥 아래처럼 작성시 "home" String 이 찍힘 (String 이 그대로 전달)
    @RequestMapping("/aa")
    public String goHome() {
        return "home";
    }

    @RequestMapping("/test1")
    public String goTest1Page(Model model) {

        // static 파일들도 기본경로가 /static 임 따라서, 그 이후부터 경로 작성해주면 된다!
        //model.addAttribute("imgSrc", "image/favicon.png");

        return "test1";
    }

    @RequestMapping("/css/test2")
    public String goToTest2Page(Model model) {
        // ../ 앞에 붙여줘야하는 이유. 현재 여기 접근할 때 url path 는 /css/test2 임.
        // 그래서 여기서 test1 처럼 경로를 써버리면 실제로는 /static/css/image/favicon.png 경로를 찾게 되는거임! 주의!!
        //model.addAttribute("imgSrc", "../image/favicon.png");
        return "test2";
    }

    @RequestMapping("/javatest")
    public String javaTest() {


        return "javatest";
    }
}
