package com.example.movienightplanner.views;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.movienightplanner.R;
import com.example.movienightplanner.controllers.adapter.EditEventOnclickListener;
import com.example.movienightplanner.controllers.adapter.SetingsDoneOnclickListener;
import com.example.movienightplanner.models.AppEngineImpl;

public class SettingsActivity extends AppCompatActivity {

    public AppEngineImpl appEngine = AppEngineImpl.getSharedInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Button doneButton;

        final EditText remindingPeriodText;

        doneButton = (Button) findViewById(R.id.doneButton);

        remindingPeriodText = (EditText) findViewById(R.id.remindingPeriodEditTextID);
        remindingPeriodText.setText(""+appEngine.getRemindingPeriod());

        //handled logic in SetingsDoneOnclickListener class
        doneButton.setOnClickListener(new SetingsDoneOnclickListener(this, remindingPeriodText));

    }
}
