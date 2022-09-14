package com.firstapp.myfinalproject;

import android.os.Parcel;
import android.os.Parcelable;


public class HomeworkRVModal {
    private String id;
    private String homework;

    public  HomeworkRVModal(){

    }
    public HomeworkRVModal(String homework, String id) {
        this.id = id;
        this.homework = homework;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHomework() {
        return homework;
    }

    public void setHomework(String homework) {
        this.homework = homework;
    }
}