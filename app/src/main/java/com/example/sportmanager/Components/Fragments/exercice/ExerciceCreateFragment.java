package com.example.sportmanager.Components.Fragments.exercice;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.sportmanager.Components.Fragments.TrainingProgram.TrainingProgramFragment;
import com.example.sportmanager.Database.AppDatabase;
import com.example.sportmanager.R;
import com.example.sportmanager.data.Domain.Exercice;

import org.w3c.dom.Text;

public class ExerciceCreateFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    public ExerciceCreateFragment() {
        // Required empty public constructor
    }

    public static ExerciceCreateFragment newInstance() {
        ExerciceCreateFragment fragment = new ExerciceCreateFragment();
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
        View view = inflater.inflate(R.layout.fragment_exercice_create, container, false);


        Button saveBtn = (Button) view.findViewById(R.id.exercice_create_btn_save);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Exercice exercice = new Exercice();

                exercice.setName(((TextView)v.getRootView().findViewById(R.id.exercice_create_editText_name)).getText().toString());
                exercice.setDescription(((TextView)v.getRootView().findViewById(R.id.exercice_create_editText_description)).getText().toString());
                String seriesNumber = ((TextView)v.getRootView().findViewById(R.id.exercice_create_editText_series)).getText().toString();
                String repetitionNumber = ((TextView)v.getRootView().findViewById(R.id.exercice_create_editText_repetitions)).getText().toString();
                String effortTime =  ((TextView)v.getRootView().findViewById(R.id.exercice_create_editText_effortTime)).getText().toString();
                String restTime = ((TextView)v.getRootView().findViewById(R.id.exercice_create_editText_restTime)).getText().toString();

                if (!seriesNumber.isEmpty()) {
                    exercice.setSeriesNumber(Integer.parseInt(seriesNumber));
                }
                if (!repetitionNumber.isEmpty()) {
                    exercice.setRepetitionNumber(Integer.parseInt(repetitionNumber));
                }
                if (!effortTime.isEmpty()) {
                    exercice.setEffortTimeSeconds(Integer.parseInt(effortTime));
                }
                if (!restTime.isEmpty()) {
                    exercice.setRestTimeSeconds(Integer.parseInt(restTime));
                }

                exercice.setDifficulty(((RatingBar)v.getRootView().findViewById(R.id.exercice_ratingBar_difficulty)).getRating());

                AppDatabase.getAppDatabase(getContext()).exerciceDao().insertAll(exercice);

                final FragmentTransaction ft = getFragmentManager().beginTransaction();
                ExerciceFragment newFragment = ExerciceFragment.newInstance();
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

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
