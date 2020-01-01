package com.example.sportmanager.Components.Fragments.step;

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
import android.widget.Toast;

import com.example.sportmanager.Components.Fragments.exercice.ExerciceCreateOrEditFragment;
import com.example.sportmanager.Components.Fragments.exercice.ExerciceDetailFragment;
import com.example.sportmanager.Database.AppDatabase;
import com.example.sportmanager.R;
import com.example.sportmanager.data.Domain.Step;

public class StepCreateFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    private int exerciceId;

    public StepCreateFragment() {
        // Required empty public constructor
    }

    public static StepCreateFragment newInstance(int exerciceId) {
        StepCreateFragment fragment = new StepCreateFragment();
        Bundle args = new Bundle();
        args.putInt("exerciceId", exerciceId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            exerciceId = getArguments().getInt("exerciceId");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_step_create, container, false);
        Button saveBtn = view.findViewById(R.id.step_create_btn_save);

        saveBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Step step = new Step();
                step = hydrateStep(step, v.getRootView());
                step.setExerciceId(exerciceId);

                if (AppDatabase.getAppDatabase(getContext()).stepDao().findByExerciceIdAndPosition(exerciceId, step.getStepPosition()) == null) {
                    AppDatabase.getAppDatabase(getContext()).stepDao().insertAll(step);

                    final FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ExerciceDetailFragment newFragment = ExerciceDetailFragment.newInstance(exerciceId);
                    ft.replace(R.id.nav_host_fragment, newFragment);
                    ft.addToBackStack(null);
                    ft.commit();
                } else {
                    Toast toast = Toast.makeText(getContext(), "Step with same exercice and position already exist", Toast.LENGTH_LONG);
                    toast.show();
                }



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

    private Step hydrateStep(Step step, View view)
    {
        step.setDescription(((EditText)view.findViewById(R.id.step_create_editText_description)).getText().toString());
        String stepPositionString = ((EditText)view.findViewById(R.id.step_create_editText_position)).getText().toString();
        if (!stepPositionString.isEmpty()) {
            step.setStepPosition(Integer.parseInt(stepPositionString));
        }
        step.setImageUrl(((EditText)view.findViewById(R.id.step_create_editText_imageURL)).getText().toString());
        step.setVideoUrl(((EditText)view.findViewById(R.id.step_create_editText_videoUrl)).getText().toString());

        return step;
    }
}
