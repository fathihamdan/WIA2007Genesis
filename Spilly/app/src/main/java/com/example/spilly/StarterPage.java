package com.example.spilly;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button; // <-- PENTING: Import class Button

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

public class StarterPage extends Fragment {

    public StarterPage() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_starter_page, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        final NavController navController = Navigation.findNavController(view);


        Button buttonStudent = view.findViewById(R.id.ButtonStudent);
        Button buttonTeacher = view.findViewById(R.id.ButtonTeacher);
        Button buttonParent = view.findViewById(R.id.ButtonParent);

        // 3. Tetapkan 'OnClickListener' untuk setiap butang
        // Apabila butang 'Student' ditekan...
        buttonStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                navController.navigate(R.id.action_starterPage_to_studentLoginPage);
            }
        });

        // Apabila butang 'Teacher' ditekan...
        buttonTeacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // ...navigasi ke halaman login guru
                // PENTING: Pastikan 'action_starterPage_to_teacherLoginPage' wujud dalam navgraph.xml
                navController.navigate(R.id.action_starterPage_to_teacherLoginPage);
            }
        });

        // Apabila butang 'Parent' ditekan...
        buttonParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // ...navigasi ke halaman login ibubapa
                // PENTING: Pastikan 'action_starterPage_to_parentLoginPage' wujud dalam navgraph.xml
                navController.navigate(R.id.action_starterPage_to_parentLoginPage);
            }
        });
    }
}
