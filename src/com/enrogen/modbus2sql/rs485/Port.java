package com.enrogen.modbus2sql.rs485;

import com.enrogen.modbus2sql.javafx.InfoPopup;
import com.enrogen.modbus2sql.logger.EgLogger;
import gnu.io.CommPortIdentifier;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;

public class Port {

    public List getComPorts() throws Exception {
        //Modifies path to include for Fedora yum install of rxtx
        //String path = System.getProperty("java.library.path");
        //System.setProperty("java.library.path", path + ":/usr/lib64/rxtx");
        
        //Get the ports
        Enumeration ports = CommPortIdentifier.getPortIdentifiers();

        //Setup and Enumerate the ports
        List portlist = new LinkedList();

        while (ports.hasMoreElements()) {
            CommPortIdentifier port = (CommPortIdentifier) ports.nextElement();
            portlist.add(port.getName());
        }
        return portlist;
    }

}
