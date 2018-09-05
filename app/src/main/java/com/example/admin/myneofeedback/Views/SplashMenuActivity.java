package com.example.admin.myneofeedback.Views;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.admin.myneofeedback.Common.SessionManager;
import com.example.admin.myneofeedback.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;


public class SplashMenuActivity extends AppCompatActivity {

    SessionManager sessionManager;

    ImageView imageView_add_feedback, imageView_admin_login, imageView_check_menu;
    AdView adView;
    //Button button_send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sessionManager = new SessionManager(this);

        MobileAds.initialize(this, "ca-app-pub-9121734075631945~1022391491");

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        if (sessionManager.isLoggedIn()) {
            finish();
            startActivity(new Intent(this, MainNavigationActivity.class));

        } else {
            setContentView(R.layout.activity_splash_menu);

            imageView_add_feedback = findViewById(R.id.splashMenu_iv_add_feedback);
            imageView_admin_login = findViewById(R.id.splashMenu_iv_admin_login);
            imageView_check_menu = findViewById(R.id.splashMenu_iv_check_menu);
            adView = findViewById(R.id.splashMenu_adView);
            //button_send = findViewById(R.id.send_btn);

            AdRequest adRequest = new AdRequest.Builder()
                    .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                    .build();
            adView.loadAd(adRequest);


            imageView_add_feedback.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    methodAddFeedback();
                }
            });


            imageView_admin_login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showAlertBox();
                }
            });


            imageView_check_menu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    methodCheckMenu();
                }
            });


           /* button_send.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    try {
                        String str_url, mobile, pwd, msg, toMobile, api_key;

                        mobile = "9173076076";
                        pwd = "Avi4337";
                        msg = "Test Message";        //This text will be sent to Customer.
                        toMobile = "9173076076";
                        api_key = "www.aSW1X59pkmfoeGMcl6";

                        str_url = "https://smsapi.engineeringtgr.com/send/?Mobile=" + mobile + "&Password=" + pwd + "&Message=" + msg + "&To=" + toMobile + "&Key=" + api_key;

                        
                        URL url = new URL(str_url);
                        URLConnection urlcon = url.openConnection();
                        InputStream stream = urlcon.getInputStream();
                        int i;
                        String response = "";
                        while ((i = stream.read()) != -1) {
                            response += (char) i;
                        }
                        if (response.contains("success")) {
                            System.out.println("Successfully send SMS");
                            Toast.makeText(getApplicationContext(), "Successfully send SMS", Toast.LENGTH_LONG).show();

                        } else {
                            System.out.println(response);
                            Toast.makeText(getApplicationContext(), "Response: " + response, Toast.LENGTH_LONG).show();

                        }
                    } catch (IOException e) {
                        System.out.println(e.getMessage());
                    }

                }
            });*/

        }

    }


    private void methodAddFeedback() {
        Intent i = new Intent(getApplicationContext(), AddFeedbackActivity.class);
        startActivity(i);
    }

    private void methodCheckMenu() {
        Toast.makeText(this, "Taking you to our delicious menu... ", Toast.LENGTH_LONG).show();
    }

    private void showAlertBox() {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Are you an Admin?");
        alertDialog.setMessage("This option is only for the admin.\nSo if you are an admin tap YES else tap NO.");

        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(i);
            }
        });
        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                Toast.makeText(getApplicationContext(), "Please proceed for giving feedback", Toast.LENGTH_SHORT).show();
            }
        });
        alertDialog.show();

    }


}
