package com.example.tutorialapp;

public class Subject {

    private int id;
    private String name;
    private String tutor;
    private String referenceLink;

    public Subject(String name, String tutor, String referenceLink) {
        this.name = name;
        this.tutor = tutor;
        this.referenceLink = referenceLink;
    }

    public int getIdChange() {
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

    public String getTutor() {
        return this.tutor;
    }

    public void setTutor(String tutor) {
        this.tutor = tutor;
    }

    public String getReferenceLink() {
        return this.referenceLink;
    }

    public void setReferenceLink(String referenceLink) {
        this.referenceLink = referenceLink;
    }





}
