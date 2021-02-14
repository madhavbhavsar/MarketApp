package com.madapps.marketappprototype1.activities.admin.ordertoday;



import android.content.Context;
import android.util.Log;
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

public class OrdersAdapter extends FirestoreRecyclerAdapter<OrdersModel, OrdersAdapter.OrdersViewHolder> {

    Context context;
    String dateh;

    public OrdersAdapter(@NonNull FirestoreRecyclerOptions<OrdersModel> options, Context cotext, String dateh) {
        super(options);
        this.context = cotext;
        this.dateh = dateh;
    }


    @Override
    protected void onBindViewHolder(@NonNull OrdersViewHolder holder, int position, @NonNull OrdersModel model) {
        holder.username.setText(model.getUsername());




        //Query query = FirebaseFirestore.getInstance().collection("AcMast").document(model.getUserid()).collection(new SimpleDateFormat("dd-MM-yyyy").format(new Date())).orderBy("srno");

        Log.i("hellllllo","hello");Log.i("hellllllo",dateh);
        Query query = FirebaseFirestore.getInstance().collection("Chalan")
                .whereEqualTo("EDate",dateh)
                //.whereEqualTo("userid","/AcMast/"+model.getUserid())
                .whereEqualTo("userid","/AcMast/"+model.getUserid());
                //.orderBy("srno");

        FirestoreRecyclerOptions<ItemsModel> options1 = new FirestoreRecyclerOptions.Builder<ItemsModel>().setQuery(query,ItemsModel.class).build();

        TodaysOrder f = new TodaysOrder();
        f.adapter2 = new ItemsAdapter(options1);
        f.adapter2.startListening();
        f.adapter2.notifyDataSetChanged();

        holder.rv.setAdapter(f.adapter2);

    }

    @NonNull
    @Override
    public OrdersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_layout,parent,false);
        return new OrdersViewHolder(view);
    }

    class OrdersViewHolder extends RecyclerView.ViewHolder{

        TextView username;
        RecyclerView rv;
        RecyclerView.LayoutManager manager;

        public OrdersViewHolder(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.usernamehere);
            rv = itemView.findViewById(R.id.rv);
            manager = new LinearLayoutManager(itemView.getContext(),LinearLayoutManager.VERTICAL,false);
            rv.setLayoutManager(manager);
        }
    }
}