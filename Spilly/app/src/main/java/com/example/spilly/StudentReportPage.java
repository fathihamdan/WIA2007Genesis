package com.example.spilly;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ReportPage#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StudentReportPage extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public StudentReportPage() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ReportPage.
     */
    // TODO: Rename and change types and number of parameters
    public static StudentReportPage newInstance(String param1, String param2) {
        StudentReportPage fragment = new StudentReportPage();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_student_report_page, container, false);

        LinearLayout lowBtn = view.findViewById(R.id.RBLowSevere);
        LinearLayout mediumBtn = view.findViewById(R.id.RBMediumSevere);
        LinearLayout highBtn = view.findViewById(R.id.RBHighSevere);

        lowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // This code runs when the box is clicked
                // Example: Highlight the box or save the "Low" value
                v.setSelected(true);
            }
        });

        mediumBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setSelected(true);
            }
        });

        highBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setSelected(true);
            }
        });

        String[] bullyingTypes = {"Select type...", "Cyberbullying", "Physical", "Verbal", "Social", "Others"};

        // 3. Find the Spinner using 'view.findViewById' (IMPORTANT: you must use 'view.')
        Spinner spinner = view.findViewById(R.id.spinner_bullying_type);

        // 4. Create and set the adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, bullyingTypes);
        spinner.setAdapter(adapter);

        return view;
    }
}