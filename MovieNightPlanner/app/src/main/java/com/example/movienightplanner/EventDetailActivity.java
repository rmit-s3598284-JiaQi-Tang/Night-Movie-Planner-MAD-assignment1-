package com.example.movienightplanner;

        import android.content.Intent;
        import android.database.Cursor;
        import android.provider.ContactsContract;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.support.v7.widget.Toolbar;
        import android.view.Menu;
        import android.view.MenuInflater;
        import android.view.MenuItem;
        import android.widget.ArrayAdapter;
        import android.widget.ImageView;
        import android.widget.ListView;
        import android.widget.TextView;
        import android.widget.Toast;

        import com.example.movienightplanner.models.AppEngineImpl;
        import com.example.movienightplanner.models.MovieImpl;

public class EventDetailActivity extends AppCompatActivity {

    int eventPosition;
    AppEngineImpl appEngine = AppEngineImpl.getSharedInstance();

    TextView venueText;
    TextView startDateText;
    TextView endDateText;

    ImageView movieImage;
    TextView movieNameText;
    TextView movieInfoText;

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);

        //toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(appEngine.eventLists.get(eventPosition).getTittle());
        setSupportActionBar(toolbar);

        //get the position integer parameter from MainActivity
        eventPosition = getIntent().getIntExtra("position", 1);

        venueText = (TextView) findViewById(R.id.venueID);
        venueText.setText("At: " + appEngine.eventLists.get(eventPosition).getVenue());

        startDateText = (TextView) findViewById(R.id.startDateID);
        startDateText.setText("Start Date: " + appEngine.eventLists.get(eventPosition).getStartDate());

        endDateText = (TextView) findViewById(R.id.endDateID);
        endDateText.setText("End Date: " + appEngine.eventLists.get(eventPosition).getEndDate());

        if(appEngine.eventLists.get(eventPosition).getMovie() != null) {

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
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, appEngine.eventLists.get(eventPosition).getAttendees());
        listView.setAdapter(adapter);


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

        switch(item.getItemId()){

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
    protected void onResume(){
        super.onResume();

    }

}
