package com.example.movienightplanner.views;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.example.movienightplanner.R;
import com.example.movienightplanner.api.APIManager;
import com.example.movienightplanner.controllers.adapter.CustomListAdapter_MainActivityList;
import com.example.movienightplanner.controllers.adapter.EventListViewOnItemClickListener;
import com.example.movienightplanner.models.AppEngineImpl;


public class MainActivity extends AppCompatActivity implements LocationListener {

    public AppEngineImpl appEngine = AppEngineImpl.getSharedInstance();
    private ListView listView;
    private CustomListAdapter_MainActivityList listAdapter;
    public static LocationManager locationManager;
    public static String provider;
    //    private Location currentLocation;
    private String duration;
    private static Context myContext;

    public static Context getMyContext() {
        return myContext;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myContext = getApplicationContext();

        //initialise read txt files, but we only read once
        if (!appEngine.getDataRead()) {
            appEngine.startUp(this);
        }

        //toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Movie Night Planner");
        setSupportActionBar(toolbar);

        listAdapter = new CustomListAdapter_MainActivityList(this, appEngine.eventLists);
        listView = (ListView) findViewById(R.id.listView);
        listAdapter.notifyDataSetChanged();
        listView.setAdapter(listAdapter);

        //event handling code is in a separate class called EventListViewOnItemClickListener
        listView.setOnItemClickListener(new EventListViewOnItemClickListener());

        initialLocationManager();

//        while (true) {
//            try {
//                Thread.sleep(1);
//                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                    return;
//                }
//                System.out.println("Latitude:" + locationManager.getLastKnownLocation(provider).getLatitude() + ", Longitude:" + locationManager.getLastKnownLocation(provider).getLongitude());
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
    }

    Runnable networkTask = new Runnable() {

        @Override
        public void run() {
            if (ActivityCompat.checkSelfPermission(getMyContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // Check Permissions Now
            } else {
                duration = APIManager.getDuration(getApplicationContext(), locationManager.getLastKnownLocation(provider), -37.811363, 144.936967);
                System.out.println(duration);
            }
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    //define menu actions
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menuAddNew:
                startActivity(new Intent(MainActivity.this, AddEventActivity.class));
                Toast.makeText(this, "Please fill the details", Toast.LENGTH_SHORT).show();
                break;

            case R.id.menuViewCalendar:
                startActivity(new Intent(MainActivity.this, CalendarViewActivity.class));
                Toast.makeText(this, "Calendar View", Toast.LENGTH_SHORT).show();
                break;

            case R.id.menuViewMap:
                startActivity(new Intent(MainActivity.this, MapsActivity.class));
                Toast.makeText(this, "Map View", Toast.LENGTH_SHORT).show();
                break;

            case R.id.menuAscending:

                appEngine.ascendEvents();

                listAdapter = new CustomListAdapter_MainActivityList(this, appEngine.eventLists);
                listAdapter.notifyDataSetChanged();
                listView.setAdapter(listAdapter);

                Toast.makeText(this, "Ascending Events", Toast.LENGTH_SHORT).show();
                break;

            case R.id.menuDescending:

                appEngine.descendEvents();

                listAdapter = new CustomListAdapter_MainActivityList(this, appEngine.eventLists);
                listAdapter.notifyDataSetChanged();
                listView.setAdapter(listAdapter);

                Toast.makeText(this, "Descending Events", Toast.LENGTH_SHORT).show();
                break;

        }
        return true;
    }

    //to update the listview
    @Override
    protected void onResume() {
        super.onResume();
        listAdapter.notifyDataSetChanged();

    }

    public void statusCheck() {
        final LocationManager manager = (LocationManager) getSystemService(
                Context.LOCATION_SERVICE);

        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps();

        }
    }

    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(
                "Your GPS seems to be disabled, do you want to enable it?")
                .setCancelable(false).setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog,
                                        final int id) {
                        startActivity(new Intent(
                                android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog,
                                        final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void onLocationChanged(Location location) {

        showAlert("Latitude:" + location.getLatitude() + ", Longitude:" + location.getLongitude());
        new Thread(networkTask).start();


    }

    public void initialLocationManager() {
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Check Permissions Now
            ActivityCompat.requestPermissions(this,
                    new String[] { Manifest.permission.ACCESS_FINE_LOCATION },
                    0);
        }
        // Getting LocationManager object
        statusCheck();

        locationManager = (LocationManager) getSystemService(
                Context.LOCATION_SERVICE);

        // Creating an empty criteria object
        Criteria criteria = new Criteria();

        // Getting the name of the provider that meets the criteria
        provider = locationManager.getBestProvider(criteria, false);

        if (provider != null && !provider.equals("")) {
            if (!provider.contains("gps")) { // if gps is disabled
                final Intent poke = new Intent();
                poke.setClassName("com.android.settings",
                        "com.android.settings.widget.SettingsAppWidgetProvider");
                poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
                poke.setData(Uri.parse("3"));
                sendBroadcast(poke);
            }
            // Get the location from the given provider
            Location location = locationManager
                    .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,500,0, (LocationListener) this);
            if (location != null) {
                onLocationChanged(location);
            }
            else {
                location = locationManager.getLastKnownLocation(provider);
            }
            if (location != null) {
                onLocationChanged(location);
            }
            else {
                Toast.makeText(getBaseContext(), "Location can't be retrieved",
                        Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getBaseContext(), "No Provider Found",
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onProviderDisabled(String provider) {

        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Check Permissions Now
            ActivityCompat.requestPermissions(this,
                    new String[] { Manifest.permission.ACCESS_FINE_LOCATION },
                    0);
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    private void showAlert(String message) {
        android.app.AlertDialog.Builder builder1 = new android.app.AlertDialog.Builder(this);
        builder1.setMessage(message);
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        android.app.AlertDialog alert11 = builder1.create();
        alert11.show();
    }

}
