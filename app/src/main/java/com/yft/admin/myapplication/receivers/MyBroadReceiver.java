package com.yft.admin.myapplication.receivers;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.Calendar;

public class MyBroadReceiver extends BroadcastReceiver {

    SharedPreferences sPref;
    Context context;
    AlarmManager alarmMgr;
    PendingIntent alarmIntent;
    public void onReceive(Context context, Intent intent) {
        this.context=context;
        sPref=context.getSharedPreferences("MyDateBase",context.MODE_PRIVATE);
        for(int i=1;i<8;i++){
            if(sPref.getInt("Day"+i,0)==1){
                setNotif(i);
            }
        }
    }

    private void setNotif(int day){
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
        setAlarm(day,id1,15,00,"second ");
        setAlarm(day,id2,19,00,"third ");
    }

    private void setAlarm(int day,int id,int hour,int minute,String text){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.DAY_OF_WEEK,day);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
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
}