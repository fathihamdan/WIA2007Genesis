package com.example.spilly;

import android.os.Bundle;import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView; // Import TextView

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

public class TeacherLoginPage extends Fragment {

    public TeacherLoginPage() {
        // Diperlukan oleh sistem, biarkan kosong
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_teacher_login_page, container, false);
    }

    // Kod logik ditambah dalam onViewCreated
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Dapatkan NavController
        final NavController navController = Navigation.findNavController(view);

        // --- Logik untuk Butang "Back" ---
        // Anda perlu semak ID butang "Back" dalam fail fragment_teacher_login_page.xml
        TextView buttonBack = view.findViewById(R.id.TVBackTeacher); // <-- TUKAR ID INI

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Arahan mudah untuk kembali ke skrin sebelumnya
                navController.popBackStack();
            }
        });

        // Di sini anda boleh tambah logik untuk butang "Sign In" jika perlu
    }
}
