package com.example.demo.src.clothes;

import com.example.demo.config.BaseException;
import com.example.demo.src.clothes.model.GetClthsRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;

//Provider : Read의 비즈니스 로직 처리
@Service
public class ClthProvider {

    private final ClthRepository clthRepository;

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public ClthProvider(ClthRepository clthRepository) {
        this.clthRepository = clthRepository;
    }

    public void test(){
        System.out.println("in user provider");
    }


    public List<GetClthsRes> retrieveClths(int userIdx) throws BaseException{

        try{
            return clthRepository.selectClths(userIdx);
        }catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }

    }


}