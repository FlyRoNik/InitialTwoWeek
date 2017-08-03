package com.cleveroad.nikita_frolov_cr.initialtwoweek.dao;

import android.content.ContentResolver;
import android.net.Uri;

import com.cleveroad.nikita_frolov_cr.initialtwoweek.BuildConfig;
import com.cleveroad.nikita_frolov_cr.initialtwoweek.data.UniversityContract.StudentEntry;
import com.cleveroad.nikita_frolov_cr.initialtwoweek.data.UniversityDBHelper;
import com.cleveroad.nikita_frolov_cr.initialtwoweek.data.model.DaoSession;
import com.cleveroad.nikita_frolov_cr.initialtwoweek.data.model.GroupDao;
import com.cleveroad.nikita_frolov_cr.initialtwoweek.data.model.Student;
import com.cleveroad.nikita_frolov_cr.initialtwoweek.data.model.StudentDao;

import java.util.List;

public class StudentRepository {
    private static final Uri STUDENT_UPDATE_URI = Uri.parse("content://"
            + BuildConfig.APPLICATION_ID + "." + UniversityDBHelper.DB_NAME + "/" +
            StudentEntry.TABLE_STUDENTS);

    private ContentResolver mContentResolver;
    private StudentDao mStudentDao;
    private GroupDao mGroupDao;

    public StudentRepository(ContentResolver mContentResolver, DaoSession daoSession) {
        this.mContentResolver = mContentResolver;
        mStudentDao = daoSession.getStudentDao();
        mGroupDao = daoSession.getGroupDao();
    }

    public List<Student> getAllStudents(){
        return mStudentDao.loadAll();
    }

    public Student getStudent(long id){
        return mStudentDao.load(id);
    }

    public void editStudent(long id, String name, long idGroup, long idPhoto){
        Student student = mStudentDao.load(id);
        student.setName(name);
        student.setGroup(mGroupDao.load(idGroup));
        student.setIdPhoto(idPhoto);
        mStudentDao.save(student);
        mContentResolver.notifyChange(STUDENT_UPDATE_URI, null);
    }

    public void addStudent(String name, long idGroup, long idPhoto){
        Student student = new Student();
        student.setName(name);
        student.setGroup(mGroupDao.load(idGroup));
        student.setIdPhoto(idPhoto);
        mStudentDao.save(student);
        mContentResolver.notifyChange(STUDENT_UPDATE_URI, null);
    }

    public void deleteStudent(long id){
        Student student = mStudentDao.load(id);
        mStudentDao.delete(student);
        mContentResolver.notifyChange(STUDENT_UPDATE_URI, null);
    }
}
