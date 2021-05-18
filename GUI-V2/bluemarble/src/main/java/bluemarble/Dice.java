package bluemarble;

import java.awt.Graphics2D;
import java.awt.Image;
import java.util.Date;
import java.util.Random;

import javax.swing.ImageIcon;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class Dice {
	public static final int DUBBLE = 1;
	public static final int NORAML = 0;
	private int val1 = 1; //
	private int val2 = 1; //
	private Random random = new Random(new Date().getTime());
	
	//----------------------------------
	public BlueMarble blueMarble;
	public Image dice1Image = new ImageIcon(BlueMarble.class.getResource("/images/dices/dice" + val1 + ".png")).getImage();
	public Image dice2Image = new ImageIcon(BlueMarble.class.getResource("/images/dices/dice" + val1 + ".png")).getImage();
	
	public void screenDraw(Graphics2D g) {
		g.drawImage(dice1Image, 240, 190, null);
		g.drawImage(dice2Image, 530, 190, null);
	}
	//----------------------------------
	
	public int rollDice() {
		val1 = ((random.nextInt())%6+12)%6 + 1;
		val2 = ((random.nextInt())%6+12)%6 + 1;
		
		//----------------------------------
		dice1Image = new ImageIcon(BlueMarble.class.getResource("/images/dices/dice" + val1 + ".png")).getImage();
		dice2Image = new ImageIcon(BlueMarble.class.getResource("/images/dices/dice" + val2 + ".png")).getImage();
		System.out.println("Dice val1: " + val1 + " Dice val2: " + val2);
		//----------------------------------
		
		if(val1 == val2) 
			return Dice.DUBBLE;
		return Dice.NORAML;
	}
	
	public int getValue() {
		return val1 + val2;
	}
	
	public boolean isDouble() {
		if(val1 == val2) System.out.println("Double!!");
		return val1 == val2;
	}
}
