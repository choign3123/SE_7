package com.example.demo.src.clothes;

import com.example.demo.src.clothes.model.GetClthInfoRes;
import com.example.demo.src.clothes.model.GetClthsRes;
import com.example.demo.src.user.model.GetUserInfoRes;
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

    //전체 옷 조회
    public List<GetClthsRes> selectClths(int userIdx) {
        String selectClthIdxImg ="select clthIdx,clthImgUrl from clothes where userIdx = ?";
        int clthUserIdx= userIdx;
        return this.jdbcTemplate.query(selectClthIdxImg,
                (rs,rowNum) -> {
            GetClthsRes getClthsRes = new GetClthsRes();
            getClthsRes.setClthIdx((rs.getInt("clthIdx")));
            getClthsRes.setClthImgUrl(rs.getString("clthImgUrl"));
            return getClthsRes;
        }
        ,clthUserIdx);
    }

    //개별옷 조회 옷 확인
    public int checkClthExist(int userIdx,int clthIdx) {
//        String checkClthExist = "select exists(select userIdx from clothes where userIdx=? and clthIdx=?)";
//        int clothIdx = clthIdx;
        String checkClthExist = "select exists(select userIdx from clothes where userIdx=? and clthIdx=?)";
        Object[] clothIdx = new Object[]{userIdx,clthIdx};
        return this.jdbcTemplate.queryForObject(checkClthExist,Integer.class,clothIdx);
    }
    //개별 옷 조회
   public GetClthInfoRes selectClthInfo(int clthIdx) {
        String selectClthInfo = "select clthImgUrl, bookmark, category, season from clothes where clthIdx = ?";
        int clothIdx = clthIdx;
       return this.jdbcTemplate.queryForObject(selectClthInfo,
               (rs, rowNum) -> new GetClthInfoRes(
//                       rs.getInt("clthIdx"),
                       rs.getString("clthImgUrl"),
                       rs.getBoolean("bookmark"),
                       rs.getString("category"),
                       rs.getString("season")
               ),
               clothIdx);
    }


}