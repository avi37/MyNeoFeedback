package com.example.admin.myneofeedback.Views;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.admin.myneofeedback.R;

public class CustomerItemActivity extends AppCompatActivity {

    TextView textView_name, textView_gender, textView_dob, textView_number, textView_email;

    private String name, gender, dob, number, email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_item);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        textView_name = findViewById(R.id.customerItem_tv_name);
        textView_gender = findViewById(R.id.customerItem_tv_gender);
        textView_dob = findViewById(R.id.customerItem_tv_dob);
        textView_number = findViewById(R.id.customerItem_tv_number);
        textView_email = findViewById(R.id.customerItem_tv_email);

        name = getIntent().getExtras().getString("name");
        gender = getIntent().getExtras().getString("gender");
        dob = getIntent().getExtras().getString("dob");
        number = getIntent().getExtras().getString("number");
        email = getIntent().getExtras().getString("email");

        setAllData();

    }

    private void setAllData() {
        textView_name.setText(name);
        textView_gender.setText(gender);
        textView_dob.setText(dob);
        textView_number.setText(number);
        textView_email.setText(email);
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
