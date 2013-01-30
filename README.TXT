Modbus2SQL Server
==================

Version
-------
Current Version is : Beta - Works for mutliple read and write.
Release            : rc1
Quality            : Alpha

Purpose
-------
The purpose of the view server is to read Modbus RTU data from various modbus slaves
and store it into a MySQL server.

The MySQL server can then be used by GUI interfaces for HMI input.

This application runs as Master Unit (ID = 1), Modbus RTU over RS485

But Why??? Getting information from multiple Modbus Slaves with large qty of resgiters,
all with differing registers is a pain. This way all data is gathered from all slaves and stored
in a single scheme, so if you want to know the value of a register from a slave you can perform
a single SQL Select command.

No need to open a connection, poll the device, read the result and close connection.

Basically, it hides all the "dirty" work of working with Modbus devices, once running you
can concentrate on your front end system.

Licence
-------
Copyright 2010 James A R Brown (james@enrogen.com), Enrogen Ltd

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

Specifically
------------
The copyright notice must always remain in this project.

Support Non Commercial / "Non Power Systems Industry"
-----------------------------------------------------
If you are outside of my industry (power systems) I will try to help

Support Commerical / "Power Systems Industry"
---------------------------------------------
If you are inside my industry, just call Enrogen Ltd (www.enrogen.com) and employ our
services for support or implementation for your project.

Dependant Projects
------------------
jamod   - Modbus Java Class  - Apache 2 Licence
opencsv - CSV Access Library - Apache 2 Licence
rxtx    - Serial port access - LGPL v 2.1 + Linking Over Controlled Interface.

Contact
-------
James A R Brown - james@enrogen.com

Develop
-------
This application will probably only develop to my needs and currently this is only
RS485 RTU with modbus functions 3 and 16.

If you want to work on this project please email me as I welcome any development
to open the application for wider use.

MySQL Installation
------------------
Windows :
Download and Install MySQL BInary

Linux :
# yum install mysql
# yum install mysql-server
# yum install mysql-query-browser mysql-administrator
# /etc/init.d/mysqld start
# chkconfig --level 345 mysqld on

Then it needs a root password
# mysqladmin -u root password mypass
# mysqladmin -h hostname -u root password mypass -p

Now add a user and default database
# /usr/bin/mysql
mysql > GRANT ALL PRIVILEGES ON *.* TO 'modbus2sql'@'localhost' IDENTIFIED BY 'modicon' WITH GRANT OPTION;
Query OK, 0 rows affected (0.06 sec)
mysql> flush privileges;
Query OK, 0 rows affected (0.04 sec)
mysql> create database modbus2sql;
Query OK, 1 row affected (0.00 sec)
mysql>exit
Bye
#

If using a remote server you need to also execute GRANT with % in place of localhost.

The default database connections (username etc) are defined in setting.xml which is created at
first start. You can use the GUI to ammend these to your liking and save to setting.xml.

Now you can create the default tables included with this project
# mysql -u root -p modbus2sql < modbus2sql.sql
Enter password: 
#

Modbus Products Supported
-------------------------
Any modbus RTU RS485 product, you create a CSV to define the registers. I do not currently
see the need for TCPIP as RS485 is more for industrial applications and the modbus server
is technically acting as a RS485 to TCP bridge

Ie it gathers data across RS485 and presents it on the SQL Database TCP port 3306.

Custom Modbus Data registers
----------------------------
New modbus registers and controllers can be registered in the software.

1) Setup > Controller Type : Add a new controller type.

2) Setup > Pages : For this controller you create a page of Modbus data. For each controller
you can add multiple pages of data (eg you can add a page for electrical instruments,
a page for mechanical readouts and so on. Each page must have its applicable Modbus function
per the Modbus specifications. (see Modbus Function codes below).

A page MUST Be consequtive registers.

3) Setup Slaves : Add the slaves applicable to your system. You can give them a specific
name.

4) Setup > RS485 : Insert the settings applicable for the serial device.

Latency
-------
It is clear the more devices you add with more registers, the slower the cycle will become.
Only create register blocks that you need if on a large system.
All results are timestamped into the database to ensure visibility of "live" data is available.

