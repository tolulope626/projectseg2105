package com.example.walkinclinicservices;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ClinicServices extends AppCompatActivity {
    public String empID;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private DatabaseReference mDatabase;
    private DatabaseReference datClinicServices;

    private Button addBtn;
    public ArrayList<Service> serviceList;
    public ArrayList<String> ids;
    ListView mListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clinic_services);

        mListView = (ListView) findViewById(R.id.serviceList2);
        addBtn = (Button) findViewById(R.id.addBtn);

        serviceList = new ArrayList<>();
        ids = new ArrayList<>();

        ServiceListAdapter adapter = new ServiceListAdapter(this, R.layout.adapter_view_layout,serviceList);
        mListView.setAdapter(adapter);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        empID ="a";

        if(mUser == null){
            finish();
            startActivity(new Intent(this, MainActivity.class));
        }else {
            mDatabase.child("Employee").child(mUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    employee curemployee = dataSnapshot.getValue(employee.class);
                    empID = dataSnapshot.getKey();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                //Service service = serviceList.get(position);

                final String idneeded = ids.get(position);


                AlertDialog.Builder builder = new AlertDialog.Builder(ClinicServices.this);
                builder.setMessage("Are you sure you want to remove this service?")
                        .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                removeService(empID,idneeded);
                            }
                        })
                        .setNegativeButton("No", null).setCancelable(false);
                AlertDialog alert = builder.create();
                alert.show();



                return false;
            }
        });

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                startActivity(new Intent(ClinicServices.this,addService.class));

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(mUser == null){
            finish();
            startActivity(new Intent(this, MainActivity.class));
        }else {
            mDatabase.child("Employee").child(mUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    employee currentEmployee = dataSnapshot.getValue(employee.class);
                    empID = dataSnapshot.getKey();

                }



                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

        mDatabase.child("ClinicService").child(mUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                serviceList.clear();

                for(DataSnapshot serviceSnapshot : dataSnapshot.getChildren()){
                    Service service = serviceSnapshot.getValue(Service.class);
                    serviceList.add(service);
                    ids.add(serviceSnapshot.getKey());
                }

                ServiceListAdapter adapter = new ServiceListAdapter(ClinicServices.this,R.layout.adapter_view_layout,serviceList);
                mListView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void removeService(String empID, String idneeded){
        datClinicServices = FirebaseDatabase.getInstance().getReference("ClinicService").child(empID);

        datClinicServices.child(idneeded).removeValue();

        Toast.makeText(ClinicServices.this,"Service has been removed",Toast.LENGTH_LONG).show();
        //return true;
    }



    public void onBackPressed(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("You are about to leave");
        builder.setMessage("Are you sure you want to leave?");

        builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                ClinicServices.super.onBackPressed();

            }
        })
                .setNegativeButton("No", null).setCancelable(false);

        AlertDialog alert = builder.create();
        alert.show();

    }
}
