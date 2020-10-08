package com.example.healthcare;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    public static Context context;

    private Me_dbopen me_dbopen;
    private Dia_dbopen dia_dbopen;
    private Obe_dbopen obe_dbopen;

    TextView list;
    TextView obe;
    TextView obepm;
    TextView mag;
    TextView maf;
    TextView lag;
    TextView laf;
    TextView dag;
    TextView daf;

    String today;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;

        me_dbopen = new Me_dbopen(this);
        dia_dbopen = new Dia_dbopen(this);
        obe_dbopen = new Obe_dbopen(this);

        me_dbopen.open();
        dia_dbopen.open();
        obe_dbopen.open();

        LinearLayout b1 = (LinearLayout) findViewById(R.id.button1);
        Button b2 = (Button) findViewById(R.id.button2);
        LinearLayout b3 = (LinearLayout) findViewById(R.id.button3);
        LinearLayout b4 = (LinearLayout) findViewById(R.id.button4);
        Button b4_b=(Button)findViewById(R.id.button4_b);

        list = (TextView) findViewById(R.id.list);
        obe = (TextView) findViewById(R.id.main_obe);
        obepm = (TextView) findViewById(R.id.main_obepm);
        mag = (TextView) findViewById(R.id.mag);
        maf = (TextView) findViewById(R.id.maf);
        lag = (TextView) findViewById(R.id.lag);
        laf = (TextView) findViewById(R.id.laf);
        dag = (TextView) findViewById(R.id.dag);
        daf = (TextView) findViewById(R.id.daf);

        long now = System.currentTimeMillis();
        Date d = new Date(now);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        today = simpleDateFormat.format(d);

        showMain();

        //페이지 전환버튼

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Hos_Act.class);
                startActivity(intent);  //엑티비티 띄우기

                //복약관리창 띄우기
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Me_Act.class);
                startActivity(intent);

                //범용금기창 띄우기
            }
        });

        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Dia_Act.class);
                startActivity(intent);

                //당뇨지수창 띄우기
            }
        });

        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Obe_Act.class);
                startActivity(intent);

                //비만도창 띄우기
            }
        });

        b4_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Obe_Act.class);
                startActivity(intent);

                //비만도창 띄우기
            }
        });
    }

    public void showMain() {
        Cursor c1 = me_dbopen.sortColumn();

        String itemname = "";

        while (c1.moveToNext()) {
            itemname = itemname + "- " + c1.getString(c1.getColumnIndex("item_name")) + "\n\n";
        }
        list.setText(itemname);

        Cursor c2 = dia_dbopen.sortColumn();

        while (c2.moveToNext()) {
            if (today.equals(c2.getString(c2.getColumnIndex("indate")))) {
                mag.setText(c2.getString(c2.getColumnIndex("mag")));
                maf.setText(c2.getString(c2.getColumnIndex("maf")));
                lag.setText(c2.getString(c2.getColumnIndex("lag")));
                laf.setText(c2.getString(c2.getColumnIndex("laf")));
                dag.setText(c2.getString(c2.getColumnIndex("dag")));
                daf.setText(c2.getString(c2.getColumnIndex("daf")));
            }
        }

        Cursor c3 = obe_dbopen.selectColumns();

        while (c3.moveToNext()) {
            if (today.equals(c3.getString(c3.getColumnIndex("indate")))) {
                obe.setText(c3.getString(c3.getColumnIndex("kg")));
                obepm.setText("bmi : " + c3.getString(c3.getColumnIndex("bmi")));
            }
        }
    }

}
