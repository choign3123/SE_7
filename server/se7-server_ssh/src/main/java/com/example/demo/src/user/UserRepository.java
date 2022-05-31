package com.example.demo.src.user;


import com.example.demo.src.user.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class UserRepository {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

//    public int test(){
//        String meg = "select count(*) from user";
//        return this.jdbcTemplate.queryForObject(meg, int.class);
//    }

    //회원가입
    public int insertUser(PostSignUpReq postSignUpReq){
        String insertUserQuery = "insert into user(id, name, password) values (?, ?, ?)";
        Object[] createUserParams = new Object[]{postSignUpReq.getId(), postSignUpReq.getName(),postSignUpReq.getPassword()};
        this.jdbcTemplate.update(insertUserQuery, createUserParams);

        String lastInserIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInserIdQuery,int.class);
    }

    //회원가입 아이디 중복조회
    public int checkIdOverlap(String id){
        String checkIdOverlapQuery = "select exists(select id from user where id = ?)";
        String checkUserExistParams = id;
        return this.jdbcTemplate.queryForObject(checkIdOverlapQuery, int.class, checkUserExistParams);
    }

    //로그인 아이디 비번 확인
    public int checkIdAndPw(PostLoginReq postLoginReq){
        String checkIdAndPw = "select exists(select id, password from user where id = ? and password = ?)";
        Object[] checkInfo =new Object[]{postLoginReq.getId(), postLoginReq.getPassword()};
        return this.jdbcTemplate.queryForObject(checkIdAndPw,Integer.class,checkInfo);

    }
    //로그인시 userIdx반환 함수
    public PostLoginRes selectUser(PostLoginReq postLoginReq){
        String getUserIdx = "select userIdx from user where id=? and password = ?";
        Object[] checkInfo =new Object[]{postLoginReq.getId(), postLoginReq.getPassword()};
        int userIdx = this.jdbcTemplate.queryForObject(getUserIdx,Integer.class,checkInfo);
        return new PostLoginRes(userIdx);

    }

    //회원정보조회
    public GetUserInfoRes selectUserInfo(int userIdx){
        String getUserInfoResQuery = "select id,name, (select count(*) from clothes where userIdx=? group by userIdx) as numOfClth from user where userIdx = ?";
        Object[] getUserInfoParams = new Object[]{userIdx, userIdx};
        return this.jdbcTemplate.queryForObject(getUserInfoResQuery,
                (rs, rowNum) -> new GetUserInfoRes(
                        rs.getString("id"),
                        rs.getString("name"),
                        rs.getInt("numOfClth")
                ),
                getUserInfoParams);
    }

    //존재하는 유저(userIdx)인지 확인
    public int checkUserExist(int userIdx){
        String checkUserExist = "select exists(select userIdx from user where userIdx = ?)";
        return this.jdbcTemplate.queryForObject(checkUserExist,int.class,userIdx);
    }

}






