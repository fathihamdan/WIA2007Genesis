package com.example.spilly;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.spilly.models.BullyingReport;
import com.example.spilly.utils.Constants;
import com.example.spilly.utils.FirebaseAuthHelper;
import com.example.spilly.utils.FirestoreHelper;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class StudentViewReport extends Fragment {

    private static final String TAG = "StudentViewReport";

    private MaterialButton btnBack;
    private TextView tvTotalReports;
    private LinearLayout reportsContainer;

    private FirebaseAuthHelper authHelper;
    private FirestoreHelper firestoreHelper;
    private NavController navController;

    private List<BullyingReport> reportsList;

    public StudentViewReport() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        authHelper = FirebaseAuthHelper.getInstance(requireContext());
        firestoreHelper = FirestoreHelper.getInstance();
        reportsList = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_student_view_report, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);

        // Initialize views
        btnBack = view.findViewById(R.id.backButton);
        tvTotalReports = view.findViewById(R.id.TotalReports);
        reportsContainer = view.findViewById(R.id.reportsContainer);

        // Back button
        btnBack.setOnClickListener(v -> navController.popBackStack());

        // Load reports
        loadReports();
    }

    private void loadReports() {

        String currentUserId = authHelper.getCurrentUserId();
        String userType = authHelper.getUserType();

        if (currentUserId == null || userType == null || userType.isEmpty()) {
            Toast.makeText(requireContext(), "Not logged in", Toast.LENGTH_SHORT).show();
            return;
        }

        // STUDENT → only own reports
        if (Constants.USER_TYPE_STUDENT.equals(userType)) {

            firestoreHelper.getReportsByStudent(currentUserId)
                    .get()
                    .addOnSuccessListener(this::handleQueryResult)
                    .addOnFailureListener(this::handleQueryError);

        }
        // TEACHER → ALL reports (no matching ID)
        else if (Constants.USER_TYPE_TEACHER.equals(userType)) {

            firestoreHelper.getReportsCollection()
                    .orderBy("createdAt", com.google.firebase.firestore.Query.Direction.DESCENDING)
                    .get()
                    .addOnSuccessListener(this::handleQueryResult)
                    .addOnFailureListener(this::handleQueryError);
        }
    }



    private void handleQueryResult(QuerySnapshot querySnapshot) {
        reportsList.clear();

        for (DocumentSnapshot doc : querySnapshot.getDocuments()) {
            BullyingReport report = doc.toObject(BullyingReport.class);
            if (report != null) {
                report.setReportId(doc.getId());
                reportsList.add(report);
            }
        }

        updateUI();
    }

    private void handleQueryError(Exception e) {
        Log.e(TAG, "Error loading reports", e);
        Toast.makeText(requireContext(),
                "Failed to load reports: " + e.getMessage(),
                Toast.LENGTH_SHORT).show();
    }

    private void updateUI() {
        // Update total count
        tvTotalReports.setText(reportsList.size() + " Total Reports");

        // Clear container
        reportsContainer.removeAllViews();

        if (reportsList.isEmpty()) {
            TextView noReports = new TextView(requireContext());
            noReports.setText("No reports yet");
            noReports.setPadding(40, 40, 40, 40);
            reportsContainer.addView(noReports);
            return;
        }

        // Add report cards
        for (BullyingReport report : reportsList) {
            View reportCard = createReportCard(report);
            reportsContainer.addView(reportCard);
        }
    }

    private View createReportCard(BullyingReport report) {
        // Inflate the report card layout
        View card = LayoutInflater.from(requireContext())
                .inflate(R.layout.item_report_card, reportsContainer, false);

        // Get views
        TextView tvCaseId = card.findViewById(R.id.tvCaseId);
        TextView tvSubmitDate = card.findViewById(R.id.tvSubmitDate);
        TextView tvDescription = card.findViewById(R.id.tvDescription);
        TextView tvBullyType = card.findViewById(R.id.tvBullyType);
        TextView tvStatus = card.findViewById(R.id.tvStatus);
        View statusCard = card.findViewById(R.id.statusCard);

        // Set data
        tvCaseId.setText("Case ID #" + report.getReportId().substring(0, 6));

        if (report.getCreatedAt() != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("MMM dd yyyy", Locale.US);
            tvSubmitDate.setText("Submitted: " + sdf.format(report.getCreatedAt()));
        }

        tvDescription.setText(report.getDescription());
        tvBullyType.setText("Type: " + report.getCaseType());
        tvStatus.setText(report.getStatus());

        // Set status colors
        setStatusColors(statusCard, tvStatus, report.getStatus());

        return card;
    }

    private void setStatusColors(View card, TextView tvStatus, String status) {
        int backgroundColor, textColor, strokeColor;

        switch (status.toLowerCase()) {
            case "pending":
                backgroundColor = 0xFFFFF6BF;
                textColor = 0xFFAB8405;
                strokeColor = 0xFFFADA3E;
                break;
            case "investigating":
                backgroundColor = 0xFFC7DFF0;
                textColor = 0xFF1250F0;
                strokeColor = 0xFF2196F3;
                break;
            case "resolved":
                backgroundColor = 0xFFD6F0B9;
                textColor = 0xFF2C9030;
                strokeColor = 0xFF4CAF50;
                break;
            default:
                backgroundColor = 0xFFE0E0E0;
                textColor = 0xFF757575;
                strokeColor = 0xFFBDBDBD;
                break;
        }

        card.setBackgroundColor(backgroundColor);
        tvStatus.setTextColor(textColor);
    }
}