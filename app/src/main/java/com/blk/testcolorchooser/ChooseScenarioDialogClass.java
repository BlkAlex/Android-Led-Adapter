package com.blk.testcolorchooser;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.madrapps.pikolo.ColorPicker;
import com.madrapps.pikolo.listeners.SimpleColorSelectionListener;

public class ChooseScenarioDialogClass extends Dialog implements
    View.OnClickListener {

  public Activity c;
  public Dialog d;
  public Button yes, no;

  public ChooseScenarioDialogClass(Activity a) {
    super(a);
    // TODO Auto-generated constructor stub
    this.c = a;
  }

  int currentColor = 0;

  ColorPicker colorPicker;

  void setCurrentColor(int color){
    currentColor = color;
    colorPicker.setColor(currentColor);
  }
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    setContentView(R.layout.alternative_layout);
    yes = (Button) findViewById(R.id.okb);
    no = (Button) findViewById(R.id.cancelb);
    yes.setOnClickListener(this);
    no.setOnClickListener(this);


    colorPicker = findViewById(R.id.colorPicker);
    colorPicker.setColorSelectionListener(new SimpleColorSelectionListener() {
      @Override
      public void onColorSelected(int color) {
        // Do whatever you want with the color
        currentColor = color;            }
    });
  }

public int getColor(){
  return  currentColor;

}
  @Override
  public void onClick(View v) {
    switch (v.getId()) {

    case R.id.cancelb:
      dismiss();
      break;
    default:
      break;
    }
    dismiss();
  }
}