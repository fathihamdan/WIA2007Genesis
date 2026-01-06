package com.example.spilly;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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


        Button btnManageUsers = view.findViewById(R.id.btn_manage_users);
        Button btnManageSchools = view.findViewById(R.id.btn_manage_schools);
        Button btnAllCases = view.findViewById(R.id.btn_all_cases);
        Button btnAnalytics = view.findViewById(R.id.btn_analytics);
        Button btnLogout = view.findViewById(R.id.btn_logout);
        View btnProfile = view.findViewById(R.id.btn_go_to_profile);




        if (btnAllCases != null) {
            btnAllCases.setOnClickListener(v -> {

                Navigation.findNavController(view)
                        .navigate(R.id.action_adminHomeFragment_to_adminAllCasesFragment);
            });
        }


        if (btnManageUsers != null) {
            btnManageUsers.setOnClickListener(v -> {
                Navigation.findNavController(view)
                        .navigate(R.id.action_adminHomeFragment_to_adminManageUsersFragment);
            });
        }


        if (btnManageSchools != null) {
            btnManageSchools.setOnClickListener(v -> {
                Navigation.findNavController(view)
                        .navigate(R.id.action_adminHomeFragment_to_adminManageSchoolsFragment3);
            });
        }


        if (btnProfile != null) {
            btnProfile.setOnClickListener(v -> {
                Navigation.findNavController(view)
                        .navigate(R.id.action_adminHomeFragment_to_adminProfileFragment);
            });
        }


        if (btnLogout != null) {
            btnLogout.setOnClickListener(v -> {
                Toast.makeText(getContext(), "Logged out", Toast.LENGTH_SHORT).show();
                Navigation.findNavController(view).popBackStack();
            });
        }


        if (btnAnalytics != null) {
            btnAnalytics.setOnClickListener(v -> {

                Navigation.findNavController(view)
                        .navigate(R.id.action_adminHomeFragment_to_adminAnalyticsFragment);
            });
        }
    }
}