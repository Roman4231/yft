package com.yft.admin.myapplication.classes;

/**
 * Created by Admin on 24.04.2017.
 */

public class SelectedExercises{
    public String exerciseName;
    public int reps;
    public SelectedExercises(String name,int reps){
        exerciseName=name;
        this.reps=reps;
    }

    public SelectedExercises(){
    }

    public SelectedExercises(SelectedExercises temp){
        this.exerciseName=temp.exerciseName;
        this.reps=temp.reps;
    }
}
