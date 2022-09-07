package com.example._tproject.controller;

import com.example._tproject.model.Member;
import com.example._tproject.repository.CBoardRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/yoopilates")
@Log4j2
public class LoginController {

    @Autowired
    private CBoardRepository cBoardRepository;

    //로그인
    @RequestMapping("/login")
    public void loginForm() {
        log.info("Login Page...");
    }

    @RequestMapping("/confirmLogin")
    public String login(@RequestParam String mid, @RequestParam String mpw, HttpServletRequest request, Model model) {
        //HttpSession session = request.getSession(false);  //false로 주는 이유는 로그인 안하고 그냥 홈페이지에 접속만 한 사람한테 세션을 주지 않기위해서

        if (cBoardRepository.loginCheck(mid).getMPw().equals(mpw)) {
            Member member = cBoardRepository.loginCheck(mid);
            HttpSession session = request.getSession();
            session.setAttribute("ID", mid);
            model.addAttribute("member", member);
            return "/yoopilates/home";
        }
        return "/errorNconfirm/loginFail";
    }

    //회원가입
    @RequestMapping("/join")
    public void joinForm() {
        log.info("Join Page...");
    }

    @RequestMapping("/confirmJoin")
    public String join(@RequestParam String mid, @RequestParam String mpw, @RequestParam String checkMpw, @RequestParam String mName, @RequestParam String mPhoneNum, @RequestParam String mAddress) {
        if(mpw.equals(checkMpw)) {
            try {
                cBoardRepository.joinMember(mid, mpw, mName, mPhoneNum, mAddress);
                return "/errorNconfirm/joinAlert";
            }catch(Exception e){
                return "/errorNconfirm/sameIdError";
            }
        }
        return "/errorNconfirm/joinFail";
        
        //이전 코드에서 equals(null)을 == null으로 바꿔보기
    }

    @RequestMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        session.invalidate();
        return "redirect:/yoopilates/";
    }
}
