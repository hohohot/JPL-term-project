package bluemarble.card;

import bluemarble.BlueMarble;
import bluemarble.type.EventType;
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
	
	public abstract EventType activateEffect();
}
