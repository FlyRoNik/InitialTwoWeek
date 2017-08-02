package com.cleveroad.nikita_frolov_cr.initialtwoweek.dao;

import android.content.ContentResolver;
import android.net.Uri;

import com.cleveroad.nikita_frolov_cr.initialtwoweek.BuildConfig;
import com.cleveroad.nikita_frolov_cr.initialtwoweek.data.UniversityDBHelper;
import com.cleveroad.nikita_frolov_cr.initialtwoweek.data.model.Group;
import com.cleveroad.nikita_frolov_cr.initialtwoweek.data.model.Student;

import java.util.List;

import static com.cleveroad.nikita_frolov_cr.initialtwoweek.data.UniversityContract.StudentEntry;

public class StudentRepository {
    private static final Uri STUDENT_UPDATE_URI = Uri.parse("content://"
            + BuildConfig.APPLICATION_ID + "." + UniversityDBHelper.DB_NAME + "/" +
            StudentEntry.TABLE_STUDENTS);

    private ContentResolver mContentResolver;

    public StudentRepository(ContentResolver mContentResolver) {
        this.mContentResolver = mContentResolver;
    }

    public List<Student> getAllStudents(){
        return Student.all(Student.class);
    }

    public Student getStudent(long id){
        return Student.load(Student.class, id);
    }

    public void editStudent(long id, String name, long idGroup, long idPhoto){
        Student student = Student.load(Student.class, id);
        student.setName(name);
        student.setGroup(Group.load(Group.class, idGroup));
        student.setIdPhoto(idPhoto);
        student.save();
        mContentResolver.notifyChange(STUDENT_UPDATE_URI, null);
    }

    public void addStudent(String name, long idGroup, long idPhoto){
        Student student = new Student();
        student.setName(name);
        student.setGroup(Group.load(Group.class, idGroup));
        student.setIdPhoto(idPhoto);
        student.save();
        mContentResolver.notifyChange(STUDENT_UPDATE_URI, null);
    }

    public void deleteStudent(long id){
        Student student = Student.load(Student.class, id);
        student.delete();
        mContentResolver.notifyChange(STUDENT_UPDATE_URI, null);
    }
}
