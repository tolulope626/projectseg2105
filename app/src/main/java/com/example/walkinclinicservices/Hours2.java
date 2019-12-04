package com.example.walkinclinicservices;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Hours2 extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private DatabaseReference mDatabase;

    String id, mon, tue, wed, thur, fri, sat, sun;

    private Button saveBtn;
    private EditText monFTxt, monTTxt, tueTTxt, tueFTxt, wedTTxt, wedFTxt, thurTTxt, thurFTxt, friTTxt, friFTxt, satFTxt, satTTxt, sunFTxt,sunTTxt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hours2);

        monFTxt = (EditText) findViewById(R.id.monFTxt) ;
        monTTxt = (EditText) findViewById(R.id.monTTxt);
        tueTTxt = (EditText) findViewById(R.id.tueTTxt);
        tueFTxt = (EditText) findViewById(R.id.tueFTxt);
        wedFTxt = (EditText) findViewById(R.id.wedFTxt);
        wedTTxt = (EditText) findViewById(R.id.wedTTxt);
        thurFTxt = (EditText) findViewById(R.id.thurFTxt);
        thurTTxt = (EditText) findViewById(R.id.thurTTxt);
        friTTxt = (EditText) findViewById(R.id.friTTxt);
        friFTxt = (EditText) findViewById(R.id.friFTxt);
        satFTxt = (EditText) findViewById(R.id.satFTxt);
        satTTxt = (EditText) findViewById(R.id.satTTxt);
        sunFTxt = (EditText) findViewById(R.id.sunFTxt);
        sunTTxt = (EditText) findViewById(R.id.sunTTxt);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        saveBtn = (Button) findViewById(R.id.saveBtn);

        if(mUser == null){
            finish();
            startActivity(new Intent(this, MainActivity.class));
        }else {
            mDatabase.child("Hours").child(mUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    EditHours hours = dataSnapshot.getValue(EditHours.class);
                    id = dataSnapshot.getKey();

                    mon = hours.getMon();
                    tue = hours.getTue();
                    wed = hours.getWed();
                    thur = hours.getThur();
                    fri = hours.getFri();
                    sat = hours.getSat();
                    sun = hours.getSun();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(Hours2.this);
                builder.setMessage("Are you sure you want to save theses changes?")
                        .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // saves info
                                updateHours(id);
                                startActivity(new Intent(Hours2.this,Hours.class));
                            }
                        })
                        .setNegativeButton("No", null).setCancelable(false);
                AlertDialog alert = builder.create();
                alert.show();

            }
        });
    }

    public void updateHours(String idneeded){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Hours").child(idneeded);

        String mon = monFTxt.getText().toString().trim();
        String mon2 = monTTxt.getText().toString().trim();
        String tue = tueFTxt.getText().toString().trim() ;
        String tue2 = tueTTxt.getText().toString().trim();
        String wed = wedFTxt.getText().toString().trim();
        String wed2 = wedTTxt.getText().toString().trim();
        String thur = thurFTxt.getText().toString().trim();
        String thur2 = thurTTxt.getText().toString().trim();
        String fri = friFTxt.getText().toString().trim();
        String fri2 = friTTxt.getText().toString().trim();
        String sat = satFTxt.getText().toString().trim();
        String sat2 = satTTxt.getText().toString().trim();
        String sun = sunFTxt.getText().toString().trim();
        String sun2 = sunTTxt.getText().toString().trim();

        boolean res = validate(mon,tue,wed,thur,fri,sat,sun, mon2, tue2, wed2, thur2, fri2, sat2, sun2);

        if(res == true){
            EditHours hours2 = new EditHours(mon, mon2, tue, tue2, wed, wed2,thur, thur2, fri, fri2, sat, sat2, sun,sun2);
            databaseReference.setValue(hours2);
            Toast.makeText(this, "Hours successfully updated", Toast.LENGTH_SHORT).show();

        }
    }

    public boolean validate(String mon, String tue, String wed, String thur, String fri, String sat, String sun,
                            String i, String j, String k, String l, String m, String n, String o){
        if(mon.equals("")|| tue.equals("") || wed.equals("") || thur.equals("") || fri.equals("") || sat.equals("") || sun.equals("")
        || i.equals("")|| j.equals("") || k.equals("") || l.equals("") || m.equals("") || n.equals("") || o.equals("")){
            Toast.makeText(Hours2.this,"Please enter all fields", Toast.LENGTH_SHORT).show();
            return false;
        }else {
            return true;
        }
    }

    public void onBackPressed(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("You are about to leave");
        builder.setMessage("Are you sure you want to leave without saving changes?");

        builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Hours2.super.onBackPressed();

            }
        })
                .setNegativeButton("No", null).setCancelable(false);

        AlertDialog alert = builder.create();
        alert.show();

    }
}
