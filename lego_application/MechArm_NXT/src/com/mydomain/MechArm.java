package com.mydomain;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import lejos.nxt.LCD;
import lejos.nxt.Motor;
import lejos.nxt.comm.USB;
import lejos.nxt.comm.USBConnection;
import lejos.util.Delay;

public class MechArm {

	private static int speed = 75;
	
	public static void main(String[] args) throws Exception {
		getData();
	}

	public static void getData() throws Exception {
		
		LCD.clear();
		LCD.drawString("waiting for \ncommand", 0, 0);
		int command = 0;
		USBConnection conn = USB.waitForConnection();
		while (true) {
			
			// Opening Input and Output streams
			DataOutputStream dOut = conn.openDataOutputStream();
			DataInputStream dIn = conn.openDataInputStream();
			
			// Trying to read command from pc application
			try {
				command = dIn.readInt();
			} catch (EOFException e) {
				break;
			}
			
			// Sending command back to pc application
			dOut.writeInt(command);
			dOut.flush();
			
			// Setting Motor Speed
			Motor.C.setSpeed(speed);
			Motor.A.setSpeed(speed);
			Motor.B.setSpeed(speed);
			
			// Checking for a motor stall
			if(Motor.A.isStalled() ) {
				Motor.A.stop();
			} 
			if(Motor.B.isStalled() ) {
				Motor.B.stop();
			}
			if(Motor.C.isStalled() ) {
				Motor.C.stop();
			}
			
			// Switch to handle command
			switch (command) {
			case 1:
				// Command to Turn Arm Left
				if (Motor.C.isMoving()) {
					// If motor C moving do nothing
				} else {
					Motor.C.backward();
				}
				break;
			case 2:
				// Command to Lift Arm
				if (Motor.B.isMoving()) {
					// If motor B is moving do nothing
				} else {
					Motor.B.forward();
				}
				break;
			case 3:
				// Command to Lower Arm
				if (Motor.B.isMoving()) {
					// If motor B is moving do nothing
				} else {
					Motor.B.backward();
				}
				break;
			case 4:
				// Command to Open Claw
				if (Motor.A.isMoving()) {

				} else {
					Motor.A.forward();
				}
				break;
			case 5:
				// Command to Turn Arm Right
				if (Motor.C.isMoving()) {

				} else {
					Motor.C.forward();
				}
				break;
			case 6:
				// Close connection
				LCD.clear();
				LCD.drawString("Closing Connection", 0, 0);
				LCD.refresh();
				break;
			case 10:
				// Commands to Stop all Motors
				if (Motor.A.isMoving()) {
					// Stop Motor A
					Motor.A.stop();
				} else {
					// Do nothing
				}
				break;
			case 12: 
				if(Motor.C.isMoving()) {
					// Stop Motor C
					Motor.C.stop();
				} else if (Motor.B.isMoving()){
					Motor.B.stop();
				}
				
				break;
			case 14:
				if(Motor.B.isMoving()) {
					Motor.B.stop();
				} else {
					// Do Nothing
				}
			case 11:
				// Command to Close Claw
				if (Motor.A.isMoving()) {

				} else {
					Motor.A.backward();
				}
				break;
			}

			LCD.clear();
			LCD.drawString("waiting for \ncommand", 0, 0);
			LCD.refresh();
			// Closing Streams
			dOut.close();
			dIn.close();
			// if code 6 break out of loop
			if (command == 6)
				break;
		} // end while
		LCD.clear();
		LCD.drawString("connection \nclosed, \nbye!", 0, 0);
		Delay.msDelay(1000);
		conn.close();
	}// end void
}// end class