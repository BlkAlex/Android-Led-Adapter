package com.blk.testcolorchooser;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.blk.testcolorchooser.scenarios.ScenarioNames;
import com.flask.colorpicker.ColorPickerView;
import com.flask.colorpicker.OnColorChangedListener;
import com.flask.colorpicker.OnColorSelectedListener;
import com.flask.colorpicker.builder.ColorPickerClickListener;
import com.flask.colorpicker.builder.ColorPickerDialogBuilder;

import java.util.ArrayList;

public class ChooseScenarioActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choosescenariolayout);
        final BluetoothHelper bh = new BluetoothHelper(this);

        final Spinner spinner = findViewById(R.id.spinner);

        ScenarioNames va[] = ScenarioNames.values();
        ArrayList <String> snlist = new ArrayList<>();
        for (ScenarioNames  sn : va){
            snlist.add(sn.getName());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, snlist);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        final SeekBar seekBTW = findViewById(R.id.seekBTW);
        final SeekBar seekCh= findViewById(R.id.seekCh);
        final SeekBar seekDelay = findViewById(R.id.seekDelay);

        SeekBar.OnSeekBarChangeListener sblistener = new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                TextView twBTW = findViewById(R.id.speedBetwTextVal);
                TextView twOnCh = findViewById(R.id.speedOnChTextVal);
                TextView twDelay = findViewById(R.id.delaytextval);
                twBTW.setText(String.valueOf(seekBTW.getProgress()));
                twOnCh.setText(String.valueOf(seekCh.getProgress()));
                twDelay.setText(String.valueOf(seekDelay.getProgress()));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        };
        seekBTW.setOnSeekBarChangeListener(sblistener);
        seekCh.setOnSeekBarChangeListener(sblistener);
        seekDelay.setOnSeekBarChangeListener(sblistener);



        Button chooseColor = findViewById(R.id.chooseButton);


        chooseColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog();
            }
        });

        bh.connect();
        Button btn = findViewById(R.id.connect);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (bh.connect() == 0) {
                    bh.sendScenario(ScenarioNames.valueOf(spinner.getSelectedItem().toString()), seekBTW.getProgress(), seekCh.getProgress(), seekDelay.getProgress());
                }
            }
        });

        Button sendButton = findViewById(R.id.send);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast toast = Toast.makeText(getApplicationContext(),
                        ("onColorSelected: 0x" + Integer.toHexString(currentColor)) +
                                " Scenario = " + spinner.getSelectedItem().toString() +
                                " Speed BTW = " + seekBTW.getProgress() +
                                " Speed CH = " + seekCh.getProgress() +
                                " Delay = " + seekDelay.getProgress()
                        , Toast.LENGTH_SHORT);
                toast.show();
            }
        });


    }
    int currentColor = 0xffffffff;

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
                       // Toast toast = Toast.makeText(getApplicationContext(),
                       //         ("onColorSelected: 0x" + Integer.toHexString(selectedColor)), Toast.LENGTH_SHORT);
                       // toast.show();


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

                        String hexColor = Integer.toHexString(currentColor);
                        // if (hexColor.length() < 8){
                        //   hexColor = "00" + hexColor;
                        //}
                        TextView tw = findViewById(R.id.colortw);

                        int red =(int) Long.parseLong(hexColor.substring(2,4),16);
                        int green = (int) Long.parseLong(hexColor.substring(4,6),16);
                        int blue = (int) Long.parseLong(hexColor.substring(6,8),16);
                        int brightness = (int) Long.parseLong(hexColor.substring(0,2),16);
                        tw.setBackgroundColor(android.graphics.Color.argb(brightness,red, green, blue));


                        //setIntColorToSeeks(Integer.toHexString(selectedColor));
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


}
