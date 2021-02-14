package com.madapps.marketappprototype1.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.madapps.marketappprototype1.R;
import com.madapps.marketappprototype1.activities.admin.AddUser;
import com.madapps.marketappprototype1.activities.admin.AllAdminActivity;
import com.madapps.marketappprototype1.activities.admin.ItemAdapter;
import com.madapps.marketappprototype1.activities.admin.summary.SummaryActivity;

import java.util.ArrayList;
import java.util.HashMap;

public class ItemMaster extends AppCompatActivity {

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView;


    AutoCompleteTextView itemname;
    RadioButton kgs, nos;
    Button additem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_master);

        kgs = findViewById(R.id.kgs);
        nos = findViewById(R.id.nos);
        itemname = findViewById(R.id.itemnamehere);
        additem = findViewById(R.id.additem);


        final ArrayList<String> itemarrayList = new ArrayList<>();
        final ArrayAdapter[] arrayAdapter = new ArrayAdapter[1];

        FirebaseFirestore.getInstance().collection("ItMast").orderBy("ItCode").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                if (error != null) {
                    Log.w("NewEntry", "Listen failed.", error);
                    return;
                }
                for (QueryDocumentSnapshot doc : value) {

                    if (!itemarrayList.contains(doc.getString("ItName"))) {
                        itemarrayList.add(doc.getString("ItName"));
                    }
                }
                arrayAdapter[0] = new ArrayAdapter(ItemMaster.this, android.R.layout.simple_list_item_1, itemarrayList);
                itemname.setAdapter(arrayAdapter[0]);
                Log.i("New Entry Arraylist1 ", String.valueOf(itemarrayList));

            }
        });
        final String[] entryid = {""};
        FirebaseFirestore.getInstance().collection("EntryMaster").document("ItMast").addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot snapshot,
                                @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Log.w("ItemMaster", "Listen failed.", e);
                    return;
                }

                if (snapshot != null && snapshot.exists()) {
                    Log.d("ItemMaster", "Current data: " + snapshot.getData());

                    entryid[0] = snapshot.getString("entryid");
                } else {
                    Log.d("ItemMaster", "Current data: null");
                }
            }
        });


        additem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (entryid[0].isEmpty()) {
                    Toast.makeText(ItemMaster.this, "Wait", Toast.LENGTH_SHORT).show();
                } else if (itemname.getText().toString().isEmpty()) {
                    Toast.makeText(ItemMaster.this, "Enter Sr. No.", Toast.LENGTH_SHORT).show();
                } else if (itemarrayList.contains(itemname.getText().toString().trim())) {
                    Toast.makeText(ItemMaster.this, "Already in List", Toast.LENGTH_SHORT).show();
                } else {
                    additem.setEnabled(false);
                    final HashMap<String, Object> map = new HashMap<>();

                    map.put("ItCode", String.valueOf(entryid[0]));
                    map.put("ItName", itemname.getText().toString().trim());

                    if (kgs.isChecked()) {
                        map.put("ItType", "k");
                    }
                    if (nos.isChecked()) {
                        map.put("ItType", "n");
                    }

//                    ItMast.child("it"+entryid[0]).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
//                        @Override
//                        public void onComplete(@NonNull Task<Void> task) {
//
//                            final HashMap<String, Object> map1 = new HashMap<>();
//                            map1.put("entryid",String.valueOf(Integer.parseInt(entryid[0])+1));
//                            FirebaseDatabase.getInstance().getReference().child("EntryMaster").child("ItMast").updateChildren(map1).addOnCompleteListener(new OnCompleteListener<Void>() {
//                                @Override
//                                public void onComplete(@NonNull Task<Void> task) {
//                                    additem.setEnabled(true);
//                                    itemname.setText("");
//                                    nos.setChecked(false);
//                                    kgs.setChecked(false);
//                                }
//                            });
//
//                        }
//                    });

                    FirebaseFirestore.getInstance().collection("ItMast").document(entryid[0]).set(map)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    final HashMap<String, Object> map1 = new HashMap<>();
                                    map1.put("entryid",String.valueOf(Integer.parseInt(entryid[0])+1));

                                    FirebaseFirestore.getInstance().collection("EntryMaster").document("ItMast").set(map1).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            additem.setEnabled(true);
                                            itemname.setText("");
                                            nos.setChecked(false);
                                            kgs.setChecked(false);
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            additem.setEnabled(true);
                                            itemname.setText("");
                                           // nos.setChecked(false);
                                           // kgs.setChecked(false);
                                        }
                                    });

                                }
                            }).addOnFailureListener(new OnFailureListener() {

                                 @Override
                                 public void onFailure(@NonNull Exception e) {
                                     additem.setEnabled(true);
                                     itemname.setText("");
                                  //   nos.setChecked(false);
                                   //  kgs.setChecked(false);
                                 }
                             });


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
                        gotoalladminacty();
                        break;

                    case R.id.nav_drawer1:
                        gotosummary();
                        break;
                    case R.id.nav_drawer2:
                        break;
                    case R.id.nav_drawer3:
                        gotouserMaster();
                        break;

                }
                return false;
            }

        });
    }
    private void gotosummary() {
        Intent o = new Intent(ItemMaster.this, SummaryActivity.class);
        startActivity(o);
        overridePendingTransition(0, 0);
        o.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

    }

    private void gotouserMaster() {
        Intent o = new Intent(ItemMaster.this, AddUser.class);
        startActivity(o);
        overridePendingTransition(0, 0);
        o.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

    }

    private void gotoalladminacty() {
        Intent o = new Intent(ItemMaster.this, AllAdminActivity.class);
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
    public void onBackPressed() {
        super.onBackPressed();

        Intent o = new Intent(ItemMaster.this, AllAdminActivity.class);
        startActivity(o);
        overridePendingTransition(0, 0);
        o.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

    }
}
