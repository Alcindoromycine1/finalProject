package main;

import javax.swing.JFrame;
import java.io.*;

/*
 * Noah Sussman, Akhilan Saravanan and Rudra Garg
 * Ms. Krasteva
 * April 2, 2025
 * Final Project ICS4U0
 */
public class Main {

	public static void main(String[] args) throws IOException {

		JFrame window = new JFrame();// creates a window object
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);// Prevents the user from resizing the window
		window.setTitle("Are We Cooked?");

		GamePanel gp = new GamePanel(window);
		window.add(gp);
		window.pack();// forces the JPanel to fit the preferred size

		window.setLocationRelativeTo(null);
		window.setVisible(true);// makes the window visible to the user

		gp.startGameThread();

	}

}
