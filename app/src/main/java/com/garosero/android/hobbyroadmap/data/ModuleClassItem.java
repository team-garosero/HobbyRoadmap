package com.garosero.android.hobbyroadmap.data;

// 학습모듈
public class ModuleClassItem{
    public ModuleClassItem(String moduleNum, String name, String text) {
        this.moduleNum = moduleNum;
        this.name = name;
        this.text = text;
    }

    public ModuleClassItem(){}

    @Override
    public String toString() {
        return "ModuleClass{" +
                "moduleNum='" + moduleNum + '\'' +
                ", name='" + name + '\'' +
                ", text='" + text + '\'' +
                '}'+"\n";
    }

    public String getModuleNum() {
        return moduleNum;
    }

    public void setModuleNum(String moduleNum) {
        this.moduleNum = moduleNum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    String moduleNum;
    String name;
    String text;
}
