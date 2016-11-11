package com.anders.bluetoothspeaker;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by anders on 11/11/2016.
 */

public class PairingViewHolder extends RecyclerView.ViewHolder {

    TextView mName, mAddress;

    public PairingViewHolder(View itemView) {
        super(itemView);

        mName = (TextView) itemView.findViewById(R.id.name);
        mAddress = (TextView) itemView.findViewById(R.id.address);
    }
}
