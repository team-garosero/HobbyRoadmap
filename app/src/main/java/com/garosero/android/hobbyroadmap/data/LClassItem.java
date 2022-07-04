package com.garosero.android.hobbyroadmap.data;

import java.util.HashMap;

// 대분류
public class LClassItem {
    public LClassItem(String code, String name) {
        this.code = code;
        this.name = name;
        this.mClassMap = new HashMap<>();
    }

    @Override
    public String toString() {
        return "LClass{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", mClassMap=" + mClassMap +
                '}';
    }

    String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HashMap<String, MClassItem> getmClassMap() {
        return mClassMap;
    }

    public void setmClassMap(HashMap<String, MClassItem> mClassMap) {
        this.mClassMap = mClassMap;
    }

    String name;
    HashMap<String, MClassItem> mClassMap;
}