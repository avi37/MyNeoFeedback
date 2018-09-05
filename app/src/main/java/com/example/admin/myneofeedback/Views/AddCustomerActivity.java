package com.example.admin.myneofeedback.Views;

import android.app.ProgressDialog;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.myneofeedback.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddCustomerActivity extends AppCompatActivity {

    FirebaseFirestore firebaseFirestore;

    View main_view;
    RadioGroup radioGroup_gender;
    RadioButton radioButton_selectedGender;
    EditText editText_name, editText_number, editText_email;
    Spinner spinner_date, spinner_month, spinner_year;
    Button button_submit;

    String name, number, email;
    String dob_date, dob_month, dob_year, dob;
    String date;
    Long tsLong;
    String timestamp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_customer);

        main_view = findViewById(R.id.activity_add_customer);

        firebaseFirestore = FirebaseFirestore.getInstance();

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        radioGroup_gender = findViewById(R.id.addCustomer_rdGroup_gender);
        editText_name = findViewById(R.id.addCustomer_et_name);
        editText_number = findViewById(R.id.addCustomer_et_number);
        editText_email = findViewById(R.id.addCustomer_et_email);
        spinner_date = findViewById(R.id.addCustomer_spinner_date);
        spinner_month = findViewById(R.id.addCustomer_spinner_month);
        spinner_year = findViewById(R.id.addCustomer_spinner_year);
        button_submit = findViewById(R.id.addCustomer_btn_submit);


        radioGroup_gender.clearCheck();
        radioGroup_gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                radioButton_selectedGender = group.findViewById(checkedId);
            }
        });

        setDateSpinner();
        setMonthSpinner();
        setYearSpinner();

        button_submit.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                methodSubmit();
            }
        });

    }

    private void setDateSpinner() {
        List<String> date = new ArrayList<>();
        for (int i = 1; i <= 31; i++) {
            date.add(String.valueOf(i));
        }

        ArrayAdapter<String> dateAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, date);

        dateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner_date.setAdapter(dateAdapter);
    }

    private void setMonthSpinner() {
        List<String> month = new ArrayList<>();
        month.add("January");
        month.add("February");
        month.add("March");
        month.add("April");
        month.add("May");
        month.add("June");
        month.add("July");
        month.add("August");
        month.add("September");
        month.add("October");
        month.add("November");
        month.add("December");

        ArrayAdapter<String> monthAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, month);

        monthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner_month.setAdapter(monthAdapter);
    }

    private void setYearSpinner() {
        List<String> year = new ArrayList<>();
        for (int i = 2018; i >= 1980; i--) {
            year.add(String.valueOf(i));
        }

        ArrayAdapter<String> yearAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, year);

        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner_year.setAdapter(yearAdapter);
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    private void methodSubmit() {

        name = editText_name.getText().toString();
        number = editText_number.getText().toString();
        email = editText_email.getText().toString();

        if (radioGroup_gender.getCheckedRadioButtonId() == -1) {
            Snackbar.make(main_view, "Please select your Gender", Snackbar.LENGTH_SHORT).show();
            radioGroup_gender.requestFocus();
        } else if (name.equals("")) {
            editText_name.setError("Name is required");
            editText_name.requestFocus();
        } else if (name.length() < 3) {
            editText_name.setError("Enter proper name");
            editText_name.requestFocus();
        } else if (number.equals("")) {
            editText_number.setError("Number is required");
            editText_number.requestFocus();
        } else if (!isValidNumber(number)) {
            editText_number.setError("Number is not valid");
            editText_number.requestFocus();
        } else if (email.equals("")) {
            editText_email.setError("E-mail is required");
            editText_email.requestFocus();
        } else if (isValidEmail(email)) {
            editText_email.setError("E-mail is not valid");
            editText_email.requestFocus();
        } else {
            addCustomerToDB();
        }


    }


    private void addCustomerToDB() {
        final ProgressDialog dialog = ProgressDialog.show(this, "", "Adding this customer. Please wait...", true);

        final boolean[] isExisting = {false};

        CollectionReference cref = firebaseFirestore.collection("Customer Details");
        Query q1 = cref.whereEqualTo("number", number);
        q1.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                for (DocumentSnapshot ds : queryDocumentSnapshots) {
                    String _number;
                    _number = ds.getString("number");

                    if (_number != null && _number.equals(number)) {
                        isExisting[0] = true;
                        break;
                    }

                }

                if (!isExisting[0]) {

                    date = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date());
                    tsLong = System.currentTimeMillis() / 1000;
                    timestamp = tsLong.toString();

                    dob_date = spinner_date.getSelectedItem().toString();
                    if (Integer.parseInt(dob_date) < 9) {
                        dob_date = "0" + dob_date;
                    }
                    dob_month = spinner_month.getSelectedItem().toString();
                    String int_month = getNumericMonth(dob_month);
                    dob_year = spinner_year.getSelectedItem().toString();

                    dob = dob_date + "-" + int_month + "-" + dob_year;

                    Map<String, Object> customer_obj = new HashMap<>();
                    customer_obj.put("timestamp", timestamp);
                    customer_obj.put("created_date", date);
                    customer_obj.put("gender", radioButton_selectedGender.getText());
                    customer_obj.put("name", name);
                    customer_obj.put("number", number);
                    customer_obj.put("email", email);
                    customer_obj.put("dob", dob);

                    firebaseFirestore.collection("Customer Details").document()
                            .set(customer_obj)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    resetAllFields();
                                    dialog.dismiss();
                                    Toast.makeText(getApplicationContext(), "Customer added successfully", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    dialog.dismiss();
                                    Toast.makeText(getApplicationContext(), "Some error occurred in adding customer to database.\nPlease try after sometime", Toast.LENGTH_SHORT).show();
                                }
                            });

                } else {
                    dialog.dismiss();
                    Snackbar.make(main_view, "This customer is already added.\nPlease add other.", Snackbar.LENGTH_LONG).show();
                }
            }
        });

    }


    private boolean isValidNumber(String number) {
        Pattern p;
        Matcher m;

        String NUMBER_STRING = "^[6-9]\\d{9}$";
        p = Pattern.compile(NUMBER_STRING);
        m = p.matcher(number);

        return m.matches();
    }

    private boolean isValidEmail(String email) {
        Pattern p;
        Matcher m;

        String NUMBER_STRING = "[a-zA-Z0-9._-]+@[a-z]+\\\\.+[a-z]+";
        p = Pattern.compile(NUMBER_STRING);
        m = p.matcher(email);

        return m.matches();
    }

    private void resetAllFields() {
        editText_name.setText("");
        editText_number.setText("");
        editText_email.setText("");

        editText_name.requestFocus();
    }

    private String getNumericMonth(String month) {
        String int_month = null;

        switch (month) {

            case "January":
                int_month = "01";
                break;

            case "February":
                int_month = "02";
                break;

            case "March":
                int_month = "03";
                break;

            case "April":
                int_month = "04";
                break;

            case "May":
                int_month = "05";
                break;

            case "June":
                int_month = "06";
                break;

            case "July":
                int_month = "07";
                break;

            case "August":
                int_month = "08";
                break;

            case "September":
                int_month = "09";
                break;

            case "October":
                int_month = "10";
                break;

            case "November":
                int_month = "11";
                break;

            case "December":
                int_month = "12";
                break;
        }

        return int_month;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
        }
        return super.onOptionsItemSelected(item);
    }

}
