package com.yft.admin.myapplication.dialog;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yft.admin.myapplication.R;
import com.yft.admin.myapplication.TrainingDetails;

/**
 * Created by Admin on 09.05.2017.
 */

public class Training_details_dialog  extends DialogFragment{

    int level;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        level=Integer.parseInt(Training_details_dialog.this.getTag());
        getDialog().setTitle("Title!");
        View v = inflater.inflate(R.layout.training_details_dialog, null);
        v.findViewById(R.id.training_details_dialog_yes).setOnClickListener(myOnClickListener);
        v.findViewById(R.id.training_details_dialog_no).setOnClickListener(myOnClickListener);
        return v;
    }

    View.OnClickListener myOnClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.training_details_dialog_yes:
                    ((TrainingDetails)getActivity()).setDialogResult(level);
                    Training_details_dialog.this.dismiss();
                    break;
                case R.id.training_details_dialog_no:
                    Training_details_dialog.this.dismiss();
                    break;
            }
        }
    };

    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
    }

    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
    }
}