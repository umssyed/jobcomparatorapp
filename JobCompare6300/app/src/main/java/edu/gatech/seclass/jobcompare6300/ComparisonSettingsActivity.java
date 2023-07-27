package edu.gatech.seclass.jobcompare6300;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ComparisonSettingsActivity extends AppCompatActivity {
    private EditText yearlySalary;
    private EditText yearlyBonus;
    private EditText leave;
    private EditText paternalLeave;
    private EditText lifeInsurance;

    Button cancel_button;

    Button edit_comparison_settings_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comparison_settings);

        // Use Memory as database
        Memory db = new Memory();

        // Find by id all the buttons
        edit_comparison_settings_button = (Button) findViewById(R.id.btn_editComparisonSettings);
        cancel_button = (Button) findViewById(R.id.btn_cancelEditSettings);

        // Find by id all the relevant variables from the form
        yearlySalary = (EditText) findViewById(R.id.yearly_salary);
        yearlyBonus = (EditText) findViewById(R.id.yearly_bonus);
        leave = (EditText) findViewById(R.id.leave);
        paternalLeave = (EditText) findViewById(R.id.parental_leave);
        lifeInsurance = (EditText) findViewById(R.id.life_insurance);


        // Set the Edit Comparison Settings button to enable if all fields are filled
        yearlySalary.addTextChangedListener(watchForTextEntered);
        yearlyBonus.addTextChangedListener(watchForTextEntered);
        leave.addTextChangedListener(watchForTextEntered);
        paternalLeave.addTextChangedListener(watchForTextEntered);
        lifeInsurance.addTextChangedListener(watchForTextEntered);

        if (db.isComparisonWeightPresent()) {
            yearlySalary.setText(String.valueOf(db.getComparisonWeight(db.salaryWeight)));
            yearlyBonus.setText(String.valueOf(db.getComparisonWeight(db.bonusWeight)));
            leave.setText(String.valueOf(db.getComparisonWeight(db.leaveWeight)));
            paternalLeave.setText(String.valueOf(db.getComparisonWeight(db.parentalLeaveWeight)));
            lifeInsurance.setText(String.valueOf(db.getComparisonWeight(db.lifeInsuranceWeight)));
        }

        // When Save Job Offer button is clicked... add to the database
        edit_comparison_settings_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // PERFORM FIELD INPUT VALIDATIONS
                // All validations require a numeric input and positive whole number
                // yearly salary
                if (!isNumeric(yearlySalary.getText().toString())) {
                    Toast.makeText(ComparisonSettingsActivity.this,
                            "Enter a positive whole number for yearly salary", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    Integer c = Integer.parseInt(yearlySalary.getText().toString());
                    if (c < 0) {
                        Toast.makeText(ComparisonSettingsActivity.this,
                                "Enter a positive whole number for yearly salary", Toast.LENGTH_SHORT).show();
                    }
                }

                // yearly bonus
                if (!isNumeric(yearlyBonus.getText().toString())) {
                    Toast.makeText(ComparisonSettingsActivity.this,
                            "Enter a positive whole number for yearly salary", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    Integer c = Integer.parseInt(yearlyBonus.getText().toString());
                    if (c < 0) {
                        Toast.makeText(ComparisonSettingsActivity.this,
                                "Enter a positive whole number for yearly salary", Toast.LENGTH_SHORT).show();
                    }
                }

                // leave
                if (!isNumeric(leave.getText().toString())) {
                    Toast.makeText(ComparisonSettingsActivity.this,
                            "Enter a positive whole number for Leave", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    Integer c = Integer.parseInt(leave.getText().toString());
                    if (c < 0) {
                        Toast.makeText(ComparisonSettingsActivity.this,
                                "Enter a positive whole number for Leave", Toast.LENGTH_SHORT).show();
                    }
                }

                // paternalLeave
                if (!isNumeric(paternalLeave.getText().toString())) {
                    Toast.makeText(ComparisonSettingsActivity.this,
                            "Enter a positive whole number for Paternal Leave", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    Integer c = Integer.parseInt(paternalLeave.getText().toString());
                    if (c < 0) {
                        Toast.makeText(ComparisonSettingsActivity.this,
                                "Enter a positive whole number for Paternal Leave", Toast.LENGTH_SHORT).show();
                    }
                }

                // lifeInsurance
                if (!isNumeric(lifeInsurance.getText().toString())) {
                    Toast.makeText(ComparisonSettingsActivity.this,
                            "Enter a positive whole number for Life Insurance", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    Integer c = Integer.parseInt(lifeInsurance.getText().toString());
                    if (c < 0) {
                        Toast.makeText(ComparisonSettingsActivity.this,
                                "Enter a positive whole number for Life Insurance", Toast.LENGTH_SHORT).show();
                    }
                }

                // Once field input validations are passed, move onto updating the comparison settings
                ComparisonSettings comparisonSettings = new ComparisonSettings(
                        Integer.parseInt(yearlySalary.getText().toString()),
                        Integer.parseInt(yearlyBonus.getText().toString()),
                        Integer.parseInt(leave.getText().toString()),
                        Integer.parseInt(paternalLeave.getText().toString()),
                        Integer.parseInt(lifeInsurance.getText().toString())
                );

                db.setComparisonWeights(
                        Integer.parseInt(yearlySalary.getText().toString()),
                        Integer.parseInt(yearlyBonus.getText().toString()),
                        Integer.parseInt(leave.getText().toString()),
                        Integer.parseInt(paternalLeave.getText().toString()),
                        Integer.parseInt(lifeInsurance.getText().toString())
                );

                Toast.makeText(getApplicationContext(), "Settings Updated", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(v.getContext(), MainActivity.class);
                startActivity(intent);
            }
        });

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
            String salaryInput = yearlySalary.getText().toString().trim();
            String bonusInput = yearlyBonus.getText().toString().trim();
            String leaveInput = leave.getText().toString().trim();
            String mpLeaveInput = paternalLeave.getText().toString().trim();
            String lifeInsurenceInput = lifeInsurance.getText().toString().trim();

            edit_comparison_settings_button.setEnabled(
                    !salaryInput.isEmpty() && !bonusInput.isEmpty() && !leaveInput.isEmpty() &&
                            !mpLeaveInput.isEmpty() && !lifeInsurenceInput.isEmpty()
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
