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

import com.example.sportmanager.Components.Fragments.step.StepCreateFragment;
import com.example.sportmanager.Database.AppDatabase;
import com.example.sportmanager.R;
import com.example.sportmanager.data.Domain.Exercice;


public class ExerciceDetailFragment extends Fragment {


    private OnFragmentInteractionListener mListener;

    private Exercice exercice;

    public ExerciceDetailFragment() {
    }


    public static ExerciceDetailFragment newInstance(int exerciceId) {
        ExerciceDetailFragment fragment = new ExerciceDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("exerciceId", exerciceId);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            int exerciceId = getArguments().getInt("exerciceId");
            this.exercice = AppDatabase.getAppDatabase(this.getContext()).exerciceDao().findById(exerciceId);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_exercice_detail, container, false);

        Button deleteButton = view.findViewById(R.id.exercice_detail_btn_delete);
        Button editButton = view.findViewById(R.id.exercice_detail_btn_edit);
        Button addStepButton = view.findViewById(R.id.step_create_btn_addStep);

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppDatabase.getAppDatabase(getContext()).exerciceDao().delete(exercice);
                final FragmentTransaction ft = getFragmentManager().beginTransaction();
                final ExerciceFragment newFragment = ExerciceFragment.newInstance();
                ft.replace(R.id.nav_host_fragment, newFragment);
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final FragmentTransaction ft = getFragmentManager().beginTransaction();
                final ExerciceCreateOrEditFragment newFragment = ExerciceCreateOrEditFragment.newInstance(exercice.getId());
                ft.replace(R.id.nav_host_fragment, newFragment);
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        addStepButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                final FragmentTransaction ft = getFragmentManager().beginTransaction();
                final StepCreateFragment newFragment = StepCreateFragment.newInstance(exercice.getId());
                ft.replace(R.id.nav_host_fragment, newFragment);
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        this.hydrateExerciceView(view);
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

    private void hydrateExerciceView(View view)
    {
        ((TextView)view.findViewById(R.id.exercice_detail_txtView_name)).setText(this.exercice.getName());
        ((TextView)view.findViewById(R.id.exercice_detail_txtView_description)).setText(this.exercice.getDescription());
        ((TextView)view.findViewById(R.id.exercice_detail_txtView_series)).setText(Integer.toString(this.exercice.getSeriesNumber()));
        ((TextView)view.findViewById(R.id.exercice_detail_txtView_repetitions)).setText(Integer.toString(this.exercice.getRepetitionNumber()));
        ((TextView)view.findViewById(R.id.exercice_detail_txtView_effortTime)).setText(Integer.toString(this.exercice.getEffortTimeSeconds()));
        ((TextView)view.findViewById(R.id.exercice_detail_txtView_restTime)).setText(Integer.toString(this.exercice.getRestTimeSeconds()));
        ((RatingBar)view.findViewById(R.id.exercice_detail_ratingBar)).setRating(this.exercice.getDifficulty());
    }
}
