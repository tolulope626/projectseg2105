package com.example.walkinclinicservices;

/*import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;*/


public class patient{
    //Button btn;
    //TextView tv;
    //String st;
    //EditText et;

    private String firstName;
    // private String lastName;
    private String userName;
    private String email;
    private String password;

    public patient(){}
    public patient(String firstName,String email,String password){
        this.firstName = firstName;
        //this.lastName = lastName;
        //this.userName = userName;
        this.email = email;
        this.password = password;
    }

    public String getFirstName(){return firstName;}
    public void setFirstName(String firstName){this.firstName = firstName;}

    // public String getLastName(){return lastName;}
    // public void setLastName(String lastName){this.lastName = lastName;}

    // public String getUserName(){return userName;}
    //public void setUserName(String userName){this.userName = userName;}

    public String getEmail(){return email;}
    public void setEmail(String emai){this.email = email;}

    public String getPassword(){return password;}
    public void setPassword(String password){this.password = password;}


    /*@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient);

        tv = findViewById(R.id.patienttxtview);

        st = getIntent().getExtras().getString("value");
        tv.setText(st);
    }

    public void OnLogOutButton(View view){
        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
        startActivityForResult(intent,0);
    } */
}
