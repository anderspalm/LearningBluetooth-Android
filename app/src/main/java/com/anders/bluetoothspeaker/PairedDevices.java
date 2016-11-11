package com.anders.bluetoothspeaker;

/**
 * Created by anders on 11/11/2016.
 */
public class PairedDevices {
    String mName, mAddress;

    public PairedDevices(String name, String address) {
        mName = name;
        mAddress = address;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getAddress() {
        return mAddress;
    }

    public void setAddress(String address) {
        mAddress = address;
    }
}
