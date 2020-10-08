package com.example.healthcare;

import android.graphics.Bitmap;

public class Hos_item {
    private Bitmap image;
    private String itemname;
    private String entp;
    private String content;


    public Bitmap getIcon() {
        return image;
    }

    public void setIcon(Bitmap im) {
        this.image = im;
    }

    public String getName() {
        return itemname;
    }

    public void setName(String name) {
        this.itemname = name;
    }

    public String getEntp() {
        return entp;
    }

    public void setEntp(String entp) {
        this.entp = entp;
    }

    public String getContents() {
        return content;
    }

    public void setContents(String contents) {
        this.content = contents;
    }

}
