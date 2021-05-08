package bluemarble.gui;

import bluemarble.BlueMarble;
import bluemarble.card.Card;
import bluemarble.tiles.BuildableCityTile;
import bluemarble.type.BuildingType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class GUI {
	private BlueMarble blueMarble;
	
	public void drawDice() {
		//주사위 그리기
	}
	
	public void drawBoardView() {
		//보드판 상황, 플레이어 상황 그리기
	}
	
	public void drawGoldenKeyCardView(Card card) {
		//뽑은 황금열쇠카드 정보 그리기
	}
	
	public boolean selectBuyOrNot() {
		//땅을 살지 안살지 선택
		return false;
	}
	
	public BuildingType selectBuilding() {
		//건물을 어떤 것을 지을지 선택. 안사기, 별장, 빌딩, 호텔중 선택, 잔액 부족시 선택 불가
		
		/* HOTEL의 건물 가격 조회
		 * BuildableCityTile currentTile = (BuildableCityTile)blueMarble.getTiles()
			.get(blueMarble.getCurrentPlayer().getPosition().intValue());
		currentTile.getBuildingCostMap().get(BuildingType.HOTEL);*/
		
		/* 플레이어 잔액 조회
		blueMarble.getCurrentPlayer().getMoney();
		*/

		
		return null;
	}
	
	public int selectSpaceTripTile() {
		//우주여행을 갈 타일 선택. 우주여행 타일은 선택 불가능.
		return 0; //타일의 idx 반환
	}
	
	
	public int selectSellingTile() {
		// 판매할 타일 선택. 선택할 타일 없을 시 -1 반환. 다른 경우 0반환.
		return -1;
	}
}
