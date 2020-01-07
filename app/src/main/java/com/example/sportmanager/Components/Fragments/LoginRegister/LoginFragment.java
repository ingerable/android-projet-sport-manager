package com.example.sportmanager.Components.Fragments.LoginRegister;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
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


public class LoginFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    private AppDatabase DB;

    public LoginFragment() {
        // Required empty public constructor
    }

    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        User rememberedUser = hasRememberedUser();
        if (rememberedUser != null) {
            startAppWithConnectedUser(rememberedUser);
        }
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
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
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
                if ( ((CheckBox)getView().findViewById(R.id.login_checkbox_rememberMe)).isChecked()) {
                    rememberUser(user);
                }
                startAppWithConnectedUser(user);
            } else {
                Toast toast = Toast.makeText(getContext(), "Unknow informations", Toast.LENGTH_LONG);
                toast.show();
            }
        } catch(NoSuchAlgorithmException e) {

        }


    }

    private void rememberUser(User user)
    {
        SharedPreferences sharedPref = getActivity().getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("rememberedUser", user.getId());
        editor.commit();
    }

    private User hasRememberedUser()
    {
        SharedPreferences sharedPref = getActivity().getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        int userId = sharedPref.getInt("rememberedUser", -1);
        return AppDatabase.getAppDatabase(getContext()).userDao().findById(userId);
    }

    private void startAppWithConnectedUser(User user)
    {
        MyApplication myapp = (MyApplication) this.getActivity().getApplication();
        myapp.setConnectedUser(user);
        Intent intent = new Intent(this.getActivity(), MainActivity.class);
        startActivity(intent);
    }
}
