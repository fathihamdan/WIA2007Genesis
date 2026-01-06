package com.example.spilly;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast; // 可选，如果后面要用

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView; // ✅ 记得导入这个
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

public class AdminAllCasesFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_admin_all_cases, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        View btnBack = view.findViewById(R.id.btnBackFromAllCases);
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> {
                Navigation.findNavController(view).navigateUp();
            });
        }


        EditText etSearch = view.findViewById(R.id.etSearchCase);
        RecyclerView rvList = view.findViewById(R.id.rvCaseList);


        View caseCard = view.findViewById(R.id.card_case_example);

        if (caseCard != null) {
            caseCard.setOnClickListener(v -> {

                Navigation.findNavController(v)
                        .navigate(R.id.action_adminAllCasesFragment_to_adminCaseDetailsFragment);
            });
        }
    }
}