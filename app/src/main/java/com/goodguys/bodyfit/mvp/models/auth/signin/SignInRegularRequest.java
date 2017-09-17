package com.goodguys.bodyfit.mvp.models.auth.signin;

import android.os.Parcel;
import android.os.Parcelable;

import com.goodguys.bodyfit.common.CheckNull;

/**
 * Created by Oleg Romanenchuk on 27.08.2017.
 */

public class SignInRegularRequest implements Parcelable{
    private String email;
    private String password;

    public SignInRegularRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    protected SignInRegularRequest(Parcel in) {
        email = in.readString();
        password = in.readString();
    }

    public static final Creator<SignInRegularRequest> CREATOR = new Creator<SignInRegularRequest>() {
        @Override
        public SignInRegularRequest createFromParcel(Parcel in) {
            return new SignInRegularRequest(in);
        }

        @Override
        public SignInRegularRequest[] newArray(int size) {
            return new SignInRegularRequest[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(email);
        dest.writeString(password);
    }

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
}
