package bluemarble.card;

import bluemarble.BlueMarble;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@RequiredArgsConstructor
public abstract class Card {
	private final BlueMarble blueMarble;
	private final String descriptionString;
	private final String titleString;
	
	public abstract int activateEffect();
}