Modbus Function Codes
---------------------
READ_MULTIPLE_REGISTERS = 3 (implemented)
WRITE_MULTIPLE_REGISTERS = 16 (implemented)
READ_COILS = 1 (to do)
READ_INPUT_DISCRETES = 2 (to do)
READ_INPUT_REGISTERS = 4 (to do)
WRITE_COIL = 5 (to do)
WRITE_MULTIPLE_COILS = 15 (to do)
WRITE_SINGLE_REGISTER = 6 (to do)

CSV Import Format
-----------------
A CSV can easily be created in Excel or OpenOffice Calc etc. This I found was the easiest
way to create a register file and get it to the app.

The CSV file containing the modbus page data must be in the format

register no, long description, minimum value, maximum value, scaling factor, units name, bits (16,32,32S),
    writeable (1=yes, 0=no), lowbyteregister (the associated low byte register for 32 bit data)

The parser will work with "16 S" as well as "16S". The S should be uppercase.

For Modbus function code information see above.

IMPORTANT : The page must contain consequtive registers (eg 1024,1025,1026,... 1051)

CSV Example
-----------
1024,"Oil pressure",0,10000,1,"KPa",0,0
1025,"Coolant temperature",-50,200,1,"Degrees C","16 S",0,0
1026,"Oil temperature",-50,200,1,"Degrees C","16 S"0,0
1027,"Fuel level",0,130,1,"%",160,0,0

CSV Included
------------
There are some CSV included. If you develop a new CSV please email us and we will include with package.


Getting the SQL Data
--------------------
Assuming you want Register 1024 from Slave6

SELECT binary, hex, integer,description FROM slave6 WHERE register=1024;

binary      - Is a string of bits representing the 16 bits eg "0100100111110101"
hex         - Is a string containing the hexadecimal value eg "0x49F5"
integer     - Is a BigInt containing the integer value     eg "18933"
description - Is the long text description of the register

How does the App deal with Modbus Read + Write Registers
--------------------------------------------------------
The application will peform an initial read cycle to fill the database. It will
keep cycling the read cycle whilst checking the slave tables for a changeflag.

It will check the slave table polled for the FIRST found "changeflag".

If the changeflag=1 it will continue gathering the consequetive registers until the 
next changeflag=0

YOU MUST Enter the data into the writedata column before setting the changeflag to ensure
the modbus data is written as 1 block.

Failing to do this will result in the app possibly writing the  data to the controller
 in 2 separate transactions and the controller ignoring your instruction.

Example single register (16bit)
UPDATE slave6 SET writedata=1234, changeflag=1 WHERE register=1234;

Example with muliple registers (16bits and 32bit):-
UPDATE slave6 SET writedata=35700 WHERE register=4104;
UPDATE slave6 SET writedata=29835 WHERE register=4105;
update slave6 SET changeflag=1 WHERE 4104 <= register AND register <= 4105;

This for a Deep Sea Electronics 55xx, 75xx or 86xx controller, place the control
in STOP mode. (Deep Sea use a inverted bit compliment of the write data ie 65535 - writedata)

The Modbus2SQL server will update the value to the controller and once done reset
the writedata and changeflag to 0. It will ignore data after the first changeflag=0 is encountered.

For 32 bit data your application must first split the data into 2 x 16bit (high byte and low byte) and
write to database. Do not send 32bit integers to the register... it will break.

Why Pages of Data?
------------------
We followed Deep Sea on this. They break sections of data types into pages of 256 registers.
Each page has a consequtive registers. This also makes the programming easier to identify a
multiple register read block.

Each page must be associated with a Modbus read type. The read type automatically associated
the correct write type if writeable=1 in the CSV.

32Bit Data
----------
Modbus2SQL will read all the data in 16 bits and store in the applicable slave table
as 2 x 16integer, 16hex and 16binary registers

Between each read cycle it will check the table for "is32bit=1" in the slave table.

For each result the Modbus2SQL server will compute from the register and register+1
the 32bit data. It will then store this in the first register that is applicable to the
32bit data.

Watchdog
--------
The system has an inbuilt watchdog. Default setting 10sec. To save transaction queries
to the SQL server and therefore latency, if ANY data is found out of time the whole Slave
Table will be flagged as "OLD"

