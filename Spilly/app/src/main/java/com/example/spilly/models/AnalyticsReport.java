package com.example.spilly.models;

import com.google.firebase.firestore.DocumentId;
import com.google.firebase.firestore.ServerTimestamp;
import java.util.Date;

public class AnalyticsRecord {
    @DocumentId
    private String analyticsId;
    private String teacherId; // FK to Teacher

    @ServerTimestamp
    private Date dateGenerated;

    private String summary;
    private int totalReports;
    private int resolvedCount;
    private int escalatedCount;

    // Constructors
    public AnalyticsRecord() {}

    public AnalyticsRecord(String teacherId, String summary, int totalReports,
                           int resolvedCount, int escalatedCount) {
        this.teacherId = teacherId;
        this.summary = summary;
        this.totalReports = totalReports;
        this.resolvedCount = resolvedCount;
        this.escalatedCount = escalatedCount;
    }

    // Getters and Setters
    public String getAnalyticsId() { return analyticsId; }
    public void setAnalyticsId(String analyticsId) { this.analyticsId = analyticsId; }

    public String getTeacherId() { return teacherId; }
    public void setTeacherId(String teacherId) { this.teacherId = teacherId; }

    public Date getDateGenerated() { return dateGenerated; }
    public void setDateGenerated(Date dateGenerated) { this.dateGenerated = dateGenerated; }

    public String getSummary() { return summary; }
    public void setSummary(String summary) { this.summary = summary; }

    public int getTotalReports() { return totalReports; }
    public void setTotalReports(int totalReports) { this.totalReports = totalReports; }

    public int getResolvedCount() { return resolvedCount; }
    public void setResolvedCount(int resolvedCount) { this.resolvedCount = resolvedCount; }

    public int getEscalatedCount() { return escalatedCount; }
    public void setEscalatedCount(int escalatedCount) { this.escalatedCount = escalatedCount; }
}