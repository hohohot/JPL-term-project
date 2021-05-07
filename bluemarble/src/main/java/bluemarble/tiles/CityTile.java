package bluemarble.tiles;

import java.nio.channels.SelectableChannel;

import javax.swing.plaf.synth.SynthButtonUI;

import bluemarble.BlueMarble;
import bluemarble.gui.GUI;
import bluemarble.player.Player;
import bluemarble.type.BuildingType;
import bluemarble.type.EventType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;



@Setter
@Getter
public class CityTile extends Tile {
	protected Player player;
	protected final Long cost; //구매가
	protected final Long fee; //통행료
	
	
	public CityTile(String name, BlueMarble blueMarble, Long cost, Long fee) {
		super(name, blueMarble);
		this.cost = cost;
		this.fee = fee;
	}


	@Override
	public EventType getStartEvent() {
		// TODO Auto-generated method stub
		return EventType.NONE;
	}


	@Override
	public EventType getPassByEvent() {
		// TODO Auto-generated method stub
		return EventType.NONE;
	}
	
	
	@Override
	public EventType getStopEvent() {
		Player currentPlayer = blueMarble.getCurrentPlayer(); 
		if(this.player == null) {
			if(currentPlayer.getMoney() < this.cost)
				return EventType.NONE;
			
			boolean isBuy = currentPlayer.selectBuyOrNot();
			if(isBuy) {
				currentPlayer.addMoney(-this.cost);
				this.player = currentPlayer; 
			}else 
				return EventType.NONE;
		}else if(this.player != currentPlayer) {
			int sellingResult = blueMarble.sellBuilding(this.fee);
			
			if(sellingResult == -1) {
				this.player.addMoney(+currentPlayer.getMoney());				
			}else {
				currentPlayer.addMoney(-this.fee);
				this.player.addMoney(+this.fee);				
			}
		}
		return EventType.NONE;
	}
	
	
	public void initialize() {
		this.player = null;
	}
}
