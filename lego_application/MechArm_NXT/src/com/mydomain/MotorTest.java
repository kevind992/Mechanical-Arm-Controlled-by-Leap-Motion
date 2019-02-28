package com.mydomain;

import lejos.nxt.*; //imports
import lejos.nxt.comm.*;
import lejos.robotics.navigation.*;
import java.io.*;

public class MotorTest {

	BTConnection btc;
	DataInputStream dis;
	DataOutputStream dos;
	int com; // for command
	DifferentialPilot controller;

	public static void main(String[] args) {
		MotorTest mot = new MotorTest();
		mot.controller = new DifferentialPilot(1.0f, 1.0f, Motor.A, Motor.B);
		mot.controller.setRotateSpeed(3);
		mot.start();
	}

	public void start() {

		try {
			LCD.drawString("waiting", 0, 0);
			LCD.refresh();
			btc = Bluetooth.waitForConnection();// wait until a connection is opened with the PC
			LCD.clear();
			LCD.drawString("connected", 0, 0);
			LCD.refresh();
			dis = btc.openDataInputStream(); // create the data input and output streams
			dos = btc.openDataOutputStream();

			while (true) {
				try {
					// Read keyboard command from user as a int
					com = dis.readInt();

					if (com == -1) {
						stop();
					} else if (com == 0) {
						dis.close();
						dos.close();
						LCD.clear();
						btc.close();
						start();// start the process of reconnecting again
					} else {
						LCD.clear();
						LCD.drawInt(com, 0, 0);

						if (com == 37) { // Rotate Right
							controller.setTravelSpeed(2);
							controller.rotateRight();
						} else if (com == 38) { // Move Arm down
							controller.setTravelSpeed(2);
							controller.forward();
						} else if (com == 39) { // Rotate Left
							controller.setTravelSpeed(2);
							controller.rotateLeft();
						} else if (com == 40) { // Move Arm Up
							controller.setTravelSpeed(2);
							controller.backward();
						} else { // Stop Motors
							controller.setTravelSpeed(0);
						}
					}
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		} catch (Exception e) {
		}
	}

	public void stop() {
		try {
			dis.close();// close all connections
			dos.close();
			Thread.sleep(100);
			LCD.clear();
			LCD.drawString("closing", 0, 0);
			LCD.refresh();
			btc.close();
			LCD.clear();
		}

		catch (Exception e) {
		}
	}

}
