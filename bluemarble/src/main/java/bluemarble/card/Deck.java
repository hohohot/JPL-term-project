package bluemarble.card;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.print.attribute.Size2DSyntax;

import bluemarble.BlueMarble;
import bluemarble.card.childs.SimpleCard;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class Deck {
	private final BlueMarble blueMarble;
	protected List<Card> cards;
	protected int idx;
	
	public Deck(BlueMarble blueMarble) {
		super();
		this.blueMarble = blueMarble;
		idx = 0;
		cards = new ArrayList<Card>();
	}
	
	
	
	public Card drawCard() {
		if(idx >= cards.size()) {
			Collections.shuffle(cards);
			idx = 0;
		}
		
		return cards.get(idx++);
	}
	
	public void initialize() {
		cards.clear();
		idx = 0;
		
		for(int i = 0; i < 40; i++)
			cards.add(new SimpleCard(blueMarble));
		Collections.shuffle(cards);
	}

	
}
