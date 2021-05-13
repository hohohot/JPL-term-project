package bluemarble.tiles;

import java.awt.Image;

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
	
	private Image tileImage;
	protected boolean rollDice = true;
	
	
	
	public abstract EventType getStartEvent();
	public abstract EventType getPassByEvent();
	public abstract EventType getStopEvent();
}
