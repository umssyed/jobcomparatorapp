package edu.gatech.seclass.jobcompare6300;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Objects;
import java.util.UUID;

public class AddCurrentJobActivity extends AppCompatActivity {

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
    Button saveCurrentJob_button;
    Button cancel_button;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_current_job);

        // Find by id all the buttons
        saveCurrentJob_button = (Button) findViewById(R.id.btn_saveCurrentJob);
        cancel_button = (Button) findViewById(R.id.btn_cancelCurrentJob);

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


        // Check to see if user already has a current job
        // If yes, display the current job with editable text to update/save
        for (String id: db.getJobDataMap().keySet()) {
            Boolean hasCurrJob = Boolean.parseBoolean(db.getJobDataMap().get(id)[db.trueIfCurrent]);
            if (hasCurrJob) {
                // Create a Location instance
                Location currJobLocation = new Location (
                        db.getJobDataMap().get(id)[db.city],
                        db.getJobDataMap().get(id)[db.state],
                        Integer.parseInt(db.getJobDataMap().get(id)[db.costOfLiving])
                );
                // Update the form
                title.setText(db.getJobDataMap().get(id)[db.title]);
                company.setText(db.getJobDataMap().get(id)[db.company]);
                city.setText(currJobLocation.getCity());
                state.setText(currJobLocation.getState());
                costOfLiving.setText(String.valueOf(currJobLocation.getCostOfLiving()));
                salary.setText(db.getJobDataMap().get(id)[db.yearlySalary]);
                bonus.setText(db.getJobDataMap().get(id)[db.yearlyBonus]);
                leave.setText(db.getJobDataMap().get(id)[db.leave]);
                parentalLeave.setText(db.getJobDataMap().get(id)[db.parentalLeave]);
                lifeInsurance.setText(db.getJobDataMap().get(id)[db.lifeInsurance]);
                saveCurrentJob_button.setText("Update Current Job");
                break;
            }
        }

        for (String id : db.getJobDataMap().keySet()){
            boolean hasCurrentJob = Boolean.parseBoolean(db.getJobDataMap().get(id)[db.trueIfCurrent]);

            if (hasCurrentJob) {
                title.setText(db.getJobDataMap().get(id)[db.title]);
                company.setText(db.getJobDataMap().get(id)[db.company]);
                city.setText(db.getJobDataMap().get(id)[db.city]);
                state.setText(db.getJobDataMap().get(id)[db.state]);
                costOfLiving.setText(db.getJobDataMap().get(id)[db.costOfLiving]);
                salary.setText(db.getJobDataMap().get(id)[db.yearlySalary]);
                bonus.setText(db.getJobDataMap().get(id)[db.yearlyBonus]);
                leave.setText(db.getJobDataMap().get(id)[db.leave]);
                parentalLeave.setText(db.getJobDataMap().get(id)[db.parentalLeave]);
                lifeInsurance.setText(db.getJobDataMap().get(id)[db.lifeInsurance]);
                break;
            }
        }

        saveCurrentJob_button.setOnClickListener(new View.OnClickListener() {
            Job savedCurrentJob = new Job ();
            boolean hasCurrentJob = false;
            @Override
            public void onClick(View v) {

                // Perform field input validations
                if (!isNumeric(costOfLiving.getText().toString())) {
                    Toast.makeText(AddCurrentJobActivity.this, "Invalid cost of living! Please enter between a whole number.", Toast.LENGTH_SHORT).show();
                    return;
                }
                // Salary check
                if (!isNumeric(salary.getText().toString())) {
                    Toast.makeText(AddCurrentJobActivity.this, "Invalid salary! Please enter between a whole number.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Bonus check
                if (!isNumeric(bonus.getText().toString())) {
                    Toast.makeText(AddCurrentJobActivity.this, "Invalid bonus! Please enter between a whole number.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Leave check
                if (isNumeric(leave.getText().toString())) {
                    Integer l = Integer.parseInt(leave.getText().toString());
                    if (l < 0 || l > 30 ) {
                        Toast.makeText(AddCurrentJobActivity.this, "Invalid leave! Please enter whole number between 0 to 30 inclusive.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                } else {
                    Toast.makeText(AddCurrentJobActivity.this, "Invalid leave! Leave should be a whole number.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Parental leave
                if (isNumeric(parentalLeave.getText().toString())) {
                    Integer l = Integer.parseInt(parentalLeave.getText().toString());
                    if (l < 0 || l > 20 ) {
                        Toast.makeText(AddCurrentJobActivity.this, "Invalid parental leave! Please enter whole number between 0 to 20 inclusive.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                } else {
                    Toast.makeText(AddCurrentJobActivity.this, "Invalid parental leave! Leave should be a whole number.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Life Insurance
                if (isNumeric(lifeInsurance.getText().toString())) {
                    Integer l = Integer.parseInt(lifeInsurance.getText().toString());
                    if (l <= 0 || l >= 10 ) {
                        Toast.makeText(AddCurrentJobActivity.this, "Invalid Life insurance! Please enter whole number between 0 to 10.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                } else {
                    Toast.makeText(AddCurrentJobActivity.this, "Invalid Life insurance! Leave should be a whole number.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Check if current job is saved.
                for (String id : db.getJobDataMap().keySet()){
                    hasCurrentJob = Boolean.parseBoolean(db.getJobDataMap().get(id)[db.trueIfCurrent]);

                    if (hasCurrentJob) {
                        db.removeJobDataById(id);
                        String updatedString = "Current job is successfully replaced with the newly added current job!";
                        Toast.makeText(getApplicationContext(), updatedString, Toast.LENGTH_SHORT ).show();
                        break;
                    }
                }

                // Save new job information in currJob instance
                Job currJob = new Job(title.getText().toString(),
                        company.getText().toString(),
                        new Location(city.getText().toString(), state.getText().toString(), Integer.parseInt(costOfLiving.getText().toString())),
                        Integer.parseInt(salary.getText().toString()),
                        Integer.parseInt(bonus.getText().toString()),
                        Integer.parseInt(leave.getText().toString()),
                        Integer.parseInt(parentalLeave.getText().toString()),
                        Integer.parseInt(lifeInsurance.getText().toString()));

                // Store currJob into the database memory
                db.setJobData(currJob.getTitle(),
                        currJob.getCompany(),
                        currJob.getLocation().getCity(),
                        currJob.getLocation().getState(),
                        String.valueOf(currJob.getLocation().getCostOfLiving()),
                        String.valueOf(currJob.getYearlySalary()),
                        String.valueOf(currJob.getYearlyBonus()),
                        String.valueOf(currJob.getLeave()),
                        String.valueOf(currJob.getParentalLeave()),
                        String.valueOf(currJob.getLifeInsurance()),
                        "true"
                );

                // If current job does not exist, display a different message
                if (!hasCurrentJob) {
                    // Return a toast
                    String s = "Successfully saved your current job!";
                    Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT ).show();
                }
                saveCurrentJob_button.setEnabled(false);

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


            saveCurrentJob_button.setEnabled(
                    !titleInput.isEmpty() && !companyInput.isEmpty() && !cityInput.isEmpty() &&
                            !stateInput.isEmpty() && !COLInput.isEmpty() &&
                            !salaryInput.isEmpty() && !bonusInput.isEmpty() &&
                            !leaveInput.isEmpty() && !parentalLeaveInput.isEmpty() &&
                            !lifeInsuranceInput.isEmpty()
            );

            if (titleInput.isEmpty() && companyInput.isEmpty() && cityInput.isEmpty() &&
                    stateInput.isEmpty() && COLInput.isEmpty() &&
                    salaryInput.isEmpty() && bonusInput.isEmpty() &&
                    leaveInput.isEmpty() && parentalLeaveInput.isEmpty() &&
                    lifeInsuranceInput.isEmpty()) {
                saveCurrentJob_button.setText("Add Current Job");
            }

        }

        @Override
        public void afterTextChanged(Editable editable) {
        }
    };

    private static boolean isNumeric(String fieldInput){
        return fieldInput != null && fieldInput.matches("[0-9]+");
    }
}