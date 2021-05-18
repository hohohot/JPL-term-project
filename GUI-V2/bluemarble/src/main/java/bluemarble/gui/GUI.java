package bluemarble.gui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import bluemarble.BlueMarble;
import bluemarble.card.Card;
import bluemarble.player.Player;
import bluemarble.tiles.BuildableCityTile;
import bluemarble.type.BuildingType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//@Getter
@Setter
public class GUI extends JFrame {
	private BlueMarble blueMarble; // 자동으로 set 되겠네
	private BuildableCityTile currentTile;
	
	private boolean safeStart = false;
	public boolean drawFinish = false;
	private boolean rollPushed = false;
	public boolean canWeRoll = false;
	
	public boolean openBuyBoard = false;
	public boolean tileBuyBtnPushed = false;
	public boolean exitBuyBtnPushed = false;
	public boolean unactiveButton = false;
	
	Scanner scn = new Scanner(System.in);
	private int mouseX, mouseY;
	
	private Image screenImage;
	private Graphics screenGraphic;
	
	private Image backgroundImage = new ImageIcon(BlueMarble.class.getResource("/images/background.png")).getImage();
	private Image purchaseBoardImage = new ImageIcon(BlueMarble.class.getResource("/images/purchaseBoard.png")).getImage();
	private JLabel menuBar = new JLabel(new ImageIcon(BlueMarble.class.getResource("/images/menuBar.png")));
	
	private ImageIcon rollButtonBasicImage = new ImageIcon(BlueMarble.class.getResource("/images/buttons/rollButtonBasic.png"));
	private ImageIcon rollButtonEnteredImage = new ImageIcon(BlueMarble.class.getResource("/images/buttons/rollButtonEntered.png"));
	private ImageIcon exitButtonBasicImage = new ImageIcon(BlueMarble.class.getResource("/images/buttons/exitButtonBasic.png"));
	private ImageIcon exitButtonEnteredImage = new ImageIcon(BlueMarble.class.getResource("/images/buttons/exitButtonEntered.png"));
	private ImageIcon buyButtonBasicImage = new ImageIcon(BlueMarble.class.getResource("/images/buttons/BuyButtonBasic.png"));
	private ImageIcon buyButtonEnteredImage = new ImageIcon(BlueMarble.class.getResource("/images/buttons/BuyButtonEntered.png"));
	private ImageIcon buyVillaButtonBasicImage = new ImageIcon(BlueMarble.class.getResource("/images/buttons/villaBasic.png"));
	private ImageIcon buyVillaButtonEnteredImage = new ImageIcon(BlueMarble.class.getResource("/images/buttons/villaEntered.png"));
	private ImageIcon buyBuildingButtonBasicImage = new ImageIcon(BlueMarble.class.getResource("/images/buttons/buildingBasic.png"));
	private ImageIcon buyBuildingButtonEnteredImage = new ImageIcon(BlueMarble.class.getResource("/images/buttons/buildingEntered.png"));
	private ImageIcon buyHotelButtonBasicImage = new ImageIcon(BlueMarble.class.getResource("/images/buttons/hotelBasic.png"));
	private ImageIcon buyHotelButtonEnteredImage = new ImageIcon(BlueMarble.class.getResource("/images/buttons/hotelEntered.png"));
	
	private JButton rollButton = new JButton(rollButtonBasicImage);
	private JButton exitButton = new JButton(exitButtonBasicImage);
	private JButton tileBuyButton = new JButton(buyButtonBasicImage);
	private JButton exitBuyButton = new JButton(exitButtonBasicImage);
	private JButton villaBuyButton = new JButton(buyVillaButtonBasicImage);
	private JButton buildingBuyButton = new JButton(buyBuildingButtonBasicImage);
	private JButton hotelBuyButton = new JButton(buyHotelButtonBasicImage);
	
