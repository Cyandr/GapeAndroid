package com.gape.cyandr.gapeandroid.gape;

import android.app.Application;
import android.os.Handler;

/**
 * Created by cyandr on 2016/11/17.
 */
public class GpeApplication extends Application {
    private String MYAPPTAG;
    private Handler MyAppHandler;
    private String Current_table;

    @Override
    public void onCreate() {
        super.onCreate();

    }

    public Handler getMyAppHandler() {
        return MyAppHandler;
    }

    public void setMyAppHandler(Handler myAppHandler) {
        MyAppHandler = myAppHandler;
    }

    public String getMYAPPTAG() {
        return MYAPPTAG;
    }

    public void setMYAPPTAG(String MYAPPTAG) {
        this.MYAPPTAG = MYAPPTAG;
    }

    public String getCurrent_table() {
        return Current_table;
    }

    public void setCurrent_table(String current_table) {
        Current_table = current_table;
    }
}
