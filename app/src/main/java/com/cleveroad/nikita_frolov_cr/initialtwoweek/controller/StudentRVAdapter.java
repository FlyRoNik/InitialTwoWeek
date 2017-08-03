package com.cleveroad.nikita_frolov_cr.initialtwoweek.controller;

import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cleveroad.nikita_frolov_cr.initialtwoweek.R;
import com.cleveroad.nikita_frolov_cr.initialtwoweek.data.model.Student;

import java.util.ArrayList;
import java.util.List;

public class StudentRVAdapter extends RecyclerView.Adapter<StudentRVAdapter.StudentViewHolder>{

    private List<Student> mStudents;

    public StudentRVAdapter() {
        this.mStudents = new ArrayList<>();
    }

    public List<Student> getStudents() {
        return new ArrayList<>(mStudents);
    }

    public void setStudents(List<Student> students) {
        this.mStudents.clear();
        this.mStudents.addAll(students);
    }

    @Override
    public StudentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.student_item, parent, false);
        return new StudentViewHolder(v);
    }

    @Override
    public void onBindViewHolder(StudentViewHolder holder, int position) {
        Student student = mStudents.get(position);
        holder.bindStudent(student);
    }

    @Override
    public int getItemCount() {
        return mStudents.size();
    }

    public Student getItemSelected(MenuItem item) {
        return mStudents.get(item.getOrder());
    }

    public static class StudentViewHolder extends RecyclerView.ViewHolder implements
            View.OnCreateContextMenuListener {
        private static final int CM_DELETE = 1;
        private static final int CM_EDIT = 2;

        private TextView tvNameStudent;
        private TextView tvGroupStudent;
        private ImageView ivPhoto;

        public StudentViewHolder(View itemView) {
            super(itemView);
            tvNameStudent = itemView.findViewById(R.id.tvNameStudent);
            tvGroupStudent = itemView.findViewById(R.id.tvGroupStudent);
            ivPhoto = itemView.findViewById(R.id.ivPhoto);
            itemView.setOnCreateContextMenuListener(this);
        }

        void bindStudent(Student student){
            tvNameStudent.setText(student.getName());
            tvGroupStudent.setText(String.valueOf(student.getGroup().getId()));
            ivPhoto.setImageResource(Integer.parseInt(student.getIdPhoto().toString()));
        }

        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view,
                                        ContextMenu.ContextMenuInfo contextMenuInfo) {
            //TODO dialog
            contextMenu.add(0, CM_DELETE, getAdapterPosition(), R.string.delete_record);
            contextMenu.add(0, CM_EDIT, getAdapterPosition(), R.string.edit_record);
        }
    }
}
