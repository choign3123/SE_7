package se7.closet.src.clothes;

import se7.closet.config.BaseResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clths")
public class ClthController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final ClthProvider clthProvider;
    private final ClthService clthService;

    @Autowired
    public ClthController(ClthProvider clthProvider, ClthService clthService){
        this.clthProvider = clthProvider;
        this.clthService = clthService;
    }

    @ResponseBody
    @GetMapping("/{userIdx}")
    public BaseResponse<String> test(@PathVariable("userIdx")int userIdx){
        clthProvider.test();
        clthService.test();
        String str = "test success at " + userIdx;
        return new BaseResponse<>(str);
    }
}
