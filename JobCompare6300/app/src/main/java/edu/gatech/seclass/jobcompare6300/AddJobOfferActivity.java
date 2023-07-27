package edu.gatech.seclass.jobcompare6300;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.text.TextWatcher;

import java.util.UUID;

import androidx.appcompat.app.AppCompatActivity;

import java.util.prefs.PreferenceChangeEvent;

public class AddJobOfferActivity extends AppCompatActivity {

    int uniqueID;
    private EditText title;
    private EditText company;
    private EditText city;
    private EditText state;
    private EditText costOfLiving;
    private EditText salary;
    private EditText bonus;
    private EditText leave;
    private EditText parentalLeave;
    private EditText lifeInsurance;
    private boolean hasCurrJob = false;
    Button saveJobOffer_button;
    Button cancel_button;
    Button addAnotherJobOffer_button;
    Button compareJobOffer_button;

    // Use these two to store the newly job offer title and company
    // Used for creating the id and passing it through if user selects
    // to perform comparison with current job
    private String newJobOfferTitle;
    private String newJobOfferCompany;

    private String currJobTitle = "";
    private String currJobCompany = "";


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_job_offer);

        // Find by id all the buttons
        saveJobOffer_button = (Button) findViewById(R.id.btn_saveJobOffer);
        cancel_button = (Button) findViewById(R.id.btn_cancelJobOffer);
        addAnotherJobOffer_button = (Button) findViewById(R.id.btn_addAnotherJobOffer);
        compareJobOffer_button = (Button) findViewById(R.id.btn_compareJobOffers);

        // Use Memory as database
        Memory db = new Memory();

        // Find by id all the relevant variables from the form
        title = (EditText) findViewById(R.id.enter_title);
        company = (EditText) findViewById(R.id.enter_company);
        city = (EditText) findViewById(R.id.enter_city);
        state = (EditText) findViewById(R.id.enter_state);
        costOfLiving = (EditText) findViewById(R.id.enter_cost_of_living);
        salary = (EditText) findViewById(R.id.enter_salary);
        bonus = (EditText) findViewById(R.id.enter_bonus);
        leave = (EditText) findViewById(R.id.enter_leave);
        parentalLeave = (EditText) findViewById(R.id.enter_parental_leave);
        lifeInsurance = (EditText) findViewById(R.id.enter_life_insurance);

        title.addTextChangedListener(watchForTextEntered);
        company.addTextChangedListener(watchForTextEntered);
        city.addTextChangedListener(watchForTextEntered);
        state.addTextChangedListener(watchForTextEntered);
        costOfLiving.addTextChangedListener(watchForTextEntered);
        salary.addTextChangedListener(watchForTextEntered);
        bonus.addTextChangedListener(watchForTextEntered);
        leave.addTextChangedListener(watchForTextEntered);
        parentalLeave.addTextChangedListener(watchForTextEntered);
        lifeInsurance.addTextChangedListener(watchForTextEntered);



        // When Save Job Offer button is clicked... add to the database
        saveJobOffer_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Perform field input validations
                if (!isNumeric(costOfLiving.getText().toString())) {
                    Toast.makeText(AddJobOfferActivity.this, "Invalid cost of living! Please enter between a whole number.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Salary check
                if (!isNumeric(salary.getText().toString())) {
                    Toast.makeText(AddJobOfferActivity.this, "Invalid salary! Please enter between a whole number.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Bonus check
                if (!isNumeric(bonus.getText().toString())) {
                    Toast.makeText(AddJobOfferActivity.this, "Invalid bonus! Please enter between a whole number.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Leave check
                if (isNumeric(leave.getText().toString())) {
                    Integer l = Integer.parseInt(leave.getText().toString());
                    if (l < 0 || l > 30 ) {
                        Toast.makeText(AddJobOfferActivity.this, "Invalid leave! Please enter whole number between 0 to 30 inclusive.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                } else {
                    Toast.makeText(AddJobOfferActivity.this, "Invalid leave! Leave should be a whole number.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Parental leave
                if (isNumeric(parentalLeave.getText().toString())) {
                    Integer l = Integer.parseInt(parentalLeave.getText().toString());
                    if (l < 0 || l > 20 ) {
                        Toast.makeText(AddJobOfferActivity.this, "Invalid parental leave! Please enter whole number between 0 to 20 inclusive.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                } else {
                    Toast.makeText(AddJobOfferActivity.this, "Invalid parental leave! Leave should be a whole number.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Life Insurance
                if (isNumeric(lifeInsurance.getText().toString())) {
                    Integer l = Integer.parseInt(lifeInsurance.getText().toString());
                    if (l <= 0 || l >= 10 ) {
                        Toast.makeText(AddJobOfferActivity.this, "Invalid Life insurance! Please enter whole number between 0 to 10.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                } else {
                    Toast.makeText(AddJobOfferActivity.this, "Invalid Life insurance! Leave should be a whole number.", Toast.LENGTH_SHORT).show();
                    return;
                }

                Job jobOffer = new Job(title.getText().toString(),
                        company.getText().toString(),
                        new Location(city.getText().toString(), state.getText().toString(), Integer.parseInt(costOfLiving.getText().toString())),
                        Integer.parseInt(salary.getText().toString()),
                        Integer.parseInt(bonus.getText().toString()),
                        Integer.parseInt(leave.getText().toString()),
                        Integer.parseInt(parentalLeave.getText().toString()),
                        Integer.parseInt(lifeInsurance.getText().toString()));


                // Store into the database memory
                db.setJobData(jobOffer.getTitle(),
                        jobOffer.getCompany(),
                        jobOffer.getLocation().getCity(),
                        jobOffer.getLocation().getState(),
                        String.valueOf(jobOffer.getLocation().getCostOfLiving()),
                        String.valueOf(jobOffer.getYearlySalary()),
                        String.valueOf(jobOffer.getYearlyBonus()),
                        String.valueOf(jobOffer.getLeave()),
                        String.valueOf(jobOffer.getParentalLeave()),
                        String.valueOf(jobOffer.getLifeInsurance()),
                        "false"
                );
                newJobOfferTitle = jobOffer.getTitle();
                newJobOfferCompany = jobOffer.getCompany();
                Toast.makeText(getApplicationContext(), "Your job offer was added!", Toast.LENGTH_SHORT ).show();

                // Change button statuses after saving a job
                addAnotherJobOffer_button.setEnabled(true);
                saveJobOffer_button.setEnabled(false);

                // Disable all text fields to prevent user from changing saved job offer fields
                title.setEnabled(false);
                company.setEnabled(false);
                city.setEnabled(false);
                state.setEnabled(false);
                costOfLiving.setEnabled(false);
                salary.setEnabled(false);
                bonus.setEnabled(false);
                leave.setEnabled(false);
                parentalLeave.setEnabled(false);
                lifeInsurance.setEnabled(false);

                // Ensure the compare job offers is not available until you save a new job offer
                for (String id: db.getJobDataMap().keySet()) {
                    hasCurrJob = Boolean.parseBoolean(db.getJobDataMap().get(id)[db.trueIfCurrent]);
                    if (hasCurrJob) {
                        currJobTitle = db.getJobDataMap().get(id)[db.title];
                        currJobCompany = db.getJobDataMap().get(id)[db.company];
                        break;
                    }
                }
                compareJobOffer_button.setEnabled(hasCurrJob);

            }
        });

        addAnotherJobOffer_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), AddJobOfferActivity.class);
                startActivity(intent);
            }
        });

        compareJobOffer_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("id1", currJobTitle + " " + currJobCompany);
                bundle.putString("id2", newJobOfferTitle + " " + newJobOfferCompany);

                Intent intent = new Intent(v.getContext(), ComparisonViewActivity.class);
                intent.putExtras(bundle);

                startActivity(intent);
            }
        });

        // When Cancel button is clicked... go back to MainActivity
        cancel_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MainActivity.class);
                startActivity(intent);
            }
        });

    }

    TextWatcher watchForTextEntered = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            String titleInput = title.getText().toString().trim();
            String companyInput = company.getText().toString().trim();
            String cityInput = city.getText().toString().trim();
            String stateInput = state.getText().toString().trim();
            String COLInput = costOfLiving.getText().toString().trim();
            String salaryInput = salary.getText().toString().trim();
            String bonusInput = bonus.getText().toString().trim();
            String leaveInput = leave.getText().toString().trim();
            String parentalLeaveInput = parentalLeave.getText().toString().trim();
            String lifeInsuranceInput = lifeInsurance.getText().toString().trim();

            saveJobOffer_button.setEnabled(
                    !titleInput.isEmpty() && !companyInput.isEmpty() && !cityInput.isEmpty() &&
                            !stateInput.isEmpty() && !COLInput.isEmpty() &&
                            !salaryInput.isEmpty() && !bonusInput.isEmpty() &&
                            !leaveInput.isEmpty() && !parentalLeaveInput.isEmpty() &&
                            !lifeInsuranceInput.isEmpty()
            );
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

    private static boolean isNumeric(String fieldInput){
        return fieldInput != null && fieldInput.matches("[0-9]+");
    }
}