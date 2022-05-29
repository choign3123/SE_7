package com.example.demo.src.clothes;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.config.BaseResponseStatus;
import com.example.demo.src.clothes.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static com.example.demo.config.BaseResponseStatus.NULL_PHOTO_FAIL;
import static com.example.demo.config.BaseResponseStatus.POST_USERS_EMPTY;

@RestController
@RequestMapping("/clths")
public class ClthController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final ClthProvider clthProvider;
    private final ClthService clthService;

    @Autowired
    public ClthController(ClthProvider clthProvider, ClthService clthService) {
        this.clthProvider = clthProvider;
        this.clthService = clthService;
    }

//    @ResponseBody
//    @GetMapping("/{userIdx}")
//    public BaseResponse<String> test(@PathVariable("userIdx") int userIdx) {
//        clthProvider.test();
//        clthService.test();
//        String str = "test success at " + userIdx;
//        return new BaseResponse<>(str);
//    }

    //전체 옷 조회
    @ResponseBody
    @GetMapping("/info/all/{userIdx}")
    public BaseResponse<List<GetClthsRes>> getClths(@PathVariable("userIdx") int userIdx) {
        try {
            List<GetClthsRes> getClthsRes = clthProvider.retrieveClths(userIdx);
            return new BaseResponse<>(getClthsRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    //개별 옷 조회
    @ResponseBody
    @GetMapping("/info/{userIdx}")
    public BaseResponse<GetClthInfoRes> getClthInfo(@PathVariable("userIdx") int userIdx, @RequestParam("clthIdx") int clthIdx) {
        try {
            GetClthInfoRes getClthInfoRes = clthProvider.retrieveClthInfo(userIdx, clthIdx);
            return new BaseResponse<>(getClthInfoRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    //즐겨찾기 된 옷 조회
    @ResponseBody
    @GetMapping("/bookmark/{userIdx}")
    public BaseResponse<List<GetClthBMRes>> getClthBookmark(@PathVariable("userIdx") int userIdx) {
        try {
            List<GetClthBMRes> getClthBMRes = clthProvider.retrieveClthBookmark(userIdx);
            return new BaseResponse<>(getClthBMRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    //옷 등록
    @ResponseBody
    @PostMapping("")
    public BaseResponse<String> createClth(@RequestBody PostClthReq postClthReq) {
        if (postClthReq.getClthImgUrl() == null)
            return new BaseResponse<>(NULL_PHOTO_FAIL);
        if (postClthReq.getSeason() == null || postClthReq.getCategory() == null)
            return new BaseResponse<>(POST_USERS_EMPTY);

        try {
            String result = "옷 등록에 성공히였습니다.";
            clthService.createClth(postClthReq);
            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }
    //옷 삭제
    @ResponseBody
    @DeleteMapping("/{userIdx}")
    public BaseResponse<String> deleteClth(@PathVariable("userIdx") int userIdx,@RequestParam("clthIdx") int clthIdx){
        try{
            String result = "옷이 삭제되었습니다.";
            clthService.deleteClth(userIdx,clthIdx);
            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }

    }

    //옷 수정
    @ResponseBody
    @GetMapping("/{userIdx}")
    public BaseResponse<String> modifyClthInfo( PatchClthReq patchClthReq,@PathVariable int userIdx){
        if (patchClthReq.getSeason() == null || patchClthReq.getCategory() == null)
            return new BaseResponse<>(POST_USERS_EMPTY);
        try{
            String result = "옷이 수정되었습니다.";
            clthService.modifyClthInfo(userIdx,patchClthReq);
            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }

    }


}
