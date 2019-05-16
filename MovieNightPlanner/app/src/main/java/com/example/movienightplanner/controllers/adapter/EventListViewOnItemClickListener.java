package com.example.movienightplanner.controllers.adapter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;

import com.example.movienightplanner.views.EventDetailActivity;
import com.example.movienightplanner.views.MainActivity;

public class EventListViewOnItemClickListener implements AdapterView.OnItemClickListener {

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(view.getContext(), EventDetailActivity.class);

        //pass the position integer as parameter
        intent.putExtra("position", position);
        view.getContext().startActivity(intent);
    }
}
