package com.enrogen.modbus2sql.javafx.windowcontroller;

import com.enrogen.modbus2sql.javafx.tablecontroller.TableControllerSlaves;
import com.enrogen.modbus2sql.javafx.tablecontroller.TableControllerDeviceTypes;
import com.enrogen.modbus2sql.javafx.tablecontroller.TableControllerRegisterBlocks;
import com.enrogen.modbus2sql.appInterface.appInterface;
import com.enrogen.modbus2sql.configuration.config;
import com.enrogen.modbus2sql.components.LogTextArea;
import com.enrogen.modbus2sql.components.NumberSpinner;
import com.enrogen.modbus2sql.javafx.InfoPopup;
import com.enrogen.modbus2sql.javafx.tablecontroller.TableControllerAlarmFlag;
import com.enrogen.modbus2sql.logger.EgLogger;
import com.enrogen.modbus2sql.rs485.Port;
import com.enrogen.modbus2sql.sql.AlarmFlag;
import com.enrogen.modbus2sql.sql.DeviceType;
import com.enrogen.modbus2sql.sql.RS485Detail;
import com.enrogen.modbus2sql.sql.RS485DetailSQLController;
import com.enrogen.modbus2sql.sql.RegisterBlock;
import com.enrogen.modbus2sql.sql.SlaveDetail;
import java.math.BigDecimal;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

public class mainWindowController implements Initializable, appInterface {

    //Paint the table unlock buttons
    Image imageLocked = new Image(getClass().getResourceAsStream("/com/enrogen/modbus2sql/icons/locked.png"));
    Image imageUnLocked = new Image(getClass().getResourceAsStream("/com/enrogen/modbus2sql/icons/unlocked.png"));

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Set the form to default status
        disableMySQLTextBoxes();

