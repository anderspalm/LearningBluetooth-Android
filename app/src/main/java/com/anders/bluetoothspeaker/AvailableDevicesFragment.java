package com.anders.bluetoothspeaker;

import android.app.Fragment;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;
import java.util.zip.Inflater;

/**
 * Created by anders on 11/11/2016.
 */

public class AvailableDevicesFragment extends Fragment {

    BroadcastReceiver mReceiver;
    public ArrayList<PairedDevices> mAvailableDevices;
    private static final String TAG = "Fragment";
    AvailableDeviceAdapter mAdapter;
    Context mContext;
    Button mScan;
    Boolean mClicked;
    public BluetoothAdapter mAdapterBluetooth;

    public AvailableDevicesFragment newInstance(BluetoothAdapter adapter) {
        AvailableDevicesFragment fragment = new AvailableDevicesFragment();
        mAdapterBluetooth = adapter;
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.available_devices_fragment,container,true);
        mAvailableDevices = new ArrayList<>();
        mContext = getActivity().getBaseContext();
        mScan = (Button) view.findViewById(R.id.scan);

        RecyclerView rv = (RecyclerView) view.findViewById(R.id.available_devices);
        GridLayoutManager gridlayoutManager = new GridLayoutManager(mContext,1);
        mAdapter = new AvailableDeviceAdapter(mAvailableDevices,mContext);
        rv.setLayoutManager(gridlayoutManager);
        rv.setAdapter(mAdapter);

        mReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if(BluetoothDevice.ACTION_FOUND.equals(action)){
                    BluetoothDevice newDevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

                    PairedDevices newDeviceItem = new PairedDevices(newDevice.getName(),newDevice.getAddress());
                    mAvailableDevices.add(newDeviceItem);
                    for (PairedDevices device : mAvailableDevices){
                        Log.i(TAG, "onReceive: " + device.getAddress() + " " + device.getAddress());
                    }
                    mAdapter.updateAdapter(mAvailableDevices);
                }
            }
        };

        mScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentFilter intentFilter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
                if (!mClicked) {
                    mAdapter.clearAdapter();
                    getActivity().registerReceiver(mReceiver, intentFilter);
                    mAdapterBluetooth.startDiscovery();
                } else {
                    getActivity().unregisterReceiver(mReceiver);
                    mAdapterBluetooth.cancelDiscovery();
                }
            }
        });



        return view;
    }


    public interface OnFragmentInteractionListener {
        public void onFragmentInteraction(String id);
    }
}
