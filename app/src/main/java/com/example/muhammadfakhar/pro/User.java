package com.example.muhammadfakhar.pro;

import android.widget.EditText;

import java.io.Serializable;

public class User implements Serializable {
    private String Name;
    private String Password;
    private String Phone;
    private String Email;
    private boolean staffMember;

    public User() {
    }

    public User(String name, String password, String phone, String email, boolean staffMember) {
        Name = name;
        Password = password;
        Phone = phone;
        Email = email;
        this.staffMember = staffMember;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public boolean isStaffMember() {
        return staffMember;
    }

    public void setStaffMember(boolean staffMember) {
        this.staffMember = staffMember;
    }
}
