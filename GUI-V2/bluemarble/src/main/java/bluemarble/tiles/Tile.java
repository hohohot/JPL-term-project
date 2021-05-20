package bluemarble.tiles;

import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.ImageIcon;

import bluemarble.BlueMarble;
import bluemarble.type.EventType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@RequiredArgsConstructor
public abstract class Tile {
	final private String name;
	final protected BlueMarble blueMarble;
	
	//--------------------------------------------
	public Image tileImage = new ImageIcon(BlueMarble.class.getResource("/images/basic3.png")).getImage();
	public Image clickableTileImage = new ImageIcon(BlueMarble.class.getResource("/images/click.png")).getImage();
	protected boolean rollDice = true;
	public Long cost;
	
	public int xPosition;
	public int yPosition;
	
	private int idx;
	
	public void screenDraw(Graphics2D g) {
		if(blueMarble.getGui().selectingTripTile)
			g.drawImage(clickableTileImage, xPosition, yPosition, null);
		else
			g.drawImage(tileImage, xPosition, yPosition, null);
	}
	//--------------------------------------------
	public boolean isInRange(int x, int y) {
		return x > xPosition && x < xPosition+70 && y > yPosition && y < yPosition + 70;
	}
	
	
	public abstract EventType getStartEvent();
	public abstract EventType getPassByEvent();
	public abstract EventType getStopEvent();
}
