package com.example.demo.src.user;


import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponseStatus;
import com.example.demo.src.user.model.GetLoginRes;
import com.example.demo.src.user.model.GetUserInfoRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.example.demo.config.BaseResponseStatus.CHECK_ID_PW;
import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;

//Provider : Read의 비즈니스 로직 처리
@Service
public class UserProvider {

    private final UserDao userDao;

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public UserProvider(UserDao userDao) {
        this.userDao = userDao;
    }

    public int test(){
        int n = userDao.test();
        return n;
//        System.out.println("in user provider");
    }

    //로그인 회원조회
//    public GetLoginRes retrieveUser(String id,String pw){
//
//        if(!checkIdAndPw(id,pw))
//            throw new BaseException(CHECK_ID_PW);
//
//    }
//
//    public int checkIdAndPw(String id,String pw){
//        return userDao.checkIdAndPw(id,pw);
//    }

    //회원정보조회 회원조회
    public GetUserInfoRes retrieveUserInfo(int userIdx) throws BaseException{
        try
        {
            return userDao.selectUserInfoRes(userIdx);
        }catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public boolean checkUserExist(int userIdx) {
        return false;
    }
    //아이디 중복확인
    public boolean checkIdOverlap(String id) {
        int overLap = userDao.checkIdOverlap(id);
        if(overLap == 0)
            return false;
        else
            return true;
    }


}
