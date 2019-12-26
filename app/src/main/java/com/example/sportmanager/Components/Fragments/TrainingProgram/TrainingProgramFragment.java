package com.example.sportmanager.Components.Fragments.TrainingProgram;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sportmanager.Database.AppDatabase;
import com.example.sportmanager.OnListTrainingProgramFragmentInteractionListener;
import com.example.sportmanager.R;
import com.example.sportmanager.data.Domain.TrainingProgram;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListTrainingProgramFragmentInteractionListener}
 * interface.
 */
public class TrainingProgramFragment extends Fragment implements OnListTrainingProgramFragmentInteractionListener {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListTrainingProgramFragmentInteractionListener mListener;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public TrainingProgramFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static TrainingProgramFragment newInstance(int columnCount) {
        TrainingProgramFragment fragment = new TrainingProgramFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_training_program, container, false);

        FloatingActionButton fab = view.findViewById(R.id.trainingProgram_fab_add);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final FragmentTransaction ft = getFragmentManager().beginTransaction();
                TrainingProgramCreateFragment newFramgent = TrainingProgramCreateFragment.newInstance();
                ft.replace(R.id.nav_host_fragment, newFramgent);
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        // Set the adapter

        View recyclerViewList = view.findViewById(R.id.trainingProgram_listView);
        if (recyclerViewList instanceof RecyclerView) {
            Context context = recyclerViewList.getContext();
            RecyclerView recyclerView = (RecyclerView) recyclerViewList;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            AppDatabase db = AppDatabase.getAppDatabase(getContext());


            recyclerView.setAdapter(new MyTrainingProgramRecyclerViewAdapter(getActivity(), db.TrainingProgramDao().getAll(), this));
        }
        return view;
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


    public void onTrainingProgramClicked(TrainingProgram trainingProgram) {
                final FragmentTransaction ft = getFragmentManager().beginTransaction();
                final TrainingProgramDetailFragment newFramgent = TrainingProgramDetailFragment.newInstance(trainingProgram.getId());
                ft.replace(R.id.nav_host_fragment, newFramgent);
                ft.addToBackStack(null);
                ft.commit();
    }
}
