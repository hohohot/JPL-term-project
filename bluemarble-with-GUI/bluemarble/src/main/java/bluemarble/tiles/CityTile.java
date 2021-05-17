package bluemarble.tiles;

import java.nio.channels.SelectableChannel;

import javax.swing.plaf.synth.SynthButtonUI;

import bluemarble.BlueMarble;
import bluemarble.gui.GUI;
import bluemarble.player.Player;
import bluemarble.type.BuildingType;
import bluemarble.type.EventType;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;



@Setter
@Getter
public class CityTile extends Tile {
	protected Player player;
	//protected final Long cost; //구매가
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
		Player currentPlayer = blueMarble.getCurrentPlayer(); // 현재 플레이어 객체
		if(this.player == null) { // 주인이 없으면
			//if(currentPlayer.getMoney() < this.cost) // 돈이 없으면
				//return EventType.NONE; // 끝, 아무일도 없음
			
			boolean isBuy = currentPlayer.selectBuyOrNot(); // 살지 말지 정하기(아직 구현 안된듯)
			System.out.println("isBuy: " +isBuy);
			if(isBuy) { // 산다면
				currentPlayer.addMoney(-this.cost); // 돈 빼기
				this.player = currentPlayer;  // 주인 설정
			}else 
				return EventType.NONE; // 끝, 아무일도 없다
		}else if(this.player != currentPlayer) { // 주인이 있는데 여기온 플레이어가 주인이 아니면
			int sellingResult = blueMarble.sellProperty(this.fee);
			
			if(sellingResult == -1) {
				this.player.addMoney(+currentPlayer.getMoney());				
			}else {
				currentPlayer.addMoney(-this.fee);
				System.out.println("second wallet: " + blueMarble.getCurrentPlayer().getMoney());
				this.player.addMoney(+this.fee);				
			}
		}
		return EventType.NONE;
	}
	
	public Long getSellingCost() {
		return this.cost;
	}
	
	
	public void initialize() {
		this.player = null;
	}
}
