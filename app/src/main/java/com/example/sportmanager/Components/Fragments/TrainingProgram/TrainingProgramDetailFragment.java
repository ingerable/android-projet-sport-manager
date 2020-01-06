package com.example.sportmanager.Components.Fragments.TrainingProgram;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.RatingBar;
import android.widget.Switch;
import android.widget.TextView;

import com.example.sportmanager.Components.Fragments.session.MySessionRecyclerViewAdapter;
import com.example.sportmanager.Components.Fragments.session.OnListSessionFragmentInteractionListener;
import com.example.sportmanager.Components.Fragments.session.SessionDetailFragment;
import com.example.sportmanager.Components.Fragments.session.SessionFragment;
import com.example.sportmanager.Database.AppDatabase;
import com.example.sportmanager.MyApplication;
import com.example.sportmanager.R;
import com.example.sportmanager.data.Domain.Session;
import com.example.sportmanager.data.Domain.TrainingProgram;
import com.example.sportmanager.data.Domain.User;
import com.example.sportmanager.data.Domain.UserFollowedTrainingsProgram;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class TrainingProgramDetailFragment extends Fragment implements OnListSessionFragmentInteractionListener {

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
            this.sessions = AppDatabase.getAppDatabase(this.getContext()).trainingProgramSessionDao().getByTrainingProgramId(trainingProgramId);
            this.trainingProgram = AppDatabase.getAppDatabase(this.getContext()).TrainingProgramDao().findById(trainingProgramId);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // connected user is the creator of the selected training program, he can edit it

            return showTrainingProgram(inflater, container);
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

    @Override
    public void onSessionClicked(Session session) {
        final FragmentTransaction ft = getFragmentManager().beginTransaction();
        final SessionDetailFragment newFragment = SessionDetailFragment.newInstance(session.getId());
        ft.replace(R.id.nav_host_fragment, newFragment);
        ft.addToBackStack(null);
        ft.commit();
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    private View showTrainingProgram(LayoutInflater inflater, ViewGroup container)
    {
        View view = inflater.inflate(R.layout.fragment_training_program_detail, container, false);

        final User connectedUser = ((MyApplication) getActivity().getApplication()).getConnectedUser();
        final UserFollowedTrainingsProgram userFollowedTrainingsProgram = AppDatabase.getAppDatabase(getContext()).userFollowedTrainingsProgramDao().
                findTrainingProgramFollowedByUserAndTrainingProgram(connectedUser.getId(), trainingProgram.getId());

        //the connected user is the creator of the training program, he can edit/delete it
        if (((MyApplication)getActivity().getApplication()).getConnectedUser().getId() == this.trainingProgram.getCreatorUser().getId()) {

            ((LinearLayout)view.findViewById(R.id.trainingProgram_layout_buttons)).setVisibility(View.VISIBLE);
            Button btnEdit = view.findViewById(R.id.trainingProgram_detail_edit);
            Button btnDelete = view.findViewById(R.id.trainingProgram_detail_delete);

            if (getArguments().get("nonEditableAndDeletable") != null && getArguments().getInt("nonEditableAndDeletable") == 1) {
                btnDelete.setVisibility(View.GONE);
                btnEdit.setVisibility(View.GONE);
            } else {
                btnEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final FragmentTransaction ft = getFragmentManager().beginTransaction();
                        TrainingProgramCreateOrEditFragment newFragment = TrainingProgramCreateOrEditFragment.newInstance(trainingProgram.getId());
                        ft.replace(R.id.nav_host_fragment, newFragment);
                        ft.addToBackStack(null);
                        ft.commit();
                    }
                });

                btnDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AppDatabase.getAppDatabase(getContext()).userFollowedTrainingsProgramDao().delete(userFollowedTrainingsProgram);
                        AppDatabase.getAppDatabase(getContext()).TrainingProgramDao().delete(trainingProgram);
                    }
                });
            }

        }

        Switch switchFollowed = view.findViewById(R.id.trainingProgram_detail_switch_follow);
        final Switch switchFavorite = view.findViewById(R.id.trainingProgram_detail_switch_favorite);

        switchFollowed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int userId = connectedUser.getId();

                //create it
                if (userFollowedTrainingsProgram == null) {
                    UserFollowedTrainingsProgram followedProgram = new UserFollowedTrainingsProgram();
                    followedProgram.setUserId(userId);
                    followedProgram.setFollowedTrainingProgramId(trainingProgram.getId());
                    AppDatabase.getAppDatabase(getContext()).userFollowedTrainingsProgramDao().update(followedProgram);
                } else { //delete it
                    AppDatabase.getAppDatabase(getContext()).userFollowedTrainingsProgramDao().delete(userFollowedTrainingsProgram);
                }
            }
        });

        switchFavorite.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if (switchFavorite.isChecked()) {
                    connectedUser.setFavoriteTrainingProgramId(trainingProgram.getId());
                } else {
                    connectedUser.setFavoriteTrainingProgramId(0);
                }
                AppDatabase.getAppDatabase(getContext()).userDao().update(connectedUser);
            }
        });

        ((TextView)view.findViewById(R.id.trainingProgram_detail_txtView_title)).setText(this.trainingProgram.getName());
        ((TextView)view.findViewById(R.id.trainingProgram_detail_editText_description)).setText(this.trainingProgram.getDescription());

        if (connectedUser.getFavoriteTrainingProgramId() == trainingProgram.getId()) {
            ((Switch)view.findViewById(R.id.trainingProgram_detail_switch_favorite)).setChecked(true);
        }

        if (userFollowedTrainingsProgram != null) {
            ((Switch)view.findViewById(R.id.trainingProgram_detail_switch_follow)).setChecked(true);
        }

        float f = trainingProgram.getDifficulty();
        RatingBar rb = view.findViewById(R.id.trainingProgram_detail_txtView_ratingBar);
        rb.setRating(f);

        RecyclerView recyclerViewSessions = view.findViewById(R.id.trainingProgram_detail_listSessions);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerViewSessions.setLayoutManager(mLayoutManager);
        recyclerViewSessions.setAdapter(new MySessionRecyclerViewAdapter(this.sessions, this));


        return view;
    }

}
