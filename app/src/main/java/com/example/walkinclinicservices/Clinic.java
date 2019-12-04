package com.example.walkinclinicservices;

import java.util.ArrayList;

public class Clinic {

    private String name;
    private ArrayList<Service> services;


    public Clinic(){}
    public Clinic(String name){
        this.name = name;
        this.services = new ArrayList<>();
    }

    public String getName(){return name;}
    public void setName(String name){this.name = name;}

    public ArrayList<Service> getServices(){return services;}
    public void setServices(ArrayList<Service> services){this.services = services;}

    public void addService(Service service) {
        services.add(service);
    }
}
