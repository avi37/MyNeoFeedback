package com.example.admin.myneofeedback.Views;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

public class CheckFeedbacksActivity extends AppCompatActivity {

    FirebaseFirestore firebaseFirestore;

    ListView listView_feedbacks;
    TextView textView_noFB;
    ProgressBar progressBar;

    private List<String> dateList;
    private List<String> nameList;
    private List<String> numberList;
    private List<String> ratingTextList;
    private List<String> feedbackList;

    AdapterFeedbackList adapterFeedbackList;

    private int preLast;
    private DocumentSnapshot lastVisible;
    private boolean isLastItemReached;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_feedbacks);

        firebaseFirestore = FirebaseFirestore.getInstance();
        dateList = new ArrayList<>();
        nameList = new ArrayList<>();
        numberList = new ArrayList<>();
        ratingTextList = new ArrayList<>();
        feedbackList = new ArrayList<>();

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        listView_feedbacks = findViewById(R.id.checkFeedbacks_listView);
        textView_noFB = findViewById(R.id.checkFeedbacks_tv_noFB);
        progressBar = findViewById(R.id.checkFeedbacks_progressBar);

        getAllFeedbacks();

        /*listView_feedbacks.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                final int lastItem = firstVisibleItem + visibleItemCount;

                if (lastItem == totalItemCount) {

                    if (preLast != lastItem) {

                        Toast.makeText(getApplicationContext(), "Reached last item", Toast.LENGTH_SHORT).show();

                        getMoreFeedbacks();

                        preLast = lastItem;
                    }
                }

            }
        });*/


        // to get all feedback with pagination
        /*progressBar.setVisibility(View.VISIBLE);

        Query firstQuery = firebaseFirestore.collection("Feedbacks").orderBy("created_date", Query.Direction.DESCENDING).limit(7);

        firstQuery.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                textView_noFB.setVisibility(View.GONE);

                dateList.clear();
                nameList.clear();
                ratingTextList.clear();
                feedbackList.clear();


                for (DocumentSnapshot snapshot : documentSnapshots) {
                    dateList.add(snapshot.getString("created_date"));
                    nameList.add(snapshot.getString("name"));
                    ratingTextList.add(snapshot.getString("ratingText"));
                    feedbackList.add(snapshot.getString("feedbackText"));
                }

                String[] dateStringArray = dateList.toArray(new String[0]);
                String[] nameStringArray = nameList.toArray(new String[0]);
                String[] ratingTextStringArray = ratingTextList.toArray(new String[0]);
                String[] feedbackStringArray = feedbackList.toArray(new String[0]);

                adapterFeedbackList = new AdapterFeedbackList(getApplicationContext(), R.id.row_feedback_list_item, dateStringArray, nameStringArray, ratingTextStringArray, feedbackStringArray);
                listView_feedbacks.setAdapter(adapterFeedbackList);

                lastVisible = documentSnapshots.getDocuments().get(documentSnapshots.size() - 1);

                if (nameList.size() < 1) {
                    textView_noFB.setVisibility(View.VISIBLE);
                }

                progressBar.setVisibility(View.GONE);
            }
        });*/


    }


    private void getAllFeedbacks() {

        progressBar.setVisibility(View.VISIBLE);

        Query allDataQuery = firebaseFirestore.collection("Feedbacks").orderBy("created_date", Query.Direction.DESCENDING);

        allDataQuery.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                textView_noFB.setVisibility(View.GONE);

                dateList.clear();
                nameList.clear();
                numberList.clear();
                ratingTextList.clear();
                feedbackList.clear();


                for (DocumentSnapshot snapshot : documentSnapshots) {
                    dateList.add(snapshot.getString("created_date"));
                    nameList.add(snapshot.getString("name"));
                    numberList.add(snapshot.getString("number"));
                    ratingTextList.add(snapshot.getString("ratingText"));
                    feedbackList.add(snapshot.getString("feedbackText"));
                }

                String[] dateStringArray = dateList.toArray(new String[0]);
                String[] nameStringArray = nameList.toArray(new String[0]);
                String[] numberStringArray = numberList.toArray(new String[0]);
                String[] ratingTextStringArray = ratingTextList.toArray(new String[0]);
                String[] feedbackStringArray = feedbackList.toArray(new String[0]);

                adapterFeedbackList = new AdapterFeedbackList(getApplicationContext(), R.id.row_feedback_list_item, dateStringArray, nameStringArray, numberStringArray, ratingTextStringArray, feedbackStringArray);
                listView_feedbacks.setAdapter(adapterFeedbackList);

                lastVisible = documentSnapshots.getDocuments().get(documentSnapshots.size() - 1);

                if (nameList.size() < 1) {
                    textView_noFB.setVisibility(View.VISIBLE);
                }

                adapterFeedbackList.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);
            }
        });
    }


    private void getMoreFeedbacks() {

        if (!isLastItemReached) {

            Query nextQuery = firebaseFirestore.collection("Feedbacks").orderBy("created_date", Query.Direction.DESCENDING).startAfter(lastVisible).limit(5);

            nextQuery.addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

                    if (!queryDocumentSnapshots.isEmpty()) {

                        lastVisible = queryDocumentSnapshots.getDocuments().get(queryDocumentSnapshots.size() - 1);

                        for (DocumentChange doc : queryDocumentSnapshots.getDocumentChanges()) {

                            dateList.add(doc.getDocument().getString("created_date"));
                            nameList.add(doc.getDocument().getString("name"));
                            ratingTextList.add(doc.getDocument().getString("ratingText"));
                            feedbackList.add(doc.getDocument().getString("feedbackText"));

                        }

                        adapterFeedbackList.notifyDataSetChanged();
                    }

                    if (queryDocumentSnapshots.size() < 5) {
                        isLastItemReached = true;
                    }

                }
            });

        } else {
            Toast.makeText(getApplicationContext(), "All feedback showed", Toast.LENGTH_LONG).show();

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
        }
        return super.onOptionsItemSelected(item);
    }


