package com.example.spilly; // KEEP YOUR ORIGINAL PACKAGE NAME HERE

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import android.os.Bundle;
import android.view.MenuItem;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class parentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent);

        // 1. Find the Navigation Bar from your XML
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);

        // 2. Set the "Listener" (Wait for clicks)
        bottomNav.setOnItemSelectedListener(navListener);

        // 3. Load the Home Fragment by default so the screen isn't empty on startup
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new ParentHomeFragment()).commit();
        }
    }

    // This section handles the click logic
    private final BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

                    // Check which button was clicked using the IDs from your menu.xml
                    int itemId = item.getItemId();

                    if (itemId == R.id.nav_home) {
                        selectedFragment = new ParentHomeFragment();
                    }
                    else if (itemId == R.id.nav_alerts) {
                        selectedFragment = new ParentAlertsFragment();
                    }
                    else if (itemId == R.id.nav_profile) {
                        selectedFragment = new ParentProfileFragment();
                    }

                    // Perform the swap
                    if (selectedFragment != null) {
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment_container, selectedFragment)
                                .commit();
                    }

                    return true; // Return true to highlight the button
                }
            };
}