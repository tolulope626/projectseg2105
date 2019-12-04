package com.example.walkinclinicservices;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;

public class NewService extends AppCompatActivity {

    private Button saveBtn;
    private EditText serviceText, roleText;

    FirebaseDatabase mDatabase;
    //FirebaseAuth mAuth

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_service);

        //intialization
        mDatabase = mDatabase.getInstance();

        saveBtn = (Button) findViewById(R.id.saveBtn);
        serviceText = (EditText) findViewById(R.id.serviceText);
        roleText = (EditText) findViewById((R.id.roleText));


        //when save btn is clicked
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validate()){
                    //Getting input texts
                    String name = serviceText.getText().toString();
                    String role = roleText.getText().toString();

                    //Create service
                    final Service mService = new Service(name,role);

                    String id = mDatabase.getReference("Service").push().getKey();

                    mDatabase.getReference("Service").child(id).setValue(mService).addOnCompleteListener(NewService.this, new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(NewService.this, "Service has been added", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(NewService.this, adminProfile.class);
                                startActivity(intent);
                            }else Toast.makeText(NewService.this, "Service addition unsuccessful", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }


    private Boolean validate(){
        Boolean result = false;
        String name = serviceText.getText().toString();
        String role = roleText.getText().toString();

        if(name.isEmpty() || role.isEmpty()){
            Toast.makeText(NewService.this,"Please enter all the details",Toast.LENGTH_SHORT).show();
        }else{
            result = true;
        }

        return result;
    }
}
