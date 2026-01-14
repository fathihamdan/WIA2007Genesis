package com.example.spilly;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavGraph;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AdminActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 1. Enable Edge-to-Edge
        androidx.activity.EdgeToEdge.enable(this);
        setContentView(R.layout.fragment_activity_admin);

        BottomNavigationView navView = findViewById(R.id.admin_bottom_nav);

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.admin_nav_host);

        if (navHostFragment != null) {
            NavController navController = navHostFragment.getNavController();

            // 2. Override the start destination to show Admin Home
            NavGraph navGraph = navController.getNavInflater().inflate(R.navigation.navgraph);
            navGraph.setStartDestination(R.id.adminHomeFragment);
            navController.setGraph(navGraph);

            // 3. Setup BottomNavigationView with NavController
            NavigationUI.setupWithNavController(navView, navController);

            // 4. Handle Visibility (The Core Modification)
            navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
                int id = destination.getId();

                // Show nav bar ONLY for these three admin fragments
                if (id == R.id.adminHomeFragment ||
                        id == R.id.adminAnalyticsFragment ||
                        id == R.id.adminProfileFragment) {

                    navView.setVisibility(View.VISIBLE);
                } else {
                    // Hide it for any other screen (e.g. login, details, etc.)
                    navView.setVisibility(View.GONE);
                }
            });
        }

        // 5. Handle padding for Edge-to-Edge
        View rootView = findViewById(R.id.main);
        if (rootView != null) {
            androidx.core.view.ViewCompat.setOnApplyWindowInsetsListener(rootView, (v, insets) -> {
                androidx.core.graphics.Insets systemBars = insets.getInsets(androidx.core.view.WindowInsetsCompat.Type.systemBars());
                // We typically only want the Top padding for the root;
                // Bottom padding is often handled by the NavView itself.
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0);
                return insets;
            });
        }
    }
}