package bluemarble.card;

import java.util.List;

import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class Deck {
	protected List<Card> cards;
	protected int idx;
	
	public Card drawCard() {
		idx = idx % cards.size();
		return cards.get(idx++);
	}
}
