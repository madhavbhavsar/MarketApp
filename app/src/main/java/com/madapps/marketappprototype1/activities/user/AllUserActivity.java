package com.madapps.marketappprototype1.activities.user;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.madapps.marketappprototype1.MainActivity;
import com.madapps.marketappprototype1.R;
import com.madapps.marketappprototype1.activities.admin.ordertoday.ItemsModel;
import com.madapps.marketappprototype1.onetimelogin.Shared;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class AllUserActivity extends AppCompatActivity {

    private long mLastPress = 0;
    int TOAST_DURATION = 5000;
    Toast onBackPressedToast;

    AutoCompleteTextView itemname;
    EditText quantity;
    TextView srno;
    Button add;
    TextView kgornos;
    String date;
///recycler view here

    RecyclerView rview;
    LinearLayoutManager manager1;
    TodaysEntryAdapter adapter;

    ///
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_user);




        add = findViewById(R.id.addbtn);
        srno = findViewById(R.id.srno);
        itemname = findViewById(R.id.itemname);
        quantity = findViewById(R.id.quantity);
        kgornos = findViewById(R.id.kgornostv);
        final Shared sh = new Shared(this);


        //for exiting app



        ///recycler view here
        rview = findViewById(R.id.rview);

        manager1 = new LinearLayoutManager(this);
        manager1.setReverseLayout(true);
        manager1.setStackFromEnd(true);
        rview.setLayoutManager(manager1);

        Query query = FirebaseFirestore.getInstance().collection("Chalan")
                .whereEqualTo("EDate", new SimpleDateFormat("dd-MM-yyyy").format(new Date()))
                .whereEqualTo("userid", "/AcMast/" + sh.userid());


        FirestoreRecyclerOptions<ItemsModel> options1 = new FirestoreRecyclerOptions.Builder<ItemsModel>().setQuery(query, ItemsModel.class).build();

        adapter = new TodaysEntryAdapter(options1, this);
        adapter.notifyDataSetChanged();


        rview.setAdapter(adapter);
        ///

        date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());

        final ArrayList<String> itemarrayList = new ArrayList<>();
        final ArrayList<String> itcodeList = new ArrayList<>();
        final ArrayList<String> itemtypearrayList = new ArrayList<>();

        getSrnoautomatic(sh);

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
                    kgornos.setText("Kgs.");
                }
                if (korn.equals("n")) {
                    kgornos.setText("Nos.");
                }
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (srno.getText().toString().isEmpty()) {
                    Toast.makeText(AllUserActivity.this, "Enter Sr. No.", Toast.LENGTH_SHORT).show();
                } else if (itemname.getText().toString().isEmpty()) {
                    Toast.makeText(AllUserActivity.this, "Enter Item Name", Toast.LENGTH_SHORT).show();
                } else if (ItCode[0].isEmpty()) {
                    Toast.makeText(AllUserActivity.this, "Select Item from List", Toast.LENGTH_SHORT).show();
                } else if (quantity.getText().toString().isEmpty()) {
                    Toast.makeText(AllUserActivity.this, "Enter Quantity", Toast.LENGTH_SHORT).show();
                } else {
                    add.setEnabled(false);
                    final HashMap<String, Object> map = new HashMap<>();


                    map.put("EDate", date);
                    map.put("srno", Integer.parseInt(srno.getText().toString().trim()));
                    map.put("ItCode", "/ItMast/" + ItCode[0].trim());
                    map.put("quantity", quantity.getText().toString().trim());
                    map.put("userid", "/AcMast/" + sh.userid().trim());


                    //chalan here
                    FirebaseFirestore.getInstance().collection("Chalan")
                            .document(date + "_" + sh.userid().trim() + "_" + srno.getText().toString().trim())
                            .set(map)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(AllUserActivity.this, "Item Added", Toast.LENGTH_SHORT).show();
                                    add.setEnabled(true);
                                    itemname.setText("");
                                    quantity.setText("");
                                    kgornos.setText("");
                                }
                            });

                    if (srno.getText().toString().trim().equals("1")) {
                        FirebaseFirestore.getInstance().collection("AcMast").document(sh.userid().trim()).update("OrderDates", FieldValue.arrayUnion(date));
                    }

                }
            }
        });


    }


    private void getSrnoautomatic(Shared sh) {
        Log.i("imhere", String.valueOf("started"));


        FirebaseFirestore.getInstance().collection("Chalan")
                .whereEqualTo("EDate", date)
                .whereEqualTo("userid", "/AcMast/" + sh.userid().trim())
                //.orderBy("srno")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException e) {

                        Log.i("imhere", String.valueOf("started1"));

                        if (e != null) {
                            Log.i("imhere", "hhere1 ");
                            return;
                        }

                        if (value.isEmpty()) {
                            Log.i("imhere", "hhere2 ");
                            srno.setText(String.valueOf(1));

                        } else {
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
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.user_menus, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.item1:
              //  Toast.makeText(this, "Item1 Selected", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.item2:
              //  Toast.makeText(this, "Item2 Selected", Toast.LENGTH_SHORT).show();
                runthisactivityagain();
                return true;
            case R.id.item3:
             //   Toast.makeText(this, "Item3 Selected", Toast.LENGTH_SHORT).show();
                showentryactivity();
                return true;
            case R.id.item4:
                logoutfn();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void logoutfn() {
        Shared sh = new Shared(AllUserActivity.this);
        sh.second("returnednull","returnednull","returnednull");

        Intent o = new Intent(AllUserActivity.this, MainActivity.class);
        startActivity(o);
        overridePendingTransition(0, 0);
        o.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
    }

    private void showentryactivity() {
        Intent o = new Intent(AllUserActivity.this, ShowEntryActivity.class);
        startActivity(o);
        overridePendingTransition(0, 0);
        o.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
    }

    private void runthisactivityagain() {
//        Intent o = new Intent(AllUserActivity.this, AllUserActivity.class);
//        startActivity(o);
//        overridePendingTransition(0, 0);
//        o.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
    }
    boolean doubleBackToExitPressedOnce = false;
    @Override
    public void onBackPressed() {

        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Press again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);

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
