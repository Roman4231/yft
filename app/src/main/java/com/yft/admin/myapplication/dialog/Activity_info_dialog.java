package com.yft.admin.myapplication.dialog;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.yft.admin.myapplication.R;

/**
 * Created by Winchester on 25.05.2017.
 */

public class Activity_info_dialog extends DialogFragment {

    String activity_name;
    TextView title;
    TextView info;
    View v;
    Button ok;
    CheckBox show;
    SharedPreferences sPref;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        activity_name=(this.getTag());
        v = inflater.inflate(R.layout.dialog_activity_info, null);

        sPref = getActivity().getSharedPreferences("MyDateBase", getActivity().MODE_PRIVATE);
        title = (TextView)  v.findViewById(R.id.activity_info_title);
        info =  (TextView)  v.findViewById(R.id.activity_info);
        ok =    (Button)    v.findViewById(R.id.ok_button_dialog_info);
        show =  (CheckBox)  v.findViewById(R.id.dontShow);

        title.setText(R.string.activity_dialog_info_title);

        switch (activity_name){
            case "info":
                info.setText(R.string.activity_dialog_info);
                break;
        }



        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        return v;
    }



    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        SharedPreferences.Editor editor=sPref.edit();
        if(show.isChecked()){
            editor.putInt("dontShowDialogAgain", 1);
        }
        else {
            editor.putInt("dontShowDialogAgain", 0);
        }
        editor.commit();
    }

    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
        SharedPreferences.Editor editor=sPref.edit();
        if(show.isChecked()){
            editor.putInt("dontShowDialogAgain", 1);
        }
        else {
            editor.putInt("dontShowDialogAgain", 0);
        }
        editor.commit();
    }
}