        //Logging Autoscroll is by default ON
        check_autoscroll.setSelected(true);

    }

    ////////////////////////////////////////////////////////////////////////////
    //The Tabs
    ////////////////////////////////////////////////////////////////////////////
    @FXML
    private Tab tab_mysql;

    @FXML
    private Tab tab_log;

    @FXML
    private Tab tab_viewer;

    @FXML
    private Tab tab_setup;

    public void lockTabs() {
        tab_viewer.setDisable(true);
        tab_setup.setDisable(true);
    }

    public void unlockTabs() {
        tab_viewer.setDisable(false);
        tab_setup.setDisable(false);
    }

    ////////////////////////////////////////////////////////////////////////////
    //MySQL Tab
    ////////////////////////////////////////////////////////////////////////////
    @FXML
    private TextField text_sqlserverip;

    public void setText_sqlserverip(String IP) {
        text_sqlserverip.setText(IP);
    }

    public String getText_sqlserverip() {
        return text_sqlserverip.getText();
    }

    @FXML
    private TextField text_sqlserverport;

    public void setText_sqlserverport(String IP) {
        text_sqlserverport.setText(IP);
    }

    public String getText_sqlserverport() {
        return text_sqlserverport.getText();
    }

    @FXML
    private TextField text_sqlusername;

    public void setText_sqlusername(String IP) {
        text_sqlusername.setText(IP);
    }

    public String getText_sqlusername() {
        return text_sqlusername.getText();
    }

    @FXML
    private TextField text_sqlpassword;

    public void setText_sqlpassword(String IP) {
        text_sqlpassword.setText(IP);
    }

    public String getText_sqlpassword() {
        return text_sqlpassword.getText();
    }

    @FXML
    private TextField text_sqldatabasename;

    public void setText_sqldatabasename(String IP) {
        text_sqldatabasename.setText(IP);
    }

    public String getText_sqldatabasename() {
        return text_sqldatabasename.getText();
    }

    @FXML
    private Button btn_sqledit;

    @FXML
    private Button btn_sqlsave;

    @FXML
    public void btn_sqlsave_click(ActionEvent event) {
        new config().saveSettingXML();
    }

    @FXML
    private Button btn_sqlcancel;

    @FXML
    public void btn_sqlcancel_click(ActionEvent event) {
        new config().readSQLSettings();
    }

    //Disable enable the boxes
    @FXML
    public void enableMySQLTextBoxes(ActionEvent event) {
        text_sqlserverip.setDisable(false);
        text_sqlserverport.setDisable(false);
        text_sqlusername.setDisable(false);
        text_sqlpassword.setDisable(false);
        text_sqldatabasename.setDisable(false);
        btn_sqlsave.setDisable(false);
        btn_sqlcancel.setDisable(false);
        btn_sqledit.setDisable(true);
    }

    public void disableMySQLTextBoxes() {
        text_sqlserverip.setDisable(true);
        text_sqlserverport.setDisable(true);
        text_sqlusername.setDisable(true);
        text_sqlpassword.setDisable(true);
        text_sqldatabasename.setDisable(true);
        btn_sqlsave.setDisable(true);
        btn_sqlcancel.setDisable(true);
        btn_sqledit.setDisable(false);
    }

    @FXML
    private CheckBox checkStartModbusAuto;

    public void setSelected_checkStartModbusAuto(boolean selected) {
        checkStartModbusAuto.setSelected(selected);
    }

    public boolean getSelected_checkStartModbusAuto() {
        return checkStartModbusAuto.isSelected();
    }

    @FXML
    private Circle red_lamp;
    private Color current_red_lamp = RED_LAMP_ON;

    public void blink_red_lamp() {
        if (current_red_lamp == RED_LAMP_ON) {
            current_red_lamp = RED_LAMP_OFF;
        } else {
            current_red_lamp = RED_LAMP_ON;
        };
        red_lamp.setFill(current_red_lamp);
    }

    public void red_lamp_off() {
        red_lamp.setFill(RED_LAMP_OFF);
    }

    @FXML
    private Circle green_lamp;
    private Color current_green_lamp = GREEN_LAMP_ON;

    public void blink_green_lamp() {
        if (current_green_lamp == GREEN_LAMP_ON) {
            current_green_lamp = GREEN_LAMP_OFF;
        } else {
            current_green_lamp = GREEN_LAMP_ON;
        };
        green_lamp.setFill(current_green_lamp);
    }

    public void green_lamp_off() {
        green_lamp.setFill(GREEN_LAMP_OFF);
    }

    ////////////////////////////////////////////////////////////////////////////
    //Setup Tab - Device Types
    ////////////////////////////////////////////////////////////////////////////
    @FXML
    TableView<DeviceType> table_controllertypes = new TableView<DeviceType>();

    public TableView<DeviceType> getTable_controllertypes() {
        return table_controllertypes;
    }

    @FXML
    private Button addDeviceTypeButton;

    @FXML
    private void addDeviceTypeButton_click(ActionEvent ae) throws Exception {
        String mainWindowResource = JAVAFX_FXML_LOCATION + "AddDeviceWindow.fxml";

        //Show the window
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(mainWindowResource));
        Parent root = fxmlLoader.load();
        AddDeviceWindowController wc = fxmlLoader.getController();

        final Stage dialog = new Stage();
        Scene scene = new Scene(root);
        dialog.setScene(scene);
        dialog.centerOnScreen();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setTitle("Add a new Device Type");
        dialog.show();
    }

    @FXML
    private Button deleteDeviceButton;

    @FXML
    public void deleteDeviceButton_click(ActionEvent ae) {
        TableControllerDeviceTypes.getInstance().deleteDeviceType();
    }

    @FXML
    private Button editDeviceButton;

    @FXML
    public void editDeviceButton_click(ActionEvent ae) throws Exception {
        try {
            DeviceType device = table_controllertypes.getSelectionModel().getSelectedItem();
            String mainWindowResource = JAVAFX_FXML_LOCATION + "AddDeviceWindow.fxml";

            //Show the window
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(mainWindowResource));
            Parent root = fxmlLoader.load();
            AddDeviceWindowController wc = fxmlLoader.getController();

            //Prepare the dialog
            final Stage dialog = new Stage();
            Scene scene = new Scene(root);
            dialog.setScene(scene);
            dialog.centerOnScreen();
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.setTitle("Edit Device");

            //Set the parameters
            wc.setEditMode(true);

            //We set the rowid, so we have reference to the edited device
            wc.setRowid(device.getUniqueID());
            wc.setDesc(device.getLongDesc());
            wc.setType(device.getDeviceType());

            dialog.show();
        } catch (Exception e) {
            new InfoPopup("You have not selected a device");
        }
    }

    ////////////////////////////////////////////////////////////////////////////
    //Setup Tab - RegisterBlocks
    ////////////////////////////////////////////////////////////////////////////
    @FXML
    TableView<RegisterBlock> table_registerblocks = new TableView<RegisterBlock>();

    public TableView<RegisterBlock> getTable_registerblocks() {
        return table_registerblocks;
    }

    @FXML
    private Button addRegisterBlockButton;

    public void addRegisterBlockButton_click(ActionEvent ae) {
        try {
            String mainWindowResource = JAVAFX_FXML_LOCATION + "AddRegisterBlockWindow.fxml";

            //Show the window
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(mainWindowResource));
            Parent root = fxmlLoader.load();
            AddRegisterBlockWindowController wc = fxmlLoader.getController();

            //Prepare the dialog
            final Stage dialog = new Stage();
            Scene scene = new Scene(root);
            dialog.setScene(scene);
            dialog.centerOnScreen();
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.setTitle("Add Register Block");

            dialog.show();
        } catch (Exception e) {
            new InfoPopup("You have not selected a device");
        }
    }

    @FXML
    private Button deleteRegisterBlock;

    @FXML
    private void deleteRegisterBlock_click() {
        TableControllerRegisterBlocks.getInstance().deleteRegisterBlock();
    }

    ////////////////////////////////////////////////////////////////////////////
    //Setup Tab - Slaves
    ////////////////////////////////////////////////////////////////////////////
    @FXML
    TableView<SlaveDetail> table_SlaveDetail = new TableView<SlaveDetail>();

    public TableView<SlaveDetail> getTable_SlaveDetail() {
        return table_SlaveDetail;
    }

    @FXML
    private Button addSlaveButton;

    @FXML
    public void addSlaveButton_click(ActionEvent ae) {
        try {
            String mainWindowResource = JAVAFX_FXML_LOCATION + "AddSlaveWindow.fxml";

            //Show the window
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(mainWindowResource));
            Parent root = fxmlLoader.load();
            AddSlaveWindowController wc = fxmlLoader.getController();

            //Prepare the dialog
            final Stage dialog = new Stage();
            Scene scene = new Scene(root);
            dialog.setScene(scene);
            dialog.centerOnScreen();
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.setTitle("Add Slave");

            dialog.show();
        } catch (Exception e) {
            EgLogger.logSevere("Unable to open Add Slave Window");
        }
    }

    @FXML
    private Button editSlave;

    @FXML
    private void editSlave_click() {
        try {
            SlaveDetail slave = table_SlaveDetail.getSelectionModel().getSelectedItem();

            String mainWindowResource = JAVAFX_FXML_LOCATION + "AddSlaveWindow.fxml";

            //Show the window
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(mainWindowResource));
            Parent root = fxmlLoader.load();
            AddSlaveWindowController wc = fxmlLoader.getController();

            //Prepare the dialog
            final Stage dialog = new Stage();
            Scene scene = new Scene(root);
            dialog.setScene(scene);
            dialog.centerOnScreen();
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.setTitle("Edit Slave");

            //Set the parameters
            wc.setEditMode(true);

            //We set the rowid, so we have reference to the edited device
            wc.setRowid(slave.getRowID());
            wc.setDescription(slave.getDescription());
            wc.setModbusID(slave.getSlaveID());

            dialog.show();
        } catch (Exception e) {
            //    new InfoPopup("You have not selected a slave");
            e.printStackTrace();
        }
    }

    @FXML
    private Button deleteSlave;

    @FXML
    private void deleteSlave_click() {
        TableControllerSlaves.getInstance().deleteSlave();
    }

    ////////////////////////////////////////////////////////////////////////////
    //Setup Tab - RS485
    ////////////////////////////////////////////////////////////////////////////
    public void RS485TabRefresh() {
        //This is to paint a comport as red if not available on the system
        //And green if available
        RS485ComboBoxComPort.setCellFactory(
                new Callback<ListView<String>, ListCell<String>>() {
                    @Override
                    public ListCell<String> call(ListView<String> param) {
                        final ListCell<String> cell = new ListCell<String>() {
                            {
                                super.setPrefWidth(200);
                            }

                            @Override
                            public void updateItem(String item,
                                    boolean empty) {
                                super.updateItem(item, empty);
                                if (item != null) {
                                    setText(item);
                                    if (item.contains("(Unavailable)")) {
                                        setTextFill(Color.RED);
                                    } else {
                                        setTextFill(Color.GREEN);
                                    }
                                } else {
                                    setText(null);
                                }
                            }
                        };
                        return cell;
                    }
                });

        //Get the system com ports
        RS485ButtonQueryPorts_click();

        RS485Detail rs485 = new RS485DetailSQLController().getRS485Settings();

        //Available Standard Bauds
        //"9600", "19200", "57600", "115200"
        RS485ComboBoxBaud.getItems().addAll(
                (Integer) 9600,
                (Integer) 19200,
                (Integer) 57600,
                (Integer) 115200);
        RS485ComboBoxBaud.getSelectionModel().selectFirst();

        //Available Parity
        //"None", "Odd", "Even" 
        RS485ComboBoxParity.getItems().addAll(
                "None",
                "Odd",
                "Even");
        RS485ComboBoxParity.getSelectionModel().selectFirst();

        //Available DataBits
        //5,6,7,8
        RS485DataBitsSpinner.setMinValueProperty(BigDecimal.valueOf(5));
        RS485DataBitsSpinner.setMaxValueProperty(BigDecimal.valueOf(8));

        //Available Stop Bits
        //0, 1, 2
        RS485StopBitsSpinner.setMinValueProperty(BigDecimal.valueOf(0));
        RS485StopBitsSpinner.setMaxValueProperty(BigDecimal.valueOf(2));

        RS485GetSettings();

    }

    public void RS485GetSettings() {
        RS485Detail rs485 = new RS485DetailSQLController().getRS485Settings();

        if (rs485 != null) {
            //Get the com port
            //See if the comport exists in the combobox
            //If not add it in *red*
            ObservableList ol = RS485ComboBoxComPort.getItems();

            boolean PortIsAvailable = false;
            for (int i = 0; i < ol.size(); i++) {
                if (rs485.getComPort().compareTo((String) ol.get(i)) == 0) {
                    PortIsAvailable = true;
                    break;
                }
                //Incase field is null in Database..ignore it
                if (ol.get(i) == null) {
                    PortIsAvailable = true;
                }
            }

            if (!PortIsAvailable) {
                String port = rs485.getComPort() + " (Unavailable)";
                RS485ComboBoxComPort.getItems().add(port);
                RS485ComboBoxComPort.getSelectionModel().select(port);
            } else {
                RS485ComboBoxComPort.getSelectionModel().select(rs485.getComPort());
            }

            //Select the Baud
            Integer Baud;
            switch (rs485.getBaud()) {
                case 19200:
                    Baud = 19200;
                    break;
                case 57600:
                    Baud = 57600;
                    break;
                case 115200:
                    Baud = 115200;
                    break;
                default:
                    Baud = 9600;
                    break;
            }
            RS485ComboBoxBaud.getSelectionModel().select((Integer) Baud);

            //Set the Parity
            String Parity;
            switch (rs485.getParity()) {
                case "Even":
                    Parity = "Even";
                    break;
                case "Odd":
                    Parity = "Odd";
                    break;
                default:
                    Parity = "None";
                    break;
            }
            RS485ComboBoxParity.getSelectionModel().select((String) Parity);

            //Set the Data Bits
            RS485DataBitsSpinner.setNumber(BigDecimal.valueOf(rs485.getDataBits()));

            //Set the Stop Bits
            RS485StopBitsSpinner.setNumber(BigDecimal.valueOf(rs485.getStopBits()));
        }
    }

    public void RS485SaveSettings() {
        String ComPort = (String) RS485ComboBoxComPort.getValue();
        Integer Baud = (Integer) RS485ComboBoxBaud.getValue();
        String Parity = (String) RS485ComboBoxParity.getValue();
        Integer DataBits = (Integer) RS485DataBitsSpinner.getNumber().intValue();
        Integer StopBits = (Integer) RS485StopBitsSpinner.getNumber().intValue();

        //We need to strip off the (Unavailable) if we did add on
        if (ComPort.contains("(Unavailable)")) {
            ComPort = ComPort.substring(0, ComPort.indexOf(" "));
        }

        RS485Detail rs485 = new RS485Detail(ComPort, Baud, Parity, DataBits, StopBits);

        new RS485DetailSQLController().setRS485Settings(rs485);
    }

    @FXML
    private Button RS485ButtonQueryPorts;

    @FXML
    private void RS485ButtonQueryPorts_click() {
        List PortList = new Port().getComPorts();
        RS485ComboBoxComPort.getItems().clear();

        for (int i = 0; i < PortList.size(); i++) {
            RS485ComboBoxComPort.getItems().add((String) PortList.get(i));
        }

        RS485ComboBoxComPort.getSelectionModel().selectFirst();
    }

    @FXML
    private ComboBox RS485ComboBoxComPort;

    @FXML
    private ComboBox RS485ComboBoxBaud;

    @FXML
    private ComboBox RS485ComboBoxParity;

    @FXML
    private NumberSpinner RS485DataBitsSpinner;

    @FXML
    private NumberSpinner RS485StopBitsSpinner;

    @FXML
    private Button RS485ButtonSaveSettings;

    @FXML
    private void RS485ButtonSaveSettings_click(ActionEvent ae) {
        RS485SaveSettings();
    }

    ////////////////////////////////////////////////////////////////////////////
    //Alarm Flag Tab
    ////////////////////////////////////////////////////////////////////////////

    //Components
    @FXML
    private TableView AlarmFlagTable;
    
    @FXML
    private Button AlarmFlagAddButton;
    
    @FXML
    private Button AlarmFlagEditButton;
    
    @FXML
    private Button AlarmFlagDeleteButton;
    
    //Methods
    public TableView<AlarmFlag> getAlarmFlagTable() {
        return AlarmFlagTable;
    }

    @FXML
    private void AlarmFlagAddButton_click(ActionEvent ae) {
        try {
            String mainWindowResource = JAVAFX_FXML_LOCATION + "AddAlarmFlagWindow.fxml";

            //Show the window
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(mainWindowResource));
            Parent root = fxmlLoader.load();
            AddAlarmFlagWindowController wc = fxmlLoader.getController();

            //Prepare the dialog
            final Stage dialog = new Stage();
            Scene scene = new Scene(root);
            dialog.setScene(scene);
            dialog.centerOnScreen();
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.setTitle("Add Controller Alarm Flag Registers");

            dialog.show();
        } catch (Exception e) {
           EgLogger.logSevere("Unable to open Add Slave Window");
           e.printStackTrace();
         }
    }
    
    @FXML
    private void AlarmFlagEditButton_click(ActionEvent ae) {
        try {
            AlarmFlag af = (AlarmFlag) AlarmFlagTable.getSelectionModel().getSelectedItem();
            
            String mainWindowResource = JAVAFX_FXML_LOCATION + "AddAlarmFlagWindow.fxml";

            //Show the window
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(mainWindowResource));
            Parent root = fxmlLoader.load();
            AddAlarmFlagWindowController wc = fxmlLoader.getController();

            //Prepare the dialog
            final Stage dialog = new Stage();
            Scene scene = new Scene(root);
            dialog.setScene(scene);
            dialog.centerOnScreen();
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.setTitle("Edit Controller Alarm Flag Registers");

            //Set the parameters
            wc.setEditMode(true);

            //We set the rowid, so we have reference to the edited device
            wc.setRowID(af.getUniqueID());
            wc.setShutdownRegister(af.getShutdownRegister());
            wc.setShutdownBit(af.getShutdownBit());
            wc.setWarningRegister(af.getWarningRegister());
            wc.setWarningBit(af.getWarningBit());
            wc.setTrippedRegister(af.getTripRegister());
            wc.setTrippedBit(af.getTripBit());

            dialog.show();
        } catch (Exception e) {
           EgLogger.logSevere("Unable to open Add Slave Window");
           e.printStackTrace();
         }
    }
    
    @FXML
    private void AlarmFlagDeleteButton_click(ActionEvent ae) {
        TableControllerAlarmFlag.getInstance().deleteAlarmFlag();
    }

    ////////////////////////////////////////////////////////////////////////////
    //Alarm Descriptor Tab
    ////////////////////////////////////////////////////////////////////////////

    //Components
    @FXML
    private Button AlarmDescriptorAddButton;
    
   
    //Methods
    @FXML
    private void AlarmDescriptorAddButton_click(ActionEvent ae) {
        try {
            //AlarmFlag af = (AlarmFlag) AlarmFlagTable.getSelectionModel().getSelectedItem();
    
            String mainWindowResource = JAVAFX_FXML_LOCATION + "AddAlarmDescriptorWindow.fxml";

            //Show the window
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(mainWindowResource));
            Parent root = fxmlLoader.load();
            AddAlarmDescriptorWindowController wc = fxmlLoader.getController();

            //Prepare the dialog
            final Stage dialog = new Stage();
            Scene scene = new Scene(root);
            dialog.setScene(scene);
            dialog.centerOnScreen();
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.setTitle("Add Alarm Descriptions CSV File");

            //Set the parameters
            //wc.setEditMode(true);

            //We set the rowid, so we have reference to the edited device
            //wc.setRowID(af.getUniqueID());
           // wc.setShutdownRegister(af.getShutdownRegister());
           // wc.setShutdownBit(af.getShutdownBit());
           // wc.setWarningRegister(af.getWarningRegister());
           // wc.setWarningBit(af.getWarningBit());
           // wc.setTrippedRegister(af.getTripRegister());
           // wc.setTrippedBit(af.getTripBit());

            dialog.show();
        } catch (Exception e) {
           EgLogger.logSevere("Unable to open Add Slave Window");
           e.printStackTrace();
         }        
    }
    
    

    ////////////////////////////////////////////////////////////////////////////
    //Log Tab
    ////////////////////////////////////////////////////////////////////////////
    @FXML
    private CheckBox checkDebugSQL;

    public void setSelected_checkDebugSQL(boolean selected) {
        checkDebugSQL.setSelected(selected);
    }

    public boolean getSelected_checkDebugSQL() {
        return checkDebugSQL.isSelected();
    }

    @FXML
    private CheckBox checkDebugModbusConn;

    public void setSelected_checkDebugModbusConn(boolean selected) {
        checkDebugModbusConn.setSelected(selected);
    }

    public boolean getSelected_checkDebugModbusConn() {
        return checkDebugModbusConn.isSelected();
    }

    @FXML
    private CheckBox checkDebugJamod;

    public void setSelected_checkDebugJamod(boolean selected) {
        checkDebugJamod.setSelected(selected);
    }

    public boolean getSelected_checkDebugJamod() {
        return checkDebugJamod.isSelected();
    }

    @FXML
    private LogTextArea text_log;

    public LogTextArea getLoggingWindow() {
        return text_log;
    }

    @FXML
    private CheckBox check_autoscroll;

    public void check_autoscroll_click(ActionEvent ae) {
        if (check_autoscroll.isSelected()) {
            text_log.pauseScroll(false);
        } else {
            text_log.pauseScroll(true);
        }
    }

    public boolean isAutoScroll() {
        return check_autoscroll.isSelected();
    }

    @FXML
    private Button btn_debug_apply;

    public void btn_debug_apply_click(ActionEvent ae) {
        new config().saveSettingXML();
    }

}
