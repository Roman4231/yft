package com.yft.admin.myapplication.classes;

import android.content.Context;
import android.content.SharedPreferences;

import com.yft.admin.myapplication.classes.converters.CompletedExercisesConverter;
import com.yft.admin.myapplication.classes.converters.CustomConverter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Admin on 26.04.2017.
 */

public class CompletedTrainings {
    public int year;
    public int month;
    public int day;
    public long totalTime;
    public String name;
    public int level;
    public ArrayList<CompletedExercises> completedExercises;

    public CompletedTrainings(CompletedTrainings temp){
        this.year=temp.year;
        this.month=temp.month;
        this.day=temp.day;
        this.totalTime=temp.totalTime;
        this.name=temp.name;
        this.completedExercises=new ArrayList<>();
        this.completedExercises=temp.completedExercises;
    }

    public CompletedTrainings(long totalTime,String name, ArrayList<CompletedExercises> exercises){
        this.totalTime=totalTime;
        this.name=name;
        this.completedExercises=new ArrayList<>();
        this.completedExercises=exercises;
    }

    public CompletedTrainings(String name,long totalTime){
        this.totalTime=totalTime;
        this.name=name;
        this.completedExercises=new ArrayList<>();
    }

    public void setDate(int year,int month,int day){
        this.year=year;
        this.month=month;
        this.day=day;
    }

    public void addExercise(CompletedExercises exercise){
        completedExercises.add(exercise);
    }

    public void addExercises(ArrayList<CompletedExercises> exercises){
        for (CompletedExercises temp:exercises) {
            completedExercises.add(temp);
        }
    }

    //////save methods
    public static CompletedTrainings getCompletedTrainingFromJson(String json){
        Gson gson = new Gson();
        return (getCompletedTrainingFromJsonSub(gson.fromJson(json, String[].class)));
    }

    private static CompletedTrainings getCompletedTrainingFromJsonSub(String[] json){
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(ExerciseClass.class, new CustomConverter());
        Gson gson = builder.create();

        CompletedTrainings result=new CompletedTrainings(json[json.length-5],
                Long.parseLong(json[json.length-4]));

        for(int i=0;i<json.length-6;i++){
            result.addExercise(gson.fromJson(json[i],CompletedExercises.class));
        }
        result.level=Integer.parseInt(json[json.length-6]);
        result.setDate(Integer.parseInt(json[json.length - 3]),
                Integer.parseInt(json[json.length-2]),
                Integer.parseInt(json[json.length-1]));
        return result;
    }

    public static String getStringFromCompletedTraining(CompletedTrainings temp){
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(CompletedExercises.class, new CompletedExercisesConverter());
        Gson gson = builder.create();
        String[] result=new String[temp.completedExercises.size()+6];
        for(int i=0;i<result.length-6;i++){
            result[i]=gson.toJson(temp.completedExercises.get(i));
        }
        result[result.length-6]=String.valueOf(temp.level);
        result[result.length-5]=temp.name;
        result[result.length-4]=String.valueOf(temp.totalTime);
        result[result.length-3]=String.valueOf(temp.year);
        result[result.length-2]=String.valueOf(temp.month);
        result[result.length-1]=String.valueOf(temp.day);
        return getStringFromCompletedTrainingSub(result);
    }

    private static String getStringFromCompletedTrainingSub(String[] temp){
        Gson gson = new Gson();
        return (gson.toJson(temp));
    }

    public static String getStringFromCompletedTrainingArray(ArrayList<CompletedTrainings> array){
        String[] result=new String[array.size()];
        for(int i=0;i<array.size();i++){
            result[i]=getStringFromCompletedTraining(array.get(i));
        }
        Gson gson = new Gson();
        return (gson.toJson(result));
    }

    public static ArrayList<CompletedTrainings> getCompletedTrainingArrayFromJson(String json){
        ArrayList<CompletedTrainings> result=new ArrayList<>();
        Gson gson = new Gson();
        for (String temp:gson.fromJson(json,String[].class)) {
            result.add(getCompletedTrainingFromJson(temp));
        }
        return result;
    }

    ////////////////save CompletedTraining
    /*
        SharedPreferences sPref = getActivity().getSharedPreferences("MyDateBase", MODE_PRIVATE);
        int n= sPref.getInt("NumberOfCompletedTrainings",0);
        for(int i=0;i<n;i++){
            completedTrainings.add(CompletedTrainings.getCompletedTrainingFromJson(sPref.getString("CompletedTraining"+i,"")));
        }
     */
    public static void saveCompletedTraining(CompletedTrainings completedTraining, Context context){
        SharedPreferences sPref = context.getSharedPreferences("MyDateBase", MODE_PRIVATE);
        SharedPreferences.Editor editor=sPref.edit();
        int n= sPref.getInt("NumberOfCompletedTrainings",0);
        String str=CompletedTrainings.getStringFromCompletedTraining(completedTraining);
        editor.putString("CompletedTraining"+n,str);
        editor.putInt("NumberOfCompletedTrainings",n+1);
        editor.commit();
    }
}
