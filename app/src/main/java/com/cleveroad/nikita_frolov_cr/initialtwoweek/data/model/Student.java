package com.cleveroad.nikita_frolov_cr.initialtwoweek.data.model;

public class Student {
    private int mId;
    private String mName;
    private int mIdGroup;
    private int mIdPhoto;

    public Student(int id, String name, int idGroup, int idPhoto) {
        this.mId = id;
        this.mName = name;
        this.mIdGroup = idGroup;
        this.mIdPhoto = idPhoto;
    }

    public int getId() {
        return mId;
    }

    public void setId(int mId) {
        this.mId = mId;
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public int getIdGroup() {
        return mIdGroup;
    }

    public void setIdGroup(int mIdGroup) {
        this.mIdGroup = mIdGroup;
    }

    public int getIdPhoto() {
        return mIdPhoto;
    }

    public void setIdPhoto(int mIdPhoto) {
        this.mIdPhoto = mIdPhoto;
    }
}
