package ie.gmit.sw;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import com.leapmotion.leap.Controller;
import com.leapmotion.leap.Frame;
import com.leapmotion.leap.Gesture;
import com.leapmotion.leap.GestureList;
import com.leapmotion.leap.Hand;
import com.leapmotion.leap.HandList;
import com.leapmotion.leap.Listener;
import com.leapmotion.leap.SwipeGesture;
import com.leapmotion.leap.Vector;
import com.rmi.RMI_Client;

public class LeapListener extends Listener {
	
	RMI_Client client;
	
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
        	}
        }

        GestureList gestures = frame.gestures();
        for (int i = 0; i < gestures.count(); i++) {
            Gesture gesture = gestures.get(i);

            switch (gesture.type()) {
//                case TYPE_CIRCLE:
//                    CircleGesture circle = new CircleGesture(gesture);
//
//                    // Calculate clock direction using the angle between circle normal and pointable
//                    String clockwiseness;
//                    if (circle.pointable().direction().angleTo(circle.normal()) <= Math.PI/2) {
//                        // Clockwise if angle is less than 90 degrees
//                        clockwiseness = "clockwise";
//                    } else {
//                        clockwiseness = "counterclockwise";
//                    }
//
//                    // Calculate angle swept since last frame
//                    double sweptAngle = 0;
//                    if (circle.state() != State.STATE_START) {
//                        CircleGesture previousUpdate = new CircleGesture(controller.frame(1).gesture(circle.id()));
//                        sweptAngle = (circle.progress() - previousUpdate.progress()) * 2 * Math.PI;
//                    }
//
//                    System.out.println("  Circle id: " + circle.id()
//                               + ", " + circle.state()
//                               + ", progress: " + circle.progress()
//                               + ", radius: " + circle.radius()
//                               + ", angle: " + Math.toDegrees(sweptAngle)
//                               + ", " + clockwiseness);
//                    break;
                case TYPE_SWIPE:
                    SwipeGesture swipe = new SwipeGesture(gesture);
                    
                    Vector direction = swipe.direction();
                    
                    System.out.println("Swipe!");
                    
                    if(direction.getX() > direction.getY()) {
                    	System.out.println("Horizontal swipe!");
                        float swipeDirection = direction.getX();
                        System.out.println("Swipe direction: = " + swipeDirection);
                        
                        if(swipeDirection > 0) {
                        	// right
                        	System.out.println("Swipe right!");
                        } else {
                        	// left
                        	System.out.println("Swipe left!");
                        }
                    	// horizontal
                    } else {
                    	System.out.println("Vertical swipe!");
                        float swipeDirection = direction.getY();
                        System.out.println("Swipe direction: = " + swipeDirection);
                        
                        if(swipeDirection > 0) {
                        	// up
                        	System.out.println("Swipe up!");
                        } else {
                        	// down
                        	System.out.println("Swipe down!");
                        }
                    	// vertical
                    }
//                    System.out.println("  Swipe id: " + swipe.id()
//                               + ", " + swipe.state()
//                               + ", position: " + swipe.position()
//                               + ", direction: " + swipe.direction()
//                               + ", speed: " + swipe.speed());
                    break;
//                case TYPE_SCREEN_TAP:
//                    ScreenTapGesture screenTap = new ScreenTapGesture(gesture);
//                    System.out.println("  Screen Tap id: " + screenTap.id()
//                               + ", " + screenTap.state()
//                               + ", position: " + screenTap.position()
//                               + ", direction: " + screenTap.direction());
//                    break;
//                case TYPE_KEY_TAP:
//                    KeyTapGesture keyTap = new KeyTapGesture(gesture);
//                    System.out.println("  Key Tap id: " + keyTap.id()
//                               + ", " + keyTap.state()
//                               + ", position: " + keyTap.position()
//                               + ", direction: " + keyTap.direction());
//                    break;
                default:
                    System.out.println("Unknown gesture type.");
                    break;
            }
        }

//        if (!frame.hands().isEmpty() || !gestures.isEmpty()) {
//            System.out.println();
//        }
    }
}
