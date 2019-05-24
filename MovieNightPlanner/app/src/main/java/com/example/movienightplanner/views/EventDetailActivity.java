package com.example.movienightplanner.views;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.movienightplanner.R;
import com.example.movienightplanner.controllers.adapter.EventDetailListViewOnItemClickListener;
import com.example.movienightplanner.database.DBHelper;
import com.example.movienightplanner.models.AppEngineImpl;

public class EventDetailActivity extends AppCompatActivity {

    private int eventPosition;
    private ArrayAdapter<String> adapter;
    public AppEngineImpl appEngine = AppEngineImpl.getSharedInstance();

    private TextView eventTittleText;
    private TextView venueText;
    private TextView startDateText;
    private TextView endDateText;

    private ImageView movieImage;
    private TextView movieNameText;
    private TextView movieInfoText;

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);

        //toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //get the position integer parameter from MainActivity
        eventPosition = getIntent().getIntExtra("position", 1);

        eventTittleText = (TextView) findViewById(R.id.eventTittleID) ;
        eventTittleText.setText(appEngine.eventLists.get(eventPosition).getTittle());

        venueText = (TextView) findViewById(R.id.venueID);
        venueText.setText("At: " + appEngine.eventLists.get(eventPosition).getVenue());

        startDateText = (TextView) findViewById(R.id.startDateID);
        startDateText.setText("Start Date: " + appEngine.eventLists.get(eventPosition).getStartDate());

        endDateText = (TextView) findViewById(R.id.endDateID);
        endDateText.setText("End Date: " + appEngine.eventLists.get(eventPosition).getEndDate());

        if (appEngine.eventLists.get(eventPosition).getMovie() != null) {

            movieImage = (ImageView) findViewById(R.id.movieImageView1ID);
            String imageName = appEngine.eventLists.get(eventPosition).getMovie().getPosterImageName().replace(".jpg", "").toLowerCase().trim();
            int resID = getResources().getIdentifier(imageName, "drawable", getPackageName());
            movieImage.setImageResource(resID);

            movieNameText = (TextView) findViewById(R.id.movieNameTextViewID);
            movieNameText.setText(appEngine.eventLists.get(eventPosition).getMovie().getTittle());

            movieInfoText = (TextView) findViewById(R.id.movieInfoTextViewID);
            movieInfoText.setText(appEngine.eventLists.get(eventPosition).getMovie().getYear());

        }

        this.listView = (ListView) findViewById(R.id.attentessListID);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, appEngine.eventLists.get(eventPosition).getAttendees());
        listView.setAdapter(adapter);

        //event handling code is in a separate class called EventListViewOnItemClickListener
        listView.setOnItemClickListener(new EventDetailListViewOnItemClickListener(eventPosition, adapter));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_eventdetail, menu);
        return true;
    }

    //define menu actions
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.addOrChangeMovie:

                Intent intent1 = new Intent(EventDetailActivity.this, MoviesActivity.class);

                //pass the position integer as parameter
                intent1.putExtra("position", eventPosition);
                startActivity(intent1);
                Toast.makeText(this, "Select A Movie", Toast.LENGTH_SHORT).show();
                break;

            case R.id.addAttendees:
                Intent intent3 = new Intent(EventDetailActivity.this, ContactsActivity.class);

                //pass the position integer as parameter
                intent3.putExtra("position", eventPosition);
                startActivity(intent3);
                Toast.makeText(this, "Add Attendees", Toast.LENGTH_SHORT).show();
                break;

            case R.id.menuEdit:

                Intent intent2 = new Intent(EventDetailActivity.this, EditEventActivity.class);

                //pass the position integer as parameter
                intent2.putExtra("position", eventPosition);
                startActivity(intent2);
                Toast.makeText(this, "Edit Event", Toast.LENGTH_SHORT).show();
                break;

            case R.id.menuDelete:

                //delete from database
                DBHelper dbHelper = new DBHelper(this);
                dbHelper.deleteEvent(Integer.parseInt(appEngine.eventLists.get(eventPosition).getId()));
                //delete the event by that position
                appEngine.eventLists.remove(eventPosition);
                onBackPressed();
                Toast.makeText(this, "Event Was Deleted", Toast.LENGTH_SHORT).show();
                break;

        }
        return true;
    }

    //to update the display
    @Override
    protected void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }

}
