package com.example.sportmanager.Components.Fragments.TrainingProgram;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;

import com.example.sportmanager.Database.AppDatabase;
import com.example.sportmanager.MyApplication;
import com.example.sportmanager.R;
import com.example.sportmanager.data.Domain.TrainingProgram;
import com.example.sportmanager.data.Domain.User;

public class TrainingProgramCreateOrEditFragment extends Fragment {


    private OnFragmentInteractionListener mListener;

    private TrainingProgram trainingProgram = null;

    public TrainingProgramCreateOrEditFragment() {
        // Required empty public constructor
    }

    public static TrainingProgramCreateOrEditFragment newInstance(int trainingProgramId) {
        TrainingProgramCreateOrEditFragment fragment = new TrainingProgramCreateOrEditFragment();
        Bundle args = new Bundle();
        args.putInt("trainingProgramId", trainingProgramId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.trainingProgram = AppDatabase.getAppDatabase(getContext()).TrainingProgramDao().findById(getArguments().getInt("trainingProgramId"));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_training_program_create, container, false);

        // edit given trainingProgram
        if (this.trainingProgram != null) {
            return editTrainingProgramView(view);
        } else { // delete training program
            return createTrainingProgramView(view);
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
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private View editTrainingProgramView(View view)
    {
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
                MyTrainingProgramFragment newFramgent = new MyTrainingProgramFragment();
                ft.replace(R.id.nav_host_fragment, newFramgent);
                ft.addToBackStack(null);
                ft.commit();
            }
        });
        return view;
    }

    private View createTrainingProgramView(View view)
    {
        Button btnCreate = view.findViewById(R.id.trainingProgramCreate_btn_create);

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = ((EditText)(v.getRootView().findViewById(R.id.trainingProgramCreate_editText_name))).getText().toString();
                String description = ((EditText)(v.getRootView().findViewById(R.id.trainingProgramCreate_editText_description))).getText().toString();
                int difficulty = ((RatingBar)(v.getRootView().findViewById(R.id.trainingProgramCreate_ratingBar_difficulty))).getNumStars();
                User connectedUser = ((MyApplication) getActivity().getApplication()).getConnectedUser();

                TrainingProgram trainingProgram = new TrainingProgram();
                trainingProgram.setDifficulty(difficulty);
                trainingProgram.setDescription(description);
                trainingProgram.setName(name);
                trainingProgram.setCreatorUser(connectedUser);


                AppDatabase db = AppDatabase.getAppDatabase(v.getContext());
                db.TrainingProgramDao().insertAll(trainingProgram);

                final FragmentTransaction ft = getFragmentManager().beginTransaction();
                MyTrainingProgramFragment newFragment = new MyTrainingProgramFragment();
                ft.replace(R.id.nav_host_fragment, newFragment);
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        return view;
    }
}
