package com.example.spilly;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.material.card.MaterialCardView;

public class TeacherAnalyticsPage extends Fragment {

    private NavController navController;
    private MaterialCardView cardTotalReports;

    public TeacherAnalyticsPage() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_teacher_analytics_page, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);

        cardTotalReports = view.findViewById(R.id.cardTotalReports);

        cardTotalReports.setOnClickListener(v -> {
            navController.navigate(R.id.studentViewReport);
        });
    }
}
