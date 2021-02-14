package com.madapps.marketappprototype1.activities.admin.summary;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.madapps.marketappprototype1.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SumAdapter1 extends FirestoreRecyclerAdapter<SumModel1, SumAdapter1.SumViewHolder1> {

    public SumAdapter1(@NonNull FirestoreRecyclerOptions<SumModel1> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull final SumViewHolder1 holder, int position, @NonNull final SumModel1 model) {
        holder.itemname.setText(model.getItName());
       // holder.itemtype.setText(model.getItType());

        FirebaseFirestore.getInstance().collection("Chalan").whereEqualTo("ItCode","/ItMast/"+model.getItCode()).whereEqualTo("EDate",new SimpleDateFormat("dd-MM-yyyy").format(new Date())).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException or) {

                if (or!= null) {
                    Log.w("Sumadapter1", "Listen failed.", or);
                    return;
                }

                int add = 0;
                for (QueryDocumentSnapshot doc : value) {
                    if (doc.get("quantity") != null) {
                       // cities.add(doc.getString("name"));
                        add = add + Integer.parseInt(doc.getString("quantity"));
                    }
                    holder.rvv.setText(String.valueOf(add)+" "+model.getItType());
                }
            }
        });



    }

    @NonNull
    @Override
    public SumViewHolder1 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sum_layout_1,parent,false);
        return new SumViewHolder1(view);
    }

    class SumViewHolder1 extends RecyclerView.ViewHolder{

        TextView itemname,itemtype;
        TextView rvv;
        public SumViewHolder1(@NonNull View itemView) {
            super(itemView);
            itemname = itemView.findViewById(R.id.itemnamehere);
           // itemtype = itemView.findViewById(R.id.ittypehere);
            rvv = itemView.findViewById(R.id.sumrv2);



        }
    }
}
