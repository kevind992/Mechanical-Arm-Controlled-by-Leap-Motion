package ie.gmit.sw;

import java.rmi.RemoteException;

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
import com.rmi.RMI_Client;

public class LeapListener extends Listener {
	
    public void onInit(Controller controller) {
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
//    		try {
    			if (furthestRight.grabStrength() > 0.90) {
//        			new RMI_Client().rmiSendCommand(11);
    				System.out.println("Close Claw!");
    			} else {
//    				System.out.println("Stop Claw!");
//    				new RMI_Client().rmiSendCommand(10);
    			}
    			if (roll > 1) {
    				System.out.println("Open Claw!");
//    				new RMI_Client().rmiSendCommand(4);
    			}
//    		} catch (RemoteException e) {}
    	}
    		
    	if(furthestLeft.isLeft()) {
    		GestureList gestures = frame.gestures();
        	for(Gesture gesture : gestures) {
        		switch(gesture.type()) {
//	        		case TYPE_SWIPE:
//	        			SwipeGesture swipe = new SwipeGesture(gesture);
//	        			
//	        			Vector swipeVector = swipe.direction();
//	        			float swipeDirectionX = swipeVector.getX();
//	        			
//	        			if(swipeDirectionX > 0) {
//	        				// 5
//	        				System.out.println("Move right");
//	        			} else if(swipeDirectionX < 0) {
//	        				// 1
//	        				System.out.println("Move left");
//	        			}
//	        			
////	        			System.out.println("Swipe Direction X: " + swipeDirectionX);
//	        			break;
	        		case TYPE_CIRCLE:
	        			CircleGesture circle = new CircleGesture(gesture);
	        			
	        			String clockwiseness;
	        			if (circle.pointable().direction().angleTo(circle.normal()) <= Math.PI/2) {
	        			        clockwiseness = "clockwise";
	        			        // move right
	        			       System.out.println("Move right");
	        			}
	        			else {
	        			    clockwiseness = "counterclockwise";
	        			    // move left
	        			    System.out.println("Move left");
	        			}
	        			
	        			// send command
	        			
	        		case TYPE_SCREEN_TAP:
	        			ScreenTapGesture tap = new ScreenTapGesture(gesture);
	        			
	        			Pointable tapPointable = tap.pointable();
	        			
	        			Finger tapFinger = new Finger(tapPointable);
	        			
	        			Finger.Type fingerType = tapFinger.type();
	        			
	        			
	        			if(fingerType.equals(Finger.Type.TYPE_INDEX)) {
	        				System.out.println("Move up");
	        				// move up
	        			}
	        			
	        			if(fingerType.equals(Finger.Type.TYPE_MIDDLE)) {
	        				System.out.println("Move down");
	        				// move down
	        			}
	        			
	        		default:
	        			System.out.println("Unrecognised gesture!");
        		}
        	}
    	}
    }
}
