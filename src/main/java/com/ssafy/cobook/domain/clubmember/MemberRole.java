package com.ssafy.cobook.domain.clubmember;

public enum MemberRole {

    LEADER("leader"), MEMBER("member"), WAITING("waiting"), REJECT("reject");

    private String name;

    MemberRole(String name) {
        this.name = name;
    }

    public String getRole() {
        return name.toUpperCase();
    }

    public boolean isLeader(String role) {
        return role.equals(LEADER.getRole());
    }
}
