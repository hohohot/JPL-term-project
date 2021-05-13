package bluemarble.gui;

import bluemarble.BlueMarble;

public class GUIT extends Thread {
	BlueMarble blueMarble;
	GUI gui;
	boolean upDate = false;

	public GUIT(BlueMarble blueMarble) {
		this.blueMarble = blueMarble;
		gui = new GUI();
		gui.setBlueMarble(blueMarble);
		this.blueMarble.setGui(gui);
	}
	
	@Override
	public void run() {
		gui.setSafeStart(true);
		while(true) {
			try {
				Thread.sleep(5);
			} catch (Exception e) {
				e.printStackTrace();
			}
			if(gui.drawFinish) {
				gui.repaint();
				gui.drawFinish = false;
			}
		}
	}
}
