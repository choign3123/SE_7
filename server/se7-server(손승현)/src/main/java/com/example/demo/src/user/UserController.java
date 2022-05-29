package com.example.demo.src.user;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.user.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static com.example.demo.config.BaseResponseStatus.*;
import static com.example.demo.utils.ValidationRegex.isRegexPw;
import static com.example.demo.utils.ValidationRegex.isRegexId;

@RestController
@RequestMapping("/users")
public class UserController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final UserProvider userProvider;
    @Autowired
    private final UserService userService;


    public UserController(UserProvider userProvider, UserService userService) {
        this.userProvider = userProvider;
        this.userService = userService;
    }

//    @ResponseBody
//    @GetMapping("")
//    public BaseResponse<String> test() {
//        int userIdx = 1;
//        int temp = userProvider.test();
//        String str = "test success at " + userIdx + "and num of " + temp;
//        return new BaseResponse<>(str);
//    }

    //회원가입
    @ResponseBody
    @PostMapping("/signUp")
    public BaseResponse<String> createUser(@RequestBody PostSignUpReq postSignUpReq){
        //빈칸 있나 확인
        if(postSignUpReq.getId()==null || postSignUpReq.getPwForCheck() ==null || postSignUpReq.getName() == null
                || postSignUpReq.getPassword() == null) {
            return new BaseResponse<>(POST_USERS_EMPTY);//status수정
        }
        //아이디 비번 형식 확인
        if(!isRegexId(postSignUpReq.getId())){
            return new BaseResponse<>(POST_USERS_INVALID_ID);
        }
        if(!isRegexId(postSignUpReq.getPassword())){
            return new BaseResponse<>(POST_USERS_INVALID_PW);
        }
        //비밀번호 비밀번호 확인
        if (!postSignUpReq.getPassword().contentEquals(postSignUpReq.getPwForCheck())) {
            return new BaseResponse<>(POST_USERS_DIFF_PW_PWCHECK);
        }

        try{
            String result = "회원가입에 성공하였습니다.";
            userService.createUser(postSignUpReq);
            return new BaseResponse<>(result);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }

    }
    //로그인
    @ResponseBody
    @PostMapping("/login")
    public BaseResponse<GetLoginRes> loginUser(@RequestBody GetLoginReq getLoginReq){

        try
        {
            GetLoginRes getLoginRes= userProvider.retrieveUser(getLoginReq);
            return new BaseResponse<>(getLoginRes);
        }catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }

    }
    //회원정보 조회
    @ResponseBody
    @GetMapping("/{userIdx}")
    public BaseResponse<GetUserInfoRes> getUserInfo(@PathVariable("userIdx") int userIdx) {
        try {
            GetUserInfoRes getUserInfoRes = userProvider.retrieveUserInfo(userIdx);
            return new BaseResponse<>(getUserInfoRes);

        }catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }





}

