package com.example._tproject.repository;

import com.example._tproject.model.CBoard;
import com.example._tproject.model.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class CBoardRepositoryImpl implements CBoardRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<CBoard> showList() {
//        String sql = "select * from cboard";
        String sql = "select @rownum:= @rownum+1 as rownum, a.* from (select cid, ctitle, cdate, mid, tname from cboard order by cid desc) a where (@rownum:= 0)=0";
        List<CBoard> list = jdbcTemplate.query(sql,new BeanPropertyRowMapper<CBoard>(CBoard.class));
        return list;
    }

    @Override
    public CBoard showContents(long cId) {
        String sql = "select * from cboard where cid = " + cId;
        CBoard cboard = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<CBoard>(CBoard.class));
        return cboard;
    }

    @Override
    public int insertBoard(String title, String content, String id, String name) {
        String sql = "insert into cboard(ctitle, ccontent, mid, tname) values(?,?,?,?)";
        return jdbcTemplate.update(sql, title, content, id, name);
    }

    @Override
    public int updateBoard(long cId, String title, String content, String name) {
        String sql = "update cboard set ctitle = ?, ccontent = ?, tname = ? where cid = ?";
        return jdbcTemplate.update(sql, title, content, name, cId);
    }

    @Override
    public int deleteBoard(long cId) {
        String sql = "delete from cboard where cid = " + cId;
        return jdbcTemplate.update(sql);
    }

    @Override
    public String confirmUser(long cId) {
//        String sql = "select cpw from cboard where cid = " + cId;
        String sql = "select mid from cboard where cid = " + cId;
        return jdbcTemplate.queryForObject(sql, String.class);
    }

    @Override
    public Member loginCheck(String mId) {
        String sql = "select * from member where mid = '" + mId + "'";
        Member member = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<Member>(Member.class));
        return member;
    }

    @Override
    public int joinMember(String mid, String mpw, String mName, String mPhoneNum, String mAddress) {
        String sql = "insert into member values(?, ?, ?, ?, ?)";
        return jdbcTemplate.update(sql, mid, mpw, mName, mPhoneNum, mAddress);
    }
}
