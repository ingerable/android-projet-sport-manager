package com.example.sportmanager.Components.Fragments.session;

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
import android.widget.Spinner;

import com.example.sportmanager.Database.AppDatabase;
import com.example.sportmanager.R;
import com.example.sportmanager.data.Domain.Session;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class SessionFragment extends Fragment implements OnListSessionFragmentInteractionListener{

    private static final String ARG_COLUMN_COUNT = "column-count";

    private int mColumnCount = 1;

    public SessionFragment() {
    }

    public static SessionFragment newInstance() {
        SessionFragment fragment = new SessionFragment();
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
        View view = inflater.inflate(R.layout.fragment_session_list, container, false);


        FloatingActionButton fab = view.findViewById(R.id.session_fab_add);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final FragmentTransaction ft = getFragmentManager().beginTransaction();
                SessionCreateOrEditFragment newFragment = new SessionCreateOrEditFragment(); //not using static method because here, we do not pass a session id (editing). We want to create a session
                ft.replace(R.id.nav_host_fragment, newFragment);
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        Spinner trainingProgramSpinner = view.findViewById(R.id.session_create_spinner_trainingProgram);


        // Set the adapter
        View recyclerViewList = view.findViewById(R.id.session_listView);
        if (recyclerViewList instanceof RecyclerView) {
            Context context = recyclerViewList.getContext();
            RecyclerView recyclerView = (RecyclerView) recyclerViewList;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            recyclerView.setAdapter(new MySessionRecyclerViewAdapter(AppDatabase.getAppDatabase(getActivity()).sessionDao().getAll(), this));
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
    public void onSessionClicked(Session session) {
        final FragmentTransaction ft = getFragmentManager().beginTransaction();
        final SessionDetailFragment newFragment = SessionDetailFragment.newInstance(session.getId());
        ft.replace(R.id.nav_host_fragment, newFragment);
        ft.addToBackStack(null);
        ft.commit();
    }
}
