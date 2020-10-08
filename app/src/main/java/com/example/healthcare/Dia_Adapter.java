package com.example.healthcare;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Dia_Adapter extends BaseAdapter {
    ArrayList<Dia_item> dia_items = new ArrayList<>();

    TextView list_day;
    TextView mag;
    TextView maf;
    TextView lag;
    TextView laf;
    TextView dag;
    TextView daf;

    @Override
    public int getCount() {
        return dia_items.size();
    }

    @Override
    public Object getItem(int position) {
        return dia_items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        final Context context = parent.getContext();

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.dia_item, parent, false);
        }

        list_day = (TextView) convertView.findViewById(R.id.list_day);
        mag = (TextView) convertView.findViewById(R.id.list_mag);
        maf = (TextView) convertView.findViewById(R.id.list_maf);
        lag = (TextView) convertView.findViewById(R.id.list_lag);
        laf = (TextView) convertView.findViewById(R.id.list_laf);
        dag = (TextView) convertView.findViewById(R.id.list_dag);
        daf = (TextView) convertView.findViewById(R.id.list_daf);


        final Dia_item dia_item = (Dia_item) getItem(position);

        list_day.setText(dia_item.getDay());
        mag.setText(dia_item.getMag());
        maf.setText(dia_item.getMaf());
        lag.setText(dia_item.getLag());
        laf.setText(dia_item.getLaf());
        dag.setText(dia_item.getDag());
        daf.setText(dia_item.getDaf());

        //식전 당뇨 수치별 색 변화
        try {
            if (Integer.parseInt((String) mag.getText()) > 125)    //당뇨
                ((TextView) mag.findViewById(R.id.list_mag)).setTextColor(Color.parseColor("#FF3030"));
            if (Integer.parseInt((String) mag.getText()) >= 100 && 126 > Integer.parseInt((String) mag.getText())) //당뇨 전단계
                ((TextView) mag.findViewById(R.id.list_mag)).setTextColor(Color.parseColor("#FFC125"));
            if (Integer.parseInt((String) mag.getText()) < 100 && Integer.parseInt((String) mag.getText()) > 70)   //정상
                ((TextView) mag.findViewById(R.id.list_mag)).setTextColor(Color.parseColor("#32CD32"));
            if (Integer.parseInt((String) mag.getText()) <= 70)   //저혈당
                ((TextView) mag.findViewById(R.id.list_mag)).setTextColor(Color.parseColor("#00BFFF"));
        } catch (NumberFormatException e) {
        }

        try {
            if (Integer.parseInt((String) lag.getText()) > 125)
                ((TextView) lag.findViewById(R.id.list_lag)).setTextColor(Color.parseColor("#FF3030"));
            if (Integer.parseInt((String) lag.getText()) >= 100 && 126 > Integer.parseInt((String) lag.getText()))
                ((TextView) lag.findViewById(R.id.list_lag)).setTextColor(Color.parseColor("#FFC125"));
            if (Integer.parseInt((String) lag.getText()) < 100 && Integer.parseInt((String) lag.getText()) > 70)
                ((TextView) lag.findViewById(R.id.list_lag)).setTextColor(Color.parseColor("#32CD32"));
            if (Integer.parseInt((String) lag.getText()) <= 70)   //저혈당
                ((TextView) lag.findViewById(R.id.list_lag)).setTextColor(Color.parseColor("#00BFFF"));
        } catch (NumberFormatException e) {
        }

        try {
            if (Integer.parseInt((String) dag.getText()) > 125)
                ((TextView) dag.findViewById(R.id.list_dag)).setTextColor(Color.parseColor("#FF3030"));
            if (Integer.parseInt((String) dag.getText()) >= 100 && 126 > Integer.parseInt((String) dag.getText()))
                ((TextView) dag.findViewById(R.id.list_dag)).setTextColor(Color.parseColor("#FFC125"));
            if (Integer.parseInt((String) dag.getText()) < 100 && Integer.parseInt((String) dag.getText()) > 70)
                ((TextView) dag.findViewById(R.id.list_dag)).setTextColor(Color.parseColor("#32CD32"));
            if (Integer.parseInt((String) dag.getText()) <= 70)   //저혈당
                ((TextView) dag.findViewById(R.id.list_dag)).setTextColor(Color.parseColor("#00BFFF"));
        } catch (NumberFormatException e) {
        }

        //식전 당뇨 수치별 색 변화
        try {
            if (Integer.parseInt((String) maf.getText()) > 125)
                ((TextView) maf.findViewById(R.id.list_maf)).setTextColor(Color.parseColor("#FF3030"));
            if (Integer.parseInt((String) maf.getText()) >= 100 && 126 > Integer.parseInt((String) maf.getText()))
                ((TextView) maf.findViewById(R.id.list_maf)).setTextColor(Color.parseColor("#FFC125"));
            if (Integer.parseInt((String) maf.getText()) < 100 && Integer.parseInt((String) maf.getText()) > 70)
                ((TextView) maf.findViewById(R.id.list_maf)).setTextColor(Color.parseColor("#32CD32"));
            if (Integer.parseInt((String) maf.getText()) <= 70)   //저혈당
                ((TextView) maf.findViewById(R.id.list_maf)).setTextColor(Color.parseColor("#00BFFF"));
        } catch (NumberFormatException e) {
        }

        try {
            if (Integer.parseInt((String) laf.getText()) > 125)
                ((TextView) laf.findViewById(R.id.list_laf)).setTextColor(Color.parseColor("#FF3030"));
            if (Integer.parseInt((String) laf.getText()) >= 100 && 126 > Integer.parseInt((String) laf.getText()))
                ((TextView) laf.findViewById(R.id.list_laf)).setTextColor(Color.parseColor("#FFC125"));
            if (Integer.parseInt((String) laf.getText()) < 100 && Integer.parseInt((String) laf.getText()) > 70)
                ((TextView) laf.findViewById(R.id.list_laf)).setTextColor(Color.parseColor("#32CD32"));
            if (Integer.parseInt((String) laf.getText()) <= 70)   //저혈당
                ((TextView) laf.findViewById(R.id.list_laf)).setTextColor(Color.parseColor("#00BFFF"));
        } catch (NumberFormatException e) {
        }

        try {
            if (Integer.parseInt((String) daf.getText()) > 125)
                ((TextView) daf.findViewById(R.id.list_daf)).setTextColor(Color.parseColor("#FF3030"));
            if (Integer.parseInt((String) daf.getText()) >= 100 && 126 > Integer.parseInt((String) daf.getText()))
                ((TextView) daf.findViewById(R.id.list_daf)).setTextColor(Color.parseColor("#FFC125"));
            if (Integer.parseInt((String) daf.getText()) < 100 && Integer.parseInt((String) daf.getText()) > 70)
                ((TextView) daf.findViewById(R.id.list_daf)).setTextColor(Color.parseColor("#32CD32"));
            if (Integer.parseInt((String) daf.getText()) <= 70)   //저혈당
                ((TextView) daf.findViewById(R.id.list_daf)).setTextColor(Color.parseColor("#00BFFF"));
        } catch (NumberFormatException e) {
        }

        //----------삭제

        TextView.OnLongClickListener onLongClickListener = new TextView.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(Dia_Act.context);

                switch (v.getId()) {
                    case R.id.list_mag:
                        dialog.setTitle("삭제")
                                .setMessage("\n해당 혈당값을 삭제 하시겠습니까?\n")
                                .setPositiveButton("네", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Dia_dbopen.mDB.execSQL("UPDATE dia SET mag=" + null + " WHERE date='" + dia_item.getDay() + "';");

                                        ((Dia_Act) Dia_Act.context).showDatabase();
                                        Toast.makeText(Dia_Act.context, "삭제하였습니다", Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                    }
                                })
                                .create()
                                .show();
                        break;

                    case R.id.list_maf:
                        dialog.setTitle("삭제")
                                .setMessage("\n해당 혈당값을 삭제 하시겠습니까?\n")
                                .setPositiveButton("네", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Dia_dbopen.mDB.execSQL("UPDATE dia SET maf=" + null + " WHERE date='" + dia_item.getDay() + "';");

                                        ((Dia_Act) Dia_Act.context).showDatabase();
                                        Toast.makeText(Dia_Act.context, "삭제하였습니다", Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                    }
                                })
                                .create()
                                .show();
                        break;

                    case R.id.list_lag:
                        dialog.setTitle("삭제")
                                .setMessage("\n해당 혈당값을 삭제 하시겠습니까?\n")
                                .setPositiveButton("네", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Dia_dbopen.mDB.execSQL("UPDATE dia SET lag=" + null + " WHERE date='" + dia_item.getDay() + "';");

                                        ((Dia_Act) Dia_Act.context).showDatabase();
                                        Toast.makeText(Dia_Act.context, "삭제하였습니다", Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                    }
                                })
                                .create()
                                .show();
                        break;

                    case R.id.list_laf:
                        dialog.setTitle("삭제")
                                .setMessage("\n해당 혈당값을 삭제 하시겠습니까?\n")
                                .setPositiveButton("네", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Dia_dbopen.mDB.execSQL("UPDATE dia SET laf=" + null + " WHERE date='" + dia_item.getDay() + "';");

                                        ((Dia_Act) Dia_Act.context).showDatabase();
                                        Toast.makeText(Dia_Act.context, "삭제하였습니다", Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                    }
                                })
                                .create()
                                .show();
                        break;

                    case R.id.list_dag:
                        dialog.setTitle("삭제")
                                .setMessage("\n해당 혈당값을 삭제 하시겠습니까?\n")
                                .setPositiveButton("네", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Dia_dbopen.mDB.execSQL("UPDATE dia SET dag=" + null + " WHERE date='" + dia_item.getDay() + "';");

                                        ((Dia_Act) Dia_Act.context).showDatabase();
                                        Toast.makeText(Dia_Act.context, "삭제하였습니다", Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                    }
                                })
                                .create()
                                .show();
                        break;

                    case R.id.list_daf:
                        dialog.setTitle("삭제")
                                .setMessage("\n해당 혈당값을 삭제 하시겠습니까?\n")
                                .setPositiveButton("네", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Dia_dbopen.mDB.execSQL("UPDATE dia SET daf=" + null + " WHERE date='" + dia_item.getDay() + "';");

                                        ((Dia_Act) Dia_Act.context).showDatabase();
                                        Toast.makeText(Dia_Act.context, "삭제하였습니다", Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                    }
                                })
                                .create()
                                .show();
                        break;
                }

                return false;
            }
        };

        TextView.OnClickListener onClickListener = new TextView.OnClickListener() {

            @Override
            public void onClick(View v) {
                String[] day;
                switch (v.getId()) {
                    case R.id.list_mag:
                        ((Dia_Act) Dia_Act.context).Bdate.setText(dia_item.getDay());
                        day = dia_item.getDay().split("\\s/\\s");
                        ((Dia_Act) Dia_Act.context).indate = dia_item.getDay().replace(" / ", "");
                        ((Dia_Act) Dia_Act.context).year = day[0];
                        ((Dia_Act) Dia_Act.context).month = day[1];
                        ((Dia_Act) Dia_Act.context).day = day[2];
                        ((Dia_Act) Dia_Act.context).mrn.callOnClick();
                        ((Dia_Act) Dia_Act.context).bfor.callOnClick();
                        ((Dia_Act) Dia_Act.context).bfor.setChecked(true);
                        ((Dia_Act) Dia_Act.context).data.setText(dia_item.getMag());

                        break;

                    case R.id.list_maf:
                        ((Dia_Act) Dia_Act.context).Bdate.setText(dia_item.getDay());
                        day = dia_item.getDay().split("\\s/\\s");
                        ((Dia_Act) Dia_Act.context).indate = dia_item.getDay().replace(" / ", "");
                        ((Dia_Act) Dia_Act.context).year = day[0];
                        ((Dia_Act) Dia_Act.context).month = day[1];
                        ((Dia_Act) Dia_Act.context).day = day[2];
                        ((Dia_Act) Dia_Act.context).mrn.callOnClick();
                        ((Dia_Act) Dia_Act.context).aft.callOnClick();
                        ((Dia_Act) Dia_Act.context).aft.setChecked(true);
                        ((Dia_Act) Dia_Act.context).data.setText(dia_item.getMaf());

                        break;

                    case R.id.list_lag:
                        ((Dia_Act) Dia_Act.context).Bdate.setText(dia_item.getDay());
                        day = dia_item.getDay().split("\\s/\\s");
                        ((Dia_Act) Dia_Act.context).indate = dia_item.getDay().replace(" / ", "");
                        ((Dia_Act) Dia_Act.context).year = day[0];
                        ((Dia_Act) Dia_Act.context).month = day[1];
                        ((Dia_Act) Dia_Act.context).day = day[2];
                        ((Dia_Act) Dia_Act.context).lnch.callOnClick();
                        ((Dia_Act) Dia_Act.context).bfor.callOnClick();
                        ((Dia_Act) Dia_Act.context).bfor.setChecked(true);
                        ((Dia_Act) Dia_Act.context).data.setText(dia_item.getLag());

                        break;

                    case R.id.list_laf:
                        ((Dia_Act) Dia_Act.context).Bdate.setText(dia_item.getDay());
                        day = dia_item.getDay().split("\\s/\\s");
                        ((Dia_Act) Dia_Act.context).indate = dia_item.getDay().replace(" / ", "");
                        ((Dia_Act) Dia_Act.context).year = day[0];
                        ((Dia_Act) Dia_Act.context).month = day[1];
                        ((Dia_Act) Dia_Act.context).day = day[2];
                        ((Dia_Act) Dia_Act.context).lnch.callOnClick();
                        ((Dia_Act) Dia_Act.context).aft.callOnClick();
                        ((Dia_Act) Dia_Act.context).aft.setChecked(true);
                        ((Dia_Act) Dia_Act.context).data.setText(dia_item.getLaf());

                        break;

                    case R.id.list_dag:
                        ((Dia_Act) Dia_Act.context).Bdate.setText(dia_item.getDay());
                        day = dia_item.getDay().split("\\s/\\s");
                        ((Dia_Act) Dia_Act.context).indate = dia_item.getDay().replace(" / ", "");
                        ((Dia_Act) Dia_Act.context).year = day[0];
                        ((Dia_Act) Dia_Act.context).month = day[1];
                        ((Dia_Act) Dia_Act.context).day = day[2];
                        ((Dia_Act) Dia_Act.context).dnnr.callOnClick();
                        ((Dia_Act) Dia_Act.context).bfor.callOnClick();
                        ((Dia_Act) Dia_Act.context).bfor.setChecked(true);
                        ((Dia_Act) Dia_Act.context).data.setText(dia_item.getDag());

                        break;

                    case R.id.list_daf:
                        ((Dia_Act) Dia_Act.context).Bdate.setText(dia_item.getDay());
                        day = dia_item.getDay().split("\\s/\\s");
                        ((Dia_Act) Dia_Act.context).indate = dia_item.getDay().replace(" / ", "");
                        ((Dia_Act) Dia_Act.context).year = day[0];
                        ((Dia_Act) Dia_Act.context).month = day[1];
                        ((Dia_Act) Dia_Act.context).day = day[2];
                        ((Dia_Act) Dia_Act.context).dnnr.callOnClick();
                        ((Dia_Act) Dia_Act.context).aft.callOnClick();
                        ((Dia_Act) Dia_Act.context).aft.setChecked(true);
                        ((Dia_Act) Dia_Act.context).data.setText(dia_item.getDaf());

                        break;
                }
            }
        };
        mag.setOnClickListener(onClickListener);
        maf.setOnClickListener(onClickListener);
        lag.setOnClickListener(onClickListener);
        laf.setOnClickListener(onClickListener);
        dag.setOnClickListener(onClickListener);
        daf.setOnClickListener(onClickListener);

        mag.setOnLongClickListener(onLongClickListener);
        maf.setOnLongClickListener(onLongClickListener);
        lag.setOnLongClickListener(onLongClickListener);
        laf.setOnLongClickListener(onLongClickListener);
        dag.setOnLongClickListener(onLongClickListener);
        daf.setOnLongClickListener(onLongClickListener);

        //------------데이터 없을시 삭제
        if (TextUtils.isEmpty(mag.getText()) && TextUtils.isEmpty(maf.getText()) && TextUtils.isEmpty(lag.getText()) && TextUtils.isEmpty(laf.getText()) && TextUtils.isEmpty(dag.getText()) && TextUtils.isEmpty(daf.getText())) {
            ((Dia_Act) Dia_Act.context).alldlt(list_day.getText().toString());
        }


        return convertView;
    }

    public void clear() {
        dia_items.clear();
    }

    public void addItem(String list_day, String mag, String maf, String lag, String laf, String dag, String daf) {
        Dia_item item = new Dia_item();
        item.setDay(list_day);
        item.setMag(mag);
        item.setMaf(maf);
        item.setLag(lag);
        item.setLaf(laf);
        item.setDag(dag);
        item.setDaf(daf);

        dia_items.add(item);
    }
}
