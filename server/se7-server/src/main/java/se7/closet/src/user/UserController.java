package se7.closet.src.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import se7.closet.config.BaseResponse;

@RestController
@RequestMapping("/users")
public class UserController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final UserProvider userProvider;
    @Autowired
    private final UserService userService;


    public UserController(UserProvider userProvider, UserService userService){
        this.userProvider = userProvider;
        this.userService = userService;
    }

    @ResponseBody
    @GetMapping("/{userIdx}")
    public BaseResponse<String> test(@PathVariable("userIdx")int userIdx){
        int temp = userProvider.test();
        String str = "test success at " + userIdx + " and num of " + temp;
        return new BaseResponse<>(str);
    }
}

