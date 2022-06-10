package se7.closet.src.clothes;


import se7.closet.src.clothes.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class ClthRepository {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void test(){
//        System.out.println("test in clthDao");

        //얘는 에러 안뜸.
        /*String strQuery = "delete from user where name='ssh'";
        int a = this.jdbcTemplate.update(strQuery);
        System.out.println("in clth repository " + a);
        a = this.jdbcTemplate.update(strQuery);
        System.out.println("in clth repository for error " + a);

        //이건 리턴값이 없어서 에러
        strQuery = "select clthIdx from clothes where userIdx = 50";
        this.jdbcTemplate.queryForObject(strQuery, int.class);*/


    }

    //전체 옷 조회
    public List<GetClthsRes> selectClths(int userIdx) {
        String selectClthIdxImg ="select clthIdx,clthImgUrl from clothes where userIdx = ? order by createdAt desc";
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

    //개별 옷 정보 조회
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

    //즐겨찾기 된 옷 조회
    public List<GetClthBMRes> selectClthBookmark(int userIdx) {
        String selectClthBookmark ="select clthIdx, clthImgUrl from clothes where bookmark=true and userIdx = ? order by createdAt desc";
        int clthBookmarkIdx= userIdx;
        return this.jdbcTemplate.query(selectClthBookmark,(rs,rownum)-> {
                    GetClthBMRes getClthBMRes = new GetClthBMRes();
                    getClthBMRes.setClthIdx(rs.getInt("clthIdx"));
                    getClthBMRes.setClthImgUrl(rs.getString("clthImgUrl"));
                    return getClthBMRes;
                },clthBookmarkIdx);
    }

    //옷 등록
    public int insertClth(int userIdx, PostClthReq postClthReq) {
        String insertClth = "insert into clothes(userIdx, clthImgUrl, bookmark, category, season) values (?,?,?,?,?)";
        Object[] createClth = new Object[]{userIdx,postClthReq.getClthImgUrl(),postClthReq.isBookmark(),
        postClthReq.getCategory(),postClthReq.getSeason()};
        this.jdbcTemplate.update(insertClth,createClth);

        String lastInsertClthIdx = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInsertClthIdx,int.class);
    }

    //옷 삭제
    public int deleteClth(int userIdx, int clthIdx) {
        String deleteClth = "delete from clothes where userIdx = ? and clthIdx =?";
        Object[] clothIdx = new Object[]{userIdx,clthIdx};
        return this.jdbcTemplate.update(deleteClth,clothIdx);

    }

    //옷 수정
    public int updateClthInfo(int clthIdx, PatchClthReq patchClthReq) {
        String updateClthInfo="update clothes set bookmark = ?, category=?, season=? where clthIdx=?";
        Object[] updateInfo = new Object[]{patchClthReq.isBookmark(),patchClthReq.getCategory(),
                                            patchClthReq.getSeason(),clthIdx};
        return this.jdbcTemplate.update(updateClthInfo,updateInfo);
    }

    //옷 검색
    public List<GetClthsRes> selectClthsBySearch(int userIdx, String season, String category, boolean bm){
        String searchClthsQuery = "select clthIdx, clthImgUrl from clothes where userIdx = ? and season REGEXP (?) and category regexp (?) and bookmark=?";
        Object[] searchClthsParams = new Object[]{userIdx, season, category, bm};

        return this.jdbcTemplate.query(searchClthsQuery,
                (rs, rowNum) -> new GetClthsRes(
                        rs.getInt("clthIdx"),
                        rs.getString("clthImgUrl"))
                , searchClthsParams);
    }

    //존재하는 유저인지 확인
    public int checkUserExist(int userIdx){
        String checkUserExist = "select exists(select userIdx from user where userIdx = ?)";
        return this.jdbcTemplate.queryForObject(checkUserExist,int.class,userIdx);
    }

    //존재하는 옷인지 확인
    public int checkClthExist(int userIdx,int clthIdx) {
        String checkClthExist = "select exists(select userIdx from clothes where userIdx=? and clthIdx=?)";
        Object[] clothIdx = new Object[]{userIdx,clthIdx};
        return this.jdbcTemplate.queryForObject(checkClthExist,Integer.class,clothIdx);
    }
}