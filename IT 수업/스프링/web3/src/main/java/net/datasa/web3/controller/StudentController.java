package net.datasa.web3.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.datasa.web3.dto.StudentDto;
import net.datasa.web3.service.StudentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequestMapping("/student")
@RequiredArgsConstructor
public class StudentController {

    public final StudentService studentService;

    @GetMapping("/save")
    public String save() {
        return "/student/saveForm";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute("student") StudentDto student) {

        log.info("student={}", student);

        studentService.saveStudent(student);

        return "/student/studentDetail";
    }

    @GetMapping("/select")
    public String goSelectPage() {
        return "/student/selectPage";
    }

    @GetMapping("/select/user")
    public String goSelectPage(@RequestParam String number,
                               Model model) {

        StudentDto std = studentService.getStudentByNumber(number);

        log.info("std = {}" , std);

        model.addAttribute("number", number);
        model.addAttribute("student", std);

        return "/student/studentDetail";
    }
}
