package se7.closet.src.clothes.clthInfo;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class Category {
    //category List를 바꾸려고하면     "error": "Internal Server Error" 가 나옴.
    private static final List<String> category = Collections.unmodifiableList(Arrays.asList("상의", "하의", "아우터", "원피스/세트", "기타", "티셔츠", "니트",
            "셔츠", "후드", "맨투맨", "스커트", "팬츠", "코트", "패딩", "집업", "가디건", "자켓"));

    public static boolean checkCategory(String c){
        if(category.contains(c)){
            return true;
        }
        else{
            return false;
        }
    }

    public static String getString(){
        String str = category.get(0);
        for(int i=1; i<category.size(); i++){
            str += "|";
            str += category.get(i);
        }

        return str;
    }
}