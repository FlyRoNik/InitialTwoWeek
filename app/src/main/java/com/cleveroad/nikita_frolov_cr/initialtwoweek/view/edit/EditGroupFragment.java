package com.cleveroad.nikita_frolov_cr.initialtwoweek.view.edit;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.cleveroad.nikita_frolov_cr.initialtwoweek.App;
import com.cleveroad.nikita_frolov_cr.initialtwoweek.R;
import com.cleveroad.nikita_frolov_cr.initialtwoweek.dao.GroupRepository;
import com.cleveroad.nikita_frolov_cr.initialtwoweek.data.model.Group;

public class EditGroupFragment extends Fragment implements View.OnClickListener {

    private static final String KEY_GROUP_ID = "groupID";

    private EditText etNameGroup;

    private GroupRepository mGroupRepository;

    public static EditGroupFragment newInstance(long id) {
        EditGroupFragment fragment = new EditGroupFragment();
        Bundle args = new Bundle();
        if (id > 0) {
            args.putLong(KEY_GROUP_ID, id);
        }
        fragment.setArguments(args);
        return fragment;
    }

    public static EditGroupFragment newInstance() {
        return newInstance(0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_group, container, false);

        mGroupRepository = new GroupRepository(getContext().getContentResolver(),
                ((App) getActivity().getApplication()).getDaoSession());

        etNameGroup = view.findViewById(R.id.etNameGroup);

        view.findViewById(R.id.bSubmitGroup).setOnClickListener(this);

        if (getArguments().containsKey(KEY_GROUP_ID)) {
            Group group = mGroupRepository.getGroup(getArguments().getLong(KEY_GROUP_ID));
            etNameGroup.setText(group.getName());
        }

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bSubmitGroup:
                chooseMethod();
                break;
            default:
        }
    }

    private void chooseMethod(){
        if (getArguments().containsKey(KEY_GROUP_ID)) {
            Group group = mGroupRepository.getGroup(getArguments().getLong(KEY_GROUP_ID));
            mGroupRepository.editGroup(group.getId(), String.valueOf(etNameGroup.getText()));
        } else {
            mGroupRepository.addGroup(String.valueOf(etNameGroup.getText()));
        }
        getFragmentManager().popBackStackImmediate();
    }
}
