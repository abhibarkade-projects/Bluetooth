package com.abhibarkade.wireless.bluetooth;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.util.Set;

public class Bluetooth {

    AppCompatActivity activity;
    android.bluetooth.BluetoothManager manager;
    BluetoothAdapter adapter;

    public Bluetooth(AppCompatActivity activity) {
        this.activity = activity;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            manager = activity.getSystemService(BluetoothManager.class);
            adapter = manager.getAdapter();
        }
    }

    public boolean isSupported() {
        return manager != null;
    }

    public void enable() {
        if (!adapter.isEnabled()) {
            if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.BLUETOOTH_CONNECT)
                    != PackageManager.PERMISSION_GRANTED)
                activity.startActivityForResult(new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE), 101);
        }
    }

    public void disable() {
        if (adapter.isEnabled()) {
            if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.BLUETOOTH_CONNECT)
                    != PackageManager.PERMISSION_GRANTED)
                adapter.disable();
        }
    }

    public boolean isEnabled() {
        boolean flg = false;
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.BLUETOOTH_CONNECT)
                != PackageManager.PERMISSION_GRANTED)
            flg = adapter.isEnabled();
        return flg;
    }

    public void setVisible(boolean state) {
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.BLUETOOTH_SCAN)
                    != PackageManager.PERMISSION_GRANTED) {
                if (state && (!adapter.isDiscovering()))
                    adapter.startDiscovery();
                else if (!state && (adapter.isDiscovering()))
                    adapter.cancelDiscovery();
            }
        }
    }

    public Set<BluetoothDevice> getPairedDevices() {
        Set<BluetoothDevice> list = null;
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED)
            list = adapter.getBondedDevices();
        return list;
    }

    public BluetoothAdapter getAdapter() {
        return adapter;
    }
}