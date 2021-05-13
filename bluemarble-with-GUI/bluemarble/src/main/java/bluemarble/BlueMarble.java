package bluemarble;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.swing.ImageIcon;
import javax.swing.plaf.TreeUI;
import javax.swing.plaf.basic.BasicGraphicsUtils;

import org.w3c.dom.traversal.NodeIterator;

import bluemarble.card.Card;
import bluemarble.card.Deck;
import bluemarble.gui.GUI;
import bluemarble.gui.GUIT;
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
	//---------------------------------------
	public static final int SCREEN_WIDTH = 1280;
	public static final int SCREEN_HEIGHT = 850;
	public ArrayList<Pair> positionList = new ArrayList<>();
	
	//private final GUI gui;
	public GUI gui;
	//---------------------------------------
	
	private final Long maxTurnLong;
	
	private List<Tile> tiles;
	private Deck deck;
	private List<Player> players;
	private Dice dice;
	private int currentTurn;
	private int nextTurn;
	private int turnCount;
	
	public void setPosition() {
		int x = 100, y = 750;
		
		for(int i = 0; i < 10; i++) {
			positionList.add(new Pair(x, y));
			tiles.get(i).setXPosition(x);
			tiles.get(i).setYPosition(y);
			y -= 70;
		}
		
		for(int i = 10; i < 20; i++) {
			positionList.add(new Pair(x, y));
			tiles.get(i).setXPosition(x);
			tiles.get(i).setYPosition(y);
			x += 70;
		}
		
		for(int i = 20; i < 30; i++) {
			positionList.add(new Pair(x, y));
			tiles.get(i).setXPosition(x);
			tiles.get(i).setYPosition(y);
			y += 70;
		}
		
		for(int i = 30; i < 40; i++) {
			positionList.add(new Pair(x, y));
			tiles.get(i).setXPosition(x);
			tiles.get(i).setYPosition(y);
			x -= 70;
		}
	}
	
	public int play() {
		turnCount = 0;
		nextTurn = 0;
		
		
		gui.drawBoardView();
		//gui.drawDice();
		gui.drawGoldenKeyCardView(null);
		while(turnCount < maxTurnLong) {
			//initial draw
			gui.drawBoardView();
			
			
			turnCount++;
			currentTurn = nextTurn;

			Player player = getCurrentPlayer();
			
			System.out.println("Current Player: " + player.getNameString()); //
			//System.out.println(currentTurn + "turn /" + turnCount + "/"+ player.getMoney());

			Tile tileNow = this.tiles.get(player.getPosition().intValue());
			nextTurn = (currentTurn+1)%players.size();
			if(player.isBanckupted())
				continue;
			
			//주사위 던지기
			if(tileNow.isRollDice()) {
				
				//System.out.println(dice.getValue());
				gui.drawDice(); // roll 버튼이 눌러야 무한 루프 탈출하고 play 진행
				
				dice.rollDice();
				//System.out.println(dice.getValue());
				if(dice.isDouble()) {
					nextTurn = currentTurn;
				}
				//gui.drawDice();
				System.out.println("Dice Sum: " +dice.getValue());
			}
			
			
			//타일 이벤트
			EventType startEvent = tileNow.getStartEvent();
			
			if(startEvent == EventType.REDICE) {
				//----------------------------------------
				// 주사위가 double일 경우에는 roll 버튼을 누를 필요가 없으니깐
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				//----------------------------------------
				dice.rollDice();
				if(dice.isDouble())
					nextTurn = currentTurn;
				//gui.drawDice();
			}else if(startEvent == EventType.STOP) {
				continue;
			}
			
			
			//주사위에 의한 이동
			if(startEvent != EventType.SKIP_MOVE) {
				System.out.println("BEFORE Player" + player.getNameString() + ": " + Long.valueOf(Optional.ofNullable(player.getPosition()).orElse(0L)).intValue());
				
				int move = dice.getValue(); // 주사위 값 받고
				int pos = player.getPosition().intValue(); // 플레이어 위치 받고
				while(move > 0) { // pos 움직여서
					move--;
					pos = (pos+1)%tiles.size();
					EventType passbyEvent = tiles.get(pos).getPassByEvent();
					/* event 처리*/
				}
				player.setPosition((long)pos); // 플레이어 위치에 pos 할당
				
				System.out.println("AFTER Player" + player.getNameString() + ": " + Long.valueOf(Optional.ofNullable(player.getPosition()).orElse(0L)).intValue());
				System.out.println("");
			}
			
			gui.drawBoardView();
			
			//정지 이벤트
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
		Map<BuildingType, Long> redLineBuildingCostMap = getBuildingMap(50000L, 150000L, 250000L); // 건물들 구매 비용 설정인듯
		Map<BuildingType, Long> yellowLineBuildingCostMap = getBuildingMap(100000L, 300000L, 500000L);
		Map<BuildingType, Long> greenLineBuildingCostMap = getBuildingMap(150000L, 450000L, 750000L);
		Map<BuildingType, Long> blueLineBuildingCostMap = getBuildingMap(200000L, 600000L, 1000000L);
		GoldenKeyTile goldenKeyTile = new GoldenKeyTile("황금열쇠", this, deck);
		tiles = new ArrayList<Tile>();
		
		
		
		tiles.add(new StartTile("출발", this, 200000L));
		
		//red line
		tiles.add(BuildableCityTile.builder() // 그냥 생성자 호출이네
				.name("타이베이")
				.blueMarble(this)
				.cost(50000L)
				.fee(2000L)
				.buildingCostMap(redLineBuildingCostMap)
				.buildingFeeMap(getBuildingMap(10000L, 90000L, 250000L))
				.build()
				);
		tiles.add(goldenKeyTile);
		tiles.add(BuildableCityTile.builder()
				.name("베이징")
				.blueMarble(this)
				.cost(80000L)
				.fee(4000L)
				.buildingCostMap(redLineBuildingCostMap)
				.buildingFeeMap(getBuildingMap(20000L, 180000L, 450000L))
				.build()
				);
		tiles.add(BuildableCityTile.builder()
				.name("마닐라")
				.blueMarble(this)
				.cost(80000L)
				.fee(4000L)
				.buildingCostMap(redLineBuildingCostMap)
				.buildingFeeMap(getBuildingMap(20000L, 180000L, 450000L))
				.build()
				);
		tiles.add(new CityTile("제주도", this, 200000L, 300000L));
		tiles.add(BuildableCityTile.builder()
				.name("마닐라")
				.blueMarble(this)
				.cost(100000L)
				.fee(6000L)
				.buildingCostMap(redLineBuildingCostMap)
				.buildingFeeMap(getBuildingMap(30000L, 270000L, 550000L))
				.build()
				);
		tiles.add(goldenKeyTile);
		tiles.add(BuildableCityTile.builder()
				.name("카이로")
				.blueMarble(this)
				.cost(100000L)
				.fee(6000L)
				.buildingCostMap(redLineBuildingCostMap)
				.buildingFeeMap(getBuildingMap(30000L, 270000L, 550000L))
				.build()
				);
		tiles.add(BuildableCityTile.builder()
				.name("이스탄불")
				.blueMarble(this)
				.cost(120000L)
				.fee(8000L)
				.buildingCostMap(redLineBuildingCostMap)
				.buildingFeeMap(getBuildingMap(40000L, 300000L, 600000L))
				.build()
				);
		
		tiles.add(new IslandTile("무인도", this, 3L));
		
		//yellow line
		tiles.add(BuildableCityTile.builder()
				.name("이스탄불")
				.blueMarble(this)
				.cost(140000L)
				.fee(10000L)
				.buildingCostMap(yellowLineBuildingCostMap)
				.buildingFeeMap(getBuildingMap(50000L, 450000L, 750000L))
				.build()
				);
		tiles.add(goldenKeyTile);
		tiles.add(BuildableCityTile.builder()
				.name("코펜하겐")
				.blueMarble(this)
				.cost(160000L)
				.fee(12000L)
				.buildingCostMap(yellowLineBuildingCostMap)
				.buildingFeeMap(getBuildingMap(60000L, 500000L, 900000L))
				.build()
				);
		tiles.add(BuildableCityTile.builder()
				.name("스톡홀름")
				.blueMarble(this)
				.cost(160000L)
				.fee(12000L)
				.buildingCostMap(yellowLineBuildingCostMap)
				.buildingFeeMap(getBuildingMap(60000L, 500000L, 900000L))
				.build()
				);
		tiles.add(new CityTile("콩코드여객기", this, 200000L, 300000L));
		tiles.add(BuildableCityTile.builder()
				.name("베른")
				.blueMarble(this)
				.cost(180000L)
				.fee(14000L)
				.buildingCostMap(yellowLineBuildingCostMap)
				.buildingFeeMap(getBuildingMap(70000L, 500000L, 950000L))
				.build()
				);
		tiles.add(goldenKeyTile);
		tiles.add(BuildableCityTile.builder()
				.name("베를린")
				.blueMarble(this)
				.cost(180000L)
				.fee(14000L)
				.buildingCostMap(yellowLineBuildingCostMap)
				.buildingFeeMap(getBuildingMap(70000L, 500000L, 950000L))
				.build()
				);
		tiles.add(BuildableCityTile.builder()
				.name("오타와")
				.blueMarble(this)
				.cost(200000L)
				.fee(16000L)
				.buildingCostMap(yellowLineBuildingCostMap)
				.buildingFeeMap(getBuildingMap(80000L, 550000L, 1000000L))
				.build()
				);
		
		
		BenefitTile benefitTile = new BenefitTile("사회복지기금 수령처", this);
		tiles.add(benefitTile);
		
		
		//green line
		tiles.add(BuildableCityTile.builder()
				.name("부에노스 아이레스")
				.blueMarble(this)
				.cost(220000L)
				.fee(18000L)
				.buildingCostMap(greenLineBuildingCostMap)
				.buildingFeeMap(getBuildingMap(90000L, 700000L, 1050000L))
				.build()
				);
		tiles.add(goldenKeyTile);
		tiles.add(BuildableCityTile.builder()
				.name("상파울로")
				.blueMarble(this)
				.cost(240000L)
				.fee(20000L)
				.buildingCostMap(greenLineBuildingCostMap)
				.buildingFeeMap(getBuildingMap(100000L, 750000L, 1100000L))
				.build()
				);
		tiles.add(BuildableCityTile.builder()
				.name("시드니")
				.blueMarble(this)
				.cost(240000L)
				.fee(20000L)
				.buildingCostMap(greenLineBuildingCostMap)
				.buildingFeeMap(getBuildingMap(100000L, 750000L, 1100000L))
				.build()
				);
		tiles.add(new CityTile("부산", this, 500000L, 600000L));
		tiles.add(BuildableCityTile.builder()
				.name("하와이")
				.blueMarble(this)
				.cost(260000L)
				.fee(22000L)
				.buildingCostMap(greenLineBuildingCostMap)
				.buildingFeeMap(getBuildingMap(110000L, 800000L, 1150000L))
				.build()
				);
		tiles.add(BuildableCityTile.builder()
				.name("리스본")
				.blueMarble(this)
				.cost(260000L)
				.fee(22000L)
				.buildingCostMap(greenLineBuildingCostMap)
				.buildingFeeMap(getBuildingMap(110000L, 800000L, 1150000L))
				.build()
				);
		tiles.add(goldenKeyTile);
		tiles.add(BuildableCityTile.builder()
				.name("마드리드")
				.blueMarble(this)
				.cost(280000L)
				.fee(24000L)
				.buildingCostMap(greenLineBuildingCostMap)
				.buildingFeeMap(getBuildingMap(120000L, 850000L, 1200000L))
				.build()
				);
		
		
		CityTile spaceShipTile = new CityTile("컬럼비아호", this, 450000L, 400000L);
		tiles.add(new SpaceTripTile("우주여행", this, spaceShipTile, 200000L));
		
		
		//blue line
		tiles.add(BuildableCityTile.builder()
				.name("도쿄")
				.blueMarble(this)
				.cost(300000L)
				.fee(26000L)
				.buildingCostMap(greenLineBuildingCostMap)
				.buildingFeeMap(getBuildingMap(130000L, 90000L, 1270000L))
				.build()
				);
		tiles.add(spaceShipTile);
		tiles.add(BuildableCityTile.builder()
				.name("파리")
				.blueMarble(this)
				.cost(320000L)
				.fee(28000L)
				.buildingCostMap(greenLineBuildingCostMap)
				.buildingFeeMap(getBuildingMap(150000L, 100000L, 1400000L))
				.build()
				);
		tiles.add(BuildableCityTile.builder()
				.name("로마")
				.blueMarble(this)
				.cost(320000L)
				.fee(28000L)
				.buildingCostMap(greenLineBuildingCostMap)
				.buildingFeeMap(getBuildingMap(150000L, 100000L, 1400000L))
				.build()
				);
		tiles.add(goldenKeyTile);
		tiles.add(BuildableCityTile.builder()
				.name("런던")
				.blueMarble(this)
				.cost(350000L)
				.fee(35000L)
				.buildingCostMap(greenLineBuildingCostMap)
				.buildingFeeMap(getBuildingMap(170000L, 110000L, 1500000L))
				.build()
				);
		tiles.add(BuildableCityTile.builder()
				.name("뉴욕")
				.blueMarble(this)
				.cost(350000L)
				.fee(35000L)
				.buildingCostMap(greenLineBuildingCostMap)
				.buildingFeeMap(getBuildingMap(170000L, 110000L, 1500000L))
				.build()
				);
		tiles.add(new FundingTile("사회복지기금 접수처", this, benefitTile, 150000L));
		tiles.add(new CityTile("서울", this, 1000000L, 2000000L));
	}
	
	
	
	
	public static void main(String args[]) {
		System.out.println("hello!");
		
		//GUI gui = new GUI();
		//BlueMarble blueMarble = new BlueMarble(gui, 1000L);
		//---------------------------------------
		BlueMarble blueMarble = new BlueMarble(1000L);
		GUIT guiT = new GUIT(blueMarble);
		//---------------------------------------
		//gui.setBlueMarble(blueMarble);
		
		
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
		
		//---------------------------------------
		int somethingVal = 1;
		for(Player p : blueMarble.players) {
			//System.out.println(p);
			p.positionList = blueMarble.positionList;
			p.setNameString(String.valueOf(somethingVal));
			p.playerImage = new ImageIcon(BlueMarble.class.getResource("/images/players/player" + somethingVal + ".png")).getImage();
			somethingVal++;
		}
		blueMarble.getDice().blueMarble = blueMarble;
		blueMarble.setPosition();
		for(int i = 0; i < 40; i++) {
			System.out.println(blueMarble.positionList.get(i).getX() + " " + blueMarble.positionList.get(i).getY());
		}
		guiT.start();
		//---------------------------------------
		
		blueMarble.play();
		
		for(Player player : blueMarble.getPlayers()) {
			System.out.println(player.isBanckupted());
			System.out.println(player.getMoney());
			System.out.println(player.getPosition());
		}
		
		System.out.println(blueMarble.getTurnCount());
		
		
		System.out.println(blueMarble.dice);
	}
}
