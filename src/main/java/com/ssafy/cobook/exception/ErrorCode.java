package com.ssafy.cobook.exception;

public enum ErrorCode {
    UNEXPECTED(0, "서버 내부 에러입니다."),
    MEMBER_DUPLICATED_EMAIL(1000, "중복된 이메일로 가입을 요청했습니다."),
    UNSIGNED(1100, "등록되지 않은 이메일입니다."),
    WRONG_PASSWORD(1200, "잘못된 비밀번호입니다."),;

    private final Integer code;
    private final String message;

    ErrorCode(final Integer code, final String message){
        this.code = code;
        this.message = message;
    }

    public Integer getCode(){
        return code;
    }

    public String getMessage(){
        return message;
    }
}
