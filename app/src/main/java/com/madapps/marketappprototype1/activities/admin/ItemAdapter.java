package com.madapps.marketappprototype1.activities.admin;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.madapps.marketappprototype1.R;

public class ItemAdapter extends FirestoreRecyclerAdapter<ItemModel, ItemAdapter.ItemViewHolder> {


    public ItemAdapter(@NonNull FirestoreRecyclerOptions<ItemModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ItemViewHolder holder, int position, @NonNull ItemModel model) {
        holder.srno.setText(model.getSrno());
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout,parent,false);
        return new ItemViewHolder(view);

    }

    class ItemViewHolder extends RecyclerView.ViewHolder{

        TextView srno;
        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            srno = itemView.findViewById(R.id.srnohere);
        }
    }
}