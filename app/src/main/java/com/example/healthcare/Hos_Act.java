package com.example.healthcare;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.TreeSet;

public class Hos_Act extends AppCompatActivity {
    Hos_Adapter hos_adapter = new Hos_Adapter();
    public static Context context;

    private ListView listView;
    private Me_dbopen dbOepnHelper;

    int i;
    String[] image = new String[100];
    Bitmap[] bitmap = new Bitmap[100];
    String[] itemname = new String[100];
    String[] entp = new String[100];
    String[] content = new String[100];

    public ArrayList<String> dur_list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hospital);
        context = this;

        dbOepnHelper = new Me_dbopen(this);
        dbOepnHelper.open();

        listView = (ListView) findViewById(R.id.listView);

        dataSetting();
        dur();
    }

    private void dataSetting() {
        Cursor cursor = dbOepnHelper.sortColumn();
        int num = 0;
        hos_adapter.clear();

        while (cursor.moveToNext()) {
            itemname[num] = cursor.getString(cursor.getColumnIndex("item_name"));
            entp[num] = cursor.getString(cursor.getColumnIndex("entp_name"));
            image[num] = cursor.getString(cursor.getColumnIndex("item_image"));
            content[num] = cursor.getString(cursor.getColumnIndex("class_name"));
            num++;
        }

        for (i = 0; i < num; i++) {
            Runnable r = new data();
            Thread thread = new Thread(r);
            thread.start();
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        for (int j = 0; j < num; j++) {
            hos_adapter.addItem(bitmap[j], itemname[j], entp[j], "\n- " + content[j]);
        }
        hos_adapter.notifyDataSetChanged();

        listView.setAdapter(hos_adapter);
    }

    class data implements Runnable {

        @Override
        public void run() {
            try {
                URL url = new URL(image[i]);

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setDoInput(true);
                conn.connect();

                InputStream is = conn.getInputStream();
                bitmap[i] = BitmapFactory.decodeStream(is);

            } catch (IOException ex) {
            }
        }
    }

    public void delete(String name) {
        dbOepnHelper.deleteColumn(name);
        dataSetting();
        dur();
    }

    @Override
    public void onBackPressed() {
        ((MainActivity) MainActivity.context).showMain();
        super.onBackPressed();
    }

    public void dur() {
        dur_list.clear();
        Runnable r = new Hos_Act.dur_act();
        Thread thread = new Thread(r);
        thread.start();
        try {
            thread.join();
        } catch (Exception e) {

        }
        TreeSet<String> treeSet = new TreeSet<>(dur_list);
        dur_list = new ArrayList<String>(treeSet);

    }

    class dur_act implements Runnable {
        Cursor cursor1 = dbOepnHelper.sortColumn();
        String query = "http://apis.data.go.kr/1470000/DURPrdlstInfoService/getUsjntTabooInfoList?"
                + "&ServiceKey=zEBgYWn%2FPlUqJul4HO36wuTy8s6CUzXf3ax6%2BSZfjEoL5ETRqizEA%2BqhcTtMqnLG7hih4Sg%2B72Jrt5AUHGna9A%3D%3D"
                + "&numOfRows=100"
                + "&itemName=";

        @Override
        public void run() {
            while (cursor1.moveToNext()) {
                String itemname = cursor1.getString(cursor1.getColumnIndex("item_name"));
                int c = cursor1.getColumnIndex("item_name");

                int page = 1;

                final String location = URLEncoder.encode(itemname);
                String j = null;

                try {
                    while (true) {
                        String queryURl = query + location + "&pageNo=" + page;

                        URL url = new URL(queryURl);//문자열로 된 요청 url을 URL 객체로 생성.
                        InputStream is = url.openStream(); //url위치로 입력스트림 연결
                        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                        XmlPullParser xpp = factory.newPullParser();
                        xpp.setInput(new InputStreamReader(is, "UTF-8")); //inputstream 으로부터 xml 입력받기

                        String tag;

                        xpp.next();
                        int eventType = xpp.getEventType();

                        while (eventType != XmlPullParser.END_DOCUMENT) {
                            switch (eventType) {
                                case XmlPullParser.START_DOCUMENT:

                                    break;

                                case XmlPullParser.START_TAG:
                                    tag = xpp.getName();//테그 이름 얻어오기

                                    if (tag.equals("totalCount")) {
                                        xpp.next();
                                        j = xpp.getText();
                                    }

                                    if (tag.equals("item")) ;

                                    if (tag.equals("MIXTURE_ITEM_NAME")) {
                                        xpp.next();

                                        Cursor cursor2 = dbOepnHelper.selectColumns();
                                        while (cursor2.moveToNext()) {
                                            String dbdata = cursor2.getString(c);

                                            if (dbdata.equals(xpp.getText())) {
                                                dur_list.add(itemname);
                                                dur_list.add(xpp.getText());

                                            }
                                        }

                                    }
                                    break;

                                case XmlPullParser.TEXT:
                                    break;

                                case XmlPullParser.END_TAG:

                                    tag = xpp.getName(); //테그 이름 얻어오기

                                    break;
                            }
                            eventType = xpp.next();
                        }
                        page++;
                        if (page > Integer.parseInt(j) / 100 + 1) break;
                    }
                } catch (Exception e) {
                    // TODO Auto-generated catch blocke.printStackTrace();
                }
            }
        }
    }

}