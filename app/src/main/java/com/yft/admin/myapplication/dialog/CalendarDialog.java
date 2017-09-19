package com.yft.admin.myapplication.dialog;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.yft.admin.myapplication.R;
import com.yft.admin.myapplication.classes.CompletedExercises;
import com.yft.admin.myapplication.classes.CompletedTrainings;

import java.util.ArrayList;

/**
 * Created by Admin on 26.04.2017.
 */

public class CalendarDialog extends DialogFragment {

    ArrayList<CompletedTrainings> completedTrainings;
    ListView lv;
    String[] listItems;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        completedTrainings=new ArrayList<>();
        completedTrainings=CompletedTrainings.getCompletedTrainingArrayFromJson(getTag());
        listItems=new String[completedTrainings.size()];
        for(int i=0;i<completedTrainings.size();i++){
            listItems[i]=completedTrainings.get(i).name;
        }
        getDialog().setTitle("Title!");
        View v = inflater.inflate(R.layout.calendar_dialog, null);
        lv=(ListView) v.findViewById(R.id.calendar_dialog_LV);
        lv.setAdapter(new MyArrayAdapter(getActivity(), listItems));

        return v;
    }

    public void onClick(View v) {
        dismiss();
    }

    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
    }

    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
    }

    ///////////////////////////////
    public class MyArrayAdapter extends ArrayAdapter<String> {
        private final Activity context;
        private final String[] names;
        View rowView;
        ViewHolder holder;

        public MyArrayAdapter(Activity context, String[] names) {
            super(context, R.layout.calendar_dialog_lv_row, names);
            this.context = context;
            this.names = names;
        }

        // ????? ??? ?????????? ?? ??????? ????? ? ??? ??????????? ???????
        // ?? ???????? ??????
        class ViewHolder {
            public TextView name;
            public TextView text;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // ViewHolder ???????????? ?????? ????????? ????? ??????? ????????

            ViewHolder holder = null;
            // ??????? ???????????? ??????, ???? ???????? ?????
            // ???????? ?????? ???? ??????? ?????? ??? ???? ??????? ???? ? ??? ??
            rowView = convertView;
            if (rowView == null) {
                holder = new MyArrayAdapter.ViewHolder();
                newView(position,holder);
            } else {
                if(((TextView)rowView.findViewById(R.id.calendar_dialog_LV_row_name)).getText().toString()!=names[position]){
                    holder = new MyArrayAdapter.ViewHolder();
                    newView(position, holder);
                }
                else {
                    holder = (ViewHolder) rowView.getTag();
                }
            }

            holder.name.setText(names[position]);

            return rowView;
        }

        void newView(int position,ViewHolder holder) {
            LayoutInflater inflater = context.getLayoutInflater();
            rowView = inflater.inflate(R.layout.calendar_dialog_lv_row, null, true);
            holder.name = (TextView) rowView.findViewById(R.id.calendar_dialog_LV_row_name);
            holder.text = (TextView) rowView.findViewById(R.id.calendar_dialog_LV_row_text);
            setText(holder.text, completedTrainings.get(position));
            rowView.setTag(holder);
        }
    }

    void setText(TextView tv,CompletedTrainings training){
        String str="";
        str+="\t\t\t" + "You did next exercises: \n";
        for (CompletedExercises temp:training.completedExercises) {
            str+="\t\t\t"+temp+"\n";
        }
        if(getLevel(training)!=""){
            str+="\t\t\t"+"Your training level was: "+getLevel(training)+"\n";
        }
        str+="\t\t\t"+"Total time: "+String.valueOf(training.totalTime/60000 + " minutes " + training.totalTime/1000 + " seconds.")+"\n";
        tv.setText(str);
    }

    String getLevel(CompletedTrainings trainings){
        switch (trainings.level){
            case -1:
                return "";
            case 0:
                return "Easy";
            case 1:
                return "Middle";
            case 2:
                return "Hard";
            default:
                return "";
        }
    }
}
