package com.example.circusschool;

public class Course {
    int Number;
    String Name;
    String Time;
    int Cost;
    int NumOfStudent;
    int MaxNumOfStudent;
    String CourseFor;
    String Type;
    String TrainerName;

    static int SNumber;
    static String SName;
    static String STime;
    static int SCost;
    static int SNumOfStudent;
    static int SMaxNumOfStudent;
    static String SCourseFor;
    static String SType;
    static String STrainerName;
    public Course(int Number,String Name,String Time,int Cost,int NumOfStudent ,int MaxNumOfStudent,String CourseFor,String Type){
    this.Number =Number;
    this.Name = Name;
    this.Time = Time;
    this.Cost = Cost;
    this.NumOfStudent = NumOfStudent;
    this.MaxNumOfStudent =MaxNumOfStudent;
    this.CourseFor = CourseFor;
    this.Type =Type;
    }

    public String getName(){
        return Name;
    }

    public int getNumber() {
        return Number;
    }

    public String getTime() {
        return Time;
    }

    public int getCost() {
        return Cost;
    }

    public int getNumOfStudent() {
        return NumOfStudent;
    }

    public int getMaxNumOfStudent() {
        return MaxNumOfStudent;
    }

    public String getCourseFor() {
        return CourseFor;
    }

    public String getType() {
        return Type;
    }

    public String getTrainerName() {
        return TrainerName;
    }
}
