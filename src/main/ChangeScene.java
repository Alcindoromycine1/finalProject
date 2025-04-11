package main;

import java.awt.Graphics2D;

public class ChangeScene {
	public int xWidth, yWidth;

	public ChangeScene(int xWidth, int yWidth) {
		this.xWidth = xWidth;
		this.yWidth = yWidth;
	}

	public void fadeStart(Graphics2D g, int scene1, int scene2) {
		g.fillRect(0, 0, xWidth, yWidth);
		for (int i = 0; i < 255; i++) {
			try {
				Thread.sleep(100);
				g.setColor(new java.awt.Color(0, 0, 0, i));
				g.fillRect(0, 0, xWidth, yWidth);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
	}
	
	public void changeScene (Maps m) {
		//System.out.println("Start chenge sance");
		m.changeMap(2);
		//System.out.println("end chenge sance");
		
	}
	
}

