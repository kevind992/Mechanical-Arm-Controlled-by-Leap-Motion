package com.lego;

import java.io.IOException;

import com.leapmotion.leap.CircleGesture;
import com.leapmotion.leap.Controller;
import com.leapmotion.leap.Finger;
import com.leapmotion.leap.Frame;
import com.leapmotion.leap.Gesture;
import com.leapmotion.leap.GestureList;
import com.leapmotion.leap.Hand;
import com.leapmotion.leap.Listener;
import com.leapmotion.leap.Pointable;
import com.leapmotion.leap.ScreenTapGesture;
import com.leapmotion.leap.SwipeGesture;
import com.leapmotion.leap.Vector;

public class LeapListener extends Listener {

	private ConnectionManager cm;

	public void onInit(Controller controller) {
		cm = new ConnectionManager();
		System.out.println("Initialized");
	}

	public void onConnect(Controller controller) {
		System.out.println("Connected");
		controller.enableGesture(Gesture.Type.TYPE_SWIPE);
		controller.enableGesture(Gesture.Type.TYPE_CIRCLE);
		controller.enableGesture(Gesture.Type.TYPE_SCREEN_TAP);
		controller.enableGesture(Gesture.Type.TYPE_KEY_TAP);
	}

	public void onDisconnect(Controller controller) {
		// Note: not dispatched when running in a debugger.
		System.out.println("Disconnected");
	}

	public void onExit(Controller controller) {
		System.out.println("Exited");
	}

	public void onFrame(Controller controller) {
		Frame frame = controller.frame();

		Hand furthestLeft = frame.hands().leftmost();
		Hand furthestRight = frame.hands().rightmost();

		if (furthestRight.isRight()) {
			float roll = furthestRight.palmNormal().roll();
			roll *= -1;

			if (furthestRight.grabStrength() > 0.90) {
				cm.sendCommand(11);
				System.out.println("Close Claw!");
			} else {
				// System.out.println("Stop Claw!");
				cm.sendCommand(10);
			}
			if (roll > 1) {
				System.out.println("Open Claw!");
				cm.sendCommand(4);
			}
		}

		if (furthestLeft.isLeft()) {
			
			float roll = furthestLeft.palmNormal().roll();
			roll *= -1;

			if (furthestLeft.grabStrength() > 0.90) {
				cm.sendCommand(12);
				System.out.println("Stop!");
			}
			
			GestureList gestures = frame.gestures();
			for (Gesture gesture : gestures) {
				//cm.sendCommand(12);
				switch (gesture.type()) {
//	        		
				case TYPE_SWIPE:
					SwipeGesture swipe = new SwipeGesture(gesture);

					Vector swipeVector = swipe.direction();
					float swipeDirectionY = swipeVector.getY();

					if (swipeDirectionY > 0) {
						System.out.println("Move Up");
						cm.sendCommand(2);
					} else if (swipeDirectionY < 0) {
						System.out.println("Move Down");
						cm.sendCommand(3);
					} else {
						cm.sendCommand(14);
					}

//        			System.out.println("Swipe Direction X: " + swipeDirectionX);
					break;

				case TYPE_CIRCLE:
					CircleGesture circle = new CircleGesture(gesture);

					String clockwiseness;
					if (circle.pointable().direction().angleTo(circle.normal()) <= Math.PI / 2) {
						clockwiseness = "clockwise";
						// move right
						System.out.println("Move right");
						cm.sendCommand(1);
					} else {
						clockwiseness = "counterclockwise";
						// move left
						System.out.println("Move left");
						cm.sendCommand(5);
					}

					// send command
					break;
				/*case TYPE_SCREEN_TAP:
					ScreenTapGesture tap = new ScreenTapGesture(gesture);

					Pointable tapPointable = tap.pointable();

					Finger tapFinger = new Finger(tapPointable);

					Finger.Type fingerType = tapFinger.type();

					if (fingerType.equals(Finger.Type.TYPE_INDEX)) {
						System.out.println("Move up");
						cm.sendCommand(12);
					}
					break;*/
				default:
					System.out.println("Unrecognised gesture!");
				}
			}
		}
	}

	public static void main(String[] args) {

		// Create a sample listener and controller
		LeapListener listener = new LeapListener();
		Controller controller = new Controller();

		// Have the sample listener receive events from the controller
		controller.addListener(listener);

		// Keep this process running until Enter is pressed
		System.out.println("Press Enter to quit...");
		try {
			System.in.read();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Remove the sample listener when done
		controller.removeListener(listener);
	}
}
