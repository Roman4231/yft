package com.yft.admin.myapplication;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.yft.admin.myapplication.classes.CompletedTrainings;

public class TrainingResult extends AppCompatActivity {

    TextView name;
    TextView level;
    TextView exercises;
    TextView time;

    CompletedTrainings training;
    int choosedTrainingNumber;
    SharedPreferences sPref;

    String nameStr;
    String levelStr;
    String exercisesStr;
    String timeStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training_result);

        name = (TextView) findViewById(R.id.training_result_name);
        level = (TextView) findViewById(R.id.training_result_level);
        exercises = (TextView) findViewById(R.id.training_result_exercises);
        time = (TextView) findViewById(R.id.training_result_total_time);
        sPref = getSharedPreferences("MyDateBase",MODE_PRIVATE);
        exercisesStr="";

        training=CompletedTrainings.getCompletedTrainingFromJson(getIntent().getStringExtra("TrainingResult"));
        choosedTrainingNumber = sPref.getInt("ChoosedTraining", 0);
        nameStr = training.name;
        for(int i=0;i<training.completedExercises.size();i++){
            exercisesStr+="\n"+training.completedExercises.get(i).name+" reps:"+training.completedExercises.get(i).reps;
        }
        timeStr = "Total time: " + training.totalTime/60000 + " minutes " + training.totalTime/1000 + " seconds!";


        name.setText(nameStr);
        exercises.setText(exercisesStr);
        time.setText(timeStr);

        if(choosedTrainingNumber > 2){
            level.setVisibility(View.GONE);
        }
        else {
            if(training.level == -1){
                training.level = 1;
            }
        }

        if(training.level == 0) {
            level.setText("Your training level is: easy");
        }
        if(training.level == 1) {
            level.setText("Your training level is: middle");
        }
        if(training.level == 2) {
            level.setText("Your training level is: hard");
        }
    }
}
