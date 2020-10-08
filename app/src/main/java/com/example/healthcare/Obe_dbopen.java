package com.example.healthcare;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Obe_dbopen {
    private static final String DATABASE_NAME = "obe.db";
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
            db.execSQL(Obe_db.CreateDB._CREATE0);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + Obe_db.CreateDB.TABLENAME);
            onCreate(db);
        }
    }

    public Obe_dbopen(Context context) {
        this.mCtx = context;
    }

    public Obe_dbopen open() throws SQLException {
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
    public long insert(ContentValues values) {
        return mDB.insert(Obe_db.CreateDB.TABLENAME, null, values);
    }

    public void delete(String day) {
        mDB.execSQL("DELETE FROM obe WHERE date='" + day + "';");
    }

    // Select DB
    public Cursor selectColumns() {
        return mDB.query(Obe_db.CreateDB.TABLENAME, null, null, null, null, null, null);
    }

    // sort by column
    public Cursor sortColumn() {
        Cursor c = mDB.rawQuery("SELECT * FROM obe ORDER BY indate desc", null);
        return c;
    }

}
