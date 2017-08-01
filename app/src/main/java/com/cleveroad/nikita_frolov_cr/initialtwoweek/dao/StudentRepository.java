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

public class StudentRepository {
    private static final Uri STUDENT_URI = Uri.parse("content://"
            + BuildConfig.APPLICATION_ID + "." + UniversityDBHelper.DB_NAME + "/" +
            UniversityContract.StudentEntry.TABLE_STUDENTS);

    public List<Student> getAllStudents() {
        String columns[] = {StudentEntry._ID,
                StudentEntry.COLUMN_NAME, StudentEntry.COLUMN_GROUP_ID, StudentEntry.COLUMN_PHOTO_ID};

        Cursor cursorStudents = App.get().getContentResolver().query(STUDENT_URI, columns, null, null, null, null);

        List<Student> students = new ArrayList<>();

        if (cursorStudents != null) {
            while (cursorStudents.moveToNext()) {
                int idStudent = cursorStudents.getInt(cursorStudents.getColumnIndexOrThrow(StudentEntry._ID));
                String name = cursorStudents.getString(cursorStudents.getColumnIndexOrThrow(StudentEntry.COLUMN_NAME));
                int idGroup = cursorStudents.getInt(cursorStudents.getColumnIndexOrThrow(StudentEntry.COLUMN_GROUP_ID));
                int idPhoto = cursorStudents.getInt(cursorStudents.getColumnIndexOrThrow(StudentEntry.COLUMN_PHOTO_ID));
                students.add(new Student(idStudent, name, idGroup, idPhoto));
            }
            cursorStudents.close();
        }
        return students;
    }

    public Student getStudent(int id) {
        String columns[] = {StudentEntry._ID,
                StudentEntry.COLUMN_NAME, StudentEntry.COLUMN_GROUP_ID, StudentEntry.COLUMN_PHOTO_ID};

        String selection = StudentEntry._ID + " = ?";
        String[] selectionArgs = new String[]{String.valueOf(id)};

        Cursor cursorStudent = App.get().getContentResolver().query(Uri.parse(STUDENT_URI + "/" + id),
                columns, selection, selectionArgs, null, null);
        Student student = null;
        if (cursorStudent != null) {
            if (cursorStudent.moveToNext()) {
                int idStudent = cursorStudent.getInt(cursorStudent.getColumnIndexOrThrow(StudentEntry._ID));
                String name = cursorStudent.getString(cursorStudent.getColumnIndexOrThrow(StudentEntry.COLUMN_NAME));
                int idGroup = cursorStudent.getInt(cursorStudent.getColumnIndexOrThrow(StudentEntry.COLUMN_GROUP_ID));
                int idPhoto = cursorStudent.getInt(cursorStudent.getColumnIndexOrThrow(StudentEntry.COLUMN_PHOTO_ID));
                student = new Student(idStudent, name, idGroup, idPhoto);
            }
            cursorStudent.close();
        }

        return student;
    }

    public void removeStudent(int id) {
        App.get().getContentResolver().delete(STUDENT_URI, StudentEntry._ID + " = " + id, null);
    }

    public void editStudent(int id, String name, String idGroup, int photo_id) {
        ContentValues cvStudent = new ContentValues();
        cvStudent.put(StudentEntry.COLUMN_NAME, name);
        cvStudent.put(StudentEntry.COLUMN_GROUP_ID, idGroup);
        cvStudent.put(StudentEntry.COLUMN_PHOTO_ID, photo_id);
        App.get().getContentResolver().update(STUDENT_URI, cvStudent, StudentEntry._ID + " = " + id, null);
    }

    public void addStudent(String name, int group_id, int photo_id) {
        ContentValues cvStudent = new ContentValues();
        cvStudent.put(StudentEntry.COLUMN_NAME, name);
        cvStudent.put(StudentEntry.COLUMN_GROUP_ID, group_id);
        cvStudent.put(StudentEntry.COLUMN_PHOTO_ID, photo_id);
        App.get().getContentResolver().insert(STUDENT_URI, cvStudent);
    }
}
