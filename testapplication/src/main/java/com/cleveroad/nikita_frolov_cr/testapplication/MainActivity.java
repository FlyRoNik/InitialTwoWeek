package com.cleveroad.nikita_frolov_cr.testapplication;

import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private static final Uri STUDENT_URI = Uri.parse("content://com.cleveroad.nikita_frolov_cr.initialtwoweek.universitydb/students");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Cursor c = getContentResolver().query(STUDENT_URI,
                null, null, null, null, null);
    }
}
