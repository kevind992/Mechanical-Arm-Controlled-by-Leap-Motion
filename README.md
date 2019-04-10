# README
The following repository contains the project submission for 4th year module Gesture Based UI. The goal of the project was to develop an application with a Natural User Interface. We could develop the application using a language of our choice. We also had multiple different types of hardware that we could use. 
## Repository Structure
The repository contains the following
- The main project code is found within the application folder. This is broken up into the code which runs on the Lego computer brick 
and the code which runs on the PC.
- A pdf document which breaks down the research for the project.
- A video demonstrating the application working.
- A presentation 
- A licence
## Creating the Mechanical Arm
The Lego mechanical arm was created by following these build [instructions.](http://www.nxtprograms.com/robot_arm/steps.html)
## Running Application
To run the application the following sets need to be completed.
### Required Software
The following software need to be installed to run the application. **Note that 32bit versions of the software are required to work with the Lego Mindstorm Brick Computer**.
- [Java 8 32bit](https://www.oracle.com/technetwork/java/javase/downloads/java-archive-javase8-2177648.html)
- [Eclipse 32bit](https://www.eclipse.org/downloads/download.php?file=/technology/epp/downloads/release/photon/R/eclipse-java-photon-R-win32.zip)
- [Leap V2 Tracking](https://developer-archive.leapmotion.com/downloads/external/skeletal-beta/windows?version=2.3.1.31549)
### Eclipse and Lego Brick Computer Setup
To setup Eclipse and the Lego brick with leJOS, go to the following [link](http://www.lejos.org/nxt/nxj/tutorial/Preliminaries/GettingStarted.htm) and follow the instructions stated.
### Run NXT Application
Once the above software has been installed, clone the repository onto your PC and follow these steps to start the NXT application
- Open the [following](https://github.com/kevind992/gesture-based-ui-project/tree/master/application/MechArm_NXT) within Eclipse
- Connection the Lego NXT device to your PC via USB
- Right Click on the Project from within Eclipse, select "Run As" and then "leJOS NXT Program". After a couple of seconds the application should now be running on the NXT device.
### Run PC Application
- From within Eclipse open the [following](https://github.com/kevind992/gesture-based-ui-project/tree/master/application/MechArm_PC/src/com) PC application.
- Right click on the application and click "Build Path" - "Configure Build Path"
- From within the Libraries tab select "Add External Jars". You now to navigate to the location you downloaded the Leap V2 Tracking and select "LeapJava.jar" from within the LeapSDK folder
- While still within Configure Build Path settings you now need to select "JRE System Library", then select "Native Library Location" and click the "Edit" button on the right hand side. Like above navigate to the LeapSDK folder, then go into lib and select the x86
- Once the both instructions have been complete click "Apply and Close"
- Insert the Leap Motion Controller
- Run the application by right clicking on the application folder and select "Run As" and then "Java Application"
The Application should successfully connect with the Lego NXT device. 
