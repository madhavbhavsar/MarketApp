package com.madapps.marketappprototype1.activities.user;

import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.madapps.marketappprototype1.R;
import com.madapps.marketappprototype1.activities.admin.ordertoday.ItemsModel;
import com.madapps.marketappprototype1.onetimelogin.Shared;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class TodaysEntryAdapter extends FirestoreRecyclerAdapter<ItemsModel, TodaysEntryAdapter.TodaysEntryViewHolder> {

    Context context;

    public TodaysEntryAdapter(@NonNull FirestoreRecyclerOptions<ItemsModel> options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull final TodaysEntryViewHolder holder, int position, @NonNull final ItemsModel model) {
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

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DialogPlus dialogPlus = DialogPlus.newDialog(context)
                        .setGravity(Gravity.CENTER)
                        .setMargin(50, 0, 50, 0)
                        .setContentHolder(new ViewHolder(R.layout.editbox_todaysentry_type2))
                        .setExpanded(false)
                        .create();

                dialogPlus.show();


                View hView = (LinearLayout) dialogPlus.getHolderView();
                final AutoCompleteTextView itemname = hView.findViewById(R.id.itemname);
                final EditText quantity = hView.findViewById(R.id.quantity);
                final TextView srno = hView.findViewById(R.id.srno);
 //               final TextView date = hView.findViewById(R.id.date);

//                final RadioButton kgs = hView.findViewById(R.id.kgs);
//                final RadioButton nos = hView.findViewById(R.id.nos);

                final TextView kgornos = hView.findViewById(R.id.kgornostv);
                final String date =  new SimpleDateFormat("dd-MM-yyyy").format(new Date());

                final ArrayList<String> itemarrayList = new ArrayList<>();
                final ArrayList<String> itcodeList = new ArrayList<>();
                final ArrayList<String> itemtypearrayList = new ArrayList<>();



                srno.setText(String.valueOf((Long) model.getSrno()));
                itemname.setText(holder.itname.getText());
                quantity.setText(holder.quanty.getText().subSequence(0, holder.quanty.getText().toString().length() - 4));

                if (holder.quanty.getText().toString().contains("k")) {
                    kgornos.setText("Kgs.");
                } else {
                    kgornos.setText("Nos.");
                }
//                SimpleDateFormat simpleDateFormated = new SimpleDateFormat("dd-MM-yyyy");
//                date.setText(simpleDateFormated.format(new Date()));


                final Shared sh = new Shared(context);
                final Button updatebtn = hView.findViewById(R.id.updatebtn);



                FirebaseFirestore.getInstance().collection("ItMast").orderBy("ItCode").addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        Log.i("NewEntry112", "Listen 1");
                        if (error != null) {
                            Log.w("NewEntry", "Listen failed.", error);
                            return;
                        }
                        for (QueryDocumentSnapshot doc : value) {
                            Log.i("NewEntry122", "Listen 2");
                            if (!itemarrayList.contains(doc.getString("ItName"))) {
                                itemarrayList.add(doc.getString("ItName"));
                                itcodeList.add(doc.getString("ItCode"));
                                itemtypearrayList.add(doc.getString("ItType"));
                            }
                        }
                    }
                });

                ArrayAdapter arrayAdapter = new ArrayAdapter(context, android.R.layout.simple_list_item_1, itemarrayList);
                itemname.setAdapter(arrayAdapter);
                final String[] ItCode = {""};
                ItCode[0] = model.getItCode();


                final boolean[] itemselectedfromlist = {false};
                itemname.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Log.i("NewEntry122", "Listen 3");
                        int pos = itemarrayList.indexOf(parent.getItemAtPosition(position).toString());
                        Log.i("New Entry kornpos ", String.valueOf(pos));
                        itemselectedfromlist[0] = true;

                        String korn = itemtypearrayList.get(pos);
                        Log.i("New Entry korn ", korn);
                        //Log.i("New Entry korn ",  itemtypearrayList.get(position));

                        ItCode[0] = "/ItMast/"+itcodeList.get(pos);
                        if (korn.equals("k")) {
                           kgornos.setText("Kgs.");
                        }
                        if (korn.equals("n")) {
                            kgornos.setText("Nos.");
                        }
                    }
                });


                updatebtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (srno.getText().toString().isEmpty()) {
                            Toast.makeText(context, "Enter Sr. No.", Toast.LENGTH_SHORT).show();
                        } else if (itemname.getText().toString().isEmpty()) {
                            Toast.makeText(context, "Enter Item Name", Toast.LENGTH_SHORT).show();
                        } else if (ItCode[0].isEmpty()) {
                            Toast.makeText(context, "Select Item from List", Toast.LENGTH_SHORT).show();
                        }
