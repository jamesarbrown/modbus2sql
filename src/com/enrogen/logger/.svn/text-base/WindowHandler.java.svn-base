//////////////////////////////////////////////////////////////////////////
//com.enrogen.logger
//2010 - James A R Brown
//Released under GPL V2
//////////////////////////////////////////////////////////////////////////
package com.enrogen.logger;

import java.io.IOException;
import java.io.OutputStream;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;
import java.util.logging.StreamHandler;
import javax.swing.JTextArea;

public class WindowHandler extends StreamHandler {

    final JTextArea textArea;
    OutputStream os;

    public WindowHandler(JTextArea jta) {
        textArea = jta;

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


        /*setOutputStream(new OutputStream() {

            @Override
            public void write(int b) {
            } // not called

            @Override
            public void write(byte[] b, int off, int len) {
                textArea.append(new String(b, off, len));
            }
        });*/
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
                textArea.setText(s);
                //close();
                //textArea.append(String.valueOf((char) b));
            }

            @Override
            public void write(byte[] b, int off, int len) throws IOException {
                String s = textArea.getText().toString();
                s = s + new String(b, off, len);
                textArea.setText(s);
                               //close();
                //textArea.append(new String(b, off, len));
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
