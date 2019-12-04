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
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class employeeProfile extends AppCompatActivity {


    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private DatabaseReference mDatabase;

    String idneeded,name,email,password,address1,number1, clinic1,payment1,insurance1;
    //private TextView introMessage;
    private EditText strTxt,unitTxt,strNameTxt, cityTxt, provinceTxt, contryTxt, numberTxt, clinicTxt, insuranceTxt, paymentTxt;
    private TextView adsTxt, phTxt;
    private Button logoutBtn;
    private Button saveBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_profile);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        //introMessage = (TextView) findViewById(R.id.introMessage);
        strTxt = (EditText) findViewById(R.id.strText) ;
        unitTxt= (EditText) findViewById(R.id.unitTxt);
        strNameTxt = (EditText) findViewById(R.id.strNameTxt);
        cityTxt = (EditText) findViewById(R.id.cityTxt);
        provinceTxt = (EditText) findViewById(R.id.provinceTxt);
        contryTxt = (EditText) findViewById(R.id.contryTxt);
        numberTxt = (EditText) findViewById(R.id.numberTxt);
        clinicTxt = (EditText) findViewById(R.id.clinicTxt);
        insuranceTxt = (EditText) findViewById(R.id.insuranceTxt);
        paymentTxt = (EditText) findViewById(R.id.paymentTxt);

        adsTxt = (TextView) findViewById(R.id.textView13);
        phTxt = (TextView) findViewById(R.id.textView14);


        logoutBtn = (Button) findViewById(R.id.logoutBtn);
        saveBtn = (Button) findViewById(R.id.saveBtn);

        if(mUser == null){
            finish();
            startActivity(new Intent(this, MainActivity.class));
        }else {
            mDatabase.child("Employee").child(mUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    employee curemployee = dataSnapshot.getValue(employee.class);
                    idneeded = dataSnapshot.getKey();

                    name = curemployee.getFirstName();
                    email = curemployee.getEmail();
                    password = curemployee.getPassword();
                    address1 = curemployee.getAddress();
                    clinic1 = curemployee.getClinic();
                    payment1 = curemployee.getPayment();
                    number1 = curemployee.getNumber();
                    insurance1 = curemployee.getInsurance();

                    /*curemployee.setAddress(address);
                    curemployee.setNumber(number);
                    curemployee.setClinic(clinic);
                    curemployee.setInsurance(insurance);
                    curemployee.setPayment(payment);*/
                    //String display = "Welcome " + name + "! You are logged in";
                    //introMessage.setText(display);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

        /*logoutBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(employeeProfile.this);
                builder.setMessage("Are you sure you want to save theses changes?")
                        .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // logs out
                                mAuth.signOut();
                                finish();
                                startActivity(new Intent(employeeProfile.this, MainActivity.class));
                            }
                        })
                        .setNegativeButton("No", null).setCancelable(false);
                AlertDialog alert = builder.create();
                alert.show();


            }
        });*/

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // pop up dialog box
                AlertDialog.Builder builder = new AlertDialog.Builder(employeeProfile.this);
                builder.setMessage("Are you sure you want to save theses changes?")
                        .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // saves info
                                boolean res = updateEmployee(idneeded,name,email,password);
                                if(res == true) {
                                    startActivity(new Intent(employeeProfile.this, employeeProfile2.class));
                                }
                            }
                        })
                        .setNegativeButton("No", null).setCancelable(false);
                AlertDialog alert = builder.create();
                alert.show();

            }
        });
    }

    private boolean updateEmployee(String id, String name, String email, String password){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Employee").child(id);

        String address = strTxt.getText().toString() + " " + strNameTxt.getText().toString() + unitTxt.getText().toString() + " " + cityTxt.getText().toString() + ", " + provinceTxt.getText().toString() + " " + contryTxt.getText().toString();
        String number = numberTxt.getText().toString().trim();
        String clinic = clinicTxt.getText().toString().trim();
        String insurance = insuranceTxt.getText().toString().trim();
        String payment = paymentTxt.getText().toString().trim();

        boolean res = true;

        if(address.isEmpty() || address.equals("  ,  ") || number.isEmpty() || clinic.isEmpty() || insurance.isEmpty() || payment.isEmpty()){
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            res = false;
        }
        //if(number.isEmpty()){number = number1;}
        //if(clinic.isEmpty()){clinic = clinic1;}
        //if(insurance.isEmpty()){insurance = insurance1;}
        //if(payment.isEmpty()){payment = payment1;}

        if(res == true) {
            employee curemployee = new employee(name, email, password, address, number, clinic, insurance, payment);
            databaseReference.setValue(curemployee);
            Toast.makeText(this, "Account successfully updated", Toast.LENGTH_SHORT).show();
        }
        return res;
    }

    public void onBackPressed(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("You are about to leave");
        builder.setMessage("Are you sure you want to leave without saaving changes?");

        builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
               employeeProfile.super.onBackPressed();

            }
        })
                .setNegativeButton("No", null).setCancelable(false);

        AlertDialog alert = builder.create();
        alert.show();

    }
}
