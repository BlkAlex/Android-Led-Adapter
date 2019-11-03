package com.blk.testcolorchooser;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.ColorSpace;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.blk.testcolorchooser.scenarios.JsonParser;
import com.blk.testcolorchooser.scenarios.LedScenario;
import com.flask.colorpicker.ColorPickerView;
import com.flask.colorpicker.OnColorChangedListener;
import com.flask.colorpicker.OnColorSelectedListener;
import com.flask.colorpicker.builder.ColorPickerClickListener;
import com.flask.colorpicker.builder.ColorPickerDialogBuilder;
import com.madrapps.pikolo.ColorPicker;
import com.madrapps.pikolo.listeners.SimpleColorSelectionListener;
import com.skydoves.colorpickerview.ColorEnvelope;
import com.skydoves.colorpickerview.ColorPickerDialog;
import com.skydoves.colorpickerview.listeners.ColorEnvelopeListener;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {
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
    BluetoothHelper bh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initOld();



        Button buttonNew = findViewById(R.id.btnNew);
        buttonNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               start();
            }
        });
        bh = new BluetoothHelper(this);
        bh.connect();

        Button tw = findViewById(R.id.id1);
        tw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });

        Button tw2 = findViewById(R.id.id2);
        tw2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showNewLibDialog();
            }
        });

        Button tw3 = findViewById(R.id.id3);
        tw3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showNewDialog();
            }
        });
      //  showDialog();
//        recyclerView = findViewById(R.id.recycler_view);
//        //initOld();
//    //    bt();
//        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
//
//        recyclerView.setLayoutManager(layoutManager);
//
//        LedScenario ls = new LedScenario();
//        try {
//            ls = JsonParser.getResponseStep(val1, LedScenario.class);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        LedScenario ls3 = new LedScenario();
//        try {
//            ls3 = JsonParser.getResponseStep(val2, LedScenario.class);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        ArrayList<LedScenario> al = new ArrayList<>();
//        al.add(ls);
//        al.add(ls3);
//        recyclerView.setAdapter(new RecyclerViewAdapter(context,al));
    }


    void start(){
        startActivity(new Intent(this, ChooseScenarioActivity.class));
    }
    int currentColor  = Color.RED;

    void showNewDialog(){

        final CustomDialogClass dialog = new CustomDialogClass(this);

        dialog.show();
        Button saveButton = (Button)dialog.findViewById(R.id.okb);
        dialog.setCurrentColor(currentColor);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentColor = dialog.getColor();
                setIntColorToSeeks(Integer.toHexString(currentColor));
                dialog.dismiss();
            }
        });





    }

    public void showNewLibDialog(){
        new ColorPickerDialog.Builder(this, AlertDialog.THEME_DEVICE_DEFAULT_DARK)
                .setTitle("ColorPicker Dialog")
                .setPreferenceName("MyColorPickerDialog")
                .setPositiveButton("yep",
                        new ColorEnvelopeListener() {
                            @Override
                            public void onColorSelected(ColorEnvelope envelope, boolean fromUser) {
                                setIntColorToSeeks(envelope.getHexCode());
                            }
                        })
                .setNegativeButton("no",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        })
                .attachAlphaSlideBar(true) // default is true. If false, do not show the AlphaSlideBar.
                .attachBrightnessSlideBar(true)  // default is true. If false, do not show the BrightnessSlideBar.
                .show();
    }
    void showDialog(){

        ColorPickerDialogBuilder
                .with(this)
                .setTitle("Choose color")
                .initialColor(currentColor)
                .lightnessSliderOnly()
                .wheelType(ColorPickerView.WHEEL_TYPE.CIRCLE)
                .density(12)
                .setOnColorSelectedListener(new OnColorSelectedListener() {
                    @Override
                    public void onColorSelected(int selectedColor) {
                        Toast toast = Toast.makeText(getApplicationContext(),
                                ("onColorSelected: 0x" + Integer.toHexString(selectedColor)), Toast.LENGTH_SHORT);
                        toast.show();


                    }
                })
                .setOnColorChangedListener(new OnColorChangedListener() {
                    @Override
                    public void onColorChanged(int selectedColor) {
                        Log.d("SC", "onColorChanged: 0x " +Integer.toHexString(selectedColor));
                    }
                })
                .setPositiveButton("ok", new ColorPickerClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int selectedColor, Integer[] allColors) {
                        int p = 0;
                        Log.d("rfd","onClick: " + String.valueOf(selectedColor));

                        currentColor = selectedColor;
                        setIntColorToSeeks(Integer.toHexString(selectedColor));
                        //changeBackgroundColor(selectedColor);
                    }
                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .build()
                .show();
    }

    void setIntColorToSeeks(String hexColor ){
        int red =(int) Long.parseLong(hexColor.substring(2,4),16);
        int green = (int) Long.parseLong(hexColor.substring(4,6),16);
        int blue = (int) Long.parseLong(hexColor.substring(6,8),16);
        int brightness = (int) Long.parseLong(hexColor.substring(0,2),16);


        TextView tw = findViewById(R.id.color);
        tw.setBackgroundColor(android.graphics.Color.argb(brightness,red, green, blue));



        bh.sendRGB(red,green,blue,brightness);

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
                bh.sendRGB(rbar.getProgress(),gbar.getProgress(), bbar.getProgress(), brbar.getProgress());

            }
        };

        rbar.setOnSeekBarChangeListener(listener);
        gbar.setOnSeekBarChangeListener(listener);
        bbar.setOnSeekBarChangeListener(listener);
        brbar.setOnSeekBarChangeListener(listener);
    }



}
