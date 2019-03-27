package com.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface MechService extends Remote{
	public void rmiSendCommand(int command) throws RemoteException;
}