package com.example.spilly;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.spilly.models.Teacher;
import com.example.spilly.models.User;
import com.example.spilly.utils.Constants;
import com.example.spilly.utils.FirebaseAuthHelper;
import com.example.spilly.utils.FirestoreHelper;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.HashMap;
import java.util.Map;

public class TeacherLoginPage extends Fragment {

    private static final String TAG = "TeacherLoginPage";

    // UI Components
    private EditText etEmail, etPassword;
    private Button btnSignIn, btnSignUp;
    private TextView tvBack;
    private ProgressBar progressBar;

    // Firebase
    private FirebaseAuthHelper authHelper;
    private FirestoreHelper firestoreHelper;

    // Navigation
    private NavController navController;

    public TeacherLoginPage() { }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        authHelper = FirebaseAuthHelper.getInstance(requireContext());
        firestoreHelper = FirestoreHelper.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_teacher_login_page, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);

        // Initialize views
        etEmail = view.findViewById(R.id.ETEmailTeacher);
        etPassword = view.findViewById(R.id.ETPasswordTeacher);
        btnSignIn = view.findViewById(R.id.ButtonSignInTeacher);
        btnSignUp = view.findViewById(R.id.ButtonSignUpTeacher);
        tvBack = view.findViewById(R.id.TVBackTeacher);
        progressBar = view.findViewById(R.id.progressBar);

        // Back button
        tvBack.setOnClickListener(v -> navController.popBackStack());

        // Sign in
        btnSignIn.setOnClickListener(v -> signInWithEmailPassword());

        // Sign up
        btnSignUp.setOnClickListener(v -> signUpWithEmailPassword());
    }

    // ================== SIGN IN ==================
    private void signInWithEmailPassword() {
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            etEmail.setError("Email required");
            return;
        }
        if (TextUtils.isEmpty(password)) {
            etPassword.setError("Password required");
            return;
        }

        showProgressBar();

        authHelper.getAuth().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = authHelper.getCurrentUser();
                        if (user != null) checkUserDocument(user);
                    } else {
                        hideProgressBar();
                        Toast.makeText(requireContext(),
                                "Login failed: " + task.getException().getMessage(),
                                Toast.LENGTH_LONG).show();
                    }
                });
    }

    // ================== SIGN UP ==================
    private void signUpWithEmailPassword() {
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            etEmail.setError("Email required");
            return;
        }
        if (TextUtils.isEmpty(password)) {
            etPassword.setError("Password required");
            return;
        }

        showProgressBar();

        authHelper.getAuth().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = authHelper.getCurrentUser();
                        if (user != null) createUserDocument(user);
                    } else {
                        hideProgressBar();
                        Toast.makeText(requireContext(),
                                "Signup failed: " + task.getException().getMessage(),
                                Toast.LENGTH_LONG).show();
                    }
                });
    }

    // ================== FIRESTORE LOGIC ==================
    private void checkUserDocument(FirebaseUser firebaseUser) {
        String userId = firebaseUser.getUid();

        firestoreHelper.getUserDoc(userId).get()
                .addOnSuccessListener(doc -> {
                    if (doc.exists()) {
                        String userType = doc.getString("userType");
                        if (Constants.USER_TYPE_TEACHER.equals(userType)) {
                            saveAndNavigate(doc);
                        } else {
                            hideProgressBar();
                            authHelper.logout();
                            Toast.makeText(requireContext(),
                                    "Not a teacher account", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        createUserDocument(firebaseUser);
                    }
                })
                .addOnFailureListener(e -> {
                    hideProgressBar();
                    Toast.makeText(requireContext(),
                            "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    private void createUserDocument(FirebaseUser firebaseUser) {
        String userId = firebaseUser.getUid();
        String email = firebaseUser.getEmail();
        String name = email.split("@")[0];

        User user = new User(name, email, Constants.USER_TYPE_TEACHER);

        firestoreHelper.getUserDoc(userId).set(user)
                .addOnSuccessListener(aVoid -> createTeacherDocument(userId, name, email))
                .addOnFailureListener(e -> {
                    hideProgressBar();
                    Toast.makeText(requireContext(),
                            "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    private void createTeacherDocument(String userId, String name, String email) {
        Teacher teacher = new Teacher(name, email, "Teacher");

        firestoreHelper.getTeachersCollection().document(userId).set(teacher)
                .addOnSuccessListener(aVoid -> {
                    authHelper.saveUserInfo(userId, Constants.USER_TYPE_TEACHER, name, email);
                    hideProgressBar();
                    navigateToHome();
                })
                .addOnFailureListener(e -> {
                    hideProgressBar();
                    Toast.makeText(requireContext(),
                            "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    private void saveAndNavigate(DocumentSnapshot doc) {
        String userId = doc.getId();
        String name = doc.getString("name");
        String email = doc.getString("email");
        String userType = doc.getString("userType");

        authHelper.saveUserInfo(userId, userType, name, email);
        hideProgressBar();
        navigateToHome();
    }

    private void navigateToHome() {
        Toast.makeText(requireContext(), "Welcome Teacher!", Toast.LENGTH_SHORT).show();
        try {
            navController.navigate(R.id.teacherAnalyticsPage);
        } catch (Exception e) {
            Toast.makeText(requireContext(),
                    "Please add navigation action",
                    Toast.LENGTH_LONG).show();
        }
    }

    private void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
        btnSignIn.setEnabled(false);
        btnSignUp.setEnabled(false);
    }

    private void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
        btnSignIn.setEnabled(true);
        btnSignUp.setEnabled(true);
    }
}