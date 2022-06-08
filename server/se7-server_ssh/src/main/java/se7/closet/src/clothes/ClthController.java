package se7.closet.src.clothes;

import se7.closet.config.BaseException;
import se7.closet.config.BaseResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import se7.closet.src.clothes.model.*;
import java.util.*;
import static se7.closet.config.BaseResponseStatus.*;

@RestController
@RequestMapping("/clths")
public class ClthController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final ClthProvider clthProvider;
    private final ClthService clthService;

    //category or season List를 바꾸려고하면     "error": "Internal Server Error" 가 나옴.
    private static final List<String> category = Collections.unmodifiableList(Arrays.asList("상의", "하의", "아우터", "원피스/세트", "기타", "티셔츠", "니트",
                    "셔츠", "후드", "맨투맨", "스커트", "팬츠", "코트", "패딩", "집업", "가디건", "자켓"));
    private static final List<String> season = Collections.unmodifiableList(Arrays.asList("봄", "여름", "가을", "겨울"));

    @Autowired
    public ClthController(ClthProvider clthProvider, ClthService clthService) {
        this.clthProvider = clthProvider;
        this.clthService = clthService;
    }

//    @ResponseBody
//    @GetMapping("/{userIdx}")
//    public BaseResponse<String> test(@PathVariable("userIdx") int userIdx) {
////        clthProvider.test();
//        clthService.test();
//        String str = "test success at " + userIdx;
//        return new BaseResponse<>(str);
//    }


    //전체 옷 조회
    //[get] /clths/info/all/{userIdx}
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

    //개별 옷 정보 조회
    //[get] /clths/info/{userIdx}?clthIdx=""
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
    //[get] /clths/bookmark/{userIdx}
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
    //[post] /clths
    @ResponseBody
    @PostMapping("{userIdx}")
    public BaseResponse<String> createClth(@PathVariable("userIdx") int userIdx, @RequestBody PostClthReq postClthReq) {
        //사진이 없으면
        if (postClthReq.getClthImgUrl() == null){
            return new BaseResponse<>(POST_CLTH_EMPTY_IMG);
        }
        //계절이나 카테고리가 없으면
        if (postClthReq.getSeason() == null || postClthReq.getCategory() == null){
            return new BaseResponse<>(POST_CLTH_EMPTY);
        }
        //카테고리에 포함되지 않는 문자열이 들어오면
        if (!category.contains(postClthReq.getCategory())){
            return new BaseResponse<>(POST_CLTH_INVALID_CATEGORY);
        }
        //계절에 포함되지 않는 문자열이 들어오면
        if (!season.contains(postClthReq.getSeason())){
            return new BaseResponse<>(POST_CLTH_INVALID_SEASON);
        }

        try {
            clthService.createClth(userIdx, postClthReq);

            String result = "옷 등록에 성공히였습니다.";
            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    //옷 삭제
    //[delete] /clths/{userIdx}?clthIdx=
    @ResponseBody
    @DeleteMapping("/{userIdx}")
    public BaseResponse<String> deleteClth(@PathVariable("userIdx") int userIdx,@RequestParam("clthIdx") int clthIdx){
        try{
            clthService.deleteClth(userIdx,clthIdx);

            String result = "옷이 삭제되었습니다.";
            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }

    }

    //옷 수정
    //[pathc] /clths/{userIdx}?clthIdx=""
    @ResponseBody
    @PatchMapping("/{userIdx}")
    public BaseResponse<String> modifyClthInfo(@PathVariable("userIdx") int userIdx,@RequestParam("clthIdx") int clthIdx,
     @RequestBody PatchClthReq patchClthReq){
        //계절이나 카테고리가 비어있다면
        if (patchClthReq.getSeason() == null || patchClthReq.getCategory() == null){
            return new BaseResponse<>(POST_CLTH_EMPTY);
        }
        //카테고리에 포함되지 않는 문자열이 들어오면
        if (!category.contains(patchClthReq.getCategory())){
            return new BaseResponse<>(POST_CLTH_INVALID_CATEGORY);
        }
        //계절에 포함되지 않는 문자열이 들어오면
        if (!season.contains(patchClthReq.getSeason())){
            return new BaseResponse<>(POST_CLTH_INVALID_SEASON);
        }

        try{
            clthService.modifyClthInfo(userIdx,clthIdx,patchClthReq);

            String result = "옷이 수정되었습니다.";
            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    //옷 검색
    //[get] /clths/search/{userIdx}?query=
    @ResponseBody
    @GetMapping("/search/{userIdx}")
    public BaseResponse<List<GetClthsRes>> searchClths(@PathVariable("userIdx") int userIdx, @RequestParam("query") String query){
        // query string으로 들오온 문자열에서 공백 -> | (후에 sql 쿼리문에서 or 검색을 위한 것임)
        query = query.replaceAll(" ", "|");
        //System.out.println(query);

        try{
            List<GetClthsRes> getClthsRes = clthProvider.retrieveClthsBySearch(userIdx, query);
            return new BaseResponse<>(getClthsRes);
        } catch (BaseException e){
            return new BaseResponse<>(e.getStatus());
        }
    }
}
