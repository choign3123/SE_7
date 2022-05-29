package com.example.demo.config;

import lombok.Getter;

/**
 * 에러 코드 관리
 */
@Getter
public enum BaseResponseStatus {
    /**
     * 1000 : 요청 성공
     */
    SUCCESS(true, 1000, "요청에 성공하였습니다."),


    /**
     * 2000 : Request 오류
     */
    // Common
    REQUEST_ERROR(false, 2000, "입력값을 확인해주세요."),
    EMPTY_JWT(false, 2001, "JWT를 입력해주세요."),
    INVALID_JWT(false, 2002, "유효하지 않은 JWT입니다."),
    INVALID_USER_JWT(false,2003,"권한이 없는 유저의 접근입니다."),

    // users
    USERS_EMPTY_USER_ID(false, 2010, "유저 아이디 값을 확인해주세요."),

    // [POST] /users
    POST_USERS_EMPTY_EMAIL(false, 2015, "이메일을 입력해주세요."),
    POST_USERS_INVALID_EMAIL(false, 2016, "이메일 형식을 확인해주세요."),
    POST_USERS_EXISTS_EMAIL(false,2017,"중복된 이메일입니다."),

    POST_POST_INVALID_CONTENTS(false,2018,"내용의 글자수를 확인해주세요."),
    POST_POST_EMPTY_IMGURL(false,2019,"게시글의 이미지를 입력해주세요."),

    CHECK_ID_PW(false, 2020, "아이디 비밀번호가 맞지 않습니다."),
//    POST_USERS_EMPTY_ID(false, 2021, "아이디를 입력해주세요."),
//    POST_USERS_EMPTY_NAME(false, 2022, "이름을 입력해주세요."),
//    POST_USERS_EMPTY_PW(false, 2023, "비밀번호를 입력해주세요."),
//    POST_USERS_EMPTY_PWCHECK(false, 2024, "비밀번호 확인을 입력해주세요."),

    POST_USERS_EMPTY(false, 2021, "빈칸을 확인해주세요."),
    POST_USERS_EXISTS_ID(false,2025,"중복된 아이디입니다."),
    POST_USERS_INVALID_ID(false, 2026, "아이디 형식을 확인해주세요."),
    POST_USERS_INVALID_PW(false, 2027, "비밀번호 형식을 확인해주세요."),
    POST_USERS_DIFF_PW_PWCHECK(false,2030,"비밀번호와 비밀번호 확인이 다릅니다."),
    NULL_PHOTO_FAIL(false,2031,"사진을 등록하지 않았습니다."),

    /**
     * 3000 : Response 오류
     */
    // Common
    RESPONSE_ERROR(false, 3000, "값을 불러오는데 실패하였습니다."),

    // [POST] /users
    DUPLICATED_EMAIL(false, 3013, "중복된 이메일입니다."),
    FAILED_TO_LOGIN(false,3014,"없는 아이디거나 비밀번호가 틀렸습니다."),

    MODIFY_FAIL_POST(false, 3015, "게시물 수정을 실패했습니다."),
    DELETE_FAIL_POST(false, 3016, "게시물 삭제를 실패했습니다."),
    FAILED_TO_FIND_CLOTH(false,3017,"옷을 불러오는데 실패하였습니다."),

    /**
     * 4000 : Database, Server 오류
     */
    DATABASE_ERROR(false, 4000, "데이터베이스 연결에 실패하였습니다."),
    SERVER_ERROR(false, 4001, "서버와의 연결에 실패하였습니다.");


    private final boolean isSuccess;
    private final int code;
    private final String message;

    private BaseResponseStatus(boolean isSuccess, int code, String message) {
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }
}
