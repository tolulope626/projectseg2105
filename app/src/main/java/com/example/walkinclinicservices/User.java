package com.example.walkinclinicservices;

public class User {
    private String firstName;
   // private String lastName;
    private String userName;
    private String email;
    private String password;

    public User(String firstName,String email,String password){
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


}
