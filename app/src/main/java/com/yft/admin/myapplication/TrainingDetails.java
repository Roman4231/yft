package com.yft.admin.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yft.admin.myapplication.classes.NonScrollListView;
import com.yft.admin.myapplication.classes.TrainingClass;
import com.yft.admin.myapplication.classes.Trainings;
import com.yft.admin.myapplication.dialog.TrainingDetailsExerciseInfoDialog;
import com.yft.admin.myapplication.dialog.Training_details_dialog;

import java.util.ArrayList;
import java.util.List;


public class TrainingDetails extends AppCompatActivity {
    int isDumbbellOn;
    int position;
    int chosedLevel;
    int curentLevelDisplayed;
    SharedPreferences sPref;
    TrainingClass currentTraining;
    ImageView imHead;
    ImageButton level0;
    ImageButton level1;
    ImageButton level2;
    LinearLayout levelLayout;
    ImageButton arrowLeft;
    ImageButton arrowRight;
    NonScrollListView listView;
    NonScrollListView listView1;
    NonScrollListView listView2;
    ArrayList<String> items;
    LinearLayout infoLayout;
    MyAdapter adapter;
    TrainingClass training0;
    TrainingClass training1;
    TrainingClass training2;
    Toolbar toolbar;
    TextView levelTV;
    CardView levelsCV;
    CardView infoCV;
    FloatingActionButton fab;
    RelativeLayout rbLayout0;
    RelativeLayout rbLayout1;
    RelativeLayout rbLayout2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training_details);
        toolbar = (Toolbar) findViewById(R.id.training_details_toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        fab = (FloatingActionButton) findViewById(R.id.training_details_fab);

        rbLayout0=(RelativeLayout) findViewById(R.id.training_details_RB0_layout);
        rbLayout1=(RelativeLayout) findViewById(R.id.training_details_RB1_layout);
        rbLayout2=(RelativeLayout) findViewById(R.id.training_details_RB2_layout);
        infoCV=(CardView) findViewById(R.id.card_view_LV);
        levelsCV=(CardView) findViewById(R.id.card_view_RB);
        levelTV=(TextView) findViewById(R.id.training_details_level_tv);
        imHead=(ImageView)findViewById(R.id.training_details_image);
        levelLayout=(LinearLayout) findViewById(R.id.training_details_myRB);
        level0=(ImageButton) findViewById(R.id.training_details_myRB0);
        level1=(ImageButton) findViewById(R.id.training_details_myRB1);
        level2=(ImageButton) findViewById(R.id.training_details_myRB2);
        arrowLeft=(ImageButton) findViewById(R.id.training_details_arrow_left);
        arrowRight=(ImageButton) findViewById(R.id.training_details_arrow_right);
        listView=(NonScrollListView) findViewById(R.id.training_details_listView);
        listView1=(NonScrollListView) findViewById(R.id.training_details_listView1);
        listView2=(NonScrollListView) findViewById(R.id.training_details_listView2);
        infoLayout=(LinearLayout)findViewById(R.id.training_details_training_training_info);

        level0.setOnClickListener(onClickListenerRB);
        level1.setOnClickListener(onClickListenerRB);
        level2.setOnClickListener(onClickListenerRB);
        listView.setFocusable(false);
        listView1.setFocusable(false);
        listView2 .setFocusable(false);
        findViewById(R.id.training_details_main_layout).requestFocus();

        Intent intent=getIntent();
        String choosedTr=intent.getStringExtra("TrainingInfo");
        isDumbbellOn=choosedTr.charAt(choosedTr.length()-1)=='1'?(1):(0);
        choosedTr = choosedTr.substring(0, choosedTr.length()-1);
        position = Integer.parseInt(choosedTr);

    }

    @Override
    public void onResume(){
        super.onResume();
        items=new ArrayList<>();
        if(position>2){
            sPref = this.getSharedPreferences("MyDateBase", MODE_PRIVATE);
            currentTraining=TrainingClass.getTrainingFromJson(sPref.getString("Training"+(position-3),""));
            setActivityCustom(currentTraining);
        }else{
            switch (position){
                case 0:
                    imHead.setImageResource(R.drawable.choose_training_top);
                    toolbar.setTitle(Trainings.topBezGant(0).name);
                    switch (isDumbbellOn){
                        case 0:
                            setActivityDefault(Trainings.topBezGant(0),Trainings.topBezGant(1),Trainings.topBezGant(2));
                            break;
                        case 1:
                            setActivityDefault(Trainings.topZGant(0),Trainings.topZGant(1),Trainings.topZGant(2));
                            break;
                    }
                    break;
                case 1:
                    imHead.setImageResource(R.drawable.choose_training_full);
                    toolbar.setTitle(Trainings.fullBodyBezGant(0).name);
                    switch (isDumbbellOn){
                        case 0:
                            setActivityDefault(Trainings.fullBodyBezGant(0),Trainings.fullBodyBezGant(1),Trainings.fullBodyBezGant(2));
                            break;
                        case 1:
                            setActivityDefault(Trainings.fullBodyZGant(0),Trainings.fullBodyZGant(1),Trainings.fullBodyZGant(2));
                            break;
                    }
                    break;
                case 2:
                    imHead.setImageResource(R.drawable.choose_training_bottom);
                    toolbar.setTitle(Trainings.bottomBezGant(0).name);
                    switch (isDumbbellOn){
                        case 0:
                            setActivityDefault(Trainings.bottomBezGant(0),Trainings.bottomBezGant(1),Trainings.bottomBezGant(2));
                            break;
                        case 1:
                            setActivityDefault(Trainings.bottomZGant(0),Trainings.bottomZGant(1),Trainings.bottomZGant(2));
                            break;
                    }
                    break;
            }
        }
    }

    private void setActivityDefault(TrainingClass trainingClass0, TrainingClass trainingClass1, TrainingClass trainingClass2) {
        fab.setVisibility(View.GONE);
        training0=trainingClass0;
        training1=trainingClass1;
        training2=trainingClass2;
        toolbar.setTitle(training0.name);
        levelLayout.setVisibility(View.VISIBLE);
        arrowLeft.setVisibility(View.VISIBLE);
        arrowRight.setVisibility(View.VISIBLE);
        sPref = this.getSharedPreferences("MyDateBase", MODE_PRIVATE);
        updateItems(training0,listView);
        updateItems(training1,listView1);
        updateItems(training2,listView2);
        if(sPref.getInt("ChoosedTraining",-1)==position&sPref.getInt("IsDumbbellOn",-1)==isDumbbellOn) {
            curentLevelDisplayed = chosedLevel = sPref.getInt("ChosedLevel", -2);
            switch (chosedLevel) {
                case 0:
                    rbLayout0.setBackgroundResource(R.color.myLightGreenTransparent);
                    level0.setImageResource(R.drawable.ic_training_details_check_mark);
                    level1.setImageResource(0);
                    level2.setImageResource(0);
                    levelTV.setText("Easy");
                    break;
                case 1:
                    rbLayout1.setBackgroundResource(R.color.myLightGreenTransparent);
                    level1.setImageResource(R.drawable.ic_training_details_check_mark);
                    level0.setImageResource(0);
                    level2.setImageResource(0);
                    levelTV.setText("Middle");
                    break;
                case 2:
                    rbLayout2.setBackgroundResource(R.color.myLightGreenTransparent);
                    level2.setImageResource(R.drawable.ic_training_details_check_mark);
                    level0.setImageResource(0);
                    level1.setImageResource(0);
                    levelTV.setText("Hard");
                    break;
                default:
                    rbLayout1.setBackgroundResource(R.color.myLightGreenTransparent);
                    curentLevelDisplayed=1;
                    levelTV.setText("Middle");
            }
        }else{
            rbLayout1.setBackgroundResource(R.color.myLightGreenTransparent);
            curentLevelDisplayed=1;
            levelTV.setText("Middle");
        }
        switch (curentLevelDisplayed){
            case 0:
                setLVVisible(0);
                currentTraining=training0;
                levelTV.setText("Easy");
                break;
            case 1:
                setLVVisible(1);
                currentTraining=training1;
                levelTV.setText("Middle");
                break;
            case 2:
                setLVVisible(2);
                currentTraining=training2;
                levelTV.setText("Hard");
                break;
        }
        arrowRight.setOnClickListener(myClickListener);
        arrowLeft.setOnClickListener(myClickListener);
    }

    private void setActivityCustom(TrainingClass currentTraining) {
        fab.setOnClickListener(myClickListener);
        levelsCV.setVisibility(View.GONE);
        arrowLeft.setVisibility(View.GONE);
        arrowRight.setVisibility(View.GONE);
        imHead.setImageResource(currentTraining.image);
        levelTV.setText(currentTraining.name);
        for(int i=0;i<currentTraining.exercises.length;i++){
            items.add(currentTraining.exercises[i].name);
        }
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(50, 100, 50, 10);
        infoCV.setLayoutParams(params);
        adapter=new MyAdapter(this,items);
        listView.setAdapter(adapter);
    }



    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public class MyAdapter extends ArrayAdapter<String> {
        private final Activity context;
        private final List<String> names;
        View rowView;
        ArrayList<String> holder;

        public MyAdapter(Activity context, ArrayList<String> names) {
            super(context, R.layout.training_details_row, names);
            this.context = context;
            this.names = names;
        }

        // Êëàññ äëÿ ñîõðàíåíèÿ âî âíåøíèé êëàññ è äëÿ îãðàíè÷åíèÿ äîñòóïà
        // èç ïîòîìêîâ êëàññà
        class ViewHolder {
            public TextView name;
            public TextView amount;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // ViewHolder áóôåðèçèðóåò îöåíêó ðàçëè÷íûõ ïîëåé øàáëîíà ýëåìåíòà

            ViewHolder holder = null;
            // Î÷èùàåò ñóùåòñâóþùèé øàáëîí, åñëè ïàðàìåòð çàäàí
            // Ðàáîòàåò òîëüêî åñëè áàçîâûé øàáëîí äëÿ âñåõ êëàññîâ îäèí è òîò æå
            rowView = convertView;
            if (rowView == null) {
                holder = new MyAdapter.ViewHolder();
                newView(position,holder);
            } else {
                String str=((TextView)rowView.findViewById(R.id.training_details_row_name)).getText().toString().replaceAll(" x4","");
                if(!(str.equals(names.get(position)))){
                    holder = new MyAdapter.ViewHolder();
                    newView(position, holder);
                }
                else {
                    holder = (ViewHolder) rowView.getTag();
                }
            }

            holder.name.setText(names.get(position));

            return rowView;
        }

        void newView(final int position, ViewHolder holder) {
            LayoutInflater inflater = context.getLayoutInflater();
            rowView = inflater.inflate(R.layout.training_details_row, null, true);
            holder.name = (TextView) rowView.findViewById(R.id.training_details_row_name);
            holder.amount = (TextView) rowView.findViewById(R.id.training_details_row_amount);
            switch (currentTraining.exercises[position].name){
                case "Recovery":
                    holder.amount.setText(currentTraining.exercises[position].howManyTimes + " sec");
                    holder.name.setTextColor(getResources().getColor(R.color.myDark20));
                    break;
                case "Plank":
                    holder.amount.setText(currentTraining.exercises[position].howManyTimes + " sec");
                    break;
                default:
                    holder.amount.setText(currentTraining.exercises[position].howManyTimes + " reps");
            }
            if(!(currentTraining.exercises[position].name.equals("Recovery"))) {
                rowView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        TrainingDetailsExerciseInfoDialog dialog = new TrainingDetailsExerciseInfoDialog();
                        dialog.show(getFragmentManager(), currentTraining.exercises[position].name);
                    }
                });
            }
            rowView.setTag(holder);
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    View.OnClickListener onClickListenerRB=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.training_details_myRB0:
                    newLevelCheck(0);
                    break;
                case R.id.training_details_myRB1:
                    newLevelCheck(1);
                    break;
                case R.id.training_details_myRB2:
                    newLevelCheck(2);
                    break;
            }
        }
    };

    View.OnClickListener myClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.training_details_fab:
                    Intent intent = new Intent(TrainingDetails.this, CreateCustomTraining.class);
                    intent.putExtra("editedTraining", position-3);
                    startActivity(intent);
                    break;
                case R.id.training_details_arrow_left:
                    switch (curentLevelDisplayed){
                        case 0:
                            rbLayout0.setBackgroundResource(R.color.myGray_f4);
                            rbLayout2.setBackgroundResource(R.color.myLightGreenTransparent);
                            setLVVisible(2);
                            curentLevelDisplayed=2;
                            currentTraining=training2;
                            levelTV.setText("Hard");
                            break;
                        case 1:
                            rbLayout1.setBackgroundResource(R.color.myGray_f4);
                            rbLayout0.setBackgroundResource(R.color.myLightGreenTransparent);
                            setLVVisible(0);
                            curentLevelDisplayed=0;
                            currentTraining=training0;
                            levelTV.setText("Easy");
                            break;
                        case 2:
                            rbLayout2.setBackgroundResource(R.color.myGray_f4);
                            rbLayout1.setBackgroundResource(R.color.myLightGreenTransparent);
                            setLVVisible(1);
                            curentLevelDisplayed=1;
                            currentTraining=training1;
                            levelTV.setText("Middle");
                            break;
                    }
                    break;
                case R.id.training_details_arrow_right:
                    switch (curentLevelDisplayed){
                        case 0:
                            rbLayout0.setBackgroundResource(R.color.myGray_f4);
                            rbLayout1.setBackgroundResource(R.color.myLightGreenTransparent);
                            setLVVisible(1);
                            curentLevelDisplayed=1;
                            currentTraining=training1;
                            levelTV.setText("Middle");
                            break;
                        case 1:
                            rbLayout1.setBackgroundResource(R.color.myGray_f4);
                            rbLayout2.setBackgroundResource(R.color.myLightGreenTransparent);
                            setLVVisible(2);
                            curentLevelDisplayed=2;
                            currentTraining=training2;
                            levelTV.setText("Hard");
                            break;
                        case 2:
                            rbLayout2.setBackgroundResource(R.color.myGray_f4);
                            rbLayout0.setBackgroundResource(R.color.myLightGreenTransparent);
                            setLVVisible(0);
                            curentLevelDisplayed=0;
                            currentTraining=training0;
                            levelTV.setText("Easy");
                            break;
                    }
                    break;
            }
        }
    };

    void updateItems(TrainingClass training,NonScrollListView LV){
        items=new ArrayList<>();
        for (int i=0;i<training.exercises.length;i++){
            if(training.isx4==true){
                items.add(training.exercises[i].name+" x4");
            }else{
                items.add(training.exercises[i].name);
            }
        }
        LV.setAdapter(new MyAdapter(this,items));
    }

    void newLevelCheck(int level){
        if(sPref.getInt("ChoosedTraining",-1)==position){
            saveLevel(level);
        }else{
            DialogFragment dialog =new Training_details_dialog();
            dialog.show(getSupportFragmentManager(),String.valueOf(level));
        }
    }

    void saveLevel(int level){
        SharedPreferences.Editor editor=sPref.edit();
        editor.putInt("IsDumbbellOn",isDumbbellOn);
        switch (level){
            case 0:
                editor.putInt("ChosedLevel",0);
                level0.setImageResource(R.drawable.ic_training_details_check_mark);
                level1.setImageResource(0);
                level2.setImageResource(0);
                chosedLevel=0;
                break;
            case 1:
                editor.putInt("ChosedLevel",1);
                level0.setImageResource(0);
                level1.setImageResource(R.drawable.ic_training_details_check_mark);
                level2.setImageResource(0);
                chosedLevel=1;
                break;
            case 2:
                editor.putInt("ChosedLevel",2);
                level0.setImageResource(0);
                level1.setImageResource(0);
                level2.setImageResource(R.drawable.ic_training_details_check_mark);
                chosedLevel=2;
                break;
        }
        editor.commit();
    }

    public void setDialogResult(int level){
        saveLevel(level);
        SharedPreferences.Editor editor=sPref.edit();
        editor.putInt("ChoosedTraining",position);
        editor.commit();
    }
    void setLVVisible(int level){
        switch (level){
            case 0:
                listView.setVisibility(View.VISIBLE);
                listView1.setVisibility(View.GONE);
                listView2.setVisibility(View.GONE);
                break;
            case 1:
                listView.setVisibility(View.GONE);
                listView1.setVisibility(View.VISIBLE);
                listView2.setVisibility(View.GONE);
                break;
            case 2:
                listView.setVisibility(View.GONE);
                listView1.setVisibility(View.GONE);
                listView2.setVisibility(View.VISIBLE);
                break;
        }
    }
}
