package com.example.spilly;

import android.os.Bundle;import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.firebase.auth.FirebaseAuth;

public class TeacherLoginPage extends Fragment {

    // Deklarasi untuk semua elemen UI
    private Spinner spinnerPortal;
    private EditText etEmail, etPassword;
    private Button buttonLogin;
    private TextView buttonBack;
    private TextView buttonSignUp;
    private FirebaseAuth mAuth;
    private NavController navController;

    public TeacherLoginPage() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate layout untuk fragmen ini
        return inflater.inflate(R.layout.fragment_teacher_login_page, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Inisialisasi Firebase Auth dan NavController
        mAuth = FirebaseAuth.getInstance();
        navController = Navigation.findNavController(view);

        // Dapatkan rujukan dari layout XML
        spinnerPortal = view.findViewById(R.id.spinner_portal);
        etEmail = view.findViewById(R.id.ETEmailTeacher);
        etPassword = view.findViewById(R.id.ETPasswordTeacher);
        buttonLogin = view.findViewById(R.id.ButtonSignInTeacher);
        buttonBack = view.findViewById(R.id.TVBackTeacher);
        buttonSignUp = view.findViewById(R.id.TVSignUpTeacher);

        // ▼▼▼ BAHAGIAN PENTING UNTUK SPINNER ▼▼▼
        setupPortalSpinner();

        // Tetapkan OnClickListener untuk butang Login
        buttonLogin.setOnClickListener(v -> loginUser());

        // Tetapkan OnClickListener untuk butang Back
        buttonBack.setOnClickListener(v -> navController.popBackStack());

        // Tetapkan OnClickListener untuk butang Sign Up (Automatik Daftar)
        buttonSignUp.setOnClickListener(v -> registerUser());
    }

    /**
     * Fungsi ini menyediakan data dan adapter untuk Spinner.
     */
    private void setupPortalSpinner() {
        String[] portals = new String[]{"Teacher", "Admin"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, portals);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPortal.setAdapter(adapter);
    }

    /**
     * Fungsi ini mengendalikan logik pendaftaran automatik.
     */
    private void registerUser() {
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(getContext(), "Please enter email and password to sign up", Toast.LENGTH_SHORT).show();
            return;
        }

        if (password.length() < 6) {
            Toast.makeText(getContext(), "Password must be at least 6 characters", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(getContext(), "Registration Successful!", Toast.LENGTH_SHORT).show();
                        // Navigasi ke Main Container (Halaman yang ada Bottom Nav)
                        navController.navigate(R.id.action_teacherLoginPage_to_teacherMainContainer);
                    } else {
                        Toast.makeText(getContext(), "Registration Failed: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }

    /**
     * Fungsi ini mengendalikan logik apabila butang Login ditekan.
     */
    private void loginUser() {
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String selectedPortal = spinnerPortal.getSelectedItem().toString();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(getContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        if ("Teacher".equals(selectedPortal)) {
                            // Masuk ke Container Utama Guru
                            navController.navigate(R.id.action_teacherLoginPage_to_teacherMainContainer);
                        } else if ("Admin".equals(selectedPortal)) {
                            navController.navigate(R.id.action_teacherLoginPage_to_adminHomeFragment);
                        }
                    } else {
                        Toast.makeText(getContext(), "Authentication failed: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }
}
