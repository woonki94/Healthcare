package com.example.healthcare;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;

import java.util.Calendar;

public class Obe_popup extends Activity implements View.OnClickListener {
    public static Context context;

    Button insert;
    Button cancel;
    DatePicker dp;
    Calendar cld;
    public String y;
    public String m;
    public String d;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.day_popup);

        cld = Calendar.getInstance();
        y = ((Obe_Act) Obe_Act.context).year;
        m = ((Obe_Act) Obe_Act.context).month;
        d = ((Obe_Act) Obe_Act.context).day;

        insert = (Button) findViewById(R.id.add);
        cancel = (Button) findViewById(R.id.cancel);
        dp = (DatePicker) findViewById(R.id.day);

        dp.init(Integer.parseInt(y), Integer.parseInt(m) - 1, Integer.parseInt(d), null);

        insert.setOnClickListener(this);
        cancel.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if (v == insert) {

            Intent intent = new Intent();
            String year = String.valueOf(dp.getYear());
            String month = String.format("%02d", dp.getMonth() + 1);
            String day = String.format("%02d", dp.getDayOfMonth());

            intent.putExtra("year", year);
            intent.putExtra("month", month);
            intent.putExtra("day", day);

            setResult(1, intent);
            finish();

        } else if (v == cancel) {
            finish();
        }

    }
}

