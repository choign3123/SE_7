package com.example.demo.src.clothes.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PatchClthReq {
    private int clthIdx;
    private boolean bookmark;
    private String category;
    private String season;
}
