package com.example.spilly;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

public class AdminProfileFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_admin_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        TextView btnBack = view.findViewById(R.id.btn_back_admin_profile);
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> Navigation.findNavController(v).navigateUp());
        }


        Button btnChangePass = view.findViewById(R.id.ButtonChangePassAdmin);
        if (btnChangePass != null) {
            btnChangePass.setOnClickListener(v -> {

                Navigation.findNavController(v).navigate(R.id.action_adminProfileFragment_to_adminChangePasswordFragment);
            });
        }


        Button btnLogout = view.findViewById(R.id.ButtonLogOutAdmin);
        if (btnLogout != null) {
            btnLogout.setOnClickListener(v -> {
                Toast.makeText(getContext(), "Logged out successfully", Toast.LENGTH_SHORT).show();
                Navigation.findNavController(v).popBackStack();
            });
        }
    }
}