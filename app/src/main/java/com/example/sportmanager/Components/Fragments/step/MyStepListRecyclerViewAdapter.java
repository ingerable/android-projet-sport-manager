package com.example.sportmanager.Components.Fragments.step;

import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sportmanager.Components.Fragments.utils.UrImageLoader;
import com.example.sportmanager.R;
import com.example.sportmanager.data.Domain.Step;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;


public class MyStepListRecyclerViewAdapter extends RecyclerView.Adapter<MyStepListRecyclerViewAdapter.ViewHolder> {

    private final List<Step> mValues;
    private final OnListStepFragmentInteractionListener mListener;

    public MyStepListRecyclerViewAdapter(List<Step> items, OnListStepFragmentInteractionListener listener) {
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
        holder.description.setText(mValues.get(position).getDescription());

        String imageUrl = mValues.get(position).getImageUrl();
        if (!imageUrl.isEmpty()) {
            new UrImageLoader(holder.imageView).execute(imageUrl);
        }

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    mListener.onClicked(holder.mItem);
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
        public final TextView description;
        public final ImageView imageView;
        public Step mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mContentView = (TextView) view.findViewById(R.id.content);
            imageView = (ImageView) view.findViewById(R.id.steplist_step_image);
            description = (TextView) view.findViewById(R.id.steplist_step_desc);
        }

        @Override
        public String toString() {
            return super.toString() + " position: '" + mContentView.getText() + "'";
        }
    }
}
