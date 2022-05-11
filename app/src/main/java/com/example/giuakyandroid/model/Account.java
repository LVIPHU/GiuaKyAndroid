package com.example.giuakyandroid.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Account implements Parcelable {
    public String username;
    public String password;
    public String chucVu;
    public String email;
    public String name;


    public Account(String username, String password, String chucVu, String email, String name) {
        this.username = username;
        this.password = password;
        this.chucVu = chucVu;
        this.email = email;
        this.name = name;
    }

    public Account() {
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getChucVu() {
        return chucVu;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setChucVu(String chucVu) {
        this.chucVu = chucVu;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.username);
        dest.writeString(this.password);
        dest.writeString(this.chucVu);
        dest.writeString(this.email);
        dest.writeString(this.name);
    }

    protected Account(Parcel in) {
        this.username = in.readString();
        this.password = in.readString();
        this.chucVu = in.readString();
        this.email = in.readString();
        this.name =in.readString();
    }

    public static final Parcelable.Creator<Account> CREATOR = new Parcelable.Creator<Account>() {
        @Override
        public Account createFromParcel(Parcel source) {
            return new Account(source);
        }

        @Override
        public Account[] newArray(int size) {
            return new Account[size];
        }
    };
}
