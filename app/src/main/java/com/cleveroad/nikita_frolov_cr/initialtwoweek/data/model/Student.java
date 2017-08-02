package com.cleveroad.nikita_frolov_cr.initialtwoweek.data.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "students")
public class Student extends Model{
    @Column(name = "name_student")
    private String name;

    @Column(name = "id_group")
    private Group group;

    @Column(name = "id_photo")
    private Long idPhoto;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public Long getIdPhoto() {
        return idPhoto;
    }

    public void setIdPhoto(Long idPhoto) {
        this.idPhoto = idPhoto;
    }
}
