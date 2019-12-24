package com.example.sportmanager.Components.Fragments.TrainingProgram;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sportmanager.Components.Fragments.TrainingProgram.TrainingProgramFragment.OnListFragmentInteractionListener;
import com.example.sportmanager.MainActivity;
import com.example.sportmanager.R;
import com.example.sportmanager.data.Domain.TrainingProgram;
import com.example.sportmanager.data.Domain.User;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link TrainingProgram} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyTrainingProgramRecyclerViewAdapter extends RecyclerView.Adapter<MyTrainingProgramRecyclerViewAdapter.ViewHolder> {

    private final List<TrainingProgram> mValues;
    private final OnListFragmentInteractionListener mListener;
    private Context context;


    public MyTrainingProgramRecyclerViewAdapter(Context context, List<TrainingProgram> items, OnListFragmentInteractionListener listener) {
        this.context = context;
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_trainingprogram, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        TrainingProgram trainingProgram = mValues.get(position);
        User user = mValues.get(position).getCreatorUser();

        holder.mCreatorInfo.setText(user.getFirstname() + user.getLastname());
        holder.mContentView.setText(trainingProgram.getName());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final FragmentTransaction ft = ((MainActivity)context).getSupportFragmentManager().beginTransaction();
                final TrainingProgramDetailFragment newFramgent = new TrainingProgramDetailFragment();
                ((MainActivity)context).getSupportFragmentManager().popBackStackImmediate();
                ft.replace(R.id.nav_host_fragment, newFramgent);
                ft.addToBackStack(null);
                ft.commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mContentView;
        public final TextView mCreatorInfo;
        public TrainingProgram mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mContentView = (TextView) view.findViewById(R.id.content);
            mCreatorInfo = (TextView) view.findViewById(R.id.trainingProgramList_txtView_creatorInfo);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }


}
