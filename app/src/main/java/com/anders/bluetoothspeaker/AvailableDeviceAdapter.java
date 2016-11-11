package com.anders.bluetoothspeaker;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by anders on 11/11/2016.
 */

public class AvailableDeviceAdapter extends RecyclerView.Adapter<PairingViewHolder> {

    List<PairedDevices> mList;
    Context mContext;

    public AvailableDeviceAdapter(List<PairedDevices> devices, Context context) {
        mList = devices;
        mContext = context;
    }

    @Override
    public PairingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.available_devices_fragment,parent,false);
        PairingViewHolder PVH = new PairingViewHolder(view);
        return PVH;
    }

    @Override
    public void onBindViewHolder(PairingViewHolder holder, int position) {

        holder.mName.setText(mList.get(position).getName());
        holder.mAddress.setText(mList.get(position).getAddress());

    }

    public void updateAdapter(List<PairedDevices> devices){
        mList = devices;
        notifyDataSetChanged();
    }

    public void clearAdapter(){
        mList = null;
    }

    @Override
    public int getItemCount() {
        if (mList != null){
            return mList.size();
        }
        else {
            return 0;
        }
    }
}
