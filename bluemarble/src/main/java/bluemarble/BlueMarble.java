package bluemarble;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.plaf.TreeUI;
import javax.swing.plaf.basic.BasicGraphicsUtils;

import org.w3c.dom.traversal.NodeIterator;

import bluemarble.card.Card;
import bluemarble.card.Deck;
import bluemarble.gui.GUI;
import bluemarble.player.*;
import bluemarble.tiles.BenefitTile;
import bluemarble.tiles.BuildableCityTile;
import bluemarble.tiles.CityTile;
import bluemarble.tiles.FundingTile;
import bluemarble.tiles.GoldenKeyTile;
import bluemarble.tiles.IslandTile;
import bluemarble.tiles.SpaceTripTile;
import bluemarble.tiles.StartTile;
import bluemarble.tiles.Tile;
import bluemarble.type.BuildingType;
import bluemarble.type.EventType;
import lombok.Getter;
import lombok.Setter;
import lombok.RequiredArgsConstructor;

@Getter
@Setter
@RequiredArgsConstructor
public class BlueMarble {
	private final GUI gui;
	private final Long maxTurnLong;
	
	private List<Tile> tiles;
	private Deck deck;
	private List<Player> players;
	private Dice dice;
	private int currentTurn;
	private int nextTurn;
	private int turnCount;
	
	public int play() {
		turnCount = 0;
		nextTurn = 0;
		
		
		gui.drawBoardView();
		gui.drawDice();
		gui.drawGoldenKeyCardView(null);
		while(turnCount < maxTurnLong) {
			//initial draw
			gui.drawBoardView();
			
			
			turnCount++;
			currentTurn = nextTurn;

			Player player = getCurrentPlayer();
			//System.out.println(currentTurn + "turn /" + turnCount + "/"+ player.getMoney());

			Tile tileNow = this.tiles.get(player.getPosition().intValue());
			nextTurn = (currentTurn+1)%players.size();
			if(player.isBanckupted())
				continue;
			
			//�ֻ��� ������
			if(tileNow.isRollDice()) {
				dice.rollDice();
				if(dice.isDouble())
					nextTurn = currentTurn;
				gui.drawDice();
				System.out.println(dice.getValue());
			}
			
			
			//Ÿ�� �̺�Ʈ
			EventType startEvent = tileNow.getStartEvent();
			
			if(startEvent == EventType.REDICE) {
				dice.rollDice();
				if(dice.isDouble())
					nextTurn = currentTurn;
				gui.drawDice();
			}else if(startEvent == EventType.STOP) {
				continue;
			}
			
			
			//�ֻ����� ���� �̵�
			if(startEvent != EventType.SKIP_MOVE) {
				int move = dice.getValue();
				int pos = player.getPosition().intValue();
				while(move > 0) {
					move--;
					pos = (pos+1)%tiles.size();
					EventType passbyEvent = tiles.get(pos).getPassByEvent();
					/* event ó��*/
				}
				player.setPosition((long)pos);
			}
			
			gui.drawBoardView();
			
			//���� �̺�Ʈ
			EventType stopEvent = tiles.get(player.getPosition().intValue()).getStopEvent();

		}

		return -1;
	}
	
