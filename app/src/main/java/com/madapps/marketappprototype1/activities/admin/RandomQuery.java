package com.madapps.marketappprototype1.activities.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.madapps.marketappprototype1.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class RandomQuery extends AppCompatActivity {

    TextView tq ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random_query);

        tq = findViewById(R.id.textViewqq);

        FirebaseFirestore.getInstance().collection("AcMast").whereArrayContains("OrderDates", new SimpleDateFormat("dd-MM-yyyy").format(new Date())).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Log.d("RandomQuerytag", document.getId() + " => " + document.getData());
                    }
                } else {
                    Log.d("RandomQuerytag", "Error getting documents: ", task.getException());
                }


            }
        });



        FirebaseFirestore.getInstance().collection("Chalan")
                .whereEqualTo("EDate",new SimpleDateFormat("dd-MM-yyyy").format(new Date()))
                //.whereEqualTo("userid","/AcMast/"+model.getUserid())
                .whereEqualTo("userid","/AcMast/u4")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Log.d("RandomQuerytag2", document.getId() + " => " + document.getData());
                    }
                } else {
                    Log.d("RandomQuerytag2", "Error getting documents: ", task.getException());
                }


            }
        });


    }
}
