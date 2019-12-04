package com.example.walkinclinicservices;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class patientProfile2 extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private FirebaseDatabase nDatabase;
    private DatabaseReference mDatabase;

    private Button bookBtn;
    private TextView waitTime;
    private Spinner timeSpinner;
    private CalendarView calendarView;

    public List<String> timeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_profile2);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        //mDatabase = FirebaseDatabase.getInstance().getReference();
        nDatabase = FirebaseDatabase.getInstance();

        bookBtn = (Button) findViewById(R.id.bookBtn);
        waitTime = (TextView) findViewById(R.id.waitTime);
        timeSpinner = (Spinner) findViewById(R.id.timeSpinner);
        calendarView = (CalendarView) findViewById(R.id.calendarView);

        Intent intent = getIntent();

        final String id = intent.getStringExtra("id");
        String from = intent.getStringExtra("From");
        String to = intent.getStringExtra("To");
        final String name = intent.getStringExtra("name");

        //ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,R.array.Services,R.layout.support_simple_spinner_dropdown_item);
        //timeSpinner.setAdapter(adapter2);

        if(mUser == null){
            finish();
            startActivity(new Intent(this, MainActivity.class));
        }else {

            //Set service spinner
            timeList = new ArrayList<String>();
            timeList = makeTimeList(Integer.parseInt(from),Integer.parseInt(to));

            ArrayAdapter<String> timeAdapter = new ArrayAdapter<String>(patientProfile2.this,android.R.layout.simple_spinner_item,timeList);
            timeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            timeSpinner.setAdapter(timeAdapter);


            /*mDatabase.child("Appointment").child(id).addValueEventListener(new ValueEventListener() {
                final List<String> timeList = new ArrayList<String>();
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for(DataSnapshot appSnapshot : dataSnapshot.getChildren()){

                        //String time = serviceSnapshot.child("serviceName").getValue(String.class);
                        //time.add(service);

                    }

                    //ArrayAdapter<String> timeAdapter = new ArrayAdapter<String>(patientProfile2.this,android.R.layout.simple_spinner_item,serviceList);
                    //timeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    //timeSpinner.setAdapter(timeAdapter);

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

             */


        }

        bookBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newint = new Intent(patientProfile2.this,Bookings.class);
                newint.putExtra("id",id);
                newint.putExtra("name",name);

                String time = timeSpinner.getSelectedItem().toString().trim();
                Appointment app = new Appointment(name,time);
                mDatabase  = FirebaseDatabase.getInstance().getReference("Appointment").child(mAuth.getCurrentUser().getUid());

                String id = mDatabase.push().getKey();
                mDatabase.child(id).setValue(app);
                startActivity(newint);
            }
        });

    }

    public ArrayList<String> makeTimeList(int from, int to){
        ArrayList<String> result = new ArrayList<>();
        int track = from;
        int whole = from;
        int point = 0;
        String holder = "";
        String wholen = "";
        String pointn = "";
        while(whole < to){

            if(point == 0){
                holder = Integer.toString(whole) + ":00 - " + Integer.toString(whole) +":15";
                result.add(holder);
                point = 15;

            }
            else if(point == 15){
                holder = Integer.toString(whole)+ ":15 - " + Integer.toString(whole) +":30";
                result.add(holder);
                point = 30;
            }
            else if(point == 30){
                holder = Integer.toString(whole)+ ":30 - " + Integer.toString(whole) +":45";
                result.add(holder);
                point = 45;
            }

            else if (point == 45){
                holder = Integer.toString(whole)+ ":45 - " + Integer.toString(whole+1) +":00";
                result.add(holder);
                whole = whole+1;
                point = 0;

            }

        }

        return result;
    }
}
