package com.cleveroad.nikita_frolov_cr.initialtwoweek.data.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.util.List;

@Table(name = "groups")
public class Group extends Model{
    @Column(name = "name_group")
    private String name;

    public List<Student> getGroupStudents(){
        return getMany(Student.class, "id_group");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
