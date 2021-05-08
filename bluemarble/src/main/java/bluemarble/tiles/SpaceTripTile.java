package bluemarble.tiles;

import bluemarble.BlueMarble;
import bluemarble.player.Player;
import bluemarble.type.BuildingType;
import bluemarble.type.EventType;



public class SpaceTripTile extends Tile {
	private final CityTile spaceShipTile;
	private final Long spaceFee;
	
	

	public SpaceTripTile(String name, BlueMarble blueMarble, CityTile spaceShipTile, Long spaceFee) {
		super(name, blueMarble);
		this.spaceShipTile = spaceShipTile;
		this.spaceFee = spaceFee;
		rollDice = false;
	}

	@Override
	public EventType getStartEvent() {
		// TODO Auto-generated method stub
		Player currentPlayer = blueMarble.getCurrentPlayer();
		Player ownerPlayer = spaceShipTile.getPlayer();
		
		
		if(ownerPlayer != null && ownerPlayer != currentPlayer) {
			int sellingResult = blueMarble.sellBuilding(this.spaceFee);
			if(sellingResult == -1) {
				ownerPlayer.addMoney(currentPlayer.getMoney());				
			}else {
				currentPlayer.addMoney(-spaceFee);
				spaceShipTile.getPlayer().addMoney(spaceFee);				
			}
		}
		
		currentPlayer.addMoney(200000L);
		currentPlayer.setPosition((long)currentPlayer.selectSpaceTripTile());
		
		return EventType.SKIP_MOVE;
	}

	@Override
	public EventType getPassByEvent() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EventType getStopEvent() {
		// TODO Auto-generated method stub
		return null;
	}

}
