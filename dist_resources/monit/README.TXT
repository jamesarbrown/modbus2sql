MONIT
-----

Monit is used to monitor system resources on a posix platform. 

1) Install Monit
2) Copy the .monit files into the monit directory (eg /etc/monit.d/). Adjust files according to your install (existing files assume an install path of /opt/modbus2sql.
3) Enable Monit "chkconfig --levels 5 monit on"
4) Copy the X0.host file into /etc. This is important, it allows the java app to display the gui.
5) reboot and monit should start your app as soon as X is available.




