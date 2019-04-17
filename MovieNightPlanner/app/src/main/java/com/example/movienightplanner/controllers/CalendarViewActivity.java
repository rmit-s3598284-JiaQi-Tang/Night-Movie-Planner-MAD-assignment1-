package com.example.movienightplanner.controllers;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.movienightplanner.R;
import com.example.movienightplanner.adapter.CustomListAdapter_DayGrids;
import com.example.movienightplanner.adapter.CustomListAdapter_MoviesActivityList;
import com.example.movienightplanner.helper.CustomCalendarHelper;
import com.example.movienightplanner.models.DayBean;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CalendarViewActivity extends AppCompatActivity {

    ImageView preMonth;
    ImageView nextMonth;
    TextView currentDate;
    GridView calendarGrids;
    List<DayBean> beans;
    CustomListAdapter_DayGrids adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_view);

        preMonth = findViewById(R.id.previous_month);
        nextMonth = findViewById(R.id.next_month);
        currentDate = findViewById(R.id.display_current_date);
        calendarGrids = findViewById(R.id.calendar_grid);

        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM yyyy");
        currentDate.setText(dateFormat.format(date));

        //display dates of this month
        beans = new ArrayList<DayBean>();
        CustomCalendarHelper.setCalendarList(CustomCalendarHelper.getCurrentTime(), beans, calendarGrids, this);
        adapter = new CustomListAdapter_DayGrids(this, beans);
        calendarGrids.setAdapter(adapter);

        //show last month
        preMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomCalendarHelper.setCalendarList(CustomCalendarHelper.getLastMonth(CustomCalendarHelper.getCurrentTime()), beans,calendarGrids,CalendarViewActivity.this);
                currentDate.setText(CustomCalendarHelper.long2str(CustomCalendarHelper.getCurrentTime()));
                adapter.notifyDataSetChanged();
            }
        });

        //show next month
        nextMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomCalendarHelper.setCalendarList(CustomCalendarHelper.getNextMonth(CustomCalendarHelper.getCurrentTime()), beans,calendarGrids,CalendarViewActivity.this);
                currentDate.setText(CustomCalendarHelper.long2str(CustomCalendarHelper.getCurrentTime()));
                adapter.notifyDataSetChanged();
            }
        });

    }

}
