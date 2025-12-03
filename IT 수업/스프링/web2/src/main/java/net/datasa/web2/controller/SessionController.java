package net.datasa.web2.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;

@Slf4j
@RequestMapping("/ss")
@Controller
public class SessionController {

    @RequestMapping(path = "/session1", method = RequestMethod.GET)
    public String saveSession(HttpServletRequest request) {
        HttpSession session = request.getSession();

        session.setAttribute("name", "이상우");

        return "redirect:/";
    }

    @RequestMapping(path = "/session2", method = RequestMethod.GET)
    public String getSession(HttpServletRequest request) {
        HttpSession session = request.getSession();

        session.getAttribute("name");
        log.info("세션 저장 name = {}", session.getAttribute("name"));

        return "redirect:/";
    }

    @RequestMapping(path = "/session3", method = RequestMethod.GET)
    public String deleteSession(HttpServletRequest request) {

        HttpSession session = request.getSession();

        log.info("세션 삭제 전 name = {}", session.getAttribute("name"));
        session.removeAttribute("name");
        log.info("세션 삭제 후 name = {}", session.getAttribute("name"));

        return "redirect:/";
    }

    @GetMapping("/login")
    public String login(HttpServletRequest request,
                        Model model) {

        HttpSession session = request.getSession();
        model.addAttribute("userId", session.getAttribute("userId"));
        model.addAttribute("password", session.getAttribute("password"));

        return "sessionView/login";
    }

    @PostMapping("/login")
    public String login(Model model,
                        HttpServletRequest request,
                        @RequestParam String userId,
                        @RequestParam String password) {

        model.addAttribute("userId", userId);
        model.addAttribute("password", password);

        request.getSession().setAttribute("userId", userId);
        request.getSession().setAttribute("password", password);
        log.info("userId={}", userId);

        return "sessionView/login";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {

        request.getSession().removeAttribute("userId");
        request.getSession().removeAttribute("password");

        return "redirect:/";
    }

    @PostMapping("/test")
    public String test(HttpServletRequest request,
                       Model model,
                       @RequestParam("userId2") String userId,
                       @RequestParam("password2") String password) {

        HttpSession session = request.getSession();

        String path = "";
        // 비어있으면 로그인 경로
        if (ObjectUtils.isEmpty(session.getAttribute("userId"))) {
            session.setAttribute("userId", userId);
            session.setAttribute("password", password);
            path = "redirect:/ss/login";
        } else {
            model.addAttribute("userId", userId);
            model.addAttribute("password", password);
            path = "/sessionView/test";
        }

        return path;
    }

}
