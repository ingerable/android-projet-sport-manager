package com.example.sportmanager.Components.Fragments.exercice;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.sportmanager.Database.AppDatabase;
import com.example.sportmanager.R;
import com.example.sportmanager.data.Domain.Exercice;

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
            public void onClick(View view) {

                Exercice exercice = new Exercice();
                exercice.setName(((TextView)view.findViewById(R.id.exercice_create_editText_name)).getText().toString());
                exercice.setDescription(((TextView)view.findViewById(R.id.exercice_create_editText_description)).getText().toString());
                exercice.setSeriesNumber(Integer.parseInt(((TextView)view.findViewById(R.id.exercice_create_editText_series)).getText().toString()));
                exercice.setRepetitionNumber(Integer.parseInt(((TextView)view.findViewById(R.id.exercice_create_editText_repetitions)).getText().toString()));
                exercice.setEffortTimeSeconds(Integer.parseInt(((TextView)view.findViewById(R.id.exercice_create_editText_effortTime)).getText().toString()));
                exercice.setRestTimeSeconds(Integer.parseInt(((TextView)view.findViewById(R.id.exercice_create_editText_restTime)).getText().toString()));
                exercice.setDifficulty(((RatingBar)view.findViewById(R.id.exercice_ratingBar_difficulty)).getRating());

                AppDatabase.getAppDatabase(getContext()).exerciceDao().insertAll(exercice);
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
