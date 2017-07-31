package com.cleveroad.nikita_frolov_cr.initialtwoweek.data.model;

public class Student {
    private int id;
    private String name;
    private int idGroup;
    private int idPhoto;

    public Student(int id, String name, int idGroup, int idPhoto) {
        this.id = id;
        this.name = name;
        this.idGroup = idGroup;
        this.idPhoto = idPhoto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIdGroup() {
        return idGroup;
    }

    public void setIdGroup(int idGroup) {
        this.idGroup = idGroup;
    }

    public int getIdPhoto() {
        return idPhoto;
    }

    public void setIdPhoto(int idPhoto) {
        this.idPhoto = idPhoto;
    }
}
