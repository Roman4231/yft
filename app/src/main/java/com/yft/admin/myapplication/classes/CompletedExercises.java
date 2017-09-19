package com.yft.admin.myapplication.classes;

/**
 * Created by Admin on 26.04.2017.
 */

public class CompletedExercises{
    public String name;
    public int reps;

    public CompletedExercises(String name,int reps){
        this.name=name;
        this.reps=reps;
    }
    @Override
    public  String toString(){
        return name+" Reps: "+reps;
    }
}
