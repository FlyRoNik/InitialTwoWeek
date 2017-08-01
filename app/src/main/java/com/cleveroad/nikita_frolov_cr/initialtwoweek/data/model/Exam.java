package com.cleveroad.nikita_frolov_cr.initialtwoweek.data.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "exams")
public class Exam extends Model {
    @Column(name = "name_exam")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}