package com.firstapp.myfinalproject;

public class AnnouncementRVModal {
    private String id;
    private String announcement;

    public  AnnouncementRVModal(){

    }

    public AnnouncementRVModal(String announcement, String id) {
        this.id = id;
        this.announcement = announcement;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAnnouncement() {
        return announcement;
    }

    public void setAnnouncement(String announcement) {
        this.announcement = announcement;
    }
}