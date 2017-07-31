package com.cleveroad.nikita_frolov_cr.initialtwoweek.dao;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.cleveroad.nikita_frolov_cr.initialtwoweek.BuildConfig;
import com.cleveroad.nikita_frolov_cr.initialtwoweek.data.UniversityContract.StudentEntry;
import com.cleveroad.nikita_frolov_cr.initialtwoweek.data.UniversityDBHelper;

public class StudentContentProvider extends ContentProvider {

    private static final UriMatcher mUriMatcher;
    private static final String AUTHORITY = BuildConfig.APPLICATION_ID + "." +
            UniversityDBHelper.DB_NAME;

    private static final Uri STUDENT_CONTENT_URI = Uri.parse("content://"
            + AUTHORITY + "/" + StudentEntry.TABLE_STUDENTS);

    private static final String STUDENT_CONTENT_TYPE = "vnd.android.cursor.dir/vnd."
            + AUTHORITY + "." + StudentEntry.TABLE_STUDENTS;

    private static final String STUDENT_CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd."
            + AUTHORITY + "." + StudentEntry.TABLE_STUDENTS;

    private static final int URI_STUDENTS = 1;
    private static final int URI_STUDENT_ID = 2;

    static {
        mUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        mUriMatcher.addURI(AUTHORITY, StudentEntry.TABLE_STUDENTS, URI_STUDENTS);
        mUriMatcher.addURI(AUTHORITY, StudentEntry.TABLE_STUDENTS + "/*", URI_STUDENT_ID);
    }

    private UniversityDBHelper mDbHelper;

    @Override
    public boolean onCreate() {
        mDbHelper = UniversityDBHelper.getInstance(getContext());
        return true;
    }

    @Override
    public String getType(@NonNull Uri uri) {
        switch (mUriMatcher.match(uri)) {
            case URI_STUDENTS:
                return STUDENT_CONTENT_TYPE;
            case URI_STUDENT_ID:
                return STUDENT_CONTENT_ITEM_TYPE;
        }
        return null;
    }

    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {
        if (mUriMatcher.match(uri) != URI_STUDENTS)
            throw new IllegalArgumentException("Wrong URI: " + uri);

        SQLiteDatabase mDB = mDbHelper.getWritableDatabase();
        long id = mDB.insert(StudentEntry.TABLE_STUDENTS, null, values);
        Uri resultUri = ContentUris.withAppendedId(STUDENT_CONTENT_URI, id);

        getContext().getContentResolver().notifyChange(resultUri, null);
        return resultUri;
    }

    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        switch (mUriMatcher.match(uri)) {
            case URI_STUDENTS:
                //do nothing
                break;
            case URI_STUDENT_ID:
                String id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    selection = StudentEntry._ID + " = " + id;
                } else {
                    selection = selection + " AND " + StudentEntry._ID + " = " + id;
                }
                break;
            default:
                throw new IllegalArgumentException("Wrong URI: " + uri);
        }
        SQLiteDatabase mDB = mDbHelper.getWritableDatabase();

        Cursor cursor = mDB.query(StudentEntry.TABLE_STUDENTS, projection, selection,
                selectionArgs, null, null, sortOrder);

        cursor.setNotificationUri(getContext().getContentResolver(),
                STUDENT_CONTENT_URI);
        return cursor;
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        switch (mUriMatcher.match(uri)) {
            case URI_STUDENTS:
                //do nothing
                break;
            case URI_STUDENT_ID:
                String id = uri.getLastPathSegment();

                if (TextUtils.isEmpty(selection)) {
                    selection = StudentEntry._ID + " = " + id;
                } else {
                    selection = selection + " AND " + StudentEntry._ID + " = " + id;
                }
                break;
            default:
                throw new IllegalArgumentException("Wrong URI: " + uri);
        }
        SQLiteDatabase mDB = mDbHelper.getWritableDatabase();
        int id = mDB.update(StudentEntry.TABLE_STUDENTS, values, selection, selectionArgs);

        getContext().getContentResolver().notifyChange(uri, null);
        return id;
    }

    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
        switch (mUriMatcher.match(uri)) {
            case URI_STUDENTS:
                //do nothing
                break;
            case URI_STUDENT_ID:
                String id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    selection = StudentEntry._ID + " = " + id;
                } else {
                    selection = selection + " AND " + StudentEntry._ID + " = " + id;
                }
                break;
            default:
                throw new IllegalArgumentException("Wrong URI: " + uri);
        }
        SQLiteDatabase mDB = mDbHelper.getWritableDatabase();
        int cnt = mDB.delete(StudentEntry.TABLE_STUDENTS, selection, selectionArgs);

        getContext().getContentResolver().notifyChange(uri, null);
        return cnt;
    }
}
