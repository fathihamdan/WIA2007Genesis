package com.example.spilly;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;   // Import Button
import android.widget.TextView; // Import TextView

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

public class StudentLoginPage extends Fragment {

    public StudentLoginPage() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_student_login_page, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        final NavController navController = Navigation.findNavController(view);


        TextView buttonBack = view.findViewById(R.id.TVBack);

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                navController.popBackStack();
            }
        });

        // --- (Pilihan) Logik untuk Butang "Sign In" dan "Sign Up" ---
        // Anda mungkin ada butang ini juga. Gantikan ID dengan yang betul.
        // Button buttonSignIn = view.findViewById(R.id.ID_BUTANG_SIGN_IN);
        // TextView textSignUp = view.findViewById(R.id.ID_TEKS_SIGN_UP);

        // buttonSignIn.setOnClickListener(new View.OnClickListener() {
        //     @Override
        //     public void onClick(View v) {
        //         // Di sini anda akan letak logik Firebase Authentication
        //         // Selepas berjaya login, navigasi ke dashboard pelajar
        //         // navController.navigate(R.id.action_studentLoginPage_to_studentDashboard);
        //     }
        // });

        // textSignUp.setOnClickListener(new View.OnClickListener() {
        //     @Override
        //     public void onClick(View v) {
        //         // Navigasi ke halaman pendaftaran (Sign Up)
        //         // Pastikan anda sudah cipta action untuk ini dalam navgraph.xml
        //         // navController.navigate(R.id.action_studentLoginPage_to_signUpPage);
        //     }
        // });
    }
}
