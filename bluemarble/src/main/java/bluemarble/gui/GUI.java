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
		//�ֻ��� �׸���
	}
	
	public void drawBoardView() {
		//������ ��Ȳ, �÷��̾� ��Ȳ �׸���
	}
	
	public void drawGoldenKeyCardView(Card card) {
		//���� Ȳ�ݿ���ī�� ���� �׸���
	}
	
	public boolean selectBuyOrNot() {
		//���� ���� �Ȼ��� ����
		return false;
	}
	
	public BuildingType selectBuilding() {
		//�ǹ��� � ���� ������ ����. �Ȼ��, ����, ����, ȣ���� ����, �ܾ� ������ ���� �Ұ�
		
		/* HOTEL�� �ǹ� ���� ��ȸ
		 * BuildableCityTile currentTile = (BuildableCityTile)blueMarble.getTiles()
			.get(blueMarble.getCurrentPlayer().getPosition().intValue());
		currentTile.getBuildingCostMap().get(BuildingType.HOTEL);*/
		
		/* �÷��̾� �ܾ� ��ȸ
		blueMarble.getCurrentPlayer().getMoney();
		*/

		
		return null;
	}
	
	public int selectSpaceTripTile() {
		//���ֿ����� �� Ÿ�� ����. ���ֿ��� Ÿ���� ���� �Ұ���.
		return 0; //Ÿ���� idx ��ȯ
	}
	
	
	public int selectSellingTile() {
		// �Ǹ��� Ÿ�� ����. ������ Ÿ�� ���� �� -1 ��ȯ. �ٸ� ��� 0��ȯ.
		return -1;
	}
}
