package com.blk.testcolorchooser;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.ColorSpace;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.blk.testcolorchooser.scenarios.JsonParser;
import com.blk.testcolorchooser.scenarios.LedScenario;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {
    static final UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    final String val1 = "{\n" +
            "\t\"nameMode\": \"custom\",\n" +
            "  \t\"colorMode\" : \"#ffffff\",\n" +
            "  \t\"brigthness\" : \"255\",\n" +
            "\t\"writeToController\": true,\n" +
            "\t\"leds\":[\n" +
            "\t{\n" +
            "\t\t\"nleds\":\"3\",\n" +
            "\t\t\"ledId\":\"led1Id_1\",\n" +
            "\t\t\"lastLedId\": \"none\",\n" +
            "\t\t\"nextLedId\": \"none\",\n" +
            "\t\t\"duration\" : \"400\",\n" +
            "\t\t\"color\" :\"#ff00ff\",\n" +
            "      \t\"brightness\" : \"255\",\n" +
            "\t\t\"delayBefore\":\"100\",\n" +
            "\t\t\"delayAfter\":\"100\"\n" +
            "\t},\n" +
            "\t{\n" +
            "\t\t\"nleds\":\"8\",\n" +
            "\t\t\"ledId\":\"led1Id_1\",\n" +
            "\t\t\"lastLedId\": \"none\",\n" +
            "\t\t\"nextLedId\": \"none\",\n" +
            "\t\t\"duration\" : \"400\",\n" +
            "      \t\"brightness\" : \"255\",\n" +
            "\t\t\"color\" :\"#ff00ff\",\n" +
            "\t\t\"delayBefore\":\"100\",\n" +
            "\t\t\"delayAfter\":\"100\"\n" +
            "\t}\n" +
            "\t]\n" +
            "}";

    final String val2 = "{\n" +
            "\t\"nameMode\": \"custom\",\n" +
            "  \t\"colorMode\" : \"#ffffff\",\n" +
            "  \t\"brigthness\" : \"255\",\n" +
            "\t\"writeToController\": true\n" +
            "}";
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alternative_layout);
        recyclerView = findViewById(R.id.recycler_view);
        //initOld();
    //    bt();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(layoutManager);

        LedScenario ls = new LedScenario();
        try {
            ls = JsonParser.getResponseStep(val1, LedScenario.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        LedScenario ls3 = new LedScenario();
        try {
            ls3 = JsonParser.getResponseStep(val2, LedScenario.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        ArrayList<LedScenario> al = new ArrayList<>();
        al.add(ls);
        al.add(ls3);
        recyclerView.setAdapter(new RecyclerViewAdapter(context,al));
    }


    void initOld(){
        final SeekBar rbar = findViewById(R.id.seekBarR);
        final SeekBar gbar = findViewById(R.id.seekBarG);
        final SeekBar bbar = findViewById(R.id.seekBarB);
        final SeekBar brbar = findViewById(R.id.seekBarBrightness);

        final TextView tw = findViewById(R.id.color);
        final TextView twR = findViewById(R.id.redText);
        final TextView twG = findViewById(R.id.greenText);
        final TextView twB = findViewById(R.id.blueText);
        final TextView twBR = findViewById(R.id.briText);
        tw.setBackgroundColor(android.graphics.Color.argb(brbar.getProgress(),rbar.getProgress(), gbar.getProgress(), bbar.getProgress()));

        SeekBar.OnSeekBarChangeListener listener = new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tw.setBackgroundColor(android.graphics.Color.argb(brbar.getProgress(),rbar.getProgress(), gbar.getProgress(), bbar.getProgress()));
                twR.setText("Красный : " + rbar.getProgress());
                twG.setText("Зеленый : " + gbar.getProgress());
                twB.setText("Синий : " +  bbar.getProgress());
                twBR.setText("Яркость : " + brbar.getProgress());

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                sendRGB(rbar.getProgress(),gbar.getProgress(), bbar.getProgress(), brbar.getProgress());

            }
        };
        context = this;

        rbar.setOnSeekBarChangeListener(listener);
        gbar.setOnSeekBarChangeListener(listener);
        bbar.setOnSeekBarChangeListener(listener);
        brbar.setOnSeekBarChangeListener(listener);
    }


    BluetoothAdapter adapter;
    private Set<BluetoothDevice> paireddevices;
    private String address;

    BluetoothSocket btSocket = null;
    private boolean isBtConnected = false;
    private ProgressDialog progress;
    void bt(){
            adapter = BluetoothAdapter.getDefaultAdapter();
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
                Toast.makeText(this, "No Paired Bluetooth Devices Found.", Toast.LENGTH_LONG).show();
            }
            int i=5;
            i++;


        for (String t: list) {
            if (t.contains("ESP"))
                address = t;
        }
        address = address.substring(address.length() - 17);
        new  connectbt().execute();
        }

    Context context;
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
