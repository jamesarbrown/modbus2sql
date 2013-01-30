//////////////////////////////////////////////////////////////////////////
//com.enrogen.modbus2sql.Modbus2SQLApp
//2010 - James A R Brown
//Released under GPL V2
//////////////////////////////////////////////////////////////////////////
package com.enrogen.modbus2sql;

import org.jdesktop.application.Application;
import org.jdesktop.application.SingleFrameApplication;

public class Modbus2SQLApp extends SingleFrameApplication {

    @Override protected void startup() {
        show(new MainWindow(this));
    }

    @Override protected void configureWindow(java.awt.Window root) {
    }

    public static Modbus2SQLApp getApplication() {
        return Application.getInstance(Modbus2SQLApp.class);
    }

    public static void main(String[] args) {
        launch(Modbus2SQLApp.class, args);
    }
}
