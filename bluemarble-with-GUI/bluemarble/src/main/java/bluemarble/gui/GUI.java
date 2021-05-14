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
	
	private boolean safeStart = false;
	public boolean drawFinish = false;
	private boolean rollPushed = false;
	public boolean canWeRoll = false;
	
	Scanner scn = new Scanner(System.in);
	private int mouseX, mouseY;
	
	private Image screenImage;
	private Graphics screenGraphic;
	
	private Image backgroundImage = new ImageIcon(BlueMarble.class.getResource("/images/background.png")).getImage();
	private JLabel menuBar = new JLabel(new ImageIcon(BlueMarble.class.getResource("/images/menuBar.png")));
	
	private ImageIcon rollButtonBasicImage = new ImageIcon(BlueMarble.class.getResource("/images/buttons/rollButtonBasic.png"));
	private ImageIcon rollButtonEnteredImage = new ImageIcon(BlueMarble.class.getResource("/images/buttons/rollButtonEntered.png"));
	private ImageIcon exitButtonBasicImage = new ImageIcon(BlueMarble.class.getResource("/images/buttons/exitButtonBasic.png"));
	private ImageIcon exitButtonEnteredImage = new ImageIcon(BlueMarble.class.getResource("/images/buttons/exitButtonEntered.png"));
	
	private JButton rollButton = new JButton(rollButtonBasicImage);
	private JButton exitButton = new JButton(exitButtonBasicImage);
	
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
