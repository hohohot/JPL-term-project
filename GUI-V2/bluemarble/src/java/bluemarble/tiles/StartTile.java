package bluemarble.tiles;

import java.awt.Image;

import bluemarble.BlueMarble;
import bluemarble.player.Player;
import bluemarble.type.EventType;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class StartTile extends Tile {
	private final Long bonus;
	
	public StartTile(String name, BlueMarble blueMarble, Long bonus) {
		super(name, blueMarble);
		this.bonus = bonus;
	}

	@Override
	public EventType getStartEvent() {
		// TODO Auto-generated method stub
		return EventType.NONE;
	}

	@Override
	public EventType getPassByEvent() {
		// TODO Auto-generated method stub
		Player player = blueMarble.getCurrentPlayer();
		
		player.addMoney(bonus);
		
		return EventType.NONE;
	}

	@Override
	public EventType getStopEvent() {
		// TODO Auto-generated method stub
		return EventType.NONE;
	}

}
