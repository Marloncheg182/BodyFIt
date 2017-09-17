package com.goodguys.bodyfit.mvp.models.auth.signin;

import android.os.Parcel;
import android.os.Parcelable;

import com.goodguys.bodyfit.common.CheckNull;

/**
 * Created by Oleg Romanenchuk on 28.08.2017.
 */

public class SignInSocialRequest implements Parcelable{
    private String network;
    private String networkKey;

    public SignInSocialRequest(String network, String networkKey) {
        this.network = network;
        this.networkKey = networkKey;
    }

    protected SignInSocialRequest(Parcel in) {
        network = in.readString();
        networkKey = in.readString();
    }

    public static final Creator<SignInSocialRequest> CREATOR = new Creator<SignInSocialRequest>() {
        @Override
        public SignInSocialRequest createFromParcel(Parcel in) {
            return new SignInSocialRequest(in);
        }

        @Override
        public SignInSocialRequest[] newArray(int size) {
            return new SignInSocialRequest[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(network);
        dest.writeString(networkKey);
    }

    public String getNetwork() {
        CheckNull.check(network, this::setNetwork, () -> setNetwork(""));
        return network;
    }

    private void setNetwork(String network) {
        this.network = network;
    }

    public String getNetworkKey() {
        CheckNull.check(networkKey, this::setNetworkKey, () -> setNetworkKey(""));
        return networkKey;
    }

    private void setNetworkKey(String networkKey) {
        this.networkKey = networkKey;
    }
}
