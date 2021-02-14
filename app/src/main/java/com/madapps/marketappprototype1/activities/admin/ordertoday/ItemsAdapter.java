package com.madapps.marketappprototype1.activities.admin.ordertoday;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.madapps.marketappprototype1.R;

public class ItemsAdapter extends FirestoreRecyclerAdapter<ItemsModel, ItemsAdapter.ItemsViewHolder> {


    public ItemsAdapter(@NonNull FirestoreRecyclerOptions<ItemsModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull final ItemsViewHolder holder, int position, @NonNull final ItemsModel model) {
        holder.srno.setText(String.valueOf((Long) model.getSrno()));


        FirebaseFirestore.getInstance().document(model.getItCode()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d("TodayEntryAdapter", "DocumentSnapshot data: " + document.getData());
                        holder.itname.setText(document.getString("ItName"));
                        holder.quanty.setText(model.getQuantity() +" "+ document.getString("ItType"));

                    } else {
                        Log.d("TodayEntryAdapter", "No such document");
                    }
                } else {
                    Log.d("TodayEntryAdapter", "get failed with ", task.getException());
                }

            }
        });





    }

    @NonNull
    @Override
    public ItemsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout,parent,false);
        return new ItemsViewHolder(view);

    }

    class ItemsViewHolder extends RecyclerView.ViewHolder{

        TextView srno, itname, quanty;

        public ItemsViewHolder(@NonNull View itemView) {
            super(itemView);
            srno = itemView.findViewById(R.id.srnohere);
            itname = itemView.findViewById(R.id.itemcodehere);
            quanty = itemView.findViewById(R.id.quantityhere);
        }
    }
}