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

import com.example.spilly.models.User;
import com.example.spilly.utils.Constants;
import com.example.spilly.utils.FirebaseAuthHelper;
import com.example.spilly.utils.FirestoreHelper;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.HashMap;
import java.util.Map;

public class StudentLoginPage extends Fragment {

    private static final String TAG = "StudentLoginPage";

    // UI Components
    private EditText etEmail, etPassword;
    private Button btnSignIn, btnSignUp; // <-- added signup button
    private TextView tvBack;
    private ProgressBar progressBar;

    // Firebase
    private FirebaseAuthHelper authHelper;
    private FirestoreHelper firestoreHelper;

    // Navigation
    private NavController navController;

    public StudentLoginPage() { }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        authHelper = FirebaseAuthHelper.getInstance(requireContext());
        firestoreHelper = FirestoreHelper.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_student_login_page, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);

        etEmail = view.findViewById(R.id.ETEmail);
        etPassword = view.findViewById(R.id.ETPassword);
        btnSignIn = view.findViewById(R.id.ButtonSubmit);
        btnSignUp = view.findViewById(R.id.ButtonSignUp); // <-- find signup button
        tvBack = view.findViewById(R.id.TVBack);
        progressBar = view.findViewById(R.id.progressBar);

        tvBack.setOnClickListener(v -> navController.popBackStack());

        // Sign in logic
        btnSignIn.setOnClickListener(v -> signInWithEmailPassword());

        // Sign up logic
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

        // Firebase Auth signup
        authHelper.getAuth().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = authHelper.getCurrentUser();
                        if (user != null) createUserDocument(user); // create Firestore docs
                    } else {
                        hideProgressBar();
                        Toast.makeText(requireContext(),
                                "Signup failed: " + task.getException().getMessage(),
                                Toast.LENGTH_LONG).show();
                    }
                });
    }

    // ================== COMMON FIRESTORE DOC CREATION ==================
    private void checkUserDocument(FirebaseUser firebaseUser) {
        String userId = firebaseUser.getUid();

        firestoreHelper.getUserDoc(userId).get()
                .addOnSuccessListener(doc -> {
                    if (doc.exists()) {
                        String userType = doc.getString("userType");
                        if (Constants.USER_TYPE_STUDENT.equals(userType)) {
                            saveAndNavigate(doc);
                        } else {
                            hideProgressBar();
                            authHelper.logout();
                            Toast.makeText(requireContext(),
                                    "Not a student account", Toast.LENGTH_SHORT).show();
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

        User user = new User(name, email, Constants.USER_TYPE_STUDENT);

        firestoreHelper.getUserDoc(userId).set(user)
                .addOnSuccessListener(aVoid -> createStudentDocument(userId, name, email))
                .addOnFailureListener(e -> {
                    hideProgressBar();
                    Toast.makeText(requireContext(),
                            "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    private void createStudentDocument(String userId, String name, String email) {
        Map<String, Object> studentData = new HashMap<>();
        studentData.put("studentId", userId);
        studentData.put("name", name);
        studentData.put("email", email);
        studentData.put("schoolId", "");
        studentData.put("grade", "");
        studentData.put("section", "");
        studentData.put("parentId", "");

        firestoreHelper.getStudentDoc(userId).set(studentData)
                .addOnSuccessListener(aVoid -> {
                    authHelper.saveUserInfo(userId, Constants.USER_TYPE_STUDENT, name, email);
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
        Toast.makeText(requireContext(), "Success!", Toast.LENGTH_SHORT).show();
        try {
            navController.navigate(R.id.studentHomePage);
        } catch (Exception e) {
            Toast.makeText(requireContext(),
                    "Please add navigation action to studentHomePage",
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
