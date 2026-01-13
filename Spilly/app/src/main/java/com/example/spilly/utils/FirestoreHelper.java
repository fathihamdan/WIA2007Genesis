//JAVA/com.example.spilly/utils/firestoreHelper

package com.example.spilly.utils;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class FirestoreHelper {
    private static FirestoreHelper instance;
    private FirebaseFirestore db;

    private FirestoreHelper() {
        db = FirebaseFirestore.getInstance();
    }

    public static synchronized FirestoreHelper getInstance() {
        if (instance == null) {
            instance = new FirestoreHelper();
        }
        return instance;
    }

    public FirebaseFirestore getDb() {
        return db;
    }

    // Collection References
    public CollectionReference getUsersCollection() {
        return db.collection(Constants.COLLECTION_USERS);
    }

    public CollectionReference getStudentsCollection() {
        return db.collection(Constants.COLLECTION_STUDENTS);
    }

    public CollectionReference getParentsCollection() {
        return db.collection(Constants.COLLECTION_PARENTS);
    }

    public CollectionReference getTeachersCollection() {
        return db.collection(Constants.COLLECTION_TEACHERS);
    }

    public CollectionReference getAdminsCollection() {
        return db.collection(Constants.COLLECTION_ADMINS);
    }

    public CollectionReference getReportsCollection() {
        return db.collection(Constants.COLLECTION_REPORTS);
    }

    public CollectionReference getEvidenceCollection() {
        return db.collection(Constants.COLLECTION_EVIDENCE);
    }

    public CollectionReference getUpdatesCollection() {
        return db.collection(Constants.COLLECTION_UPDATES);
    }

    public CollectionReference getNotificationsCollection() {
        return db.collection(Constants.COLLECTION_NOTIFICATIONS);
    }

    public CollectionReference getOutcomesCollection() {
        return db.collection(Constants.COLLECTION_OUTCOMES);
    }

    public CollectionReference getReviewsCollection() {
        return db.collection(Constants.COLLECTION_REVIEWS);
    }

    public CollectionReference getAnalyticsCollection() {
        return db.collection(Constants.COLLECTION_ANALYTICS);
    }

    // Document References
    public DocumentReference getUserDoc(String userId) {
        return getUsersCollection().document(userId);
    }

    public DocumentReference getStudentDoc(String studentId) {
        return getStudentsCollection().document(studentId);
    }

    public DocumentReference getReportDoc(String reportId) {
        return getReportsCollection().document(reportId);
    }

    // Queries
    public Query getReportsByStudent(String studentId) {
        return getReportsCollection()
                .whereEqualTo("studentId", studentId)
                .orderBy("createdAt", Query.Direction.DESCENDING);
    }

    public Query getReportsByTeacher(String teacherId) {
        return getReportsCollection()
                .whereEqualTo("teacherId", teacherId)
                .orderBy("createdAt", Query.Direction.DESCENDING);
    }

    public Query getReportsByStatus(String status) {
        return getReportsCollection()
                .whereEqualTo("status", status)
                .orderBy("createdAt", Query.Direction.DESCENDING);
    }

    public Query getNotificationsByUser(String userId, String userType) {
        String fieldName = userType.equals(Constants.USER_TYPE_STUDENT) ? "studentId" :
                userType.equals(Constants.USER_TYPE_TEACHER) ? "teacherId" : "parentId";
        return getNotificationsCollection()
                .whereEqualTo(fieldName, userId)
                .orderBy("dateSent", Query.Direction.DESCENDING);
    }

    public Query getEvidenceByReport(String reportId) {
        return getEvidenceCollection()
                .whereEqualTo("reportId", reportId)
                .orderBy("uploadedAt", Query.Direction.DESCENDING);
    }

    public Query getUpdatesByReport(String reportId) {
        return getUpdatesCollection()
                .whereEqualTo("reportId", reportId)
                .orderBy("updateDate", Query.Direction.DESCENDING);
    }
}