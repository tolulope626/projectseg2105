package com.example.walkinclinicservices;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class Accounts extends AppCompatActivity {
    private ListView actList;
    private static final String TAG = "Accounts";
    public ArrayList<employee> accountList;
    public ArrayList<String> ids;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accounts);

        Log.d(TAG, "onCreate: Started.");
        actList =  (ListView) findViewById(R.id.actList);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        accountList = new ArrayList<>();
        ids = new ArrayList<>();

        AccountListAdapter adapter = new AccountListAdapter(Accounts.this, R.layout.adapter_view_layout,accountList);
        actList.setAdapter(adapter);

        actList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {


                //employee act = accountList.get(position);

                final String idneeded = ids.get(position);
                AlertDialog.Builder builder = new AlertDialog.Builder(Accounts.this);
                builder.setMessage("Are you sure you want to delete this Account?")
                        .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                deleteAccount(idneeded);
                            }
                        })
                        .setNegativeButton("No", null).setCancelable(false);
                AlertDialog alert = builder.create();
                alert.show();

                return false;
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        mDatabase.child("Employee").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                accountList.clear();

                for(DataSnapshot serviceSnapshot : dataSnapshot.getChildren()){
                    employee employee1 = serviceSnapshot.getValue(employee.class);
                    accountList.add(employee1);
                    ids.add(serviceSnapshot.getKey());
                }

                AccountListAdapter adapter = new AccountListAdapter(Accounts.this,R.layout.adapter_view_layout,accountList);
                actList.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void deleteAccount(String serviceId){
        DatabaseReference drAccount = FirebaseDatabase.getInstance().getReference("Employee").child(serviceId);

        drAccount.removeValue();

        Toast.makeText(this,"Account has been deleted",Toast.LENGTH_LONG).show();
    }

    public void popUpDialogDel(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to delete this Account?");
        builder.setCancelable(false);
        builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                finish();

            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                dialogInterface.cancel();
            }
        });


        AlertDialog alertDialog = builder.create();
        alertDialog.show();

        super.onBackPressed();

    }

    public void onBackPressed(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("You are about to leave");
        builder.setMessage("Are you sure you want to leave?");

        builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Accounts.super.onBackPressed();

            }
        })
                .setNegativeButton("No", null).setCancelable(false);

        AlertDialog alert = builder.create();
        alert.show();

    }
}
