package com.example.spilly;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


public class AdminLoginFragment extends Fragment {

    private EditText etEmail, etPassword;
    private Button btnLogin;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_admin_login_page, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        etEmail = view.findViewById(R.id.ETEmailAdmin);
        etPassword = view.findViewById(R.id.ETPasswordAdmin);
        btnLogin = view.findViewById(R.id.ButtonSignInAdmin);


        btnLogin.setOnClickListener(v -> {

            String email = etEmail.getText().toString();
            String password = etPassword.getText().toString();


            if(email.equals("admin@gmail.com") && password.equals("12345678")) {
                Toast.makeText(getContext(), "Login Successful", Toast.LENGTH_SHORT).show();

                // 1. Create an Intent to switch from MainActivity to AdminActivity
                Intent intent = new Intent(getActivity(), AdminActivity.class);

                // 2. Start the new Activity
                startActivity(intent);

                // 3. Optional: Finish the current activity so the user can't "Back" into the login screen
                if (getActivity() != null) {
                    getActivity().finish();
                }
            } else {
                Toast.makeText(getContext(), "Invalid Admin Credentials", Toast.LENGTH_SHORT).show();
            }
        });
    }
}