package bluemarble.player;

import java.awt.Graphics2D;
import java.awt.Image;
import java.util.ArrayList;
import java.util.Optional;

import javax.swing.ImageIcon;

import bluemarble.BlueMarble;
import bluemarble.type.BuildingType;
import bluemarble.Pair;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public abstract class Player {
	protected final BlueMarble blueMarble;
	protected String nameString;
	protected Long position = 0L;
	protected Long money = 100000L;
	private boolean isBanckupted = false;
	
	//------------------------------------------
	public Image playerImage;
	public ArrayList<Pair> positionList;
	public int xPosition, xTemp;
	public int yPosition, yTemp;
	protected int id;
	
	public void screenDraw(Graphics2D g) {
		xPosition = positionList.get(Long.valueOf(Optional.ofNullable(position).orElse(0L)).intValue()).getX();
		yPosition = positionList.get(Long.valueOf(Optional.ofNullable(position).orElse(0L)).intValue()).getY();
		xTemp = xPosition;
		yTemp = yPosition;
		
		switch(nameString) {
		case "1":
			break;
		case "2":
			xTemp = xPosition + 30;
			break;
		case "3":
			yTemp = yPosition + 30;
			break;
		case "4":
			xTemp = xPosition + 30;
			yTemp = yPosition + 30;
			break;
		}
		
		g.drawImage(playerImage, xTemp, yTemp, null);
	}
	//------------------------------------------
	
	
	public abstract boolean selectBuyOrNot();
	public abstract BuildingType selectBuilding();
	public abstract int selectSpaceTripTile();
	public abstract int selectSellingTile();
	
	
	public void addMoney(Long money) {
		this.money += money;
	}
}
