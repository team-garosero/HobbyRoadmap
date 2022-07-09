package com.garosero.android.hobbyroadmap.data;

import java.util.HashMap;

// 중분류
public class MClassItem {
    public MClassItem(String name) {
        this.name = name;
        this.sClassMap = new HashMap<>();
    }

    @Override
    public String toString() {
        return "MClass{" +
                "name='" + name + '\'' +
                ", sClassMap=" + sClassMap +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HashMap<String, SClassItem> getsClassMap() {
        return sClassMap;
    }

    public void setsClassMap(HashMap<String, SClassItem> sClassMap) {
        this.sClassMap = sClassMap;
    }

    String name;
    HashMap<String, SClassItem> sClassMap;
}