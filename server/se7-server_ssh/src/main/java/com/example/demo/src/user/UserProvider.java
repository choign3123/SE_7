package com.example.demo.src.user;


import com.example.demo.config.BaseException;
import com.example.demo.src.user.model.GetLoginReq;
import com.example.demo.src.user.model.GetLoginRes;
import com.example.demo.src.user.model.GetUserInfoRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.example.demo.config.BaseResponseStatus.*;

//Provider : Read의 비즈니스 로직 처리
@Service
public class UserProvider {
    @Autowired
    private final UserRepository userRepository;

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public UserProvider(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public int test(){
        int n = userRepository.test();
        return n;
//        System.out.println("in user provider");
    }
    //회원가입시 아이디 중복확인
    public boolean checkIdOverlap(String id) {
        int overLap = userRepository.checkIdOverlap(id);
        if(overLap == 0)
            return false;
        else
            return true;
    }
    //로그인시 회원조회
    public GetLoginRes retrieveUser(GetLoginReq getLoginReq) throws BaseException{
        if(!checkIdAndPw(getLoginReq))
            throw new BaseException(CHECK_ID_PW);
        try
        {
            return userRepository.selectUser(getLoginReq);
        }catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }
    //로그인시 아이디 비번 체크 함수
    public boolean checkIdAndPw(GetLoginReq getLoginReq){
        if(userRepository.checkIdAndPw(getLoginReq)==0)
            return false;
        else
            return true;
    }

    //회원정보조회 회원조회
    public GetUserInfoRes retrieveUserInfo(int userIdx) throws BaseException{
        if(!checkUserExist(userIdx))
            throw new BaseException(RESPONSE_ERROR);
        try
        {
            return userRepository.selectUserInfo(userIdx);
        }catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }
    //회원정보조회시 userIdx있는지 확인
    public boolean checkUserExist(int userIdx) {
        if(userRepository.checkUserExist(userIdx) == 1)
            return true;
        else
            return false;

    }
}
