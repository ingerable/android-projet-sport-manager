package com.example.sportmanager.Components.Fragments.profile;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.sportmanager.Components.Fragments.exercice.ExerciceFragment;
import com.example.sportmanager.Components.Fragments.home.HomeFragment;
import com.example.sportmanager.Database.AppDatabase;
import com.example.sportmanager.MyApplication;
import com.example.sportmanager.R;
import com.example.sportmanager.data.Domain.User;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ProfileFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    public ProfileFragment() {
        // Required empty public constructor
    }

    public static ProfileFragment newInstance() {
        ProfileFragment fragment = new ProfileFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        view = this.hydrateProfileView(view, ((MyApplication)getActivity().getApplication()).getConnectedUser());

        Button saveBtn = view.findViewById(R.id.profile_btn_save);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User newUser = ((MyApplication)getActivity().getApplication()).getConnectedUser();
                newUser = hydrateUser(v.getRootView(), newUser);
                AppDatabase.getAppDatabase(getContext()).userDao().update(newUser);
                ((MyApplication)getActivity().getApplication()).setConnectedUser(newUser);

                final FragmentTransaction ft = getFragmentManager().beginTransaction();
                HomeFragment newFragment = new HomeFragment();
                ft.replace(R.id.nav_host_fragment, newFragment);
                ft.addToBackStack(null);
                ft.commit();

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

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, 1);
        }
    }

    private View hydrateProfileView(View view, User user)
    {
        ((EditText)view.findViewById(R.id.profile_editText_login)).setText(user.getLogin());
        ((EditText)view.findViewById(R.id.profile_editText_firstname)).setText(user.getFirstname());
        ((EditText)view.findViewById(R.id.profile_editText_lastname)).setText(user.getLastname());
        ((EditText)view.findViewById(R.id.profile_editText_weight)).setText(Float.toString(user.getWeight()));
        ((EditText)view.findViewById(R.id.profile_editText_age)).setText(Integer.toString(user.getAge()));

        return view;
    }

    private User hydrateUser(View view, User user)
    {
        user.setLogin(((EditText)view.findViewById(R.id.profile_editText_login)).getText().toString());
        user.setFirstname(((EditText)view.findViewById(R.id.profile_editText_firstname)).getText().toString());
        user.setLastname(((EditText)view.findViewById(R.id.profile_editText_lastname)).getText().toString());
        user.setEmail(((EditText)view.findViewById(R.id.profile_editText_email)).getText().toString());
        user.setWeight(Float.parseFloat(((EditText)view.findViewById(R.id.profile_editText_weight)).getText().toString()));
        user.setAge(Integer.parseInt(((EditText)view.findViewById(R.id.profile_editText_age)).getText().toString()));
        String password = ((EditText)view.findViewById(R.id.profile_editText_password)).getText().toString();
        String repeatPassword = ((EditText)view.findViewById(R.id.profile_editText_repeatPassword)).getText().toString();

        if (!password.isEmpty() && !repeatPassword.isEmpty() && password.compareTo(repeatPassword) == 0) {
            MessageDigest md = null;
            try {
                md = MessageDigest.getInstance("SHA-256");
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            byte[] digestPassword = md.digest(password.getBytes());
            user.setHashPass(new String(digestPassword));
        }

        return user;
    }
}
