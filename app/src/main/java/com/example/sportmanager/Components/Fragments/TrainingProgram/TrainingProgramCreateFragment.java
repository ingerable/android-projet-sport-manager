package com.example.sportmanager.Components.Fragments.TrainingProgram;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;

import com.example.sportmanager.Database.AppDatabase;
import com.example.sportmanager.MyApplication;
import com.example.sportmanager.R;
import com.example.sportmanager.data.Domain.TrainingProgram;

public class TrainingProgramCreateFragment extends Fragment {


    private OnFragmentInteractionListener mListener;

    public TrainingProgramCreateFragment() {
        // Required empty public constructor
    }

    public static TrainingProgramCreateFragment newInstance() {
        TrainingProgramCreateFragment fragment = new TrainingProgramCreateFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
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
        View view = inflater.inflate(R.layout.fragment_training_program_create, container, false);

        Button btnCreate = view.findViewById(R.id.trainingProgramCreate_btn_create);

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = ((EditText)(v.getRootView().findViewById(R.id.trainingProgramCreate_editText_name))).getText().toString();
                String description = ((EditText)(v.getRootView().findViewById(R.id.trainingProgramCreate_editText_description))).getText().toString();
                int difficulty = ((RatingBar)(v.getRootView().findViewById(R.id.trainingProgramCreate_ratingBar_difficulty))).getNumStars();
                int connectedUserId = ((MyApplication) getActivity().getApplication()).getConnectedUser().getId();

                TrainingProgram trainingProgram = new TrainingProgram();
                trainingProgram.setDifficulty(difficulty);
                trainingProgram.setDescription(description);
                trainingProgram.setName(name);
                trainingProgram.setCreatorUserId(connectedUserId);


                AppDatabase db = AppDatabase.getAppDatabase(v.getContext());
                db.TrainingProgramDao().insertAll(trainingProgram);
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
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
