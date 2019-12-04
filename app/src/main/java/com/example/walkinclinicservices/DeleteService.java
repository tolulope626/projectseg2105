package com.example.walkinclinicservices;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class DeleteService extends AppCompatActivity {

    private EditText serviceName, roleName;
    private Button delBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_service);

        serviceName =  (EditText) findViewById(R.id.serviceText);
        roleName = (EditText) findViewById(R.id.roleText);
        delBtn = (Button) findViewById(R.id.delBtn);
    }
}
