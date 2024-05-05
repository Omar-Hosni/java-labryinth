package com.project.Models;

public class User {
    private Integer id;
    public String name;
    public String email;
    public String password;

    public User(Integer id, String n, String e, String p){
        id=id;
        name=n;
        email=e;
        password=p;
    }

    public User(String n, String e, String p){

        name=n;
        email=e;
        password=p;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
