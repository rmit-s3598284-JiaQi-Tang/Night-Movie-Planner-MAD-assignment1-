package com.example.movienightplanner.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.movienightplanner.MainActivity;
import com.example.movienightplanner.R;
import com.example.movienightplanner.models.EventImpl;

import java.util.List;

public class CustomListAdapter_MainActivityList extends ArrayAdapter {

    //to reference the Activity
    private MainActivity context;

    private List<EventImpl> eventsList;

    public CustomListAdapter_MainActivityList(MainActivity context, List<EventImpl> eventsList){

        super(context, R.layout.event_listview_row, eventsList);

        this.context = context;

        this.eventsList = eventsList;

    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.event_listview_row, null,true);

        //this code gets references to objects in the listview_row.xml file
        TextView nameTextField = (TextView) rowView.findViewById(R.id.tittleTextViewID);
        TextView contentTextField = (TextView) rowView.findViewById(R.id.contentTextViewID);
        TextView contentTextField2 = (TextView) rowView.findViewById(R.id.contentTextViewID2);
        //this code sets the values of the objects to values from the list
        nameTextField.setText(eventsList.get(position).getTittle());
        contentTextField.setText(eventsList.get(position).getVenue());
        contentTextField2.setText(eventsList.get(position).getStartDate() + " - " + eventsList.get(position).getEndDate());
        return rowView;

    }
}
