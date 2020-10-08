package com.example.healthcare;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Me_dbopen {

    private static final String DATABASE_NAME = "mydur.db";
    private static final int DATABASE_VERSION = 1;
    public static SQLiteDatabase mDB;
    private DatabaseHelper mDBHelper;
    private Context mCtx;

    private class DatabaseHelper extends SQLiteOpenHelper {

        public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(Me_db.CreateDB._CREATE0);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + Me_db.CreateDB.TABLENAME);
            onCreate(db);
        }
    }

    public Me_dbopen(Context context) {
        this.mCtx = context;
    }

    public Me_dbopen open() throws SQLException {
        mDBHelper = new DatabaseHelper(mCtx, DATABASE_NAME, null, DATABASE_VERSION);
        mDB = mDBHelper.getWritableDatabase();
        return this;
    }

    public void create() {
        mDBHelper.onCreate(mDB);
    }

    public void close() {
        mDB.close();
    }

    // Insert DB
    public long insertColumn(String item_seq, String item_name, String entp_name, String item_image, String class_name) {
        ContentValues values = new ContentValues();
        values.put(Me_db.CreateDB.ITEM_SEQ, item_seq);
        values.put(Me_db.CreateDB.ITEM_NAME, item_name);
        values.put(Me_db.CreateDB.ENTP_NAME, entp_name);
        values.put(Me_db.CreateDB.ITEM_IMAGE, item_image);
        values.put(Me_db.CreateDB.CLASS_NAME, class_name);
        return mDB.insert(Me_db.CreateDB.TABLENAME, null, values);
    }

    public int overlap(String item_name) {
        Cursor cursor = mDB.rawQuery("SELECT * FROM mydur WHERE ITEM_NAME='" + item_name + "';", null);
        cursor.moveToFirst();

        if (cursor.getCount() == 0)
            return 0;   //중복 없을시
        else return 1;   //중복시

    }

    // Delete DB
    public void deleteColumn(String name) {
        mDB.execSQL("DELETE FROM mydur WHERE ITEM_NAME='" + name + "';");
    }

    // Select DB
    public Cursor selectColumns() {
        return mDB.query(Me_db.CreateDB.TABLENAME, null, null, null, null, null, null);
    }

    // sort by column
    public Cursor sortColumn() {
        Cursor c = mDB.rawQuery("SELECT * FROM mydur ", null);
        return c;
    }

}