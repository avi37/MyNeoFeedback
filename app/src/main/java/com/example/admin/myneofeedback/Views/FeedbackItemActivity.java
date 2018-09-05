package com.example.admin.myneofeedback.Views;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.admin.myneofeedback.R;

public class FeedbackItemActivity extends AppCompatActivity {

    TextView textView_date, textView_name, textView_number, textView_ratingText, textView_feedbackText;

    private String date, name, number, ratingText, feedbackText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback_item);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);


        textView_date = findViewById(R.id.feedbackItem_tv_date);
        textView_name = findViewById(R.id.feedbackItem_tv_name);
        textView_number = findViewById(R.id.feedbackItem_tv_number);
        textView_ratingText = findViewById(R.id.feedbackItem_tv_ratingText);
        textView_feedbackText = findViewById(R.id.feedbackItem_tv_feedbackText);

        date = getIntent().getExtras().getString("date");
        name = getIntent().getExtras().getString("name");
        number = getIntent().getExtras().getString("number");
        ratingText = getIntent().getExtras().getString("ratingText");
        feedbackText = getIntent().getExtras().getString("feedbackText");

        setAllData();

    }


    private void setAllData() {
        textView_date.setText(date);
        textView_name.setText(name);
        textView_number.setText(number);
        textView_ratingText.setText(ratingText);
        textView_feedbackText.setText(feedbackText);
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
