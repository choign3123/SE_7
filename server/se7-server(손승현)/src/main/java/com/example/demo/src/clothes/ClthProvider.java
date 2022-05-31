package com.example.demo.src.clothes;

import com.example.demo.config.BaseException;
import com.example.demo.src.clothes.model.GetClthBMRes;
import com.example.demo.src.clothes.model.GetClthInfoRes;
import com.example.demo.src.clothes.model.GetClthsRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.prefs.BackingStoreException;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;
import static com.example.demo.config.BaseResponseStatus.FAILED_TO_FIND_CLOTH;

//Provider : Read의 비즈니스 로직 처리
@Service
public class ClthProvider {

    private final ClthRepository clthRepository;

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public ClthProvider(ClthRepository clthRepository) {
        this.clthRepository = clthRepository;
    }

    public void test() {
        System.out.println("in user provider");
    }


    //전체 옷 조회
    public List<GetClthsRes> retrieveClths(int userIdx) throws BaseException {

        try {
            return clthRepository.selectClths(userIdx);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }

    }

    //개별 옷 조회
    public GetClthInfoRes retrieveClthInfo(int userIdx,int clthIdx) throws BaseException {
        if (!checkClthExist(userIdx,clthIdx))
            throw new BaseException(FAILED_TO_FIND_CLOTH);
        try {
            return clthRepository.selectClthInfo(clthIdx);
        }catch(Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }
    //개별 옷 조회 옷 확인
    public boolean checkClthExist(int userIdx,int clthIdx) {
        if (clthRepository.checkClthExist(userIdx,clthIdx) == 1)
            return true;
        else
            return false;
    }
    //즐겨찾기 된 옷 조회
    public List<GetClthBMRes> retrieveClthBookmark(int userIdx) throws BaseException{
        try {
            return clthRepository.selectClthBookmark(userIdx);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
