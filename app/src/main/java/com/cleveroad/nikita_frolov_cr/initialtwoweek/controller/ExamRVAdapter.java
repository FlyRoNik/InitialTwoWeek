package com.cleveroad.nikita_frolov_cr.initialtwoweek.controller;

import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cleveroad.nikita_frolov_cr.initialtwoweek.R;
import com.cleveroad.nikita_frolov_cr.initialtwoweek.data.model.Exam;

import java.util.ArrayList;
import java.util.List;

public class ExamRVAdapter extends RecyclerView.Adapter<ExamRVAdapter.ExamHolder>{
    private List<Exam> mExams;

    public ExamRVAdapter() {
        this.mExams = new ArrayList<>();
    }

    @Override
    public ExamHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.exam_item, parent, false);
        return new ExamHolder(view);
    }

    @Override
    public void onBindViewHolder(ExamHolder holder, int position) {
        Exam exam = mExams.get(position);
        holder.bindExam(exam);
    }

    public List<Exam> getExams() {
        return new ArrayList<>(mExams);
    }

    public void setExams(List<Exam> exams) {
        mExams.clear();
        mExams.addAll(exams);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mExams.size();
    }

    public Exam getItemSelected(MenuItem item) {
        return mExams.get(item.getOrder());
    }

    class ExamHolder extends RecyclerView.ViewHolder implements
            View.OnCreateContextMenuListener {

        private static final int CM_DELETE = 5;
        private static final int CM_EDIT = 6;

        private TextView tvNameExam;

        public ExamHolder(View itemView) {
            super(itemView);
            tvNameExam = itemView.findViewById(R.id.tvNameExam);
            itemView.setOnCreateContextMenuListener(this);
        }

        void bindExam(Exam exam){
            tvNameExam.setText(exam.getName());
        }

        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            contextMenu.add(0, CM_DELETE, getAdapterPosition(), R.string.delete_record);
            contextMenu.add(0, CM_EDIT, getAdapterPosition(), R.string.edit_record);
        }
    }
}