	public GUI() {
		setUndecorated(true);
		setTitle("Test Frame");
		setSize(BlueMarble.SCREEN_WIDTH, BlueMarble.SCREEN_HEIGHT); // 화면 크기
		setResizable(false); // 크기조정 FALSE
		setLocationRelativeTo(null); // 화면 중앙에서 시작
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 프로그램 종료
		setVisible(true);	
		setBackground(new Color(0, 0, 0, 0));
		setLayout(null);
		
		buttonSetting();
	}
	
	public void paint(Graphics g) {
		screenImage = createImage(BlueMarble.SCREEN_WIDTH, BlueMarble.SCREEN_HEIGHT);
		screenGraphic = screenImage.getGraphics();
		screenDraw((Graphics2D)screenGraphic);
		g.drawImage(screenImage, 0, 0, null);
	}
	
	public void screenDraw(Graphics2D g) {
		g.drawImage(backgroundImage, 0, 0, null); // 넘겨받은 그래픽 객체에다가 이미지 그리기
		
		if (safeStart) {
			for (int i = 0; i < 40; i++) {
				blueMarble.getTiles().get(i).screenDraw(g);
			}
			for (Player p : blueMarble.getPlayers()) {
				p.screenDraw(g);
			}
			blueMarble.getDice().screenDraw(g);
			if(openBuyBoard) g.drawImage(purchaseBoardImage, 240, 190, null);
			paintComponents(g); // 부가 요소들 직접 그리는 거 add한거
		}

		drawFinish = true;
	}
	
