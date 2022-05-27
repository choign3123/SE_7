package com.example.demo.src.user;


import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.user.model.PostSignUpReq;
import com.example.demo.utils.SHA256;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.example.demo.config.BaseResponseStatus.*;

// Service Create, Update, Delete 의 로직 처리
@Service
public class UserService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final UserDao userDao;
    private final UserProvider userProvider;




    public UserService(UserDao userDao, UserProvider userProvider) {
        this.userDao = userDao;
        this.userProvider = userProvider;

    }

    public void createUser(PostSignUpReq postSignUpReq) throws BaseException {
        //아이디 데베 가서 중복확인
        if (userProvider.checkIdOverlap(postSignUpReq.getId())) {
            throw new BaseException(POST_USERS_EXISTS_ID);
        }
        try
        {
            int userIdx = userDao.insertUser(postSignUpReq);
        }catch(Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }

    }
}
