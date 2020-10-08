package com.example.healthcare;

import android.provider.BaseColumns;

public class Obe_db {
    public static final class CreateDB implements BaseColumns {
        public static final String indate = "indate";
        public static final String date = "date";
        public static final String cm = "cm";
        public static final String kg = "kg";
        public static final String bmi = "bmi";

        public static final String TABLENAME = "obe";
        public static final String _CREATE0 = "create table if not exists " + TABLENAME + "("
                + indate + " text , "
                + date + " text , "
                + cm + " text ,"
                + kg + " text ,"
                + bmi + " text );";
    }
}
