package com.example.spilly.utils;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import androidx.core.app.NotificationCompat;
import com.example.spilly.R;
import com.example.spilly.models.Notification;

public class NotificationHelper {
    private static final String CHANNEL_ID = "spilly_notifications";
    private static final String CHANNEL_NAME = "Spilly Notifications";
    private static final int NOTIFICATION_ID = 1;

    private Context context;
    private NotificationManager notificationManager;

    public NotificationHelper(Context context) {
        this.context = context;
        notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        createNotificationChannel();
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            channel.setDescription("Notifications for bullying reports and updates");
            notificationManager.createNotificationChannel(channel);
        }
    }

    public void sendNotification(String title, String message, Intent intent) {
        PendingIntent pendingIntent = PendingIntent.getActivity(
                context,
                0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                // Add this icon to your drawable
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }

    // Create notification in Firestore
    public void createFirestoreNotification(Notification notification) {
        FirestoreHelper.getInstance()
                .getNotificationsCollection()
                .add(notification)
                .addOnSuccessListener(documentReference -> {
                    // Notification saved successfully
                })
                .addOnFailureListener(e -> {
                    // Handle error
                });
    }

    // Mark notification as read
    public void markAsRead(String notificationId) {
        FirestoreHelper.getInstance()
                .getNotificationsCollection()
                .document(notificationId)
                .update("read", true)
                .addOnSuccessListener(aVoid -> {
                    // Updated successfully
                })
                .addOnFailureListener(e -> {
                    // Handle error
                });
    }

    // Get unread notification count
    public void getUnreadCount(String userId, String userType, OnCountListener listener) {
        String fieldName = userType.equals(Constants.USER_TYPE_STUDENT) ? "studentId" :
                userType.equals(Constants.USER_TYPE_TEACHER) ? "teacherId" : "parentId";

        FirestoreHelper.getInstance()
                .getNotificationsCollection()
                .whereEqualTo(fieldName, userId)
                .whereEqualTo("read", false)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (listener != null) {
                        listener.onCount(queryDocumentSnapshots.size());
                    }
                })
                .addOnFailureListener(e -> {
                    if (listener != null) {
                        listener.onError(e.getMessage());
                    }
                });
    }

    public interface OnCountListener {
        void onCount(int count);
        void onError(String error);
    }
}