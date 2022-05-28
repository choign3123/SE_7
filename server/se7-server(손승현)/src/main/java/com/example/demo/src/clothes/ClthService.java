package com.example.demo.src.clothes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClthService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final ClthDao clthDao;
    private final ClthProvider clthProvider;


    @Autowired
    public ClthService(ClthDao clthDao, ClthProvider clthProvider) {
        this.clthDao = clthDao;
        this.clthProvider = clthProvider;

    }

    public void test(){
        System.out.println("in user provider");
    }
}
