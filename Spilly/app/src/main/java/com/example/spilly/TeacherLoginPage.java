package com.example.spilly;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TeacherLoginPage#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TeacherLoginPage extends Fragment {

<<<<<<< Updated upstream
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
=======
    // Deklarasi untuk semua elemen UI
    private Spinner spinnerPortal;
    private EditText etEmail, etPassword;
    private Button buttonLogin;
    private TextView buttonBack; // Deklarasi untuk butang back
    private FirebaseAuth mAuth;
    private TextView buttonSignUp; // Deklarasi untuk butang sign up
    private NavController navController;
>>>>>>> Stashed changes

    public TeacherLoginPage() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TeacherLoginPage.
     */
    // TODO: Rename and change types and number of parameters
    public static TeacherLoginPage newInstance(String param1, String param2) {
        TeacherLoginPage fragment = new TeacherLoginPage();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_teacher_login_page, container, false);
    }
<<<<<<< Updated upstream
=======

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
        buttonBack = view.findViewById(R.id.TVBackTeacher); // Dapatkan rujukan butang back
        buttonSignUp = view.findViewById(R.id.TVSignUpTeacher); // Dapatkan rujukan butang sign up

        // ▼▼▼ BAHAGIAN PENTING UNTUK SPINNER ▼▼▼
        setupPortalSpinner();
        // ▲▲▲ BAHAGIAN PENTING UNTUK SPINNER ▲▲▲

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

        // Firebase memerlukan kata laluan sekurang-kurangnya 6 aksara
        if (password.length() < 6) {
            Toast.makeText(getContext(), "Password must be at least 6 characters", Toast.LENGTH_SHORT).show();
            return;
        }

        // Proses pendaftaran dengan Firebase
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(getContext(), "Registration Successful!", Toast.LENGTH_SHORT).show();
                        // Navigasi ke Teacher Profile Page selepas berjaya daftar
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
        // Dapatkan item yang dipilih dari Spinner
        String selectedPortal = spinnerPortal.getSelectedItem().toString();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(getContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Lakukan proses login dengan Firebase
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Jika login berjaya, periksa pilihan portal
                        if ("Teacher".equals(selectedPortal)) {
                            // Navigasi ke halaman utama Guru
                            navController.navigate(R.id.action_teacherLoginPage_to_teacherCasesPage);
                        } else if ("Admin".equals(selectedPortal)) {
                            // Navigasi ke halaman utama Admin
                            navController.navigate(R.id.action_teacherLoginPage_to_adminHomeFragment);
                        }
                    } else {
                        // Jika login gagal, paparkan mesej ralat
                        Toast.makeText(getContext(), "Authentication failed: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }
>>>>>>> Stashed changes
}