//                        else if (!itemselectedfromlist[0]) {
//                            Toast.makeText(context, "Select Item from Item List", Toast.LENGTH_SHORT).show();
//                        }
                        else if (quantity.getText().toString().isEmpty()) {
                            Toast.makeText(context, "Enter Quantity", Toast.LENGTH_SHORT).show();
                        }
                        else if (!itemname.getText().toString().trim().equals(holder.itname.getText().toString().trim()) ) {

                            if (!itemselectedfromlist[0]){
                                Toast.makeText(context, "Select Item from Item List ", Toast.LENGTH_SHORT).show();
                            } else {

                                updatebtn.setEnabled(false);
                                final HashMap<String, Object> map = new HashMap<>();


                                map.put("EDate", date);
                                map.put("srno", Integer.parseInt(srno.getText().toString().trim()));
                                map.put("ItCode",ItCode[0].trim());
                                map.put("quantity", quantity.getText().toString().trim());
                                map.put("userid", "/AcMast/" + sh.userid().trim());


                                //chalan here
                                FirebaseFirestore.getInstance().collection("Chalan")
                                        .document(date + "_" + sh.userid().trim() + "_" + srno.getText().toString().trim())
                                        .set(map)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Toast.makeText(context, "Item Updated", Toast.LENGTH_SHORT).show();
                                                updatebtn.setEnabled(true);
                                                itemname.setText("");
                                                quantity.setText("");
                                                kgornos.setText("");
                                                dialogPlus.dismiss();

                                            }
                                        });
                            }
                        }
                         else {

//                             update_func();
//


                            updatebtn.setEnabled(false);
                            final HashMap<String, Object> map = new HashMap<>();


                            map.put("EDate", date);
                            map.put("srno", Integer.parseInt(srno.getText().toString().trim()));
                            map.put("ItCode",ItCode[0].trim());
                            map.put("quantity", quantity.getText().toString().trim());
                            map.put("userid", "/AcMast/" + sh.userid().trim());


                            //chalan here
                            FirebaseFirestore.getInstance().collection("Chalan")
                                    .document(date + "_" + sh.userid().trim() + "_" + srno.getText().toString().trim())
                                    .set(map)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Toast.makeText(context, "Item Updated", Toast.LENGTH_SHORT).show();
                                            updatebtn.setEnabled(true);
                                            itemname.setText("");
                                            quantity.setText("");
                                            kgornos.setText("");
                                            dialogPlus.dismiss();

                                        }
                                    });


                        }
                    }


                });
            }
        });


    }

    @NonNull
    @Override
    public TodaysEntryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout_for_todaysentry, parent, false);

        return new TodaysEntryViewHolder(view);
    }

    public class TodaysEntryViewHolder extends RecyclerView.ViewHolder {

        TextView srno, itname, quanty;
        ImageButton edit;

        public TodaysEntryViewHolder(@NonNull View itemView) {
            super(itemView);
            srno = itemView.findViewById(R.id.srnohere);
            itname = itemView.findViewById(R.id.itemcodehere);
            quanty = itemView.findViewById(R.id.quantityhere);
            edit = itemView.findViewById(R.id.editbtn);
        }
    }

}
