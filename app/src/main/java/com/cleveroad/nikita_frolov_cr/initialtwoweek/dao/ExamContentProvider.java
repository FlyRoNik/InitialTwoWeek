package com.cleveroad.nikita_frolov_cr.initialtwoweek.dao;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.activeandroid.ActiveAndroid;
import com.cleveroad.nikita_frolov_cr.initialtwoweek.BuildConfig;
import com.cleveroad.nikita_frolov_cr.initialtwoweek.data.UniversityContract.ExamEntry;
import com.cleveroad.nikita_frolov_cr.initialtwoweek.data.UniversityDBHelper;
import com.cleveroad.nikita_frolov_cr.initialtwoweek.data.model.Exam;

public class ExamContentProvider extends ContentProvider {
    private static final UriMatcher mUriMatcher;
    private static final String AUTHORITY = BuildConfig.APPLICATION_ID + "." +
            UniversityDBHelper.DB_NAME + "2";

    private static final Uri EXAM_CONTENT_URI = Uri.parse("content://"
            + AUTHORITY + "/" + ExamEntry.TABLE_EXAMS);

    private static final String EXAM_CONTENT_TYPE = "vnd.android.cursor.dir/vnd."
            + AUTHORITY + "." + ExamEntry.TABLE_EXAMS;

    private static final String EXAM_CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd."
            + AUTHORITY + "." + ExamEntry.TABLE_EXAMS;

    private static final int URI_EXAMS = 1;
    private static final int URI_EXAM_ID = 2;

    static {
        mUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        mUriMatcher.addURI(AUTHORITY, ExamEntry.TABLE_EXAMS, URI_EXAMS);
        mUriMatcher.addURI(AUTHORITY, ExamEntry.TABLE_EXAMS + "/#", URI_EXAM_ID);
    }

    private UniversityDBHelper mDbHelper;
    private ContentResolver mContentResolver;

    @Override
    public boolean onCreate() {
        //TODO question
        if (getContext() != null) {
            mDbHelper = UniversityDBHelper.getInstance(getContext());
            mContentResolver = getContext().getContentResolver();
        }
        return true;
    }

    @Override
    public String getType(@NonNull Uri uri) {
        switch (mUriMatcher.match(uri)) {
            case URI_EXAMS:
                return EXAM_CONTENT_TYPE;
            case URI_EXAM_ID:
                return EXAM_CONTENT_ITEM_TYPE;
        }
        return null;
    }

    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {
        if (mUriMatcher.match(uri) != URI_EXAMS)
            throw new IllegalArgumentException("Wrong URI: " + uri);

        Exam exam = new Exam();
        exam.setName(values.getAsString(ExamEntry.COLUMN_NAME));
        exam.save();

        Uri resultUri = ContentUris.withAppendedId(EXAM_CONTENT_URI, exam.getId());
        mContentResolver.notifyChange(resultUri, null);
        return resultUri;
    }

    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        switch (mUriMatcher.match(uri)) {
            case URI_EXAMS:
                //do nothing
                break;
            case URI_EXAM_ID:
                String id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    selection = ExamEntry._ID + " = " + id;
                } else {
                    selection = selection + " AND " + ExamEntry._ID + " = " + id;
                }
                break;
            default:
                throw new IllegalArgumentException("Wrong URI: " + uri);
        }

        Cursor cursor = ActiveAndroid.getDatabase().query(ExamEntry.TABLE_EXAMS, projection, selection,
                selectionArgs, null, null, sortOrder);

        cursor.setNotificationUri(mContentResolver,
                EXAM_CONTENT_URI);

        return cursor;
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
