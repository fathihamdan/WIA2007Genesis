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
import androidx.fragment.app.Fragment;

import com.example.spilly.models.BullyingReport;
import com.example.spilly.utils.FirestoreHelper;
import com.google.android.material.card.MaterialCardView;
import com.google.firebase.firestore.DocumentSnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class TeacherCasesPage extends Fragment {

    private static final String TAG = "TeacherCasesPage";

    private TextView tvTotalCount, tvPendingCount, tvActiveCount, tvResolvedCount;
    private LinearLayout casesListContainer;

    private FirestoreHelper firestoreHelper;
    private List<BullyingReport> reportsList;

    public TeacherCasesPage() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firestoreHelper = FirestoreHelper.getInstance();
        reportsList = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_teacher_cases_page, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvTotalCount = view.findViewById(R.id.total_count);
        tvPendingCount = view.findViewById(R.id.pending_count);
        tvActiveCount = view.findViewById(R.id.active_count);
        tvResolvedCount = view.findViewById(R.id.resolved_count);
        casesListContainer = view.findViewById(R.id.cases_list);

        loadAllReports();
    }

    private void loadAllReports() {
        firestoreHelper.getReportsCollection()
                .orderBy("createdAt", com.google.firebase.firestore.Query.Direction.DESCENDING)
                .get()
                .addOnSuccessListener(querySnapshot -> {
                    reportsList.clear();

                    for (DocumentSnapshot doc : querySnapshot.getDocuments()) {
                        BullyingReport report = doc.toObject(BullyingReport.class);
                        if (report != null) {
                            report.setReportId(doc.getId());
                            reportsList.add(report);
                        }
                    }

                    updateUI();
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, "Error loading reports", e);
                    Toast.makeText(requireContext(),
                            "Failed to load reports: " + e.getMessage(),
                            Toast.LENGTH_SHORT).show();
                });
    }

    private void updateUI() {
        int total = reportsList.size();
        int pending = 0, active = 0, resolved = 0;

        for (BullyingReport report : reportsList) {
            String status = report.getStatus().toLowerCase();
            if (status.equals("pending")) pending++;
            else if (status.equals("investigating")) active++;
            else if (status.equals("resolved")) resolved++;
        }

        tvTotalCount.setText(String.valueOf(total));
        tvPendingCount.setText(String.valueOf(pending));
        tvActiveCount.setText(String.valueOf(active));
        tvResolvedCount.setText(String.valueOf(resolved));

        casesListContainer.removeAllViews();

        if (reportsList.isEmpty()) {
            TextView noReports = new TextView(requireContext());
            noReports.setText("No reports found");
            noReports.setPadding(40, 40, 40, 40);
            casesListContainer.addView(noReports);
            return;
        }

        for (BullyingReport report : reportsList) {
            View row = createCaseRow(report);
            casesListContainer.addView(row);
        }
    }

    private View createCaseRow(BullyingReport report) {
        LinearLayout rowWrapper = new LinearLayout(requireContext());
        rowWrapper.setOrientation(LinearLayout.VERTICAL);
        rowWrapper.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));

        LinearLayout rowContent = new LinearLayout(requireContext());
        rowContent.setOrientation(LinearLayout.HORIZONTAL);
        rowContent.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, 200));
        rowContent.setPadding(30, 20, 30, 20);

        TextView tvId = new TextView(requireContext());
        LinearLayout.LayoutParams idParams = new LinearLayout.LayoutParams(0,
                LinearLayout.LayoutParams.WRAP_CONTENT, 1.2f);
        tvId.setLayoutParams(idParams);
        tvId.setText("#" + report.getReportId().substring(0, 6));
        tvId.setTextColor(0xFF2563EB);
        tvId.setTextSize(15);

        TextView tvType = new TextView(requireContext());
        LinearLayout.LayoutParams typeParams = new LinearLayout.LayoutParams(0,
                LinearLayout.LayoutParams.WRAP_CONTENT, 1.4f);
        tvType.setLayoutParams(typeParams);
        tvType.setText(report.getCaseType());
        tvType.setTextColor(0xFF111827);
        tvType.setTextSize(15);

        TextView tvDate = new TextView(requireContext());
        LinearLayout.LayoutParams dateParams = new LinearLayout.LayoutParams(0,
                LinearLayout.LayoutParams.WRAP_CONTENT, 1.2f);
        tvDate.setLayoutParams(dateParams);
        if (report.getCreatedAt() != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("MMM\ndd,\nyyyy", Locale.US);
            tvDate.setText(sdf.format(report.getCreatedAt()));
        }
        tvDate.setTextColor(0xFF374151);
        tvDate.setTextSize(14);

        TextView tvStatus = new TextView(requireContext());
        tvStatus.setText(report.getStatus());
        tvStatus.setTextSize(13);
        tvStatus.setPadding(30, 15, 30, 15);

        int bgColor, textColor;
        switch (report.getStatus().toLowerCase()) {
            case "pending":
                bgColor = 0xFFFFF7E0;
                textColor = 0xFF7A4C00;
                break;
            case "investigating":
                bgColor = 0xFFDEEBFF;
                textColor = 0xFF2563EB;
                break;
            case "resolved":
                bgColor = 0xFFD1FAE5;
                textColor = 0xFF059669;
                break;
            default:
                bgColor = 0xFFE5E7EB;
                textColor = 0xFF6B7280;
                break;
        }
        tvStatus.setBackgroundColor(bgColor);
        tvStatus.setTextColor(textColor);

        rowContent.addView(tvId);
        rowContent.addView(tvType);
        rowContent.addView(tvDate);
        rowContent.addView(tvStatus);

        View divider = new View(requireContext());
        divider.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, 2));
        divider.setBackgroundColor(0xFFE5E7EB);

        rowWrapper.addView(rowContent);
        rowWrapper.addView(divider);

        return rowWrapper;
    }
}