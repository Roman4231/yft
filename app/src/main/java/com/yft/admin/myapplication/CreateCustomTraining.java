package com.yft.admin.myapplication;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.DragEvent;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yft.admin.myapplication.adapters.StableArrayAdapter;
import com.yft.admin.myapplication.classes.DynamicListView;
import com.yft.admin.myapplication.classes.ExerciseClass;
import com.yft.admin.myapplication.classes.MyDragShadowBuilder;
import com.yft.admin.myapplication.classes.SelectedExercises;
import com.yft.admin.myapplication.classes.TrainingClass;

import java.util.ArrayList;


public class CreateCustomTraining extends AppCompatActivity {
    public ArrayList<String> listItems;
    public StableArrayAdapter adapter;
    DynamicListView listView;
    LinearLayout buttonsLayout;
    RelativeLayout layoutLV;
    LinearLayout linearLayout;
    Button submit;
    EditText editText;
    public TextView[] myTVList;
    boolean var;
    int amountOfExercises;
    String[] exerciseNames;
    boolean mCellIsMobile;
    int mDownX;
    int mDownY;
    String[] trainingNames;
    public ArrayList<SelectedExercises> selectedExercises;
    int trainingNumber;
    TrainingClass training;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_custom_training);


        Intent intent = getIntent();
        setTrainingNames();
        selectedExercises=new ArrayList<>();
        listItems= new ArrayList<String>();
        adapter= new StableArrayAdapter(this, R.layout.row, listItems);
        editText=(EditText)findViewById(R.id.create_custom_training_exercise_name);
        linearLayout=(LinearLayout)findViewById(R.id.create_custom_training_adding_point);
        submit=(Button) findViewById(R.id.create_custom_training_submit);
        listView = (DynamicListView) findViewById(R.id.listview);
        buttonsLayout=(LinearLayout) findViewById(R.id.buttonsLayout);
        layoutLV=(RelativeLayout)findViewById(R.id.create_custom_training_LV);
        listView.setCheeseList(listItems);
        listView.setAdapter(adapter);
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        layoutLV.setOnDragListener(new MyDragListener());
        buttonsLayout.setOnDragListener(new MyDragButtonsListener());

        editText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.setFocusable(true);
                v.setFocusableInTouchMode(true);
                return false;
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editText.getText().toString().equals("")) {
                    Toast.makeText(CreateCustomTraining.this, "Enter training name", Toast.LENGTH_SHORT).show();
                    return;
                }

                for(int i=0;i<trainingNames.length;i++){
                    if(editText.getText().toString().equals(trainingNames[i])){
                        if(editText.getText().toString().equals(training.name)){break;}
                        Toast.makeText(CreateCustomTraining.this, "This name is already in use. Please choose another", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }

                if(adapter.getCount()==0){
                    Toast.makeText(CreateCustomTraining.this, "Please choose exercise", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(trainingNumber>=0){
                    TrainingClass res = new TrainingClass(listItems.size());
                    res.name = editText.getText().toString();
                    for (int i = 0; i < selectedExercises.size(); i++) {
                        String temp=selectedExercises.get(i).exerciseName;
                        if(temp.charAt(temp.length()-1)=='4'){
                            temp=temp.substring(0,temp.length()-3);
                        }
                        int reps=selectedExercises.get(i).reps;
                        res.addExercise(getExercise(temp,reps), i);
                    }
                    res.isx4=true;
                    res.image=training.image;
                    saveTrainingAtPosition(res,trainingNumber);
                    InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    mgr.hideSoftInputFromWindow(editText.getWindowToken(), 0);
                    finish();
                    return;
                }

                SharedPreferences sPref = CreateCustomTraining.this.getSharedPreferences("MyDateBase", MODE_PRIVATE);
                int lastImage = sPref.getInt("LastImage",0);
                SharedPreferences.Editor editor=sPref.edit();
                if(lastImage==4){
                    editor.putInt("LastImage",0);
                }else{
                    editor.putInt("LastImage",lastImage+1);
                }
                editor.commit();
                TrainingClass res = new TrainingClass(listItems.size());
                res.isx4=true;
                res.name = editText.getText().toString();
                for (int i = 0; i < selectedExercises.size(); i++) {
                    ///store date somewhere and get view from that folder
                    String name=selectedExercises.get(i).exerciseName;
                    if(name.charAt(name.length()-1)=='4'){
                        name=name.substring(0,name.length()-3);
                    }
                    int reps=selectedExercises.get(i).reps;
                    res.addExercise(getExercise(name,reps), i);
                }
                switch (lastImage){
                    case 0:
                        res.image=R.drawable.custom_training_0;
                        break;
                    case 1:
                        res.image=R.drawable.custom_training_1;
                        break;
                    case 2:
                        res.image=R.drawable.custom_training_2;
                        break;
                    case 3:
                        res.image=R.drawable.custom_training_3;
                        break;
                    case 4:
                        res.image=R.drawable.custom_training_4;
                        break;
                }
                saveTraining(res);
                InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                mgr.hideSoftInputFromWindow(editText.getWindowToken(), 0);
                finish();
                }
        });

        //////adding exercises in bottom
        mCellIsMobile=false;

        amountOfExercises=ExerciseClass.getAmountOfExercises();
        exerciseNames=ExerciseClass.getAllExercisesNames();

        myTVList=new TextView[amountOfExercises];
        for(int i=0;i<amountOfExercises;i++)
        {
            myTVList[i] = new TextView(this);
            LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.MATCH_PARENT);
            params.setMargins(10,10,10,10);
            myTVList[i].setLayoutParams(params);
            myTVList[i].setText(exerciseNames[i]);
            myTVList[i].setBackgroundResource(R.drawable.normal_border);
            myTVList[i].setPadding(20, 20, 20, 20);// in pixels (left, top, right, bottom)
            myTVList[i].setGravity(Gravity.CENTER);
            linearLayout.addView(myTVList[i]);
            myTVList[i].setOnLongClickListener(myOnLongClickListener);
        }


        trainingNumber = intent.getIntExtra("editedTraining",-1);
        if(trainingNumber>=0){
            setTraining(trainingNumber);
        }




    }

    private void setTraining(int position) {
        SharedPreferences sPref = getSharedPreferences("MyDateBase",MODE_PRIVATE);
        if(!(sPref.getString("Training"+position,"").equals(""))){
            training=TrainingClass.getTrainingFromJson((sPref.getString("Training"+position,"")));
            editText.setText(training.name);
            setListItems(training);
        }

    }

    public void setListItems(TrainingClass training) {
        for(int i=0;i<training.exercises.length;i++){
            addToListSetInvis(training.exercises[i].name);
            selectedExercises.add(new SelectedExercises(training.exercises[i].name,training.exercises[i].howManyTimes));
            adapter.notifyDataSetChanged();
        }

    }


    private void addToListSetInvis(String name) {
        listItems.add(name);
        adapter.addElement(name);
        for (int i=0;i<myTVList.length;i++){
            String str=myTVList[i].getText().toString().replaceAll("\n"," ");
            if(str.equals(name)){
                myTVList[i].setVisibility(View.INVISIBLE);
                break;
            }
        }
    }


    class MyDragListener implements View.OnDragListener {
        @Override
        public boolean onDrag(View v, DragEvent event) {
            TextView view=(TextView)event.getLocalState();
            String name=(view).getText().toString();
            switch (event.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:
                    // do nothing
                    break;
                case DragEvent.ACTION_DRAG_ENTERED:
                    var=false;
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    break;
                case DragEvent.ACTION_DROP:
                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                    break;
                default:
                    break;
            }
            return true;
        }
    }

    class MyDragButtonsListener implements View.OnDragListener {
        @Override
        public boolean onDrag(View v, DragEvent event) {
            TextView view=(TextView)event.getLocalState();
            String name=(view).getText().toString();
            switch (event.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:
                    // do nothing
                    break;
                case DragEvent.ACTION_DRAG_ENTERED:
                    var=true;
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    break;
                case DragEvent.ACTION_DROP:
                    // Dropped, reassign View to ViewGroup
                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                    if(var==false){
                        name=name.replaceAll("\n"," ");
                        listItems.add(name);
                        adapter.addElement(name);
                        selectedExercises.add(new SelectedExercises(name,0));
                        //listView.setCheeseList(listItems);
                        //adapter.addElement(name);
                        adapter.notifyDataSetChanged();
                        layoutLV.findViewById(R.id.create_custom_training_drop_it_here).setVisibility(View.GONE);

                    }else{
                        if(listItems.size()!=0) {
                            layoutLV.findViewById(R.id.create_custom_training_drop_it_here).setVisibility(View.GONE);
                        }
                        view.setVisibility(View.VISIBLE);
                    }
                    break;
                default:
                    break;
            }
            return true;
        }
    }

    private View.OnLongClickListener myOnLongClickListener=new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            ClipData data = ClipData.newPlainText("", "");
            MyDragShadowBuilder shadowBuilder = new MyDragShadowBuilder(v);
            shadowBuilder.onProvideShadowMetrics(new Point(v.getLeft(),v.getTop()),new Point(mDownX,mDownY));

            v.startDrag(data,shadowBuilder,v,0);
            layoutLV.findViewById(R.id.create_custom_training_drop_it_here).setVisibility(View.VISIBLE);
            v.setVisibility(View.INVISIBLE);
            v.setOnTouchListener(myOnTouchListener);
            mCellIsMobile=true;
            return false;
        }
    };

    private View.OnTouchListener myOnTouchListener=new View.OnTouchListener() {

        @Override
        public boolean onTouch(View v, MotionEvent event) {

            switch (event.getAction() & MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_DOWN:
                    mDownX = (int)event.getX();
                    mDownY = (int)event.getY();
                    break;
                case MotionEvent.ACTION_MOVE:
                    if (mCellIsMobile) {
                        return false;
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    v.setOnTouchListener(null);
                    mCellIsMobile=false;
                    mDownX=0;
                    mDownY=0;
                    break;
                case MotionEvent.ACTION_CANCEL:
                    v.setOnTouchListener(null);
                    mCellIsMobile=false;
                    mDownX=0;
                    mDownY=0;
                    break;
                default:
                    break;
            }
            return true;
        }
    };

    private ExerciseClass getExercise(String name,int amount){
        for(int i=0;i<amountOfExercises;i++){
            String str=exerciseNames[i].replaceAll("\n"," ");
            if(name.equals(str)){
                return ExerciseClass.getFunkByIndex(i,amount);
            }
        }
        return null;
    }

    private void setTrainingNames(){
        SharedPreferences sPref = CreateCustomTraining.this.getSharedPreferences("MyDateBase", MODE_PRIVATE);
        int n = sPref.getInt("numberOfCustomTrainings", 0);
        trainingNames=new String[n];
        for(int i=0;i<n;i++){
            trainingNames[i]=TrainingClass.getTrainingFromJson(sPref.getString("Training"+i,"")).name;
        }
    }
    private void saveTrainingAtPosition(TrainingClass training,int position){
        SharedPreferences sPref = CreateCustomTraining.this.getSharedPreferences("MyDateBase", MODE_PRIVATE);
        SharedPreferences.Editor editor = sPref.edit();
        editor.putString("Training" + position, TrainingClass.getStringFromTraining(training));
        editor.commit();
    }

    private void saveTraining(TrainingClass training){
        SharedPreferences sPref = CreateCustomTraining.this.getSharedPreferences("MyDateBase", MODE_PRIVATE);
        int n = sPref.getInt("numberOfCustomTrainings", 0);
        SharedPreferences.Editor editor = sPref.edit();
        editor.putString("Training" + n, TrainingClass.getStringFromTraining(training));
        editor.putInt("numberOfCustomTrainings", n + 1);
        editor.commit();
    }

}

