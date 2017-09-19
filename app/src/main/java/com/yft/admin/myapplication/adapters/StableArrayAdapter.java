/*
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.yft.admin.myapplication.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yft.admin.myapplication.CreateCustomTraining;
import com.yft.admin.myapplication.R;
import com.yft.admin.myapplication.classes.ExerciseClass;

import java.util.HashMap;
import java.util.List;

public class StableArrayAdapter extends ArrayAdapter<String> {

    final int INVALID_ID = -1;
    HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();
    ViewHolder holder;
    List<String> objects;
    Activity context;
    View rowView;

    public StableArrayAdapter(Activity context, int row, List<String> objects) {
        super(context, row, R.id.create_custom_training_row_name, objects);
        this.context=context;
        this.objects=objects;
        for (int i = 0; i < objects.size(); ++i) {
            mIdMap.put(objects.get(i), i);
        }
    }

    public void addElement(String str){
        //this.add(str);
        mIdMap.put(str,mIdMap.size());
    }

    public void removeElement(String str,List<String> objects){
        int n=mIdMap.get(str);
        mIdMap.remove(str);
        for (int i = n; i <mIdMap.size()-n; ++i) {
            mIdMap.put(objects.get(i), i);
        }
    }

    public long getItemId(int position) {
        if (position < 0 || position >= mIdMap.size()) {
            return INVALID_ID;
        }
        String item = getItem(position);
        return mIdMap.get(item);
    }

    class ViewHolder {
        public Button cancelBtn;
        public TextView name;
        public TextView amount;
    }

    public View getView(final int position, final View convertView, final ViewGroup parent){
// ViewHolder буферизирует оценку различных полей шаблона элемента\



// Очищает сущетсвующий шаблон, если параметр задан
// Работает только если базовый шаблон для всех классов один и тот же
            rowView = convertView;

            if (rowView == null) {
                newView(position);
            } else {
                holder = (ViewHolder) rowView.getTag();
                if(holder.name.getText().toString()!=objects.get(position)){
                    newView(position);
                }
            }
            String str=objects.get(position)+" x4";
            holder.name.setText(str);
            return rowView;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }


    private void newView(final int position){
        holder =new StableArrayAdapter.ViewHolder();
        LayoutInflater inflater = context.getLayoutInflater();
        rowView = inflater.inflate(R.layout.row, null, true);
        holder.cancelBtn=(Button) rowView.findViewById(R.id.create_custom_training_row_cancel);
        holder.name = (TextView) rowView.findViewById(R.id.create_custom_training_row_name);
        holder.amount = (TextView) rowView.findViewById(R.id.create_custom_training_row_amount);

        for(int i=0;i<((CreateCustomTraining)context).selectedExercises.size();i++){
            String str=((CreateCustomTraining)context).selectedExercises.get(i).exerciseName.replaceAll(" x4","");
            if(str.equals(objects.get(position))){
                String temp=String.valueOf(((CreateCustomTraining)context).selectedExercises.get(i).reps);
                holder.amount.setText(temp);
            }
        }
        holder.cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=((TextView)((LinearLayout)v.getParent()).findViewById(R.id.create_custom_training_row_name)).getText().toString();
                name=name.replaceAll(" x4","");
                ((CreateCustomTraining) context).listItems.remove(name);
                removeElement(name,objects);
                notifyDataSetChanged();
                if(objects.size()==0){
                    context.findViewById(R.id.create_custom_training_drop_it_here).setVisibility(View.VISIBLE);
                }

                for(int i=0;i<((CreateCustomTraining) context).selectedExercises.size();i++) {
                    String str = ((CreateCustomTraining) context).selectedExercises.get(i).exerciseName;
                    if (str ==name) {
                        ((CreateCustomTraining) context).selectedExercises.remove(i);
                    }
                }
                setVisibility(name);
            }
        });

        rowView.setTag(holder);
    }

    private void setVisibility(String name){
        for(int i=0;i< ExerciseClass.getAmountOfExercises();i++){
            String str=ExerciseClass.getAllExercisesNames()[i].replaceAll("\n"," ");
            if(name.equals(str)){
                ((CreateCustomTraining)context).myTVList[i].setVisibility(View.VISIBLE);
                break;
            }
        }
    }
}



