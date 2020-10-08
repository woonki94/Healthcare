package com.example.healthcare;

import android.provider.BaseColumns;

public class Dia_db {
    public static final class CreateDB implements BaseColumns {
        public static final String indate = "indate";
        public static final String date = "date";
        public static final String mag = "mag";
        public static final String maf = "maf";
        public static final String lag = "lag";
        public static final String laf = "laf";
        public static final String dag = "dag";
        public static final String daf = "daf";

        public static final String TABLENAME = "dia";
        public static final String _CREATE0 = "create table if not exists " + TABLENAME + "("
                + indate + " text , "
                + date + " text , "
                + mag + " text ,"
                + maf + " text ,"
                + lag + " text , "
                + laf + " text ,"
                + dag + " text ,"
                + daf + " text );";
    }
}
