package com.example.walkinclinicservices;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Bookings extends AppCompatActivity {

    private ListView bookList;
    private Button bookAgBtn, logoutBtn;

    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private DatabaseReference mDatabase;

    public ArrayList<Appointment> timeList;
    public ArrayList<String> ids;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookings);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        timeList = new ArrayList<>();
        ids = new ArrayList<>();

        bookList = (ListView) findViewById(R.id.bookList);
        bookAgBtn = (Button) findViewById(R.id.bookagBtn);
        logoutBtn = (Button) findViewById(R.id.logoutBtn);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String id = intent.getStringExtra("id");


        mDatabase.child("Appointment").child(mUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot appSnapshot: dataSnapshot.getChildren()){
                    Appointment appointment = appSnapshot.getValue(Appointment.class);
                    timeList.add(appointment);
                    ids.add(appSnapshot.getKey());
                }

                AppointmentListAdapter adapter = new AppointmentListAdapter(Bookings.this, R.layout.adapter_view_layout,timeList);
                bookList.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        bookAgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Bookings.this, patientProfile.class));
            }
        });

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                finish();
                startActivity(new Intent(Bookings.this, MainActivity.class));
            }
        });
    }
}
