package com.example.demo.src.user;


import com.example.demo.config.BaseException;
import com.example.demo.src.user.model.PostLoginReq;
import com.example.demo.src.user.model.PostLoginRes;
import com.example.demo.src.user.model.GetUserInfoRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.example.demo.config.BaseResponseStatus.*;

//Provider : Read의 비즈니스 로직 처리
@Service
public class UserProvider {
    private final UserRepository userRepository;

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public UserProvider(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

//    public int test(){
//        int n = userRepository.test();
//        return n;
////        System.out.println("in user provider");
//    }

    //로그인
    public PostLoginRes retrieveUser(PostLoginReq postLoginReq) throws BaseException{
        if(!checkIdAndPw(postLoginReq)) //아이디와, 그 아이디에 해당하는 비번이 없을경우
            throw new BaseException(CHECK_ID_PW);
        try
        {
            return userRepository.selectUser(postLoginReq);
        }catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    //회원정보조회
    public GetUserInfoRes retrieveUserInfo(int userIdx) throws BaseException{
        if(!checkUserExist(userIdx)){ //유저 idx가 존재하지 않음
            throw new BaseException(POST_USERS_INVALID);
        }

        try {
            return userRepository.selectUserInfo(userIdx);
        }catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    //로그인시 아이디 비번 체크 함수
    public boolean checkIdAndPw(PostLoginReq postLoginReq){
        if(userRepository.checkIdAndPw(postLoginReq)==0) {
            return false; //회원가입한 아이디와 비번이 없음.
        }
        else {
            return true;
        }
    }

    //아이디 중복확인
    public boolean checkIdOverlap(String id) {
        if(userRepository.checkIdOverlap(id) == 0){
            return false; //id 중복 X
        }
        else{
            return true; //id 중복
        }
    }

    //존재하는 유저(userIdx)인지 확인
    public boolean checkUserExist(int userIdx) {
        if(userRepository.checkUserExist(userIdx) == 1){
            return true; //존재하는 유저임
        }
        else{
            return false; //존재하지 않는 유저
        }
    }
}
