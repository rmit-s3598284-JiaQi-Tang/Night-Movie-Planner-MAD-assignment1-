package com.example.movienightplanner.controllers.adapter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

import com.example.movienightplanner.models.AppEngineImpl;
import com.example.movienightplanner.views.MainActivity;
import com.example.movienightplanner.views.MoviesActivity;

public class MovieListViewOnItemClickListener implements AdapterView.OnItemClickListener {

    public AppEngineImpl appEngine = AppEngineImpl.getSharedInstance();
    private Intent intent;

    public MovieListViewOnItemClickListener(Intent intent) {
        this.intent = intent;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
        //get the right event by event position parameter passed from last page
        int eventPosition = intent.getIntExtra("position", 1);
        appEngine.eventLists.get(eventPosition).setMovie(appEngine.moviesList.get(position));

        //show alert to tell the movie of the event has been changed
        AlertDialog.Builder builder1 = new AlertDialog.Builder(view.getContext());
        builder1.setMessage("Movie Changed to: " + "\"" + appEngine.moviesList.get(position).getTittle() + "\"");
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        Intent intent = new Intent(view.getContext(), MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        view.getContext().startActivity(intent);
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }
}
