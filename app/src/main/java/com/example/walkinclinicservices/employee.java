package com.example.walkinclinicservices;

/*import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;*/

public class employee{

    //TextView tv;
    //String st;

    private String firstName;
    // private String lastName;
    private String userName;
    private String email;
    private String password;
    private String number, address, insurance, payment, clinic;

    public employee(){}
    public employee(String firstName,String email,String password){
        this.firstName = firstName;
        //this.lastName = lastName;
        //this.userName = userName;
        this.email = email;
        this.password = password;
        this.address = "Edit your profile!";
        this.clinic = "Edit your profile!";
        this.number = "Edit your profile!";
        this.insurance = "Edit your profile!";
        this.payment = "Edit your profile!";
    }



    public employee(String firstName, String email,String password, String address, String number, String clinic ,String insurance, String payment){
        this.firstName = firstName;
        this.email = email;
        this.password = password;
        this.address = address;
        this.number = number;
        this.clinic = clinic;
        this.insurance = insurance;
        this.payment = payment;
    }

    public String getFirstName(){return firstName;}
    public void setFirstName(String firstName){this.firstName = firstName;}

    // public String getLastName(){return lastName;}
    // public void setLastName(String lastName){this.lastName = lastName;}

    // public String getUserName(){return userName;}
    //public void setUserName(String userName){this.userName = userName;}

    public String getEmail(){return email;}
    public void setEmail(String email){this.email = email;}

    public String getPassword(){return password;}
    public void setPassword(String password){this.password = password;}

    public String getNumber(){return number;}
    public void setNumber(String number){this.number = number;}

    public String getAddress(){return address;}
    public void setAddress(String address){this.address = address;}

    public String getClinic(){return clinic;}
    public void setClinic(String clinic){this.clinic= clinic;}

    public String getInsurance(){return insurance;}
    public void setInsurance(String insurance){this.insurance = insurance;}

    public String getPayment(){return payment;}
    public void setPayment(String payment){this.payment = payment;}
}
