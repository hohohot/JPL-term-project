package bluemarble.tiles;

import java.awt.Graphics2D;
import java.awt.Image;
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
	
	// ````````````````````````
	public boolean vUnactiveButton = true;
	public boolean bUnactiveButton = true;
	public boolean hUnactiveButton = true;
	
	private Image villaImage = new ImageIcon(BlueMarble.class.getResource("/images/buildings/villaImage.png")).getImage();
	private Image buildingImage = new ImageIcon(BlueMarble.class.getResource("/images/buildings/buildingImage.png")).getImage();
	private Image hotelImage = new ImageIcon(BlueMarble.class.getResource("/images/buildings/hotelImage.png")).getImage();
	
	@Override
	public void screenDraw(Graphics2D g) {
		super.screenDraw(g);
		if(buildingList.contains(BuildingType.VILLA)) g.drawImage(villaImage, xPosition+5, yPosition+5, null);
		if(buildingList.contains(BuildingType.BUILDING)) g.drawImage(buildingImage, xPosition+5+20, yPosition+5, null);
		if(buildingList.contains(BuildingType.HOTEL)) g.drawImage(hotelImage, xPosition+5+20+20, yPosition+5, null);
	}
	// ````````````````````````
	
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
		System.out.println("owner : " + this.player);
		System.out.println("curr : " + currentPlayer);
		
		if(this.player == null) {
			//if(currentPlayer.getMoney() < this.cost)
				//return EventType.NONE;
			
			boolean isBuy = currentPlayer.selectBuyOrNot();
			System.out.println("isBuy: " +isBuy);
			if(isBuy) {
				currentPlayer.addMoney(-this.cost);
				this.player = currentPlayer; 
				
				BuildingType selectedBuildingType = player.selectBuilding();
				System.out.println("BuildingType: " + selectedBuildingType);
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
			System.out.println("BuildingType: " + selectedBuildingType);
			if(!selectedBuildingType.equals(BuildingType.NONE)){
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
		Long retVal = 0L;
		for(BuildingType building : buildingList) {
			retVal += buildingFeeMap.get(building);
		}
		return retVal;
	}

}
