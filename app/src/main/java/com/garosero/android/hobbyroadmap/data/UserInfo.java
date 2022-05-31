package com.garosero.android.hobbyroadmap.data;

public class UserInfo {
    public UserInfo(){}
    public UserInfo(String name, String nickname, int life, int xp){
        this.name = name;
        this.life = life;
        this.nickname = nickname;
        this.xp = xp;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "name='" + name + '\'' +
                ", nickname='" + nickname + '\'' +
                ", life=" + life +
                ", xp=" + xp +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public int getXp() {
        return xp;
    }

    public void setXp(int xp) {
        this.xp = xp;
    }

    private String name;
    private String nickname;
    private int life;
    private int xp;
}
