package net.datasa.web5.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.datasa.web5.dto.MemberDto;
import net.datasa.web5.security.AuthenticatedUser;
import net.datasa.web5.service.MemberService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/join")
    public String join(Model model,
                       @RequestParam(name="isDuplicate", defaultValue = "") String isDuplicate,
                       @ModelAttribute("member") MemberDto memberDto) {


        log.info("isDuplicate = {}", isDuplicate);
        log.info("member = {}", memberDto);
        model.addAttribute("isDuplicate", isDuplicate);

        return "/member/join";
    }

    @PostMapping("/join")
    public String saveMember(@Validated @ModelAttribute("member") MemberDto member,
                             BindingResult bindingResult) {

        if (StringUtils.hasText(member.getUserId()) && "false".equals(member.getDupChecked())){
            bindingResult.addError(new FieldError("member", "userId", "아이디 중복 확인해주세요."));
        }

        if (!StringUtils.hasText(member.getPasswordRe())) {
            bindingResult.addError(new FieldError("member", "passwordRe", "비밀번호 재입력부분 채워주세요."));
        }

        if (!member.getPassword().equals(member.getPasswordRe())) {
            bindingResult.addError(new FieldError("member", "passwordRe", "비밀번호 입력과 재입력에 동일한 값을 넣어주세요."));
        }

        if (bindingResult.hasErrors()) {
            log.info("error = {}", bindingResult.getAllErrors());
            return "/member/join";
        }

        log.info("member = {}", member);
        memberService.saveMember(member);

        return "redirect:/";
    }

    @GetMapping("/checkId")
    public String checkId(@ModelAttribute MemberDto memberDto,
                          RedirectAttributes redirectAttributes) {

        MemberDto member = memberService.findMemberById(memberDto.getUserId());

        String isDuplicate = "true";
        if (ObjectUtils.isEmpty(member)){
            isDuplicate = "false";
        }

        redirectAttributes.addAttribute("isDuplicate", isDuplicate);

        redirectAttributes.addAttribute("userId", memberDto.getUserId());
        redirectAttributes.addAttribute("password", memberDto.getPassword());
        redirectAttributes.addAttribute("passwordRe", memberDto.getPasswordRe());
        redirectAttributes.addAttribute("userName", memberDto.getUserName());
        redirectAttributes.addAttribute("mailAddress", memberDto.getMailAddress());
        redirectAttributes.addAttribute("address", memberDto.getAddress());
        redirectAttributes.addAttribute("phoneNumber", memberDto.getPhoneNumber());

        return "redirect:/member/join";
    }

    @GetMapping("/login")
    public String login(Model model) {

        model.addAttribute("user", new MemberDto());

        return "/member/login";
    }

    @GetMapping("/logout")
    public String logout() {
        return "/member/logout";
    }

    @GetMapping("/chkpassword")
    public String chkPassword(@RequestParam String userId,
                              @RequestParam String chkPassword,
                              RedirectAttributes redirectAttributes) {

        boolean result = memberService.chkPassword(userId, chkPassword);
        log.info("비밀번호 체크 후 = {}", result);
        // @PathVariable 설정한거 외에는 자동으로 쿼리 스트링으로 세팅해줌

        String chkResult = result ? "true" : "false";
        redirectAttributes.addAttribute("chkResult", chkResult);
        return "redirect:/member/info";
    }

    // @AuthenticationPrincipal 로 받을 친구는 UserDetails 의 구현체여야 한다.
    // @AuthenticationPrincipal 은 인증된 사용자 정보를 자동으로 특정 오브젝트에 매핑해준다.
    // 로그인 정보 스프링 시큐리티 통해 확인하는 방법
    @GetMapping("/info")
    public String info(@AuthenticationPrincipal AuthenticatedUser user,
                       @RequestParam(name = "chkResult", defaultValue = "-") String chkResult,
                       Model model) {


        MemberDto memberDto = memberService.findMemberById(user.getUserId());

        log.info("chkResult = {}", chkResult);
        model.addAttribute("member", memberDto);
        model.addAttribute("chkResult", chkResult);

        return "/member/info";

    }

    @PostMapping("/info")
    public String updateMember(@Validated @ModelAttribute("member") MemberDto member, BindingResult bindingResult) {

        // password 가 입력된 경우에만 체크
        if (StringUtils.hasText(member.getUpdatePassword()) && !member.getUpdatePassword().equals(member.getPasswordRe())) {
            bindingResult.addError(new FieldError("member", "updatePassword", "비밀번호 입력과 재입력에 동일한 값을 넣어주세요."));
            bindingResult.addError(new FieldError("member", "passwordRe", "비밀번호 입력과 재입력에 동일한 값을 넣어주세요."));
        }

        if (bindingResult.hasErrors()) {
            log.info("bindingResult= {}", bindingResult.getAllErrors());
            return "/member/info";
        }
        
        // 개인정보 수정
        memberService.updateMember(member);
        
        return "redirect:/";
    }

    @GetMapping("/search")
    public String searchPage(Model model) {

        List<MemberDto> memberList = memberService.findAll();

        model.addAttribute("memberList", memberList);
        model.addAttribute("searchText", "");
        model.addAttribute("category", "userId");

        return "/member/search";
    }
    @PostMapping("/search")
    public String searchUser(@RequestParam String searchText,
                             @RequestParam String category, Model model) {

        List<MemberDto> memberList = new ArrayList<>();
        log.info("category ={}", category);
        log.info("searchText = {}", searchText);
        switch (category) {
            case "userId": memberList = memberService.findAllByUserIdLike(searchText);
                            break;
            case "userName" : memberList = memberService.findAllByUserNameLike(searchText);
                            break;
            default:
        }
        model.addAttribute("memberList", memberList);
        model.addAttribute("searchText", searchText);
        model.addAttribute("category", category);

        return "/member/search";
    }

    @GetMapping("/searchDetail")
    public String redirectSearchPage() {
        return "redirect:/member/search";
    }
    /**
     *  상세검색
     */
    @PostMapping("/searchDetail")
    public String searchDetail(@RequestParam String searchName,
                               @RequestParam String searchMail,
                               @RequestParam String searchNumber,
                               Model model) {

        List<MemberDto> memberList = memberService.findAllByDetailSearch(searchName, searchMail, searchNumber);


        model.addAttribute("isChecked", true);
        model.addAttribute("searchName", searchName);
        model.addAttribute("searchMail", searchMail);
        model.addAttribute("searchNumber", searchNumber);
        model.addAttribute("memberList", memberList);

        return "member/search";
    }

    @GetMapping("/findId")
    public String goFindIdForm(Model model) {

        model.addAttribute("msg", "");
        model.addAttribute("userName", "");
        model.addAttribute("mailAddress", "");

        return "member/findId";
    }

    @PostMapping("/findId")
    public String searchId(@RequestParam String userName,
                           @RequestParam String mailAddress,
                           Model model) {

        String findResult = memberService.findUserId(userName, mailAddress);

        if (!StringUtils.hasText(findResult)) {
           findResult = "조회 결과가 없습니다.";
        }

        model.addAttribute("msg", findResult);
        model.addAttribute("userName", userName);
        model.addAttribute("mailAddress", mailAddress);

        return "member/findId";
    }
}
