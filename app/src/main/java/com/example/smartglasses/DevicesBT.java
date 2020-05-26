package com.example.smartglasses;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Set;

public class DevicesBT extends AppCompatActivity {
    private static final String TAG = "DevicesBT";
    public static String EXTRA_DEVICE_ADDRESS = "device_address";
    private BluetoothAdapter mBtAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_devices_bt);

    }

    @Override
    public void onResume() {
        super.onResume();
        VerifyStatusBT();
        ArrayAdapter<String> mPairedArrayAdapter = new ArrayAdapter(this, R.layout.nameofdevices);
        ListView idLista = findViewById(R.id.Idlista);
        idLista.setAdapter(mPairedArrayAdapter);
        idLista.setOnItemClickListener(mDeviceClickListener);
        mBtAdapter = BluetoothAdapter.getDefaultAdapter();
        Set<BluetoothDevice> pairedDevices = mBtAdapter.getBondedDevices();
        if (pairedDevices.size() > 0) {
            for (BluetoothDevice device : pairedDevices) {
                mPairedArrayAdapter.add(device.getName() + "\n" + device.getAddress());
            }
        }
    }

    private AdapterView.OnItemClickListener mDeviceClickListener = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView<?> av, View v, int arg2, long arg3) {
            String info = ((TextView) v).getText().toString();
            String address = info.substring(info.length() - 17);
            Intent i = new Intent(DevicesBT.this, MainActivity.class);
            Toast.makeText(DevicesBT.this, address, Toast.LENGTH_LONG).show();

            i.putExtra(EXTRA_DEVICE_ADDRESS, address);
            startActivity(i);
        }
    };

    private void VerifyStatusBT() {
        mBtAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBtAdapter == null) {

            Toast.makeText(getBaseContext(), "the device didn't support the bluetooth", Toast.LENGTH_LONG).show();
        } else {

            if (mBtAdapter.isEnabled()) {

                Log.d(TAG, "bluetooth activied");


            } else {
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, 1);
            }
        }
    }
}
