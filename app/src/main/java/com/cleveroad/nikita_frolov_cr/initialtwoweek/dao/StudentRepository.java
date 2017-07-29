package com.cleveroad.nikita_frolov_cr.initialtwoweek.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.cleveroad.nikita_frolov_cr.initialtwoweek.data.UniversityContract.StudentEntry;
import com.cleveroad.nikita_frolov_cr.initialtwoweek.data.UniversityDBHelper;
import com.cleveroad.nikita_frolov_cr.initialtwoweek.model.Student;

import java.util.ArrayList;
import java.util.List;

public class StudentRepository implements Subject{
    private UniversityDBHelper mDbHelper;
    private SQLiteDatabase mDB;
    private Context mCtn;
    private List<RepositoryObserver> mObservers = new ArrayList<>();
    private static StudentRepository INSTANCE = null;

    private StudentRepository(Context context) {
        mCtn = context;
    }

    public static StudentRepository getInstance(Context context) {
        if(INSTANCE == null) {
            INSTANCE = new StudentRepository(context);
        }
        return INSTANCE;
    }

    public void open() {
        mDbHelper = new UniversityDBHelper(mCtn);
        mDB = mDbHelper.getWritableDatabase();
        notifyObservers();
    }

    public void close() {
        if (mDbHelper !=null) mDbHelper.close();
    }

    public List<Student> getAllStudents() {
        String table = StudentEntry.TABLE_STUDENTS;
        String columns[] = { StudentEntry._ID,
                StudentEntry.COLUMN_NAME, StudentEntry.COLUMN_GROUP_ID, StudentEntry.COLUMN_PHOTO_ID};

        Cursor c = mDB.query(table, columns, null, null, null, null, null);

        ArrayList<Student> students = new ArrayList<>();

        while(c.moveToNext()){
            int idStudent = c.getInt(c.getColumnIndexOrThrow(StudentEntry._ID));
            String name = c.getString(c.getColumnIndexOrThrow(StudentEntry.COLUMN_NAME));
            int idGroup = c.getInt(c.getColumnIndexOrThrow(StudentEntry.COLUMN_GROUP_ID));
            int idPhoto = c.getInt(c.getColumnIndexOrThrow(StudentEntry.COLUMN_PHOTO_ID));
            students.add(new Student(idStudent, name, idGroup, idPhoto));
        }
        return students;
    }

    public Student getStudent(int id) {
        String table = StudentEntry.TABLE_STUDENTS;
        String columns[] = {StudentEntry._ID,
                StudentEntry.COLUMN_NAME, StudentEntry.COLUMN_GROUP_ID, StudentEntry.COLUMN_PHOTO_ID};

        String selection = StudentEntry._ID + " = ?";
        String[] selectionArgs = new String[] {String.valueOf(id)};

        Cursor c = mDB.query(table, columns, selection, selectionArgs, null, null, null);
        c.moveToNext();
        int idStudent = c.getInt(c.getColumnIndexOrThrow(StudentEntry._ID));
        String name = c.getString(c.getColumnIndexOrThrow(StudentEntry.COLUMN_NAME));
        int idGroup = c.getInt(c.getColumnIndexOrThrow(StudentEntry.COLUMN_GROUP_ID));
        int idPhoto = c.getInt(c.getColumnIndexOrThrow(StudentEntry.COLUMN_PHOTO_ID));

        return new Student(idStudent, name, idGroup,idPhoto);
    }

    public void removeStudent(int id) {
        mDB.delete(StudentEntry.TABLE_STUDENTS, StudentEntry._ID + " = " + id, null);
        notifyObservers();
    }

    public void editStudent(int id, String name, String idGroup, int photo_id) {
        ContentValues cv = new ContentValues();
        cv.put(StudentEntry.COLUMN_NAME, name);
        cv.put(StudentEntry.COLUMN_GROUP_ID, idGroup);
        cv.put(StudentEntry.COLUMN_PHOTO_ID, photo_id);
        mDB.update(StudentEntry.TABLE_STUDENTS, cv, StudentEntry._ID + " = " + id, null);
        notifyObservers();
    }

    public void addStudent(String name, int group_id, int photo_id) {
        ContentValues cv = new ContentValues();
        cv.put(StudentEntry.COLUMN_NAME, name);
        cv.put(StudentEntry.COLUMN_GROUP_ID, group_id);
        cv.put(StudentEntry.COLUMN_PHOTO_ID, photo_id);
        mDB.insert(StudentEntry.TABLE_STUDENTS, null, cv);
        notifyObservers();
    }

    @Override
    public void registerObserver(RepositoryObserver repositoryObserver) {
        if(!mObservers.contains(repositoryObserver)) {
            mObservers.add(repositoryObserver);
        }
    }

    @Override
    public void removeObserver(RepositoryObserver repositoryObserver) {
        if(mObservers.contains(repositoryObserver)) {
            mObservers.remove(repositoryObserver);
        }
    }

    @Override
    public void notifyObservers() {
        for (RepositoryObserver observer: mObservers) {
            observer.onStudentDataChanged();
        }
    }
}
