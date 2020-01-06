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

import com.example.sportmanager.Database.AppDatabase;
import com.example.sportmanager.MyApplication;
import com.example.sportmanager.R;
import com.example.sportmanager.data.Domain.Recurrence;
import com.example.sportmanager.data.Domain.Session;
import com.example.sportmanager.data.Domain.TrainingProgram;
import com.example.sportmanager.data.Domain.TrainingProgramSession;

import java.util.List;

public class SessionCreateOrEditFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    private Session session = null;

    public SessionCreateOrEditFragment() {
        // Required empty public constructor
    }

    public static SessionCreateOrEditFragment newInstance(int sessionId) {
        SessionCreateOrEditFragment fragment = new SessionCreateOrEditFragment();
        Bundle args = new Bundle();
        args.putInt("sessionId", sessionId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            session = AppDatabase.getAppDatabase(getContext()).sessionDao().findById(getArguments().getInt("sessionId"));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_session_create, container, false);

        Button createBtn = view.findViewById(R.id.session_create_btn_create);

        //we are editing
        if (this.session != null) {
            createBtn.setText("SAVE");
            this.hydrateView(session, view);
            createBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    session = hydrateSession(session, view);
                    AppDatabase.getAppDatabase(getContext()).sessionDao().updateSessions(session);

                    final FragmentTransaction ft = getFragmentManager().beginTransaction();
                    SessionFragment newFramgent = SessionFragment.newInstance();
                    ft.replace(R.id.nav_host_fragment, newFramgent);
                    ft.addToBackStack(null);
                    ft.commit();
                }
            });
        } else { // we are creating
            createBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Session session = new Session();
                    session.setRecurrence(new Recurrence());
                    session = hydrateSession(session, view);

                    long sessionId = AppDatabase.getAppDatabase(getContext()).sessionDao().insert(session);

                    TrainingProgramSession trainingProgramSession = new TrainingProgramSession();
                    trainingProgramSession.setSessionId((int)sessionId);

                    TrainingProgram trainingProgram = (TrainingProgram)((Spinner)view.findViewById(R.id.session_create_spinner_trainingProgram)).getSelectedItem();
                    if (trainingProgram != null) {
                        trainingProgramSession.setTrainingProgramId(trainingProgram.getId());
                    }

                    AppDatabase.getAppDatabase(getContext()).trainingProgramSessionDao().insert(trainingProgramSession);

                    final FragmentTransaction ft = getFragmentManager().beginTransaction();
                    SessionFragment newFramgent = SessionFragment.newInstance();
                    ft.replace(R.id.nav_host_fragment, newFramgent);
                    ft.addToBackStack(null);
                    ft.commit();
                }
            });
        }


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
        String order = ((EditText)view.findViewById(R.id.session_create_editText_order)).getText().toString();
        if (!order.isEmpty()) {
            int orderInt = Integer.parseInt(order);
            session.setOrder(orderInt);
        }

        session.setName(name);
        session.setDescription(desc);

        session.getRecurrence().setMonday(((CheckBox)view.findViewById(R.id.session_create_checkBox_monday)).isChecked());
        session.getRecurrence().setTuesday(((CheckBox)view.findViewById(R.id.session_create_checkBox_tuesday)).isChecked());
        session.getRecurrence().setWednesday(((CheckBox)view.findViewById(R.id.session_create_checkBox_wednesday)).isChecked());
        session.getRecurrence().setThursday(((CheckBox)view.findViewById(R.id.session_create_checkBox_thursday)).isChecked());
        session.getRecurrence().setFriday(((CheckBox)view.findViewById(R.id.session_create_checkBox_friday)).isChecked());
        session.getRecurrence().setSaturday(((CheckBox)view.findViewById(R.id.session_create_checkBox_saturday)).isChecked());
        session.getRecurrence().setSunday(((CheckBox)view.findViewById(R.id.session_create_checkBox_sunday)).isChecked());
        TimePicker tp = view.findViewById(R.id.session_create_timePicker);
        session.getRecurrence().setHour(tp.getHour());
        session.getRecurrence().setMinutes(tp.getMinute());

        return session;
    }

    private void hydrateView(Session session, View view)
    {
        ((EditText)view.findViewById(R.id.session_create_editText_name)).setText(session.getName());
        ((EditText)view.findViewById(R.id.session_create_editText_desc)).setText(session.getDescription());
        ((EditText)view.findViewById(R.id.session_create_editText_order)).setText(Integer.toString(session.getOrder()));
        ((CheckBox)view.findViewById(R.id.session_create_checkBox_monday)).setChecked(session.getRecurrence().isMonday());
        ((CheckBox)view.findViewById(R.id.session_create_checkBox_tuesday)).setChecked(session.getRecurrence().isTuesday());
        ((CheckBox)view.findViewById(R.id.session_create_checkBox_wednesday)).setChecked(session.getRecurrence().isWednesday());
        ((CheckBox)view.findViewById(R.id.session_create_checkBox_thursday)).setChecked(session.getRecurrence().isThursday());
        ((CheckBox)view.findViewById(R.id.session_create_checkBox_friday)).setChecked(session.getRecurrence().isFriday());
        ((CheckBox)view.findViewById(R.id.session_create_checkBox_saturday)).setChecked(session.getRecurrence().isSaturday());
        ((CheckBox)view.findViewById(R.id.session_create_checkBox_sunday)).setChecked(session.getRecurrence().isSunday());
        TimePicker tp = view.findViewById(R.id.session_create_timePicker);
        tp.setHour(session.getRecurrence().getHour());
        tp.setMinute(session.getRecurrence().getMinutes());

    }

}
