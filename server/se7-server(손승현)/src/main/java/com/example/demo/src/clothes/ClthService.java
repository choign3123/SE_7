package com.example.demo.src.clothes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
