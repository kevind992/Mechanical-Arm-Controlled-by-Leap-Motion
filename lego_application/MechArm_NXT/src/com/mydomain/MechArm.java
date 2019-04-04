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
			
			try {
				command = dIn.readInt();
			} catch (EOFException e) {
				break;
			}

			dOut.writeInt(command);
			dOut.flush();
			
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
				} else if (Motor.B.isMoving()) {
					// Stop Motor B
					Motor.B.stop();
				}
				break;
			case 12: 
				if(Motor.C.isMoving()) {
					// Stop Motor C
					Motor.C.stop();
				}
				break;
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