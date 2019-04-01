package com.rmi;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class RMI_Client implements MechService {

	private MechService ms;
	
	public RMI_Client() throws RemoteException {
		super();
		try {
			this.ms = (MechService) Naming.lookup("rmi://127.0.0.1:1099/mechservice");
			System.out.println("[info] - Connected to Mech Service..");
		} catch (MalformedURLException | RemoteException | NotBoundException e) {
			e.printStackTrace();
		}
	}
	
	public void rmiSendCommand(int command) throws RemoteException {
		ms.rmiSendCommand(command);
	}
}