package se7.closet.src.clothes.clthInfo;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Season {
    //season List를 바꾸려고하면     "error": "Internal Server Error" 가 나옴.
    private static final List<String> season = Collections.unmodifiableList(Arrays.asList("봄", "여름", "가을", "겨울"));

    public static boolean checkSeason(String s){
        if(season.contains(s)){
            return true;
        }
        else{
            return false;
        }
    }

    public static String getString(){
        String str = season.get(0);
        for(int i=1; i<season.size(); i++){
            str += "|";
            str += season.get(i);
        }

        return str;
    }
}
