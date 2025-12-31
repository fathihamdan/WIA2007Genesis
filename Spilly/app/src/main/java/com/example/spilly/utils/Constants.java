package com.example.spilly.utils;

public class Constants {
    // Firestore Collections
    public static final String COLLECTION_USERS = "users";
    public static final String COLLECTION_STUDENTS = "students";
    public static final String COLLECTION_PARENTS = "parents";
    public static final String COLLECTION_TEACHERS = "teachers";
    public static final String COLLECTION_ADMINS = "admins";
    public static final String COLLECTION_REPORTS = "bullying_reports";
    public static final String COLLECTION_EVIDENCE = "evidence";
    public static final String COLLECTION_UPDATES = "case_updates";
    public static final String COLLECTION_NOTIFICATIONS = "notifications";
    public static final String COLLECTION_OUTCOMES = "case_outcomes";
    public static final String COLLECTION_REVIEWS = "case_reviews";
    public static final String COLLECTION_ANALYTICS = "analytics_records";

    // User Types
    public static final String USER_TYPE_STUDENT = "student";
    public static final String USER_TYPE_TEACHER = "teacher";
    public static final String USER_TYPE_PARENT = "parent";
    public static final String USER_TYPE_ADMIN = "admin";

    // Report Status
    public static final String STATUS_PENDING = "Pending";
    public static final String STATUS_UNDER_REVIEW = "Under Review";
    public static final String STATUS_RESOLVED = "Resolved";
    public static final String STATUS_ESCALATED = "Escalated";

    // Severity Levels
    public static final String SEVERITY_LOW = "Low";
    public static final String SEVERITY_MEDIUM = "Medium";
    public static final String SEVERITY_HIGH = "High";

    // Case Types
    public static final String CASE_PHYSICAL = "Physical";
    public static final String CASE_VERBAL = "Verbal";
    public static final String CASE_CYBER = "Cyber";
    public static final String CASE_SOCIAL = "Social Exclusion";

    // Update Types
    public static final String UPDATE_STATUS_CHANGE = "Status Change";
    public static final String UPDATE_COMMENT = "Comment";
    public static final String UPDATE_ESCALATION = "Escalation";

    // File Types
    public static final String FILE_TYPE_IMAGE = "image";
    public static final String FILE_TYPE_VIDEO = "video";
    public static final String FILE_TYPE_DOCUMENT = "document";

    // Firebase Storage Paths
    public static final String STORAGE_EVIDENCE = "evidence/";

    // SharedPreferences Keys
    public static final String PREF_USER_ID = "user_id";
    public static final String PREF_USER_TYPE = "user_type";
    public static final String PREF_USER_NAME = "user_name";
    public static final String PREF_USER_EMAIL = "user_email";
}