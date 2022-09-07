package com.example._tproject.controller;

import com.example._tproject.repository.CBoardRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import sun.util.resources.ga.LocaleNames_ga;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


@Controller
@RequestMapping("/yoopilates")
@Log4j2
public class CBoardController {

    @Autowired
    private CBoardRepository cBoardRepository;

    //상담목록
    @RequestMapping("/consult")
    public void consult(Model model) {
        log.info("Consult Page...");
        model.addAttribute("cBoard", cBoardRepository.showList());
//        return "consult";
    }

    //상담내용보기
    @RequestMapping("/consultContent/{cId}")
    public String consultContent(@PathVariable long cId, Model model) {
        log.info("Consult Content Page...");
        model.addAttribute("cBoard", cBoardRepository.showContents(cId));
        return "/yoopilates/consultContent";
    }

    //상담등록
    @RequestMapping("/consultReg")
    public void consultReg() {
        log.info("Consult Regist Page...");
//        return "consultReg";
    }

    @RequestMapping(value = "/consultRegConfirm", method = RequestMethod.POST)
    public String consultRegConfirm(@RequestParam String title, @RequestParam String content, @RequestParam String id, @RequestParam String name) {
        //cBoardRepository.insertBoard("title", "content", "id", "name", "pw");
        cBoardRepository.insertBoard(title, content, id, name);
        return "redirect:/yoopilates/consult";
    }

    //수정하기
//    @RequestMapping("/confirmUPassword")  //세션생성 후 필요없어짐
//    public String confirmUPassword(HttpServletRequest request, Model model) {
//        model.addAttribute("cId", Integer.parseInt(request.getParameter("cId")));
//        return "confirmUPassword";
//    }

    @RequestMapping("/consultUpdate/{cId}")
    public String consultUpdate(@PathVariable long cId, HttpServletRequest request, Model model) {
//        if (cBoardRepository.confirmPassword(cId).equals(request.getParameter("cpw"))) {
//            model.addAttribute("cBoard", cBoardRepository.showContents(cId));
//            return "consultUpdate";
//        }
        log.info("Content Update Page...");
        HttpSession session = request.getSession();

        if(cBoardRepository.confirmUser(cId).equals(session.getAttribute("ID"))){
            model.addAttribute("cBoard", cBoardRepository.showContents(cId));
            return "/yoopilates/consultUpdate";
        }
        return "redirect:/yoopilates/fail";
    }


    @RequestMapping("/consultUpdateConfirm")
    public String consultUpdateConfirm(@RequestParam long cId, @RequestParam String title, @RequestParam String content, @RequestParam String name) {
        cBoardRepository.updateBoard(cId, title, content, name);
        return "redirect:/yoopilates/consult";
    }

    //삭제하기
//    @RequestMapping("/confirmDPassword")   //세션 생성 후 필요없어짐
//    public String confrimDPassword(HttpServletRequest request, Model model) {
//        model.addAttribute("cId", Integer.parseInt(request.getParameter("cId")));
//        return "confirmDPassword";
//    }

    @RequestMapping("/consultDelete/{cId}")
    public String consultDelete(@PathVariable long cId,HttpServletRequest request) {
        HttpSession session = request.getSession();

//        if (cBoardRepository.confirmPassword(cid).equals(request.getParameter("cpw"))) {
//            cBoardRepository.deleteBoard(cid);
//            return "redirect:/consult";
//        }
        if(cBoardRepository.confirmUser(cId).equals(session.getAttribute("ID"))){
            cBoardRepository.deleteBoard(cId);
            return "redirect:/yoopilates/consult";
        }
        return "redirect:/yoopilates/fail";  //삭제 실패 팝업 뜨고 넘어가게 수정
    }

    //게시글 비밃번호 불일치시
    //로그인된 아이디와 작성자 아이디가 불일치하면 권한이 없는 글이라고 ALERT 뜨도록
    @RequestMapping("/fail")
    public String failAlert() {
        return "/errorNconfirm/failAlert";
    }
}
