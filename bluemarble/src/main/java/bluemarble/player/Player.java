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
	private final BlueMarble blueMarble;
	private String nameString;
	private Long position;
	private Long money;
	
	public abstract boolean selectBuyOrNot();
	public abstract BuildingType selectBuilding();
	public abstract int selectTile();
	public abstract int selectSellingTile();
	
	
	public void addMoney(Long money) {
		this.money = money;
	}
}
