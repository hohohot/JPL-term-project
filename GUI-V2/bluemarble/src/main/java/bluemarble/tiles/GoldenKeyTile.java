package bluemarble.tiles;

import java.awt.Graphics2D;

import javax.swing.ImageIcon;

import bluemarble.BlueMarble;
import bluemarble.card.Card;
import bluemarble.card.Deck;
import bluemarble.player.LocalPlayer;
import bluemarble.type.EventType;

public class GoldenKeyTile extends Tile {
	private final Deck deck;
	
	
	

	public GoldenKeyTile(String name, BlueMarble blueMarble, Deck deck) {
		super(name, blueMarble);
		this.deck = deck;
		this.tileImage = new ImageIcon(BlueMarble.class.getResource("/images/goldenKey.png")).getImage(); //
	}
	
	//---------------------------------------

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
		if(blueMarble.getCurrentPlayer() instanceof LocalPlayer)
			blueMarble.getGui().drawGoldenKeyCardView(card);
		return card.activateEffect();
	}

}
