package com.madapps.marketappprototype1.activities.admin;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.madapps.marketappprototype1.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class OrderAdapter extends FirestoreRecyclerAdapter<OrderModel, OrderAdapter.OrderViewHolder> {

    Context context;
    public OrderAdapter(@NonNull FirestoreRecyclerOptions<OrderModel> options, Context cotext) {
        super(options);
        this.context = cotext;
    }

    @Override
    protected void onBindViewHolder(@NonNull OrderViewHolder holder, int position, @NonNull OrderModel model) {
        holder.username.setText(model.getUsername());




        Query query = FirebaseFirestore.getInstance().collection("AcMast").document(model.getUserid()).collection(new SimpleDateFormat("dd-MM-yyyy").format(new Date())).orderBy("srno");

        FirestoreRecyclerOptions<ItemModel> options1 = new FirestoreRecyclerOptions.Builder<ItemModel>().setQuery(query,ItemModel.class).build();

        TodayOrders f = new TodayOrders();
        f.adapter2 = new ItemAdapter(options1);
        f.adapter2.startListening();
        f.adapter2.notifyDataSetChanged();

        holder.rv.setAdapter(f.adapter2);

    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_layout,parent,false);
        return new OrderViewHolder(view);
    }

    class OrderViewHolder extends RecyclerView.ViewHolder{

        TextView username;
        RecyclerView rv;
        RecyclerView.LayoutManager manager;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.usernamehere);
            rv = itemView.findViewById(R.id.rv);
            manager = new LinearLayoutManager(itemView.getContext(),LinearLayoutManager.VERTICAL,false);
            rv.setLayoutManager(manager);
        }
    }
}
