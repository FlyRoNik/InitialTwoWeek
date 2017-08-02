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
        mStudents.clear();
        mStudents.addAll(students);
        notifyDataSetChanged();
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

        private TextView tvStudentName;
        private TextView tvStudentGroup;
        private ImageView ivStudentPhoto;

        public StudentViewHolder(View itemView) {
            super(itemView);
            tvStudentName = itemView.findViewById(R.id.tvStudentName);
            tvStudentGroup = itemView.findViewById(R.id.tvStudentGroup);
            ivStudentPhoto = itemView.findViewById(R.id.ivStudentPhoto);
            itemView.setOnCreateContextMenuListener(this);
        }

        void bindStudent(Student student) {
            tvStudentName.setText(student.getName());
            ivStudentPhoto.setImageResource(Integer.parseInt(student.getIdPhoto().toString()));
            tvStudentGroup.setText(String.valueOf(student.getGroup().getId()));
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
