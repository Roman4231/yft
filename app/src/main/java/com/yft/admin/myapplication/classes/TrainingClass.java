package com.yft.admin.myapplication.classes;

import com.yft.admin.myapplication.classes.converters.CustomConverter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by Winchester on 04.03.2017.
 */



public class TrainingClass {
    public int image;
    public ExerciseClass[] exercises;
    public String name;
    public boolean isx4;

    public TrainingClass(int numb) {
        exercises = new ExerciseClass[numb];
    }

    public TrainingClass(TrainingClass temp) {
        image=temp.image;
        name=temp.name;
        isx4=temp.isx4;
        exercises = new ExerciseClass[temp.exercises.length];
        for(int i = 0; i < exercises.length; i++){
            exercises[i] = new ExerciseClass(temp.exercises[i]);
        }
    }

    public void addExercise(ExerciseClass exercise, int numb){
        exercises[numb] = new ExerciseClass(exercise);
    }

    public String returnNameOfExercise(int i){
        return exercises[i].name;
    }





    //////////////////////////////////////////////save methods
    public static TrainingClass getTrainingFromJson(String json){
        Gson gson = new Gson();
        return (getTrainingFromJsonSub(gson.fromJson(json, String[].class)));
    }

    private static TrainingClass getTrainingFromJsonSub(String[] json){
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(ExerciseClass.class, new CustomConverter());
        Gson gson = builder.create();

        TrainingClass result=new TrainingClass(json.length-3);

        for(int i=0;i<json.length-3;i++){
            result.addExercise(gson.fromJson(json[i],ExerciseClass.class),i);
        }
        result.isx4=Boolean.parseBoolean(json[json.length-3]);
        result.name=json[json.length-2];
        result.image=Integer.parseInt(json[json.length-1]);
        return result;
    }

    public static String getStringFromTraining(TrainingClass temp){
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(ExerciseClass.class, new CustomConverter());
        Gson gson = builder.create();
        String[] result=new String[temp.exercises.length+3];
        for(int i=0;i<result.length-3;i++){
            result[i]=gson.toJson(temp.exercises[i]);
        }
        result[result.length-3]=String.valueOf(temp.isx4);
        result[result.length-2]=temp.name;
        result[result.length-1]=String.valueOf(temp.image);
        return getStringFromTrainingSub(result);
    }

    private static String getStringFromTrainingSub(String[] temp){
        Gson gson = new Gson();
        return (gson.toJson(temp));
    }
}
