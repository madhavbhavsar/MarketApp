package com.madapps.marketappprototype1.activities.admin;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;


import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.madapps.marketappprototype1.R;
import com.madapps.marketappprototype1.activities.ItemMaster;
import com.madapps.marketappprototype1.activities.admin.ordertoday.ItemsAdapter;
import com.madapps.marketappprototype1.activities.admin.ordertoday.OrdersAdapter;
import com.madapps.marketappprototype1.activities.admin.ordertoday.OrdersModel;
import com.madapps.marketappprototype1.activities.admin.summary.SummaryActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AllAdminActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView;


    TextView datepicker;
    Button load;
    RecyclerView rv;
    OrdersAdapter adapter, adapter12, adapter123;
    ItemsAdapter adapter2;
    RecyclerView.LayoutManager manager1;

    AutoCompleteTextView usernameforadmin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_admin);


        usernameforadmin = findViewById(R.id.usernameforadmin);
        //func ofor setting user name adapter
        final ArrayList<String> userarrayList = new ArrayList<>();
        final ArrayList<String> usercodeList = new ArrayList<>();
        FirebaseFirestore.getInstance().collection("AcMast").orderBy("userid").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                if (error != null) {
                    Log.w("NewEntry", "Listen failed.", error);
                    return;
                }
                for (QueryDocumentSnapshot doc : value) {

                    if (doc.get("user") != null) {
                        if (!usercodeList.contains(doc.getString("userid"))) {
                            userarrayList.add(doc.getString("username"));
                            usercodeList.add(doc.getString("userid"));
                            //  itemtypearrayList.add(doc.getString("ItType"));
                        }
                    }
                }
                Log.i("usercodeshere ", String.valueOf(usercodeList));
            }
        });
        Log.i("usercodeshere ", String.valueOf(usercodeList));
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, userarrayList);
        usernameforadmin.setAdapter(arrayAdapter);
        final String[] usercode = {""};
        usernameforadmin.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                int pos = userarrayList.indexOf(parent.getItemAtPosition(position).toString());

                usercode[0] = usercodeList.get(pos);

            }
        });


        //funcuseradapter(usernameforadmin,usercode[0]);
        //


        datepicker = findViewById(R.id.datepicker);
        load = findViewById(R.id.loadbtn);
        rv = findViewById(R.id.todayrv);
        manager1 = new LinearLayoutManager(this);
        rv.setLayoutManager(manager1);

        datepicker.setText(new SimpleDateFormat("dd-MM-yyyy").format(new Date()));

        final Calendar myCalendar = Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel(myCalendar);
            }

        };
        datepicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new DatePickerDialog(AllAdminActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();


            }
        });

        Query query = FirebaseFirestore.getInstance().collection("AcMast").whereArrayContains("OrderDates", new SimpleDateFormat("dd-MM-yyyy").format(new Date()));

        FirestoreRecyclerOptions<OrdersModel> options = new FirestoreRecyclerOptions.Builder<OrdersModel>().setQuery(query, OrdersModel.class).build();

        adapter = new OrdersAdapter(options, this, new SimpleDateFormat("dd-MM-yyyy").format(new Date()));
        adapter.notifyDataSetChanged();
        rv.setAdapter(adapter);

        load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (datepicker.getText().toString().isEmpty()) {
                } else {

                    if (!usernameforadmin.getText().toString().isEmpty()) {

                        Query query123 = FirebaseFirestore.getInstance().collection("AcMast").whereArrayContains("OrderDates", datepicker.getText().toString().trim()).whereEqualTo("userid",usercode[0] );

                        FirestoreRecyclerOptions<OrdersModel> options123 = new FirestoreRecyclerOptions.Builder<OrdersModel>().setQuery(query123, OrdersModel.class).build();

                        adapter123 = new OrdersAdapter(options123, AllAdminActivity.this, datepicker.getText().toString().trim());
                        adapter123.notifyDataSetChanged();
                        //  entriesrv.setHasFixedSize(true);
                        rv.setAdapter(adapter123);
                        // adapter2.startListening();
                        adapter123.startListening();


                    } else {

                        Log.i("hellllllo", "hello");
                        Log.i("hellllllo", datepicker.getText().toString().trim());
                        Query query12 = FirebaseFirestore.getInstance().collection("AcMast").whereArrayContains("OrderDates", datepicker.getText().toString().trim());

                        FirestoreRecyclerOptions<OrdersModel> options12 = new FirestoreRecyclerOptions.Builder<OrdersModel>().setQuery(query12, OrdersModel.class).build();

                        adapter12 = new OrdersAdapter(options12, AllAdminActivity.this, datepicker.getText().toString().trim());
                        adapter12.notifyDataSetChanged();
                        //  entriesrv.setHasFixedSize(true);
                        rv.setAdapter(adapter12);
                        // adapter2.startListening();
                        adapter12.startListening();
                    }
                }

            }
        });


        setUpToolbar();
        navigationView = (NavigationView) findViewById(R.id.navigation_menu);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.nav_home:
                        break;

                    case R.id.nav_drawer1:
                        gotosummaryacty();
                        break;
                    case R.id.nav_drawer2:
                        gotoItemMaster();
                        break;
                    case R.id.nav_drawer3:
                        gotoUserMaster();
                        break;

                }
                return false;
            }

        });
    }

    private void gotoUserMaster() {
        Intent o = new Intent(AllAdminActivity.this, AddUser.class);
        startActivity(o);
        overridePendingTransition(0, 0);
        o.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
    }

    private void gotoItemMaster() {
        Intent o = new Intent(AllAdminActivity.this, ItemMaster.class);
        startActivity(o);
        overridePendingTransition(0, 0);
        o.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
    }

    private void gotosummaryacty() {
        Intent o = new Intent(AllAdminActivity.this, SummaryActivity.class);
        startActivity(o);
        overridePendingTransition(0, 0);
        o.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

    }

    private void updateLabel(Calendar myCalendar) {

        String myFormat = "dd-MM-yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        datepicker.setText(sdf.format(myCalendar.getTime()));


    }

    public void setUpToolbar() {
        drawerLayout = findViewById(R.id.drawerLayout);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        //  actionBarDrawerToggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.black));
        actionBarDrawerToggle.syncState();

    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
        //  adapter12.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
        //    adapter12.stopListening();
    }

    boolean doubleBackToExitPressedOnce = false;
    @Override
    public void onBackPressed() {

        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Press again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);

    }
}
