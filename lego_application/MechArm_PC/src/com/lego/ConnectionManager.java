package com.lego;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import lejos.pc.comm.NXTCommLogListener;
import lejos.pc.comm.NXTConnector;

/**
 * This is a PC sample. It connects to the NXT, and then sends an integer and
 * waits for a reply.
 * 
 * @author Lawrie Griffiths
 * adapted by Erik Nauman
 */
public class ConnectionManager {
	private static NXTConnector conn = new NXTConnector();

	public ConnectionManager() {
		conn.addLogListener(new NXTCommLogListener() {

			public void logEvent(String message) {
				System.out.println("USBSend Log.listener: " + message);
			}

			public void logEvent(Throwable throwable) {
				System.out.println("USBSend Log.listener - stack trace: ");
				throwable.printStackTrace();
			}
		});

		if (!conn.connectTo("usb://")) {
			System.err.println("No NXT found using USB");
			System.exit(1);
		}
		System.out.println("connection established");

	}

	public void sendCommand(int commandCode) {
		DataInputStream inDat = new DataInputStream(conn.getInputStream());
		DataOutputStream outDat = new DataOutputStream(conn.getOutputStream());

		int x = 0;
		try {
			outDat.writeInt(commandCode);
			outDat.flush();
		} catch (IOException ioe) {
			System.err.println("IO Exception writing bytes");
		}

		try {
			x = inDat.readInt();
		} catch (IOException ioe) {
			System.err.println("IO Exception reading reply");
		}
		System.out.println("Sent " + commandCode + " Received " + x);
		if (commandCode == 6) {
			try {
				inDat.close();
				outDat.close();
				System.out.println("Closed data streams");
			} catch (IOException ioe) {
				System.err.println("IO Exception Closing connection");
			}
			try {
				conn.close();
				System.out.println("Closed connection");
			} catch (IOException ioe) {
				System.err.println("IO Exception Closing connection");
			}
		}
	}
}