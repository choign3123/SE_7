package se7.closet.src.clothes.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetClthsRes {
    private int clthIdx;
    private String clthImgUrl;
}
