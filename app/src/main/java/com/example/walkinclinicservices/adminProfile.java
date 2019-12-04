package com.example.walkinclinicservices;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class adminProfile extends AppCompatActivity {


    private static final String TAG = "adminProfile";
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private DatabaseReference mDatabase;
    public ArrayList<Service> serviceList;
    public ArrayList<String> ids;


    //private TextView introMessage;
    private Button logoutBtn;
    private Button addBtn;
    //private Button delBtn;
    //private Button edtBtn;
    private Button actBtn;
    ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_profile);

        //Creating service list view
        Log.d(TAG, "onCreate: Started.");
        mListView = (ListView) findViewById(R.id.serviceList1);

        serviceList = new ArrayList<>();
        ids = new ArrayList<>();


        ServiceListAdapter adapter = new ServiceListAdapter(this, R.layout.adapter_view_layout,serviceList);
        mListView.setAdapter(adapter);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        logoutBtn = (Button) findViewById(R.id.logoutBtn);
        addBtn = (Button) findViewById(R.id.addBtn);
        actBtn = (Button) findViewById(R.id.actBtn);


        logoutBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){
                mAuth.signOut();
                finish();
                startActivity(new Intent(adminProfile.this, MainActivity.class));

            }
        });

        addBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v ){
                AlertDialog.Builder builder = new AlertDialog.Builder(adminProfile.this);
                builder.setMessage("Are you sure you want to Add a new services?")
                        .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                startActivity(new Intent(adminProfile.this, NewService.class ));

                            }
                        })
                        .setNegativeButton("No", null).setCancelable(false);
                AlertDialog alert = builder.create();
                alert.show();
                
            }
        });


        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                final Service service = serviceList.get(position);

                final String idneeded = ids.get(position);

// shows dialog box
                AlertDialog.Builder builder = new AlertDialog.Builder(adminProfile.this);
                builder.setMessage("Are you sure you want to edit this existing service?")
                        .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // shows list of services
                                showUpdateDialog(idneeded,service.getServiceName(),service.getRoleName());

                            }
                        })
                        .setNegativeButton("No", null).setCancelable(false);
                AlertDialog alert = builder.create();
                alert.show();

                showUpdateDialog(idneeded,service.getServiceName(),service.getRoleName());
                return false;
            }
        });

        /*delBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(adminProfile.this,DeleteService.class));
            }
        });*/

        actBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(adminProfile.this, Accounts.class));
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        mDatabase.child("Service").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                serviceList.clear();

                for(DataSnapshot serviceSnapshot : dataSnapshot.getChildren()){
                    Service service = serviceSnapshot.getValue(Service.class);
                    serviceList.add(service);
                    ids.add(serviceSnapshot.getKey());
                }

                ServiceListAdapter adapter = new ServiceListAdapter(adminProfile.this,R.layout.adapter_view_layout,serviceList);
                mListView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void showUpdateDialog(final String serviceId,String serviceName, String roleName){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.activity_delete_service,null);

        dialogBuilder.setView(dialogView);

        final EditText serviceText = (EditText) dialogView.findViewById(R.id.serviceText);
        final EditText roleText = (EditText) dialogView.findViewById(R.id.roleText);
        final Button updateBtn = (Button) dialogView.findViewById(R.id.updateBtn);
        final Button delBtn = (Button) dialogView.findViewById(R.id.delBtn);

        dialogBuilder.setTitle("Updating Service: " + serviceName);

        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = serviceText.getText().toString().trim();
                String role = roleText.getText().toString().trim();

                if(TextUtils.isEmpty(name)){
                    Toast.makeText(adminProfile.this,"Please enter all the details",Toast.LENGTH_SHORT).show();
                }

                updateService(serviceId,name,role);
                alertDialog.dismiss();
            }

        });

        delBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteService(serviceId);
            }
        });



    }

    private boolean updateService(String id, String serviceName, String roleName){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Service").child(id);

        Service service = new Service(serviceName, roleName);
        databaseReference.setValue(service);
        Toast.makeText(this, "Service successfully updated", Toast.LENGTH_LONG).show();

        return true;
    }


    private void deleteService(String serviceId){
        DatabaseReference drService = FirebaseDatabase.getInstance().getReference("Service").child(serviceId);

        drService.removeValue();

        Toast.makeText(this,"Service has been deleted",Toast.LENGTH_LONG).show();
    }

    public void onBackPressed(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("You are about to leave without signing out! if you leave you will be signed out");
        builder.setMessage("do you still want to leave?");

        builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                adminProfile.super.onBackPressed();

            }
        })
                .setNegativeButton("No", null).setCancelable(false);

        AlertDialog alert = builder.create();
        alert.show();

    }
}
