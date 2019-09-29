// HelloMVC: a simple MVC example
// the model is just a counter 
// inspired by code by Joseph Mack, http://www.austintek.com/mvc/

/**
 *  Two views with integrated controllers.  Uses java.util.Observ{er, able} instead
 *  of custom IView.
 */

import javax.swing.*;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.Timer;
import java.util.TimerTask;
//import java.util.Scanner;
public class main {

	public static void main(String[] args) {
		int FPS = 30;
		//Scanner in = new Scanner(System.in);
		//String s = in.nextLine();
		FPS = Integer.parseInt(args[0]);
		//Scanner speedin = new Scanner(System.in);
		//String speed = speedin.nextLine();
		int sspeed = Integer.parseInt(args[1]);
		JFrame frame = new JFrame("Breakout");
		int n = JOptionPane.showOptionDialog(frame, "ID 20648296 " + "Mingxi Zhang", "Press Yes to Start", JOptionPane.YES_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
		if (n == 0) {
			Model model = new Model();

			// create View, tell it about model (and controller)
			View view = new View(model);
			// tell Model about View.

			model.addObserver(view);


			model.notifyObservers();


			model.set_FPS(FPS);
			model.set_ballspeed(sspeed);
			JPanel p = new JPanel(new GridLayout(1, 1));

			frame.getContentPane().add(p);
			// add views (each view is a JPanel)
			p.add(view);
//
//		JPanel p2 = new JPanel();
//
//		p.add(p2);


			java.util.Timer timer = new Timer();
			TimerTask task = new TimerTask() {
				@Override
				public void run() {
					view.handle_animation();
				}
			};
			timer.schedule(task, 0, (1000 / FPS));
			//p.add(view2);
			// setup window
			frame.setPreferredSize(new Dimension(300, 300));
			frame.pack();
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setVisible(true);
		} else {
			System.exit(0);
		}
	}
}
