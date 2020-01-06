package com.example.sportmanager.Components.Fragments.profile;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.sportmanager.Components.Fragments.home.HomeFragment;
import com.example.sportmanager.Components.Fragments.utils.PictureManager;
import com.example.sportmanager.Database.AppDatabase;
import com.example.sportmanager.MainActivity;
import com.example.sportmanager.MyApplication;
import com.example.sportmanager.R;
import com.example.sportmanager.data.Domain.User;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ProfileFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    private ImageView imageView;

    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;

    private User updatedUser = null;

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
        updatedUser = ((MyApplication) getActivity().getApplication()).getConnectedUser();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        view = this.hydrateProfileView(view, ((MyApplication) getActivity().getApplication()).getConnectedUser());

        Button saveBtn = view.findViewById(R.id.profile_btn_save);
        final ImageButton takePicture = view.findViewById(R.id.profile_btn_picture);
        imageView = view.findViewById(R.id.profile_imageView_picture);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatedUser = hydrateUser(v.getRootView(), updatedUser);
                AppDatabase.getAppDatabase(getContext()).userDao().update(updatedUser);
                ((MyApplication) getActivity().getApplication()).setConnectedUser(updatedUser);
                ((MainActivity) getActivity()).updateUserDisplayedInfo();

                final FragmentTransaction ft = getFragmentManager().beginTransaction();
                HomeFragment newFragment = new HomeFragment();
                ft.replace(R.id.nav_host_fragment, newFragment);
                ft.addToBackStack(null);
                ft.commit();

            }
        });

        takePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,
                        CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);

                if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
                    File photoFile = null;
                    try {
                        int userId = ((MyApplication) getActivity().getApplication()).getConnectedUser().getId();

                        photoFile = PictureManager.createImageFile(getActivity(), Integer.toString(userId));
                    } catch (IOException e) {
                        Log.e("debuggg", e.getMessage());
                    }

                    if (photoFile != null) {
                        Uri photoURI = FileProvider.getUriForFile(getContext(),
                                "com.example.sportmanager.fileprovider",
                                photoFile);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                        startActivityForResult(intent, 1);

                        if (updatedUser.getPathImage() != null) {
                            File previousImage = new File(updatedUser.getPathImage());
                            if (previousImage.exists()) {
                                previousImage.delete();
                            }
                        }
                        updatedUser.setPathImage(photoFile.getAbsolutePath());
                    }
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

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    private View hydrateProfileView(View view, User user) {
        ((EditText) view.findViewById(R.id.profile_editText_login)).setText(user.getLogin());
        ((EditText) view.findViewById(R.id.profile_editText_firstname)).setText(user.getFirstname());
        ((EditText) view.findViewById(R.id.profile_editText_lastname)).setText(user.getLastname());
        ((EditText) view.findViewById(R.id.profile_editText_weight)).setText(Float.toString(user.getWeight()));
        ((EditText) view.findViewById(R.id.profile_editText_age)).setText(Integer.toString(user.getAge()));

        String pathAvatar = updatedUser.getPathImage();
        if (pathAvatar != null) {
            File file = new File(updatedUser.getPathImage());
            if (file.exists()) {
                Bitmap myBitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                ImageView myImage = (ImageView) view.findViewById(R.id.profile_imageView_picture);
                myImage.setImageBitmap(myBitmap);
            }
        }
        return view;
    }

    private User hydrateUser(View view, User user) {
        user.setLogin(((EditText) view.findViewById(R.id.profile_editText_login)).getText().toString());
        user.setFirstname(((EditText) view.findViewById(R.id.profile_editText_firstname)).getText().toString());
        user.setLastname(((EditText) view.findViewById(R.id.profile_editText_lastname)).getText().toString());
        user.setEmail(((EditText) view.findViewById(R.id.profile_editText_email)).getText().toString());
        user.setWeight(Float.parseFloat(((EditText) view.findViewById(R.id.profile_editText_weight)).getText().toString()));
        user.setAge(Integer.parseInt(((EditText) view.findViewById(R.id.profile_editText_age)).getText().toString()));
        String password = ((EditText) view.findViewById(R.id.profile_editText_password)).getText().toString();
        String repeatPassword = ((EditText) view.findViewById(R.id.profile_editText_repeatPassword)).getText().toString();

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
