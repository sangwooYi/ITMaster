package net.datasa.web4.controller;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.datasa.web4.dto.GuestBookDto;
import net.datasa.web4.service.GuestbookService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class GuestbookController {

    private final GuestbookService guestbookService;

    // @RequiredArgsConstructor 가 이걸 자동으로 해주는 거
//    @Autowired
//    public GuestbookController(GuestbookService guestbookService) {
//        this.guestbookService = guestbookService;
//    }

    @GetMapping("/read/{boardNum}")
    public String goDetail(@PathVariable Integer boardNum, Model model) {

        GuestBookDto guestBookDto = guestbookService.findGuestBookByBoardNum(boardNum);

        log.info("가져온 DTO = {}", guestBookDto);

        model.addAttribute("guestbook", guestBookDto);

        return "detail";
    }

    @GetMapping("/write")
    public String goWritePage(Model model) {

        model.addAttribute("guestbook",  new GuestBookDto());
        return "write";
    }

    @PostMapping("/write")
    public String saveBoard(HttpServletRequest request,
                            @Validated @ModelAttribute("guestbook") GuestBookDto guestBookDto,
                            RedirectAttributes redirectAttributes,
                            BindingResult bindingResult, Model model) {

        log.info("bindingResult = {}", bindingResult);
        if (bindingResult.hasErrors()) {

            // 리다이렉트가 아니다 조심
            return "write";

        }
        String curIp = request.getRemoteAddr();
        guestBookDto.setUserIp(curIp);

        GuestBookDto resultDto = guestbookService.saveGuestBook(guestBookDto);

        redirectAttributes.addAttribute("boardNum", resultDto.getBoardNum());

        return "redirect:/read/{boardNum}";

    }

    @GetMapping("/all")
    public String goListPage(Model model, @RequestParam(defaultValue = "") String errMsg) {

        List<GuestBookDto> guestBookDtoList = guestbookService.findAll();

        model.addAttribute("guestbookList", guestBookDtoList);

        if (StringUtils.hasText(errMsg)) {
            model.addAttribute("errMsg", errMsg);
        }

        return "list";
    }

    @GetMapping("/delete/{boardNum}")
    public String delete(@PathVariable Integer boardNum,
                         @RequestParam String password, RedirectAttributes redirectAttributes) {

        String errMsg = "";
        try {
            guestbookService.delete(boardNum, password);
        } catch (EntityNotFoundException e) {   // 만약 catch 여러개 해야되면 구체적인거 -> 포괄적인거 순으로
            log.error(e.getMessage());
            errMsg = e.getMessage();
        } catch (RuntimeException e) {
            errMsg = e.getMessage();
        }

        if (StringUtils.hasText(errMsg)) {
            // 그냥 addAttribute 는 해당 값이 쿼리스트링으로 추가 됨
            // addFlashAttribute 는 리트리브 후 값이 휘발됨 (url 에 표기 X )
            redirectAttributes.addFlashAttribute("errMsg", errMsg);
        }

        return  "redirect:/all";
    }

    @GetMapping("/update/{boardNum}")
    public String goToUpdate(@PathVariable Integer boardNum, Model model) {

        GuestBookDto guestBookDto = guestbookService.findGuestBookByBoardNum(boardNum);
        model.addAttribute("guestbook", guestBookDto);

        return "update";

    }

    @PostMapping("/update/{boardNum}")
    public String updateBoard(@PathVariable Integer boardNum,
                              @Validated @ModelAttribute("guestbook") GuestBookDto guestBookDto,
                              BindingResult bindingResult,
                              RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            return "update";
        }

        try {
            guestbookService.updateBoard(guestBookDto);
        } catch (EntityNotFoundException e) {
            log.error(e.getMessage());
        }

        redirectAttributes.addFlashAttribute("boardNum", boardNum);
        return "redirect:/read/{boardNum}";
    }

    @GetMapping("/recommend/{boardNum}")
    public String recommendBoard(HttpServletRequest request,
                                 @PathVariable Integer boardNum) {

        log.info("boardNum = {}" , boardNum);
        log.info("requestIP = {}", request.getRemoteAddr());

        HttpSession httpSession = request.getSession();
        // | 가 구분자
        String sessionKey = request.getRemoteAddr() + "|" + Integer.toString(boardNum);

        if (ObjectUtils.isEmpty(httpSession.getAttribute(sessionKey))) {
            try {
                guestbookService.recommend(boardNum);
                httpSession.setAttribute(sessionKey, "1");
            } catch (EntityNotFoundException e) {
                log.info("error ! : {}", e.getMessage());
            }
        }

        return  "redirect:/all";
    }

    @GetMapping("/report/{boardNum}")
    public String reportBoard(@PathVariable Integer boardNum) {
        try {
            guestbookService.reportBoard(boardNum);
        } catch (EntityNotFoundException e) {
            log.error("error!! : {}", e.getMessage());
        }
        return  "redirect:/all";
    }
}
