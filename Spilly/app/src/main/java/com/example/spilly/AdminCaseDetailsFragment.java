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

public class AdminCaseDetailsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_admin_case_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        view.findViewById(R.id.btn_back_case_details).setOnClickListener(v ->
                Navigation.findNavController(v).navigateUp()
        );


        Button btnResolve = view.findViewById(R.id.btn_mark_resolved);
        btnResolve.setOnClickListener(v -> {
            Toast.makeText(getContext(), "Case marked as Resolved!", Toast.LENGTH_SHORT).show();
            Navigation.findNavController(v).navigateUp(); // 处理完返回列表
        });


        view.findViewById(R.id.btn_contact_student).setOnClickListener(v ->
                Toast.makeText(getContext(), "Opening chat with student...", Toast.LENGTH_SHORT).show()
        );
    }
}