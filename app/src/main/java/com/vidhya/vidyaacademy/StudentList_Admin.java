package com.vidhya.vidyaacademy;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class StudentList_Admin extends Activity  {
/*
    ArrayList<Object> itemlist = new ArrayList<>();
*/


    ArrayList<Admin_Stud_CardDetails> arrayList;
    FirebaseDatabase database = FirebaseDatabase.getInstance();

    SharedPreferences sharedPreferences;

    DatabaseReference databaseReference;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recy_admin_cardstud);
        Intent i = getIntent();
        final String UID = i.getStringExtra("student_list");




        sharedPreferences = getApplicationContext().getSharedPreferences("MyShared", Context.MODE_PRIVATE);
        String userid = sharedPreferences.getString("userid", "");

        arrayList = new ArrayList<>();
        databaseReference = FirebaseDatabase.getInstance().getReference("users/admin/" + userid + "/" + UID);

        Log.e("UID",UID);


        final RecyclerView recyclerforstudcard = (RecyclerView) findViewById(R.id.recyclerforstudcard);

        recyclerforstudcard.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        recyclerforstudcard.setLayoutManager(layoutManager);


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    Log.e("classlist", dataSnapshot1.getKey());


                    Log.e("studentlist", dataSnapshot1.toString());
                    /*SID=dataSnapshot1.getKey();
                    Log.e("SID",SID);*/
                    Admin_Stud_CardDetails adminStudCardDetails = new Admin_Stud_CardDetails(dataSnapshot1.child("name").getValue().toString(), dataSnapshot1.child("address").getValue().toString(), dataSnapshot1.child("parent_name").getValue().toString(), dataSnapshot1.child("image").getValue().toString(),dataSnapshot1.getKey().toString());
                    arrayList.add(adminStudCardDetails);

                }
                RecyclerViewForStudentInfo_admin playAdapter = new RecyclerViewForStudentInfo_admin(StudentList_Admin.this, arrayList,UID);
                recyclerforstudcard.setAdapter(playAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "there is some error found", Toast.LENGTH_SHORT).show();

            }
        });

    }


}
