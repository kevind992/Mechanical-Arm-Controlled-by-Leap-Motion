package com.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import com.lego.ConnectionManager;

public class MechServiceImpl  extends UnicastRemoteObject implements MechService{
	
	private static final long serialVersionUID = 1L;

	public MechServiceImpl() throws RemoteException {
		super();
	}

	@Override
	public void rmiSendCommand(int command) throws RemoteException {
		new ConnectionManager().sendCommand(command);
	}

}
