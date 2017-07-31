package com.cleveroad.nikita_frolov_cr.initialtwoweek.view.edit;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.cleveroad.nikita_frolov_cr.initialtwoweek.R;
import com.cleveroad.nikita_frolov_cr.initialtwoweek.dao.StudentRepository;
import com.cleveroad.nikita_frolov_cr.initialtwoweek.data.model.Student;

public class EditStudentFragment extends Fragment implements View.OnClickListener {

    private StudentRepository mStudentRepository;

    private EditText etName;
    private EditText etGroup;
    private ImageView ivPhotoStudent;

    private FloatingActionButton btnSubmit;

    private static final String KEY_STUDENT_ID = "studentID";

    public static EditStudentFragment newInstance(int id) {
        EditStudentFragment fragment = new EditStudentFragment();
        Bundle args = new Bundle();
        args.putInt(KEY_STUDENT_ID, id);
        fragment.setArguments(args);
        return fragment;
    }

    public static EditStudentFragment newInstance() {
        EditStudentFragment fragment = new EditStudentFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_edit_student, container, false);

        mStudentRepository = new StudentRepository();

        etName = view.findViewById(R.id.etName);
        etGroup = view.findViewById(R.id.etGroup);
        ivPhotoStudent = view.findViewById(R.id.ivPhotoStudent);

        btnSubmit = view.findViewById(R.id.bSubmit);
        btnSubmit.setOnClickListener(this);

        if (getArguments().getInt(KEY_STUDENT_ID, -1) != -1) {
            Student student = mStudentRepository.getStudent(getArguments().getInt(KEY_STUDENT_ID));

            etName.setText(student.getName());
            etGroup.setText(String.valueOf(student.getIdGroup()));
        }

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bSubmit:
                chooseMethod();
                break;
        }
    }

    private void chooseMethod(){
        if (getArguments().getInt(KEY_STUDENT_ID, -1) != -1) {
            mStudentRepository.editStudent(getArguments().getInt(KEY_STUDENT_ID),
                    etName.getText().toString(),
                    etGroup.getText().toString(),
                    R.mipmap.emma);
        }else {
            mStudentRepository.addStudent(etName.getText().toString(),
                    Integer.parseInt(etGroup.getText().toString()), R.mipmap.emma); //TODO choose photo
        }
        getFragmentManager().popBackStack();
    }


}
