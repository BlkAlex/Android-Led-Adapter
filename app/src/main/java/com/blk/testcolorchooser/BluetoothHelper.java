package com.blk.testcolorchooser;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.blk.testcolorchooser.scenarios.ScenarioNames;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;


public class BluetoothHelper {



    BluetoothHelper(Context context){
        this.context = context;

    }
    static final UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");


    BluetoothAdapter adapter;
    private Set<BluetoothDevice> paireddevices;
    private String address;

    BluetoothSocket btSocket = null;

    public boolean isBtConnected() {
        return isBtConnected;
    }

    private boolean isBtConnected = false;
    private ProgressDialog progress;

    Context context;
    public int connect(){
        adapter = BluetoothAdapter.getDefaultAdapter();
        if (adapter == null)
            return -1;
        paireddevices=adapter.getBondedDevices();
        ArrayList<String> list = new ArrayList();
        if (paireddevices.size()>0)
        {
            for(BluetoothDevice bt : paireddevices)
            {
                list.add(bt.getName() + "\n" + bt.getAddress());
            }
        }
        else
        {
            Toast.makeText(context, "No Paired Bluetooth Devices Found.", Toast.LENGTH_LONG).show();
        }
        int i=5;
        i++;


        for (String t: list) {
            if (t.contains("ESP"))
                address = t;
        }
        address = address.substring(address.length() - 17);
        new  connectbt().execute();
        return 0;
    }

    void sendRGB(int r,int g,int b,int br){
        if (isBtConnected) {
            String s = String.format("{\"red\":\"%s\" , \"green\":\"%s\" , \"blue\":\"%s\" , \"brightness\":\"%s\"}\n", r, g, b, br);
            Log.d("MESSAGE", s);
            try {
                btSocket.getOutputStream().write(s.getBytes());
            } catch (IOException e) {
                Log.d("bt:", "Error");
            }
        }
    }

    void sendScenario(String name,int speedBetw,int speedCh,int maxDelay,int color){
        if (isBtConnected) {
            String hexColor = Integer.toHexString(color);
            int red =(int) Long.parseLong(hexColor.substring(2,4),16);
            int green = (int) Long.parseLong(hexColor.substring(4,6),16);
            int blue = (int) Long.parseLong(hexColor.substring(6,8),16);
            int brightness = (int) Long.parseLong(hexColor.substring(0,2),16);


            String s = String.format("" +
                    "{\"scenario\":\"%s\" , " +
                    "\"delayChairs\":\"%s\" , " +
                    "\"delayPerChair\":\"%s\" , " +
                    "\"delayAfterAnimation\":\"%s\" ," +
                    " \"red\":\"%s\" , " +
                    "\"green\":\"%s\" , " +
                    "\"blue\":\"%s\" , " +
                    "\"brightness\":\"%s\"" +
                    "}\n", name, speedBetw, speedCh, maxDelay*1000,red,green,blue,brightness);

            Log.d("MESSAGE SCENARIO", s);
            try {
                btSocket.getOutputStream().write(s.getBytes());
            } catch (IOException e) {
                Log.d("bt:", "Error");
            }
        }
    }



    private class connectbt extends AsyncTask<Void, Void, Void>  // UI thread
    {
        private boolean ConnectSuccess = true; //if it's here, it's almost connected

        @Override
        protected void onPreExecute() {
            progress = ProgressDialog.show(context, "Connecting...", "Please wait!!!");  //show a progress dialog
        }

        @Override
        protected Void doInBackground(Void... devices) //while the progress dialog is shown, the connection is done in background
        {
            try {
                if (btSocket == null || !isBtConnected) {
                    adapter = BluetoothAdapter.getDefaultAdapter();//get the mobile bluetooth device
                    BluetoothDevice dispositivo = adapter.getRemoteDevice(address);//connects to the device's address and checks if it's available
                    btSocket = dispositivo.createInsecureRfcommSocketToServiceRecord(myUUID);//create a RFCOMM (SPP) connection
                    BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
                    btSocket.connect();//start connection
                }
            } catch (IOException e) {
                ConnectSuccess = false;//if the try failed, you can check the exception here
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) //after the doInBackground, it checks if everything went fine
        {
            super.onPostExecute(result);

            if (!ConnectSuccess) {
                Toast.makeText(context.getApplicationContext(), "Connection Failed Please Try again later", Toast.LENGTH_SHORT).show();


            } else {
                Toast.makeText(context.getApplicationContext(), "Successfully Connected ", Toast.LENGTH_SHORT).show();

                isBtConnected = true;

            }
            progress.dismiss();
        }
    }
}
