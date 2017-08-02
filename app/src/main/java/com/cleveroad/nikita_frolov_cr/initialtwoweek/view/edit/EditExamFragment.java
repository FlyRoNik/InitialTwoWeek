package com.cleveroad.nikita_frolov_cr.initialtwoweek.view.edit;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.cleveroad.nikita_frolov_cr.initialtwoweek.R;
import com.cleveroad.nikita_frolov_cr.initialtwoweek.dao.ExamRepository;
import com.cleveroad.nikita_frolov_cr.initialtwoweek.data.model.Exam;

public class EditExamFragment extends Fragment implements View.OnClickListener{
    private static final String KEY_EXAM_ID = "examID";

    private EditText etNameExam;

    private ExamRepository mExamRepository;

    public static EditExamFragment newInstance(long id) {
        EditExamFragment fragment = new EditExamFragment();
        Bundle args = new Bundle();
        if(id > 0){
            args.putLong(KEY_EXAM_ID, id);
        }
        fragment.setArguments(args);
        return fragment;
    }

    public static EditExamFragment newInstance() {
        return newInstance(0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_exam, container, false);

        mExamRepository = new ExamRepository(getContext().getContentResolver());

        etNameExam = view.findViewById(R.id.etNameExam);

        view.findViewById(R.id.bSubmitExam).setOnClickListener(this);

        if (getArguments().containsKey(KEY_EXAM_ID)) {
            Exam exam = mExamRepository.getExam(getArguments().getLong(KEY_EXAM_ID));
            etNameExam.setText(exam.getName());
        }

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bSubmitExam:
                chooseMethod();
                break;
            default:
        }
    }

    private void chooseMethod(){
        if (getArguments().containsKey(KEY_EXAM_ID)) {
            Exam exam = mExamRepository.getExam(getArguments().getLong(KEY_EXAM_ID));
            mExamRepository.editExam(exam.getId(), String.valueOf(etNameExam.getText()));
        } else {
            mExamRepository.addExam(String.valueOf(etNameExam.getText()));
        }
        getFragmentManager().popBackStackImmediate();
    }
}
