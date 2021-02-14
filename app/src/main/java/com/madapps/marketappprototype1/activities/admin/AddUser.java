package com.madapps.marketappprototype1.activities.admin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.madapps.marketappprototype1.R;
import com.madapps.marketappprototype1.activities.ItemMaster;
import com.madapps.marketappprototype1.activities.admin.summary.SummaryActivity;

import java.util.Arrays;
import java.util.HashMap;

public class AddUser extends AppCompatActivity {
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView;

    EditText name, user;
    Button adduserbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

        name = findViewById(R.id.name);
        user = findViewById(R.id.user);
        adduserbtn = findViewById(R.id.adduserbtn);

        final String[] entryid = {""};
        FirebaseFirestore.getInstance().collection("EntryMaster").document("AcMast").addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot snapshot,
                                @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Log.w("ADDUser", "Listen failed.", e);
                    return;
                }

                if (snapshot != null && snapshot.exists()) {
                    Log.d("ADDUser", "Current data: " + snapshot.getData());

                    entryid[0] = snapshot.getString("userid");
                } else {
                    Log.d("ADDUser", "Current data: null");
                }
            }
        });


        adduserbtn.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {


                if (entryid[0].isEmpty()) {
                    Toast.makeText(AddUser.this, "Wait", Toast.LENGTH_SHORT).show();
                } else if (name.getText().toString().isEmpty()) {
                    Toast.makeText(AddUser.this, "Enter Name", Toast.LENGTH_SHORT).show();
                } else if (user.getText().toString().isEmpty()) {
                    Toast.makeText(AddUser.this, "Enter Password", Toast.LENGTH_SHORT).show();
                } else {

                    adduserbtn.setEnabled(false);
                    final HashMap<String, Object> map = new HashMap<>();
                    map.put("userid", "u" + entryid[0]);
                    map.put("username", name.getText().toString().trim());
                    map.put("user", user.getText().toString().trim());
                    map.put("OrderDates", Arrays.asList(""));

//


                    FirebaseFirestore.getInstance().collection("AcMast").document("u" + entryid[0]).set(map)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    final HashMap<String, Object> map1 = new HashMap<>();
                                    map1.put("userid", String.valueOf(Integer.parseInt(entryid[0]) + 1));

                                    FirebaseFirestore.getInstance().collection("EntryMaster").document("AcMast").set(map1).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            adduserbtn.setEnabled(true);
                                            name.setText("");
                                            user.setText("");

                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {

                                            adduserbtn.setEnabled(true);
                                            name.setText("");
                                            user.setText("");
                                        }
                                    });
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {

                                @Override
                                public void onFailure(@NonNull Exception e) {

                                    adduserbtn.setEnabled(true);
                                    name.setText("");
                                    user.setText("");
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
                        gotoItemMaster();
                        break;
                    case R.id.nav_drawer3:

                        break;

                }
                return false;
            }

        });
    }
    private void gotosummary() {
        Intent o = new Intent(AddUser.this, SummaryActivity.class);
        startActivity(o);
        overridePendingTransition(0, 0);
        o.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

    }

    private void gotoItemMaster() {
        Intent o = new Intent(AddUser.this, ItemMaster.class);
        startActivity(o);
        overridePendingTransition(0, 0);
        o.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

    }

    private void gotoalladminacty() {
        Intent o = new Intent(AddUser.this, AllAdminActivity.class);
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

        Intent o = new Intent(AddUser.this, AllAdminActivity.class);
        startActivity(o);
        overridePendingTransition(0, 0);
        o.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

    }
}
