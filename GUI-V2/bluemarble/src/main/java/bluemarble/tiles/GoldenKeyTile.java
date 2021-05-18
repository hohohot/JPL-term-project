package bluemarble.tiles;

import java.awt.Graphics2D;

import javax.swing.ImageIcon;

import bluemarble.BlueMarble;
import bluemarble.card.Card;
import bluemarble.card.Deck;
import bluemarble.type.EventType;

public class GoldenKeyTile extends Tile {
	private final Deck deck;
	
	
	

	public GoldenKeyTile(String name, BlueMarble blueMarble, Deck deck) {
		super(name, blueMarble);
		this.deck = deck;
		super.tileImage = new ImageIcon(BlueMarble.class.getResource("/images/goldenKey.png")).getImage(); //
	}
	
	//---------------------------------------
	@Override
	public void screenDraw(Graphics2D g) {
		g.drawImage(tileImage, 100, 610, null);
		g.drawImage(tileImage, 100, 260, null);
		
		g.drawImage(tileImage, 240, 50, null);
		g.drawImage(tileImage, 590, 50, null);
		
		g.drawImage(tileImage, 800, 190, null);
		g.drawImage(tileImage, 800, 610, null);
		
		g.drawImage(tileImage, 450, 750, null);
		//골드 키 개수만큼 추가
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
		blueMarble.getGui().drawGoldenKeyCardView(card);
		return card.activateEffect();
	}

}
