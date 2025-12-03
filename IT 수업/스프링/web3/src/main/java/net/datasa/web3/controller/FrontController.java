package net.datasa.web3.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.datasa.web3.dto.PersonDto;
import net.datasa.web3.dto.StudentDto;
import net.datasa.web3.service.PersonService;
import net.datasa.web3.service.StudentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
public class FrontController {

    private final PersonService personService;
    private final StudentService studentService;

    @GetMapping("/home")
    public String goHome() {
        return "redirect:/";
    }

    @RequestMapping("/test/test1")
    public void test1() {
        personService.test();
    }

    @GetMapping("/input")
    public String inputTest() {
        return "input";
    }

    @PostMapping("/input")
    public String inputTest(@ModelAttribute PersonDto person) {
        log.info("Person={}", person);

        personService.insertPerson(person);

        return "redirect:/";
    }

    //  요청경로 :  /delete/3333 식으로 요청
    @GetMapping("/delete/{userId}")
    public String deleteTest(@PathVariable String userId) {
        log.info("userId={}", userId);

        personService.deletePerson(userId);
    
        // 이렇게 서버로 리다이렉트 해야함
        return "redirect:/person/all";
    }

    @GetMapping("/select")
    public String getPerson(@RequestParam String userId,
                            Model model) {
        log.info("select = {}", userId);

        PersonDto personDto = personService.findPersonById(userId);

        model.addAttribute("userId", userId);
        model.addAttribute("person", personDto);

        return "/person/personDetail";
    }

    @GetMapping("/person/all")
    public String getAllPerson(Model model) {
        List<PersonDto> personList = personService.findPersonAll();
        model.addAttribute("personList", personList);

        return "/person/personList";
    }

    @GetMapping("/person/{userId}")
    public String getPersonDetail(@PathVariable String userId, Model model) {

        PersonDto person = personService.findPersonById(userId);
        model.addAttribute("person", person);

        return "/person/personDetail";
    }


    // 요청경로 : /delete?userId=3333
    @GetMapping("/delete")
    public String deleteTest2(@RequestParam String userId) {

        log.info("userId={userId}", userId);

        return "/person/personList";
    }
    
    // 업데이트
    @GetMapping("/update/{userId}")
    public String update(@PathVariable String userId, Model model) {

        PersonDto person = personService.findPersonById(userId);

        model.addAttribute("userId", userId);
        model.addAttribute("person", person);

        return "/person/edit";
    }

    @PostMapping("/update/{userId}")
    public String update(@PathVariable String userId,
                         @ModelAttribute PersonDto person) {

        personService.update(person);


        return "redirect:/person/" + userId;
    }


}
