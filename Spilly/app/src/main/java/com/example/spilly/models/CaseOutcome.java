package com.example.spilly.models;

import com.google.firebase.firestore.DocumentId;
import com.google.firebase.firestore.ServerTimestamp;
import java.util.Date;

public class CaseOutcome {
    @DocumentId
    private String outcomeId;
    private String reportId; // FK to BullyingReport
    private String teacherId; // FK to Teacher
    private String actionTaken; // e.g., "Warning", "Suspension", "Counseling"
    private String resolutionSummary;
    private String sanitizedForParent; // Simplified version for parents

    @ServerTimestamp
    private Date createdAt;

    // Constructors
    public CaseOutcome() {}

    public CaseOutcome(String reportId, String teacherId, String actionTaken,
                       String resolutionSummary, String sanitizedForParent) {
        this.reportId = reportId;
        this.teacherId = teacherId;
        this.actionTaken = actionTaken;
        this.resolutionSummary = resolutionSummary;
        this.sanitizedForParent = sanitizedForParent;
    }

    // Getters and Setters
    public String getOutcomeId() { return outcomeId; }
    public void setOutcomeId(String outcomeId) { this.outcomeId = outcomeId; }

    public String getReportId() { return reportId; }
    public void setReportId(String reportId) { this.reportId = reportId; }

    public String getTeacherId() { return teacherId; }
    public void setTeacherId(String teacherId) { this.teacherId = teacherId; }

    public String getActionTaken() { return actionTaken; }
    public void setActionTaken(String actionTaken) { this.actionTaken = actionTaken; }

    public String getResolutionSummary() { return resolutionSummary; }
    public void setResolutionSummary(String resolutionSummary) { this.resolutionSummary = resolutionSummary; }

    public String getSanitizedForParent() { return sanitizedForParent; }
    public void setSanitizedForParent(String sanitizedForParent) { this.sanitizedForParent = sanitizedForParent; }

    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }
}