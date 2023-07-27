package edu.gatech.seclass.jobcompare6300;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ComparisonViewActivity extends AppCompatActivity {
    Button main_menu_button;

    Button perform_another_comparison_button;

    TableLayout tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comparison_view);

        // Find by id all the buttons
        perform_another_comparison_button = (Button) findViewById(R.id.btn_performAnotherComparison);
        main_menu_button = (Button) findViewById(R.id.btn_goMainMenu);

        // Find table layout
        tv = (TableLayout) findViewById(R.id.tableLayout);
        // Use Memory as database
        Memory db = new Memory();

        //Get the bundle
        Bundle bundle = getIntent().getExtras();

        //Extract the data…
        String id1 = bundle.getString("id1");
        String id2 = bundle.getString("id2");

        List<String> ids = new ArrayList<>();
        ids.add(id1);
        ids.add(id2);

        List <Job> jobOffers = new ArrayList<>();


        for (String id : ids){
            String title = db.getJobDataMap().get(id)[db.title];
            String company = db.getJobDataMap().get(id)[db.company];
            Integer yearlySalary = Integer.valueOf(db.getJobDataMap().get(id)[db.yearlySalary]);
            Integer yearlyBonus = Integer.valueOf(db.getJobDataMap().get(id)[db.yearlyBonus]);
            Integer leave = Integer.valueOf(db.getJobDataMap().get(id)[db.leave]);
            Integer parentalLeave = Integer.valueOf(db.getJobDataMap().get(id)[db.parentalLeave]);
            Integer lifeInsurance = Integer.valueOf(db.getJobDataMap().get(id)[db.lifeInsurance]);

            Location thisLocation = new Location(
                    db.getJobDataMap().get(id)[db.city],
                    db.getJobDataMap().get(id)[db.state],
                    Integer.parseInt(db.getJobDataMap().get(id)[db.costOfLiving])
            );

            Job jobOfferFromMemory = new Job();
            jobOfferFromMemory.setTitle(title);
            jobOfferFromMemory.setCompany(company);
            jobOfferFromMemory.setYearlySalary(yearlySalary);
            jobOfferFromMemory.setYearlyBonus(yearlyBonus);
            jobOfferFromMemory.setLeave(leave);
            jobOfferFromMemory.setParentalLeave(parentalLeave);
            jobOfferFromMemory.setLifeInsurance(lifeInsurance);
            jobOfferFromMemory.setLocation(thisLocation);
            jobOffers.add(jobOfferFromMemory);
            //Toast.makeText(getApplicationContext(), title_and_company, Toast.LENGTH_SHORT).show();
        }

        Job job1 = jobOffers.get(0);
        Job job2 = jobOffers.get(1);

        int left_margin = 50;
        int bottom_margin = 70;
        TableRow tbrow0 = new TableRow(this);
        TextView tv0 = new TextView(this);
        tv0.setText("      ");
        tv0.setTextColor(Color.BLACK);
        tv0.setPadding(left_margin, 0, 0, bottom_margin);
        tbrow0.addView(tv0);
        TextView tv1 = new TextView(this);
        tv1.setText("Job 1");
        tv1.setTextColor(Color.BLACK);
        tv1.setPadding(left_margin, 0, 0, bottom_margin);
        tbrow0.addView(tv1);
        TextView tv2 = new TextView(this);
        tv2.setText("Job 2");
        tv2.setTextColor(Color.BLACK);
        tv2.setPadding(left_margin, 0, 0, bottom_margin);
        tbrow0.addView(tv2);
        tv.addView(tbrow0);

        // Add Title
        TableRow tbrow1 = new TableRow(this);
        TextView t1v1 = new TextView(this);
        t1v1.setText("Title");
        t1v1.setTextColor(Color.BLACK);
        t1v1.setGravity(Gravity.LEFT);
        t1v1.setPadding(left_margin, 0, 0, bottom_margin);
        t1v1.setLayoutParams(new TableRow.LayoutParams(300, TableRow.LayoutParams.WRAP_CONTENT));
        tbrow1.addView(t1v1);

        TextView t2v1 = new TextView(this);
        t2v1.setText(job1.getTitle());
        t2v1.setTextColor(Color.BLACK);
        t2v1.setGravity(Gravity.LEFT);
        t2v1.setPadding(left_margin, 0, 0, bottom_margin);
        t2v1.setLayoutParams(new TableRow.LayoutParams(300, TableRow.LayoutParams.WRAP_CONTENT));
        tbrow1.addView(t2v1);

        TextView t3v1 = new TextView(this);
        t3v1.setText(job2.getTitle());
        t3v1.setTextColor(Color.BLACK);
        t3v1.setGravity(Gravity.LEFT);
        t3v1.setPadding(left_margin, 0, 0, bottom_margin);
        t3v1.setLayoutParams(new TableRow.LayoutParams(300, TableRow.LayoutParams.WRAP_CONTENT));
        tbrow1.addView(t3v1);
        tv.addView(tbrow1);

        // Add Company
        TableRow tbrow2 = new TableRow(this);
        TextView t1v2 = new TextView(this);
        t1v2.setText("Company");
        t1v2.setTextColor(Color.BLACK);
        t1v2.setGravity(Gravity.LEFT);
        t1v2.setPadding(left_margin, 0, 0, bottom_margin);
        t1v2.setLayoutParams(new TableRow.LayoutParams(300, TableRow.LayoutParams.WRAP_CONTENT));
        tbrow2.addView(t1v2);

        TextView t2v2 = new TextView(this);
        t2v2.setText(job1.getCompany());
        t2v2.setTextColor(Color.BLACK);
        t2v2.setGravity(Gravity.LEFT);
        t2v2.setPadding(left_margin, 0, 0, bottom_margin);
        t2v2.setLayoutParams(new TableRow.LayoutParams(300, TableRow.LayoutParams.WRAP_CONTENT));
        tbrow2.addView(t2v2);

        TextView t3v2 = new TextView(this);
        t3v2.setText(job2.getCompany());
        t3v2.setTextColor(Color.BLACK);
        t3v2.setGravity(Gravity.LEFT);
        t3v2.setPadding(left_margin, 0, 0, bottom_margin);
        t3v2.setLayoutParams(new TableRow.LayoutParams(300, TableRow.LayoutParams.WRAP_CONTENT));
        tbrow2.addView(t3v2);
        tv.addView(tbrow2);

        // Add Location
        TableRow tbrow3 = new TableRow(this);
        TextView t1v3 = new TextView(this);
        t1v3.setText("Location");
        t1v3.setTextColor(Color.BLACK);
        t1v3.setGravity(Gravity.LEFT);
        t1v3.setPadding(left_margin, 0, 0, bottom_margin);
        t1v3.setLayoutParams(new TableRow.LayoutParams(300, TableRow.LayoutParams.WRAP_CONTENT));
        tbrow3.addView(t1v3);

        TextView t2v3 = new TextView(this);
        t2v3.setText(job1.getLocation().toString());
        t2v3.setTextColor(Color.BLACK);
        t2v3.setGravity(Gravity.LEFT);
        t2v3.setPadding(left_margin, 0, 0, bottom_margin);
        t2v3.setLayoutParams(new TableRow.LayoutParams(300, TableRow.LayoutParams.WRAP_CONTENT));
        tbrow3.addView(t2v3);

        TextView t3v3 = new TextView(this);
        t3v3.setText(job2.getLocation().toString());
        t3v3.setTextColor(Color.BLACK);
        t3v3.setGravity(Gravity.LEFT);
        t3v3.setPadding(left_margin, 0, 0, bottom_margin);
        t3v3.setLayoutParams(new TableRow.LayoutParams(300, TableRow.LayoutParams.WRAP_CONTENT));
        tbrow3.addView(t3v3);
        tv.addView(tbrow3);

        // Yearly Salary Adjusted
        TableRow tbrow4 = new TableRow(this);
        TextView t1v4 = new TextView(this);
        t1v4.setText("Yearly Salary Adjusted");
        t1v4.setTextColor(Color.BLACK);
        t1v4.setGravity(Gravity.LEFT);
        t1v4.setPadding(left_margin, 0, 0, bottom_margin);
        t1v4.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT));
        tbrow4.addView(t1v4);

        TextView t2v4 = new TextView(this);
        t2v4.setText(String.valueOf(job1.getYearlySalary() / job1.getLocation().getCostOfLiving()));
        t2v4.setTextColor(Color.BLACK);
        t2v4.setGravity(Gravity.LEFT);
        t2v4.setPadding(left_margin, 0, 0, bottom_margin);
        t2v4.setLayoutParams(new TableRow.LayoutParams(300, TableRow.LayoutParams.WRAP_CONTENT));
        tbrow4.addView(t2v4);

        TextView t3v4 = new TextView(this);
        t3v4.setText(String.valueOf(job2.getYearlySalary() / job2.getLocation().getCostOfLiving()));
        t3v4.setTextColor(Color.BLACK);
        t3v4.setGravity(Gravity.LEFT);
        t3v4.setPadding(left_margin, 0, 0, bottom_margin);
        t3v4.setLayoutParams(new TableRow.LayoutParams(300, TableRow.LayoutParams.WRAP_CONTENT));
        tbrow4.addView(t3v4);
        tv.addView(tbrow4);

        // Yearly Bonus Adjusted
        TableRow tbrow5 = new TableRow(this);
        TextView t1v5 = new TextView(this);
        t1v5.setText("Yearly Bonus Adjusted");
        t1v5.setTextColor(Color.BLACK);
        t1v5.setGravity(Gravity.LEFT);
        t1v5.setPadding(left_margin, 0, 0, bottom_margin);
        tbrow5.addView(t1v5);
        t1v5.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT));

        TextView t2v5 = new TextView(this);
        t2v5.setText(String.valueOf(job1.getYearlyBonus() / job1.getLocation().getCostOfLiving()));
        t2v5.setTextColor(Color.BLACK);
        t2v5.setGravity(Gravity.LEFT);
        t2v5.setLayoutParams(new TableRow.LayoutParams(300, TableRow.LayoutParams.WRAP_CONTENT));
        t2v5.setPadding(left_margin, 0, 0, bottom_margin);
        tbrow5.addView(t2v5);

        TextView t3v5 = new TextView(this);
        t3v5.setText(String.valueOf(job2.getYearlyBonus() / job2.getLocation().getCostOfLiving()));
        t3v5.setTextColor(Color.BLACK);
        t3v5.setGravity(Gravity.LEFT);
        t3v5.setPadding(left_margin, 0, 0, bottom_margin);
        t3v5.setLayoutParams(new TableRow.LayoutParams(300, TableRow.LayoutParams.WRAP_CONTENT));
        tbrow5.addView(t3v5);
        tv.addView(tbrow5);

        // Yearly Bonus Adjusted
        TableRow tbrow6 = new TableRow(this);
        TextView t1v6 = new TextView(this);
        t1v6.setText("Leave");
        t1v6.setTextColor(Color.BLACK);
        t1v6.setGravity(Gravity.LEFT);
        t1v6.setPadding(left_margin, 0, 0, bottom_margin);
        t1v6.setLayoutParams(new TableRow.LayoutParams(300, TableRow.LayoutParams.WRAP_CONTENT));
        tbrow6.addView(t1v6);
        TextView t2v6 = new TextView(this);
        t2v6.setText(String.valueOf(job1.getLeave()));
        t2v6.setTextColor(Color.BLACK);
        t2v6.setGravity(Gravity.LEFT);
        t2v6.setPadding(left_margin, 0, 0, bottom_margin);
        t2v6.setLayoutParams(new TableRow.LayoutParams(300, TableRow.LayoutParams.WRAP_CONTENT));
        tbrow6.addView(t2v6);
        TextView t3v6 = new TextView(this);
        t3v6.setText(String.valueOf(job2.getLeave()));
        t3v6.setTextColor(Color.BLACK);
        t3v6.setGravity(Gravity.LEFT);
        t3v6.setPadding(left_margin, 0, 0, bottom_margin);
        t3v6.setLayoutParams(new TableRow.LayoutParams(300, TableRow.LayoutParams.WRAP_CONTENT));
        tbrow6.addView(t3v6);
        tv.addView(tbrow6);

        // Maternity/Paternity Leave
        TableRow tbrow7 = new TableRow(this);
        TextView t1v7 = new TextView(this);
        t1v7.setText("Maternity/Paternity Leave");
        t1v7.setTextColor(Color.BLACK);
        t1v7.setGravity(Gravity.LEFT);
        t1v7.setPadding(left_margin, 0, 0, bottom_margin);
        t1v7.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT));

        tbrow7.addView(t1v7);
        TextView t2v7 = new TextView(this);
        t2v7.setText(String.valueOf(job1.getParentalLeave()));
        t2v7.setTextColor(Color.BLACK);
        t2v7.setGravity(Gravity.LEFT);
        t2v7.setLayoutParams(new TableRow.LayoutParams(300, TableRow.LayoutParams.WRAP_CONTENT));
        t2v7.setPadding(left_margin, 0, 0, bottom_margin);

        tbrow7.addView(t2v7);
        TextView t3v7 = new TextView(this);
        t3v7.setText(String.valueOf(job2.getParentalLeave()));
        t3v7.setTextColor(Color.BLACK);
        t3v7.setGravity(Gravity.LEFT);
        t3v7.setPadding(left_margin, 0, 0, bottom_margin);
        t3v7.setLayoutParams(new TableRow.LayoutParams(300, TableRow.LayoutParams.WRAP_CONTENT));
        tbrow7.addView(t3v7);
        tv.addView(tbrow7);

        // Life Insurance
        TableRow tbrow8 = new TableRow(this);
        TextView t1v8 = new TextView(this);
        t1v8.setText("Life Insurance");
        t1v8.setTextColor(Color.BLACK);
        t1v8.setGravity(Gravity.LEFT);
        t1v8.setPadding(left_margin, 0, 0, bottom_margin);
        t1v8.setLayoutParams(new TableRow.LayoutParams(300, TableRow.LayoutParams.WRAP_CONTENT));
        tbrow8.addView(t1v8);
        TextView t2v8 = new TextView(this);
        t2v8.setText(String.valueOf(job1.getLifeInsurance()));
        t2v8.setTextColor(Color.BLACK);
        t2v8.setGravity(Gravity.LEFT);
        t2v8.setPadding(left_margin, 0, 0, bottom_margin);
        t2v8.setLayoutParams(new TableRow.LayoutParams(300, TableRow.LayoutParams.WRAP_CONTENT));
        tbrow8.addView(t2v8);
        TextView t3v8 = new TextView(this);
        t3v8.setText(String.valueOf(job2.getLifeInsurance()));
        t3v8.setTextColor(Color.BLACK);
        t3v8.setGravity(Gravity.LEFT);
        t3v8.setPadding(left_margin, 0, 0, bottom_margin);
        t3v8.setLayoutParams(new TableRow.LayoutParams(300, TableRow.LayoutParams.WRAP_CONTENT));
        tbrow8.addView(t3v8);
        tv.addView(tbrow8);


