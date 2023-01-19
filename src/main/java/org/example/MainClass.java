package org.example;/* JserialComm: https://fazecast.github.io/jSerialComm/
 * Maven: com.fazecast:jSerialComm:2.9.1
 *
 * Example:  https://github.com/Fazecast/jSerialComm/wiki/Event-Based-Reading-Usage-Example
 *  * Original coding by Richard Robinson and James Andrew Smith; York University
 */

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;

import java.io.InputStream;
import java.util.Scanner;

public class MainClass {
    public static void main(String[] args) {

//        SerialPort comPort = SerialPort.getCommPorts()[2];
//        System.out.println(SerialPort.getCommPorts().length);;
//        comPort.openPort();
////        comPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 0, 0);
//        InputStream comPortInput = comPort.getInputStream();
//        try {
//            comPortInput.skip(comPort.bytesAvailable());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        int readOnlyByteASCII;
//        do {
//            try {
//                readOnlyByteASCII = comPortInput.read();
//                System.out.print((char) readOnlyByteASCII);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        } while (true);
        SerialPort [] ports = SerialPort.getCommPorts();
        System.out.println("Ports disponibles: " + ports.length);
        for (int i = 0; i < ports.length; i++) {
                    System.out.println((i+1) + " : " + ports[i].getSystemPortName());
                                }
                Scanner scanner = new Scanner(System.in);
        System.out.println("Entrer le numéro du port à utiliser : ");
        int portNumber = scanner.nextInt();
        System.out.println("Vous avez choisi le port : " + ports[portNumber-1].getSystemPortName());
        SerialPort comPort = ports[portNumber-1];
        comPort.openPort();
        comPort.addDataListener(new SerialPortDataListener() {
            @Override
            public int getListeningEvents() { return SerialPort.LISTENING_EVENT_DATA_AVAILABLE; }
            @Override
            public void serialEvent(SerialPortEvent event)
            {
                if (event.getEventType() != SerialPort.LISTENING_EVENT_DATA_AVAILABLE)
                    return;
                byte[] newData = new byte[comPort.bytesAvailable()];
                int numRead = comPort.readBytes(newData, newData.length);
                System.out.println("Read " + numRead + " bytes.");
                System.out.println(new String(newData));
            }
        });


//        var controller = new DataController(); // create the controller
//        var serialPort = SerialPortService.getSerialPort("COM9");
//        serialPort.addDataListener(controller);
//        var now = System.currentTimeMillis();


    }
}
