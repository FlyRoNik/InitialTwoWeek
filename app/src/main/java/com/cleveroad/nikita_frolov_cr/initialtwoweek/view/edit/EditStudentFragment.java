package com.cleveroad.nikita_frolov_cr.initialtwoweek.view.edit;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.cleveroad.nikita_frolov_cr.initialtwoweek.App;
import com.cleveroad.nikita_frolov_cr.initialtwoweek.R;
import com.cleveroad.nikita_frolov_cr.initialtwoweek.dao.GroupRepository;
import com.cleveroad.nikita_frolov_cr.initialtwoweek.dao.StudentRepository;
import com.cleveroad.nikita_frolov_cr.initialtwoweek.data.model.Group;
import com.cleveroad.nikita_frolov_cr.initialtwoweek.data.model.Student;

import java.util.ArrayList;
import java.util.stream.IntStream;

public class EditStudentFragment extends Fragment implements View.OnClickListener {

    private static final String KEY_STUDENT_ID = "studentID";

    private StudentRepository mStudentRepository;

    private EditText etNameStudent;
    private Spinner sChooseGroup;

    public static EditStudentFragment newInstance(long id) {
        EditStudentFragment fragment = new EditStudentFragment();
        Bundle args = new Bundle();
        if (id > 0) {
            args.putLong(KEY_STUDENT_ID, id);
        }
        fragment.setArguments(args);
        return fragment;
    }

    public static EditStudentFragment newInstance() {
        return newInstance(0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_edit_student, container, false);

        mStudentRepository = new StudentRepository(getContext().getContentResolver(),
                ((App)getActivity().getApplication()).getDaoSession());
        GroupRepository groupRepository = new GroupRepository(getContext().getContentResolver(),
                ((App)getActivity().getApplication()).getDaoSession());

        etNameStudent = view.findViewById(R.id.etNameStudent);

        view.findViewById(R.id.bSubmitStudent).setOnClickListener(this);

        ArrayAdapter<Group> adapter = new ArrayAdapter<Group>(getContext(),
                R.layout.group_spinner_item, groupRepository.getAllGroups()){
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                Group group = getItem(position);

                if (convertView == null) {
                    convertView = LayoutInflater.from(getContext()).inflate(R.layout.group_spinner_item, parent, false);
                }

                TextView tvNameGroupChoose = convertView.findViewById(R.id.tvNameGroupChoose);

                if(group != null){
                    tvNameGroupChoose.setText(group.getName());
                }

                return convertView;
            }

            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                Group group = getItem(position);

                if (convertView == null) {
                    convertView = LayoutInflater.from(getContext()).inflate(R.layout.group_spinner_item, parent, false);
                }

                TextView tvNameGroupChoose = convertView.findViewById(R.id.tvNameGroupChoose);

                if(group != null){
                    tvNameGroupChoose.setText(group.getName());
                }

                return convertView;
            }
        };
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sChooseGroup = view.findViewById(R.id.sChooseGroup);
        sChooseGroup.setAdapter(adapter);

        if (getArguments().containsKey(KEY_STUDENT_ID)) {
            Student student = mStudentRepository.getStudent(getArguments().getLong(KEY_STUDENT_ID));
            etNameStudent.setText(student.getName());
            ArrayList<Group> students = (ArrayList<Group>) groupRepository.getAllGroups();

            int index = IntStream.range(0, students.size())
                    .filter(i -> student.getGroup().equals(students.get(i)))
                    .findFirst().orElse(-1);
            sChooseGroup.setSelection(index);
        }

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bSubmitStudent:
                chooseMethod();
                break;
            default:
        }
    }

    private void chooseMethod(){
        if (getArguments().containsKey(KEY_STUDENT_ID)) {
            mStudentRepository.editStudent(getArguments().getLong(KEY_STUDENT_ID),
                    etNameStudent.getText().toString(),
                    ((Group)sChooseGroup.getSelectedItem()).getId(),
                    R.mipmap.emma);
        }else {
            mStudentRepository.addStudent(etNameStudent.getText().toString(),
                    ((Group)sChooseGroup.getSelectedItem()).getId(), R.mipmap.emma); //TODO choose photo
        }
        getFragmentManager().popBackStack();
    }




}
