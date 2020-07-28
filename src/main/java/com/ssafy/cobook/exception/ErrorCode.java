package com.ssafy.cobook.exception;

public enum ErrorCode {

    UNEXPECTED(0, "서버 내부 에러."),
    UNEXPECTED_USER(1000, "존재하지 않는 회원입니다."),
    EXIST_USER_EMAIL(1100, "이미 가입된 이메일입니다."),
    WRONG_EMAIL_CHECK_AUTH_CODE(1200, "인증코드가 틀립니다"),
    UNEXPECTED_CLUB(2000, "존재하지 않는 클럽입니다."),
    EXIST_CLUB_NAME(2100, "중복된 클럽 이름입니다."),
    UNEXPECTED_POST(3000, "존재하지 않는 게시물입니다."),
    INVALID_GENRE(5000, "존재하지 않는 장르입니다"),
    UNEXPECTED_BOOK(6000, "존재하지 않는 책 정보입니다"),
    UNEXPECTED_READING(7000, "존재하지 않는 이벤트 정보입니다"),
    ILLEGAL_ACCESS_READING(7100, "잘못된 접근입니다"),
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