package com.example.healthcare;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Dia_Act extends AppCompatActivity {
    Dia_Adapter dia_adapter = new Dia_Adapter();

    public Button mrn;
    public Button lnch;
    public Button dnnr;

    public CheckBox bfor;
    public CheckBox aft;

    ListView listview;

    public static Context context;
    Button Bdate;

    String year;
    String month;
    String day;
    String totalday;

    public String indate;
    String date;
    long time = 0;
    long bfaft = 0;

    //---------------db
    public Dia_dbopen dbOpenHelper;
    //---------------

    EditText data;
    Button insert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diabetes);
        context = this;

        listview = (ListView) findViewById(R.id.dia_list);

        //---------db
        dbOpenHelper = new Dia_dbopen(this);
        dbOpenHelper.open();
        dbOpenHelper.create();

        Bdate = findViewById(R.id.date);
        data = (EditText) findViewById(R.id.data);
        insert = findViewById(R.id.insert);


        mrn = (Button) findViewById(R.id.mrn);
        lnch = (Button) findViewById(R.id.lnch);
        dnnr = (Button) findViewById(R.id.dnnr);
        bfor = (CheckBox) findViewById(R.id.before);
        aft = (CheckBox) findViewById(R.id.after);

        final InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);


        long now = System.currentTimeMillis();
        final Date d = new Date(now);

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

        showDatabase();

        //------------중복 클릭 방지
        Button.OnClickListener onClickListener = new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.mrn:
                        lnch.setSelected(false);
                        dnnr.setSelected(false);
                        mrn.setSelected(true);
                        time = 1;
                        break;

                    case R.id.lnch:
                        dnnr.setSelected(false);
                        mrn.setSelected(false);
                        lnch.setSelected(true);
                        time = 2;
                        break;

                    case R.id.dnnr:
                        mrn.setSelected(false);
                        lnch.setSelected(false);
                        dnnr.setSelected(true);
                        time = 3;
                        break;
                }
            }
        };
        mrn.setOnClickListener(onClickListener);
        lnch.setOnClickListener(onClickListener);
        dnnr.setOnClickListener(onClickListener);


        bfor.setOnClickListener(new CheckBox.OnClickListener() {
            @Override
            public void onClick(View v) {
                aft.setChecked(false);
                bfaft = 1;

            }
        });

        aft.setOnClickListener(new CheckBox.OnClickListener() {
            @Override
            public void onClick(View v) {
                bfor.setChecked(false);
                bfaft = 2;

            }
        });

        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputMethodManager.hideSoftInputFromWindow(data.getWindowToken(), 0);
                ContentValues contentValues = new ContentValues();
                int num = 0;
                int i = 0;

                String dia = data.getText().toString();
                Cursor cursor = dbOpenHelper.sortColumn();

                if (dia.equals("")) {
                    num = 2;
                    Toast.makeText(Dia_Act.context, "혈당을 입력하세요", Toast.LENGTH_SHORT).show();
                }

                if (num == 0) {

                    while (cursor.moveToNext()) {
                        String c_indate = cursor.getString(cursor.getColumnIndex("indate"));

                        if (c_indate.equals(indate)) {
                            i = 1;
                            switch ((int) time) {
                                case 1:
                                    if (bfaft == 1)
                                        Dia_dbopen.mDB.execSQL("UPDATE dia SET mag=" + dia + " WHERE indate='" + indate + "';");

                                    else if (bfaft == 2)
                                        Dia_dbopen.mDB.execSQL("UPDATE dia SET maf=" + dia + " WHERE indate='" + indate + "';");

                                    break;

                                case 2:
                                    if (bfaft == 1)
                                        Dia_dbopen.mDB.execSQL("UPDATE dia SET lag=" + dia + " WHERE indate='" + indate + "';");

                                    else if (bfaft == 2)
                                        Dia_dbopen.mDB.execSQL("UPDATE dia SET laf=" + dia + " WHERE indate='" + indate + "';");
                                    break;

                                case 3:
                                    if (bfaft == 1)
                                        Dia_dbopen.mDB.execSQL("UPDATE dia SET dag=" + dia + " WHERE indate='" + indate + "';");

                                    else if (bfaft == 2)
                                        Dia_dbopen.mDB.execSQL("UPDATE dia SET daf=" + dia + " WHERE indate='" + indate + "';");
                                    break;
                            }
                        }
                    }
                    if (i == 0) {
                        num = 1;
                    }
                }

                //---------------data가 없을 경우
                if (num == 1) {
                    switch ((int) time) {
                        case 1:
                            if (bfaft == 1) {
                                contentValues.put(Dia_db.CreateDB.indate, indate);
                                contentValues.put(Dia_db.CreateDB.date, date);
                                contentValues.put(Dia_db.CreateDB.mag, dia);

                                dbOpenHelper.insert(contentValues);
                            } else if (bfaft == 2) {
                                contentValues.put(Dia_db.CreateDB.indate, indate);
                                contentValues.put(Dia_db.CreateDB.date, date);
                                contentValues.put(Dia_db.CreateDB.maf, dia);

                                dbOpenHelper.insert(contentValues);
                            }
                            break;

                        case 2:
                            if (bfaft == 1) {
                                contentValues.put(Dia_db.CreateDB.indate, indate);
                                contentValues.put(Dia_db.CreateDB.date, date);
                                contentValues.put(Dia_db.CreateDB.lag, dia);

                                dbOpenHelper.insert(contentValues);
                            } else if (bfaft == 2) {
                                contentValues.put(Dia_db.CreateDB.indate, indate);
                                contentValues.put(Dia_db.CreateDB.date, date);
                                contentValues.put(Dia_db.CreateDB.laf, dia);

                                dbOpenHelper.insert(contentValues);
                            }
                            break;

                        case 3:
                            if (bfaft == 1) {
                                contentValues.put(Dia_db.CreateDB.indate, indate);
                                contentValues.put(Dia_db.CreateDB.date, date);
                                contentValues.put(Dia_db.CreateDB.dag, dia);

                                dbOpenHelper.insert(contentValues);
                            } else if (bfaft == 2) {
                                contentValues.put(Dia_db.CreateDB.indate, indate);
                                contentValues.put(Dia_db.CreateDB.date, date);
                                contentValues.put(Dia_db.CreateDB.daf, dia);

                                dbOpenHelper.insert(contentValues);
                            }
                            break;
                    }

                }
                data.setText("");
                showDatabase();
            }
        });


        //-------------날짜 클릭
        Bdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Dia_popup.class);
                startActivityForResult(intent, 0);
            }
        });

    }

    //--------날짜 입력 받음

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Dia_Act.super.onActivityResult(requestCode, resultCode, data);
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

    //---------db 데이터 리스트 출력
    public void showDatabase() {
        String[] list_date = new String[10000];
        String[] mag = new String[10000];
        String[] maf = new String[10000];
        String[] dag = new String[10000];
        String[] daf = new String[10000];
        String[] lag = new String[10000];
        String[] laf = new String[10000];

        Cursor cursor = dbOpenHelper.sortColumn();
        dia_adapter.clear();

        int num = 0;

        while (cursor.moveToNext()) {
            list_date[num] = cursor.getString(cursor.getColumnIndex("date"));
            mag[num] = cursor.getString(cursor.getColumnIndex("mag"));
            maf[num] = cursor.getString(cursor.getColumnIndex("maf"));
            lag[num] = cursor.getString(cursor.getColumnIndex("lag"));
            laf[num] = cursor.getString(cursor.getColumnIndex("laf"));
            dag[num] = cursor.getString(cursor.getColumnIndex("dag"));
            daf[num] = cursor.getString(cursor.getColumnIndex("daf"));

            num++;

        }
        for (int i = 0; i < num; i++) {
            dia_adapter.addItem(list_date[i], mag[i], maf[i], lag[i], laf[i], dag[i], daf[i]);
        }
        dia_adapter.notifyDataSetChanged();
        listview.setAdapter(dia_adapter);
    }

    public void alldlt(String date) {
        dbOpenHelper.delete(date);
    }

    @Override
    public void onBackPressed() {
        ((MainActivity) MainActivity.context).showMain();
        super.onBackPressed();
    }

}
