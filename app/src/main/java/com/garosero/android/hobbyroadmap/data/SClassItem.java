package com.garosero.android.hobbyroadmap.data;


import java.util.HashMap;

// 소분류
public class SClassItem {
    public SClassItem(String name) {
        this.name = name;
        this.subClassMap = new HashMap<>();
    }

    @Override
    public String toString() {
        return "SClass{" +
                "name='" + name + '\'' +
                ", subClassMap=" + subClassMap +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HashMap<String, SubClassItem> getSubClassMap() {
        return subClassMap;
    }

    public void setSubClassMap(HashMap<String, SubClassItem> subClassMap) {
        this.subClassMap = subClassMap;
    }

    String name;
    HashMap<String, SubClassItem> subClassMap;
}
