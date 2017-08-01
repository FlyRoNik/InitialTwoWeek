package com.cleveroad.nikita_frolov_cr.initialtwoweek.view.edit;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.cleveroad.nikita_frolov_cr.initialtwoweek.App;
import com.cleveroad.nikita_frolov_cr.initialtwoweek.R;
import com.cleveroad.nikita_frolov_cr.initialtwoweek.data.model.DaoSession;
import com.cleveroad.nikita_frolov_cr.initialtwoweek.data.model.Group;
import com.cleveroad.nikita_frolov_cr.initialtwoweek.data.model.GroupDao;

public class EditGroupFragment extends Fragment implements View.OnClickListener {

    private static final String KEY_GROUP_ID = "groupID";

    private EditText etNameGroup;

    private GroupDao mGroupDao;

    public static EditGroupFragment newInstance(long id) {
        EditGroupFragment fragment = new EditGroupFragment();
        Bundle args = new Bundle();
        args.putLong(KEY_GROUP_ID, id);
        fragment.setArguments(args);
        return fragment;
    }

    public static EditGroupFragment newInstance() {
        EditGroupFragment fragment = new EditGroupFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_group, container, false);

        etNameGroup = view.findViewById(R.id.etNameGroup);

        view.findViewById(R.id.bSubmitGroup).setOnClickListener(this);

        DaoSession daoSession = ((App)getActivity().getApplication()).getDaoSession();
        mGroupDao = daoSession.getGroupDao();

        if (getArguments().containsKey(KEY_GROUP_ID)) {
            Group group = mGroupDao.load(getArguments().getLong(KEY_GROUP_ID));
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
            Group group = new Group();
            group.setId(getArguments().getLong(KEY_GROUP_ID));
            group.setName(String.valueOf(etNameGroup.getText()));
            mGroupDao.update(group);
        } else {
            Group group = new Group();
            group.setName(String.valueOf(etNameGroup.getText()));
            mGroupDao.insert(group);
        }
        getFragmentManager().popBackStackImmediate();
    }
}
