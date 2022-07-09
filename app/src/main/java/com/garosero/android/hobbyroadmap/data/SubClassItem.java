package com.garosero.android.hobbyroadmap.data;


import java.util.HashMap;

// 세분류
public class SubClassItem{
    public SubClassItem(String name) {
        this.name = name;
        this.moduleClassMap = new HashMap<>();
    }

    @Override
    public String toString() {
        return "SubClass{" +
                "name='" + name + '\'' +
                ", moduleClassMap=" + moduleClassMap +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HashMap<String, ModuleClassItem> getModuleClassMap() {
        return moduleClassMap;
    }

    public void setModuleClassMap(HashMap<String, ModuleClassItem> moduleClassMap) {
        this.moduleClassMap = moduleClassMap;
    }

    String name;
    HashMap<String, ModuleClassItem> moduleClassMap;
}
