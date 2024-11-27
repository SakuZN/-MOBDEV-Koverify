package com.example.koverify;

public class DashboardItem {
    private String title;
    private int imageResId;
    private Class<?> targetActivity;
    private String extraKey;
    private String extraValue;

    public DashboardItem(String title, int imageResId, Class<?> targetActivity) {
        this.title = title;
        this.imageResId = imageResId;
        this.targetActivity = targetActivity;
    }

    public DashboardItem(String title, int imageResId, Class<?> targetActivity, String extraKey, String extraValue) {
        this.title = title;
        this.imageResId = imageResId;
        this.targetActivity = targetActivity;
        this.extraKey = extraKey;
        this.extraValue = extraValue;
    }

    // Getters and Setters
    public String getTitle() { return title; }
    public int getImageResId() { return imageResId; }
    public Class<?> getTargetActivity() { return targetActivity; }
    public String getExtraKey() { return extraKey; }
    public String getExtraValue() { return extraValue; }
}
