package com.example.movienightplanner.controllers.adapter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;

import com.example.movienightplanner.models.AppEngineImpl;
import com.example.movienightplanner.views.MainActivity;
import com.example.movienightplanner.views.SettingsActivity;

public class SetingsDoneOnclickListener implements View.OnClickListener {

    public AppEngineImpl appEngine = AppEngineImpl.getSharedInstance();
    private SettingsActivity superActivity;
    final EditText remindingPeriodText;
    final EditText thresholdPeriodText;

    public SetingsDoneOnclickListener(SettingsActivity superActivity, EditText remindingPeriodText, EditText thresholdPeriodText) {
        this.superActivity = superActivity;
        this.remindingPeriodText = remindingPeriodText;
        this.thresholdPeriodText = thresholdPeriodText;
    }

    @Override
    public void onClick(final View v) {

        //check if input was empty
        if (remindingPeriodText.getText().toString().isEmpty()) {
            appEngine.showAlert("Reminding peoriod can not be empty !", v.getContext());
        }
        else if (thresholdPeriodText.getText().toString().isEmpty()) {
            appEngine.showAlert("Threshold can not be empty !", v.getContext());
        }
        else {
            try{
                appEngine.setRemindingPeriod(Integer.parseInt(remindingPeriodText.getText().toString()));
                appEngine.setThreshold(Integer.parseInt(thresholdPeriodText.getText().toString()));
                //show alert
                AlertDialog.Builder builder1 = new AlertDialog.Builder(v.getContext());
                builder1.setMessage("remind period has been edited to " + appEngine.getRemindingPeriod() + " minutes" + System.getProperty("line.separator") + "threshold has been edited to " + appEngine.getThreshold() + " minutes");
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "ok",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                                Intent intent = new Intent(v.getContext(), MainActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                superActivity.startActivity(intent);
                            }
                        });

                AlertDialog alert11 = builder1.create();
                alert11.show();

            }catch (Exception e) {
                appEngine.showAlert("Please input an Integer !", v.getContext());
            }

        }

    }
}