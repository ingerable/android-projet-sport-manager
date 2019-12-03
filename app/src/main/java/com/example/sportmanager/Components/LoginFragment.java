package com.example.sportmanager.Components;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.sportmanager.Database.AppDatabase;
import com.example.sportmanager.MainActivity;
import com.example.sportmanager.MyApplication;
import com.example.sportmanager.R;
import com.example.sportmanager.data.Domain.User;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link LoginFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    private AppDatabase DB;

    public LoginFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment LoginFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();
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

        DB = AppDatabase.getAppDatabase(getContext());

        View view = inflater.inflate(R.layout.fragment_login, container, false);
        Button registerButton = (Button) view.findViewById(R.id.login_button_register);
        registerButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view)
            {
                getFragmentManager().beginTransaction().replace(R.id.main_linearLayout, RegisterFragment.newInstance()).commit();
            }
        });

        Button loginButton = (Button) view.findViewById(R.id.login_button_login);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
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
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } //else {
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

    public void loginUser()
    {
        String login = ((TextView) getView().findViewById(R.id.login_editText_username)).getText().toString();
        String pass = ((TextView) getView().findViewById(R.id.login_editText_pasword)).getText().toString();

        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] digest = md.digest(pass.getBytes());
            pass = new String(digest);

            User user = DB.userDao().findByLoginAndHashPass(pass, login);
            if (user != null) {
                //((MyApplication) this.getActivity().getApplication()).setConnectedUser(user);
                MyApplication myapp = (MyApplication) this.getActivity().getApplication();
                Intent intent = new Intent(this.getActivity(), MainActivity.class);
                startActivity(intent);
            } else {
                Toast toast = Toast.makeText(getContext(), "Unknow informations", Toast.LENGTH_LONG);
                toast.show();
            }
        } catch(NoSuchAlgorithmException e) {

        }


    }
}
