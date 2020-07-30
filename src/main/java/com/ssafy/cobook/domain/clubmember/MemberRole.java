package com.ssafy.cobook.domain.clubmember;

public enum MemberRole {

    LEADER("leader"), MEMBER("member"), WAITING("candidate");

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

    public boolean onWait() {
        return this.equals(WAITING);
    }
}
