package bluemarble.tiles;

import bluemarble.BlueMarble;
import bluemarble.card.Card;
import bluemarble.card.Deck;
import bluemarble.type.EventType;

public class GoldenKeyTile extends Tile {
	private final Deck deck;
	
	
	

	public GoldenKeyTile(String name, BlueMarble blueMarble, Deck deck) {
		super(name, blueMarble);
		this.deck = deck;
	}

	@Override
	public EventType getStartEvent() {
		// TODO Auto-generated method stub
		return EventType.NONE;
	}

	@Override
	public EventType getPassByEvent() {
		// TODO Auto-generated method stub
		return EventType.NONE;
	}

	@Override
	public EventType getStopEvent() {
		Card card = deck.drawCard();
		blueMarble.getGui().drawGoldenKeyCardView(card);
		return card.activateEffect();
	}

}
