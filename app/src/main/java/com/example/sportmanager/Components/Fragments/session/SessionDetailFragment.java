package com.example.sportmanager.Components.Fragments.session;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.sportmanager.Database.AppDatabase;
import com.example.sportmanager.R;
import com.example.sportmanager.data.Domain.Session;

public class SessionDetailFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    private Session session;

    public SessionDetailFragment() {
        // Required empty public constructor
    }

    public static SessionDetailFragment newInstance(int sessionId) {
        SessionDetailFragment fragment = new SessionDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("sessionId", sessionId);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            int sessionId = getArguments().getInt("sessionId");
            this.session = AppDatabase.getAppDatabase(this.getContext()).sessionDao().findById(sessionId);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_session_detail, container, false);
        hydrateCheckbox(view);
        ((TextView)view.findViewById(R.id.session_detail_desc)).setText(this.session.getDescription());
        ((TextView)view.findViewById(R.id.session_detail_name)).setText(this.session.getName());
        ((TextView)view.findViewById(R.id.session_detail_time)).setText(this.session.getRecurrence().getHour() + " : " + this.session.getRecurrence().getMinutes());

        Button editBtn = view.findViewById(R.id.session_detail_btn_edit);
        Button deleteBtn = view.findViewById(R.id.session_detail_btn_delete);

        editBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                final FragmentTransaction ft = getFragmentManager().beginTransaction();
                SessionCreateOrEditFragment newFragment = SessionCreateOrEditFragment.newInstance(session.getId());
                ft.replace(R.id.nav_host_fragment, newFragment);
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppDatabase.getAppDatabase(getContext()).sessionDao().delete(session);

                final FragmentTransaction ft = getFragmentManager().beginTransaction();
                SessionFragment newFragment = SessionFragment.newInstance();
                ft.replace(R.id.nav_host_fragment, newFragment);
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        return view;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private void hydrateCheckbox(View view)
    {
        ((CheckBox)view.findViewById(R.id.session_detail_checkBox_monday)).setChecked(this.session.getRecurrence().isMonday());
        ((CheckBox)view.findViewById(R.id.session_detail_checkBox_tuesday)).setChecked(this.session.getRecurrence().isTuesday());
        ((CheckBox)view.findViewById(R.id.session_detail_checkBox_thursday)).setChecked(this.session.getRecurrence().isThursday());
        ((CheckBox)view.findViewById(R.id.session_detail_checkBox_wednesday)).setChecked(this.session.getRecurrence().isWednesday());
        ((CheckBox)view.findViewById(R.id.session_detail_checkBox_friday)).setChecked(this.session.getRecurrence().isFriday());
        ((CheckBox)view.findViewById(R.id.session_detail_checkBox_saturday)).setChecked(this.session.getRecurrence().isSaturday());
        ((CheckBox)view.findViewById(R.id.session_detail_checkBox_sunday)).setChecked(this.session.getRecurrence().isSunday());

    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
