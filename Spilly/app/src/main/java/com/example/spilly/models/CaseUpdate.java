package com.example.spilly.models;

import com.google.firebase.firestore.DocumentId;
import com.google.firebase.firestore.ServerTimestamp;
import java.util.Date;

public class CaseUpdate {
    @DocumentId
    private String updateId;
    private String reportId; // FK to BullyingReport
    private String teacherId; // FK to Teacher

    @ServerTimestamp
    private Date updateDate;

    private String updateType; // e.g., "Status Change", "Comment", "Escalation"
    private String notes;

    // Constructors
    public CaseUpdate() {}

    public CaseUpdate(String reportId, String teacherId, String updateType, String notes) {
        this.reportId = reportId;
        this.teacherId = teacherId;
        this.updateType = updateType;
        this.notes = notes;
    }

    // Getters and Setters
    public String getUpdateId() { return updateId; }
    public void setUpdateId(String updateId) { this.updateId = updateId; }

    public String getReportId() { return reportId; }
    public void setReportId(String reportId) { this.reportId = reportId; }

    public String getTeacherId() { return teacherId; }
    public void setTeacherId(String teacherId) { this.teacherId = teacherId; }

    public Date getUpdateDate() { return updateDate; }
    public void setUpdateDate(Date updateDate) { this.updateDate = updateDate; }

    public String getUpdateType() { return updateType; }
    public void setUpdateType(String updateType) { this.updateType = updateType; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
}