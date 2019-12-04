package com.example.walkinclinicservices;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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

public class addService extends AppCompatActivity {

    //String idneeded;
    public String empID;

    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private DatabaseReference mDatabase;
    private DatabaseReference datClinicServices;
    public ArrayList<Service> serviceList;
    public ArrayList<String> ids;


    ListView mListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_service);

        mListView = (ListView) findViewById(R.id.serviceList3);

        serviceList = new ArrayList<>();
        ids = new ArrayList<>();


        ServiceListAdapter adapter = new ServiceListAdapter(this, R.layout.adapter_view_layout,serviceList);
        mListView.setAdapter(adapter);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        empID = "a";

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

                final Service service = serviceList.get(position);

                //String idneeded = ids.get(position);
                final String idneeded = ids.get(position);
                AlertDialog.Builder builder = new AlertDialog.Builder(addService.this);
                builder.setMessage("Are you sure you want to delete this Account?")
                        .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                updateService(empID,service);

                            }
                        })
                        .setNegativeButton("No", null).setCancelable(false);
                AlertDialog alert = builder.create();
                alert.show();

                Toast.makeText(addService.this, "Service has been added", Toast.LENGTH_SHORT).show();

                return false;
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
                    employee curemployee = dataSnapshot.getValue(employee.class);
                    empID = dataSnapshot.getKey();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

        mDatabase.child("Service").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                serviceList.clear();

                for(DataSnapshot serviceSnapshot : dataSnapshot.getChildren()){
                    Service service = serviceSnapshot.getValue(Service.class);
                    serviceList.add(service);
                    ids.add(serviceSnapshot.getKey());
                }

                ServiceListAdapter adapter = new ServiceListAdapter(addService.this,R.layout.adapter_view_layout,serviceList);
                mListView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public boolean updateService(String id, Service service){
        datClinicServices = FirebaseDatabase.getInstance().getReference("ClinicService").child(id);

        String ids = datClinicServices.push().getKey();
        datClinicServices.child(ids).setValue(service);
        return true;
    }

    public void onBackPressed(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("You are about to leave");
        builder.setMessage("Are you sure you want to leave?");

        builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                addService.super.onBackPressed();

            }
        })
                .setNegativeButton("No", null).setCancelable(false);

        AlertDialog alert = builder.create();
        alert.show();

    }


}
