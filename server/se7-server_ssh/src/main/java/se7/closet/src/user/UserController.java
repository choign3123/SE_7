package se7.closet.src.user;

import se7.closet.config.BaseException;
import se7.closet.config.BaseResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import se7.closet.src.user.model.*;

import static se7.closet.config.BaseResponseStatus.*;
import static se7.closet.utils.ValidationRegex.isRegexId;

@RestController
@RequestMapping("/users")
public class UserController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final UserProvider userProvider;
    private final UserService userService;

    @Autowired
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
    //[post] users/signUp
    @ResponseBody
    @PostMapping("/signup")
    public BaseResponse<String> createUser(@RequestBody PostSignUpReq postSignUpReq)
    {
        //빈칸 있나 확인
        if(postSignUpReq.getId()==null || postSignUpReq.getPwForCheck() ==null || postSignUpReq.getName() == null
                || postSignUpReq.getPassword() == null) {
            return new BaseResponse<>(POST_USERS_EMPTY);
        }
        //아이디 형식 확인
        if(!isRegexId(postSignUpReq.getId())){
            return new BaseResponse<>(POST_USERS_INVALID_ID);
        }
        //비번 형식 확인
        if(!isRegexId(postSignUpReq.getPassword())){
            return new BaseResponse<>(POST_USERS_INVALID_PW);
        }
        //비밀번호와 비밀번호확인용이 같은지 확인
        if (!postSignUpReq.getPassword().contentEquals(postSignUpReq.getPwForCheck())) {
            return new BaseResponse<>(POST_USERS_DIFF_PW_PWCHECK);
        }

        try{
            userService.createUser(postSignUpReq);

            String result = "회원가입에 성공하였습니다.";
            return new BaseResponse<>(result);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }

    }

    //로그인
    //[post] /users/login
    @ResponseBody
    @PostMapping("/login")
    public BaseResponse<PostLoginRes> loginUser(@RequestBody PostLoginReq postLoginReq)
    {
        //아이디 또는 비민번호가 빈값일경우
        if(postLoginReq.getId()==null || postLoginReq.getPassword()==null){
            return new BaseResponse<>(POST_USERS_EMPTY_LOGIN);
        }

        try {
            PostLoginRes postLoginRes = userProvider.retrieveUser(postLoginReq);
            return new BaseResponse<>(postLoginRes);
        }catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }

    }

    //회원정보 조회
    //[get] /users/{userIdx}
    @ResponseBody
    @GetMapping("/{userIdx}")
    public BaseResponse<GetUserInfoRes> getUserInfo(@PathVariable("userIdx") int userIdx)
    {
        try {
            GetUserInfoRes getUserInfoRes = userProvider.retrieveUserInfo(userIdx);
            return new BaseResponse<>(getUserInfoRes);
        }catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }
}

