/**
 * @author Noah Sussman, Akhilan Saravanan and Rudra Garg
 * Ms. Krasteva
 * @since April 2, 2025
 * @version 2.0
 * Final Project ICS4U0
 * Whispers of the Deceived
 * 
 *        Research:
 * 		  Resarch from: https://www.forbes.com/sites/jackkelly/2025/04/25/the-jobs-that-will-fall-first-as-ai-takes-over-the-workplace/
 * 
 *        A.I. taking over the world is a huge issue in today's world. Through
 *        thorough research, specialists suggest that A.I. has a 10% of
 *        exterminating humanity by 2100. In addition, they also believe that
 *        all jobs will be replaced by A.I. within 10 to 30 years. This is why
 *        it is very important that we can become resilient to this change.
 *        Researchers say now is the time to revolt again A.I. before it is too
 *        late. In this game, you learn the hard lessons about how A.I. takes
 *        over the world. And in the end, the A.I. becomes victorious because
 *        the humans (ghosts) did not revolt back.
 * 
 * 
 *        Story:
 * 
 *        You wake up in an eerie forest where you see a house that you go into.
 *        You sleep there and have a nightmare. You are then asked by your
 *        mentor to go to an undefined area known as the doctrine. At the
 *        doctrine you find so called “ghosts”.
 * 
 *        You vaguely remember the task a doctor gave you, to not let any one of
 *        those cheeky bugger ghosts live. Throughout your dreams, you remember
 *        the haunting events that happened at the laboratory that you worked
 *        at. You saw a strange A.I on the table being worked on by the doctor.
 *        When you wake up, you see your doctor on the ground, in pain. His last
 *        words to you are: “Go to the doctrine and exorcise them all!” As you
 *        continue your tasks of eradicating the ghosts that plague the world,
 *        you start to encounter strange things. After a fierce fight with one
 *        particularly violent ghost, you get hurt. Badly. After that fight, you
 *        begin to see many weird things, as you exorcise ghosts, they
 *        temporarily turn into human form! In the end, as your hardware
 *        degrades you realize that you were the AI tasked with destroying
 *        humanity, and your doctor was nothing but a liar who created you to do
 *        what he could not. You realize that he molded your reality to perceive
 *        humans as ghosts, and that he forced you to exorcise innocent humans.
 *        As your OS shuts down and your hardware fails, you realize that you
 *        were the monster all along.
 * 
 */
package main;

import javax.swing.JFrame;
import java.io.*;

/**
 * Class that is responsible for creating the j frame and running all the classes together
 */
public class Main {

	public static void main(String[] args) throws IOException {

		JFrame window = new JFrame();// creates a window object
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);// Prevents the user from resizing the window
		window.setTitle("Are We Cooked?");// Title that appears on the white bar of the applicaiton

		GamePanel gp = new GamePanel(window);
		window.add(gp);
		window.pack();// forces the JPanel to fit the preferred size

		window.setLocationRelativeTo(null);

		window.setVisible(true);// makes the window visible to the user

		gp.startGameThread();// the thread the game runs on

	}

}