	public void buttonSetting() {
		rollButton.setBounds(410, 500, 150, 100); // 버튼 위치
		rollButton.setBorderPainted(false);
		rollButton.setContentAreaFilled(false);
		rollButton.setFocusPainted(false);
		rollButton.addMouseListener(new MouseAdapter() { // 버튼 리스너 <- 암튼 버튼에 관한 마우스 액션을 감지
			@Override
			public void mouseEntered(MouseEvent e) { // 그러다가 커서를 가져다 놓는다는 액션
				rollButton.setIcon(rollButtonEnteredImage);
				rollButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
			@Override
			public void mouseExited(MouseEvent e) { // 커서를 다시 떼놓는다는 액션
				rollButton.setIcon(rollButtonBasicImage);
				rollButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
			@Override
			public void mousePressed(MouseEvent e) { // 눌르는 액션 에 대해 이와 같은 행동을 한다 딴 액션들은 그냥 어뎁터 클래스가 리턴해줌
				//System.out.println("Pressed!");
				if(canWeRoll) rollPushed = true;
				canWeRoll = false;
			}
		});
		add(rollButton);
		rollButton.setVisible(false);
		
		tileBuyButton.setBounds(410, 300, 150, 100); // 버튼 위치
		tileBuyButton.setBorderPainted(false);
		tileBuyButton.setContentAreaFilled(false);
		tileBuyButton.setFocusPainted(false);
		tileBuyButton.addMouseListener(new MouseAdapter() { // 버튼 리스너 <- 암튼 버튼에 관한 마우스 액션을 감지
			@Override
			public void mouseEntered(MouseEvent e) { // 그러다가 커서를 가져다 놓는다는 액션
				tileBuyButton.setIcon(buyButtonEnteredImage);
				tileBuyButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
			@Override
			public void mouseExited(MouseEvent e) { // 커서를 다시 떼놓는다는 액션
				if(!unactiveButton) {
					tileBuyButton.setIcon(buyButtonBasicImage);
					tileBuyButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				}
			}
			@Override
			public void mousePressed(MouseEvent e) { // 눌르는 액션 에 대해 이와 같은 행동을 한다 딴 액션들은 그냥 어뎁터 클래스가 리턴해줌
				if(!unactiveButton)  {
					tileBuyBtnPushed = true; // if 예산이 충분하면 일단 전긍정
				}
				unactiveButton = true;
			}
		});
		add(tileBuyButton);
		tileBuyButton.setVisible(false);
		
		villaBuyButton.setBounds(320, 500, 100, 100); // 버튼 위치 //435
		villaBuyButton.setBorderPainted(false);
		villaBuyButton.setContentAreaFilled(false);
		villaBuyButton.setFocusPainted(false);
		villaBuyButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				villaBuyButton.setIcon(buyVillaButtonEnteredImage);
				villaBuyButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				if(!currentTile.vUnactiveButton) {
					villaBuyButton.setIcon(buyVillaButtonBasicImage);
					villaBuyButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				}
			}
			@Override
			public void mousePressed(MouseEvent e) { // 눌르는 액션 에 대해 이와 같은 행동을 한다 딴 액션들은 그냥 어뎁터 클래스가 리턴해줌
				if(!currentTile.vUnactiveButton)  {
					tileBuyBtnPushed = true; // 버튼이 active 상태였고 눌렀음
					currentTile.vUnactiveButton = true; // 구매 했으니까 더이상 불활성화
				}
				
			}
		});
		add(villaBuyButton);
		villaBuyButton.setVisible(false);
		
		buildingBuyButton.setBounds(435, 500, 100, 100); // 버튼 위치 //435
		buildingBuyButton.setBorderPainted(false);
		buildingBuyButton.setContentAreaFilled(false);
		buildingBuyButton.setFocusPainted(false);
		buildingBuyButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				buildingBuyButton.setIcon(buyBuildingButtonEnteredImage);
				buildingBuyButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				if(!currentTile.bUnactiveButton) {
					buildingBuyButton.setIcon(buyBuildingButtonBasicImage);
					buildingBuyButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				}
			}
			@Override
			public void mousePressed(MouseEvent e) { // 눌르는 액션 에 대해 이와 같은 행동을 한다 딴 액션들은 그냥 어뎁터 클래스가 리턴해줌
				if(!currentTile.bUnactiveButton)  {
					tileBuyBtnPushed = true; // 버튼이 active 상태였고 눌렀음
					currentTile.bUnactiveButton = true; // 구매 했으니까 더이상 불활성화
				}
				
			}
		});
		add(buildingBuyButton);
		buildingBuyButton.setVisible(false);
		
		hotelBuyButton.setBounds(550, 500, 100, 100); // 버튼 위치 //435
		hotelBuyButton.setBorderPainted(false);
		hotelBuyButton.setContentAreaFilled(false);
		hotelBuyButton.setFocusPainted(false);
		hotelBuyButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				hotelBuyButton.setIcon(buyHotelButtonEnteredImage);
				hotelBuyButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				if(!currentTile.hUnactiveButton) {
					hotelBuyButton.setIcon(buyHotelButtonBasicImage);
					hotelBuyButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				}
			}
			@Override
			public void mousePressed(MouseEvent e) { // 눌르는 액션 에 대해 이와 같은 행동을 한다 딴 액션들은 그냥 어뎁터 클래스가 리턴해줌
				if(!currentTile.hUnactiveButton)  {
					tileBuyBtnPushed = true; // 버튼이 active 상태였고 눌렀음
					currentTile.hUnactiveButton = true; // 구매 했으니까 더이상 불활성화
				}
				
			}
		});
		add(hotelBuyButton);
		hotelBuyButton.setVisible(false);
		
		exitBuyButton.setBounds(680, 190, 50, 25);
		exitBuyButton.setBorderPainted(false);
		exitBuyButton.setContentAreaFilled(false);
		exitBuyButton.setFocusPainted(false);
		exitBuyButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				exitBuyButton.setIcon(exitButtonEnteredImage);
				exitBuyButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				exitBuyButton.setIcon(exitButtonBasicImage);
				exitBuyButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
			@Override
			public void mousePressed(MouseEvent e) {
				exitBuyBtnPushed = true;
			}
		});
		add(exitBuyButton);
		exitBuyButton.setVisible(false);
		
		exitButton.setBounds(1225, 5, 50, 25); // 버튼 위치
		exitButton.setBorderPainted(false);
		exitButton.setContentAreaFilled(false);
		exitButton.setFocusPainted(false);
		exitButton.addMouseListener(new MouseAdapter() { // 버튼 리스너 <- 암튼 버튼에 관한 마우스 액션을 감지
			@Override
			public void mouseEntered(MouseEvent e) { // 그러다가 커서를 가져다 놓는다는 액션
				exitButton.setIcon(exitButtonEnteredImage);
				exitButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
			@Override
			public void mouseExited(MouseEvent e) { // 커서를 다시 떼놓는다는 액션
				exitButton.setIcon(exitButtonBasicImage);
				exitButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
			@Override
			public void mousePressed(MouseEvent e) { // 눌르는 액션 에 대해 이와 같은 행동을 한다 딴 액션들은 그냥 어뎁터 클래스가 리턴해줌
				System.exit(0);
			}
		});
		add(exitButton);
		
		menuBar.setBounds(0, 0, 1280, 30);
		menuBar.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) { // 클릭
				mouseX = e.getX();
				mouseY = e.getY();
			}
		});
		menuBar.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) { // 드래그
				int x = e.getXOnScreen(); // 창 위치 받아오기
				int y = e.getYOnScreen();
				setLocation(x - mouseX, y - mouseY);
			}
		});
		add(menuBar);
	}
	
	public void drawDice() {
		//주사위 그리기xx
		//roll 버튼 눌르는거 기다리기
		rollButton.setVisible(true);
		blueMarble.gui.canWeRoll = true;
		for(;;) {
			if (rollPushed) {
				rollPushed = false;
				rollButton.setVisible(false);
				return;
			} else {
				try {
					Thread.sleep(5);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	
	}
	

	public void drawGoldenKeyCardView(Card card) {
		//뽑은 황금열쇠카드 정보 그리기
	}
	
	public boolean selectBuyOrNot() {
		//땅을 살지 안살지 선택
		// 초기화
		System.out.println(blueMarble.getTiles()
				.get(blueMarble.getCurrentPlayer().getPosition().intValue()).getName());
		System.out.println(blueMarble.getCurrentPlayer().getMoney() + "/"+
				blueMarble.getTiles()
		.get(blueMarble.getCurrentPlayer().getPosition().intValue()).getCost()
		);
		if(blueMarble.getCurrentPlayer().getMoney() < blueMarble.getTiles()
				.get(blueMarble.getCurrentPlayer().getPosition().intValue()).cost)
			return false;
		
		openBuyBoard = true; // 구매 보드 열기
		rollButton.setVisible(false); // 롤 버튼 가리고
		tileBuyButton.setVisible(true); // 타일 구매 버튼 활성화
		exitBuyButton.setVisible(true); // 구매 보드 닫는 버튼 활성화
		unactiveButton = false;
		
		
		if(unactiveButton) tileBuyButton.setIcon(buyButtonEnteredImage);
		else tileBuyButton.setIcon(buyButtonBasicImage);
		
		
		System.out.print("first wallet: " + blueMarble.getCurrentPlayer().getMoney());
		System.out.println("  tile cost: " + blueMarble.getTiles()
				.get(blueMarble.getCurrentPlayer().getPosition().intValue()).cost);
		
		tileBuyBtnPushed = false;
		exitBuyBtnPushed = false;
		for(;;) {
			if (tileBuyBtnPushed || exitBuyBtnPushed) { // 구매 버튼이 눌리면 or 캔슬 버튼이 눌리면 진입
	
				if(true) {
					System.out.println("Entered");
					openBuyBoard = false;
					rollButton.setVisible(true);
					tileBuyButton.setVisible(false);
					exitBuyButton.setVisible(false);
					exitBuyBtnPushed = false;
					unactiveButton = false;
					
					return tileBuyBtnPushed; // 이때 샀는지 안샀는지를 return
				}
			} else {
				try {
					Thread.sleep(5);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

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
		
		openBuyBoard = true; // 구매 보드 열기
		rollButton.setVisible(false); // 롤 버튼 가리고
		villaBuyButton.setVisible(true); // 구매 버튼 활성화
		buildingBuyButton.setVisible(true);
		hotelBuyButton.setVisible(true);
		exitBuyButton.setVisible(true); // 구매 보드 닫는 버튼 활성화
		
		currentTile = (BuildableCityTile)blueMarble.getTiles()
				.get(blueMarble.getCurrentPlayer().getPosition().intValue());
		
		/* unactive 초기화 + 타겟 설정 */
		BuildingType targetBuilding = BuildingType.NONE;
		if(!currentTile.getBuildingList().contains(BuildingType.VILLA)) {
			currentTile.vUnactiveButton = false;
			currentTile.bUnactiveButton = true;
			currentTile.hUnactiveButton = true;
			
			if(blueMarble.getCurrentPlayer().getMoney() < currentTile.getBuildingCostMap().get(BuildingType.VILLA)) 
				currentTile.vUnactiveButton = true;
			targetBuilding = BuildingType.VILLA;
		} else if(!currentTile.getBuildingList().contains(BuildingType.BUILDING)) {
			currentTile.vUnactiveButton = true;
			currentTile.bUnactiveButton = false;
			currentTile.hUnactiveButton = true;
			
			if(blueMarble.getCurrentPlayer().getMoney() < currentTile.getBuildingCostMap().get(BuildingType.BUILDING)) 
				currentTile.bUnactiveButton = true;
			targetBuilding = BuildingType.BUILDING;
		} else if(!currentTile.getBuildingList().contains(BuildingType.HOTEL)) {
			currentTile.vUnactiveButton = true;
			currentTile.bUnactiveButton = true;
			currentTile.hUnactiveButton = false;
			
			if(blueMarble.getCurrentPlayer().getMoney() < currentTile.getBuildingCostMap().get(BuildingType.HOTEL)) 
				currentTile.hUnactiveButton = true;
			targetBuilding = BuildingType.HOTEL;
		}
		
		if(currentTile.vUnactiveButton) villaBuyButton.setIcon(buyVillaButtonEnteredImage);
		else villaBuyButton.setIcon(buyVillaButtonBasicImage);
		if(currentTile.bUnactiveButton) buildingBuyButton.setIcon(buyBuildingButtonEnteredImage);
		else buildingBuyButton.setIcon(buyBuildingButtonBasicImage);
		if(currentTile.hUnactiveButton) hotelBuyButton.setIcon(buyHotelButtonEnteredImage);
		else hotelBuyButton.setIcon(buyHotelButtonBasicImage);
		
		tileBuyBtnPushed = false;
		exitBuyBtnPushed = false;
		
		// 모두 unactive면 그냥 바로 탈출
		if(currentTile.vUnactiveButton && currentTile.bUnactiveButton && currentTile.hUnactiveButton) exitBuyBtnPushed = true;
		
		for(;;) {
			if (tileBuyBtnPushed || exitBuyBtnPushed) { // 구매 버튼이 눌리면 or 캔슬 버튼이 눌리면 진입
				
				openBuyBoard = false;
				rollButton.setVisible(true);
				villaBuyButton.setVisible(false);
				buildingBuyButton.setVisible(false);
				hotelBuyButton.setVisible(false);
				exitBuyButton.setVisible(false);
				exitBuyBtnPushed = false;
				
				if(tileBuyBtnPushed) {
					tileBuyBtnPushed = false;
					return targetBuilding;
				}
				
				return BuildingType.NONE; // 이때 샀는지 안샀는지를 return
			} else {
				try {
					Thread.sleep(5);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		//return BuildingType.NONE;
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
