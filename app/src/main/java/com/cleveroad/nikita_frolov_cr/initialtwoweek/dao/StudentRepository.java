package com.cleveroad.nikita_frolov_cr.initialtwoweek.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import com.cleveroad.nikita_frolov_cr.initialtwoweek.App;
import com.cleveroad.nikita_frolov_cr.initialtwoweek.BuildConfig;
import com.cleveroad.nikita_frolov_cr.initialtwoweek.data.UniversityContract;
import com.cleveroad.nikita_frolov_cr.initialtwoweek.data.UniversityContract.StudentEntry;
import com.cleveroad.nikita_frolov_cr.initialtwoweek.data.UniversityDBHelper;
import com.cleveroad.nikita_frolov_cr.initialtwoweek.data.model.Student;

import java.util.ArrayList;
import java.util.List;

public class StudentRepository{
    private static final Uri STUDENT_URI = Uri.parse("content://"
            + BuildConfig.APPLICATION_ID + "." + UniversityDBHelper.DB_NAME + "/" +
            UniversityContract.StudentEntry.TABLE_STUDENTS);

    public List<Student> getAllStudents() {
        String columns[] = {StudentEntry._ID,
                StudentEntry.COLUMN_NAME, StudentEntry.COLUMN_GROUP_ID, StudentEntry.COLUMN_PHOTO_ID};

        Cursor c = App.get().getContentResolver().query(STUDENT_URI, columns, null, null, null, null);

        ArrayList<Student> students = new ArrayList<>();

        if (c != null) {
            while(c.moveToNext()){
                int idStudent = c.getInt(c.getColumnIndexOrThrow(StudentEntry._ID));
                String name = c.getString(c.getColumnIndexOrThrow(StudentEntry.COLUMN_NAME));
                int idGroup = c.getInt(c.getColumnIndexOrThrow(StudentEntry.COLUMN_GROUP_ID));
                int idPhoto = c.getInt(c.getColumnIndexOrThrow(StudentEntry.COLUMN_PHOTO_ID));
                students.add(new Student(idStudent, name, idGroup, idPhoto));
            }
        }
        return students;
    }

    public Student getStudent(int id) {
        String columns[] = {StudentEntry._ID,
                StudentEntry.COLUMN_NAME, StudentEntry.COLUMN_GROUP_ID, StudentEntry.COLUMN_PHOTO_ID};

        String selection = StudentEntry._ID + " = ?";
        String[] selectionArgs = new String[] {String.valueOf(id)};

        Cursor c = App.get().getContentResolver().query(Uri.parse(STUDENT_URI + "/" + id),
                columns, selection, selectionArgs, null, null);
        Student student = null; //TODO question
        if (c != null) {
            c.moveToNext();
            int idStudent = c.getInt(c.getColumnIndexOrThrow(StudentEntry._ID));
            String name = c.getString(c.getColumnIndexOrThrow(StudentEntry.COLUMN_NAME));
            int idGroup = c.getInt(c.getColumnIndexOrThrow(StudentEntry.COLUMN_GROUP_ID));
            int idPhoto = c.getInt(c.getColumnIndexOrThrow(StudentEntry.COLUMN_PHOTO_ID));
            student = new Student(idStudent, name, idGroup,idPhoto);
        }

        return student;
    }

    public void removeStudent(int id) {
        App.get().getContentResolver().delete(STUDENT_URI, StudentEntry._ID + " = " + id, null);
    }

    public void editStudent(int id, String name, String idGroup, int photo_id) {
        ContentValues cv = new ContentValues();
        cv.put(StudentEntry.COLUMN_NAME, name);
        cv.put(StudentEntry.COLUMN_GROUP_ID, idGroup);
        cv.put(StudentEntry.COLUMN_PHOTO_ID, photo_id);
        App.get().getContentResolver().update(STUDENT_URI, cv, StudentEntry._ID + " = " + id, null);
    }

    public void addStudent(String name, int group_id, int photo_id) {
        ContentValues cv = new ContentValues();
        cv.put(StudentEntry.COLUMN_NAME, name);
        cv.put(StudentEntry.COLUMN_GROUP_ID, group_id);
        cv.put(StudentEntry.COLUMN_PHOTO_ID, photo_id);
        App.get().getContentResolver().insert(STUDENT_URI, cv);
    }
}
