package com.example.sportmanager.Components;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.sportmanager.Database.AppDatabase;
import com.example.sportmanager.R;
import com.example.sportmanager.data.Domain.User;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RegisterFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RegisterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegisterFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    private AppDatabase DB;

    public RegisterFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment RegisterFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RegisterFragment newInstance() {
        RegisterFragment fragment = new RegisterFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_register, container, false);

        DB = AppDatabase.getAppDatabase(getContext());

        Button btnLogin = (Button) view.findViewById(R.id.register_button_login);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view)
            {
                goToLogin();
            }
        });

        Button btnRegister = (Button) view.findViewById(R.id.register_button_register);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser(v);
            }
        });
        // Inflate the layout for this fragment
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private void registerUser(View v)
    {
        String login = ((EditText) getView().findViewById(R.id.editText_username)).getText().toString();
        String firstname = ((EditText) getView().findViewById(R.id.editText_firstname)).getText().toString();
        String lastname = ((EditText) getView().findViewById(R.id.editText_lastname)).getText().toString();
        String email = ((EditText) getView().findViewById(R.id.editText_email)).getText().toString();
        String pass = ((EditText) getView().findViewById(R.id.editText_password)).getText().toString();

        User user = DB.userDao().findByLogin(login);

        if (user != null) {

            //hide keyboard and display snackbar
            Toast toast = Toast.makeText(getContext(), "User already existing", Toast.LENGTH_LONG);
            toast.show();
        } else {
            try {
                user = new User();
                user.setLogin(login);
                user.setFirstname(firstname);
                user.setLastname(lastname);
                user.setEmail(email);

                MessageDigest md = MessageDigest.getInstance("SHA-256");
                byte[] digest = md.digest(pass.getBytes());
                user.setHashPass(new String(digest));
                DB.userDao().insertAll(user);
                Toast toast = Toast.makeText(getContext(), "User " + login + " sucessfully inserted", Toast.LENGTH_LONG);
                toast.show();
                goToLogin();
            }catch (NoSuchAlgorithmException e) {
                android.util.Log.d("bug", e.getMessage());
            }
        }
    }

    public void goToLogin()
    {
        getFragmentManager().beginTransaction().replace(R.id.main_linearLayout, LoginFragment.newInstance()).commit();
    }
}
