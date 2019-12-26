package com.example.sportmanager.Components.Fragments.TrainingProgram;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.sportmanager.Database.AppDatabase;
import com.example.sportmanager.MyApplication;
import com.example.sportmanager.R;
import com.example.sportmanager.data.Domain.Session;
import com.example.sportmanager.data.Domain.TrainingProgram;
import com.example.sportmanager.data.Domain.User;

import java.util.ArrayList;
import java.util.List;

public class TrainingProgramDetailFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    private List<Session> sessions = new ArrayList<>();

    private TrainingProgram trainingProgram = null;

    public TrainingProgramDetailFragment() {
        // Required empty public constructor
    }

    public static TrainingProgramDetailFragment newInstance(int trainingProgramId) {
        TrainingProgramDetailFragment fragment = new TrainingProgramDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("trainingProgramId", trainingProgramId);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            int trainingProgramId = getArguments().getInt("trainingProgramId");
            this.sessions = AppDatabase.getAppDatabase(this.getContext()).sessionDao().getByTrainingProgramId(trainingProgramId);
            this.trainingProgram = AppDatabase.getAppDatabase(this.getContext()).TrainingProgramDao().findById(trainingProgramId);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // connected user is the creator of the selected training program, he can edit it
        if ( ((MyApplication)getActivity().getApplication()).getConnectedUser().getId() == this.trainingProgram.getCreatorUser().getId()) {
            return editTrainingProgram(inflater, container);
        } else {
            return showTrainingProgram(inflater, container);
        }

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

    private View showTrainingProgram(LayoutInflater inflater, ViewGroup container)
    {
        View view = inflater.inflate(R.layout.fragment_training_program_detail, container, false);

        final Button btnPopup = view.findViewById(R.id.trainingProgram_detail_btn_sessions);

        btnPopup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PopupMenu popupMenuSessions = new PopupMenu(v.getContext(), btnPopup);
                for (Session s : sessions) {
                    popupMenuSessions.getMenu().add(1, s.getId(), s.getOrder(), s.getName());
                }
                popupMenuSessions.show();
            }
        });

        ((TextView)view.findViewById(R.id.trainingProgram_detail_txtView_title)).setText(this.trainingProgram.getName());
        ((TextView)view.findViewById(R.id.trainingProgram_detail_editText_description)).setText(this.trainingProgram.getDescription());
        float f = trainingProgram.getDifficulty();
        RatingBar rb = view.findViewById(R.id.trainingProgram_detail_txtView_ratingBar);
        rb.setRating(f);

        return view;
    }

    private View editTrainingProgram(LayoutInflater inflater, ViewGroup container)
    {
        View view = inflater.inflate(R.layout.fragment_training_program_create, container, false);

        ((EditText)view.findViewById(R.id.trainingProgramCreate_editText_name)).setText(this.trainingProgram.getName());
        ((EditText)view.findViewById(R.id.trainingProgramCreate_editText_description)).setText(this.trainingProgram.getDescription());
        ((RatingBar)view.findViewById(R.id.trainingProgramCreate_ratingBar_difficulty)).setRating(this.trainingProgram.getDifficulty());
        Button btn = view.findViewById(R.id.trainingProgramCreate_btn_create);
        btn.setText("Sauvegarder");

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = ((EditText)(v.getRootView().findViewById(R.id.trainingProgramCreate_editText_name))).getText().toString();
                String description = ((EditText)(v.getRootView().findViewById(R.id.trainingProgramCreate_editText_description))).getText().toString();
                float difficulty = ((RatingBar)(v.getRootView().findViewById(R.id.trainingProgramCreate_ratingBar_difficulty))).getRating();

                trainingProgram.setDifficulty(difficulty);
                trainingProgram.setDescription(description);
                trainingProgram.setName(name);

                AppDatabase.getAppDatabase(getContext()).TrainingProgramDao().updateTrainingPrograms(trainingProgram);

                final FragmentTransaction ft = getFragmentManager().beginTransaction();
                TrainingProgramFragment newFramgent = new TrainingProgramFragment();
                ft.replace(R.id.nav_host_fragment, newFramgent);
                ft.addToBackStack(null);
                ft.commit();
            }
        });
        return view;
    }


}
