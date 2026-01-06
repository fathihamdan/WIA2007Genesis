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
import androidx.recyclerview.widget.RecyclerView;

public class AdminManageSchoolsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_admin_manage_schools, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        Button btnBack = view.findViewById(R.id.btn_back_schools);
        Button btnAddSchool = view.findViewById(R.id.btn_add_school);
        RecyclerView rvSchoolList = view.findViewById(R.id.rv_school_list);
        TextView tvTotalCount = view.findViewById(R.id.tv_total_schools_count);


        if (btnBack != null) {
            btnBack.setOnClickListener(v -> {

                Navigation.findNavController(v).popBackStack();
            });
        }


        if (btnAddSchool != null) {
            btnAddSchool.setOnClickListener(v -> {

                Toast.makeText(getContext(), "Add School feature coming soon", Toast.LENGTH_SHORT).show();
            });
        }


    }
}