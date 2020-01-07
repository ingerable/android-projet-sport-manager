package com.example.sportmanager.Components.Fragments.session;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sportmanager.Components.Fragments.exercice.ExerciceDetailFragment;
import com.example.sportmanager.Components.Fragments.exercice.MyExerciceRecyclerViewAdapter;
import com.example.sportmanager.Components.Fragments.exercice.OnListExerciceFragmentInteractionListener;
import com.example.sportmanager.Database.AppDatabase;
import com.example.sportmanager.R;
import com.example.sportmanager.data.Domain.Exercice;
import com.example.sportmanager.data.Domain.Session;
import com.example.sportmanager.data.Domain.SessionExercice;

import java.util.List;

public class SessionDetailFragment extends Fragment implements OnListExerciceFragmentInteractionListener {

    private OnFragmentInteractionListener mListener;

    private Session session;

    private MyExerciceRecyclerViewAdapter exerciceRecycleAdapter;

    private List<Exercice> exerciceListRecyclerView;

    public SessionDetailFragment() {
        // Required empty public constructor
    }

    public static SessionDetailFragment newInstance(int sessionId) {
        SessionDetailFragment fragment = new SessionDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("sessionId", sessionId);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            int sessionId = getArguments().getInt("sessionId");
            this.session = AppDatabase.getAppDatabase(this.getContext()).sessionDao().findById(sessionId);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_session_detail, container, false);
        hydrateCheckbox(view);
        ((TextView)view.findViewById(R.id.session_detail_desc)).setText(this.session.getDescription());
        ((TextView)view.findViewById(R.id.session_detail_name)).setText(this.session.getName());
        ((TextView)view.findViewById(R.id.session_detail_time)).setText(this.session.getRecurrence().getHour() + " : " + this.session.getRecurrence().getMinutes());


        //spinner exercices
        List<Exercice> exerciceList = AppDatabase.getAppDatabase(getContext()).exerciceDao().getAll();
        ArrayAdapter<Exercice> exerciceArrayAdapter =
                new ArrayAdapter<>(
                        getContext(),
                        android.R.layout.simple_spinner_item,
                        exerciceList
                );
        ((Spinner)view.findViewById(R.id.session_detail_spinner_exercices)).setAdapter(exerciceArrayAdapter);

        //recyclerview of exercices
        RecyclerView exerciceRecyclerView = view.findViewById(R.id.session_detail_exercicesList);
        exerciceListRecyclerView = AppDatabase.getAppDatabase(getContext()).sessionExerciceDao().findExercicesBySessionId(session.getId());
        exerciceRecycleAdapter = new MyExerciceRecyclerViewAdapter(exerciceListRecyclerView, this);
        exerciceRecyclerView.setAdapter(exerciceRecycleAdapter);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        exerciceRecyclerView.setLayoutManager(mLayoutManager);

        //button listeners
        Button editBtn = view.findViewById(R.id.session_detail_btn_edit);
        Button deleteBtn = view.findViewById(R.id.session_detail_btn_delete);
        Button addExercice = view.findViewById(R.id.session_detail_btn_addExercice);

        editBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                final FragmentTransaction ft = getFragmentManager().beginTransaction();
                SessionCreateOrEditFragment newFragment = SessionCreateOrEditFragment.newInstance(session.getId());
                ft.replace(R.id.nav_host_fragment, newFragment);
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppDatabase.getAppDatabase(getContext()).sessionDao().delete(session);

                final FragmentTransaction ft = getFragmentManager().beginTransaction();
                SessionFragment newFragment = SessionFragment.newInstance();
                ft.replace(R.id.nav_host_fragment, newFragment);
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        addExercice.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Exercice selectedExercice = (Exercice) ((Spinner)view.findViewById(R.id.session_detail_spinner_exercices)).getSelectedItem();
                if (AppDatabase.getAppDatabase(getContext()).sessionExerciceDao().findBySessionAndExerciceId(session.getId(), selectedExercice.getId()) == null) {
                    SessionExercice sessionExercice = new SessionExercice();
                    sessionExercice.setExerciceId(selectedExercice.getId());
                    sessionExercice.setSessionId(session.getId());

                    AppDatabase.getAppDatabase(getContext()).sessionExerciceDao().insert(sessionExercice);
                    //update list
                    exerciceListRecyclerView.clear();
                    exerciceListRecyclerView.addAll(AppDatabase.getAppDatabase(getContext()).sessionExerciceDao().findExercicesBySessionId(session.getId()));
                    exerciceRecycleAdapter.notifyDataSetChanged();
                } else {
                    Toast toast = Toast.makeText(getContext(), "Exercice already in the list", Toast.LENGTH_LONG);
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

    private void hydrateCheckbox(View view)
    {
        ((CheckBox)view.findViewById(R.id.session_detail_checkBox_monday)).setChecked(this.session.getRecurrence().isMonday());
        ((CheckBox)view.findViewById(R.id.session_detail_checkBox_tuesday)).setChecked(this.session.getRecurrence().isTuesday());
        ((CheckBox)view.findViewById(R.id.session_detail_checkBox_thursday)).setChecked(this.session.getRecurrence().isThursday());
        ((CheckBox)view.findViewById(R.id.session_detail_checkBox_wednesday)).setChecked(this.session.getRecurrence().isWednesday());
        ((CheckBox)view.findViewById(R.id.session_detail_checkBox_friday)).setChecked(this.session.getRecurrence().isFriday());
        ((CheckBox)view.findViewById(R.id.session_detail_checkBox_saturday)).setChecked(this.session.getRecurrence().isSaturday());
        ((CheckBox)view.findViewById(R.id.session_detail_checkBox_sunday)).setChecked(this.session.getRecurrence().isSunday());

    }

    @Override
    public void onExerciceClicked(Exercice exercice) {
        final FragmentTransaction ft = getFragmentManager().beginTransaction();
        final ExerciceDetailFragment newFragment = ExerciceDetailFragment.newInstance(exercice.getId());
        ft.replace(R.id.nav_host_fragment, newFragment);
        ft.addToBackStack(null);
        ft.commit();
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
