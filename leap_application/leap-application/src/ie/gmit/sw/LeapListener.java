package ie.gmit.sw;

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
    		
        	if(furthestRight.grabStrength() > 0.90) {
        		System.out.println("Grab!");
        	} 
        	
        	if(roll > 1){
        		System.out.println("Open!");
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
}
