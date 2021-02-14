package com.madapps.marketappprototype1.activities.admin.ordertoday;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.madapps.marketappprototype1.R;
import com.madapps.marketappprototype1.activities.user.ShowEntryActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class TodaysOrder extends AppCompatActivity {

    TextView datepicker;
    Button load;
    RecyclerView rv;
    OrdersAdapter adapter,adapter12;
    ItemsAdapter adapter2;
    RecyclerView.LayoutManager manager1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todays_order);

        datepicker = findViewById(R.id.datepicker);
        load = findViewById(R.id.loadbtn);
        rv = findViewById(R.id.todayrv);
        manager1 = new LinearLayoutManager(this);
        rv.setLayoutManager(manager1);


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

                new DatePickerDialog(TodaysOrder.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();


            }
        });




        //Query query = FirebaseFirestore.getInstance().collection("Chalan").whereEqualTo("EDate", new SimpleDateFormat("dd-MM-yyyy").format(new Date()));

        Query query = FirebaseFirestore.getInstance().collection("AcMast").whereArrayContains("OrderDates", new SimpleDateFormat("dd-MM-yyyy").format(new Date()));

        FirestoreRecyclerOptions<OrdersModel> options = new FirestoreRecyclerOptions.Builder<OrdersModel>().setQuery(query,OrdersModel.class).build();

        adapter = new OrdersAdapter(options,this,new SimpleDateFormat("dd-MM-yyyy").format(new Date()));


        adapter.notifyDataSetChanged();
        //rv.setHasFixedSize(true);
        rv.setAdapter(adapter);


        load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (datepicker.getText().toString().isEmpty()) {
                } else {

                    Log.i("hellllllo","hello");Log.i("hellllllo",datepicker.getText().toString().trim());
                    Query query12 = FirebaseFirestore.getInstance().collection("AcMast").whereArrayContains("OrderDates",datepicker.getText().toString().trim());

                    FirestoreRecyclerOptions<OrdersModel> options12 = new FirestoreRecyclerOptions.Builder<OrdersModel>().setQuery(query12,OrdersModel.class).build();

                    adapter12 = new OrdersAdapter(options12,TodaysOrder.this,datepicker.getText().toString().trim());
                    adapter12.notifyDataSetChanged();
                    //  entriesrv.setHasFixedSize(true);
                    rv.setAdapter(adapter12);
                   // adapter2.startListening();
                    adapter12.startListening();
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
        adapter.startListening();
      //  adapter12.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    //    adapter12.stopListening();
    }
}
