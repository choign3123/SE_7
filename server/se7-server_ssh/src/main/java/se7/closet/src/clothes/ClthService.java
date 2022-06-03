package se7.closet.src.clothes;

import se7.closet.config.BaseException;
import se7.closet.src.clothes.model.PatchClthReq;
import se7.closet.src.clothes.model.PostClthReq;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static se7.closet.config.BaseResponseStatus.*;

@Service
public class ClthService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final ClthRepository clthRepository;
    private final ClthProvider clthProvider;


    @Autowired
    public ClthService(ClthRepository clthRepository, ClthProvider clthProvider) {
        this.clthRepository = clthRepository;
        this.clthProvider = clthProvider;

    }

    public void test(){
        clthRepository.test();;
        System.out.println("in user provider");
    }

    //옷 등록
    public void createClth(int userIdx, PostClthReq postClthReq) throws BaseException{
        if(!clthProvider.checkUserExist(userIdx)){ //존재하지 않는 유저이면
            throw new BaseException(POST_USERS_INVALID);
        }

        try{
            clthRepository.insertClth(userIdx, postClthReq);
        }catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }

    }

    //옷 삭제
    public void deleteClth(int userIdx, int clthIdx) throws BaseException {
        if(!clthProvider.checkClthExist(userIdx,clthIdx)){  //옷이 존재하지 않으면
            throw new BaseException(DELETE_FAIL_POST); //수정 필요
        }

        try{
            clthRepository.deleteClth(userIdx,clthIdx);
        }catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    //옷 수정
    public void modifyClthInfo(int userIdx,int clthIdx, PatchClthReq patchClthReq) throws BaseException{
        if(!clthProvider.checkClthExist(userIdx,clthIdx)){ //옷이 존재하지 않으면
            throw new BaseException(MODIFY_FAIL_POST); //수정 필요
        }

        try{
            clthRepository.updateClthInfo(clthIdx,patchClthReq); //int값 받는거 삭제함.
        }catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
