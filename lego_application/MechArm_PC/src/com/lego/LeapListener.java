package com.lego;

import java.io.IOException;

import com.leapmotion.leap.Controller;
import com.leapmotion.leap.Frame;
import com.leapmotion.leap.Gesture;
import com.leapmotion.leap.GestureList;
import com.leapmotion.leap.Hand;
import com.leapmotion.leap.Listener;
import com.leapmotion.leap.SwipeGesture;
import com.rmi.MechService;

public class LeapListener extends Listener {
	
	private MechService mechClient;
	private ConnectionManager cm;
	
    public void onInit(Controller controller) {
    	cm = new ConnectionManager();
        System.out.println("Initialized");
    }

    public void onConnect(Controller controller) {
        System.out.println("Connected");
        controller.enableGesture(Gesture.Type.TYPE_SWIPE);
    }

    public void onDisconnect(Controller controller) {
        //Note: not dispatched when running in a debugger.
        System.out.println("Disconnected");
    }

    public void onExit(Controller controller) {
        System.out.println("Exited");
    }
    
    public void onFrame(Controller controller) {
    	Frame frame = controller.frame();
    	
    	Hand furthestLeft = frame.hands().leftmost();
    	Hand furthestRight = frame.hands().rightmost();
    	
    	if(furthestRight.isRight()) {
    		float roll = furthestRight.palmNormal().roll();
    		roll *= -1;
    		
        	if(furthestRight.grabStrength() > 0.90) {
        		cm.sendCommand(11);
        		System.out.println("Close Claw!");
        	} else {
        		System.out.println("Stop Claw!");
        		cm.sendCommand(10);
        	}
        	if(roll > 1){
        		System.out.println("Open Claw!");
        		cm.sendCommand(4);
        	}
        	
    	} else if(furthestLeft.isLeft()) {
    		GestureList gestures = frame.gestures();
        	for(Gesture gesture : gestures) {
        		if(gesture.type() == Gesture.Type.TYPE_SWIPE) {
        			SwipeGesture swipe = new SwipeGesture(gesture);
        			System.out.println("Swipe!");
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
