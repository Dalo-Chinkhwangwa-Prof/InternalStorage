package com.bb.birthdayapp.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Birthday implements Parcelable {

    String name;
    String gender;
    String phone;
    String date;

    public Birthday(String name, String gender, String phone, String date) {
        this.name = name;
        this.gender = gender;
        this.phone = phone;
        this.date = date;
    }

    protected Birthday(Parcel in) {
        name = in.readString();
        gender = in.readString();
        phone = in.readString();
        date = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(gender);
        dest.writeString(phone);
        dest.writeString(date);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Birthday> CREATOR = new Creator<Birthday>() {
        @Override
        public Birthday createFromParcel(Parcel in) {
            return new Birthday(in);
        }

        @Override
        public Birthday[] newArray(int size) {
            return new Birthday[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