//-------------------------------------------- Adapter Class ----------------------------------------------//


    public class AdapterFeedbackList extends ArrayAdapter<String> {

        private Context ctx;

        private String[] dateArray;
        private String[] nameArray;
        private String[] numberArray;
        private String[] ratingTextArray;
        private String[] feedbackArray;

        AdapterFeedbackList(@NonNull Context context, int resource, String[] dateArray, String[] nameArray, String[] numberArray, String[] ratingTextArray, String[] feedbackArray) {
            super(context, resource);

            this.ctx = context;
            this.dateArray = dateArray;
            this.nameArray = nameArray;
            this.numberArray = numberArray;
            this.ratingTextArray = ratingTextArray;
            this.feedbackArray = feedbackArray;
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
            View row = inflater.inflate(R.layout.row_feedback_list_item, parent, false);

            TextView tv_date = row.findViewById(R.id.row_feedbackItem_tv_date);
            tv_date.setText(dateArray[position]);

            TextView tv_name = row.findViewById(R.id.row_feedbackItem_tv_name);
            tv_name.setText(nameArray[position]);

            TextView tv_number = row.findViewById(R.id.row_feedbackItem_tv_number);
            tv_number.setText(numberArray[position]);

            TextView tv_ratingText = row.findViewById(R.id.row_feedbackItem_tv_ratingText);
            tv_ratingText.setText(ratingTextArray[position]);

            TextView tv_feedback = row.findViewById(R.id.row_feedbackItem_tv_feedback);
            tv_feedback.setText(feedbackArray[position]);

            row.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent i = new Intent(CheckFeedbacksActivity.this, FeedbackItemActivity.class);

                    i.putExtra("date", dateArray[position]);
                    i.putExtra("name", nameArray[position]);
                    i.putExtra("number", numberArray[position]);
                    i.putExtra("ratingText", ratingTextArray[position]);
                    i.putExtra("feedbackText", feedbackArray[position]);

                    startActivity(i);

                }
            });

            return row;
        }

        @Override
        public int getCount() {
            return nameArray.length;
        }


        @Override
        public void notifyDataSetChanged() {
            super.notifyDataSetChanged();
        }


    }


}
