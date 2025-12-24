package net.datasa.web5.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.datasa.web5.dto.BoardDto;
import net.datasa.web5.dto.ReplyDto;
import net.datasa.web5.security.AuthenticatedUser;
import net.datasa.web5.service.BoardService;
import net.datasa.web5.service.ReplyService;
import org.hibernate.action.internal.EntityActionVetoException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;


@Slf4j
@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;
    private final ReplyService replyService;

    // org.springframework.beans.factory.annotation 이 경로의 @Value 를 써야한다.
    @Value("${board.uploadPath}")
    String uploadPath;
    @Value("${board.pageSize}")
    int pageSize;

    @GetMapping("/list")
    public String list(Model model,
                       @RequestParam(defaultValue = "1") int pageNum,
                       @RequestParam(defaultValue = "") String searchType,
                       @RequestParam(defaultValue = "") String searchWord) {

        Page<BoardDto> boardDtoList = boardService.getList(pageNum, pageSize, searchType, searchWord);

        model.addAttribute("boardList", boardDtoList);
        model.addAttribute("pageNum", pageNum);
        model.addAttribute("searchType", searchType);
        model.addAttribute("searchWord", searchWord);

        return "/board/list";
    }

    @GetMapping("/addForm")
    public String addForm(Model model ){

        model.addAttribute("board", new BoardDto());

        return "board/boardAddForm";
    }

    @PostMapping("/addForm")
    public String saveBoard(@Validated @ModelAttribute("board") BoardDto boardDto,
                            BindingResult bindingResult,
                            @AuthenticationPrincipal AuthenticatedUser user,
                            MultipartFile uploadFile) {

        if  (user == null) {
            return "redirect:/";
        }

        if (bindingResult.hasErrors()) {
            return "board/boardAddForm";
        }

        boardDto.setUserId(user.getUsername());
        log.info("uploadFile = {}" , uploadFile);
        // 첨부파일 존재하는 경우
        if (uploadFile != null) {
            log.info("Empty : {}", uploadFile.isEmpty());
            log.info("파라미터 이름 : {}", uploadFile.getName());
            log.info("파일명 : {}", uploadFile.getOriginalFilename());
            log.info("파일크기 : {}", uploadFile.getSize());
            log.info("파일종류 : {}", uploadFile.getContentType());
            log.info("저장할 경로 : {}", uploadPath);
        }

        boolean isSuccess = true;
        try {
            boardService.saveBoard(boardDto, uploadPath, uploadFile);
        } catch (EntityActionVetoException e) {

            isSuccess = false;
        } catch (Exception e) {
            isSuccess = false;
        }

        if (isSuccess) {
            return "redirect:/board/list";
        } else {
            return "board/boardAddForm";
        }
    }

    @GetMapping("/read/{boardNum}")
    public String read(Model model,
                       @PathVariable Integer boardNum) {

        log.info("조회할 글번호 : {}", boardNum);

        try {
            BoardDto boardDTO = boardService.getBoard(boardNum);
            List<ReplyDto> replyList = replyService.findAllByBoardNum(boardNum);
            model.addAttribute("board", boardDTO);
            model.addAttribute("reply", new ReplyDto());
            model.addAttribute("replyList", replyList);
            return "board/boardDetail";
        } catch (Exception e) {
            return "redirect:/board/list";
        }
    }

    @GetMapping("/download/{boardNum}")
    public void download(@PathVariable Integer boardNum,
                         HttpServletResponse response) {

        boardService.download(boardNum, response, uploadPath);

    }

    @PostMapping("/addReply")
    public String addReply(@Validated @ModelAttribute("reply") ReplyDto replyDto,
                           RedirectAttributes redirectAttributes) {

        log.info("replyDto = {}", replyDto);

        try {
            replyService.saveReply(replyDto);
        } catch (Exception e) {
            redirectAttributes.addAttribute("err", e.getMessage());
        }

        redirectAttributes.addAttribute("boardNum", replyDto.getBoardNum());
        return "redirect:/board/read/{boardNum}";
    }

    @PostMapping("/deleteReply")
    public String deleteReply(@RequestParam Integer boardNum,
                              @RequestParam Integer replyNum,
                              RedirectAttributes redirectAttributes) {

        try {
            replyService.deleteReply(replyNum);
        } catch (Exception e){
            redirectAttributes.addAttribute("err", e.getMessage());
        }

        redirectAttributes.addAttribute("boardNum", boardNum);
        return "redirect:/board/read/{boardNum}";
    }
}
