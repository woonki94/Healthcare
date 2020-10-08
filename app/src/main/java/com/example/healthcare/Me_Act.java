package com.example.healthcare;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeSet;


public class Me_Act extends AppCompatActivity {
    Me_Adapter me_adapter = new Me_Adapter();

    public static Context context;

    String[] itemname = new String[100];


    String select;
    EditText edit;
    Button insert;


    String key = "wb%2B%2BocEfk4Cf5g%2FhNF%2ByCWbAPdFcTMOBhGeUsygnNtb09p220hEP0emi9D8SHr0mQByFhY3S8qZAG0NNxha0ww%3D%3D";
    String queryUrl = "http://apis.data.go.kr/1470000/MdcinGrnIdntfcInfoService/getMdcinGrnIdntfcInfoList?"//요청 URL
            + "&ServiceKey=" + key
            + "&numOfRows=100";

    //----------------------------listview
    ArrayList<HashMap<String, String>> arraylist = new ArrayList<>();
    public ArrayList<String> dur_list = new ArrayList<String>();

    HashMap<String, String> item;

    //----------------------------db
    Me_dbopen dbOpenHelper;

    //-------------db listview
    private ArrayAdapter<String> arrayAdapter;
    ListView listview;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.medicine);
        context = this;

        edit = (EditText) findViewById(R.id.edit);
        insert = (Button) findViewById(R.id.button);

        final InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        dbOpenHelper = new Me_dbopen(this);
        dbOpenHelper.open();
        dbOpenHelper.create();

        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        listview = (ListView) findViewById(R.id.list);

        showDatabase();
        dur();

        edit.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == event.KEYCODE_ENTER) {
                    insert.callOnClick();
                    return true;
                }
                return false;
            }
        });

        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imm.hideSoftInputFromWindow(edit.getWindowToken(), 0);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        getXmlData();

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                setlistviewtext(arraylist);
                            }
                        });
                    }
                }).start();
            }
        });
    }

    public void setlistviewtext(ArrayList<HashMap<String, String>> arraylist) {
        //약 품목명과 업체명 두 가지 데이터 저장
        SimpleAdapter simpleadapter = new SimpleAdapter(this, arraylist, android.R.layout.simple_list_item_2,
                new String[]{"item1", "item2"}, new int[]{android.R.id.text1, android.R.id.text2});
        ListView list = (ListView) findViewById(R.id.listview);
        list.setAdapter(simpleadapter);
        list.setOnItemClickListener(totaldataclick);
        simpleadapter.notifyDataSetChanged();
    }

    //api xml을 통해 파싱
    public void getXmlData() {
        String str = edit.getText().toString();//EditText에 작성된 Text얻어오기
        String location = URLEncoder.encode(str);

        item = new HashMap<String, String>();
        arraylist.clear();

        try {
            String queryUrle = queryUrl + "&item_name=" + location;    //api 페이지

            URL url = new URL(queryUrle);//문자열로 된 요청 url을 URL 객체로 생성.
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

                        if (tag.equals("item")) ;
                        else if (tag.equals("ITEM_NAME")) {
                            xpp.next();
                            item = new HashMap<String, String>();
                            item.put("item1", xpp.getText());

                        } else if (tag.equals("ENTP_NAME")) {//ITEM_NAME에서 입력받은 값이 동일시
                            xpp.next();
                            item.put("item2", xpp.getText());
                            arraylist.add(item);
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
        } catch (Exception e) {
            // TODO Auto-generated catch blocke.printStackTrace();
        }
    }//getXmlData

    //------------TOTAL_listview 클릭시 데이터 입력
    public AdapterView.OnItemClickListener totaldataclick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            HashMap<String, String> tempDate = arraylist.get(position);

            if (dbOpenHelper.overlap(tempDate.get("item1")) == 1) {//중복시
                Toast.makeText(Me_Act.this, "이미 약물을 선택하셨습니다.", Toast.LENGTH_SHORT).show();
            } else {
                select = null;
                select = tempDate.get("item1");
                Runnable r = new insertdb();
                Thread thread = new Thread(r);
                thread.start();
                try {
                    thread.join();
                } catch (Exception e) {
                }
                showDatabase();
                dur();
            }
        }
    };

    class insertdb implements Runnable {
        final String[] data = new String[5];
        String location = URLEncoder.encode(select);
        final String queryUrle = queryUrl + "&item_name=" + location;

        @Override
        public void run() {

            try {
                URL url = new URL(queryUrle);//문자열로 된 요청 url을 URL 객체로 생성.
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

                            if (tag.equals("item")) ;
                            else if (tag.equals("ITEM_SEQ")) {
                                xpp.next();
                                data[0] = xpp.getText();
                            } else if (tag.equals("ITEM_NAME")) {
                                xpp.next();
                                data[1] = xpp.getText();
                            } else if (tag.equals("ENTP_NAME")) {
                                xpp.next();
                                data[2] = xpp.getText();
                            } else if (tag.equals("ITEM_IMAGE")) {
                                xpp.next();
                                data[3] = xpp.getText();
                            } else if (tag.equals("CLASS_NAME")) {
                                xpp.next();
                                data[4] = xpp.getText();
                                dbOpenHelper.insertColumn(data[0], data[1], data[2], data[3], data[4]);
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
            } catch (Exception e) {
                // TODO Auto-generated catch blocke.printStackTrace();
            }
        }
    }

    public void showDatabase() {
        Cursor cursor = dbOpenHelper.sortColumn();
        me_adapter.clear();

        int num = 0;

        while (cursor.moveToNext()) {
            itemname[num] = cursor.getString(cursor.getColumnIndex("item_name"));
            num++;
        }

        for (int i = 0; i < num; i++) {
            me_adapter.addItem(itemname[i]);
        }

        me_adapter.notifyDataSetChanged();
        listview.setAdapter(me_adapter);
    }

    public void dur() {
        dur_list.clear();
        Runnable r = new dur_act();
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
        Cursor cursor1 = dbOpenHelper.sortColumn();
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

                                        Cursor cursor2 = dbOpenHelper.selectColumns();
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

    public void delete(String name) {
        dbOpenHelper.deleteColumn(name);
        dur();
    }

    @Override
    public void onBackPressed() {
        ((MainActivity) MainActivity.context).showMain();
        super.onBackPressed();
    }

}//MainActivity

