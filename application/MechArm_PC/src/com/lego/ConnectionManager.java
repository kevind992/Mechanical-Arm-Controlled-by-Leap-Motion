package com.lego;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import lejos.pc.comm.NXTCommLogListener;
import lejos.pc.comm.NXTConnector;

/* 
 * This class is for handling the connection with the NXT unit
 * Command are sent as integers  
 */
public class ConnectionManager {
	
	private static NXTConnector conn = new NXTConnector();

	public ConnectionManager() {
		conn.addLogListener(new NXTCommLogListener() {

			public void logEvent(String message) {
				System.out.println("[INFO] - USBSend Log.listener: " + message);
			}

			// If an event has occurred
			public void logEvent(Throwable throwable) {
				System.out.println("[INFO] - USBSend Log.listener - stack trace: ");
				throwable.printStackTrace();
			}
		});
		// Trying to make connection
		if (!conn.connectTo("usb://")) {
			System.err.println("[INFO] - No NXT found using USB");
			System.exit(1);
		}
		System.out.println("[INFO] - Connection Established");

	}

	public void sendCommand(int commandCode) {
		
		// Creating Input and Output Streams 
		DataInputStream inDat = new DataInputStream(conn.getInputStream());
		DataOutputStream outDat = new DataOutputStream(conn.getOutputStream());

		int x = 0;
		
		try {
			System.out.println("[INFO] - Command from Leap Motion: " + commandCode);
			// Sending command to NXT unit
			outDat.writeInt(commandCode);
			outDat.flush();
		} catch (IOException ioe) {
			System.err.println("IO Exception writing bytes");
		}

		try {
			// Reading a response from the NXT unit
			x = inDat.readInt();
		} catch (IOException ioe) {
			System.err.println("IO Exception reading reply");
		}
		System.out.println("[INFO] - Sent " + commandCode + " Received " + x);
		if (commandCode == 6) { // If a 6 is received, cut the connection with NXT unit
			try {
				// Close connection
				inDat.close();
				outDat.close();
				System.out.println("[INFO] - Closed data streams..");
			} catch (IOException ioe) {
				System.err.println("IO Exception Closing connection");
			}
			try {
				conn.close();
				System.out.println("[INFO] - Closed connection..");
			} catch (IOException ioe) {
				System.err.println("IO Exception Closing connection");
			}
		}
	}
}