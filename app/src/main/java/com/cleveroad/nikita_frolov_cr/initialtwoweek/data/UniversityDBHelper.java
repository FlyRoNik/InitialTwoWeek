package com.cleveroad.nikita_frolov_cr.initialtwoweek.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class UniversityDBHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "universitydb";
    public static final int DB_VERSION = 1;

    private static UniversityDBHelper INSTANCE;

    private UniversityDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public static synchronized UniversityDBHelper getInstance(Context context)
    {
        if (INSTANCE == null)
            INSTANCE = new UniversityDBHelper(context);

        return INSTANCE;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
//        db.execSQL("delete from "+ UniversityContract.StudentEntry.TABLE_STUDENTS);
//        db.execSQL("delete from "+ UniversityContract.GroupEntry.TABLE_GROUPS);
//        db.execSQL("delete from "+ UniversityContract.ExamEntry.TABLE_EXAMS);

//        db.execSQL(UniversityContract.StudentEntry.TABLE_STUDENTS_CREATE);
//        db.execSQL(UniversityContract.GroupEntry.TABLE_GROUPS_CREATE);
//        db.execSQL(UniversityContract.ExamEntry.TABLE_EXAMS_CREATE);
//        db.execSQL(RatingEntry.TABLE_RATINGS_CREATE);
//        db.execSQL(GroupExamsEntry.TABLE_GROUPS_EXAMS_CREATE);

//        ContentValues cv = new ContentValues();
//        for (int i = 1; i < 4; i++) {
//            cv.put(GroupEntry.COLUMN_NAME, "group " + i);
//            db.insert(GroupEntry.TABLE_GROUPS, null, cv);
//        }
//
//        cv = new ContentValues();
//        cv.put(StudentEntry.COLUMN_NAME, "name " + 1);
//        cv.put(StudentEntry.COLUMN_GROUP_ID, 1);
//        cv.put(StudentEntry.COLUMN_PHOTO_ID, R.mipmap.ic_launcher);
//        db.insert(StudentEntry.TABLE_STUDENTS, null, cv);
//        cv = new ContentValues();
//        cv.put(StudentEntry.COLUMN_NAME, "name " + 2);
//        cv.put(StudentEntry.COLUMN_GROUP_ID, 1);
//        cv.put(StudentEntry.COLUMN_PHOTO_ID, R.mipmap.ic_launcher);
//        db.insert(StudentEntry.TABLE_STUDENTS, null, cv);
//        cv = new ContentValues();
//        cv.put(StudentEntry.COLUMN_NAME, "name " + 3);
//        cv.put(StudentEntry.COLUMN_GROUP_ID, 2);
//        cv.put(StudentEntry.COLUMN_PHOTO_ID, R.mipmap.ic_launcher);
//        db.insert(StudentEntry.TABLE_STUDENTS, null, cv);
//        cv = new ContentValues();
//        cv.put(StudentEntry.COLUMN_NAME, "name " + 4);
//        cv.put(StudentEntry.COLUMN_GROUP_ID, 2);
//        cv.put(StudentEntry.COLUMN_PHOTO_ID, R.mipmap.ic_launcher);
//        db.insert(StudentEntry.TABLE_STUDENTS, null, cv);
//        cv = new ContentValues();
//        cv.put(StudentEntry.COLUMN_NAME, "name " + 5);
//        cv.put(StudentEntry.COLUMN_GROUP_ID, 3);
//        cv.put(StudentEntry.COLUMN_PHOTO_ID, R.mipmap.ic_launcher);
//        db.insert(StudentEntry.TABLE_STUDENTS, null, cv);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
