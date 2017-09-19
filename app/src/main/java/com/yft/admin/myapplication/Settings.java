package com.yft.admin.myapplication;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Switch;

import com.yft.admin.myapplication.dialog.Activity_info_dialog;

public class Settings extends AppCompatActivity {

    Switch musicSwitch;
    CardView music;
    boolean isMusicOn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__settings);

        music=(CardView) findViewById(R.id.settings_music_control);
        musicSwitch=(Switch) findViewById(R.id.settings_music_switch);

        music.setOnClickListener(myClickListener);
        musicSwitch.setOnClickListener(myClickListener);

        SharedPreferences sPref = getSharedPreferences("MyDateBase",MODE_PRIVATE);
        isMusicOn=sPref.getBoolean("IsMusicOn",true);
        musicSwitch.setChecked(isMusicOn);
    }

    Activity_info_dialog dialog = new Activity_info_dialog();
    View.OnClickListener myClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.settings_music_switch:
                case R.id.settings_music_control:
                    changeSwitch();
                    break;
            }
        }
    };

    void changeSwitch(){
        isMusicOn=!isMusicOn;
        SharedPreferences sPref = getSharedPreferences("MyDateBase",MODE_PRIVATE);
        SharedPreferences.Editor editor=sPref.edit();
        editor.putBoolean("IsMusicOn",isMusicOn);
        editor.commit();
        musicSwitch.setChecked(isMusicOn);
    }
}
