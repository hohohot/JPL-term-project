package bluemarble.card.childs;

import bluemarble.BlueMarble;
import bluemarble.card.Card;
import bluemarble.type.EventType;

public class SimpleCard extends Card {

	public SimpleCard(BlueMarble blueMarble) {
		super(blueMarble, "�����ݿ��� 10000 �߰�", "�뵷");
		// TODO Auto-generated constructor stub
	}

	@Override
	public EventType activateEffect() {
		blueMarble.getCurrentPlayer().addMoney(10000L);
		return EventType.NONE;
	}

}
