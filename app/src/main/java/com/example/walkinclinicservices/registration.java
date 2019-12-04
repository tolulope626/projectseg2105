package com.example.walkinclinicservices;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class registration extends AppCompatActivity {
    //EditText et;
    //String st;

    //Intent i;
    //Intent j;
    private EditText _txtUserName,_txtUserEmail,_txtUserPassword;
    private Button _btnRegister;
    private TextView _txtInfo;
    private Spinner _userType;


    FirebaseAuth firebaseAuth;
    FirebaseDatabase mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        _btnRegister = (Button)findViewById(R.id.btnRegister);
        _txtUserName = (EditText)findViewById(R.id.txtUserNamer);
        _txtUserEmail = (EditText)findViewById(R.id.txtUserEmail);
        _txtUserPassword = (EditText)findViewById(R.id.txtUserPassword);
        _txtInfo = (TextView) findViewById(R.id.txtInfo);

        firebaseAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();


        _userType = (Spinner)findViewById(R.id.userType2);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.usertype2,R.layout.support_simple_spinner_dropdown_item);
        _userType.setAdapter(adapter);


        _btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //et = findViewById(R.id.txtUserNamer);

                if(validate()) {
                    String userEmail = _txtUserEmail.getText().toString().trim();
                    String userPassword = _txtUserPassword.getText().toString().trim();


                    firebaseAuth.createUserWithEmailAndPassword(userEmail, userPassword).addOnCompleteListener(registration.this,new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            String item = _userType.getSelectedItem().toString();
                            String userName =  _txtUserName.getText().toString().trim();
                            String userEmail = _txtUserEmail.getText().toString().trim();
                            String userPassword = _txtUserPassword.getText().toString().trim();


                            if (task.isSuccessful() && item.equals("Patient")){

                                final patient mPatient = new patient(userName,userEmail,userPassword);
                                mDatabase.getReference("Patient").child(firebaseAuth.getCurrentUser().getUid()).setValue(mPatient).addOnCompleteListener(registration.this, new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                        if(task.isSuccessful()){
                                            Toast.makeText(registration.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(registration.this, welcomePatient.class);

                                            startActivity(intent);
                                        }else Toast.makeText(registration.this, "Registration unsuccessful", Toast.LENGTH_SHORT).show();
                                    }
                                });

                            }
                            else if (task.isSuccessful() && item.equals("Employee")){
                                final EditHours hours = new EditHours();
                                final employee mEmployee = new employee(userName,userEmail,userPassword);
                                mDatabase.getReference("Employee").child(firebaseAuth.getCurrentUser().getUid()).setValue(mEmployee).addOnCompleteListener(registration.this, new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {

                                                if(task.isSuccessful()){
                                                    Toast.makeText(registration.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                                                    Intent intent = new Intent(registration.this, welcomeEmployee.class);

                                                    startActivity(intent);
                                                }else Toast.makeText(registration.this, "Registration unsuccessful", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                );
                                mDatabase.getReference("Hours").child(firebaseAuth.getCurrentUser().getUid()).setValue(hours);


                               //Toast.makeText(registration.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                               //Intent intent = new Intent(registration.this, employeeProfile.class);

                               //startActivity(intent);

                            }else {
                               Toast.makeText(registration.this, "Registration unsuccessful", Toast.LENGTH_SHORT).show();

                            }


                           /* if (item.equals("Employee")){
                                i = new Intent(registration.this, employee.class);

                            }
                            if (item.equals("Patient")){
                                i = new Intent(registration.this, patient.class);
                            }
                            st = et.getText().toString();
                            i.putExtra("value", st);
                            startActivity(i);
                            finish(); */


                        }
                    });


                }


            }
        });






    }

    private Boolean validate(){
        Boolean result = false;
        String name = _txtUserName.getText().toString();
        String password = _txtUserPassword.getText().toString();
        String email = _txtUserEmail.getText().toString();

        if(name.isEmpty() || password.isEmpty() || email.isEmpty()){
            Toast.makeText(registration.this,"Please enter all the details",Toast.LENGTH_SHORT).show();
        }else{
            result = true;
        }

        return result;
    }



}
