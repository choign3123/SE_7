package com.example.demo.src.clothes.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostClthReq {

    private int userIdx;
    private String clthImgUrl;
    private boolean bookmark;
    private String category;
    private String season;
}