package com.wja.keren.user.home.base;

import android.os.Parcel;
import android.os.Parcelable;

public class BaseModel implements Parcelable {

    protected int id;

    protected BaseModel(Parcel in) {
        id = in.readInt();
    }

    public static final Creator <BaseModel> CREATOR = new Creator <BaseModel>() {
        @Override
        public BaseModel createFromParcel(Parcel in) {
            return new BaseModel(in);
        }

        @Override
        public BaseModel[] newArray(int size) {
            return new BaseModel[size];
        }
    };

    public void save() {

    }

    public void update() {

    }

    public void remove() {

    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
    }
}
