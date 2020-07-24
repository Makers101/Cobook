package com.ssafy.cobook.exception;

public enum ErrorCode {
<<<<<<< src/main/java/com/ssafy/cobook/exception/ErrorCode.java

    UNEXPECTED(0, "서버 내부 에러."),
    UNEXPECTED_USER(1000, "존재하지 않는 회원입니다."),
    EXIST_USER_EMAIL(1100, "이미 가입된 이메일입니다."),
    UNEXPECTED_CLUB(2000, "존재하지 않는 클럽입니다."),
    UNEXPECTED_POST(3000, "존재하지 않는 게시물입니다."),
    MEMBER_DUPLICATED_EMAIL(1100, "이미 가입된 이메일입니다."),
    UNSIGNED(1000, "존재하지 않는 회원입니다."),
    WRONG_PASSWORD(1200, "잘못된 비밀번호입니다.");
    private final Integer code;
    private final String message;

    ErrorCode(final Integer code, final String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage(){
        return message;
    }
}
