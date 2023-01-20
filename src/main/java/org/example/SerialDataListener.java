package org.example;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;

import static java.lang.System.exit;

public class SerialDataListener implements SerialPortDataListener {
    private final SerialPort comPort;

    public SerialDataListener(SerialPort comPort) {
        this.comPort = comPort;
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
                System.out.println(new String(newData));
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
