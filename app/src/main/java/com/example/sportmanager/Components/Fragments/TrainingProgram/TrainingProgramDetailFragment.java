package com.example.sportmanager.Components.Fragments.TrainingProgram;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.sportmanager.Database.AppDatabase;
import com.example.sportmanager.R;
import com.example.sportmanager.data.Domain.Session;
import com.example.sportmanager.data.Domain.TrainingProgram;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TrainingProgramDetailFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TrainingProgramDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TrainingProgramDetailFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    private List<Session> sessions = new ArrayList<>();

    private TrainingProgram trainingProgram = null;

    public TrainingProgramDetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment TrainingProgramDetailFragment.
     */
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
        // Inflate the layout for this fragment
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
        rb.setNumStars(10);
        rb.setRating(f);

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
