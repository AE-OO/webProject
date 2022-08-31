package com.example._tproject.controller;

import com.example._tproject.repository.CBoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;


@Controller
public class CBoardController {

    @Autowired
    private CBoardRepository cBoardRepository;

    //상담목록
    @RequestMapping("/consult")
    public String consult(Model model) {
        model.addAttribute("cBoard", cBoardRepository.showList());
        return "consult";
    }

    //상담내용보기
    @RequestMapping("/consultContent")
    public String consultContent(long cId, Model model) {
        model.addAttribute("cBoard", cBoardRepository.showContents(cId));
        return "consultContent";
    }

    //상담등록
    @RequestMapping("/consultReg")
    public String consultReg() {
        return "consultReg";
    }

    @RequestMapping(value = "/consultRegConfirm", method = RequestMethod.POST)
    public String consultRegConfirm(HttpServletRequest request) {
        //cBoardRepository.insertBoard("title", "content", "id", "name", "pw");
        cBoardRepository.insertBoard(request.getParameter("title"), request.getParameter("content"), request.getParameter("id"), request.getParameter("name"), request.getParameter("pw"));
        return "redirect:/consult";
    }

    //수정하기
    @RequestMapping("/confirmUPassword")
    public String confirmUPassword(HttpServletRequest request, Model model) {
        model.addAttribute("cId", Integer.parseInt(request.getParameter("cId")));
        return "confirmUPassword";
    }

    @RequestMapping("/consultUpdate")
    public String consultUpdate(long cId, HttpServletRequest request, Model model) {
        System.out.println(cId);
        if (cBoardRepository.confirmPassword(cId).equals(request.getParameter("cpw"))) {
            model.addAttribute("cBoard", cBoardRepository.showContents(cId));
            return "consultUpdate";
        }
        return "redirect:/fail";
    }

    @RequestMapping("/consultUpdateConfirm")
    public String consultUpdateConfirm(HttpServletRequest request) {
        long cid = Integer.parseInt(request.getParameter("cid"));
        cBoardRepository.updateBoard(cid, request.getParameter("title"), request.getParameter("content"), request.getParameter("name"), request.getParameter("pw"));
        return "redirect:/consult";
    }

    //삭제하기
    @RequestMapping("/confirmDPassword")
    public String confrimDPassword(HttpServletRequest request, Model model) {
        model.addAttribute("cId", Integer.parseInt(request.getParameter("cId")));
        return "confirmDPassword";
    }

    @RequestMapping("/consultDelete")
    public String consultDelete(HttpServletRequest request) {
        long cid = Integer.parseInt(request.getParameter("cId"));

        if (cBoardRepository.confirmPassword(cid).equals(request.getParameter("cpw"))) {
            cBoardRepository.deleteBoard(cid);
            return "redirect:/consult";
        }

        return "redirect:/fail";  //삭제 실패 팝업 뜨고 넘어가게 수정
    }

    //게시글 비밃번호 불일치시
    @RequestMapping("/fail")
    public String failAlert() {
        return "/errorNconfirm/failAlert";
    }
}
