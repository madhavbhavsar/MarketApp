package com.madapps.marketappprototype1.activities.admin.summary;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.madapps.marketappprototype1.R;
import com.madapps.marketappprototype1.activities.ItemMaster;
import com.madapps.marketappprototype1.activities.admin.AddUser;
import com.madapps.marketappprototype1.activities.admin.AllAdminActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class SummaryActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView;

    TextView datepicker;
    Button load;

    RecyclerView rv1;
    SumAdapter1 adap,adapter12;
    RecyclerView.LayoutManager manage1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        datepicker = findViewById(R.id.datepicker);
        load = findViewById(R.id.loadbtn);
        rv1 = findViewById(R.id.sumrv1);
        manage1 = new LinearLayoutManager(this);
        rv1.setLayoutManager(manage1);

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

                new DatePickerDialog(SummaryActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();


            }
        });




        Query query = FirebaseFirestore.getInstance().collection("ItMast");

        FirestoreRecyclerOptions<SumModel1> options = new FirestoreRecyclerOptions.Builder<SumModel1>().setQuery(query,SumModel1.class).build();

        adap = new SumAdapter1(options);


        adap.notifyDataSetChanged();
        //rv.setHasFixedSize(true);
        rv1.setAdapter(adap);



        load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (datepicker.getText().toString().isEmpty()) {
                } else {

//                        Query query12 = FirebaseFirestore.getInstance().collection("AcMast").whereArrayContains("OrderDates", datepicker.getText().toString().trim());
//
//                        FirestoreRecyclerOptions<OrdersModel> options12 = new FirestoreRecyclerOptions.Builder<OrdersModel>().setQuery(query12, OrdersModel.class).build();
//
//                        adapter12 = new OrdersAdapter(options12, AllAdminActivity.this, datepicker.getText().toString().trim());
//                        adapter12.notifyDataSetChanged();
//                        //  entriesrv.setHasFixedSize(true);
//                        rv.setAdapter(adapter12);
//                        // adapter2.startListening();
//                        adapter12.startListening();

                }

            }
        });




















        //FirebaseFirestore.getInstance().collection("Chalan").whereEqualTo()







//        FirebaseFirestore.getInstance().collection("ItMast").addSnapshotListener(new EventListener<QuerySnapshot>() {
//            @Override
//            public void onEvent(@Nullable QuerySnapshot value, @Nullable final FirebaseFirestoreException e) {
//                if (e != null) {
//                    Log.w("SummaryActivity", "Listen failed.", e);
//                    return;
//                }
//                    //List<String> heer = new ArrayList<>();
//                    final HashMap<String, String> hashmap = new HashMap<String, String>();
//
//                    String ss1 = "";
//                //final String[] quantityhere = {""};
//                    for (final QueryDocumentSnapshot doc : value) {
//
//                        ss1 = ss1 + doc.getId();
//
//                        FirebaseFirestore.getInstance().collection("ItMast").document(doc.getId()).collection(new SimpleDateFormat("dd-MM-yyyy").format(new Date()))
//
//                                .addSnapshotListener(new EventListener<QuerySnapshot>() {
//                            @Override
//                            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
//
//                                if (e != null) {
//                                    Log.w("SummaryActivity", "Listen failed.", e);
//                                    return;
//                                }
//
//                                final String[] quantityhere = {""};
//
//                                final int[] totalq = {0};
//
//
//                                ArrayList<DocumentReference> arraydb = new ArrayList<>();
//                                for (QueryDocumentSnapshot docky : value) {
//
//                                    DocumentReference df = (DocumentReference) docky.get("quantity");
//
//                                    if (arraydb.contains(df)){}
//                                    else{
//                                        arraydb.add(df);
//                                        df.addSnapshotListener(new EventListener<DocumentSnapshot>() {
//                                            @Override
//                                            public void onEvent(@Nullable DocumentSnapshot snapshot122, @Nullable FirebaseFirestoreException error) {
//
//                                                if (snapshot122 != null && snapshot122.exists()) {
//                                                    Log.d("Snapshot here", "Current data: " + snapshot122.getData());
//                                                    quantityhere[0] = snapshot122.getString("quantity");
//
//                                                    totalq[0] = totalq[0] + Integer.parseInt(snapshot122.getString("quantity"));
//
//
//                                                    Log.d("Snapshot here", "Current data: " + quantityhere[0] + totalq[0]);
//                                                    Log.d("Snapshot here", "mad" + totalq[0]);
//                                                } else {
//                                                    Log.d("Snapshot here", "Current data: null");
//                                                }
//
//                                                tvv.setText("mad" + totalq[0]);
//                                            }
//                                        });
//
//                                    }
//
//
//
//
//                                }
//
//
//
//                            }
//                        });
//
//                    }
//                    tvv.setText(ss1);
//
//
//
//
//            }
//        });




        setUpToolbar();
        navigationView = (NavigationView) findViewById(R.id.navigation_menu);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.nav_home:
                        gotoalladminacty();
                        break;

                    case R.id.nav_drawer1:
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

    private void updateLabel(Calendar myCalendar) {
        String myFormat = "dd-MM-yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        datepicker.setText(sdf.format(myCalendar.getTime()));
    }

    private void gotoUserMaster() {
        Intent o = new Intent(SummaryActivity.this, AddUser.class);
        startActivity(o);
        overridePendingTransition(0, 0);
        o.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

    }

    private void gotoItemMaster() {
        Intent o = new Intent(SummaryActivity.this, ItemMaster.class);
        startActivity(o);
        overridePendingTransition(0, 0);
        o.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
    }

    private void gotoalladminacty() {
        Intent o = new Intent(SummaryActivity.this, AllAdminActivity.class);
        startActivity(o);
        overridePendingTransition(0, 0);
        o.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
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
        adap.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adap.stopListening();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent o = new Intent(SummaryActivity.this, AllAdminActivity.class);
        startActivity(o);
        overridePendingTransition(0, 0);
        o.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

    }
}
