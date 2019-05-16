package com.example.movienightplanner.views;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.movienightplanner.R;
import com.example.movienightplanner.models.AppEngineImpl;

import java.util.ArrayList;
import java.util.List;

public class ContactsActivity extends AppCompatActivity {

    public AppEngineImpl appEngine = AppEngineImpl.getSharedInstance();
    private ListView listView;
    private List<String> contactsList;

    // Request code for READ_CONTACTS. It can be any number > 0.
    private static final int PERMISSIONS_REQUEST_READ_CONTACTS = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        this.listView = (ListView) findViewById(R.id.contactsListID);

        // Read and show the contacts
        showContacts();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //get the right event by event position parameter passed from last page
                int eventPosition = getIntent().getIntExtra("position", 1);

                //check if this person have already been invited
                if (appEngine.eventLists.get(eventPosition).getAttendees().contains(contactsList.get(position))) {
                    //show alert to tell the contact have been added
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(ContactsActivity.this);
                    builder1.setMessage("This person already exits !");
                    builder1.setCancelable(true);

                    builder1.setPositiveButton(
                            "ok",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });

                    AlertDialog alert11 = builder1.create();
                    alert11.show();
                } else {
                    List<String> newContacts = appEngine.eventLists.get(eventPosition).getAttendees();
                    newContacts.add(contactsList.get(position));

                    appEngine.eventLists.get(eventPosition).setAttendees(newContacts);

                    //show alert to tell the contact have been added
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(ContactsActivity.this);
                    builder1.setMessage(contactsList.get(position) + " has been added to " + appEngine.eventLists.get(eventPosition).getTittle());
                    builder1.setCancelable(true);

                    builder1.setPositiveButton(
                            "ok",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                    onBackPressed();
                                }
                            });

                    AlertDialog alert11 = builder1.create();
                    alert11.show();
                }

            }
        });
    }


    private void showContacts() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, PERMISSIONS_REQUEST_READ_CONTACTS);
            //After this point you wait for callback in onRequestPermissionsResult(int, String[], int[]) overriden method
        } else {
            contactsList = getContactNames();
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, contactsList);
            listView.setAdapter(adapter);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        if (requestCode == PERMISSIONS_REQUEST_READ_CONTACTS) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission is granted
                showContacts();
            } else {
                Toast.makeText(this, "Until you grant the permission, we canot display the names", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private List<String> getContactNames() {
        List<String> contacts = new ArrayList<>();
        // Get the ContentResolver
        ContentResolver cr = getContentResolver();
        // Get the Cursor of all the contacts
        Cursor cursor = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);

        // Move the cursor to first. Also check whether the cursor is empty or not.
        if (cursor.moveToFirst()) {
            // Iterate through the cursor
            do {
                // Get the contacts name
                String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                contacts.add(name);
            } while (cursor.moveToNext());
        }
        // Close the curosor
        cursor.close();

        return contacts;
    }
}
