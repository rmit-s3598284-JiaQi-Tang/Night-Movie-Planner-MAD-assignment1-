package com.example.movienightplanner.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.movienightplanner.R;
import com.example.movienightplanner.controllers.adapter.CustomListAdapter_DayGrids;
import com.example.movienightplanner.models.helper.CustomCalendarHelper;
import com.example.movienightplanner.models.AppEngineImpl;
import com.example.movienightplanner.models.DayBean;
import com.example.movienightplanner.models.EventImpl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class CalendarViewActivity extends AppCompatActivity {

    AppEngineImpl appEngine = AppEngineImpl.getSharedInstance();

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

        currentDate.setText(CustomCalendarHelper.long2str(CustomCalendarHelper.getCurrentTime()));

        //display dates of this month
        beans = new ArrayList<DayBean>();
        CustomCalendarHelper.setCalendarList(CustomCalendarHelper.getFirOfMonth(CustomCalendarHelper.getCurrentTime()), beans, calendarGrids, adapter, this);

        //show last month
        preMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomCalendarHelper.setCalendarList(CustomCalendarHelper.getFirOfMonth(CustomCalendarHelper.getLastMonth(CustomCalendarHelper.getCurrentTime(), -1)), beans, calendarGrids, adapter, CalendarViewActivity.this);
                currentDate.setText(CustomCalendarHelper.long2str(CustomCalendarHelper.getCurrentTime()));
            }
        });

        //show next month
        nextMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomCalendarHelper.setCalendarList(CustomCalendarHelper.getFirOfMonth(CustomCalendarHelper.getNextMonth(CustomCalendarHelper.getCurrentTime(), 1)), beans, calendarGrids, adapter, CalendarViewActivity.this);
                currentDate.setText(CustomCalendarHelper.long2str(CustomCalendarHelper.getCurrentTime()));
            }
        });

        //entry to the event
        calendarGrids.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (beans.get(position).getHasEvent()) {
                    int index = -1;
                    for (EventImpl event : appEngine.eventLists) {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                        if (sdf.format(event.getDateTime()).equals(sdf.format(beans.get(position).getCalendar().getTime()))) {
                            index = appEngine.eventLists.indexOf(event);
                        }
                    }

                    if (index >= 0) {
                        Intent intent = new Intent(CalendarViewActivity.this, EventDetailActivity.class);

                        //pass the position integer as parameter
                        intent.putExtra("position", index);
                        startActivity(intent);
                    }
                }

            }
        });

    }

}
