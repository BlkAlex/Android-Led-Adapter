package com.blk.testcolorchooser;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.flask.colorpicker.ColorPickerView;

public class CustomDialogClass extends Dialog implements
    android.view.View.OnClickListener {

  public Activity c;
  public Dialog d;
  public Button yes, no;

  public CustomDialogClass(Activity a) {
    super(a);
    // TODO Auto-generated constructor stub
    this.c = a;
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

  }

public void getColor(){
  ColorPickerView cpv = findViewById(R.id.color_picker_view);

}
  @Override
  public void onClick(View v) {
    switch (v.getId()) {
    case R.id.okb:
      c.finish();
      break;
    case R.id.cancelb:
      dismiss();
      break;
    default:
      break;
    }
    dismiss();
  }
}