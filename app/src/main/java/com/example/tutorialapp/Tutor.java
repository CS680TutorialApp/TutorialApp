package com.example.tutorialapp;

public class Tutor {

    private int id;
    private String name;
    private String zoomLink;
    private String address;
    private String phone;
    private String email;

    public Tutor(String name, String zoomLink, String address, String phone, String email) {
        this.name = name;
        this.zoomLink = zoomLink;
        this.address = address;
        this.phone = phone;
        this.email = email;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getZoomLink() {
        return this.zoomLink;
    }

    public void setZoomLink(String zoomLink) {
        this.zoomLink = zoomLink;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


}
