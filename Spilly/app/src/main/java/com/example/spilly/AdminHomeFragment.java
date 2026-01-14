package com.example.spilly;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

public class AdminHomeFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_admin_home_page, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // 1. Find the LinearLayout "boxes" for the rows
        LinearLayout row12456 = view.findViewById(R.id.Cyberbully12456);
        LinearLayout row12455 = view.findViewById(R.id.Verbal12455);

        // 2. Set click listener for the first row (#12456)
        if (row12456 != null) {
            row12456.setOnClickListener(v -> {
                Navigation.findNavController(v).navigate(
                        R.id.action_adminHomeFragment_to_adminCaseDetailsFragment
                );
            });
        }

        // 3. Set click listener for the second row (#12455)
        if (row12455 != null) {
            row12455.setOnClickListener(v -> {
                Navigation.findNavController(v).navigate(
                        R.id.action_adminHomeFragment_to_adminCaseDetailsFragment
                );
            });
        }
    }
}