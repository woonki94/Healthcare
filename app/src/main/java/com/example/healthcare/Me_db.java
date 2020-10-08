package com.example.healthcare;

import android.provider.BaseColumns;

public class Me_db {
    public static final class CreateDB implements BaseColumns {
        public static final String ITEM_SEQ = "item_seq";
        public static final String ITEM_NAME = "item_name";
        public static final String ENTP_NAME = "entp_name";
        public static final String ITEM_IMAGE = "item_image";
        public static final String CLASS_NAME = "class_name";


        public static final String TABLENAME = "mydur";
        public static final String _CREATE0 = "create table if not exists " + TABLENAME + "("
                + ITEM_SEQ + " text not null , "
                + ITEM_NAME + " text not null , "
                + ENTP_NAME + " text not null,"
                + ITEM_IMAGE + " text not null,"
                + CLASS_NAME + " text not null);";
    }
}
