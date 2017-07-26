package com.cleveroad.nikita_frolov_cr.initialtwoweek.data;

import android.provider.BaseColumns;

public final class UniversityContract {
    private UniversityContract() {
    }

    public static final class StudentEntry implements BaseColumns {
        public static final String TABLE_STUDENTS = "students";

        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_NAME = "name_student";
        public static final String COLUMN_GROUP_ID = "id_group";

        public static final String TABLE_STUDENTS_CREATE =
                "create table " + TABLE_STUDENTS + "(" +
                        _ID + " integer primary key autoincrement, " +
                        COLUMN_NAME + " text, " +
                        COLUMN_GROUP_ID + " integer, " +
                        "FOREIGN KEY(" + COLUMN_GROUP_ID + ") REFERENCES " +
                        GroupEntry.TABLE_GROUPS + "(" + GroupEntry._ID + ")" +
                        ");";
    }

    public static final class GroupEntry implements BaseColumns {
        public static final String TABLE_GROUPS = "groups";

        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_NAME = "name_group";

        public static final String TABLE_GROUPS_CREATE =
                "create table " + TABLE_GROUPS + "(" +
                        _ID + " integer primary key autoincrement, " +
                        COLUMN_NAME + " text" +
                        ");";
    }

    public static final class ExamEntry implements BaseColumns {
        public static final String TABLE_EXAMS = "exams";

        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_NAME = "name_exam";

        public static final String TABLE_EXAMS_CREATE =
                "create table " + TABLE_EXAMS + "(" +
                        _ID + " integer primary key autoincrement, " +
                        COLUMN_NAME + " text" +
                        ");";
    }

    public static final class RatingEntry implements BaseColumns {
        public static final String TABLE_RATINGS = "ratings";

        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_NAME = "name_rating";
        public static final String COLUMN_EXAM_ID = "id_exam";
        public static final String COLUMN_STUDENT_ID = "id_student";

        public static final String TABLE_RATINGS_CREATE =
                "create table " + TABLE_RATINGS + "(" +
                        _ID + " integer primary key autoincrement, " +
                        COLUMN_NAME + " text, " +
                        COLUMN_EXAM_ID + " integer, " +
                        COLUMN_STUDENT_ID + " integer, " +
                        "FOREIGN KEY(" + COLUMN_EXAM_ID + ") REFERENCES " +
                        ExamEntry.TABLE_EXAMS + "(" + ExamEntry._ID + "), " +
                        "FOREIGN KEY(" + COLUMN_STUDENT_ID + ") REFERENCES " +
                        StudentEntry.TABLE_STUDENTS + "(" + StudentEntry._ID + ")" +
                        ");";
    }

    public static final class GroupExamsEntry implements BaseColumns {
        public static final String TABLE_GROUPS_EXAMS = "groups_exams";

        public final static String _ID = BaseColumns._ID;
        public static final String COLUMN_EXAM_ID = "id_exam";
        public static final String COLUMN_STUDENT_ID = "id_student";


        public static final String TABLE_GROUPS_EXAMS_CREATE =
                "create table " + TABLE_GROUPS_EXAMS + "(" +
                        _ID + " integer primary key autoincrement, " +
                        COLUMN_EXAM_ID + " integer, " +
                        COLUMN_STUDENT_ID + " integer, " +
                        "FOREIGN KEY(" + COLUMN_EXAM_ID + ") REFERENCES " +
                        ExamEntry.TABLE_EXAMS + "(" + ExamEntry._ID + "), " +
                        "FOREIGN KEY(" + COLUMN_STUDENT_ID + ") REFERENCES " +
                        StudentEntry.TABLE_STUDENTS + "(" + StudentEntry._ID + ")" +
                        ");";
    }
}
