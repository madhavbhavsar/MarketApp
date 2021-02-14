package com.madapps.marketappprototype1.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.madapps.marketappprototype1.MainActivity;
import com.madapps.marketappprototype1.R;
import com.madapps.marketappprototype1.activities.user.NewEntry;
import com.madapps.marketappprototype1.activities.user.ShowEntryActivity;
import com.madapps.marketappprototype1.activities.user.TodaysEntryActivity;
import com.madapps.marketappprototype1.onetimelogin.Shared;

public class UserActivity extends AppCompatActivity {


    Button newentry,todayentry,allentry,ItemMaster,logout;
    TextView username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        newentry  =(Button) findViewById(R.id.newentry);
        todayentry  =(Button) findViewById(R.id.todayentry);
        allentry  = (Button)findViewById(R.id.allentry);
        username = (TextView) findViewById(R.id.username);


        Shared ss = new Shared(getApplicationContext());
        username.setText(ss.typeadminuser());

        newentry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent o = new Intent(UserActivity.this, NewEntry.class);
                startActivity(o);
                overridePendingTransition(0, 0);
                o.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            }
        });


        ItemMaster = findViewById(R.id.additem);
        ItemMaster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent o = new Intent(UserActivity.this, ItemMaster.class);
                startActivity(o);
                overridePendingTransition(0, 0);
                o.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

            }
        });

        logout=findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Shared sh = new Shared(UserActivity.this);
                sh.second("returnednull","returnednull","returnednull");


                Intent o = new Intent(UserActivity.this, MainActivity.class);
                startActivity(o);
                overridePendingTransition(0, 0);
                o.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            }
        });

        todayentry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent o = new Intent(UserActivity.this, TodaysEntryActivity.class);
                startActivity(o);
                overridePendingTransition(0, 0);
                o.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            }
        });


        allentry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent o = new Intent(UserActivity.this, ShowEntryActivity.class);
                startActivity(o);
                overridePendingTransition(0, 0);
                o.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            }
        });

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent o = new Intent(UserActivity.this, UserActivity.class);
        startActivity(o);
        overridePendingTransition(0, 0);
        o.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

    }
}
