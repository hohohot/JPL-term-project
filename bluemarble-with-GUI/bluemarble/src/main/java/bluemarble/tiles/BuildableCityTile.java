package bluemarble.tiles;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.ImageIcon;

import bluemarble.BlueMarble;
import bluemarble.player.Player;
import bluemarble.type.BuildingType;
import bluemarble.type.EventType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class BuildableCityTile extends CityTile {
	private List<BuildingType> buildingList;
	final private Map<BuildingType, Long> buildingCostMap;
	final private Map<BuildingType, Long> buildingFeeMap;
	
	@Builder
	public BuildableCityTile(String name, BlueMarble blueMarble, Long cost, Long fee,
			Map<BuildingType, Long> buildingCostMap, Map<BuildingType, Long> buildingFeeMap) {
		super(name, blueMarble, cost, fee);
		this.buildingCostMap = buildingCostMap; // 비용
		this.buildingFeeMap = buildingFeeMap; // 요금
		this.buildingList = new ArrayList<BuildingType>(); // 건물목록
		
		//System.out.println(buildingCostMap.get(BuildingType.VILLA) + "yeah");
		//if(buildingCostMap.get(BuildingType.VILLA) == 150000L) {
			//System.out.println("boob");
			//super.tileImage = new ImageIcon(BlueMarble.class.getResource("/images/blueTile.png")).getImage();
		//}
	}

	@Override
	public EventType getStopEvent() {
		Player currentPlayer = blueMarble.getCurrentPlayer(); 
		if(this.player == null) {
			//if(currentPlayer.getMoney() < this.cost)
				//return EventType.NONE;
			
			boolean isBuy = currentPlayer.selectBuyOrNot();
			System.out.println("isBuy: " +isBuy);
			if(isBuy) {
				currentPlayer.addMoney(-this.cost);
				this.player = currentPlayer; 
				
				BuildingType selectedBuildingType = player.selectBuilding();
				if(selectedBuildingType != BuildingType.NONE) {
					player.addMoney(-buildingCostMap.get(selectedBuildingType));
					this.buildingList.add(selectedBuildingType);
				}
			}else 
				return EventType.NONE;
		}else if(this.player != currentPlayer) {
			int sellingResult = blueMarble.sellProperty(this.fee);
			
			if(sellingResult == -1) {
				this.player.addMoney(+currentPlayer.getMoney());				
			}else {
				currentPlayer.addMoney(-getFee());
				this.player.addMoney(getFee());				
			}
		}else {
			BuildingType selectedBuildingType = player.selectBuilding();
			if(selectedBuildingType != BuildingType.NONE){
				player.addMoney(-buildingCostMap.get(selectedBuildingType));
				this.buildingList.add(selectedBuildingType);
			}
		}
		return EventType.NONE;
	}
	
	@Override
	public Long getSellingCost() {
		Long sumLong = this.cost;
		for(BuildingType building : buildingList) {
			sumLong += this.buildingCostMap.get(building);
		}
		return sumLong;
	}
	
	@Override
	public void initialize() {
		this.player = null;
		buildingList.clear();
	}
	
	public Long getFee() {
		System.out.println((Long)buildingList.stream().map(p->buildingFeeMap.get(p)).reduce((a,b)->a+b).get());
		return this.fee + (Long)buildingList.stream().map(p->buildingFeeMap.get(p)).reduce((a,b)->a+b).get();
	}

}
