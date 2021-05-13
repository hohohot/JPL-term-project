package bluemarble.tiles;

import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;

import bluemarble.BlueMarble;
import bluemarble.Dice;
import bluemarble.player.Player;
import bluemarble.type.EventType;


public class IslandTile extends Tile {
	private Map<Player,Long> countMap;
	private final Long maxWaitLong;

	public IslandTile(String name, BlueMarble blueMarble, Long maxWaitLong) {
		super(name, blueMarble);
		this.maxWaitLong = maxWaitLong;
		countMap = new HashMap();
		super.tileImage = new ImageIcon(BlueMarble.class.getResource("/images/island.png")).getImage();
	}

	@Override
	public EventType getStartEvent() {
		Dice dice = blueMarble.getDice();
		Player currentPlayer = blueMarble.getCurrentPlayer();
		
		if(countMap.get(currentPlayer) >= maxWaitLong) {
			countMap.remove(currentPlayer);
			return EventType.NONE;
		} else if(dice.isDouble()) {
			countMap.remove(currentPlayer);
			return EventType.REDICE;
		}
		countMap.put(currentPlayer, countMap.get(currentPlayer) + 1L);
		return EventType.STOP;
	}

	@Override
	public EventType getPassByEvent() {
		// TODO Auto-generated method stub
		return EventType.NONE;
	}

	@Override
	public EventType getStopEvent() {
		// TODO Auto-generated method stub
		countMap.put(blueMarble.getCurrentPlayer(), 0L);
		return EventType.NONE;
	}

}
