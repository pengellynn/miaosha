package com.ripon.miaoshaserver.domain;

public class BaseUser {
    private Long id;
    private String nickname;

    public BaseUser() {
    }

    public BaseUser(Long id, String nickname) {
        this.id = id;
        this.nickname = nickname;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
