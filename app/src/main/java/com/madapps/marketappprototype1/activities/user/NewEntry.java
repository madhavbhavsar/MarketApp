package com.madapps.marketappprototype1.activities.user;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.madapps.marketappprototype1.R;
import com.madapps.marketappprototype1.activities.AdminActivity;
import com.madapps.marketappprototype1.activities.UserActivity;
import com.madapps.marketappprototype1.onetimelogin.Shared;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;

public class NewEntry extends AppCompatActivity {
    TextView entry, date;
    AutoCompleteTextView itemname;
    EditText quantity;
    TextView srno;
    RadioButton kgs, nos;
    Button add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_entry);


        date = findViewById(R.id.date);
        add = findViewById(R.id.addbtn);
        srno = findViewById(R.id.srno);
        itemname = findViewById(R.id.itemname);
        quantity = findViewById(R.id.quantity);
        kgs = findViewById(R.id.kgs);
        nos = findViewById(R.id.nos);
        final Shared sh = new Shared(this);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        date.setText(simpleDateFormat.format(new Date()));


        final ArrayList<String> itemarrayList = new ArrayList<>();
        final ArrayList<String> itcodeList = new ArrayList<>();
        final ArrayList<String> itemtypearrayList = new ArrayList<>();


        getSrnoautomatic(sh);
     //   getSrnoautomatic2(sh);


        FirebaseFirestore.getInstance().collection("ItMast").orderBy("ItCode").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                if (error != null) {
                    Log.w("NewEntry", "Listen failed.", error);
                    return;
                }
                for (QueryDocumentSnapshot doc : value) {

                    if (!itemarrayList.contains(doc.getString("ItName"))) {
                        itemarrayList.add(doc.getString("ItName"));
                        itcodeList.add(doc.getString("ItCode"));
                        itemtypearrayList.add(doc.getString("ItType"));
                    }
                }
            }
        });

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, itemarrayList);
        itemname.setAdapter(arrayAdapter);
        final String[] ItCode = {""};
        itemname.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                int pos = itemarrayList.indexOf(parent.getItemAtPosition(position).toString());
                Log.i("New Entry kornpos ", String.valueOf(pos));

                String korn = itemtypearrayList.get(pos);
                Log.i("New Entry korn ", korn);
                //Log.i("New Entry korn ",  itemtypearrayList.get(position));

                ItCode[0] = itcodeList.get(pos);
                if (korn.equals("k")) {
                    kgs.setChecked(true);
                }
                if (korn.equals("n")) {
                    nos.setChecked(true);
                }
            }
        });



        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (srno.getText().toString().isEmpty()) {
                    Toast.makeText(NewEntry.this, "Enter Sr. No.", Toast.LENGTH_SHORT).show();
                } else if (itemname.getText().toString().isEmpty()) {
                    Toast.makeText(NewEntry.this, "Enter Item Name", Toast.LENGTH_SHORT).show();
                } else if (quantity.getText().toString().isEmpty()) {
                    Toast.makeText(NewEntry.this, "Enter Quantity", Toast.LENGTH_SHORT).show();
                } else {
                    add.setEnabled(false);
                    final HashMap<String, Object> map = new HashMap<>();


                    map.put("EDate", date.getText().toString().trim());
                    map.put("srno",Integer.parseInt(srno.getText().toString().trim()));
                    map.put("ItCode", "/ItMast/" + ItCode[0]);
                    map.put("quantity", quantity.getText().toString().trim());
                    map.put("userid", "/AcMast/" + sh.userid().trim());


                    //chalan here
                    FirebaseFirestore.getInstance().collection("Chalan")
                            .document(date.getText().toString().trim()+"_"+sh.userid().trim()+"_"+srno.getText().toString().trim())
                            .set(map)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(NewEntry.this, "Item Added", Toast.LENGTH_SHORT).show();
                                    add.setEnabled(true);
                                    itemname.setText("");
                                    quantity.setText("");
                                }
                            });

                    if (srno.getText().toString().trim().equals("1")) {
                      FirebaseFirestore.getInstance().collection("AcMast").document(sh.userid().trim()).update("OrderDates", FieldValue.arrayUnion(date.getText().toString().trim()));
                    }


                    //old entry type here

