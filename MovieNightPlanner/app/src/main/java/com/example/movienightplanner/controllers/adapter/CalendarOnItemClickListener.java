package com.example.movienightplanner.controllers.adapter;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.movienightplanner.models.AppEngineImpl;
import com.example.movienightplanner.models.DayBean;
import com.example.movienightplanner.models.EventImpl;
import com.example.movienightplanner.views.CalendarViewActivity;
import com.example.movienightplanner.views.EventDetailActivity;

import java.text.SimpleDateFormat;
import java.util.List;

public class CalendarOnItemClickListener implements AdapterView.OnItemClickListener {

    public AppEngineImpl appEngine = AppEngineImpl.getSharedInstance();
    private List<DayBean> beans;

    public CalendarOnItemClickListener(List<DayBean> beans) {
        this.beans = beans;
    }

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
                Intent intent = new Intent(view.getContext(), EventDetailActivity.class);

                //pass the position integer as parameter
                intent.putExtra("position", index);
                view.getContext().startActivity(intent);
            }
        }

    }
}
