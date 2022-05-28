package com.example.demo.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidationRegex {

    public static boolean isRegexId(String target){
        String regex1 = "^[a-z0-9]{4,20}$";
        Pattern pattern1 = Pattern.compile(regex1);
        Matcher matcher1 = pattern1.matcher(target);

        String regex2 = "[0-9]+";
        Pattern pattern2 = Pattern.compile(regex2);
        Matcher matcher2 = pattern2.matcher(target);

        String regex3 = "[a-z]+";
        Pattern pattern3 = Pattern.compile((regex3));
        Matcher matcher3 = pattern3.matcher(target);
        return matcher1.find()&&matcher2.find()&& matcher3.find();
    }

    public static boolean isRegexPw(String target){
        String regex1 = "^[a-z0-9]{6,20}$";
        Pattern pattern1 = Pattern.compile(regex1);
        Matcher matcher1 = pattern1.matcher(target);

        String regex2 = "[0-9]+";
        Pattern pattern2 = Pattern.compile(regex2);
        Matcher matcher2 = pattern2.matcher(target);

        String regex3 = "[a-z]+";
        Pattern pattern3 = Pattern.compile((regex3));
        Matcher matcher3 = pattern3.matcher(target);

        return matcher1.find()&&matcher2.find()&& matcher3.find();
    }
}
