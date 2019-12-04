package com.example.walkinclinicservices;

public class EditHours {

    private String mon, tue, wed,thur,fri,sat,sun, mon2, tue2, wed2,thur2, fri2, sat2, sun2;
    public EditHours(){
        this.mon = "0";
        this.tue = "0";
        this.wed = "0";
        this.thur = "0";
        this.fri = "0";
        this.sat = "0";
        this.sun = "0";
        this.mon2 = "0";
        this.tue2 = "0";
        this.wed2 = "0";
        this.thur2 = "0";
        this.fri2 = "0";
        this.sat2 = "0";
        this.sun2 = "0";


    }

    public EditHours(String mon, String mon2, String tue, String tue2,
                     String wed, String wed2, String thur, String thur2,
                     String fri, String fri2, String sat, String sat2,
                     String sun,String sun2){
        this.mon = mon;
        this.tue = tue;
        this.wed = wed;
        this.thur = thur;
        this.fri = fri;
        this.sat = sat;
        this.sun = sun;
        this.mon2 = mon2;
        this.tue2 = tue2;
        this.wed2 = wed2;
        this.thur2 = thur2;
        this.fri2 = fri2;
        this.sat2 = sat2;
        this.sun2 = sun2;
    }

    public String getMon() {
        return mon;
    }

    public String getTue() {
        return tue;
    }

    public String getWed() {
        return wed;
    }

    public String getFri() {
        return fri;
    }

    public String getThur() {
        return thur;
    }

    public String getSat() {
        return sat;
    }

    public String getSun() {
        return sun;
    }

    public String getMon2(){ return mon2;}

    public String getTue2(){ return tue2;}

    public String getWed2(){ return wed2;}

    public String getFri2(){ return fri2;}

    public String getSat2(){ return sat2;}

    public String getSun2(){ return sun2;}

    public String getThur2(){ return thur2;}

    public void setFri(String fri) {
        this.fri = fri;
    }
}
