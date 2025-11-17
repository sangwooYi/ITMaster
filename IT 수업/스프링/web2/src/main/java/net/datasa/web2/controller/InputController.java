package net.datasa.web2.controller;

import lombok.extern.slf4j.Slf4j;
import net.datasa.Person;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RequestMapping("/input")
@Controller
public class InputController {

    @PostMapping("/input1")
    public String inputFirst(@RequestParam String userId,   // 만약에 param 명이 다르면 @RequestParam("user-id") String userId 이런식으로 매핑해야함
                             @RequestParam String password,
                             @RequestParam String userName,
                             @RequestParam String address) {

        // 이러면 index.html 로 리다이렉트 됨
        return "redirect:/";
    }

    @PostMapping("/input2")
    public String inputSecond(@ModelAttribute Person person) {

        log.info("Person = {}", person);

        // 이러면 index.html 로 리다이렉트 됨
        return "redirect:/";
    }

    // url 경로에 ? 이후에 친구들이 전부 parameter  ?key1=val1&key2=val2 이런형태로 구성된다.
    @GetMapping("/input3")
    public String inputThird(@RequestParam Integer num) {

        log.info("num = {}", num);

        return "redirect:/";
    }

    // Path Variable ( Path Variable 을 가져오는 친구 아래처럼 param 전에 / 로 이어지는 친구들이 Path Variable)
    @GetMapping("/input4/{num}")
    public String inputFourth(@PathVariable Integer num,
                              Model model) {

        log.info("num = {}", num);
        Person person1 = new Person("okqwaszx", "qwaszx", "상우", "우리집");
        model.addAttribute("num", num);
        model.addAttribute("person", person1);
        return "/param/path-variable";
    }
}
