package com.firstapp.myfinalproject;

import android.os.Parcel;
import android.os.Parcelable;

public class StudentRVModal implements Parcelable {
    public String studentName;
    public String studentComment;
    public String studentAge;
    public String studentHouse;
    public String studentImageUri;
    public String maths;
    public String science;
    public String english;
    public String sSt;
    public String studentID;
    public StudentRVModal(){

    }

    public StudentRVModal(String studentName, String studentComment, String studentAge, String studentHouse, String studentImageUri, String maths, String science, String english, String sSt, String studentID) {
        this.studentName = studentName;
        this.studentComment = studentComment;
        this.studentAge = studentAge;
        this.studentHouse = studentHouse;
        this.studentImageUri = studentImageUri;
        this.studentID = studentID;
        this.maths = maths;
        this.science = science;
        this.english = english;
        this.sSt = sSt;
    }

    protected StudentRVModal(Parcel in) {
        studentName = in.readString();
        studentComment = in.readString();
        studentAge = in.readString();
        studentHouse = in.readString();
        studentImageUri = in.readString();
        studentID = in.readString();
        maths = in.readString();
        science = in.readString();
        english = in.readString();
        sSt = in.readString();

    }

    public static final Creator<StudentRVModal> CREATOR = new Creator<StudentRVModal>() {
        @Override
        public StudentRVModal createFromParcel(Parcel in) {
            return new StudentRVModal(in);
        }

        @Override
        public StudentRVModal[] newArray(int size) {
            return new StudentRVModal[size];
        }
    };

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentComment() {
        return studentComment;
    }

    public void setStudentComment(String studentComment) {this.studentComment = studentComment; }

    public String getStudentAge() {
        return studentAge;
    }

    public void setStudentAge(String studentAge) {
        this.studentAge = studentAge;
    }

    public String getStudentHouse() {
        return studentHouse;
    }

    public void setStudentHouse(String studentHouse) {
        this.studentHouse = studentHouse;
    }

    public String getStudentImageUri() {
        return studentImageUri;
    }

    public void setStudentImageUri(String studentImageUri) { this.studentImageUri = studentImageUri;
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public String getMaths() {
        return maths;
    }

    public void setMaths(String maths) {
        this.maths = maths;
    }


    public String getEnglish() {
        return english;
    }

    public void setEnglish(String english) {this.english = english;  }

    public String getScience() {
        return science;
    }

    public void setScience(String science) {
        this.science = science;
    }

    public String getsSt() {
        return sSt;
    }

    public void setsSt(String sSt) { this.sSt = sSt;}

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(studentName);
        parcel.writeString(studentComment);
        parcel.writeString(studentAge);
        parcel.writeString(studentHouse);
        parcel.writeString(studentImageUri);
        parcel.writeString(studentID);
        parcel.writeString(maths);
        parcel.writeString(science);
        parcel.writeString(english);
        parcel.writeString(sSt);

    }
}
