package org.example;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;

import java.time.LocalDateTime;

import static java.lang.System.exit;

public class SerialDataListener implements SerialPortDataListener {
    private final SerialPort comPort;
    private final MySQLAccess mySQLAccess;
    private String completeString = "";
    private int count = 0;
    private final int flightId;

    public SerialDataListener(SerialPort comPort, int flightId) {
        this.comPort = comPort;
        this.mySQLAccess = new MySQLAccess();
        this.flightId = flightId;
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
                System.out.println("\n\nRead " + numRead + " bytes." + LocalDateTime.now());
                addDataInDB(newData);
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

    /**
     * Cast les bytes en String et ajoute un donné complète dans la Db
     * si elle existe, sinon gère le buffer
     * @param newData Peut être incomplet
     */
    private void addDataInDB(byte[] newData) {
        completeString += new String(newData);
        if (completeString.contains("\r\n")) {
            //Split completeString into two strings at last \r\n occurrence
            String[] splitString = completeString.split("\r\n");

            System.out.println("Complete string: \n\"" + completeString.substring(0, completeString.length() - 1) + "\"");
            System.out.println("Insert string: \"" + splitString[0] + "\"");
            count++;
            if (count != 1) {
                try {
                    mySQLAccess.insertData(new FlightData(splitString[0], flightId));
                } catch (Exception e) {
                    System.err.println("Error inserting data into database. With message: " + e.getMessage());
                }
            } else {
                System.out.println("Didn't insert first line.");
            }
            completeString = splitString.length > 1 ? splitString[splitString.length -1] : "";
            System.out.println("Incomplete string: \"" + completeString + "\"");

        }
    }
}
