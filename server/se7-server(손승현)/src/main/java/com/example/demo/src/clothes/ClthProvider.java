package com.example.demo.src.clothes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//Provider : Read의 비즈니스 로직 처리
@Service
public class ClthProvider {

    private final ClthDao clthDao;

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public ClthProvider(ClthDao clthDao) {
        this.clthDao = clthDao;
    }

    public void test(){
        System.out.println("in user provider");
    }
}