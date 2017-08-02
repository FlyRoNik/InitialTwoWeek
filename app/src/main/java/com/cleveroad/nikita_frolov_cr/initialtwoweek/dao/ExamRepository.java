package com.cleveroad.nikita_frolov_cr.initialtwoweek.dao;

import android.content.ContentResolver;
import android.net.Uri;

import com.cleveroad.nikita_frolov_cr.initialtwoweek.BuildConfig;
import com.cleveroad.nikita_frolov_cr.initialtwoweek.data.UniversityContract;
import com.cleveroad.nikita_frolov_cr.initialtwoweek.data.UniversityDBHelper;
import com.cleveroad.nikita_frolov_cr.initialtwoweek.data.model.Exam;

import java.util.List;

public class ExamRepository {
    private static final Uri EXAM_UPDATE_URI = Uri.parse("content://"
            + BuildConfig.APPLICATION_ID + "." + UniversityDBHelper.DB_NAME + "/" +
            UniversityContract.ExamEntry.TABLE_EXAMS);

    private ContentResolver mContentResolver;

    public ExamRepository(ContentResolver mContentResolver) {
        this.mContentResolver = mContentResolver;
    }

    public List<Exam> getAllExams(){
        return Exam.all(Exam.class);
    }

    public Exam getExam(long id){
        return Exam.load(Exam.class, id);
    }

    public void editExam(long id, String name){
        Exam exam = Exam.load(Exam.class, id);
        exam.setName(name);
        exam.save();
        mContentResolver.notifyChange(EXAM_UPDATE_URI, null);
    }

    public void addExam(String name){
        Exam exam = new Exam();
        exam.setName(name);
        exam.save();
        mContentResolver.notifyChange(EXAM_UPDATE_URI, null);
    }

    public void deleteExam(long id){
        Exam exam = Exam.load(Exam.class, id);
        exam.delete();
        mContentResolver.notifyChange(EXAM_UPDATE_URI, null);
    }
}
