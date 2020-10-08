package com.example.healthcare;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Obe_Act extends AppCompatActivity {
    Obe_Adapter obe_adapter = new Obe_Adapter();
    public static Context context;

    String year;
    String month;
    String day;
    String totalday;

    String indate;
    String date;

    public Obe_dbopen dbOpenHelper;

    Button Bdate;
    EditText ob_cm;
    EditText ob_kg;
    TextView ob_bmi;
    Button insert;

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.obesity);
        context = this;

        listView = (ListView) findViewById(R.id.obe_list);

        dbOpenHelper = new Obe_dbopen(this);
        dbOpenHelper.open();
        dbOpenHelper.create();

        Bdate = findViewById(R.id.ob_date);
        ob_kg = (EditText) findViewById(R.id.kg);
        ob_cm = (EditText) findViewById(R.id.cm);
        ob_bmi = (TextView) findViewById(R.id.bmi);
        insert = findViewById(R.id.ob_insert);

        Cursor c = Obe_dbopen.mDB.rawQuery("SELECT * FROM obe", null);
        c.moveToFirst();
        if (c != null && c.getCount() == 0) {
        } else {
            String maincm = c.getString(c.getColumnIndex("cm"));
            ob_cm.setText(maincm);
        }

        final InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        long now = System.currentTimeMillis();
        Date d = new Date(now);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy");
        year = simpleDateFormat.format(d);

        simpleDateFormat = new SimpleDateFormat("MM");
        month = simpleDateFormat.format(d);

        simpleDateFormat = new SimpleDateFormat("dd");
        day = simpleDateFormat.format(d);

        totalday = year + " / " + month + " / " + day;
        Bdate.setText(totalday);
        date = totalday;
        indate = totalday.replace(" / ", "");

        Bdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Obe_popup.class);
                startActivityForResult(intent, 0);
            }
        });

        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputMethodManager.hideSoftInputFromWindow(ob_kg.getWindowToken(), 0);
                ContentValues contentValues = new ContentValues();

                double cm = Double.parseDouble((ob_cm.getText().toString()));
                double kg = 0;
                try {
                    kg = Double.parseDouble(ob_kg.getText().toString());
                } catch (NumberFormatException e) {
                    System.out.println("90");
                }
                double bmi = Math.round(kg / cm / cm * 10000 * 10) / 10.0;

                contentValues.put(Obe_db.CreateDB.indate, indate);
                contentValues.put(Obe_db.CreateDB.date, date);
                contentValues.put(Obe_db.CreateDB.cm, String.valueOf(cm));
                contentValues.put(Obe_db.CreateDB.kg, String.valueOf(kg));
                contentValues.put(Obe_db.CreateDB.bmi, String.valueOf(bmi));

                Cursor cursor = dbOpenHelper.sortColumn();

                int num = 0;

                while (cursor.moveToNext()) {
                    String c_indate = cursor.getString(cursor.getColumnIndex("indate"));
                    if (c_indate.equals(indate)) {
                        Obe_dbopen.mDB.execSQL("UPDATE obe SET cm=" + cm + " WHERE indate='" + indate + "';");
                        Obe_dbopen.mDB.execSQL("UPDATE obe SET kg=" + kg + " WHERE indate='" + indate + "';");
                        Obe_dbopen.mDB.execSQL("UPDATE obe SET bmi=" + bmi + " WHERE indate='" + indate + "';");

                        num = 1;
                    }
                }

                if (num == 0)
                    dbOpenHelper.insert(contentValues);

                ob_kg.setText("");
                showdatabase();
            }
        });
        showdatabase();
    }

    //--------날짜 입력 받음
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Obe_Act.super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case 1:
                year = data.getStringExtra("year");
                month = data.getStringExtra("month");
                day = data.getStringExtra("day");

                totalday = year + " / " + month + " / " + day;

                Bdate.setText(totalday);
                date = totalday;
                indate = totalday.replace(" / ", "");

                break;
            default:
                break;

        }
    }

    public void showdatabase() {
        Cursor cursor = dbOpenHelper.sortColumn();
        obe_adapter.clear();

        String[] day = new String[10000];
        String[] cm = new String[10000];
        String[] kg = new String[10000];
        String[] bmi = new String[10000];
        String[] condition = new String[10000];

        int num = 0;

        while (cursor.moveToNext()) {
            day[num] = cursor.getString(cursor.getColumnIndex("date"));
            cm[num] = cursor.getString(cursor.getColumnIndex("cm"));
            kg[num] = cursor.getString(cursor.getColumnIndex("kg"));
            bmi[num] = cursor.getString(cursor.getColumnIndex("bmi"));
            double minus = Double.parseDouble(kg[num]) - Double.parseDouble(cm[num]) * Double.parseDouble(cm[num]) * 18.5 / 10000;
            double plus = Double.parseDouble(kg[num]) - Double.parseDouble(cm[num]) * Double.parseDouble(cm[num]) * 22.9 / 10000;

            if (Double.parseDouble(bmi[num]) < 18.5)
                condition[num] = "저체중\n" + Math.round(minus * 10) / 10.0;
            else if (18.5 <= Double.parseDouble(bmi[num]) && Double.parseDouble(bmi[num]) < 23)
                condition[num] = "정상";
            else if (23 <= Double.parseDouble(bmi[num]) && Double.parseDouble(bmi[num]) < 25)
                condition[num] = "과체중\n+" + Math.round(plus * 10) / 10.0;
            else if (25 <= Double.parseDouble(bmi[num]) && Double.parseDouble(bmi[num]) < 30)
                condition[num] = "비만\n+" + Math.round(plus * 10) / 10.0;
            else if (30 <= Double.parseDouble(bmi[num]))
                condition[num] = "고도비만\n+" + Math.round(plus * 10) / 10.0;

            num++;
        }

        for (int i = 0; i < num; i++) {
            obe_adapter.addItem(day[i], cm[i], kg[i], bmi[i], condition[i]);
        }
        obe_adapter.notifyDataSetChanged();
        listView.setAdapter(obe_adapter);
    }

    public void delete(String day) {
        dbOpenHelper.delete(day);
        showdatabase();
    }

    @Override
    public void onBackPressed() {
        ((MainActivity) MainActivity.context).showMain();
        super.onBackPressed();
    }
}
