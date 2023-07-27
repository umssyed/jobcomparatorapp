package edu.gatech.seclass.jobcompare6300;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.WindowDecorActionBar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class CompareJobOffersActivity extends AppCompatActivity {
    private ListView listOfJobOffers;
    ArrayAdapter jobOffersListAdapter;
    Button performComparison_button;
    Button cancel_button;
    Button clearSelection_button;
    TextView currentJobTitle;
    TextView currentJobCompany;
    TextView selection1_text;
    TextView selection2_text;
    TextView id1;
    TextView id2;
    CheckBox checkbox;

    class JobOfferAdapterView extends LinearLayout {
        public JobOfferAdapterView(Context context, Job job) {
            super(context);
            this.setOrientation(HORIZONTAL);

            LinearLayout.LayoutParams titleParams =
                    new LinearLayout.LayoutParams(600, LayoutParams.WRAP_CONTENT);
            titleParams.setMargins(30,10,0,5);

            TextView titleText = new TextView(context);
            titleText.setText(job.getTitle());
            titleText.setTextSize(16f);
            addView(titleText, titleParams);

            LinearLayout.LayoutParams companyParams =
                    new LinearLayout.LayoutParams(600, LayoutParams.WRAP_CONTENT);
            companyParams.setMargins(5,5,0,30);

            TextView companyText = new TextView(context);
            companyText.setText(job.getCompany());
            companyText.setTextSize(16f);
            companyText.setTextColor(Color.BLUE);
            addView(companyText, companyParams);
        }
    }

    class JobOfferAdapter extends BaseAdapter {
        private Context context;
        private List<Job> jobOffersList;

        public JobOfferAdapter(Context context, List<Job> jobOffersList) {
            this.context = context;
            this.jobOffersList = jobOffersList;
        }

        @Override
        public int getCount() {
            return jobOffersList.size();
        }

        @Override
        public Object getItem(int position) {
            return jobOffersList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Job jobOffer = jobOffersList.get(position);

            return new JobOfferAdapterView(this.context, jobOffer);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compare_job_offers);

        // Find by id all the buttons
        performComparison_button = (Button) findViewById(R.id.btn_performComparison);
        cancel_button = (Button) findViewById(R.id.btn_cancelCompareJobOffers);
        listOfJobOffers = (ListView) findViewById(R.id.listView);
        clearSelection_button = (Button) findViewById(R.id.btn_clearSelection);
        selection1_text = (TextView) findViewById(R.id.selection1DynamicText);
        selection2_text = (TextView) findViewById(R.id.selection2DynamicText);
        currentJobTitle = (TextView) findViewById(R.id.currentJobTitle);
        currentJobCompany = (TextView) findViewById(R.id.currentJobCompany);
        checkbox = (CheckBox) findViewById(R.id.checkBox);

        // These are hidden textviews for job id storage
        id1 = (TextView) findViewById(R.id.id1);
        id2 = (TextView) findViewById(R.id.id2);

        // Use Memory as database
        Memory db = new Memory();

        // This list will store the list of jobs which are job offers from the memory
        List <Job> jobOffers = new ArrayList<>();

        for (String id : db.getJobDataMap().keySet()){
            Boolean isCurrentJob = Boolean.parseBoolean(db.getJobDataMap().get(id)[db.trueIfCurrent]);
            Location thisLocation = new Location(
                    db.getJobDataMap().get(id)[db.city],
                    db.getJobDataMap().get(id)[db.state],
                    Integer.parseInt(db.getJobDataMap().get(id)[db.costOfLiving])
            );
            if (isCurrentJob) {
                // If the job is a current job, do not display in the list view
                // Instead, display in the Current Job section
                String title = db.getJobDataMap().get(id)[db.title];
                String company = db.getJobDataMap().get(id)[db.company];
                currentJobTitle.setText(title);
                currentJobCompany.setText(company);
            } else {
                // If the job is not a current job, display in the list view
                // Create new job instance and update variables
                Job jobOfferFromMemory = new Job();
                jobOfferFromMemory.setTitle(db.getJobDataMap().get(id)[db.title]);
                jobOfferFromMemory.setCompany(db.getJobDataMap().get(id)[db.company]);
                jobOfferFromMemory.setLocation(thisLocation);
                jobOfferFromMemory.setYearlySalary(Integer.valueOf(db.getJobDataMap().get(id)[db.yearlySalary]));
                jobOfferFromMemory.setYearlyBonus(Integer.valueOf(db.getJobDataMap().get(id)[db.yearlyBonus]));
                jobOfferFromMemory.setLeave(Integer.valueOf(db.getJobDataMap().get(id)[db.leave]));
                jobOfferFromMemory.setParentalLeave(Integer.valueOf(db.getJobDataMap().get(id)[db.parentalLeave]));
                jobOfferFromMemory.setLifeInsurance(Integer.valueOf(db.getJobDataMap().get(id)[db.lifeInsurance]));

                // Update the jobOffers list of Jobs
                jobOffers.add(jobOfferFromMemory);
            }
        }
        ComparisonSettings comparisonSettings;
        if (!db.isComparisonWeightPresent()) {
            comparisonSettings = new ComparisonSettings(1,1,1,1,1);
        } else {
            comparisonSettings = new ComparisonSettings(
                    db.getComparisonWeight(db.salaryWeight),
                    db.getComparisonWeight(db.bonusWeight),
                    db.getComparisonWeight(db.leaveWeight),
                    db.getComparisonWeight(db.parentalLeaveWeight),
                    db.getComparisonWeight(db.lifeInsuranceWeight));
        }

        // Sort the job offers
        jobOffers.sort((Job job1, Job job2) -> JobComparator.compareJobs(job1,job2,comparisonSettings));

        listOfJobOffers = findViewById(R.id.listView);
        JobOfferAdapter adapter = new JobOfferAdapter(this, jobOffers);
        listOfJobOffers.setAdapter(adapter);
        listOfJobOffers.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        listOfJobOffers.setHeaderDividersEnabled(true);
        listOfJobOffers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Job items = (Job) adapterView.getItemAtPosition(i);
                view.setSelected(true);
                //String text = items.getTitle() + ",  " + items.getCompany();
                String title = items.getTitle();
                String company = items.getCompany();

                performSelection(title, company, false);
            }
        });

        // Check if user selects checkbox the current job for comparison
        checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = currentJobTitle.getText().toString();
                String company = currentJobCompany.getText().toString();


                if (checkbox.isChecked()) {
                    if (title.equals("Title") || company.equals("Company")) {
                        Toast.makeText(CompareJobOffersActivity.this,
                                "Update your current job from the main menu first!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    performSelection(title, company, false);
                }

                if (!checkbox.isChecked()) {
                    if (selection1_text.getText().toString().equals(title + ", " + company)) {
                        selection1_text.setText("");
                    } else if (selection2_text.getText().toString().equals(title + ", " + company)) {
                        selection2_text.setText("");
                    }
                }

            }
        });

        // When Cancel button is clicked... go back to MainActivity
        cancel_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        clearSelection_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                selection1_text.setText("");
                selection2_text.setText("");
            }
        });

        performComparison_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String sid1 = id1.getText().toString();
                String sid2 = id2.getText().toString();

                if (sid1.equals("") || sid2.equals("")) {
                    Toast.makeText(CompareJobOffersActivity.this,
                            "Select two jobs to perform a comparison!", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    Bundle bundle = new Bundle();
                    bundle.putString("id1", sid1);
                    bundle.putString("id2", sid2);

                    Intent intent = new Intent(v.getContext(), ComparisonViewActivity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }

            }
        });
    }

    public void performSelection(String title, String company, Boolean forCurrJob) {
        String text = title + ", " + company;
        String text2 = title + " " + company;
        if (selection1_text.getText().toString().equals(text)
                || selection2_text.getText().toString().equals(text)) {
            Toast.makeText(CompareJobOffersActivity.this, "You have already selected this job for comparison.",
                    Toast.LENGTH_SHORT).show();
        } else {
            if (selection1_text.getText().equals("") && selection2_text.getText().equals("")) {
                selection1_text.setText(text);
                id1.setText(text2);
            }
            else if (!(selection1_text.getText().equals("")) && selection2_text.getText().equals("")) {
                selection2_text.setText(text);
                id2.setText(text2);
            } else if (selection1_text.getText().equals("") && !(selection2_text.getText().equals(""))) {
                selection1_text.setText(text);
                id1.setText(text2);
            } else {
                Toast.makeText(CompareJobOffersActivity.this, "Clear selections to select a different option.",
                        Toast.LENGTH_SHORT).show();
            }
        }

    }

}