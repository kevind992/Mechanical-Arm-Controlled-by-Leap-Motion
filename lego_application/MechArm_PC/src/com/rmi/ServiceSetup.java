package com.rmi;

import java.rmi.registry.LocateRegistry;
import java.rmi.Naming;

public class ServiceSetup {
	public static void main(String[] args) throws Exception{
		MechService ms = new MechServiceImpl();
		
		LocateRegistry.createRegistry(1099);
		
		Naming.rebind("mechservice", ms);
		
		System.out.println("[info] - mech arm service running..");
	}
}
