package org.example;

import com.fazecast.jSerialComm.SerialPort;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        SerialPort comPort = getPort();

        if (comPort == null) {
            System.err.println("No COM port found.");
            System.exit(1);
        }
//
        comPort.openPort();
        comPort.getInputStream();
        comPort.addDataListener(new SerialDataListener(comPort));

    }

    private static SerialPort getPort() {
        SerialPort [] ports = SerialPort.getCommPorts();
        System.out.println("Available ports: " + ports.length);

        if (ports.length == 0) {
            System.err.println("No ports available");
            System.exit(1);
        }

        for (int i = 0; i < ports.length; i++) {
            System.out.println((i+1) + " : " + ports[i].getSystemPortName()+ " : " + ports[i].getDescriptivePortName());
        }
        boolean validPort = false;
        int portNumber = 0;
        do {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter port number: ");
            portNumber = scanner.nextInt();

            if (portNumber > 0 && portNumber <= ports.length) {
                validPort = true;
            } else {
                System.err.println("Invalid port.");
            }
        } while (!validPort);


        System.out.println("Serial interface is now listening on " + ports[portNumber-1].getSystemPortName()+ " : " + ports[portNumber-1].getDescriptivePortName());
        return ports[portNumber-1];
    }
}