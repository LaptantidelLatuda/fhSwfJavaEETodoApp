package com.webapp.backend.models;

public class Users {
    private Integer IDUser;
    private String Name;

    public String getName(){
        return Name;
    }

    public Integer getIdUser(){
        return IDUser;
    }

    public void setName(String Name){
        this.Name = Name;
    }

    public void setIdUser(int IDUser){
        this.IDUser = IDUser;
    }
}
