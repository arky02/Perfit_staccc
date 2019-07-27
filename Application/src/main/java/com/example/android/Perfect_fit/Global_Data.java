package com.example.android.Perfect_fit;

import android.app.Application;

public class Global_Data extends Application {
    private String name;
    private int height;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
