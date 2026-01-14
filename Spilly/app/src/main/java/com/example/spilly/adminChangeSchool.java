package com.example.spilly;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast; // Added missing Toast import

public class adminChangeSchool extends Fragment {

    public adminChangeSchool() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_admin_change_school, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // 1. Back Button logic
        View btnBack = view.findViewById(R.id.TVBackChangeShoolTeacher);
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> {
                Navigation.findNavController(v).navigateUp();
            });
        }

        // 2. Submit Request button
        View btnSubmit = view.findViewById(R.id.ButtonSubmitRequestTeacher);
        if (btnSubmit != null) {
            btnSubmit.setOnClickListener(v -> {
                Toast.makeText(getContext(), "Request Submitted Successfully", Toast.LENGTH_SHORT).show();
                Navigation.findNavController(v).navigateUp();
            });
        }
    }
}