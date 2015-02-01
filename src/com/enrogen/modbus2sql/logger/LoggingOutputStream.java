//*************************************************************************//
//com.enrogen.genesys.logger.LoggingOutputStream                           //
//Date : 04 March 2012 (Rewrite of 2010 Work - Modbus2SQL Project          //
//Desc : Fileformatter for Logging                                         //
//(c) James A R Brown 2010                                                 //
//*************************************************************************//

package com.enrogen.modbus2sql.logger;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoggingOutputStream extends ByteArrayOutputStream {

    private String lineSeparator;
    private Logger logger;
    private Level level;

    public LoggingOutputStream(Logger logger, Level level) {
        super();
        this.logger = logger;
        this.level = level;
        this.lineSeparator = System.getProperty("line.separator");
    }

    @Override
    public void flush() throws IOException {
        String record;
        synchronized (this) {
            super.flush();
            record = this.toString();
            super.reset();

            if (record.length() == 0 || record.equals(lineSeparator)) {
                // avoid empty records
                return;
            }

            logger.logp(level, "", "", record);
        }
    }
}
