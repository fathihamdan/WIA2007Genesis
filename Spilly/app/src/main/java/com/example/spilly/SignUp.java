package com.example.spilly;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
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

import com.example.spilly.models.Admin;
import com.example.spilly.models.Student;
import com.example.spilly.models.Teacher;
import com.example.spilly.models.User;
import com.example.spilly.utils.Constants;
import com.example.spilly.utils.FirebaseAuthHelper;
import com.example.spilly.utils.FirestoreHelper;
import com.google.firebase.auth.FirebaseUser;

import java.util.HashMap;
import java.util.Map;

public class SignUp extends Fragment {

    private EditText etName, etEmail, etPassword, etConfirmPassword, etSchool;
    private Button btnSignUp;
    private TextView tvBack, tvLogin;
    private Spinner spinnerRole;

    private FirebaseAuthHelper authHelper;
    private FirestoreHelper firestoreHelper;
    private NavController navController;

    public SignUp() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        authHelper = FirebaseAuthHelper.getInstance(requireContext());
        firestoreHelper = FirestoreHelper.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sign_up, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);

        etName = view.findViewById(R.id.ETNameSignUp);
        etEmail = view.findViewById(R.id.ETEmailSignUp);
        etPassword = view.findViewById(R.id.ETPasswordSignUp);
        etConfirmPassword = view.findViewById(R.id.ETConfirmPass);
        etSchool = view.findViewById(R.id.ETSearchSchool);
        btnSignUp = view.findViewById(R.id.ButtonSignUp);
        tvBack = view.findViewById(R.id.TVBackSignUp);
        tvLogin = view.findViewById(R.id.TVLogInSignUp);

        tvBack.setOnClickListener(v -> navController.popBackStack());
        tvLogin.setOnClickListener(v -> navController.popBackStack());
        btnSignUp.setOnClickListener(v -> signUpUser());
    }

    private void signUpUser() {
        String name = etName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String confirmPassword = etConfirmPassword.getText().toString().trim();
        String school = etSchool.getText().toString().trim();

        // Validate
        if (TextUtils.isEmpty(name)) {
            etName.setError("Name required");
            return;
        }

        if (TextUtils.isEmpty(email)) {
            etEmail.setError("Email required");
            return;
        }

        if (TextUtils.isEmpty(password)) {
            etPassword.setError("Password required");
            return;
        }

        if (password.length() < 6) {
            etPassword.setError("Password must be at least 6 characters");
            return;
        }

        if (!password.equals(confirmPassword)) {
            etConfirmPassword.setError("Passwords do not match");
            return;
        }

        if (TextUtils.isEmpty(school)) {
            etSchool.setError("School required");
            return;
        }

        btnSignUp.setEnabled(false);
        btnSignUp.setText("Signing up...");

        // Determine user type from email domain or default to student
        String userType = determineUserType(email);

        authHelper.getAuth().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = authHelper.getCurrentUser();
                        if (user != null) {
                            createUserDocuments(user.getUid(), name, email, school, userType);
                        }
                    } else {
                        btnSignUp.setEnabled(true);
                        btnSignUp.setText("Sign Up");
                        Toast.makeText(requireContext(),
                                "Signup failed: " + task.getException().getMessage(),
                                Toast.LENGTH_LONG).show();
                    }
                });
    }

    private String determineUserType(String email) {
        // Simple logic: if email contains "admin", make admin
        // if contains "teacher", make teacher
        // otherwise, student
        if (email.toLowerCase().contains("admin")) {
            return Constants.USER_TYPE_ADMIN;
        } else if (email.toLowerCase().contains("teacher")) {
            return Constants.USER_TYPE_TEACHER;
        } else {
            return Constants.USER_TYPE_STUDENT;
        }
    }

    private void createUserDocuments(String userId, String name, String email,
                                     String school, String userType) {
        // Create main user document
        User user = new User(name, email, userType);

        firestoreHelper.getUserDoc(userId).set(user)
                .addOnSuccessListener(aVoid -> {
                    // Create role-specific document
                    createRoleDocument(userId, name, email, school, userType);
                })
                .addOnFailureListener(e -> {
                    btnSignUp.setEnabled(true);
                    btnSignUp.setText("Sign Up");
                    Toast.makeText(requireContext(),
                            "Error: " + e.getMessage(),
                            Toast.LENGTH_SHORT).show();
                });
    }

    private void createRoleDocument(String userId, String name, String email,
                                    String school, String userType) {
        if (userType.equals(Constants.USER_TYPE_STUDENT)) {
            // Create student document
            Map<String, Object> studentData = new HashMap<>();
            studentData.put("studentId", userId);
            studentData.put("name", name);
            studentData.put("email", email);
            studentData.put("schoolId", school);
            studentData.put("grade", "");
            studentData.put("section", "");
            studentData.put("parentId", "");

            firestoreHelper.getStudentDoc(userId).set(studentData)
                    .addOnSuccessListener(aVoid -> onSignupSuccess(userId, userType, name, email))
                    .addOnFailureListener(this::onSignupFailure);

        } else if (userType.equals(Constants.USER_TYPE_TEACHER)) {
            // Create teacher document
            Teacher teacher = new Teacher(name, email, "Teacher");

            firestoreHelper.getTeachersCollection().document(userId).set(teacher)
                    .addOnSuccessListener(aVoid -> onSignupSuccess(userId, userType, name, email))
                    .addOnFailureListener(this::onSignupFailure);

        } else if (userType.equals(Constants.USER_TYPE_ADMIN)) {
            // Create admin document
            Admin admin = new Admin(name, email, "Admin");

            firestoreHelper.getAdminsCollection().document(userId).set(admin)
                    .addOnSuccessListener(aVoid -> onSignupSuccess(userId, userType, name, email))
                    .addOnFailureListener(this::onSignupFailure);
        }
    }

    private void onSignupSuccess(String userId, String userType, String name, String email) {
        authHelper.saveUserInfo(userId, userType, name, email);
        Toast.makeText(requireContext(), "Account created successfully!", Toast.LENGTH_SHORT).show();

        // Navigate based on user type
        navigateBasedOnUserType(userType);
    }

    private void onSignupFailure(Exception e) {
        btnSignUp.setEnabled(true);
        btnSignUp.setText("Sign Up");
        Toast.makeText(requireContext(),
                "Error: " + e.getMessage(),
                Toast.LENGTH_SHORT).show();
    }

    private void navigateBasedOnUserType(String userType) {
        try {
            if (userType.equals(Constants.USER_TYPE_STUDENT)) {
                navController.navigate(R.id.studentHomePage);
            } else if (userType.equals(Constants.USER_TYPE_TEACHER)) {
                navController.navigate(R.id.teacherCasesPage);
            } else if (userType.equals(Constants.USER_TYPE_ADMIN)) {
                navController.navigate(R.id.adminHomeFragment);
            }
        } catch (Exception e) {
            Toast.makeText(requireContext(),
                    "Signup successful! Please login.",
                    Toast.LENGTH_LONG).show();
            navController.popBackStack();
        }
    }
}