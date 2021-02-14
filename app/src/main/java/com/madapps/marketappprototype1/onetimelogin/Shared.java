package com.madapps.marketappprototype1.onetimelogin;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.madapps.marketappprototype1.activities.AdminActivity;
import com.madapps.marketappprototype1.activities.UserActivity;
import com.madapps.marketappprototype1.activities.admin.AllAdminActivity;
import com.madapps.marketappprototype1.activities.user.AllUserActivity;

import java.util.ArrayList;

public class Shared {
    Context context;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    public Shared(Context context){
        this.context = context;
        sharedPreferences = context.getSharedPreferences("systemfiles", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

    }

    public void second(String keyoflock,String typeadminuser,String userid){

        editor.putString("userid",userid);
        editor.putString("type",typeadminuser);
        editor.putString("lock",keyoflock);
        editor.commit();
    }

    public void first(ArrayList<String> pdadminlist, ArrayList<String> pduserlist){

        if (!pdadminlist.contains(ss()) && !pduserlist.contains(ss())){

            Log.i("pdlistttt sp ",ss());
            Intent i = new Intent(context,LoginActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            context.startActivity(i);


        } else {

            if (pdadminlist.contains(ss())){
                Intent i = new Intent(context, AllAdminActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                context.startActivity(i);
            } else if (pduserlist.contains(ss())){
                Intent i = new Intent(context, AllUserActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                context.startActivity(i);
            } else{
                Toast.makeText(context, "User Not Registered", Toast.LENGTH_SHORT).show();
            }


        }
    }
    public String ss() {
        return sharedPreferences.getString("lock", "returnednull");
    }
    public String typeadminuser() {
        return sharedPreferences.getString("type", "returnednull");
    }
    public String userid() {
        return sharedPreferences.getString("userid", "returnednull");
    }
}
