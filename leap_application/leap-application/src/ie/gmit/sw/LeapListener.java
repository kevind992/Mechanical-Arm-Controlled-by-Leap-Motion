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
	
<<<<<<< HEAD
	private MechService mechClient;
=======
	RMI_Client client;
>>>>>>> e74d64530553f5c96a71968616c55a2106705930
	
    public void onInit(Controller controller) {
        System.out.println("Initialized");
    	
        client = null;
        
    	try {
			client = new RMI_Client();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
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
<<<<<<< HEAD
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
=======
        // Get the most recent frame and report some basic information
        Frame frame = controller.frame();
//        System.out.println("Frame id: " + frame.id()
//                         + ", timestamp: " + frame.timestamp()
//                         + ", hands: " + frame.hands().count()
//                         + ", fingers: " + frame.fingers().count()
//                         + ", tools: " + frame.tools().count()
//                         + ", gestures " + frame.gestures().count());
        
        HandList hands = frame.hands();
//      
        for(Hand hand : hands) {
        	if(hand.isRight()) {
            	if(hand.grabStrength() > 0.99) {
            		System.out.println("Grab detected!");
            		try {
						client.rmiSendCommand(11);
						System.out.println("[INFO] - Sent command 11..");
					} catch (RemoteException e) {
						e.printStackTrace();
					}
            	} else {
            		System.out.println("Open hand!");
            		try {
						client.rmiSendCommand(4);
						System.out.println("[INFO] - Sent command 4..");
					} catch (RemoteException e) {
						e.printStackTrace();
					}
            	}
>>>>>>> e74d64530553f5c96a71968616c55a2106705930
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
