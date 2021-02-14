package com.madapps.marketappprototype1.onetimelogin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.madapps.marketappprototype1.R;
import com.madapps.marketappprototype1.activities.AdminActivity;
import com.madapps.marketappprototype1.activities.UserActivity;
import com.madapps.marketappprototype1.activities.admin.AllAdminActivity;
import com.madapps.marketappprototype1.activities.user.AllUserActivity;

public class LoginActivity extends AppCompatActivity {

    RadioButton admin, user;
    Button login;
    EditText pd;

    // Query query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        admin = findViewById(R.id.admin);
        user = findViewById(R.id.user);
        login = findViewById(R.id.login);
        pd = findViewById(R.id.pd);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (pd.getText().toString().isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Enter Password", Toast.LENGTH_SHORT).show();
                } else {

                    if (admin.isChecked()) {

                        FirebaseFirestore.getInstance().collection("AcMast")
                                .whereEqualTo("admin", pd.getText().toString())
                                .get()
                                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                    @Override
                                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                                        if (queryDocumentSnapshots.isEmpty()) {
                                            Toast.makeText(LoginActivity.this, "Wrong Password", Toast.LENGTH_SHORT).show();
                                            //    Snackbar.make(mainContactsLayout, "No matches found", Snackbar.LENGTH_SHORT).show();
                                        } else {
                                            String username = "";
                                            String userid = "";
                                            for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                                                // Snackbar.make(mainContactsLayout, documentSnapshot.getId(), Snackbar.LENGTH_SHORT).show();
                                                username = document.getString("username");
                                                userid = document.getString("userid");


                                            }

                                            startActivity(new Intent(LoginActivity.this, AllAdminActivity.class));
                                            Shared sh = new Shared(getApplicationContext());
                                            sh.second(pd.getText().toString(), username, userid);
                                        }

                                    }
                                });

                    }


                    if (user.isChecked()) {

                        FirebaseFirestore.getInstance().collection("AcMast")
                                .whereEqualTo("user", pd.getText().toString())
                                .get()
                                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                    @Override
                                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                                        if (queryDocumentSnapshots.isEmpty()) {
                                            Toast.makeText(LoginActivity.this, "Wrong Password", Toast.LENGTH_SHORT).show();
                                            //    Snackbar.make(mainContactsLayout, "No matches found", Snackbar.LENGTH_SHORT).show();
                                        } else {
                                            String username = "";
                                            String userid = "";
                                            for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                                                // Snackbar.make(mainContactsLayout, documentSnapshot.getId(), Snackbar.LENGTH_SHORT).show();
                                                username = document.getString("username");
                                                userid = document.getString("userid");


                                            }

                                            startActivity(new Intent(LoginActivity.this, AllUserActivity.class));
                                            Shared sh = new Shared(getApplicationContext());
                                            sh.second(pd.getText().toString(), username, userid);
                                        }

                                    }

                                }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                            }
                        });


//                        FirebaseFirestore.getInstance().collection("AcMast")
//                                .whereEqualTo("user", pd.getText().toString())
//                                .get()
//                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                                    @Override
//                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                                        if (task.isSuccessful()) {
//                                            String username = "";
//                                            String userid = "";
//                                            for (QueryDocumentSnapshot document : task.getResult()) {
//                                                Log.d("LoginActivity", document.getId() + " => " + document.getData());
//                                                username = document.getString("username");
//                                                userid = document.getString("userid");
//
//                                            }
//                                            startActivity(new Intent(LoginActivity.this, UserActivity.class));
//                                            Shared sh = new Shared(getApplicationContext());
//                                            sh.second(pd.getText().toString(), username, userid);
//
//                                        } else {
//                                            Toast.makeText(LoginActivity.this, "Wrong Password", Toast.LENGTH_SHORT).show();
//                                            Log.d("LoginActivity", "Error getting documents: ", task.getException());
//                                        }
//
//
//                                    }
//                                });

                    }


                }


            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent o = new Intent(LoginActivity.this, LoginActivity.class);
        startActivity(o);
        overridePendingTransition(0, 0);
        o.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
    }
}
