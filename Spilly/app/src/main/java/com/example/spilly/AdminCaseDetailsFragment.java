package com.example.spilly;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

public class AdminCaseDetailsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_admin_case_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        // Inside AdminCaseDetailsFragment.java onViewCreated
        Spinner spinnerTeacher = view.findViewById(R.id.spinner_assign_teacher);// 1. Create a list of teachers
        String[] teachers = new String[]{"Select teacher...", "Mr. Smith", "Ms. Johnson", "Mr. Lee"};

// 2. Setup the Adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(),
                android.R.layout.simple_spinner_item, teachers);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

// 3. Apply to Spinner
        spinnerTeacher.setAdapter(adapter);

// 4. Handle Assign Button Click
        view.findViewById(R.id.btn_assign_teacher).setOnClickListener(v -> {
            String selected = spinnerTeacher.getSelectedItem().toString();
            if (!selected.equals("Select teacher...")) {
                Toast.makeText(getContext(), "Case assigned to " + selected, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "Please select a teacher first", Toast.LENGTH_SHORT).show();
            }
        });
        // Back Button
        View btnBack = view.findViewById(R.id.btn_back_case_details);
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_adminCaseDetailsFragment_to_adminHomeFragment));
        }

        // Resolve Button
        Button btnResolve = view.findViewById(R.id.btn_mark_resolved);
        if (btnResolve != null) {
            btnResolve.setOnClickListener(v -> {
                Toast.makeText(getContext(), "Case marked as Resolved!", Toast.LENGTH_SHORT).show();
                Navigation.findNavController(v).navigateUp();
            });
        }

        // Contact Button
        View btnContact = view.findViewById(R.id.btn_contact_student);
        if (btnContact != null) {
            btnContact.setOnClickListener(v ->
                    Toast.makeText(getContext(), "Opening chat with student...", Toast.LENGTH_SHORT).show()
            );
        }

        // Inside AdminCaseDetailsFragment.java
            // Find the "Review Case Outcome" button (check your XML for the exact ID)
            View btnReview = view.findViewById(R.id.btn_review_outcome);

            if (btnReview != null) {
                btnReview.setOnClickListener(v -> {
                    Navigation.findNavController(v).navigate(
                            R.id.action_adminCaseDetailsFragment_to_caseOutcomeReview
                    );
                });
            }
        }

    }
