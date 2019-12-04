package com.example.walkinclinicservices;

public class Service {
    private String serviceName;
    private String roleName;

    public Service(){}

    public Service(String serviceName, String roleName){
        this.serviceName = serviceName;
        this.roleName = roleName;
    }

    public String getServiceName() {
        return serviceName;
    }
    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getRoleName(){
        return roleName;
    }
    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
