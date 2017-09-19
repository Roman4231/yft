package com.yft.admin.myapplication.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yft.admin.myapplication.R;
import com.yft.admin.myapplication.SetSchedule;
import com.yft.admin.myapplication.dialog.CalendarDialog;
import com.yft.admin.myapplication.classes.CompletedTrainings;
import com.yft.admin.myapplication.classes.WeekDays.TrainingDecorator;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.util.ArrayList;
import java.util.Calendar;

import static android.content.Context.MODE_PRIVATE;

public class Schedule extends android.support.v4.app.Fragment {


    SharedPreferences sPref;
    FloatingActionButton setTimetable;
    MaterialCalendarView calendarView;
    ArrayList<Days> days;
    ArrayList<CompletedTrainings> completedTrainings;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    View v;

    private OnFragmentInteractionListener mListener;

    public Schedule() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentSchedule.
     */
    // TODO: Rename and change types and number of parameters
    public static Schedule newInstance(String param1, String param2) {
        Schedule fragment = new Schedule();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v= inflater.inflate(R.layout.fragment_schedule, container, false);

        setTimetable = (FloatingActionButton) v.findViewById(R.id.floatingActionButtonSetTimetable);
        calendarView = (MaterialCalendarView) v.findViewById(R.id.calendarView);
        calendarView.setPagingEnabled(false);
        calendarView.state().edit()
                .setFirstDayOfWeek(Calendar.MONDAY)
                .setMinimumDate(CalendarDay.from(2017, 1, 1))
                .setMaximumDate(CalendarDay.from(2050, 12, 31))
                .setCalendarDisplayMode(CalendarMode.MONTHS)
                .commit();

        calendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                int year=date.getYear();
                int month=date.getMonth();
                int day=date.getDay();
                if(getCompletedTrainingsByDay(year,month,day)!=null){
                    DialogFragment dialog =new CalendarDialog();
                    dialog.show(getFragmentManager(),
                            CompletedTrainings.getStringFromCompletedTrainingArray(getCompletedTrainingsByDay(year,month,day)));
                }
            }
        });
        setTimetable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.floatingActionButtonSetTimetable:
                        startActivity(new Intent(getActivity(), SetSchedule.class));
                        break;
                }

            }
        });


        return v;
    }
    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
      /*  if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }*/
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onResume() {
        days=new ArrayList<>();
        completedTrainings=new ArrayList<>();
        getCompletedTrainings();
        calendarView.removeDecorators();
        calendarView.addDecorator(new TrainingDecorator(getContext(),getCalendarDayFromDay(days)));


        super.onResume();
    }

    void getCompletedTrainings(){
        SharedPreferences sPref = getActivity().getSharedPreferences("MyDateBase", MODE_PRIVATE);
        int n= sPref.getInt("NumberOfCompletedTrainings",0);
        for(int i=0;i<n;i++){
            completedTrainings.add(CompletedTrainings.getCompletedTrainingFromJson(sPref.getString("CompletedTraining"+i,"")));
        }
        setDays();
    }

    private void setDays() {
        CalendarDay temp;
        for(int i=0;i<completedTrainings.size();i++){
            temp=CalendarDay.from(completedTrainings.get(i).year,completedTrainings.get(i).month,completedTrainings.get(i).day);
            int var=existDay(temp);
            if(var==-1){
                days.add(new Days(temp,1));
            }else{
                days.set(var,new Days(days.get(var).day,days.get(var).amountOfTrainings));
            }
        }
    }

    private int existDay(CalendarDay temp) {
        for(int i=0;i<days.size();i++){
            if(days.get(i).day.equals(temp)){
                return i;
            }
        }
        return -1;
    }

    private class Days{
        CalendarDay day;
        int amountOfTrainings;

        public Days(CalendarDay day,int amountOfTrainings){
            this.day=day;
            this.amountOfTrainings=amountOfTrainings;
        }
    }

    ArrayList<CompletedTrainings> getCompletedTrainingsByDay(int year,int month,int day){
        ArrayList<CompletedTrainings> result=new ArrayList<>();
        for(int i=0;i<completedTrainings.size();i++){
            if((completedTrainings.get(i).year==year)&&
                    (completedTrainings.get(i).month==month)&&
                    (completedTrainings.get(i).day==day)){
                result.add(completedTrainings.get(i));
            }
        }
        if(result.size()!=0) return result;
        return null;
    }




    ArrayList<CalendarDay> getCalendarDayFromDay(ArrayList<Days> arrayList){
        ArrayList<CalendarDay> result=new ArrayList<>();
        for (Days temp:arrayList) {
            result.add(temp.day);
        }
        return result;
    }

}
