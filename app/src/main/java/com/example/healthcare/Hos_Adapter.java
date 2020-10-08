package com.example.healthcare;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class Hos_Adapter extends BaseAdapter {

    private ArrayList<Hos_item> hos_items = new ArrayList<>();

    @Override
    public int getCount() {
        return hos_items.size();
    }

    @Override
    public Object getItem(int position) {
        return hos_items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ArrayList<String> dur_list = ((Hos_Act) Hos_Act.context).dur_list;

        final Context context = parent.getContext();

        /* 'listview_custom' Layout을 inflate하여 convertView 참조 획득 */
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.hos_item, parent, false);
        }

        /* 'listview_custom'에 정의된 위젯에 대한 참조 획득 */
        final ImageView iv = (ImageView) convertView.findViewById(R.id.image);
        final TextView tv1 = (TextView) convertView.findViewById(R.id.itemname);
        TextView tv2 = (TextView) convertView.findViewById(R.id.entp);
        TextView tv3 = (TextView) convertView.findViewById(R.id.content);
        Button dltb = (Button) convertView.findViewById(R.id.dltb);
        LinearLayout hos_item = (LinearLayout) convertView.findViewById(R.id.hos_item);

        /* 각 리스트에 뿌려줄 아이템을 받아오는데 mMyItem 재활용 */
        final Hos_item myItem = (Hos_item) getItem(position);

        /* 각 위젯에 세팅된 아이템을 뿌려준다 */
        iv.setImageBitmap(myItem.getIcon());
        tv1.setText(myItem.getName());
        tv2.setText(myItem.getEntp());
        tv3.setText(myItem.getContents());

        ((LinearLayout) hos_item.findViewById(R.id.hos_item)).setBackgroundResource(R.drawable.dur_box);
        ((TextView) tv1.findViewById(R.id.itemname)).setTextColor(Color.parseColor("#4A4A4A"));
        ((Button) dltb.findViewById(R.id.dltb)).setBackgroundResource(R.drawable.circle_button);
        ((Button) dltb.findViewById(R.id.dltb)).setTextColor(Color.parseColor("#2B2B2B"));


        for (int i = 0; i < ((Hos_Act) Hos_Act.context).dur_list.size(); i++) {
            if (myItem.getName().equals(dur_list.get(i))) {
                ((LinearLayout) hos_item.findViewById(R.id.hos_item)).setBackgroundResource(R.drawable.hos_dur);
                ((TextView) tv1.findViewById(R.id.itemname)).setTextColor(Color.parseColor("#551D1D"));
                ((Button) dltb.findViewById(R.id.dltb)).setBackgroundResource(R.drawable.hosdur_dtl);
                ((Button) dltb.findViewById(R.id.dltb)).setTextColor(Color.parseColor("#FFFFFF"));
            }
        }


        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Hos_popup.class);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                myItem.getIcon().compress(Bitmap.CompressFormat.JPEG, 100, stream);
                byte[] byteArray = stream.toByteArray();

                intent.putExtra("image", byteArray);

                ((Activity) context).startActivity(intent);
            }

        });

        dltb.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                ((Hos_Act) Hos_Act.context).delete(myItem.getName());

            }
        });

        return convertView;
    }

    public void clear() {
        hos_items.clear();
    }


    public void addItem(Bitmap img, String tv1, String tv2, String tv3) {
        Hos_item item = new Hos_item();

        item.setIcon(img);
        item.setName(tv1);
        item.setEntp(tv2);
        item.setContents(tv3);

        hos_items.add(item);
    }
}
