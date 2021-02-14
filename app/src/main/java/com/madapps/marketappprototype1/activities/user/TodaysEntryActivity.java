package com.madapps.marketappprototype1.activities.user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.SharedMemory;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.madapps.marketappprototype1.R;
import com.madapps.marketappprototype1.activities.admin.ordertoday.ItemsModel;
import com.madapps.marketappprototype1.onetimelogin.Shared;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TodaysEntryActivity extends AppCompatActivity {

    RecyclerView rview;

    RecyclerView.LayoutManager manager1;
    TodaysEntryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todays_entry);

        Shared sss = new Shared(this);
        rview = findViewById(R.id.rview);

        manager1 = new LinearLayoutManager(this);
        rview.setLayoutManager(manager1);



        Query query = FirebaseFirestore.getInstance().collection("Chalan")
                .whereEqualTo("EDate",  new SimpleDateFormat("dd-MM-yyyy").format(new Date()))
                .whereEqualTo("userid", "/AcMast/" + sss.userid());



        FirestoreRecyclerOptions<ItemsModel> options1 = new FirestoreRecyclerOptions.Builder<ItemsModel>().setQuery(query, ItemsModel.class).build();

        adapter = new TodaysEntryAdapter(options1, this);
        adapter.notifyDataSetChanged();
        //  entriesrv.setHasFixedSize(true);
        rview.setAdapter(adapter);


    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}
