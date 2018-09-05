package com.example.admin.myneofeedback.Views;

import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.admin.myneofeedback.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class BirthdayListActivity extends AppCompatActivity {

    FirebaseFirestore firebaseFirestore;

    ListView listView_birthdays;
    TextView textView_noBT;
    ProgressBar progressBar;

    private List<String> nameList;
    private List<String> numberList;
    private List<String> emailList;

    String str_today_date;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_birthday_list);

        firebaseFirestore = FirebaseFirestore.getInstance();
        nameList = new ArrayList<>();
        numberList = new ArrayList<>();
        emailList = new ArrayList<>();

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        listView_birthdays = findViewById(R.id.birthdayList_listView);
        textView_noBT = findViewById(R.id.birthdayList_tv_noBT);
        progressBar = findViewById(R.id.birthdayList_progressBar);

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        str_today_date = sdf.format(cal.getTime());
        str_today_date = str_today_date.substring(0, 5);

    }


    @Override
    protected void onResume() {
        super.onResume();

        getBirthdayData(str_today_date);
    }


    private void getBirthdayData(final String str_today_date) {
        progressBar.setVisibility(View.VISIBLE);
        final String[] dob = new String[1];

        CollectionReference cref = firebaseFirestore.collection("Customer Details");
        cref.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                for (DocumentSnapshot ds : queryDocumentSnapshots) {

                    dob[0] = ds.getString("dob");
                    if (dob[0] != null) {
                        dob[0] = dob[0].substring(0, 5);
                    }

                    if (dob[0] != null && dob[0].equals(str_today_date)) {
                        nameList.add(ds.getString("name"));
                        numberList.add(ds.getString("number"));
                        emailList.add(ds.getString("email"));
                    }

                }

                String[] nameStringArray = nameList.toArray(new String[0]);
                String[] numberStringArray = numberList.toArray(new String[0]);
                String[] emailStringArray = emailList.toArray(new String[0]);

                AdapterBirthdayList adapterBirthdayList = new AdapterBirthdayList(getApplicationContext(), R.id.row_birthday_list_item, nameStringArray, numberStringArray, emailStringArray);
                adapterBirthdayList.notifyDataSetChanged();
                listView_birthdays.setAdapter(adapterBirthdayList);

                if (nameList.size() < 1) {
                    textView_noBT.setVisibility(View.VISIBLE);
                }
                progressBar.setVisibility(View.GONE);

            }
        });

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
        }
        return super.onOptionsItemSelected(item);
    }


//----------------------------------- Adapter Class -----------------------------------------//

    public class AdapterBirthdayList extends ArrayAdapter<String> {

        private Context ctx;

        private String[] nameArray;
        private String[] numberArray;
        private String[] emailArray;

        AdapterBirthdayList(@NonNull Context context, int resource, String[] nameArray, String[] numberArray, String[] emailArray) {
            super(context, resource);

            this.ctx = context;
            this.nameArray = nameArray;
            this.numberArray = numberArray;
            this.emailArray = emailArray;
        }

        @Override
        public View getDropDownView(int position, View convertView, @NonNull ViewGroup parent) {
            return getCustomView(position, convertView, parent);
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, @NonNull ViewGroup parent) {
            return getCustomView(position, convertView, parent);
        }

        View getCustomView(final int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = inflater.inflate(R.layout.row_birthday_list_item, parent, false);

            TextView tv_name = row.findViewById(R.id.row_birthdayItem_tv_name);
            tv_name.setText(nameArray[position]);

            TextView tv_number = row.findViewById(R.id.row_birthdayItem_tv_number);
            tv_number.setText(numberArray[position]);

            TextView tv_email = row.findViewById(R.id.row_birthdayItem_tv_email);
            tv_email.setText(emailArray[position]);

            row.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });


            return row;
        }


        @Override
        public int getCount() {
            return nameArray.length;
        }

    }


}

