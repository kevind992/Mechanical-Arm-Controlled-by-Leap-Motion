package ie.gmit.sw;

import java.rmi.RemoteException;

import com.leapmotion.leap.Controller;
import com.leapmotion.leap.Frame;
import com.leapmotion.leap.Gesture;
import com.leapmotion.leap.GestureList;
import com.leapmotion.leap.Hand;
import com.leapmotion.leap.Listener;
import com.leapmotion.leap.SwipeGesture;
import com.rmi.MechService;
import com.rmi.RMI_Client;

public class LeapListener extends Listener {
	
    public void onInit(Controller controller) {
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
    		try {
    			if (furthestRight.grabStrength() > 0.90) {
        			new RMI_Client().rmiSendCommand(11);
    				System.out.println("Close Claw!");
    			} else {
    				System.out.println("Stop Claw!");
    				new RMI_Client().rmiSendCommand(10);
    			}
    			if (roll > 1) {
    				System.out.println("Open Claw!");
    				new RMI_Client().rmiSendCommand(4);
    			}
    		} catch (RemoteException e) {}
    		
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


}
