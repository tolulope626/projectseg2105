package com.example.walkinclinicservices;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

public class MainActivity extends AppCompatActivity {
    private EditText _txtUsername, _txtPassword;
    private Button _btnLogin,_btnSignUp;
    private Spinner _userType;
    private FirebaseAuth firebaseAuth;
    //DatabaseReference databseServices

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        _txtPassword = (EditText)findViewById(R.id.txtUserPassword);
        _txtUsername= (EditText)findViewById(R.id.txtUsername);
        _btnLogin = (Button)findViewById(R.id.btnLogin);
        _btnSignUp = (Button)findViewById(R.id.btnSignUp);
        _userType = (Spinner)findViewById(R.id.userType);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.usertype,R.layout.support_simple_spinner_dropdown_item);
        _userType.setAdapter(adapter);

        firebaseAuth = firebaseAuth.getInstance();

        FirebaseUser user = firebaseAuth.getCurrentUser();
        //if (user != null){
        //    finish();

        //}
        _btnLogin.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                authenticate(_txtUsername.getText().toString().trim(), _txtPassword.getText().toString().trim());
            }
        });

        _btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,registration.class);
                startActivity(intent);
            }
        });
    }

    private void authenticate(String userName, String userPassword){
        if (validate()){
            firebaseAuth.signInWithEmailAndPassword(userName, userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    String item = _userType.getSelectedItem().toString();
                    if (task.isSuccessful() && item.equals("Admin")) {
                        Intent intent = new Intent(MainActivity.this, adminProfile.class);
                        startActivity(intent);
                    } else if (task.isSuccessful() && item.equals("Employee")) {
                        Intent intent = new Intent(MainActivity.this, employeeProfile2.class);
                        startActivity(intent);
                    } else if (task.isSuccessful() && item.equals("Patient")) {
                        Intent intent = new Intent(MainActivity.this, patientProfile.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(MainActivity.this, "Login unsuccessful", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private Boolean validate(){
        Boolean result = false;
        String name = _txtUsername.getText().toString();
        String password = _txtPassword.getText().toString();

        if(name.isEmpty() || password.isEmpty()){
            Toast.makeText(MainActivity.this,"Please enter all the details",Toast.LENGTH_SHORT).show();
        }else{
            result = true;
        }

        return result;
    }
}
