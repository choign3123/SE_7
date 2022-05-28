package com.example.demo.src.clothes;

import com.example.demo.src.clothes.model.GetClthsRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ClthRepository {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void test(){
        System.out.println("test in clthDao");
    }

    public List<GetClthsRes> selectClths(int userIdx) {
        String selectClthIdxImg ="select clthIdx,clthImgUrl from clothes where userIdx = ?";
        int clthUserIdx= userIdx;
        List<GetClthsRes> list = this.jdbcTemplate.query(selectClthIdxImg,
                (rs,rowNum) -> {
            GetClthsRes getClthsRes = new GetClthsRes();
            getClthsRes.setClthIdx((rs.getInt("clthIdx")));
            getClthsRes.setClthImgUrl(rs.getString("clthImgUrl"));
            return getClthsRes;
        }
        ,clthUserIdx);

        return list;
    }

}