package com.example.spilly.models;

import com.google.firebase.firestore.DocumentId;
import com.google.firebase.firestore.ServerTimestamp;
import java.util.Date;

public class CaseReview {
    @DocumentId
    private String reviewId;
    private String reportId; // FK to BullyingReport
    private String adminId; // FK to Admin
    private String reviewNote;
    private String decision; // e.g., "Approved", "Needs Revision", "Escalated"

    @ServerTimestamp
    private Date reviewedAt;

    // Constructors
    public CaseReview() {}

    public CaseReview(String reportId, String adminId, String reviewNote, String decision) {
        this.reportId = reportId;
        this.adminId = adminId;
        this.reviewNote = reviewNote;
        this.decision = decision;
    }

    // Getters and Setters
    public String getReviewId() { return reviewId; }
    public void setReviewId(String reviewId) { this.reviewId = reviewId; }

    public String getReportId() { return reportId; }
    public void setReportId(String reportId) { this.reportId = reportId; }

    public String getAdminId() { return adminId; }
    public void setAdminId(String adminId) { this.adminId = adminId; }

    public String getReviewNote() { return reviewNote; }
    public void setReviewNote(String reviewNote) { this.reviewNote = reviewNote; }

    public String getDecision() { return decision; }
    public void setDecision(String decision) { this.decision = decision; }

    public Date getReviewedAt() { return reviewedAt; }
    public void setReviewedAt(Date reviewedAt) { this.reviewedAt = reviewedAt; }
}