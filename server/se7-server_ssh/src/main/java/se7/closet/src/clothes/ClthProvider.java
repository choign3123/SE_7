package se7.closet.src.clothes;

import se7.closet.config.BaseException;
import se7.closet.src.clothes.model.GetClthBMRes;
import se7.closet.src.clothes.model.GetClthInfoRes;
import se7.closet.src.clothes.model.GetClthsRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static se7.closet.config.BaseResponseStatus.*;

//Provider : Read의 비즈니스 로직 처리
@Service
public class ClthProvider {

    private final ClthRepository clthRepository;

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public ClthProvider(ClthRepository clthRepository) {
        this.clthRepository = clthRepository;
    }

//    public void test() {
//        System.out.println("in user provider");
//    }


    //전체 옷 조회
    public List<GetClthsRes> retrieveClths(int userIdx) throws BaseException {
        if(!checkUserExist(userIdx)){ //존재하지 않는 유저면
            throw new BaseException(POST_USERS_INVALID);
        }

        try {
            return clthRepository.selectClths(userIdx);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    //개별 옷 정보 조회
    public GetClthInfoRes retrieveClthInfo(int userIdx, int clthIdx) throws BaseException {
        if (!checkClthExist(userIdx,clthIdx)){ //옷이 없다면
            throw new BaseException(FAILED_TO_FIND_CLOTHES);
        }

        try {
            return clthRepository.selectClthInfo(clthIdx);
        }catch(Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    //즐겨찾기 된 옷 조회
    public List<GetClthBMRes> retrieveClthBookmark(int userIdx) throws BaseException{
        if(!checkUserExist(userIdx)){ //존재하지 않는 유저면
            throw new BaseException(POST_USERS_INVALID);
        }

        try {
            return clthRepository.selectClthBookmark(userIdx);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    //옷 검색
    public List<GetClthsRes> retrieveClthsBySearch(int userIdx, String query) throws BaseException{
        if(!checkUserExist(userIdx)){ //존재하지 않는 유저면
            throw new BaseException(POST_USERS_INVALID);
        }

        try{
            return clthRepository.selectClthsBySearch(userIdx, query);
        } catch (Exception e){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    //존재하는 옷인지 확인
    public boolean checkClthExist(int userIdx,int clthIdx) {
        if (clthRepository.checkClthExist(userIdx,clthIdx) == 1){
            return true; //존재하는 옷임
        }
        else{
            return false;
        }
    }

    //존재하는 유저인지 확인
    public boolean checkUserExist(int userIdx){
        if(clthRepository.checkUserExist(userIdx) == 1){
            return true; //존재하는 유저임
        }
        else{
            return false; //존재하지 않는 유저
        }
    }
}
