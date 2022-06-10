package se7.closet.src.user;


import se7.closet.config.BaseException;
import se7.closet.src.user.model.PostSignUpReq;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static se7.closet.config.BaseResponseStatus.*;

// Service Create, Update, Delete 의 로직 처리
@Service
public class UserService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final UserRepository userRepository;
    private final UserProvider userProvider;

    @Autowired
    public UserService(UserRepository userRepository, UserProvider userProvider) {
        this.userRepository = userRepository;
        this.userProvider = userProvider;

    }

    //회원가입
    //회원가입시 provider->repository 아이디 중복확인 후 repository가서 아이디 저장 후 반환
    public void createUser(PostSignUpReq postSignUpReq) throws BaseException
    {
        if (userProvider.checkIdOverlap(postSignUpReq.getId())) { //아이디 중복됨.
            throw new BaseException(POST_USERS_EXISTS_ID);
        }

        try{
            userRepository.insertUser(postSignUpReq);
        }catch(Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }

    }
}