	public Map<BuildingType, Long> getBuildingMap(Long vila, Long building, Long hotel){
		return new HashMap<BuildingType, Long>(){{
			put(BuildingType.VILLA, vila);
			put(BuildingType.BUILDING, building);
			put(BuildingType.HOTEL, hotel);
		}};
	}
	
	
	public Player getCurrentPlayer() {
		return players.get(currentTurn);
	}
	
	
	public int sellProperty(Long target) {
		Player player = getCurrentPlayer();
		while(player.getMoney() < target) {
			int tile = player.selectSellingTile();
			if(tile == -1) {
				bankruptCurrentPlayer();
				return -1;
			}
			player.addMoney(((CityTile)this.tiles.get(tile)).getSellingCost());
			((CityTile)this.tiles.get(tile)).initialize();
		}
		return 0;
	}
	
	
	public void bankruptCurrentPlayer() {
		this.getCurrentPlayer().setBanckupted(true);
	}
	
	
	public void initTile(Deck deck) {
		Map<BuildingType, Long> redLineBuildingCostMap = getBuildingMap(50000L, 150000L, 250000L);
		Map<BuildingType, Long> yellowLineBuildingCostMap = getBuildingMap(100000L, 300000L, 500000L);
		Map<BuildingType, Long> greenLineBuildingCostMap = getBuildingMap(150000L, 450000L, 750000L);
		Map<BuildingType, Long> blueLineBuildingCostMap = getBuildingMap(200000L, 600000L, 1000000L);
		GoldenKeyTile goldenKeyTile = new GoldenKeyTile("Ȳ�ݿ���", this, deck);
		tiles = new ArrayList<Tile>();
		
		
		
		tiles.add(new StartTile("���", this, 200000L));
		
		//red line
		tiles.add(BuildableCityTile.builder()
				.name("Ÿ�̺���")
				.blueMarble(this)
				.cost(50000L)
				.fee(2000L)
				.buildingCostMap(redLineBuildingCostMap)
				.buildingFeeMap(getBuildingMap(10000L, 90000L, 250000L))
				.build()
				);
		tiles.add(goldenKeyTile);
		tiles.add(BuildableCityTile.builder()
				.name("����¡")
				.blueMarble(this)
				.cost(80000L)
				.fee(4000L)
				.buildingCostMap(redLineBuildingCostMap)
				.buildingFeeMap(getBuildingMap(20000L, 180000L, 450000L))
				.build()
				);
		tiles.add(BuildableCityTile.builder()
				.name("���Ҷ�")
				.blueMarble(this)
				.cost(80000L)
				.fee(4000L)
				.buildingCostMap(redLineBuildingCostMap)
				.buildingFeeMap(getBuildingMap(20000L, 180000L, 450000L))
				.build()
				);
		tiles.add(new CityTile("���ֵ�", this, 200000L, 300000L));
		tiles.add(BuildableCityTile.builder()
				.name("���Ҷ�")
				.blueMarble(this)
				.cost(100000L)
				.fee(6000L)
				.buildingCostMap(redLineBuildingCostMap)
				.buildingFeeMap(getBuildingMap(30000L, 270000L, 550000L))
				.build()
				);
		tiles.add(goldenKeyTile);
		tiles.add(BuildableCityTile.builder()
				.name("ī�̷�")
				.blueMarble(this)
				.cost(100000L)
				.fee(6000L)
				.buildingCostMap(redLineBuildingCostMap)
				.buildingFeeMap(getBuildingMap(30000L, 270000L, 550000L))
				.build()
				);
		tiles.add(BuildableCityTile.builder()
				.name("�̽�ź��")
				.blueMarble(this)
				.cost(120000L)
				.fee(8000L)
				.buildingCostMap(redLineBuildingCostMap)
				.buildingFeeMap(getBuildingMap(40000L, 300000L, 600000L))
				.build()
				);
		
		tiles.add(new IslandTile("���ε�", this, 3L));
		
		//yellow line
		tiles.add(BuildableCityTile.builder()
				.name("�̽�ź��")
				.blueMarble(this)
				.cost(140000L)
				.fee(10000L)
				.buildingCostMap(yellowLineBuildingCostMap)
				.buildingFeeMap(getBuildingMap(50000L, 450000L, 750000L))
				.build()
				);
		tiles.add(goldenKeyTile);
		tiles.add(BuildableCityTile.builder()
				.name("�����ϰ�")
				.blueMarble(this)
				.cost(160000L)
				.fee(12000L)
				.buildingCostMap(yellowLineBuildingCostMap)
				.buildingFeeMap(getBuildingMap(60000L, 500000L, 900000L))
				.build()
				);
		tiles.add(BuildableCityTile.builder()
				.name("����Ȧ��")
				.blueMarble(this)
				.cost(160000L)
				.fee(12000L)
				.buildingCostMap(yellowLineBuildingCostMap)
				.buildingFeeMap(getBuildingMap(60000L, 500000L, 900000L))
				.build()
				);
		tiles.add(new CityTile("���ڵ忩����", this, 200000L, 300000L));
		tiles.add(BuildableCityTile.builder()
				.name("����")
				.blueMarble(this)
				.cost(180000L)
				.fee(14000L)
				.buildingCostMap(yellowLineBuildingCostMap)
				.buildingFeeMap(getBuildingMap(70000L, 500000L, 950000L))
				.build()
				);
		tiles.add(goldenKeyTile);
		tiles.add(BuildableCityTile.builder()
				.name("������")
				.blueMarble(this)
				.cost(180000L)
				.fee(14000L)
				.buildingCostMap(yellowLineBuildingCostMap)
				.buildingFeeMap(getBuildingMap(70000L, 500000L, 950000L))
				.build()
				);
		tiles.add(BuildableCityTile.builder()
				.name("��Ÿ��")
				.blueMarble(this)
				.cost(200000L)
				.fee(16000L)
				.buildingCostMap(yellowLineBuildingCostMap)
				.buildingFeeMap(getBuildingMap(80000L, 550000L, 1000000L))
				.build()
				);
		
		
		BenefitTile benefitTile = new BenefitTile("��ȸ������� ����ó", this);
		tiles.add(benefitTile);
		
		
		//green line
		tiles.add(BuildableCityTile.builder()
				.name("�ο��뽺 ���̷���")
				.blueMarble(this)
				.cost(220000L)
				.fee(18000L)
				.buildingCostMap(greenLineBuildingCostMap)
				.buildingFeeMap(getBuildingMap(90000L, 700000L, 1050000L))
				.build()
				);
		tiles.add(goldenKeyTile);
		tiles.add(BuildableCityTile.builder()
				.name("���Ŀ��")
				.blueMarble(this)
				.cost(240000L)
				.fee(20000L)
				.buildingCostMap(greenLineBuildingCostMap)
				.buildingFeeMap(getBuildingMap(100000L, 750000L, 1100000L))
				.build()
				);
		tiles.add(BuildableCityTile.builder()
				.name("�õ��")
				.blueMarble(this)
				.cost(240000L)
				.fee(20000L)
				.buildingCostMap(greenLineBuildingCostMap)
				.buildingFeeMap(getBuildingMap(100000L, 750000L, 1100000L))
				.build()
				);
		tiles.add(new CityTile("�λ�", this, 500000L, 600000L));
		tiles.add(BuildableCityTile.builder()
				.name("�Ͽ���")
				.blueMarble(this)
				.cost(260000L)
				.fee(22000L)
				.buildingCostMap(greenLineBuildingCostMap)
				.buildingFeeMap(getBuildingMap(110000L, 800000L, 1150000L))
				.build()
				);
		tiles.add(BuildableCityTile.builder()
				.name("������")
				.blueMarble(this)
				.cost(260000L)
				.fee(22000L)
				.buildingCostMap(greenLineBuildingCostMap)
				.buildingFeeMap(getBuildingMap(110000L, 800000L, 1150000L))
				.build()
				);
		tiles.add(goldenKeyTile);
		tiles.add(BuildableCityTile.builder()
				.name("���帮��")
				.blueMarble(this)
				.cost(280000L)
				.fee(24000L)
				.buildingCostMap(greenLineBuildingCostMap)
				.buildingFeeMap(getBuildingMap(120000L, 850000L, 1200000L))
				.build()
				);
		
		
		CityTile spaceShipTile = new CityTile("�÷����ȣ", this, 450000L, 400000L);
		tiles.add(new SpaceTripTile("���ֿ���", this, spaceShipTile, 200000L));
		
		
		//blue line
		tiles.add(BuildableCityTile.builder()
				.name("����")
				.blueMarble(this)
				.cost(300000L)
				.fee(26000L)
				.buildingCostMap(greenLineBuildingCostMap)
				.buildingFeeMap(getBuildingMap(130000L, 90000L, 1270000L))
				.build()
				);
		tiles.add(spaceShipTile);
		tiles.add(BuildableCityTile.builder()
				.name("�ĸ�")
				.blueMarble(this)
				.cost(320000L)
				.fee(28000L)
				.buildingCostMap(greenLineBuildingCostMap)
				.buildingFeeMap(getBuildingMap(150000L, 100000L, 1400000L))
				.build()
				);
		tiles.add(BuildableCityTile.builder()
				.name("�θ�")
				.blueMarble(this)
				.cost(320000L)
				.fee(28000L)
				.buildingCostMap(greenLineBuildingCostMap)
				.buildingFeeMap(getBuildingMap(150000L, 100000L, 1400000L))
				.build()
				);
		tiles.add(goldenKeyTile);
		tiles.add(BuildableCityTile.builder()
				.name("����")
				.blueMarble(this)
				.cost(350000L)
				.fee(35000L)
				.buildingCostMap(greenLineBuildingCostMap)
				.buildingFeeMap(getBuildingMap(170000L, 110000L, 1500000L))
				.build()
				);
		tiles.add(BuildableCityTile.builder()
				.name("����")
				.blueMarble(this)
				.cost(350000L)
				.fee(35000L)
				.buildingCostMap(greenLineBuildingCostMap)
				.buildingFeeMap(getBuildingMap(170000L, 110000L, 1500000L))
				.build()
				);
		tiles.add(new FundingTile("��ȸ������� ����ó", this, benefitTile, 150000L));
		tiles.add(new CityTile("����", this, 1000000L, 2000000L));
	}
	
	
	
	
	public static void main(String args[]) {
		System.out.println("hello!");
		
		
		//gui ����
		GUI gui = new GUI();
		BlueMarble blueMarble = new BlueMarble(gui, 1000L);
		gui.setBlueMarble(blueMarble);
		Deck deck = new Deck(blueMarble);
		deck.initialize();
		
		blueMarble.setDeck(deck);
		blueMarble.setDice(new Dice());
		blueMarble.initTile(deck);
		List<Player> players = new ArrayList<Player>() {{
			add(new LocalPlayer(blueMarble));
			add(new LocalPlayer(blueMarble));
			add(new LocalPlayer(blueMarble));
			add(new LocalPlayer(blueMarble));
		}};
		blueMarble.setPlayers(players);
		
		
		blueMarble.play();
		
		for(Player player : blueMarble.getPlayers()) {
			System.out.println(player.isBanckupted());
			System.out.println(player.getMoney());
			System.out.println(player.getPosition());
		}
		
		System.out.println(blueMarble.getTurnCount());
		
		
	}
}
