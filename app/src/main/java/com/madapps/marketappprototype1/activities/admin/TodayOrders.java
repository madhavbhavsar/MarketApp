package com.madapps.marketappprototype1.activities.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.madapps.marketappprototype1.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TodayOrders extends AppCompatActivity {

    RecyclerView rv;
    OrderAdapter adapter;
    ItemAdapter adapter2;
    RecyclerView.LayoutManager manager1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_today_orders);
        rv = findViewById(R.id.order_rv);
        manager1 = new LinearLayoutManager(this);
        rv.setLayoutManager(manager1);

        setUP();


    }

    private void setUP() {
        Query query = FirebaseFirestore.getInstance().collection("AcMast").whereEqualTo("refDate", new SimpleDateFormat("dd-MM-yyyy").format(new Date()));

        FirestoreRecyclerOptions<OrderModel> options = new FirestoreRecyclerOptions.Builder<OrderModel>().setQuery(query,OrderModel.class).build();

        adapter = new OrderAdapter(options,this);


        adapter.notifyDataSetChanged();
        //rv.setHasFixedSize(true);
        rv.setAdapter(adapter);


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
