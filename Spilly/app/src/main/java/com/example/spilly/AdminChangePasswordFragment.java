package com.example.spilly;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

public class AdminChangePasswordFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_admin_change_password, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        TextView btnBack = view.findViewById(R.id.btn_back_admin_change_pass);
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> Navigation.findNavController(v).navigateUp());
        }


        EditText etNewPass = view.findViewById(R.id.et_new_pass_admin);
        EditText etConfirmPass = view.findViewById(R.id.et_confirm_pass_admin);


        Button btnUpdate = view.findViewById(R.id.btn_update_pass_admin);
        if (btnUpdate != null) {
            btnUpdate.setOnClickListener(v -> {
                String newPass = etNewPass.getText().toString();
                String confirmPass = etConfirmPass.getText().toString();

                if (newPass.isEmpty() || confirmPass.isEmpty()) {
                    Toast.makeText(getContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
                } else if (!newPass.equals(confirmPass)) {
                    Toast.makeText(getContext(), "Passwords do not match", Toast.LENGTH_SHORT).show();
                } else {

                    Toast.makeText(getContext(), "Password Updated Successfully!", Toast.LENGTH_SHORT).show();
                    Navigation.findNavController(v).navigateUp();
                }
            });
        }
    }
}