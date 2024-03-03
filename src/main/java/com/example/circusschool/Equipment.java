package com.example.circusschool;

public class Equipment {
    String EName;
    String EType;
    int ECount;
    static String SEName;
    static String SEType;
    static int SECount;


    public Equipment( String EName, int ECount, String EType){
        this.EName =EName;
        this.EType = EType;
        this.ECount =ECount;
    }
    public String getEName(){
        return EName;
    }
    public String getEType(){
        return EType;
    }
    public int getECount(){
        return ECount;
    }
}
