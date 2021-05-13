package bluemarble.player;

import bluemarble.BlueMarble;
import bluemarble.gui.GUI;
import bluemarble.type.BuildingType;

public class LocalPlayer extends Player {

	public LocalPlayer(BlueMarble blueMarble) {
		super(blueMarble);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean selectBuyOrNot() {
		// TODO Auto-generated method stub
		return blueMarble.getGui().selectBuyOrNot();
	}

	@Override
	public BuildingType selectBuilding() {
		// TODO Auto-generated method stub
		return blueMarble.getGui().selectBuilding();
	}

	@Override
	public int selectSpaceTripTile() {
		// TODO Auto-generated method stub
		return blueMarble.getGui().selectSpaceTripTile();
	}

	@Override
	public int selectSellingTile() {
		// TODO Auto-generated method stub
		return blueMarble.getGui().selectSellingTile();
	}

}
