package com.example.spilly.utils;

import android.net.Uri;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import java.util.UUID;

public class StorageHelper {
    private static StorageHelper instance;
    private FirebaseStorage storage;
    private StorageReference storageRef;

    private StorageHelper() {
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();
    }

    public static synchronized StorageHelper getInstance() {
        if (instance == null) {
            instance = new StorageHelper();
        }
        return instance;
    }

    public FirebaseStorage getStorage() {
        return storage;
    }

    // Upload evidence file
    public UploadTask uploadEvidence(Uri fileUri, String reportId, OnUploadListener listener) {
        String fileName = UUID.randomUUID().toString();
        StorageReference fileRef = storageRef.child(Constants.STORAGE_EVIDENCE + reportId + "/" + fileName);

        UploadTask uploadTask = fileRef.putFile(fileUri);

        uploadTask.addOnSuccessListener(taskSnapshot -> {
            fileRef.getDownloadUrl().addOnSuccessListener(uri -> {
                if (listener != null) {
                    listener.onSuccess(uri.toString());
                }
            });
        }).addOnFailureListener(e -> {
            if (listener != null) {
                listener.onFailure(e.getMessage());
            }
        }).addOnProgressListener(snapshot -> {
            double progress = (100.0 * snapshot.getBytesTransferred()) / snapshot.getTotalByteCount();
            if (listener != null) {
                listener.onProgress(progress);
            }
        });

        return uploadTask;
    }

    // Delete evidence file
    public void deleteEvidence(String fileUrl, OnDeleteListener listener) {
        StorageReference fileRef = storage.getReferenceFromUrl(fileUrl);
        fileRef.delete()
                .addOnSuccessListener(aVoid -> {
                    if (listener != null) {
                        listener.onSuccess();
                    }
                })
                .addOnFailureListener(e -> {
                    if (listener != null) {
                        listener.onFailure(e.getMessage());
                    }
                });
    }

    // Interfaces for callbacks
    public interface OnUploadListener {
        void onSuccess(String downloadUrl);
        void onFailure(String error);
        void onProgress(double progress);
    }

    public interface OnDeleteListener {
        void onSuccess();
        void onFailure(String error);
    }
}