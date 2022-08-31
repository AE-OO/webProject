package com.example._tproject.controller;

import com.example._tproject.model.Member;
import com.example._tproject.repository.CBoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class LoginController {

    @Autowired
    private CBoardRepository cBoardRepository;

    //로그인
    @RequestMapping("/login")
    public String loginForm() {
        return "loginForm";
    }

    @RequestMapping("/confirmLogin")
    public String login(HttpServletRequest request, Model model) {
        String writeId = request.getParameter("mid");
        String writePw = request.getParameter("mpw");
        //HttpSession session = request.getSession(false);  //false로 주는 이유는 로그인 안하고 그냥 홈페이지에 접속만 한 사람한테 세션을 주지 않기위해서

        if (cBoardRepository.loginCheck(writeId).getMPw().equals(writePw)) {
            Member member = cBoardRepository.loginCheck(writeId);
            HttpSession session = request.getSession();
            session.setAttribute("ID", writeId);
            model.addAttribute("member", member);
            return "/home";
        }
        return "/errorNconfirm/loginFail";
    }

    //회원가입
    @RequestMapping("/join")
    public String joinForm() {
        return "joinForm";
    }

    @RequestMapping("/confirmJoin")
    public String join(HttpServletRequest request) {
        String writeId = request.getParameter("mid");
        String writePw = request.getParameter("mpw");
        if(writePw.equals(request.getParameter("checkMpw"))) {
            try {
                cBoardRepository.joinMember(writeId, writePw, request.getParameter("mName"), request.getParameter("mPhoneNum"), request.getParameter("mAddress"));
                return "/errorNconfirm/joinAlert";
            }catch(Exception e){
                return "/errorNconfirm/sameIdError";
            }
        }
        return "/errorNconfirm/joinFail";
    }

    @RequestMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        session.invalidate();
        return "redirect:/";
    }
}
