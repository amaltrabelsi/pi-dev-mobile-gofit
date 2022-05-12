package com.gofit.entities;

public class Utilisateur {

    private int id;
    private String email;
    private String role;
    private String password;
    private String num;

    public Utilisateur() {
    }

    public Utilisateur(int id, String email, String role, String password, String num) {
        this.id = id;
        this.email = email;
        this.role = role;
        this.password = password;
        this.num = num;
    }

    public Utilisateur(String email, String role, String password, String num) {
        this.email = email;
        this.role = role;
        this.password = password;
        this.num = num;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }


}