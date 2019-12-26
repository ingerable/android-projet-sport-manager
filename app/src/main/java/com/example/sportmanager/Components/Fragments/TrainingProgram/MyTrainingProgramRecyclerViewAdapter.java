package com.example.sportmanager.Components.Fragments.TrainingProgram;

import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sportmanager.OnListTrainingProgramFragmentInteractionListener;
import com.example.sportmanager.R;
import com.example.sportmanager.data.Domain.TrainingProgram;
import com.example.sportmanager.data.Domain.User;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link TrainingProgram} and makes a call to the
 * specified {@link OnListTrainingProgramFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyTrainingProgramRecyclerViewAdapter extends RecyclerView.Adapter<MyTrainingProgramRecyclerViewAdapter.ViewHolder> {

    private final List<TrainingProgram> mValues;
    private final OnListTrainingProgramFragmentInteractionListener mListener;
    private Context context;


    public MyTrainingProgramRecyclerViewAdapter(Context context, List<TrainingProgram> items, OnListTrainingProgramFragmentInteractionListener listener) {
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
        holder.mItem = mValues.get(position);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    mListener.onTrainingProgramClicked(holder.mItem);
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
