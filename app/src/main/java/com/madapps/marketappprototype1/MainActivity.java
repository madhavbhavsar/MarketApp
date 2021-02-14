package com.madapps.marketappprototype1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.madapps.marketappprototype1.onetimelogin.Shared;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    String TAG = "MainActivity";
    ArrayList<String> pdlist, pdadminlist, pduserlist;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ProgressBar pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        pb = findViewById(R.id.progressBarma);
        pdlist = new ArrayList<String>();
        pdadminlist = new ArrayList<String>();
        pduserlist = new ArrayList<String>();


        pb.setVisibility(View.VISIBLE);
        db.collection("AcMast").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Log.d(TAG, document.getId() + " => " + document.getData());
                        pduserlist.add(document.getString("user"));
                        pdadminlist.add(document.getString("admin"));
                    }

                    pb.setVisibility(View.GONE);
                    Log.i("arraylisthere ", String.valueOf(pdadminlist));
                    Shared sh = new Shared(getApplicationContext());
                    sh.first(pdadminlist, pduserlist);
                    Log.i("arraylisthere ", String.valueOf(pduserlist));


                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }
            }
        });

    }
}
