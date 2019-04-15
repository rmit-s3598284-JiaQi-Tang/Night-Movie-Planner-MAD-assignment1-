package com.example.movienightplanner.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.movienightplanner.MoviesActivity;
import com.example.movienightplanner.R;
import com.example.movienightplanner.models.MovieImpl;

import java.util.List;

public class CustomListAdapter_MoviesActivityList extends ArrayAdapter {


    //to reference the Activity
    private MoviesActivity context;

    private List<MovieImpl> moviesList;

    public CustomListAdapter_MoviesActivityList(MoviesActivity context, List<MovieImpl> moviesList){

        super(context, R.layout.movie_listview_row, moviesList);

        this.context = context;

        this.moviesList = moviesList;

    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.movie_listview_row, null,true);

        //this code gets references to objects in the listview_row.xml file
        ImageView imageView = (ImageView) rowView.findViewById(R.id.imageView1ID);
        TextView name = (TextView) rowView.findViewById(R.id.nameTextViewID);
        TextView year = (TextView) rowView.findViewById(R.id.infoTextViewID);

        //this code sets the values of the objects to values from the list
        String imageName = moviesList.get(position).getPosterImageName().replace(".jpg", "").toLowerCase().trim();
        int resID = context.getResources().getIdentifier(imageName, "drawable", context.getPackageName());
        imageView.setImageResource(resID);
        name.setText(moviesList.get(position).getTittle());
        year.setText(moviesList.get(position).getYear());
        return rowView;

    }
}
