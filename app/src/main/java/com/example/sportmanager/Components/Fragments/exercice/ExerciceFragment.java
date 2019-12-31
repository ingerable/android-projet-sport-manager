package com.example.sportmanager.Components.Fragments.exercice;

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
import com.example.sportmanager.R;
import com.example.sportmanager.data.Domain.Exercice;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ExerciceFragment extends Fragment implements OnListExerciceFragmentInteractionListener{


    private int mColumnCount = 1;

    public ExerciceFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static ExerciceFragment newInstance() {
        ExerciceFragment fragment = new ExerciceFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_exercice_list, container, false);

        FloatingActionButton fab = view.findViewById(R.id.exercice_fab_add);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final FragmentTransaction ft = getFragmentManager().beginTransaction();
                ExerciceCreateOrEditFragment newFramgent = new ExerciceCreateOrEditFragment();
                ft.replace(R.id.nav_host_fragment, newFramgent);
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        View listView = view.findViewById(R.id.exercice_list);
        if (listView instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) listView;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            recyclerView.setAdapter(new MyExerciceRecyclerViewAdapter(AppDatabase.getAppDatabase(getContext()).exerciceDao().getAll(), this));
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
    }

    @Override
    public void onExerciceClicked(Exercice exercice) {
        final FragmentTransaction ft = getFragmentManager().beginTransaction();
        final ExerciceDetailFragment newFragment = ExerciceDetailFragment.newInstance(exercice.getId());
        ft.replace(R.id.nav_host_fragment, newFragment);
        ft.addToBackStack(null);
        ft.commit();
    }

}
