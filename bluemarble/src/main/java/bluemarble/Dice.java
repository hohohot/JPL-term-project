package bluemarble;

import java.util.Date;
import java.util.Random;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class Dice {
	public static final int DUBBLE = 1;
	public static final int NORAML = 0;
	private int val1;
	private int val2;
	
	public int rollDice() {
		Random random = new Random(new Date().getTime());
		val1 = random.nextInt()%6 + 1;
		val2 = random.nextInt()%6 + 1;
		if(val1 == val2) 
			return Dice.DUBBLE;
		return Dice.NORAML;
	}
	
	public int getValue() {
		return val1 + val2;
	}
}
