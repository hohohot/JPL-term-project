package bluemarble.card;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.FontMetrics;

import javax.swing.ImageIcon;

import bluemarble.BlueMarble;
import bluemarble.type.EventType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@RequiredArgsConstructor
public abstract class Card {
	protected final BlueMarble blueMarble;
	private final String descriptionString;
	private final String titleString;
	public Image cardImage = new ImageIcon(BlueMarble.class.getResource("/images/goldenKeyCard.png")).getImage();

	public abstract EventType activateEffect();

	public void screenDraw(Graphics2D g) {
		int xPosition = 200, yPosition = 190;
		int padding = 20;
		FontMetrics fm = g.getFontMetrics();
		g.drawImage(cardImage, xPosition, yPosition, null);
		g.drawString(titleString, xPosition+padding, yPosition+padding+fm.getAscent());
		g.drawString(descriptionString, xPosition+padding, yPosition+padding+2*fm.getAscent());
	}
}
