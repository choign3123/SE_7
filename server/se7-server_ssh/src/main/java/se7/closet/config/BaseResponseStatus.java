package se7.closet.config;

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
    EMPTY_ERROR(false, 2021, "빈 항목을 확인해주세요."),

    // users
    USERS_EMPTY_USER_ID(false, 2010, "유저 아이디 값을 확인해주세요."),
    POST_USERS_INVALID_LOGIN(false, 2020, "일치하는 아이디 또는 비밀번호가 없습니다."),
    POST_USERS_EXISTS_ID(false,2025,"중복된 아이디입니다."),
    POST_USERS_INVALID_ID(false, 2026, "아이디 형식을 확인해주세요."),
    POST_USERS_INVALID_PW(false, 2027, "비밀번호 형식을 확인해주세요."),
    POST_USERS_DIFF_PW_PWCHECK(false,2030,"비밀번호가 일치하지 않습니다."),
    POST_USERS_EMPTY_LOGIN(false,2031,"아이디 또는 비밀번호를 입력해주세요"),
    POST_USERS_INVALID(false,2032,"존재하지 않는 유저입니다."),

    //clths
    POST_CLTH_EMPTY_IMG(false,2033,"사진을 등록하지 않았습니다."),
    POST_CLTH_INVALID_CATEGORY(false,2034,"잘못된 카테고리입니다."),
    POST_CLTH_INVALID_SEASON(false,2034,"잘못된 계절입니다."),
    POST_CLTH_EMPTY(false,2036,"비어있는 카테고리나 계절이 있습니다"),


    /**
     * 3000 : Response 오류
     */
    // Common
    RESPONSE_ERROR(false, 3000, "정보를 불러오는데 실패하였습니다."),

    // users
    FAILED_TO_LOGIN(false,3014,"없는 아이디거나 비밀번호가 틀렸습니다."),

    // clths
    MODIFY_FAIL_POST(false, 3015, "옷 수정을 실패했습니다."),
    DELETE_FAIL_POST(false, 3016, "옷 삭제를 실패했습니다."),
    FAILED_TO_FIND_CLOTHES(false,3017,"옷을 불러오는데 실패하였습니다."),

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
