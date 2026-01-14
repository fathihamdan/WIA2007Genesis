package com.example.spilly;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ParentNotificationPage extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_parent_notification_page, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        BottomNavigationView bottomNavigationView = view.findViewById(R.id.parent_bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.parentHomePage) {
                    NavHostFragment.findNavController(ParentNotificationPage.this)
                            .navigate(R.id.action_parentNotificationPage_to_parentHomePage);
                    return true;
                } else if (itemId == R.id.parentNotificationPage) {
                    // You are already here, do nothing.
                    return true;
                } else if (itemId == R.id.parentProfilePage) {
                    NavHostFragment.findNavController(ParentNotificationPage.this)
                            .navigate(R.id.action_parentNotificationPage_to_parentProfilePage);
                    return true;
                }
                return false;
            }
        });
    }
}
