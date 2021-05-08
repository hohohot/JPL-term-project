package bluemarble.card.childs;

import bluemarble.BlueMarble;
import bluemarble.card.Card;
import bluemarble.type.EventType;

public class SimpleCard extends Card {

	public SimpleCard(BlueMarble blueMarble) {
		super(blueMarble, "소지금에서 10000 추가", "용돈");
		// TODO Auto-generated constructor stub
	}

	@Override
	public EventType activateEffect() {
		blueMarble.getCurrentPlayer().addMoney(10000L);
		return EventType.NONE;
	}

}
