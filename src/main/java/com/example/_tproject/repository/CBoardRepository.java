package com.example._tproject.repository;

import com.example._tproject.model.CBoard;
import com.example._tproject.model.Member;

import java.util.List;

public interface CBoardRepository {
    public List<CBoard> showList();
    public CBoard showContents(long cId);
    public int insertBoard(String title, String content, String mid, String name);
//    public int updateBoard(long cId, String pw, String title, String content, String name);
    public int updateBoard(long cId, String title, String content, String name);
    public int deleteBoard(long cId);
    public String confirmUser(long cId);
    public Member loginCheck(String mId);
    public int joinMember(String mid, String mpw, String mName, String mPhoneNum, String mAddress);
}