//        int left_margin = 6;
//        TableRow tbrow0 = new TableRow(this);
//        TextView tv0 = new TextView(this);
//        tv0.setText("Title");
//        tv0.setTextColor(Color.BLACK);
//        tv0.setPadding(left_margin, 0, 0, 10);
//        tbrow0.addView(tv0);
//        TextView tv1 = new TextView(this);
//        tv1.setText("Company");
//        tv1.setTextColor(Color.BLACK);
//        tv1.setPadding(left_margin, 0, 0, 10);
//        tbrow0.addView(tv1);
//        TextView tv2 = new TextView(this);
//        tv2.setText("Location");
//        tv2.setTextColor(Color.BLACK);
//        tv2.setPadding(left_margin, 0, 0, 10);
//        tbrow0.addView(tv2);
//        TextView tv3 = new TextView(this);
//        tv3.setText("Yearly \n Salary \n Adjust");
//        tv3.setTextColor(Color.BLACK);
//        tv3.setPadding(left_margin, 0, 0, 10);
//        tbrow0.addView(tv3);
//
//        TextView tv4 = new TextView(this);
//        tv4.setText("Yearly \n Bonus \n Adjust");
//        tv4.setTextColor(Color.BLACK);
//        tv4.setPadding(left_margin, 0, 0, 10);
//        tbrow0.addView(tv4);
//
//        TextView tv5 = new TextView(this);
//        tv5.setText("Leave");
//        tv5.setTextColor(Color.BLACK);
//        tv5.setPadding(left_margin, 0, 0, 10);
//        tbrow0.addView(tv5);
//
//        TextView tv6 = new TextView(this);
//        tv6.setText("Parent \n Leave");
//        tv6.setTextColor(Color.BLACK);
//        tv6.setPadding(left_margin, 0, 0, 10);
//        tbrow0.addView(tv6);
//
//        TextView tv7 = new TextView(this);
//        tv7.setText("Life \n Cover");
//        tv7.setTextColor(Color.BLACK);
//        tv7.setPadding(10, 0, 0, 10);
//        tbrow0.addView(tv7);
//
//        tv.addView(tbrow0);
//        for (Job job: jobOffers) {
//            TableRow tbrow = new TableRow(this);
//            TextView t1v = new TextView(this);
//            t1v.setText(job.getTitle());
//            t1v.setTextColor(Color.BLACK);
//            t1v.setGravity(Gravity.CENTER);
//            t1v.setPadding(left_margin, 0, 0, 10);
//            tbrow.addView(t1v);
//            TextView t2v = new TextView(this);
//            t2v.setText(job.getCompany());
//            t2v.setTextColor(Color.BLACK);
//            t2v.setGravity(Gravity.CENTER);
//            t2v.setPadding(left_margin, 0, 0, 10);
//            tbrow.addView(t2v);
//            TextView t3v = new TextView(this);
//            t3v.setText("Location");
//            t3v.setTextColor(Color.BLACK);
//            t3v.setGravity(Gravity.CENTER);
//            t3v.setPadding(left_margin, 0, 0, 10);
//            tbrow.addView(t3v);
//
//            TextView t4v = new TextView(this);
//            t4v.setText(String.valueOf(job.getYearlySalary())); // TODO: Adjust with location cost of living
//            t4v.setTextColor(Color.BLACK);
//            t4v.setGravity(Gravity.CENTER);
//            t4v.setPadding(left_margin, 0, 0, 10);
//            tbrow.addView(t4v);
//
//            TextView t5v = new TextView(this);
//            t5v.setText(String.valueOf(job.getYearlyBonus())); // TODO: Adjust with location cost of living
//            t5v.setTextColor(Color.BLACK);
//            t5v.setGravity(Gravity.CENTER);
//            t5v.setPadding(left_margin, 0, 0, 10);
//            tbrow.addView(t5v);
//
//            TextView t6v = new TextView(this);
//            t6v.setText(String.valueOf(job.getLeave()));
//            t6v.setTextColor(Color.BLACK);
//            t6v.setGravity(Gravity.CENTER);
//            t6v.setPadding(left_margin, 0, 0, 10);
//            tbrow.addView(t6v);
//
//            TextView t7v = new TextView(this);
//            t7v.setText(String.valueOf(job.getParentalLeave()));
//            t7v.setTextColor(Color.BLACK);
//            t7v.setGravity(Gravity.CENTER);
//            t7v.setPadding(left_margin, 0, 0, 10);
//            tbrow.addView(t7v);
//
//            TextView t8v = new TextView(this);
//            t8v.setText(String.valueOf(job.getLifeInsurance()));
//            t8v.setTextColor(Color.BLACK);
//            t8v.setGravity(Gravity.CENTER);
//            t8v.setPadding(left_margin, 0, 0, 10);
//            tbrow.addView(t8v);
//
//            tv.addView(tbrow);
//        }
//        Toast.makeText(getApplicationContext(), jobOffers.toString(), Toast.LENGTH_SHORT).show()

        main_menu_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        perform_another_comparison_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), CompareJobOffersActivity.class);
                startActivity(intent);
            }
        });

    }
}