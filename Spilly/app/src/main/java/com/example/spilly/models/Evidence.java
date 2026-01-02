package com.example.spilly.models;

import com.google.firebase.firestore.DocumentId;
import com.google.firebase.firestore.ServerTimestamp;
import java.util.Date;

public class BullyingReport {
    @DocumentId
    private String reportId;
    private String studentId; // FK to Student
    private String teacherId; // FK to Teacher (handler)
    private String caseType; // e.g., "Physical", "Verbal", "Cyber"
    private String severityLevel; // e.g., "Low", "Medium", "High"
    private String description;
    private String status; // e.g., "Pending", "Under Review", "Resolved", "Escalated"

    @ServerTimestamp
    private Date createdAt;

    // Constructors
    public BullyingReport() {}

    public BullyingReport(String studentId, String teacherId, String caseType,
                          String severityLevel, String description, String status) {
        this.studentId = studentId;
        this.teacherId = teacherId;
        this.caseType = caseType;
        this.severityLevel = severityLevel;
        this.description = description;
        this.status = status;
    }

    // Getters and Setters
    public String getReportId() { return reportId; }
    public void setReportId(String reportId) { this.reportId = reportId; }

    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }

    public String getTeacherId() { return teacherId; }
    public void setTeacherId(String teacherId) { this.teacherId = teacherId; }

    public String getCaseType() { return caseType; }
    public void setCaseType(String caseType) { this.caseType = caseType; }

    public String getSeverityLevel() { return severityLevel; }
    public void setSeverityLevel(String severityLevel) { this.severityLevel = severityLevel; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }
}