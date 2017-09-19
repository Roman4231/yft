package com.yft.admin.myapplication;


import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;


import com.yft.admin.myapplication.classes.CompletedExercises;
import com.yft.admin.myapplication.classes.CompletedTrainings;
import com.yft.admin.myapplication.classes.ExerciseClass;
import com.yft.admin.myapplication.classes.TrainingClass;
import com.yft.admin.myapplication.classes.Trainings;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;


public class TrainingActivity extends AppCompatActivity{

    private static final int EXERCISE_END = 0;
    private static final int SET_END = 1;
    private static final int RECOVERY_END = 2;
    SharedPreferences sPref;
    TrainingClass currentTraining;
    VideoView video;
    TextView trainingName;
    TextView exerciseName;
    TextView sets;
    TextView reps;
    TextView recoveryTime;
    ImageView imagePlank;
    ImageButton arrowLeft;
    ImageButton arrowRight;
    RelativeLayout mainLayout;
    Handler handler;
    int currentSet;
    int currentExercise;
    TextToSpeech tts;
    Timer mTimer;
    long timeBegin;
    ArrayList<CompletedExercises> completedExercises;
    MediaPlayer music;
    MediaPlayer timerSound;
    CountDownTimer recoveryTimer;
    CountDownTimer resumeTimer;
    boolean firstTime;
    boolean isMusicOn;
    View toastView;
    ImageView toastImage;
    Toast toast;
    SaveState state;
    int choosedLevel;
    PlankTimer plankTimer;
    PlankTimerResume plankTimerResume;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training);

        currentExercise=0;
        currentSet=0;
        sPref = getSharedPreferences("MyDateBase",MODE_PRIVATE);
        setCurrentTraining();

        state=new SaveState();
        state.isRecovery=false;
        state.condition=true;
        firstTime=false;
        toastView = getLayoutInflater().inflate(R.layout.toast, (ViewGroup)findViewById(R.id.toastLayout));
        toastImage= (ImageView)toastView.findViewById(R.id.toast_image);
        music=MediaPlayer.create(getApplicationContext(), R.raw.training_music);
        timerSound=MediaPlayer.create(getApplicationContext(), R.raw.timer_sound);
        completedExercises=new ArrayList<>();
        timeBegin=System.currentTimeMillis();
        imagePlank=(ImageView) findViewById(R.id.training_activity_plank_image);
        video=(VideoView) findViewById(R.id.training_activity_video);
        recoveryTime=(TextView) findViewById(R.id.training_activity_recovery_timer);
        trainingName=(TextView) findViewById(R.id.training_activity_training_name);
        exerciseName=(TextView) findViewById(R.id.training_activity_exercise_name);
        sets=(TextView) findViewById(R.id.training_activity_sets);
        reps=(TextView) findViewById(R.id.training_activity_reps);
        arrowLeft=(ImageButton) findViewById(R.id.training_activity_arrow_left);
        arrowRight=(ImageButton) findViewById(R.id.training_activity_arrow_right);
        mainLayout=(RelativeLayout) findViewById(R.id.training_activity_main_layout);
        toast = new Toast(this);
        music.setOnCompletionListener(musicRepeat);
        timerSound.setOnCompletionListener(musicRepeat);
        music.setVolume(0.2f,0.2f);

        isMusicOn=sPref.getBoolean("IsMusicOn",true);
        arrowLeft.setOnClickListener(arrowClickListener);
        arrowRight.setOnClickListener(arrowClickListener);
        mainLayout.setOnClickListener(mainLayoutClickListener);

        video.setZOrderOnTop(true);
        video.setOnInfoListener(myVideoInfoListener);
        tts=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    tts.setLanguage(Locale.getDefault());
                }
            }
        });
        if(currentTraining.isx4) {
            handler =handlerCustom;
        }else{
            handler=handlerStandard;
        }

        if(currentTraining.isx4){
            startExerciseX4();
        }else{
            startExerciseStandard();
        }

    }

    private void startExerciseStandard() {
        sets.setVisibility(View.GONE);
        setExerciseName();
        startExercise(currentTraining.exercises[currentExercise]);
    }

    private void startExerciseX4() {
        currentSet=0;
        setExerciseName();
        startExercise(currentTraining.exercises[currentExercise]);
    }


    private void startRecovery(final int sec) {
        timerSound.start();
        state.isRecovery=true;
        recoveryTime.setVisibility(View.VISIBLE);
        video.setVisibility(View.GONE);
        recoveryTimer = new MyRecoveryTimer(sec * 1000, 100);
        recoveryTimer.start();
    }

    void setCurrentTraining(){
        int choosedTr=sPref.getInt("ChoosedTraining",0);
        int isDumbbellOn=sPref.getInt("IsDumbbellOn",1);
        choosedLevel=sPref.getInt("ChosedLevel",1);
        if(choosedLevel==-1){choosedLevel=1;}
        if(choosedTr>2){
            choosedTr-=3;
            currentTraining=TrainingClass.getTrainingFromJson(sPref.getString("Training"+choosedTr,""));
            return;
        }else{
            currentTraining=Trainings.getStandartTraining(choosedTr,isDumbbellOn,choosedLevel);
        }
    }

    void startPlank(ExerciseClass exercise){
        state.isRecovery=false;
        musicStart();
        reps.setText(String.valueOf(exercise.howManyTimes));
        video.setVisibility(View.GONE);
        imagePlank.setVisibility(View.VISIBLE);
        plankTimer=new PlankTimer(exercise.howManyTimes*1000);
        plankTimer.start();
        return;
    }

    void startExercise(ExerciseClass exercise){
        mTimer = new Timer();
        if(exercise.name.equals("Plank")){
            startPlank(exercise);
            return;
        }
        if(exercise.name.equals("Recovery")){
            startRecovery(exercise.howManyTimes);
            return;
        }
        state.isRecovery=false;
        if (currentTraining.isx4) {
            sets.setText("Set: "+(currentSet+1) + "/4");
        }

        if (currentTraining.exercises[currentExercise].url2 == 0) {
            video.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + exercise.url1));
            video.setOnCompletionListener(new MyOnCompletionListener(exercise.howManyTimes));
            musicStart();
            video.start();

        } else {
            video.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + exercise.url1));
            video.setOnCompletionListener(new MyOnCompletionListener(exercise.howManyTimes, Uri.parse("android.resource://" + getPackageName() + "/" + exercise.url2)));
            musicStart();
            video.start();
        }

    }

    class MyOnCompletionListener implements MediaPlayer.OnCompletionListener{
        int amount;
        int currentRep;
        int currentRepSecond;
        Uri uriSecond;

        public  MyOnCompletionListener(int amount){
            this(amount,null);
        }

        public  MyOnCompletionListener(int amount, Uri uriSecond){
            currentRep=1;
            currentRepSecond=0;
            this.uriSecond=uriSecond;
            this.amount=amount;
            reps.setText("Rep: "+currentRep+"/"+amount);
        }

        @Override
        public void onCompletion(MediaPlayer mp) {
            if(currentRep<amount){
                currentRep++;
                reps.setText("Rep: "+currentRep+"/"+amount);
                mp.start();
                if(currentRep!=amount +1|| uriSecond!=null) {
                    tts.speak("One", TextToSpeech.QUEUE_FLUSH, null);
                    mTimer=new Timer();
                    mTimer.schedule(new MyTimerTaskSayTwo(), mp.getDuration() / 2);
                }
            }else{
                if(uriSecond!=null) {
                    (new MyCountDownTimerChangeHand(3500,1000,uriSecond,amount)).start();
                }else {
                    addExerciseToCompleted(currentTraining.exercises[currentExercise]);
                    handler.sendEmptyMessage(EXERCISE_END);
                }
            }
        }
    }

    class MyOnCompletionListenerSecond implements MediaPlayer.OnCompletionListener {
        int amount;
        int currentRep;
        public MyOnCompletionListenerSecond(int amount) {
            this.amount=amount;
            currentRep=1;
        }

        @Override
        public void onCompletion(MediaPlayer mp) {
            if(currentRep<amount){
                currentRep++;
                reps.setText("Rep: "+currentRep+"/"+amount);
                mp.start();
                if(currentRep!=amount +1) {
                    tts.speak("One", TextToSpeech.QUEUE_FLUSH, null);
                    mTimer=new Timer();
                    mTimer.schedule(new MyTimerTaskSayTwo(), mp.getDuration() / 2);
                }
            }else{
                addExerciseToCompleted(currentTraining.exercises[currentExercise]);
                handler.sendEmptyMessage(EXERCISE_END);
            }
        }
    }

    Handler handlerCustom=new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case EXERCISE_END:
                    musicStopAndPrepare();
                    //TODO:SET TV
                    if (currentSet == 3) {

                        currentExercise++;
                        handler.sendEmptyMessage(SET_END);
                    } else {
                        currentSet++;
                        startRecovery(30);
                    }
                    break;
                case SET_END:
                    musicStopAndPrepare();
                    //TODO:SET TV
                    currentSet=0;
                    if (currentExercise >= currentTraining.exercises.length) {
                        recoveryTime.setVisibility(View.VISIBLE);
                        video.setVisibility(View.GONE);
                        recoveryTime.setText("Finish");
                        goToFinish();
                    } else {
                        startRecovery(60);
                    }
                    break;
                case RECOVERY_END:
                    state.isRecovery=false;
                    setExerciseName();
                    startExerciseTimer.start();
                    break;
            }
        }
    };

    Handler handlerStandard=new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case RECOVERY_END:
                    state.isRecovery=false;
                case EXERCISE_END:
                    musicStopAndPrepare();
                    currentExercise++;
                    if (currentExercise >= currentTraining.exercises.length) {
                        recoveryTime.setVisibility(View.VISIBLE);
                        video.setVisibility(View.GONE);
                        recoveryTime.setText("Finish");
                        goToFinish();
                    } else {
                        setExerciseName();
                        startExerciseTimer.start();
                    }
                    break;
            }
        }
    };

    private void goToFinish() {
        long totalTime=System.currentTimeMillis()-timeBegin;
        CompletedTrainings res=new CompletedTrainings(totalTime,currentTraining.name,completedExercises);
        int year = Calendar.getInstance().get(Calendar.YEAR);
        int month= Calendar.getInstance().get(Calendar.MONTH);
        int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        res.setDate(year,month,day);
        res.level=choosedLevel;
        String completedTraining=CompletedTrainings.getStringFromCompletedTraining(res);
        if(completedExercises.size()!=0) {
            CompletedTrainings.saveCompletedTraining(res, TrainingActivity.this);
        }
        Intent intent=new Intent(TrainingActivity.this, TrainingResult.class);
        intent.putExtra("TrainingResult",completedTraining);
        startActivity(intent);
    }

    MediaPlayer.OnInfoListener myVideoInfoListener=new MediaPlayer.OnInfoListener() {
        @Override
        public boolean onInfo(MediaPlayer mp, int what, int extra) {

            if (what == MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START) {
                tts.speak("One", TextToSpeech.QUEUE_FLUSH, null);
                mTimer=new Timer();
                mTimer.schedule(new MyTimerTaskSayTwo(),mp.getDuration()/2);
                return true;
            }
            return false;
        }
    };

    class MyTimerTaskSayTwo extends TimerTask {
        @Override
        public void run() {
            tts.speak("Two", TextToSpeech.QUEUE_FLUSH, null);
        }
    }

    class PlankTimer extends CountDownTimer{
        int fullTime;
        public PlankTimer(int milliseconds){
            super(milliseconds,1000);
            fullTime=milliseconds;
        }
        public void onTick(long millisUntilFinished){
            reps.setText(String.valueOf(millisUntilFinished/1000));
            state.plankTimeLeft=millisUntilFinished;
            if(millisUntilFinished/1000==fullTime/2000){
                tts.speak(fullTime/2000+" seconds to the end", TextToSpeech.QUEUE_FLUSH, null);
            }else{
                if(millisUntilFinished <4*1000&&millisUntilFinished >=1000){
                    tts.speak(String.valueOf(millisUntilFinished / 1000), TextToSpeech.QUEUE_FLUSH, null);
                }
            }
        }
        public void onFinish() {
            addExerciseToCompleted(currentTraining.exercises[currentExercise]);
            video.setVisibility(View.VISIBLE);
            imagePlank.setVisibility(View.GONE);
            handler.sendEmptyMessage(EXERCISE_END);
        }
    }

    class PlankTimerResume extends CountDownTimer{
        int fullTime;
        public PlankTimerResume(ExerciseClass exercise){
            super(state.plankTimeLeft,1000);
            fullTime=exercise.howManyTimes;
        }
        public void onTick(long millisUntilFinished){
            reps.setText(String.valueOf(millisUntilFinished/1000));
            state.plankTimeLeft=millisUntilFinished;
            if(millisUntilFinished/1000==fullTime/2000){
                tts.speak(fullTime/2000+" seconds to the end", TextToSpeech.QUEUE_FLUSH, null);
            }else{
                if(millisUntilFinished <4*1000&&millisUntilFinished >=1000){
                    tts.speak(String.valueOf(millisUntilFinished / 1000), TextToSpeech.QUEUE_FLUSH, null);
                }
            }
        }
        public void onFinish() {
            addExerciseToCompleted(currentTraining.exercises[currentExercise]);
            video.setVisibility(View.VISIBLE);
            imagePlank.setVisibility(View.GONE);
            handler.sendEmptyMessage(EXERCISE_END);
        }
    }



    void addExerciseToCompleted(ExerciseClass exercise){
        completedExercises.add(new CompletedExercises(exercise.name,exercise.howManyTimes));
    }

    CountDownTimer startExerciseTimer=new CountDownTimer(300,300) {
        @Override
        public void onTick(long millisUntilFinished) {

        }

        @Override
        public void onFinish() {
            startExercise(currentTraining.exercises[currentExercise]);
        }
    };

    class MyRecoveryTimer extends CountDownTimer{
        long wholeTime;
        boolean three;
        boolean two;
        boolean one;
        boolean getReady;
        public MyRecoveryTimer(long wholeTimeInMillis,long timeBetweenTick){
            super(wholeTimeInMillis,timeBetweenTick);
            wholeTime=wholeTimeInMillis;
            three=true;
            two=true;
            one=true;
            getReady=true;
        }

        public void onTick(long millisUntilFinished) {
            state.recovery_time=wholeTime;
            state.timerSaveState=millisUntilFinished;
            if(millisUntilFinished / 1000==wholeTime/2000&&getReady){
                if(currentTraining.exercises[currentExercise].name.equals("Recovery")) {
                    tts.speak("Get ready to " + currentTraining.exercises[currentExercise+1].name, TextToSpeech.QUEUE_FLUSH, null);
                }else{
                    tts.speak("Get ready to " + currentTraining.exercises[currentExercise].name, TextToSpeech.QUEUE_FLUSH, null);
                }
                getReady=false;
            }
            if (three&&millisUntilFinished/1000==3){
                tts.speak(String.valueOf(millisUntilFinished / 1000), TextToSpeech.QUEUE_FLUSH, null);
                three=false;
            }
            if (two&&millisUntilFinished/1000==2){
                tts.speak(String.valueOf(millisUntilFinished / 1000), TextToSpeech.QUEUE_FLUSH, null);
                two=false;
            }
            if (one&&millisUntilFinished/1000==1){
                tts.speak(String.valueOf(millisUntilFinished / 1000), TextToSpeech.QUEUE_FLUSH, null);
                one=false;
            }
            recoveryTime.setText( millisUntilFinished / 1000+"."+(millisUntilFinished / 100)%10);
        }

        public void onFinish() {
            timerSound.pause();
            tts.speak("Go!", TextToSpeech.QUEUE_FLUSH, null);
            recoveryTime.setVisibility(View.GONE);
            video.setVisibility(View.VISIBLE);
            handler.sendEmptyMessage(RECOVERY_END);

        }
    }

    @Override
    public void onPause(){
        super.onPause();
        setOnPause();
    }

    @Override
    public void  onDestroy(){
        super.onDestroy();
        toast.cancel();
    }


    View.OnClickListener mainLayoutClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(state.condition){
                setOnPause();
            }else{
                setOnResume();
            }
        }
    };

    private void setOnResume() {
        mTimer = new Timer();
        state.condition=true;
        if(state.isRecovery){
            resumeTimer=new OnResumeTimer(state.timerSaveState,100,state.recovery_time);
            resumeTimer.start();
        }else{
            if(currentTraining.exercises[currentExercise].name.equals("Plank")){
                musicStart();
                resumeTimer=new PlankTimerResume(currentTraining.exercises[currentExercise]);
                resumeTimer.start();
            }else{
                musicStart();
                video.start();
            }
        }
        toastImage.setImageResource(R.drawable.ic_toast_play);
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(toastView);
        toast.show();
    }

    private void setOnPause() {
        state.condition=false;
        if(plankTimer!=null)plankTimer.cancel();
        if(plankTimerResume!=null)plankTimerResume.cancel();
        if(resumeTimer!=null)resumeTimer.cancel();
        if(state.isRecovery){
            timerSound.pause();
            tts.stop();
            recoveryTimer.cancel();
        }else{
            musicPause();
            video.pause();
            tts.stop();
            mTimer.cancel();
        }
        toastImage.setImageResource(R.drawable.ic_toast_pause);
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(toastView);
        toast.show();
    }

    class SaveState{
        public long recovery_time;
        public long timerSaveState;
        public long plankTimeLeft;
        public boolean condition;
        public boolean isRecovery;
    }

    class OnResumeTimer extends CountDownTimer{
        long recoveryTime;
        boolean three=true;
        boolean two=true;
        boolean one=true;
        boolean getReady=true;
        public OnResumeTimer(long wholeTime, long timeBetweenTick,long recoveryTime){
            super(wholeTime,timeBetweenTick);
            timerSound.start();
            this.recoveryTime=recoveryTime;
        }
        @Override
        public void onTick(long millisUntilFinished) {
            state.timerSaveState=millisUntilFinished;
            state.recovery_time=recoveryTime;
            if(millisUntilFinished/1000==recoveryTime/2000&&getReady){
                if(currentTraining.exercises[currentExercise].name.equals("Recovery")) {
                    tts.speak("Get ready to " + currentTraining.exercises[currentExercise+1].name, TextToSpeech.QUEUE_FLUSH, null);
                }else{
                    tts.speak("Get ready to " + currentTraining.exercises[currentExercise].name, TextToSpeech.QUEUE_FLUSH, null);
                }
                getReady=false;
            }
            if (three&&millisUntilFinished/1000==3){
                tts.speak(String.valueOf(millisUntilFinished / 1000), TextToSpeech.QUEUE_FLUSH, null);
                three=false;
            }
            if (two&&millisUntilFinished/1000==2){
                tts.speak(String.valueOf(millisUntilFinished / 1000), TextToSpeech.QUEUE_FLUSH, null);
                two=false;
            }
            if (one&&millisUntilFinished/1000==1){
                tts.speak(String.valueOf(millisUntilFinished / 1000), TextToSpeech.QUEUE_FLUSH, null);
                one=false;
            }
            TrainingActivity.this.recoveryTime.setText(millisUntilFinished / 1000+"."+(millisUntilFinished / 100)%10);
        }

        @Override
        public void onFinish() {
            timerSound.pause();
            tts.speak("Go!", TextToSpeech.QUEUE_FLUSH, null);
            TrainingActivity.this.recoveryTime.setVisibility(View.GONE);
            video.setVisibility(View.VISIBLE);
            handler.sendEmptyMessage(RECOVERY_END);
        }
    }

    View.OnClickListener arrowClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.training_activity_arrow_left:
                    if(currentTraining.isx4){
                        if(currentSet==0){
                            if(currentExercise==0){return;}
                            changeExercisePlayersOff();
                            currentSet=3;
                            currentExercise--;
                            setExerciseName();
                            state.condition=true;
                            startExercise(currentTraining.exercises[currentExercise]);
                        }else{
                            changeExercisePlayersOff();
                            currentSet--;
                            state.condition=true;
                            startExercise(currentTraining.exercises[currentExercise]);
                        }
                    }else{
                        if(currentExercise==0){
                            return;
                        }else{
                            changeExercisePlayersOff();
                            currentExercise--;
                            setExerciseName();
                            state.condition=true;
                            trainingName.setText(currentTraining.name+"\n"+currentTraining.exercises[currentExercise].name);
                            startExercise(currentTraining.exercises[currentExercise]);
                        }
                    }
                    break;
                case R.id.training_activity_arrow_right:
                    if(currentTraining.isx4){
                        if(currentSet==3){
                            if(currentExercise >= currentTraining.exercises.length-1){
                                goToFinish();
                                return;
                            }
                            currentSet=0;
                            if(state.isRecovery==false) {
                                currentExercise++;
                            }
                            setExerciseName();
                            changeExercisePlayersOff();
                            state.condition=true;
                            startExercise(currentTraining.exercises[currentExercise]);
                        }else{
                            changeExercisePlayersOff();
                            if(state.isRecovery==false) {
                                currentSet++;
                            }
                            state.condition=true;
                            startExercise(currentTraining.exercises[currentExercise]);
                        }
                    }else{
                        if(currentExercise >= currentTraining.exercises.length-1){
                            goToFinish();
                            return;
                        }else{
                            changeExercisePlayersOff();
                            currentExercise++;
                            setExerciseName();
                            state.condition=true;
                            startExercise(currentTraining.exercises[currentExercise]);
                        }
                    }
                    break;
            }
        }
    };

    void changeExercisePlayersOff(){
        if(state.isRecovery){
            timerSound.pause();
            recoveryTime.setVisibility(View.GONE);
            video.setVisibility(View.VISIBLE);
            tts.stop();
            recoveryTimer.cancel();
        }else{
            musicStopAndPrepare();
            video.pause();
            tts.stop();
            mTimer.cancel();
        }
    }
    void setExerciseName(){
        trainingName.setText(currentTraining.name + " " + currentTraining.exercises[currentExercise].name);
        if (currentTraining.exercises.length == currentExercise + 1) {
            exerciseName.setText("Finish!");
        } else {
            exerciseName.setText("Next exercise: " + currentTraining.exercises[currentExercise + 1].name);
        }
    }

    MediaPlayer.OnCompletionListener musicRepeat=new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            mp.start();
        }
    };

    void musicStart(){
        if(isMusicOn)music.start();
    }

    void musicStopAndPrepare(){
        if(isMusicOn){
            music.stop();
            try {
                music.prepare();
            }catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    void musicPause(){
        if(isMusicOn)music.pause();
    }


    class MyCountDownTimerChangeHand extends CountDownTimer{
        Uri uriSecond;
        int amount;

        public MyCountDownTimerChangeHand(long wholeTime,long betweenTick,Uri uriSecond,int amount){
            super(wholeTime,betweenTick);
            this.uriSecond=uriSecond;
            this.amount=amount;
        }

        @Override
        public void onTick(long millisUntilFinished) {
            if(millisUntilFinished/1000==3){
                tts.speak("Change Hand", TextToSpeech.QUEUE_FLUSH, null);
            }
            if(millisUntilFinished/1000==1){
                tts.speak("Go", TextToSpeech.QUEUE_FLUSH, null);
            }
        }
        @Override
        public void onFinish() {
            video.setVideoURI(uriSecond);
            video.setOnCompletionListener(new MyOnCompletionListenerSecond(amount));
            video.start();
        }
    }
}
