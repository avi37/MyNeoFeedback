package com.example.admin.myneofeedback.Views;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.myneofeedback.R;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class CustomerListActivity extends AppCompatActivity {

    FirebaseFirestore firebaseFirestore;

    ListView listView_customers;
    TextView textView_noFB;
    ProgressBar progressBar;

    private List<String> nameList;
    private List<String> genderList;
    private List<String> dobList;
    private List<String> numberList;
    private List<String> emailList;


    private int preLast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_list);

        firebaseFirestore = FirebaseFirestore.getInstance();
        nameList = new ArrayList<>();
        genderList = new ArrayList<>();
        dobList = new ArrayList<>();
        numberList = new ArrayList<>();
        emailList = new ArrayList<>();

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        listView_customers = findViewById(R.id.customerList_listView);
        textView_noFB = findViewById(R.id.customerList_tv_noCA);
        progressBar = findViewById(R.id.customerList_progressBar);

        getAllCustomerData();

        /*listView_customers.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                final int lastItem = firstVisibleItem + visibleItemCount;

                if (lastItem == totalItemCount) {
                    if (preLast != lastItem) {
                        Toast.makeText(getApplicationContext(), "Last Item", Toast.LENGTH_SHORT).show();
                        preLast = lastItem;
                    }
                }

            }
        });*/


    }


    private void getCustomerData() {

        progressBar.setVisibility(View.VISIBLE);

        Query firstQuery = firebaseFirestore.collection("Customer Details")
                .orderBy("name", Query.Direction.ASCENDING)
                .limit(7);

        firstQuery.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                textView_noFB.setVisibility(View.GONE);

                nameList.clear();
                genderList.clear();
                dobList.clear();
                numberList.clear();
                emailList.clear();

                /*// Get the last visible document
                DocumentSnapshot lastVisible = documentSnapshots.getDocuments().get(documentSnapshots.size() - 1);

                // Construct a new query starting at this document,
                // get the next 25 cities.
                Query nextQuery = firebaseFirestore.collection("Customer Details")
                        .orderBy("name", Query.Direction.ASCENDING)
                        .limit(7);

                // Use the query for pagination
                // ...
*/

                for (DocumentSnapshot snapshot : documentSnapshots) {
                    nameList.add(snapshot.getString("name"));
                    genderList.add(snapshot.getString("gender"));
                    dobList.add(snapshot.getString("dob"));
                    numberList.add(snapshot.getString("number"));
                    emailList.add(snapshot.getString("email"));
                }

                String[] nameStringArray = nameList.toArray(new String[0]);
                String[] genderStringArray = genderList.toArray(new String[0]);
                String[] dobStringArray = dobList.toArray(new String[0]);
                String[] numberStringArray = numberList.toArray(new String[0]);
                String[] emailStringArray = emailList.toArray(new String[0]);

                AdapterCustomerList adapterCustomerList = new AdapterCustomerList(getApplicationContext(), R.id.row_customer_list_item, nameStringArray, genderStringArray, dobStringArray, numberStringArray, emailStringArray);
                adapterCustomerList.notifyDataSetChanged();
                listView_customers.setAdapter(adapterCustomerList);

                if (nameList.size() < 1) {
                    textView_noFB.setVisibility(View.VISIBLE);
                }

                progressBar.setVisibility(View.GONE);
            }
        });

    }

    private void getAllCustomerData() {
        progressBar.setVisibility(View.VISIBLE);

        Query firstQuery = firebaseFirestore.collection("Customer Details")
                .orderBy("name", Query.Direction.ASCENDING);

        firstQuery.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                textView_noFB.setVisibility(View.GONE);

                nameList.clear();
                genderList.clear();
                dobList.clear();
                numberList.clear();
                emailList.clear();

                for (DocumentSnapshot snapshot : documentSnapshots) {
                    nameList.add(snapshot.getString("name"));
                    genderList.add(snapshot.getString("gender"));
                    dobList.add(snapshot.getString("dob"));
                    numberList.add(snapshot.getString("number"));
                    emailList.add(snapshot.getString("email"));
                }

                String[] nameStringArray = nameList.toArray(new String[0]);
                String[] genderStringArray = genderList.toArray(new String[0]);
                String[] dobStringArray = dobList.toArray(new String[0]);
                String[] numberStringArray = numberList.toArray(new String[0]);
                String[] emailStringArray = emailList.toArray(new String[0]);

                AdapterCustomerList adapterCustomerList = new AdapterCustomerList(getApplicationContext(), R.id.row_customer_list_item, nameStringArray, genderStringArray, dobStringArray, numberStringArray, emailStringArray);
                adapterCustomerList.notifyDataSetChanged();
                listView_customers.setAdapter(adapterCustomerList);

                if (nameList.size() < 1) {
                    textView_noFB.setVisibility(View.VISIBLE);
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

    public class AdapterCustomerList extends ArrayAdapter<String> {

        private Context ctx;

        private String[] nameArray;
        private String[] genderArray;
        private String[] dobArray;
        private String[] numberArray;
        private String[] emailArray;

        AdapterCustomerList(@NonNull Context context, int resource, String[] nameArray, String[] genderArray, String[] dobArray, String[] numberArray, String[] emailArray) {
            super(context, resource);

            this.ctx = context;
            this.nameArray = nameArray;
            this.numberArray = numberArray;
            this.genderArray = genderArray;
            this.dobArray = dobArray;
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
            View row = inflater.inflate(R.layout.row_customer_list_item, parent, false);

            TextView tv_name = row.findViewById(R.id.row_customerItem_tv_name);
            tv_name.setText(nameArray[position]);

            TextView tv_number = row.findViewById(R.id.row_customerItem_tv_number);
            tv_number.setText(numberArray[position]);

            row.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent i = new Intent(CustomerListActivity.this, CustomerItemActivity.class);

                    i.putExtra("name", nameArray[position]);
                    i.putExtra("gender", genderArray[position]);
                    i.putExtra("dob", dobArray[position]);
                    i.putExtra("number", numberArray[position]);
                    i.putExtra("email", emailArray[position]);

                    startActivity(i);

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
