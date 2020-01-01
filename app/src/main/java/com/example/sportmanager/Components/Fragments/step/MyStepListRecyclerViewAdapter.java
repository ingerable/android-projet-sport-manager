package com.example.sportmanager.Components.Fragments.step;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sportmanager.Components.Fragments.step.StepListFragment.OnListFragmentInteractionListener;
import com.example.sportmanager.R;
import com.example.sportmanager.data.Domain.Step;

import java.util.List;


public class MyStepListRecyclerViewAdapter extends RecyclerView.Adapter<MyStepListRecyclerViewAdapter.ViewHolder> {

    private final List<Step> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MyStepListRecyclerViewAdapter(List<Step> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_steplist, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mContentView.setText(Integer.toString(mValues.get(position).getStepPosition()));

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
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
        public Step mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mContentView = (TextView) view.findViewById(R.id.content);
        }

        @Override
        public String toString() {
            return super.toString() + " position: '" + mContentView.getText() + "'";
        }
    }
}