//                    FirebaseFirestore.getInstance().collection("AcMast").document(sh.userid()).collection(date.getText().toString())
//                            .add(map)
//                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//                                @Override
//                                public void onSuccess(DocumentReference documentReference) {
//
//                                    final HashMap<String, Object> mapq = new HashMap<>();
//                                    mapq.put("quantity",FirebaseFirestore.getInstance().collection("AcMast").document(sh.userid()).collection(date.getText().toString()).document(documentReference.getId()));
//
//
//                                    FirebaseFirestore.getInstance()
//                                            .collection("ItMast")
//                                            .document(ItCode[0])
//                                            .collection(date.getText().toString())
//                                            .document(documentReference.getId())
//                                            .set(mapq);
//
//
//
//                                }
//                            })
//                            .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
//                                @Override
//                                public void onComplete(@NonNull Task<DocumentReference> task) {
//
//                                    add.setEnabled(true);
//                                    srno.setText("");
//                                    itemname.setText("");
//                                    quantity.setText("");
//                                   // nos.setChecked(false);
//                                    //kgs.setChecked(false);
//                                }
//                            })
//                            .addOnFailureListener(new OnFailureListener() {
//                                @Override
//                                public void onFailure(@NonNull Exception e) {
//                                    Log.w("NewEntry", "Error adding document", e);
//                                    add.setEnabled(true);
//                                    srno.setText("");
//                                    itemname.setText("");
//                                    quantity.setText("");
//                                  //  kgs.setChecked(false);
//                                  //  nos.setChecked(false);
//                                }
//                            });
//
//
//                    final HashMap<String, Object> map1 = new HashMap<>();
//                    map1.put("refDate",date.getText().toString());
//                    FirebaseFirestore.getInstance().collection("AcMast").document(sh.userid()).update(map1).addOnSuccessListener(new OnSuccessListener<Void>() {
//                        @Override
//                        public void onSuccess(Void aVoid) {
//                            Log.d("NewEntry", "DocumentSnapshot successfully updated!");
//                        }
//                    }).addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//                            Log.w("NewEntry", "Error updating document", e);
//                        }
//                    });


                }
            }
        });


    }

    private void getSrnoautomatic2(Shared sh) {
        Log.i("tagtagtag", String.valueOf("started"));

        FirebaseFirestore.getInstance().collection("Chalan")
                .whereEqualTo("EDate",date.getText().toString().trim())
                .whereEqualTo("userid","/AcMast/"+sh.userid().trim())
               // .orderBy("srno")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {


                        if (task.isSuccessful()) {
                            QuerySnapshot querySnapshot = task.getResult();


                            if (querySnapshot.isEmpty()) {
                                srno.setText(String.valueOf(1));
                                Log.i("tagtagtag" , "i1");
                            } else {
                                for (QueryDocumentSnapshot document : querySnapshot){
                                    //srnoo = Integer.parseInt(document.get("srno"));

                                    long ss1 = (long) document.get("srno");
                                   // srnoo = Integer.parseInt((String) document.get("srno"))   ;

                                    srno.setText(String.valueOf(ss1+1));
                                    Log.i("tagtagtag" , ss1 +"i2");
                                }
                            }
                        } else {
                           // srno.setText(String.valueOf(srnoo+1));
                            Log.i("tagtagtag" , "i3");
                        }
                    }
                });

    }


    private void getSrnoautomatic(Shared sh) {
        Log.i("imhere", String.valueOf("started"));


        FirebaseFirestore.getInstance().collection("Chalan")
                .whereEqualTo("EDate",date.getText().toString().trim())
                .whereEqualTo("userid","/AcMast/"+ sh.userid().trim())
                //.orderBy("srno")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException e) {

                Log.i("imhere", String.valueOf("started1"));

                if (e != null) {
                    Log.i("imhere", "hhere1 ");
                    return;
                }

                if(value.isEmpty()){
                    Log.i("imhere", "hhere2 ");
                    srno.setText(String.valueOf(1));

                }
                else {
                    Log.i("imhere", "hh3 ");

                    for (QueryDocumentSnapshot doc : value) {
                        if (doc.get("srno") != null) {
                            Log.i("imhere", "hh4 ");
                            long l = (long) doc.get("srno");


                            srno.setText(String.valueOf(l + 1));
                        }
                        Log.i("imhere", "hh5 ");

                    }
                    Log.i("imhere", "hh6 ");
                }

            }
        });



    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent o = new Intent(NewEntry.this, UserActivity.class);
        startActivity(o);
        overridePendingTransition(0, 0);
        o.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);


    }
}
