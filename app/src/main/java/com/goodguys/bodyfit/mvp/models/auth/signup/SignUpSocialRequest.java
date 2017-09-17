package com.goodguys.bodyfit.mvp.models.auth.signup;

import android.os.Parcel;
import android.os.Parcelable;

import com.goodguys.bodyfit.common.CheckNull;

/**
 * Created by Oleg Romanenchuk on 28.08.2017.
 */

public class SignUpSocialRequest implements Parcelable{
    private String network;
    private String key;

    public SignUpSocialRequest(String network, String key) {
        this.network = network;
        this.key = key;
    }

    protected SignUpSocialRequest(Parcel in) {
        network = in.readString();
        key = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(network);
        dest.writeString(key);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<SignUpSocialRequest> CREATOR = new Creator<SignUpSocialRequest>() {
        @Override
        public SignUpSocialRequest createFromParcel(Parcel in) {
            return new SignUpSocialRequest(in);
        }

        @Override
        public SignUpSocialRequest[] newArray(int size) {
            return new SignUpSocialRequest[size];
        }
    };

    public String getNetwork() {
        CheckNull.check(network, this::setNetwork, () -> setNetwork(""));
        return network;
    }

    public String getKey() {
        CheckNull.check(key, this::setKey, () -> setKey(""));
        return key;
    }

    private void setNetwork(String network) {
        this.network = network;
    }

    private void setKey(String key) {
        this.key = key;
    }
}
