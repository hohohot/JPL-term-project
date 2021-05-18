package bluemarble.tiles;

import bluemarble.BlueMarble;
import bluemarble.player.Player;
import bluemarble.type.EventType;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class BenefitTile extends Tile {
	private Long fundedMoneyLong;
	public BenefitTile(String name, BlueMarble blueMarble) {
		super(name, blueMarble);
		// TODO Auto-generated constructor stub
		fundedMoneyLong = 0L;
	}

	@Override
	public EventType getStartEvent() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EventType getPassByEvent() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EventType getStopEvent() {
		// TODO Auto-generated method stub
		
		Player currentPlayer = blueMarble.getCurrentPlayer();
		
		currentPlayer.addMoney(fundedMoneyLong);
		fundedMoneyLong = 0L;
		
		return EventType.NONE;
	}

}
