package com.mydomain;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import lejos.nxt.LCD;
import lejos.nxt.Motor;
import lejos.nxt.comm.USB;
import lejos.nxt.comm.USBConnection;
import lejos.util.Delay;

/**
 * Test of Java streams over USB. Run the PC example, USBSend, to send data.
 * 
 * @author Lawrie Griffiths
 * adapted by Erik Nauman
 */
public class MechArm {

	public static void main(String[] args) throws Exception {
		getData();
	}

	public static void getData() throws Exception {
		LCD.clear();
		LCD.drawString("waiting for \ncommand", 0, 0);
		int b = 0;
		USBConnection conn = USB.waitForConnection();
		while (true) {
			DataOutputStream dOut = conn.openDataOutputStream();
			DataInputStream dIn = conn.openDataInputStream();
			// Drive robot = new Drive();

			try {
				b = dIn.readInt();
			} catch (EOFException e) {
				break;
			}

			dOut.writeInt(b);
			dOut.flush();

			switch (b) {
			case 1:
				LCD.clear();
				LCD.drawString("turn left", 0, 0);
				LCD.refresh();
				Motor.C.forward();
				Delay.msDelay(200);
				Motor.C.stop();
				
				break;
			case 2:
				LCD.clear();
				LCD.drawString("straight", 0, 0);
				Motor.B.forward();
				Delay.msDelay(200);
				Motor.B.stop();
				break;
			case 3:
				LCD.clear();
				LCD.drawString("reverse", 0, 0);
				Motor.B.backward();
				Delay.msDelay(200);
				Motor.B.stop();
				break;
			case 4:
				LCD.clear();
				LCD.drawString("Opening Claw", 0, 0);
				if(Motor.A.isMoving()) {
					
				}else {
					Motor.A.forward();
				}
				break;
			case 5:
				LCD.clear();
				LCD.drawString("turn right", 0, 0);
				Motor.C.backward();
				Delay.msDelay(200);
				Motor.C.stop();
				break;
			case 6:
				
				LCD.clear();
				LCD.drawString("Closing Connection", 0, 0);
				Delay.msDelay(200);
				Motor.C.stop();
				break;
			case 10:
				if(Motor.A.isMoving()) {
					Motor.A.stop();
				} else if(Motor.B.isMoving()) {
					Motor.B.stop();
				} else if(Motor.C.isMoving()) {
					Motor.C.stop();
				} else {}
				break;
			case 11:
				LCD.clear();
				LCD.drawString("Closing Claw", 0, 0);
				if(Motor.A.isMoving()) {
					
				}else {
					Motor.A.backward();
				}
				break;
			}
			
			LCD.clear();
			LCD.drawString("waiting for \ncommand", 0, 0);
			dOut.close();
			dIn.close();
			//if code 6 break out of loop
			if (b == 6)
				break;
		}//end while
		LCD.clear();
		LCD.drawString("connection \nclosed, \nbye!", 0, 0);
		Delay.msDelay(1000);
		conn.close();
	}//end void
}//end class