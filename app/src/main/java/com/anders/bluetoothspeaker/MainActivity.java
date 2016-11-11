package com.anders.bluetoothspeaker;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private BluetoothAdapter mBluetoothAdapter;
    private Set<BluetoothDevice> mPairedDevices;
    public int mBluetoothRequest;
    public ArrayList<PairedDevices> mPairedDevicesArray;
    public ArrayList<PairedDevices> mAvailableDevices;
    Button mScan, mConnected;
    BroadcastReceiver mReceiver;
    AvailableDevicesFragment ADF;
    Boolean mChecked;

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBluetoothRequest = 1;
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        mScan = (Button) findViewById(R.id.scan);
        mConnected = (Button) findViewById(R.id.current);
        mPairedDevicesArray = new ArrayList<>();
        mAvailableDevices = new ArrayList<>();
        ADF = new AvailableDevicesFragment();

//      check if the adapter was not able to be initialized, if so then close the application
        if (mBluetoothAdapter == null) {
            new AlertDialog.Builder(this)
                    .setTitle("Bluetooth Error")
                    .setMessage("Your device might not have bluetooth capabilities")
                    .setPositiveButton("Exit Application", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            System.exit(0);
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        } else {

//      check and if not enabled, ask the user enable bluetooth on the device with an implicit intent
            if (!mBluetoothAdapter.isEnabled()) {
                Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(intent, mBluetoothRequest);
            }


            mScan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//              Now to pair unconnected devices to yours
//              paired devices share a link key that allows them to know each other's existence
                    getFragmentManager()
                            .beginTransaction()
                            .add(R.id.avail_dev_fragment,ADF)
                            .addToBackStack(null)
                            .commit();


                }
            });

            mConnected.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                  The following gives you a list of devices currently connected to yours
                    mPairedDevices = mBluetoothAdapter.getBondedDevices();
                    for (BluetoothDevice device : mPairedDevices) {
//                  use getName() and getAddress() to get information from these
//                  getAddress() returns a key that uniquely identifies the device
                        PairedDevices pairedDevices = new PairedDevices(device.getName(), device.getAddress());
                        mPairedDevicesArray.add(pairedDevices);
                        Log.i(TAG, "onCreate: currently paired device: " + device.getName() + " " + device.getAddress());
                    }

                }
            });

        }
    }


}
