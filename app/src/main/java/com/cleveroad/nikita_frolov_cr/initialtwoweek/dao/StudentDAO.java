package com.cleveroad.nikita_frolov_cr.initialtwoweek.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.cleveroad.nikita_frolov_cr.initialtwoweek.data.UniversityContract;
import com.cleveroad.nikita_frolov_cr.initialtwoweek.data.UniversityContract.GroupEntry;
import com.cleveroad.nikita_frolov_cr.initialtwoweek.data.UniversityContract.StudentEntry;
import com.cleveroad.nikita_frolov_cr.initialtwoweek.data.UniversityDBHelper;

import static com.cleveroad.nikita_frolov_cr.initialtwoweek.data.UniversityContract.StudentEntry.TABLE_STUDENTS;

public class StudentDAO {
    private UniversityDBHelper mDbHelper;
    private SQLiteDatabase mDB;

    public StudentDAO(Context context) {
        mDbHelper = new UniversityDBHelper(context);
        mDB = mDbHelper.getWritableDatabase();
    }

    public void close() {
        if (mDbHelper !=null) mDbHelper.close();
    }

    public Cursor getAllStudents() {
        String table = StudentEntry.TABLE_STUDENTS +
                " inner join " + GroupEntry.TABLE_GROUPS +
                " on " + StudentEntry.COLUMN_GROUP_ID + " = " + GroupEntry.TABLE_GROUPS + "." + GroupEntry._ID;
        String columns[] = { StudentEntry.TABLE_STUDENTS + "." + StudentEntry._ID , StudentEntry.COLUMN_NAME, GroupEntry.COLUMN_NAME };
        return mDB.query(table, columns, null, null, null, null, null);
    }

    public void addStudent(String name, String group_id) {
        ContentValues cv = new ContentValues();
        cv.put(StudentEntry.COLUMN_NAME, name);
        cv.put(StudentEntry.COLUMN_GROUP_ID, group_id);
        mDB.insert(StudentEntry.TABLE_STUDENTS, null, cv);
    }
}
