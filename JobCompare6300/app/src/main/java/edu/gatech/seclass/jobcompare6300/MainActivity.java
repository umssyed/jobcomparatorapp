package edu.gatech.seclass.jobcompare6300;

import android.content.Context;
import android.os.Bundle;


import android.widget.TextView;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.provider.ContactsContract;
import android.view.View;
import android.content.Intent;

import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    // MainActivity Variables
    Button addCurrentJob_button;
    Button addJobOffer_button;
    Button viewJobOffers_button;
    Button adjustComparisonSettings_button;
    Button compareJobOffers_button;

    ListView lv_jobOffers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Declare memory
        Memory db = new Memory();

        // Uncomment these lines to delete everything from memory.
        // Comment these again for final production app
        //db.clearJobData();

        addCurrentJob_button = (Button) findViewById(R.id.btn_addCurrentJob);
        addJobOffer_button = (Button) findViewById(R.id.btn_addJobOffer);
        adjustComparisonSettings_button = (Button) findViewById(R.id.btn_adjustComparisonSettings);
        compareJobOffers_button = (Button) findViewById(R.id.btn_compareJobOffers);
        if(db.getJobDataMap().keySet().toArray().length < 2) {
            compareJobOffers_button.setEnabled(false);
        }

        addCurrentJob_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), AddCurrentJobActivity.class);
                startActivity(intent);
            }
        });

        addJobOffer_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), AddJobOfferActivity.class);
                startActivity(intent);
            }
        });


        adjustComparisonSettings_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ComparisonSettingsActivity.class);
                startActivity(intent);
            }
        });

        compareJobOffers_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), CompareJobOffersActivity.class);
                startActivity(intent);
            }
        });



    }


}