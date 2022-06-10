package se7.closet.src.clothes.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetClthInfoRes {
    private String clthImgUrl;
    private boolean bookmark;
    private String category;
    private String season;
}
