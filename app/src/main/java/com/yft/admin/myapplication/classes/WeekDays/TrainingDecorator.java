package com.yft.admin.myapplication.classes.WeekDays;

import android.content.Context;

import com.yft.admin.myapplication.R;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;

import java.util.Collection;
import java.util.HashSet;

/**
 * Created by Admin on 25.04.2017.
 */

public class TrainingDecorator implements DayViewDecorator {

    private int color=0;
    private final HashSet<CalendarDay> dates;
    Context context;

    public TrainingDecorator(int color, Collection<CalendarDay> dates) {
        this.color = color;
        this.dates = new HashSet<>(dates);
    }

    public TrainingDecorator(Context context, Collection<CalendarDay> dates) {
        this.context=context;
        this.dates = new HashSet<>(dates);
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return dates.contains(day);
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.ic_schedule_calendar_day_background));
    }
}
