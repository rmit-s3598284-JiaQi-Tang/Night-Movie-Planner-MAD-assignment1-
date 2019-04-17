package com.example.movienightplanner.adapter;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.movienightplanner.R;
import com.example.movienightplanner.controllers.CalendarViewActivity;
import com.example.movienightplanner.controllers.MainActivity;
import com.example.movienightplanner.helper.CustomCalendarHelper;
import com.example.movienightplanner.models.DayBean;
import com.example.movienightplanner.models.EventImpl;

import java.util.Calendar;
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
        ViewHolder holder;
        LayoutInflater inflater = context.getLayoutInflater();
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_layout, null);
            holder = new ViewHolder();
            holder.day_text = (TextView) convertView.findViewById(R.id.day_text);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.day_text.setText("" + beans.get(position).getDay());
        if (beans.get(position).hasEvent()) {
            holder.day_text.setBackgroundColor(Color.RED);
        } else {
            holder.day_text.setBackgroundColor(Color.DKGRAY);
        }
        if (beans.get(position).isCurrentMonth()) {
            holder.day_text.setTextColor(Color.WHITE);
        } else {
            holder.day_text.setTextColor(Color.DKGRAY);
        }

        return convertView;
    }

    class ViewHolder {
        TextView day_text;
    }

}
