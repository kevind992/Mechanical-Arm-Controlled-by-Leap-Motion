package com.test;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.*;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import javax.swing.*;

import com.lego.ConnectionManager;

public class KeyboardManager extends JFrame implements KeyListener, ActionListener {

	private static final long serialVersionUID = 1L;
	JTextArea displayArea; // output messages
	JTextField typingArea; // needed for keyListener
	static final String newline = System.getProperty("line.separator");
	private ConnectionManager cm;

	public static void main(String[] args) {

		/* Use an appropriate Look and Feel */
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
		} catch (UnsupportedLookAndFeelException ex) {
			ex.printStackTrace();
		} catch (IllegalAccessException ex) {
			ex.printStackTrace();
		} catch (InstantiationException ex) {
			ex.printStackTrace();
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
		}
		/* Turn off metal's use of bold fonts */
		UIManager.put("swing.boldMetal", Boolean.FALSE);

		// Schedule a job for event dispatch thread:
		// creating and showing this application's GUI.
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				try {
					createAndShowGUI();
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NotBoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}

	private static void createAndShowGUI() throws MalformedURLException, RemoteException, NotBoundException {
		// Create and set up the window.
		KeyboardManager frame = new KeyboardManager("test");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Set up the content pane.
		frame.addComponentsToPane();

		// Display the window.
		frame.pack();
		frame.setVisible(true);
	}

	private void addComponentsToPane() {

		JButton button = new JButton("Clear");
		button.addActionListener(this);

		typingArea = new JTextField(20);
		typingArea.addKeyListener(this);

		displayArea = new JTextArea();
		displayArea.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(displayArea);
		scrollPane.setPreferredSize(new Dimension(375, 125));

		getContentPane().add(typingArea, BorderLayout.PAGE_START);
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		getContentPane().add(button, BorderLayout.PAGE_END);

		displayArea.setText("hit c to close session" + newline);
	}

	public KeyboardManager(String name) throws MalformedURLException, RemoteException, NotBoundException {
		super(name);
		cm = new ConnectionManager();
	}

	/** Handle the key typed event from the text field. */
	public void keyTyped(KeyEvent e) {
		if (e.getKeyChar() == ' ') {
			displayInfo(e, "stop (space)");
			// sender.sendCommand(4);
			// rmi.rmiSendCommand(4);
		}
		if (e.getKeyChar() == 'q') {
			displayInfo(e, "closing connection");
			try {
				cm.sendCommand(6);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			// sender.sendCommand(6);
		}
	}

	/** Handle the key pressed event from the text field. */
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			displayInfo(e, "back");
			// sender.sendCommand(3);
			try {
				cm.sendCommand(3);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} else if (e.getKeyCode() == KeyEvent.VK_UP) {
			displayInfo(e, "forward");
			try {
				cm.sendCommand(2);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			// sender.sendCommand(2);
		} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			displayInfo(e, "left");
			try {
				cm.sendCommand(5);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			// sender.sendCommand(5);
		} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			displayInfo(e, "right");
			try {
				cm.sendCommand(1);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();

			}
			// sender.sendCommand(1);
		} else if (e.getKeyCode() == KeyEvent.VK_O) {
			displayInfo(e, "Open");
			try {
				cm.sendCommand(4);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			// sender.sendCommand(4);
		} else if (e.getKeyCode() == KeyEvent.VK_C) {
			displayInfo(e, "Close");
			try {
				cm.sendCommand(11);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			// sender.sendCommand(11);
		} else if (e.getKeyCode() == KeyEvent.VK_S) {
			displayInfo(e, "Close");
			try {
				cm.sendCommand(10);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			// sender.sendCommand(11);
		}
	}

	/** Handle the button click. */
	public void actionPerformed(ActionEvent e) {
		// Clear the text components.
		displayArea.setText("hit c to close session" + newline);
		typingArea.setText("");

		// Return the focus to the typing area.
		typingArea.requestFocusInWindow();
	}

	private void displayInfo(KeyEvent e, String keyStatus) {

		// You should only rely on the key char if the event
		// is a key typed event.
		int id = e.getID();
		String keyString;
		if (id == KeyEvent.KEY_TYPED) {
			keyString = " ";
		} else {
			int keyCode = e.getKeyCode();
			keyString = " (" + KeyEvent.getKeyText(keyCode) + ")";
		}

		displayArea.append(keyStatus + keyString + newline);
		displayArea.setCaretPosition(displayArea.getDocument().getLength());
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}
}