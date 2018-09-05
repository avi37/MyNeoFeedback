package com.example.admin.myneofeedback.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.admin.myneofeedback.R;
import com.example.admin.myneofeedback.Views.AddCustomerActivity;
import com.example.admin.myneofeedback.Views.AddFeedbackActivity;
import com.example.admin.myneofeedback.Views.BirthdayListActivity;
import com.example.admin.myneofeedback.Views.CheckFeedbacksActivity;
import com.example.admin.myneofeedback.Views.CustomerListActivity;

public class DashboardFragment extends Fragment implements View.OnClickListener {

    View main_view;

    CardView cardView_addFeedback, cardView_checkFeedbacks, cardView_addCustomer, cardView_userDetails, cardView_checkBirthdays;
    ImageView iv_dash_birthday_noty_dot;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        main_view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        cardView_addFeedback = main_view.findViewById(R.id.dash_card_add_feedback);
        cardView_checkFeedbacks = main_view.findViewById(R.id.dash_card_check_feedbacks);
        cardView_addCustomer = main_view.findViewById(R.id.dash_card_add_customer);
        cardView_userDetails = main_view.findViewById(R.id.dash_card_user_details);
        cardView_checkBirthdays = main_view.findViewById(R.id.dash_card_check_birthdays);
        iv_dash_birthday_noty_dot = main_view.findViewById(R.id.dash_birthday_noty_dot);

        cardView_addFeedback.setOnClickListener(this);
        cardView_checkFeedbacks.setOnClickListener(this);
        cardView_addCustomer.setOnClickListener(this);
        cardView_userDetails.setOnClickListener(this);
        cardView_checkBirthdays.setOnClickListener(this);

        return main_view;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dash_card_add_feedback:
                openAddFeedback();
                break;

            case R.id.dash_card_check_feedbacks:
                openCheckFeedbacks();
                break;

            case R.id.dash_card_add_customer:
                openAddCustomer();
                break;

            case R.id.dash_card_user_details:
                openUserDetails();
                break;

            case R.id.dash_card_check_birthdays:
                openTodayBirthday();
                break;

        }
    }


    private void openAddFeedback() {
        Intent i = new Intent(getContext(), AddFeedbackActivity.class);
        startActivity(i);
    }

    private void openCheckFeedbacks() {
        Intent i = new Intent(getContext(), CheckFeedbacksActivity.class);
        startActivity(i);
    }

    private void openAddCustomer() {
        Intent i = new Intent(getContext(), AddCustomerActivity.class);
        startActivity(i);
    }

    private void openUserDetails() {
        Intent i = new Intent(getContext(), CustomerListActivity.class);
        startActivity(i);
    }

    private void openTodayBirthday() {
        Intent i = new Intent(getContext(), BirthdayListActivity.class);
        startActivity(i);
    }

}
