package com.example.movienightplanner;

        import android.content.Intent;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.support.v7.widget.Toolbar;
        import android.view.Menu;
        import android.view.MenuInflater;
        import android.view.MenuItem;
        import android.widget.TextView;
        import android.widget.Toast;

        import com.example.movienightplanner.models.AppEngineImpl;

public class EventDetailActivity extends AppCompatActivity {

    int eventPosition;
    AppEngineImpl appEngine = AppEngineImpl.getSharedInstance();

    TextView tittleText;
    TextView venueText;
    TextView startDateText;
    TextView endDateText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);

        //toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Event");
        setSupportActionBar(toolbar);

        //get the position integer parameter from MainActivity
        eventPosition = getIntent().getIntExtra("position", 1);

        tittleText = (TextView) findViewById(R.id.tittleID);
        tittleText.setText(appEngine.eventLists.get(eventPosition).getTittle());

        venueText = (TextView) findViewById(R.id.venueID);
        venueText.setText("At: " + appEngine.eventLists.get(eventPosition).getVenue());

        startDateText = (TextView) findViewById(R.id.startDateID);
        startDateText.setText("Start Date: " + appEngine.eventLists.get(eventPosition).getStartDate());

        endDateText = (TextView) findViewById(R.id.endDateID);
        endDateText.setText("End Date: " + appEngine.eventLists.get(eventPosition).getEndDate());
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
            case R.id.menuEdit:

                Intent intent = new Intent(EventDetailActivity.this, EditEventActivity.class);

                //pass the position integer as parameter
                intent.putExtra("position", eventPosition);
                startActivity(intent);
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
