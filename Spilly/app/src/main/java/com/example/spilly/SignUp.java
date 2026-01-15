package com.example.spilly;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class SignUp extends Fragment {

    public SignUp() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_up, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Find the "Sign Up" button
        Button btnSignUp = view.findViewById(R.id.ButtonSignUp);
        if (btnSignUp != null) {
            btnSignUp.setOnClickListener(v -> {
                // Navigate to Teacher Login Page
                Navigation.findNavController(v).navigate(R.id.action_signUp_to_teacherLoginPage);
            });
        }

        // Find the "Log In" text
        TextView tvLogIn = view.findViewById(R.id.TVLogInSignUp);
        if (tvLogIn != null) {
            tvLogIn.setOnClickListener(v -> {
                // Also navigate to Teacher Login Page when "Log In" is clicked
                Navigation.findNavController(v).navigate(R.id.action_signUp_to_teacherLoginPage);
            });
        }

        // Find the "Back" text
        TextView tvBack = view.findViewById(R.id.TVBackSignUp);
        if (tvBack != null) {
            tvBack.setOnClickListener(v -> {
                Navigation.findNavController(v).popBackStack();
            });
        }
    }
}