package com.example.sportmanager.Components.Fragments.session;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;

import com.example.sportmanager.Components.Fragments.TrainingProgram.TrainingProgramFragment;
import com.example.sportmanager.Database.AppDatabase;
import com.example.sportmanager.MyApplication;
import com.example.sportmanager.R;
import com.example.sportmanager.data.Domain.Recurrence;
import com.example.sportmanager.data.Domain.Session;
import com.example.sportmanager.data.Domain.TrainingProgram;

import java.sql.Time;
import java.util.List;

public class SessionCreateFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    public SessionCreateFragment() {
        // Required empty public constructor
    }

    public static SessionCreateFragment newInstance() {
        SessionCreateFragment fragment = new SessionCreateFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_session_create, container, false);

        Button createBtn = view.findViewById(R.id.session_create_btn_create);

        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Session session = new Session();
                session.setRecurrence(new Recurrence());
                session = hydrateSession(session, view);

                AppDatabase.getAppDatabase(getContext()).sessionDao().insertAll(session);

                final FragmentTransaction ft = getFragmentManager().beginTransaction();
                SessionFragment newFramgent = SessionFragment.newInstance();
                ft.replace(R.id.nav_host_fragment, newFramgent);
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        //populate training programs spinner with connected user's training program
        int connectedUserId = ((MyApplication) getActivity().getApplication()).getConnectedUser().getId();
        List<TrainingProgram> trainingProgramList = AppDatabase.getAppDatabase(getContext()).TrainingProgramDao().getByUserId(connectedUserId);
        ArrayAdapter<TrainingProgram> trainingProgramArrayAdapter =
                new ArrayAdapter<>(
                        getContext(),
                        android.R.layout.simple_spinner_item,
                        trainingProgramList
                        );
        ((Spinner)view.findViewById(R.id.session_create_spinner_trainingProgram)).setAdapter(trainingProgramArrayAdapter);
        ((TimePicker)view.findViewById(R.id.session_create_timePicker)).setIs24HourView(true);
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

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    private Session hydrateSession(Session session, View view)
    {
        String name = ((EditText)view.findViewById(R.id.session_create_editText_name)).getText().toString();
        String desc = ((EditText)view.findViewById(R.id.session_create_editText_desc)).getText().toString();
        int order = Integer.parseInt(((EditText)view.findViewById(R.id.session_create_editText_order)).getText().toString());

        session.setName(name);
        session.setDescription(desc);
        session.setOrder(order);

        session.getRecurrence().setMonday(((CheckBox)view.findViewById(R.id.session_create_checkBox_monday)).isChecked());
        session.getRecurrence().setThursday(((CheckBox)view.findViewById(R.id.session_create_checkBox_thursday)).isChecked());
        session.getRecurrence().setWednesday(((CheckBox)view.findViewById(R.id.session_create_checkBox_wednesday)).isChecked());
        session.getRecurrence().setFriday(((CheckBox)view.findViewById(R.id.session_create_checkBox_friday)).isChecked());
        session.getRecurrence().setThursday(((CheckBox)view.findViewById(R.id.session_create_checkBox_thursday)).isChecked());
        session.getRecurrence().setFriday(((CheckBox)view.findViewById(R.id.session_create_checkBox_friday)).isChecked());
        session.getRecurrence().setSaturday(((CheckBox)view.findViewById(R.id.session_create_checkBox_saturday)).isChecked());
        session.getRecurrence().setSunday(((CheckBox)view.findViewById(R.id.session_create_checkBox_sunday)).isChecked());
        TimePicker tp = view.findViewById(R.id.session_create_timePicker);
        session.getRecurrence().setHour(tp.getHour());
        session.getRecurrence().setMinutes(tp.getMinute());

        return session;
    }
}
