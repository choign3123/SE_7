package com.example.demo.src.clothes;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.clothes.model.GetClthInfoRes;
import com.example.demo.src.clothes.model.GetClthsRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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

    //전체 옷 조회
    @ResponseBody
    @GetMapping("/info/all/{userIdx}")
    public BaseResponse<List<GetClthsRes>> getClths(@PathVariable("userIdx") int userIdx){
        try{
            List<GetClthsRes> getClthsRes= clthProvider.retrieveClths(userIdx);
            return new BaseResponse<>(getClthsRes);
        }catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }
    //개별 옷 조회
    @ResponseBody
    @GetMapping("/info/{userIdx}")
    public BaseResponse<GetClthInfoRes> getClthInfo(@PathVariable("userIdx") int userIdx,@RequestParam("clthIdx") int clthIdx)
    {
        try{
            GetClthInfoRes getClthInfoRes = clthProvider.retrieveClthInfo(userIdx,clthIdx);
            return new BaseResponse<>(getClthInfoRes);
        }catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }
    //즐겨찾기 된 옷 조회


}
