package com.example.movienightplanner.controllers.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.movienightplanner.R;
import com.example.movienightplanner.controllers.CalendarViewActivity;
import com.example.movienightplanner.models.DayBean;
import com.example.movienightplanner.models.EventImpl;
import com.example.movienightplanner.views.GridColumnViewHolder;

import java.util.List;

public class CustomListAdapter_DayGrids extends ArrayAdapter {

    //to reference the Activity
    private CalendarViewActivity context;

    private List<EventImpl> eventsList;

    List<DayBean> beans;

    public CustomListAdapter_DayGrids(CalendarViewActivity context, List<DayBean> beans) {

        super(context, R.layout.event_listview_row, beans);

        this.context = context;

        this.beans = beans;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        GridColumnViewHolder holder;
        LayoutInflater inflater = context.getLayoutInflater();
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_layout, null);
            holder = new GridColumnViewHolder();
            holder.day_text = (TextView) convertView.findViewById(R.id.day_text);
            convertView.setTag(holder);
        } else {
            holder = (GridColumnViewHolder) convertView.getTag();
        }
        holder.day_text.setText("" + beans.get(position).getDay());
        if (beans.get(position).getHasEvent()) {
            holder.day_text.setBackgroundColor(Color.RED);
        } else {
            holder.day_text.setBackgroundColor(Color.DKGRAY);
        }
        if (beans.get(position).getIsCurrentMonth()) {
            holder.day_text.setTextColor(Color.WHITE);
        } else {
            holder.day_text.setTextColor(Color.DKGRAY);
        }

        return convertView;
    }

}
