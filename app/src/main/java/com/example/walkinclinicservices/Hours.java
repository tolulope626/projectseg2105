package com.example.walkinclinicservices;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Hours extends AppCompatActivity {

    private TextView monTxt,tueTxt,wedTxt,thurTxt,friTxt,satTxt,sunTxt;
    private Button editBtn;

    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hours);

        monTxt = (TextView) findViewById(R.id.monTxt);
        tueTxt = (TextView) findViewById(R.id.tueTxt);
        wedTxt = (TextView) findViewById(R.id.wedTxt);
        thurTxt = (TextView) findViewById(R.id.thurTxt);
        friTxt = (TextView) findViewById(R.id.friTxt);
        satTxt = (TextView) findViewById(R.id.satFTxt);
        sunTxt = (TextView) findViewById(R.id.sunTxt);

        editBtn = (Button) findViewById(R.id.editListBtn);


        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        if(mUser == null){
            finish();
            startActivity(new Intent(this, MainActivity.class));
        }else {
            mDatabase.child("Hours").child(mUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    EditHours hours = dataSnapshot.getValue(EditHours.class);

                    String mon = hours.getMon();
                    String mon2 = hours.getMon2();
                    String tue = hours.getTue();
                    String tue2 = hours.getTue2();
                    String wed = hours.getWed();
                    String wed2 = hours.getWed2();
                    String thur = hours.getThur();
                    String thur2 = hours.getThur2();
                    String fri = hours.getFri();
                    String fri2 = hours.getFri2();
                    String sat = hours.getSat();
                    String sat2 = hours.getSat2();
                    String sun = hours.getSun();
                    String sun2 = hours.getSun2();

                    //String display = "Welcome " + name + "! You are logged in";
                    monTxt.setText(mon + ":00 to " + mon2 +":00");
                    tueTxt.setText(tue + ":00 to " + tue2 +":00");
                    wedTxt.setText(wed + ":00 to " + wed2 +":00");
                    thurTxt.setText(thur + ":00 to " + thur2 +":00");
                    friTxt.setText(fri + ":00 to " + fri2 +":00");
                    satTxt.setText(sat+ ":00 to " + sat2 +":00");
                    sunTxt.setText(sun+ ":00 to " + sun2 +":00");
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Hours.this,Hours2.class));
            }
        });


    }
}
