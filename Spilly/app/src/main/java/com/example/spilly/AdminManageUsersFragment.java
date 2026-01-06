package com.example.spilly;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class AdminManageUsersFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_admin_manage_users, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        TextView btnBack = view.findViewById(R.id.btn_back_users);
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> {
                Navigation.findNavController(v).navigateUp();
            });
        }


        FloatingActionButton fabAdd = view.findViewById(R.id.fab_add_user);
        if (fabAdd != null) {
            fabAdd.setOnClickListener(v -> {
                Toast.makeText(getContext(), "Add User feature coming soon!", Toast.LENGTH_SHORT).show();
            });
        }


        RecyclerView rvList = view.findViewById(R.id.rvUserList);
    }
}