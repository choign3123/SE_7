package com.example.demo.src.user;


import com.example.demo.src.user.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class UserDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public int test(){
        String meg = "select count(*) from user";
        return this.jdbcTemplate.queryForObject(meg, int.class);
    }

    public GetUserInfoRes selectUserInfoRes(int userIdx){
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

    public int insertUser(PostSignUpReq postSignUpReq){
        String insertUserQuery = "insert into user(id, name, password) values (?, ?, ?)";
        Object[] createUserParams = new Object[]{postSignUpReq.getId(), postSignUpReq.getName(),postSignUpReq.getPassword()};
        this.jdbcTemplate.update(insertUserQuery, createUserParams);

        String lastInserIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInserIdQuery,int.class);
    }


    public int checkIdOverlap(String id){
        String checkIdOverlapQuery = "select exists(select id from user where id = ?)";
        String checkUserExistParams = id;
        return this.jdbcTemplate.queryForObject(checkIdOverlapQuery, int.class, checkUserExistParams);
    }

//
//    public int checkIdAndPw(String id, String pw){
//        String checkIdAndPw = "select exists(select id, password from user where id = ? and password = ?)";
//        Object[] checkInfo =new Object[]{id,pw};
//        this.jdbcTemplate.query(checkIdAndPw,checkInfo);
//
//
//    }



}






