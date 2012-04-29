package nl.frankkie.cowsay;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.io.File;

/**
 *
 * @author frankkie
 */
public class MainActivity extends Activity {

  TextView tv;
  Button btn;
  String cowfile = "default";
  String fortuneText = "";
  String fullText = "";
  public static Context c;
  float fontSize = 11;

  /** Called when the activity is first created.
   * @param icicle
   */
  @Override
  public void onCreate(Bundle icicle) {
    super.onCreate(icicle);
    c = this.getApplicationContext();

    setContentView(R.layout.main);
    tv = (TextView) findViewById(R.id.tv);
    tv.setTypeface(Typeface.MONOSPACE);
    tv.setTextSize(fontSize);
    btn = (Button) findViewById(R.id.btn);
    btn.setOnClickListener(new OnClickListener() {

      public void onClick(View arg0) {
        refreshCow();
      }
    });

    //
    refreshCow();
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    //TODO remove hardcoded Strings
    menu.add(0, 0, 0, "Set FontSize");
    menu.add(0, 1, 0, "Set Cow");
    menu.add(0, 2, 0, "Share Text");
    menu.add(0, 3, 0, "Share Cow");
    menu.add(0, 4, 0, "About");
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case 0:
        changeFontSize();
        break;
      case 1:
        changeCow();
        break;
      case 2:
        shareFortune();
        break;
      case 3:
        shareCowSay();
        break;
      case 4:
        showAboutDialog();
    }
    return true;
  }

  public void showAboutDialog() {
    AlertDialog.Builder builder = new AlertDialog.Builder(this);
    builder.setTitle("About");
    builder.setMessage("Implementation of cowsay in java/android by FrankkieNL\n\noriginal version by: Tony Monroe\nSee: http://en.wikipedia.org/wiki/Cowsay");
    builder.setPositiveButton("Ok", new android.content.DialogInterface.OnClickListener() {

      public void onClick(DialogInterface arg0, int arg1) {
        //nothing
      }
    });
    builder.create().show();
  }

  public void refreshCow() {
    CowSayApi c = new CowSayApi();
    fortuneText = Fortune.getFortune();
    c.setBalloonText(fortuneText);
    //System.out.println(c.list_cowfiles());
    c.setCowFile(cowfile);
    fullText = c.make();
    tv.setText(fullText);
  }

  public void changeFontSize() {
    showDialog(0);
  }

  public void changeCow() {
    // showDialog(1);
    String cowpath = Environment.getExternalStorageDirectory().getPath();
    if (!cowpath.endsWith("/")) {
      cowpath += "/";
    }
    cowpath += "cowsay/cows/";
    File f = new File(cowpath);
    final String[] list = f.list();
    AlertDialog.Builder builder = new AlertDialog.Builder(this);
    builder.setTitle("Set Cow");
    builder.setItems(list, new DialogInterface.OnClickListener() {

      public void onClick(DialogInterface dialog, int item) {
        //@see http://developer.android.com/guide/topics/ui/dialogs.html
        //Toast.makeText(getApplicationContext(), list[item], Toast.LENGTH_SHORT).show();
        cowfile = list[item];
      }
    });
    AlertDialog alert = builder.create();
    alert.show();
  }

  public void shareFortune() {
    Intent i = new Intent();
    i.setType("text/plain");
    i.setAction(Intent.ACTION_SEND);
    i.putExtra(Intent.EXTRA_TEXT, fortuneText);
    startActivity(i);
  }

  public void shareCowSay() {
    Intent i = new Intent();
    i.setType("text/plain");
    i.setAction(Intent.ACTION_SEND);
    i.putExtra(Intent.EXTRA_TEXT, fullText);
    startActivity(i);
  }

  @Override
  protected Dialog onCreateDialog(int id) {
    final Dialog d = new Dialog(this);
    switch (id) {
      case 0:
        d.setTitle("Change Font Size");
        LinearLayout lin = new LinearLayout(this);
        final EditText ed = new EditText(this);
        ed.setText("" + fontSize);
        ed.setHint("font size (float e.g.: 11)");
        lin.addView(ed);
        Button okBtn = new Button(this);
        okBtn.setText("Ok");
        lin.addView(okBtn);
        okBtn.setOnClickListener(new OnClickListener() {

          public void onClick(View arg0) {
            try {
              fontSize = Float.parseFloat(ed.getText().toString());
              tv.setTextSize(fontSize);
              dismissDialog(d);
            } catch (NumberFormatException nfe) {
              Toast.makeText(MainActivity.this, "not an Float-number: '" + ed.getText().toString() + "'", 0).show();
            }
          }
        });
        d.setContentView(lin);
        break;
      case 1:
        d.setTitle("Set Cow-file");
        //TODO
        LinearLayout lin2 = new LinearLayout(this);
        final EditText ed2 = new EditText(this);
        ed2.setText("" + cowfile);
        ed2.setHint("name of cow, e.g.: tux");
        lin2.addView(ed2);
        Button okBtn2 = new Button(this);
        okBtn2.setText("Ok");
        lin2.addView(okBtn2);
        okBtn2.setOnClickListener(new OnClickListener() {

          public void onClick(View arg0) {
            cowfile = ed2.getText().toString();
            //tv.setTextSize(fontSize);
            dismissDialog(d);
          }
        });
        d.setContentView(lin2);
        break;
    }
    return d;
  }

  public void dismissDialog(Dialog d) {
    try {
      d.dismiss();
    } catch (Exception e) {
      //./ignore
    }
  }
}
