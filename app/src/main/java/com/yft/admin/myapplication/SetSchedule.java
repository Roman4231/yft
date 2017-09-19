package com.yft.admin.myapplication;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.yft.admin.myapplication.receivers.MyReceiver;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class SetSchedule extends AppCompatActivity {
    SharedPreferences sPref;
    AlarmManager alarmMgr;
    PendingIntent alarmIntent;

    private ArrayList<String> weekdays ;
    private ListView timeTableLv;
    int MONDAY=0;
    int TUESDAY=1;
    int WEDNESDAY=2;
    int THURSDAY=3;
    int FRIDAY=4;
    int SATURDAY=5;
    int SUNDAY=6;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_schedule);


        sPref = getSharedPreferences("MyDateBase",MODE_PRIVATE);
        weekdays= new ArrayList<>();
        weekdays.add("Monday");
        weekdays.add("Tuesday");
        weekdays.add("Wednesday");
        weekdays.add("Thursday");
        weekdays.add("Friday");
        weekdays.add("Saturday");
        weekdays.add("Sunday");


        final Animation animationFlipIn = AnimationUtils.loadAnimation(this,
                R.anim.slide_in);
        animationFlipIn.setFillAfter(true);
        class MyAnimationListener implements Animation.AnimationListener{
            CardView cv;
            int position;
            int imgRes;

            public MyAnimationListener(CardView cv,int position){
                this.cv=cv;
                this.position=position;
            }
            @Override
            public void onAnimationStart(Animation animation) {
                if(sPref.getInt("Day"+position,0)==1){
                    imgRes=R.drawable.ic_set_schedule_minus;
                }else{
                    imgRes=R.drawable.ic_set_schedule_plus;
                }
                changeCheck(position,cv);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                ((ImageView)cv.findViewById(R.id.set_schedule_row_check)).setImageResource(imgRes);
                cv.startAnimation(animationFlipIn);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        }




        timeTableLv = (ListView) findViewById(R.id.timeTableLv);
        timeTableLv.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        timeTableLv.setAdapter(new MyAdapter(this,weekdays));


        timeTableLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                CardView v=(CardView) arg1.findViewById(R.id.set_schedule_row_card_view_check);
                Animation animationFlipOut = AnimationUtils.loadAnimation(SetSchedule.this,
                        R.anim.slide_out);
                animationFlipOut.setFillAfter(true);
                animationFlipOut.setAnimationListener(new MyAnimationListener(v,arg2));
                v.startAnimation(animationFlipOut);
            }
        });

    }

    void changeCheck(int pos,CardView v){
        sPref = getSharedPreferences("MyDateBase",MODE_PRIVATE);
        SharedPreferences.Editor editor=sPref.edit();
        if(sPref.getInt("Day"+(pos),-1) != 1){
            editor.putInt("Day"+(pos), 1);
            setNotif(pos);
        }
        else{
            editor.putInt("Day"+(pos), 0);
            cancelNotif(pos);
        }
        editor.commit();
    }

    private void setNotif(int day){
        day++;
        if(day==7){
            day=1;
        }
        else{
            day++;
        }
        int id0=(day-1)*3;
        int id1=(day-1)*3+1;
        int id2=(day-1)*3+2;
        setAlarm(day,id0,11,0,"first ");
        setAlarm(day,id1,15,0,"second ");
        setAlarm(day,id2,19,0,"third ");
    }

    private void cancelNotif(int day){
        day++;
        if(day==7){
            day=1;
        }
        else{
            day++;
        }
        int id0=(day-1)*3;
        int id1=(day-1)*3+1;
        int id2=(day-1)*3+2;
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 11);
        calendar.set(Calendar.MINUTE, 0);
        Context context = getApplicationContext();
        calendar.set(Calendar.DAY_OF_WEEK,day);
        alarmMgr = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent1 = new Intent(context, MyReceiver.class);
        alarmIntent = PendingIntent.getBroadcast(context, id0, intent1, 0);
        alarmMgr.cancel(alarmIntent);

        calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 17);
        calendar.set(Calendar.MINUTE,7);
        context = getApplicationContext();
        calendar.set(Calendar.DAY_OF_WEEK,day);
        alarmMgr = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        intent1 = new Intent(context, MyReceiver.class);
        alarmIntent = PendingIntent.getBroadcast(context, id1, intent1, 0);
        alarmMgr.cancel(alarmIntent);

        calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 19);
        calendar.set(Calendar.MINUTE, 0);
        context = getApplicationContext();
        calendar.set(Calendar.DAY_OF_WEEK,day);
        alarmMgr = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        intent1 = new Intent(context, MyReceiver.class);
        alarmIntent = PendingIntent.getBroadcast(context, id2, intent1, 0);
        alarmMgr.cancel(alarmIntent);
    }
    private void setAlarm(int day,int id,int hour,int minute,String text){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.DAY_OF_WEEK,day);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        Context context = getApplicationContext();
        if(calendar.getTimeInMillis()<System.currentTimeMillis()){
            calendar.add(Calendar.DATE,7);
        }
        alarmMgr = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent1 = new Intent(context, MyReceiver.class);
        intent1.putExtra("message",text+"Day"+(day-1));
        alarmIntent = PendingIntent.getBroadcast(context, id, intent1, 0);
        alarmMgr.setInexactRepeating(AlarmManager.RTC, calendar.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY*7, alarmIntent);
    }




    /////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////
    public class MyAdapter extends ArrayAdapter<String> {
        private final Activity context;
        private final List<String> names;
        View rowView;
        ViewHolder holder;

        public MyAdapter(Activity context, ArrayList<String> names) {
            super(context, R.layout.set_schedule_row, names);
            this.context = context;
            this.names = names;
        }

        // Класс для сохранения во внешний класс и для ограничения доступа
        // из потомков класса
        class ViewHolder {
            public TextView name;
            public ImageView check;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // ViewHolder буферизирует оценку различных полей шаблона элемента

           holder = null;
            // Очищает сущетсвующий шаблон, если параметр задан
            // Работает только если базовый шаблон для всех классов один и тот же
            rowView = convertView;
            if (rowView == null) {
                holder = new MyAdapter.ViewHolder();
                newView(position);
            } else {
                if(((TextView)rowView.findViewById(R.id.set_schedule_row_day)).getText().toString()!=names.get(position)){
                    holder = new MyAdapter.ViewHolder();
                    newView(position);
                }
                else {
                    holder = (ViewHolder) rowView.getTag();
                }
            }

            holder.name.setText(names.get(position));

            return rowView;
        }

        void newView(final int position) {
            LayoutInflater inflater = context.getLayoutInflater();
            rowView = inflater.inflate(R.layout.set_schedule_row, null, true);
            holder.name = (TextView) rowView.findViewById(R.id.set_schedule_row_day);
            holder.check = (ImageView) rowView.findViewById(R.id.set_schedule_row_check);
            sPref = getSharedPreferences("MyDateBase",MODE_PRIVATE);
            if(sPref.getInt("Day"+position,0)==1){
                holder.check.setImageResource(R.drawable.ic_set_schedule_plus);
            }
            rowView.setTag(holder);
        }
    }
}
