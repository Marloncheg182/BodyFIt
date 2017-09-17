package com.goodguys.bodyfit.mvp.models.auth.signup;

import android.os.Parcel;
import android.os.Parcelable;

import com.goodguys.bodyfit.common.CheckNull;

/**
 * Created by Oleg Romanenchuk on 27.08.2017.
 */

public class SignUpRegularRequest implements Parcelable{
    private String email;
    private String password;

    public SignUpRegularRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    protected SignUpRegularRequest(Parcel in) {
        email = in.readString();
        password = in.readString();
    }

    public static final Creator<SignUpRegularRequest> CREATOR = new Creator<SignUpRegularRequest>() {
        @Override
        public SignUpRegularRequest createFromParcel(Parcel in) {
            return new SignUpRegularRequest(in);
        }

        @Override
        public SignUpRegularRequest[] newArray(int size) {
            return new SignUpRegularRequest[size];
        }
    };

    public String getEmail() {
        CheckNull.check(email, this::setEmail, () -> setEmail(""));
        return email;
    }

    private void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        CheckNull.check(password, this::setPassword, () -> setPassword(""));
        return password;
    }

    private void setPassword(String password) {
        this.password = password;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(email);
        dest.writeString(password);
    }


}
