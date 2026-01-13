package com.example.spilly;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.spilly.models.BullyingReport;
import com.example.spilly.utils.Constants;
import com.example.spilly.utils.FirebaseAuthHelper;
import com.example.spilly.utils.FirestoreHelper;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class StudentReportPage extends Fragment {

    private TextView tvBack;
    private EditText etDate, etDescription;
    private Spinner spinnerBullyingType;
    private RadioButton rbLow, rbMedium, rbHigh;
    private Button btnContinue;

    private FirebaseAuthHelper authHelper;
    private FirestoreHelper firestoreHelper;
    private NavController navController;

    private Calendar selectedDate;
    private String selectedSeverity = "";

    public StudentReportPage() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        authHelper = FirebaseAuthHelper.getInstance(requireContext());
        firestoreHelper = FirestoreHelper.getInstance();
        selectedDate = Calendar.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_student_report_page, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);

        // Initialize views
        tvBack = view.findViewById(R.id.TVBackStudentReport);
        etDate = view.findViewById(R.id.ETDate);
        etDescription = view.findViewById(R.id.ETMDescWhatHappen);
        spinnerBullyingType = view.findViewById(R.id.spinner_bullying_type);
        rbLow = view.findViewById(R.id.RBLowSevere);
        rbMedium = view.findViewById(R.id.RBMediumSevere);
        rbHigh = view.findViewById(R.id.RBHighSevere);
        btnContinue = view.findViewById(R.id.ButtonContinue);

        // Setup spinner
        String[] bullyingTypes = {
                "Select type...",
                "Cyberbullying",
                "Physical",
                "Verbal",
                "Social",
                "Others"
        };
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_spinner_dropdown_item,
                bullyingTypes
        );
        spinnerBullyingType.setAdapter(adapter);

        // Setup date picker
        etDate.setFocusable(false);
        etDate.setOnClickListener(v -> showDatePicker());

        // Setup severity radio buttons
        rbLow.setOnClickListener(v -> selectedSeverity = "Low");
        rbMedium.setOnClickListener(v -> selectedSeverity = "Medium");
        rbHigh.setOnClickListener(v -> selectedSeverity = "High");

        // Back button
        tvBack.setOnClickListener(v -> navController.popBackStack());

        // Submit button
        btnContinue.setOnClickListener(v -> submitReport());
    }

    private void showDatePicker() {
        int year = selectedDate.get(Calendar.YEAR);
        int month = selectedDate.get(Calendar.MONTH);
        int day = selectedDate.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                requireContext(),
                (view, year1, month1, dayOfMonth) -> {
                    selectedDate.set(year1, month1, dayOfMonth);
                    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
                    etDate.setText(sdf.format(selectedDate.getTime()));
                },
                year, month, day
        );
        datePickerDialog.show();
    }

    private void submitReport() {
        // Get values
        String date = etDate.getText().toString().trim();
        String bullyingType = spinnerBullyingType.getSelectedItem().toString();
        String description = etDescription.getText().toString().trim();

        // Validate
        if (TextUtils.isEmpty(date) || date.equals("mm/dd/yyyy")) {
            Toast.makeText(requireContext(), "Please select a date", Toast.LENGTH_SHORT).show();
            return;
        }

        if (bullyingType.equals("Select type...")) {
            Toast.makeText(requireContext(), "Please select bullying type", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(selectedSeverity)) {
            Toast.makeText(requireContext(), "Please select severity level", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(description) || description.equals("Describe what happened...")) {
            Toast.makeText(requireContext(), "Please describe the incident", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create report
        String studentId = authHelper.getCurrentUserId();
        if (studentId == null) {
            Toast.makeText(requireContext(), "User not logged in", Toast.LENGTH_SHORT).show();
            return;
        }

        // Disable button
        btnContinue.setEnabled(false);
        btnContinue.setText("Submitting...");

        // Create report object
        BullyingReport report = new BullyingReport(
                studentId,
                "", // teacherId will be assigned later
                bullyingType,
                selectedSeverity,
                description,
                Constants.STATUS_PENDING
        );

        // Save to Firestore
        firestoreHelper.getReportsCollection().add(report)
                .addOnSuccessListener(documentReference -> {
                    Toast.makeText(requireContext(),
                            "Report submitted successfully!",
                            Toast.LENGTH_LONG).show();

                    // Navigate back or to view reports
                    navController.popBackStack();
                })
                .addOnFailureListener(e -> {
                    btnContinue.setEnabled(true);
                    btnContinue.setText("Continue");
                    Toast.makeText(requireContext(),
                            "Failed to submit report: " + e.getMessage(),
                            Toast.LENGTH_LONG).show();
                });
    }
}