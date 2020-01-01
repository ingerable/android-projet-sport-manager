package com.example.sportmanager.Components.Fragments.TrainingProgram;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sportmanager.Database.AppDatabase;
import com.example.sportmanager.MyApplication;
import com.example.sportmanager.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListTrainingProgramFragmentInteractionListener}
 * interface.
 */
public class MyTrainingProgramFragment extends TrainingProgramFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_training_program, container, false);

        FloatingActionButton fab = view.findViewById(R.id.trainingProgram_fab_add);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final FragmentTransaction ft = getFragmentManager().beginTransaction();
                TrainingProgramCreateOrEditFragment newFramgent = new TrainingProgramCreateOrEditFragment();
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
            if (this.getmColumnCount() <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, getmColumnCount()));
            }
            AppDatabase db = AppDatabase.getAppDatabase(getContext());

            int connectedUserId = ((MyApplication)getActivity().getApplication()).getConnectedUser().getId();

            recyclerView.setAdapter(new MyTrainingProgramRecyclerViewAdapter(getActivity(), db.TrainingProgramDao().getByUserId(connectedUserId), this));
        }
        return view;
    }

}
