package net.data.web6.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@RequiredArgsConstructor
@Controller
@RequestMapping("/jquery")
public class JqueryController {

    @GetMapping("/basic")
    public String goBasicPage() {
        return "jquery/basic";
    }

    @GetMapping("/event")
    public String goEventPage() {
        return "jquery/event";
    }

    @GetMapping("/exam1")
    public String goEventExamPage() {
        return "jquery/eventExam";
    }
}
