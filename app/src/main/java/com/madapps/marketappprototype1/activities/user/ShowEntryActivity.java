package com.madapps.marketappprototype1.activities.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.madapps.marketappprototype1.R;
import com.madapps.marketappprototype1.activities.admin.ordertoday.ItemsAdapter;
import com.madapps.marketappprototype1.activities.admin.ordertoday.ItemsModel;
import com.madapps.marketappprototype1.onetimelogin.Shared;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ShowEntryActivity extends AppCompatActivity {

    TextView datepicker;
    Button load;
    RecyclerView entriesrv;
    ItemsAdapter adapter2;
    RecyclerView.LayoutManager manager1;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.user_menus,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.item1:
             //   Toast.makeText(this, "Item1 Selected", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.item2:
              //  Toast.makeText(this, "Item2 Selected", Toast.LENGTH_SHORT).show();
                gotonewActivity();
                return true;
            case R.id.item3:
              //  Toast.makeText(this, "Item3 Selected", Toast.LENGTH_SHORT).show();
              //  showentryactivity();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void gotonewActivity() {

        Intent o = new Intent(ShowEntryActivity.this, AllUserActivity.class);
        startActivity(o);
        overridePendingTransition(0, 0);
        o.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_entry_type2);

        datepicker = findViewById(R.id.datepicker);
        load = findViewById(R.id.loadbtn);
        entriesrv = findViewById(R.id.entriesrv);

        datepicker.setText(new SimpleDateFormat("dd-MM-yyyy").format(new Date()));
        final Calendar myCalendar = Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel(myCalendar);
            }

        };
        datepicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new DatePickerDialog(ShowEntryActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();


            }
        });

        final Shared sss = new Shared(ShowEntryActivity.this);

        manager1 = new LinearLayoutManager(this);
        entriesrv.setLayoutManager(manager1);

        load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (datepicker.getText().toString().isEmpty()) {
                } else {

                    manager1 = new LinearLayoutManager(ShowEntryActivity.this);
                    entriesrv.setLayoutManager(manager1);
                    Log.i("rvherer ", "helllo1");
                    Query query = FirebaseFirestore.getInstance().collection("Chalan")
                            .whereEqualTo("EDate", datepicker.getText().toString().trim())
                            .whereEqualTo("userid", "/AcMast/" + sss.userid());
                    //.orderBy("srno");

                    FirestoreRecyclerOptions<ItemsModel> options1 = new FirestoreRecyclerOptions.Builder<ItemsModel>().setQuery(query, ItemsModel.class).build();

                    adapter2 = new ItemsAdapter(options1);
                    adapter2.notifyDataSetChanged();
                    //  entriesrv.setHasFixedSize(true);
                    entriesrv.setAdapter(adapter2);
                    adapter2.startListening();
                }


            }
        });


    }

    private void updateLabel(Calendar myCalendar) {

        String myFormat = "dd-MM-yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        datepicker.setText(sdf.format(myCalendar.getTime()));

    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter2.stopListening();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent o = new Intent(ShowEntryActivity.this, AllUserActivity.class);
        startActivity(o);
        overridePendingTransition(0, 0);
        o.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
    }
}
