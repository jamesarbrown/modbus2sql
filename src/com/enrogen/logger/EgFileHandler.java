//////////////////////////////////////////////////////////////////////////
//com.enrogen.logger.EgFileHandler
//2010 - James A R Brown
//Released under GPL V2
//////////////////////////////////////////////////////////////////////////
package com.enrogen.logger;

import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public class EgFileHandler {

    FileHandler fh1;

    public EgFileHandler(String filename, int sizelimit, int maxfiles) {
        try {
            fh1 = new FileHandler(filename, sizelimit, maxfiles);

            fh1.setFormatter(new Formatter() {

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
        } catch (Exception e) {
            System.out.println("com.enrogen.logger - Unable to Create file handler");
            e.printStackTrace();
        }
    }

    public FileHandler getFileHandler() {
        return fh1;
    }
}
