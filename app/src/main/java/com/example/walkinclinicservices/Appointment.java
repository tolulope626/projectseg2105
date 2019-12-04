package com.example.walkinclinicservices;

public class Appointment {

    private String clinicname,time;

    public Appointment(){}

    public Appointment(String clinicname, String time){
        this.clinicname = clinicname;
        this.time = time;
    }

    public String getClinicname() {
        return clinicname;
    }

    public String getTime(){
        return time;
    }

    public void setClinicname(String clinicname) {
        this.clinicname = clinicname;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
