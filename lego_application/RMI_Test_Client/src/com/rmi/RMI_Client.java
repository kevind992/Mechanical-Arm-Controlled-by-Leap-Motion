package com.rmi;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class RMI_Client {

	private MechService ms;

	public RMI_Client() throws MalformedURLException, RemoteException, NotBoundException {

		// Connecting to rmi server
		ms = (MechService) Naming.lookup("rmi://127.0.0.1:1099/mechservice");
		System.out.println("[info] - Connected to Mech Service..");
	}
	
	public void rmiSendCommand(int command) throws RemoteException {
		ms.rmiSendCommand(command);
	}

}
