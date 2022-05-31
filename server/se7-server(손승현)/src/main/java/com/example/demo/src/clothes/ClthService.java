package com.example.demo.src.clothes;

import com.example.demo.config.BaseException;
import com.example.demo.src.clothes.model.PatchClthReq;
import com.example.demo.src.clothes.model.PostClthReq;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.example.demo.config.BaseResponseStatus.*;

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
        System.out.println("in user provider");
    }

    //옷 등록
    public void createClth(PostClthReq postClthReq) throws BaseException{
        try{
            clthRepository.insertClth(postClthReq);
        }catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }

    }
    //옷 삭제
    public void deleteClth(int userIdx, int clthIdx) throws BaseException {
        if(!clthProvider.checkClthExist(userIdx,clthIdx))
            throw new BaseException(DELETE_FAIL_POST);
        try{
            clthRepository.deleteClth(userIdx,clthIdx);
        }catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
    //옷 수정
    public void modifyClthInfo(int userIdx,int clthIdx, PatchClthReq patchClthReq) throws BaseException{
        if(!clthProvider.checkClthExist(userIdx,clthIdx))
            throw new BaseException(MODIFY_FAIL_POST);
        try{
            int update = clthRepository.updateClthInfo(clthIdx,patchClthReq);
        }catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
