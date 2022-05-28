package com.example.demo.src.clothes.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetClthInfoRes {
//    private int clthIdx;   //clthidx확인 필요
    private String clthImgUrl;
    private boolean bookmark;
    private String category;
    private String season;
}
