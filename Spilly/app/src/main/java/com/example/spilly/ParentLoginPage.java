package com.example.spilly;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;   // <--- JANGAN LUPA IMPORT INI
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

public class ParentLoginPage extends Fragment {

    public ParentLoginPage() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Pastikan nama layout xml awak betul. User bagi nama file xml 'parent_login_page.xml'
        // tapi dalam kod java awak tulis 'fragment_parent_login_page'.
        // Sila pastikan ia sepadan. Contoh di bawah guna 'parent_login_page':
        return inflater.inflate(R.layout.fragment_parent_login_page, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final NavController navController = Navigation.findNavController(view);

        // --- 1. Logik Butang Back (Kod asal awak) ---
        TextView buttonBack = view.findViewById(R.id.TVBackParent);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.popBackStack();
            }
        });

        // --- 2. Logik Butang Sign In (INI YANG BARU) ---
        Button btnSignIn = view.findViewById(R.id.ButtonSignInParent); // ID dari XML awak

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Di sini kita arahkan NavController pergi ke Home Page
                // PENTING: Awak mesti dah create 'Action' (anak panah) dalam Navigation Graph
                // Gantikan 'R.id.action_login_to_home' dengan ID sebenar dalam nav_graph.xml awak

                navController.navigate(R.id.action_parentLoginPage_to_parentHomePage);
            }
        });
    }
}