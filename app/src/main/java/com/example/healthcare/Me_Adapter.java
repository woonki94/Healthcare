package com.example.healthcare;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class Me_Adapter extends BaseAdapter implements View.OnTouchListener {
    private ArrayList<Me_item> me_items = new ArrayList<>();

    TextView name;
    RelativeLayout name1;
    Button dlt;

    @Override
    public int getCount() {
        return me_items.size();
    }

    @Override
    public Object getItem(int position) {
        return me_items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ArrayList<String> dur_list = ((Me_Act) Me_Act.context).dur_list;

        final Context context = parent.getContext();

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.me_item, parent, false);
        }

        name = (TextView) convertView.findViewById(R.id.click_name);
        name1 = (RelativeLayout) convertView.findViewById(R.id.rll);
        dlt = (Button) convertView.findViewById(R.id.dlt);


        final Me_item me_item = (Me_item) getItem(position);
        name.setText(me_item.getName());

        dlt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Me_Act) Me_Act.context).delete(me_item.getName());
                ((Me_Act) Me_Act.context).showDatabase();
            }
        });

        ((TextView) name.findViewById(R.id.click_name)).setTextColor(Color.parseColor("#BC000000"));
        ((RelativeLayout) name1.findViewById(R.id.rll)).setBackgroundResource(R.drawable.me_click);


        for (int i = 0; i < ((Me_Act) Me_Act.context).dur_list.size(); i++) {
            if (name.getText().equals(dur_list.get(i))) {
                ((TextView) name.findViewById(R.id.click_name)).setTextColor(Color.parseColor("#FFFFFFFF"));
                ((RelativeLayout) name1.findViewById(R.id.rll)).setBackgroundResource(R.drawable.dur);
            }
        }

        return convertView;
    }

    public void clear() {
        me_items.clear();
    }

    @Override
    public boolean isEnabled(int position) {
        if (me_items.get(position).equals(null)) return true;
        return false;
    }

    public void addItem(String name) {
        Me_item item = new Me_item();
        item.setName(name);

        me_items.add(item);
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {

        return false;
    }
}
