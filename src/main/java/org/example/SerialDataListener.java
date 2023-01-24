package org.example;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;

import static java.lang.System.exit;

public class SerialDataListener implements SerialPortDataListener {
    private final SerialPort comPort;
    private final MySQLAccess mySQLAccess;
    private String completeString;
    private int count = 0;

    public SerialDataListener(SerialPort comPort) {
        this.comPort = comPort;
        this.mySQLAccess = new MySQLAccess();
    }

    @Override
    public int getListeningEvents() { return SerialPort.LISTENING_EVENT_DATA_AVAILABLE + SerialPort.LISTENING_EVENT_PORT_DISCONNECTED; }

    @Override
    public void serialEvent(SerialPortEvent event)
    {
        switch (event.getEventType()) {
            case SerialPort.LISTENING_EVENT_DATA_AVAILABLE:
                byte[] newData = new byte[comPort.bytesAvailable()];
                int numRead = comPort.readBytes(newData, newData.length);
                System.out.println("Read " + numRead + " bytes.");
                completeString += new String(newData);
                if (completeString.contains("\r\n")) {
                    //Split completeString into two strings at \r\n
                    String[] splitString = completeString.split("\r\n");

                    System.out.println("Complete string: " + completeString);
                    System.out.println("Split string 0: " + splitString[0]);
                    count++;
                    if (count != 1) {
                        try {
                            mySQLAccess.insertData(new FlightData(splitString[0]));
                        } catch (Exception e) {
                            System.err.println("Error inserting data into database. With message: " + e.getMessage());
                        }
                    }
                    completeString = splitString.length > 1 ? splitString[1] : "";
                    System.out.println("Incomplete string: " + completeString);

                }
                // System.out.println(new String(newData));
                break;
            case SerialPort.LISTENING_EVENT_PORT_DISCONNECTED:
                System.err.println("Break interrupt detected.");
                exit(20);
                break;
            default:
                break;
        }
    }
}
