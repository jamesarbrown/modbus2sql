//////////////////////////////////////////////////////////////////////////
//com.enrogen.logger
//2010 - James A R Brown
//Released under GPL V2
//////////////////////////////////////////////////////////////////////////
package com.enrogen.modbus2sql.logger;

import com.enrogen.modbus2sql.components.LogTextArea;
import java.io.IOException;
import java.io.OutputStream;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;
import java.util.logging.StreamHandler;


public class WindowHandler extends StreamHandler {

    private LogTextArea textArea;
    OutputStream os;

    public WindowHandler(LogTextArea ta) {
        textArea = ta;

        this.setFormatter(new Formatter() {
            @Override
            public String format(LogRecord rec) {
                StringBuffer buf = new StringBuffer(1000);
                buf.append(new java.util.Date());
                buf.append(' ');
                buf.append(rec.getLevel());
                buf.append(' ');
                buf.append(formatMessage(rec));
                buf.append('\n');
                return buf.toString();
            }
        });

        if (os==null) {
            os = createOutputStream();
        }
        setOutputStream(os);

    }

    public OutputStream createOutputStream() {
        OutputStream out = new OutputStream() {

            @Override
            public void write(int b) throws IOException {
                String s = textArea.getText().toString();
                s = s + String.valueOf((char) b);
                textArea.setMessage(s);
            }

            @Override
            public void write(byte[] b, int off, int len) throws IOException {
                String s = textArea.getText().toString();
                s = s + new String(b, off, len);
                textArea.setMessage(s);
            }

            @Override
            public void write(byte[] b) throws IOException {
                //write(b, 0, b.length);
                               // close();
            }
        };
        return out;
    }

    public void publish(LogRecord record) {
        super.publish(record);
        flush();
    }
}
