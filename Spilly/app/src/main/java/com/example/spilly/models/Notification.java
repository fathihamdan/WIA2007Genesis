package com.example.spilly.models;

import com.google.firebase.firestore.DocumentId;
import com.google.firebase.firestore.ServerTimestamp;
import java.util.Date;

public class Notification {
    @DocumentId
    private String notificationId;
    private String teacherId; // FK to Teacher (nullable)
    private String studentId; // FK to Student (nullable)
    private String parentId; // FK to Parent (nullable)
    private String reportId; // FK to BullyingReport (nullable)
    private String message;

    @ServerTimestamp
    private Date dateSent;

    private boolean isRead;

    // Constructors
    public Notification() {}

    public Notification(String teacherId, String studentId, String parentId,
                        String reportId, String message) {
        this.teacherId = teacherId;
        this.studentId = studentId;
        this.parentId = parentId;
        this.reportId = reportId;
        this.message = message;
        this.isRead = false;
    }

    // Getters and Setters
    public String getNotificationId() { return notificationId; }
    public void setNotificationId(String notificationId) { this.notificationId = notificationId; }

    public String getTeacherId() { return teacherId; }
    public void setTeacherId(String teacherId) { this.teacherId = teacherId; }

    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }

    public String getParentId() { return parentId; }
    public void setParentId(String parentId) { this.parentId = parentId; }

    public String getReportId() { return reportId; }
    public void setReportId(String reportId) { this.reportId = reportId; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public Date getDateSent() { return dateSent; }
    public void setDateSent(Date dateSent) { this.dateSent = dateSent; }

    public boolean isRead() { return isRead; }
    public void setRead(boolean read) { isRead = read; }
}