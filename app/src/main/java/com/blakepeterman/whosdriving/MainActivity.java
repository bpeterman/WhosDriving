package com.blakepeterman.whosdriving;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.InputType;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;
import java.util.Vector;


public class MainActivity extends Activity {

    private LinearLayout mLayout;
    private ScrollView scroll;
    private Button addBut;
    private Button chooseBut;
    private int driverNum=4;
    private ArrayList<EditText> drivers= new ArrayList<EditText>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLayout = (LinearLayout) findViewById(R.id.moreBoxes);
        scroll = (ScrollView) findViewById(R.id.scroll);
        chooseBut = (Button) findViewById(R.id.choose);
        chooseBut.setOnClickListener(onClickChoose());
        addBut = (Button) findViewById(R.id.add);
        addBut.setOnClickListener(onClickAdd());
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        drivers.add((EditText) findViewById(R.id.driver1));
        drivers.add((EditText) findViewById(R.id.driver2));
        drivers.add((EditText) findViewById(R.id.driver3));
    }

    private OnClickListener onClickAdd() {
        return new OnClickListener() {

            @Override
            public void onClick(View v) {
                scroll.fullScroll(View.FOCUS_DOWN);
                mLayout.addView(createNewEditText());

            }
        };
    }

    private View.OnClickListener onClickChoose() {
        return new OnClickListener() {

            @Override
            public void onClick(View v) {
                Vector<String> names = new Vector<String>();
                for(int i = 0; i<drivers.size(); i++){
                    if(drivers.get(i).getText().toString().equals("")){

                    } else{
                        names.add(drivers.get(i).getText().toString());
                    }
                }
                if (names.isEmpty()){
                    hideKeyboard();
                    Toast.makeText(getApplicationContext(), "You didn't enter any names...",
                            Toast.LENGTH_LONG).show();
                } else {
                    hideKeyboard();

                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    TextView myMsg = new TextView(MainActivity.this);
                    myMsg.setText(names.get(randInt(0, names.size()-1))+" is driving");
                    myMsg.setGravity(Gravity.CENTER_HORIZONTAL);
                    myMsg.setTextSize(18);
                    myMsg.setPadding(10,10,10,10);
                    builder.setView(myMsg);
                    builder.setCancelable(false)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    //do things
                                }
                            }).setView(myMsg);
                    AlertDialog alert = builder.create();
                    alert.show();
                    //Toast.makeText(getApplicationContext(), names.get(randInt(0, names.size()-1))+" is driving",
                    //        Toast.LENGTH_LONG).show();
                }

            }
        };
    }
    private View.OnClickListener onClickPlus() {
        return new OnClickListener() {

            @Override
            public void onClick(View v) {
                mLayout.addView(createNewEditText());
            }
        };
    }

    private EditText createNewEditText() {
        final LayoutParams lparams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        final EditText editText = new EditText(this);
        editText.setLayoutParams(lparams);
        editText.setHint("Driver " + driverNum);
        editText.requestFocus();
        editText.setInputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
        drivers.add(editText);
        driverNum++;
        return editText;
    }
    private void hideKeyboard(){
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }
    private static int randInt(int min, int max) {

        // NOTE: Usually this should be a field rather than a method
        // variable so that it is not re-seeded every call.
        Random rand = new Random();
        rand.setSeed(System.currentTimeMillis()*SystemClock.elapsedRealtimeNanos());
        // nextInt is normally exclusive of the top value,
        // so add 1 to make it inclusive
        int randomNum = rand.nextInt((max - min) + 1) + min;

        return randomNum;
    }

    protected void showAbout() {
        // Inflate the about message contents
        View messageView = getLayoutInflater().inflate(R.layout.about, null, false);

        // When linking text, force to always use default color. This works
        // around a pressed color state bug.
        TextView textView = (TextView) messageView.findViewById(R.id.about_credits);
        int defaultColor = textView.getTextColors().getDefaultColor();
        textView.setTextColor(defaultColor);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.app_name);
        builder.setView(messageView);
        builder.create();
        builder.show();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.erase) {
            for(int i = 0; i<drivers.size(); i++){
                drivers.get(i).setText("");
                if(i>=3){
                    drivers.get(i).setVisibility(View.GONE);
                    drivers.remove(i);
                    i--;
                }
                driverNum=4;
            }
            drivers.get(0).requestFocus();
            return true;
        }
        if (id == R.id.about) {
            showAbout();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

