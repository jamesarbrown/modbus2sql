package com.enrogen.modbus2sql.sql;

import java.util.List;

public class RS485DetailSQLController {

    public RS485Detail getRS485Settings() {
        String sqlcmd = "SELECT serialport, baud, parity, databits, stopbits FROM rs485settings WHERE rowid=1;";
        List resultList = sqlConnection.getInstance().SQLSelectCommand(sqlcmd);
        
        RS485Detail rs485 = new RS485Detail();
       
        //There should only be one result, row 0
        //Any DB errors/multiple rows will return *last* row
        for (int rows = 0; rows < resultList.size(); rows++) {
            //Get the row data
            List resultValues = (List) resultList.get(rows);
            rs485.setComPort((String) resultValues.get(0));
            rs485.setBaud((Integer) resultValues.get(1));
            rs485.setParity((String) resultValues.get(2));
            rs485.setDataBits((Integer) resultValues.get(3));
            rs485.setStopBits((Integer) resultValues.get(4));
        }

        return rs485;
    }
    
    public void setRS485Settings(RS485Detail rs485) {
        //Build the sql insert
        String sqlcmd = "UPDATE rs485settings SET serialport='"
                + rs485.getComPort() + "', baud=" + rs485.getBaud() + ", "
                + "parity='" + rs485.getParity() + "', databits="
                + rs485.getDataBits() + ", stopbits=" + rs485.getStopBits();
        sqlConnection.getInstance().SQLUpdateCommand(sqlcmd);
    }

}
