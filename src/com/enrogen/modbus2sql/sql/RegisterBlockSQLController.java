package com.enrogen.modbus2sql.sql;

import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class RegisterBlockSQLController {

    private final ObservableList<RegisterBlock> RegisterBlockList = FXCollections.observableArrayList();

    public void RegisterBlockSQLController() {
    }

    public ObservableList getRegisterBlocks() {
        refresh();
        return RegisterBlockList;
    }

    public void refresh() {
        //Clear the list is already init
        RegisterBlockList.clear();

        //Now fill the table
        String sqlcmd = "SELECT rowid, controllertype, page, registerstart, registerend, "
                + "modbusfunctiontype, description FROM registerblocks "
                + "ORDER BY controllertype, page;";
        List resultList = sqlConnection.getInstance().SQLSelectCommand(sqlcmd);

        for (int rows = 0; rows < resultList.size(); rows++) {
            //Get the row data
            List resultValues = (List) resultList.get(rows);
            Integer rowid = (Integer) resultValues.get(0);
            String Type = (String) resultValues.get(1);
            Integer Page = (Integer) resultValues.get(2);
            Integer RegisterStart = (Integer) resultValues.get(3);
            Integer RegisterEnd = (Integer) resultValues.get(4);
            Integer ModbusFunctionType = (Integer) resultValues.get(5);
            String Desc = (String) resultValues.get(6);

            //Put it into the class
            RegisterBlock registerBlock = new RegisterBlock(rowid, Type, Page, RegisterStart, RegisterEnd, Desc, ModbusFunctionType);

            //Now add to the Observable List
            RegisterBlockList.add(registerBlock);
        }
    }

    public boolean insert(RegisterBlock rb) {
        try {
            String SqlCmd = "INSERT INTO registerblocks SET controllertype='"
                    + rb.getDeviceType() + "', "
                    + "page=" + rb.getPageNo() + ", "
                    + "registerstart=" + rb.getRegisterStart() + ", "
                    + "registerend=" + rb.getRegisterEnd() + ", "
                    + "description='" + rb.getDescription() + "', "
                    + "modbusFunctionType=" + rb.getModbusFunctionCode()
                    + ";";
            sqlConnection.getInstance().SQLUpdateCommand(SqlCmd);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void delete(RegisterBlock rb) {
        //Open SQL
        String sqlcmd = "DELETE FROM registerblocks WHERE rowid=" + rb.getRowID() + ";";
        sqlConnection.getInstance().SQLUpdateCommand(sqlcmd);
        String sqlcmd2 = "DELETE FROM registerdetail WHERE page=" + rb.getPageNo() + " AND controllertype='" + rb.getDeviceType() + "'";
        sqlConnection.getInstance().SQLUpdateCommand(sqlcmd2);
    }

}
