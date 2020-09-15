package com.example.contactsapplication;

import java.io.Serializable;
import java.util.List;

public class Contact implements Serializable {
    public String id, name, email, phone, type;

    public Contact(String id, String name, String email, String phone, String type){
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.type = type;
    }
    public Contact(String string){
        String[] list = string.split(",");
        this.id = list[0];
        this.name = list[1];
        this.email = list[2];
        this.phone = list[3];
        this.type = list[4];
    }

    @Override
    public String toString() {
        return name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
