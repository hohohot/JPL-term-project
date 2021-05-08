package bluemarble.player;

import bluemarble.BlueMarble;
import bluemarble.type.BuildingType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public abstract class Player {
	protected final BlueMarble blueMarble;
	private String nameString;
	private Long position = 0L;
	private Long money = 0L;
	private boolean isBanckupted = false;
	
	
	public abstract boolean selectBuyOrNot();
	public abstract BuildingType selectBuilding();
	public abstract int selectSpaceTripTile();
	public abstract int selectSellingTile();
	
	
	public void addMoney(Long money) {
		this.money += money;
	}
}
