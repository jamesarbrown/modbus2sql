package com.enrogen.modbus2sql.components;

import com.enrogen.modbus2sql.javafx.windowcontroller.mainWindowController;
import javafx.scene.control.TextArea;
import javafx.scene.text.Font;

public class LogTextArea extends TextArea {

    private boolean pausedScroll = false;
    private double scrollPosition = 0;

    public LogTextArea() {
        super();

        //Set to freeMono
        final Font loggingfont = Font.loadFont(mainWindowController.class.getResource("/com/enrogen/modbus2sql/fonts/FreeMono.ttf").toExternalForm(), 11);
        this.setFont(loggingfont);
        
    }

    public void setMessage(String data) {
        if (pausedScroll) {
            scrollPosition = this.getScrollTop();
            this.setText(data);
            this.setScrollTop(scrollPosition);
        } else {
            this.setText(data);
            this.setScrollTop(Double.MAX_VALUE);
        }
    }

    public void pauseScroll(Boolean pause) {
        pausedScroll = pause;
    }

}
