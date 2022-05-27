package com.example.demo.src.user.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PostSignUpReq {
    private String id;
    private String name;
    private String password;
    private String pwForCheck;

    private PostSignUpReq(){

    }
}
