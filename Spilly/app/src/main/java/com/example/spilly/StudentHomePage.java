package com.example.spilly;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

public class StudentHomePage extends Fragment {

    private Button btnStartReport;
    private NavController navController;

    public StudentHomePage() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_student_home_page, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);

        // Find the button
        btnStartReport = view.findViewById(R.id.ContinueButton);

        // Click to go to report page
        btnStartReport.setOnClickListener(v -> {
            navController.navigate(R.id.studentReportPage);
        });
        // ADD THIS - View Reports button
        Button btnViewReports = view.findViewById(R.id.btnViewReports);
        btnViewReports.setOnClickListener(v -> {
            navController.navigate(R.id.studentViewReport);
        });
    }
}