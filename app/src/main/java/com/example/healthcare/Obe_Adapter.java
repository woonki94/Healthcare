package com.example.healthcare;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class Obe_Adapter extends BaseAdapter {
    ArrayList<Obe_item> obe_items = new ArrayList<>();

    TextView day;
    TextView kg;
    TextView bmi;
    TextView condition;
    LinearLayout list;

    @Override
    public int getCount() {
        return obe_items.size();
    }

    @Override
    public Object getItem(int position) {
        return obe_items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final Context context = parent.getContext();

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.obe_item, parent, false);
        }

        day = (TextView) convertView.findViewById(R.id.list_oday);
        kg = (TextView) convertView.findViewById(R.id.list_kilo);
        bmi = (TextView) convertView.findViewById(R.id.list_bmi);
        condition = (TextView) convertView.findViewById(R.id.condition);
        list = (LinearLayout) convertView.findViewById(R.id.list_obe);

        final Obe_item obe_item = (Obe_item) getItem(position);

        day.setText(obe_item.getDay().replaceAll(" ", ""));
        kg.setText(obe_item.getKg());
        bmi.setText(obe_item.getBmi());
        condition.setText(obe_item.getCondition());
        ((TextView) condition.findViewById(R.id.condition)).setTextColor(Color.parseColor("#5DA3AF"));

        String c = null;

        if (condition.getText().toString() != null) {
            c = (String) condition.getText();
            if (!c.equals("정상"))
                c = c.substring(0, c.indexOf("\n"));
        }

        if (c.equals("저체중"))
            ((TextView) condition.findViewById(R.id.condition)).setTextColor(Color.parseColor("#5DA3AF"));
        else if (c.equals("정상"))
            ((TextView) condition.findViewById(R.id.condition)).setTextColor(Color.parseColor("#2A6CCF"));
        else if (c.equals("과체중"))
            ((TextView) condition.findViewById(R.id.condition)).setTextColor(Color.parseColor("#4400FF"));
        else if (c.equals("비만"))
            ((TextView) condition.findViewById(R.id.condition)).setTextColor(Color.parseColor("#C6269E"));
        else if (c.equals("고도비만"))
            ((TextView) condition.findViewById(R.id.condition)).setTextColor(Color.parseColor("#EC2D2D"));

        list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] d;
                ((Obe_Act) Obe_Act.context).Bdate.setText(obe_item.getDay());
                d = obe_item.getDay().split("\\s/\\s");
                ((Obe_Act) Obe_Act.context).indate = obe_item.getDay().replace(" / ", "");
                ((Obe_Act) Obe_Act.context).year = d[0];
                ((Obe_Act) Obe_Act.context).month = d[1];
                ((Obe_Act) Obe_Act.context).day = d[2];
                ((Obe_Act) Obe_Act.context).ob_cm.setText(obe_item.getCm());
                ((Obe_Act) Obe_Act.context).ob_kg.setText(obe_item.getKg());
                ((Obe_Act) Obe_Act.context).ob_bmi.setText(obe_item.getBmi());
            }
        });

        list.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(Obe_Act.context);

                dialog.setTitle("삭제")
                        .setMessage("\n 해당 값을 삭제 하시겠습니까?\n")
                        .setPositiveButton("네", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ((Obe_Act) Obe_Act.context).delete(obe_item.getDay());
                            }
                        })
                        .setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .create()
                        .show();

                return false;
            }
        });
        return convertView;
    }

    public void clear() {
        obe_items.clear();
    }

    public void addItem(String day, String cm, String kg, String bmi, String condition) {
        Obe_item item = new Obe_item();
        item.setDay(day);
        item.setCm(cm);
        item.setKg(kg);
        item.setBmi(bmi);
        item.setCondition(condition);

        obe_items.add(item);
    }
}
