package com.example.movienightplanner.controllers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.movienightplanner.R;
import com.example.movienightplanner.controllers.adapter.CustomListAdapter_DayGrids;
import com.example.movienightplanner.controllers.adapter.CustomListAdapter_MainActivityList;
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
        adapter = new CustomListAdapter_DayGrids(this, beans);
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

        //toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.calendarViewToolbar);
        toolbar.setTitle("Movie Night Calendar");
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_calendarview, menu);
        return true;
    }

    //define menu actions
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menuAddNewFromCalendar:
                startActivity(new Intent(CalendarViewActivity.this, AddEventActivity.class));
                Toast.makeText(this, "Please fill the details", Toast.LENGTH_SHORT).show();
                break;

            case R.id.menuViewList:
                startActivity(new Intent(CalendarViewActivity.this, MainActivity.class));
                Toast.makeText(this, "List View", Toast.LENGTH_SHORT).show();
                break;

        }
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        CustomCalendarHelper.setCalendarList(CustomCalendarHelper.getFirOfMonth(CustomCalendarHelper.getCurrentTime()), beans, calendarGrids, adapter, this);
        adapter.notifyDataSetChanged();
    }

}
