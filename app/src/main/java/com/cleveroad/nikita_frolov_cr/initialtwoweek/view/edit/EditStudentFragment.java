package com.cleveroad.nikita_frolov_cr.initialtwoweek.view.edit;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.cleveroad.nikita_frolov_cr.initialtwoweek.R;

public class EditStudentFragment extends Fragment implements View.OnClickListener {

    private OnFragmentStudentListener mListener;

    private EditText etName;
    private EditText etGroup;

    private Button btnSubmit;

    private static final String KEY_STUDENT_ID = "studentID";
    private static final String KEY_STUDENT_NAME = "studentName";
    private static final String KEY_STUDENT_GROUP = "studentGroup";

    private static final String KEY_CHOOSE = "choose";
    private static final int KEY_CHOOSE_EDIT = 1;
    private static final int KEY_CHOOSE_ADD = 2;


    public static EditStudentFragment newInstance(long id, String name, String group) {
        EditStudentFragment fragment = new EditStudentFragment();
        Bundle args = new Bundle();
        args.putLong(KEY_STUDENT_ID, id);
        args.putString(KEY_STUDENT_NAME, name);
        args.putString(KEY_STUDENT_GROUP, group);
        args.putInt(KEY_CHOOSE, KEY_CHOOSE_EDIT);
        fragment.setArguments(args);
        return fragment;
    }

    public static EditStudentFragment newInstance() {
        EditStudentFragment fragment = new EditStudentFragment();
        Bundle args = new Bundle();
        args.putInt(KEY_CHOOSE, KEY_CHOOSE_ADD);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_edit_student, container, false);

        etName = view.findViewById(R.id.etName);
        etGroup = view.findViewById(R.id.etGroup);

        btnSubmit = view.findViewById(R.id.bSubmit);
        btnSubmit.setOnClickListener(this);

        if (getArguments().getInt(KEY_CHOOSE) == KEY_CHOOSE_EDIT) {
            etName.setText(getArguments().getString(KEY_STUDENT_NAME));
            etGroup.setText(getArguments().getString(KEY_STUDENT_GROUP));
        }

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentStudentListener) {
            mListener = (OnFragmentStudentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentStudentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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
        switch (getArguments().getInt(KEY_CHOOSE)) {
            case KEY_CHOOSE_ADD:
                mListener.addStudent(etName.getText().toString(),
                        etGroup.getText().toString());
                break;
            case KEY_CHOOSE_EDIT:
                mListener.editStudent(getArguments().getLong(KEY_STUDENT_ID, 0),
                        etName.getText().toString(),
                        etGroup.getText().toString());
                break;
        }
    }

    public interface OnFragmentStudentListener {
        void addStudent(String name, String idGroup);

        void editStudent(long id, String name, String idGroup);
    }
}
