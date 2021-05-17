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
	//protected final Long cost; //���Ű�
	protected final Long fee; //�����
	
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
		Player currentPlayer = blueMarble.getCurrentPlayer(); // ���� �÷��̾� ��ü
		if(this.player == null) { // ������ ������
			//if(currentPlayer.getMoney() < this.cost) // ���� ������
				//return EventType.NONE; // ��, �ƹ��ϵ� ����
			
			boolean isBuy = currentPlayer.selectBuyOrNot(); // ���� ���� ���ϱ�(���� ���� �ȵȵ�)
			System.out.println("isBuy: " +isBuy);
			if(isBuy) { // ��ٸ�
				currentPlayer.addMoney(-this.cost); // �� ����
				this.player = currentPlayer;  // ���� ����
			}else 
				return EventType.NONE; // ��, �ƹ��ϵ� ����
		}else if(this.player != currentPlayer) { // ������ �ִµ� ����� �÷��̾ ������ �ƴϸ�
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
