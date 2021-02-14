package com.madapps.marketappprototype1.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.madapps.marketappprototype1.MainActivity;
import com.madapps.marketappprototype1.R;
import com.madapps.marketappprototype1.activities.admin.AddUser;
import com.madapps.marketappprototype1.activities.admin.RandomQuery;
import com.madapps.marketappprototype1.activities.admin.ordertoday.TodaysOrder;
import com.madapps.marketappprototype1.activities.admin.summary.SummaryActivity;
import com.madapps.marketappprototype1.activities.admin.TodayOrders;
import com.madapps.marketappprototype1.onetimelogin.Shared;

public class AdminActivity extends AppCompatActivity {


    Button adduseracty, todayorder,summary;
    Button orderfortoday,ItemMaster,logout;
    TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        textView = findViewById(R.id.adminusername);
        Shared ss = new Shared(getApplicationContext());
        textView.setText(ss.typeadminuser());


        ItemMaster = findViewById(R.id.additem);
        ItemMaster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent o = new Intent(AdminActivity.this, ItemMaster.class);
                startActivity(o);
                overridePendingTransition(0, 0);
                o.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

            }
        });

        summary = findViewById(R.id.summary);
        summary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent o = new Intent(AdminActivity.this, SummaryActivity.class);
                startActivity(o);
                overridePendingTransition(0, 0);
                o.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

            }
        });


        adduseracty = findViewById(R.id.adduseracty);

        adduseracty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent o = new Intent(AdminActivity.this, AddUser.class);
                startActivity(o);
                overridePendingTransition(0, 0);
                o.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            }
        });


        todayorder = findViewById(R.id.todayorder);
        todayorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent o = new Intent(AdminActivity.this, TodaysOrder.class);
                startActivity(o);
                overridePendingTransition(0, 0);
                o.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            }
        });


        orderfortoday = findViewById(R.id.orderfortoday);
        orderfortoday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent o = new Intent(AdminActivity.this, RandomQuery.class);
                startActivity(o);
                overridePendingTransition(0, 0);
                o.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            }
        });







        logout=findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Shared sh = new Shared(AdminActivity.this);
                sh.second("returnednull","returnednull","returnednull");


                Intent o = new Intent(AdminActivity.this, MainActivity.class);
                startActivity(o);
                overridePendingTransition(0, 0);
                o.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent o = new Intent(AdminActivity.this, AdminActivity.class);
        startActivity(o);
        overridePendingTransition(0, 0);
        o.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

    }
}
