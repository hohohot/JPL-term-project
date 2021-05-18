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
	private BlueMarble blueMarble; // �ڵ����� set �ǰڳ�
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
		setSize(BlueMarble.SCREEN_WIDTH, BlueMarble.SCREEN_HEIGHT); // ȭ�� ũ��
		setResizable(false); // ũ������ FALSE
		setLocationRelativeTo(null); // ȭ�� �߾ӿ��� ����
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // ���α׷� ����
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
		g.drawImage(backgroundImage, 0, 0, null); // �Ѱܹ��� �׷��� ��ü���ٰ� �̹��� �׸���
		
		if (safeStart) {
			for (int i = 0; i < 40; i++) {
				blueMarble.getTiles().get(i).screenDraw(g);
			}
			for (Player p : blueMarble.getPlayers()) {
				p.screenDraw(g);
			}
			blueMarble.getDice().screenDraw(g);
			if(openBuyBoard) g.drawImage(purchaseBoardImage, 240, 190, null);
			paintComponents(g); // �ΰ� ��ҵ� ���� �׸��� �� add�Ѱ�
		}

		drawFinish = true;
	}
	
	public void buttonSetting() {
		rollButton.setBounds(410, 500, 150, 100); // ��ư ��ġ
		rollButton.setBorderPainted(false);
		rollButton.setContentAreaFilled(false);
		rollButton.setFocusPainted(false);
		rollButton.addMouseListener(new MouseAdapter() { // ��ư ������ <- ��ư ��ư�� ���� ���콺 �׼��� ����
			@Override
			public void mouseEntered(MouseEvent e) { // �׷��ٰ� Ŀ���� ������ ���´ٴ� �׼�
				rollButton.setIcon(rollButtonEnteredImage);
				rollButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
			@Override
			public void mouseExited(MouseEvent e) { // Ŀ���� �ٽ� �����´ٴ� �׼�
				rollButton.setIcon(rollButtonBasicImage);
				rollButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
			@Override
			public void mousePressed(MouseEvent e) { // ������ �׼� �� ���� �̿� ���� �ൿ�� �Ѵ� �� �׼ǵ��� �׳� ��� Ŭ������ ��������
				//System.out.println("Pressed!");
				if(canWeRoll) rollPushed = true;
				canWeRoll = false;
			}
		});
		add(rollButton);
		rollButton.setVisible(false);
		
		tileBuyButton.setBounds(410, 300, 150, 100); // ��ư ��ġ
		tileBuyButton.setBorderPainted(false);
		tileBuyButton.setContentAreaFilled(false);
		tileBuyButton.setFocusPainted(false);
		tileBuyButton.addMouseListener(new MouseAdapter() { // ��ư ������ <- ��ư ��ư�� ���� ���콺 �׼��� ����
			@Override
			public void mouseEntered(MouseEvent e) { // �׷��ٰ� Ŀ���� ������ ���´ٴ� �׼�
				tileBuyButton.setIcon(buyButtonEnteredImage);
				tileBuyButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
			@Override
			public void mouseExited(MouseEvent e) { // Ŀ���� �ٽ� �����´ٴ� �׼�
				if(!unactiveButton) {
					tileBuyButton.setIcon(buyButtonBasicImage);
					tileBuyButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				}
			}
			@Override
			public void mousePressed(MouseEvent e) { // ������ �׼� �� ���� �̿� ���� �ൿ�� �Ѵ� �� �׼ǵ��� �׳� ��� Ŭ������ ��������
				if(!unactiveButton)  {
					tileBuyBtnPushed = true; // if ������ ����ϸ� �ϴ� ������
				}
				unactiveButton = true;
			}
		});
		add(tileBuyButton);
		tileBuyButton.setVisible(false);
		
		villaBuyButton.setBounds(320, 500, 100, 100); // ��ư ��ġ //435
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
			public void mousePressed(MouseEvent e) { // ������ �׼� �� ���� �̿� ���� �ൿ�� �Ѵ� �� �׼ǵ��� �׳� ��� Ŭ������ ��������
				if(!currentTile.vUnactiveButton)  {
					tileBuyBtnPushed = true; // ��ư�� active ���¿��� ������
					currentTile.vUnactiveButton = true; // ���� �����ϱ� ���̻� ��Ȱ��ȭ
				}
				
			}
		});
		add(villaBuyButton);
		villaBuyButton.setVisible(false);
		
		buildingBuyButton.setBounds(435, 500, 100, 100); // ��ư ��ġ //435
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
			public void mousePressed(MouseEvent e) { // ������ �׼� �� ���� �̿� ���� �ൿ�� �Ѵ� �� �׼ǵ��� �׳� ��� Ŭ������ ��������
				if(!currentTile.bUnactiveButton)  {
					tileBuyBtnPushed = true; // ��ư�� active ���¿��� ������
					currentTile.bUnactiveButton = true; // ���� �����ϱ� ���̻� ��Ȱ��ȭ
				}
				
			}
		});
		add(buildingBuyButton);
		buildingBuyButton.setVisible(false);
		
		hotelBuyButton.setBounds(550, 500, 100, 100); // ��ư ��ġ //435
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
			public void mousePressed(MouseEvent e) { // ������ �׼� �� ���� �̿� ���� �ൿ�� �Ѵ� �� �׼ǵ��� �׳� ��� Ŭ������ ��������
				if(!currentTile.hUnactiveButton)  {
					tileBuyBtnPushed = true; // ��ư�� active ���¿��� ������
					currentTile.hUnactiveButton = true; // ���� �����ϱ� ���̻� ��Ȱ��ȭ
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
		
		exitButton.setBounds(1225, 5, 50, 25); // ��ư ��ġ
		exitButton.setBorderPainted(false);
		exitButton.setContentAreaFilled(false);
		exitButton.setFocusPainted(false);
		exitButton.addMouseListener(new MouseAdapter() { // ��ư ������ <- ��ư ��ư�� ���� ���콺 �׼��� ����
			@Override
			public void mouseEntered(MouseEvent e) { // �׷��ٰ� Ŀ���� ������ ���´ٴ� �׼�
				exitButton.setIcon(exitButtonEnteredImage);
				exitButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
			@Override
			public void mouseExited(MouseEvent e) { // Ŀ���� �ٽ� �����´ٴ� �׼�
				exitButton.setIcon(exitButtonBasicImage);
				exitButton.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
			@Override
			public void mousePressed(MouseEvent e) { // ������ �׼� �� ���� �̿� ���� �ൿ�� �Ѵ� �� �׼ǵ��� �׳� ��� Ŭ������ ��������
				System.exit(0);
			}
		});
		add(exitButton);
		
		menuBar.setBounds(0, 0, 1280, 30);
		menuBar.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) { // Ŭ��
				mouseX = e.getX();
				mouseY = e.getY();
			}
		});
		menuBar.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) { // �巡��
				int x = e.getXOnScreen(); // â ��ġ �޾ƿ���
				int y = e.getYOnScreen();
				setLocation(x - mouseX, y - mouseY);
			}
		});
		add(menuBar);
	}
	
	public void drawDice() {
		//�ֻ��� �׸���xx
		//roll ��ư �����°� ��ٸ���
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
		//���� Ȳ�ݿ���ī�� ���� �׸���
	}
	
	public boolean selectBuyOrNot() {
		//���� ���� �Ȼ��� ����
		// �ʱ�ȭ
		System.out.println(blueMarble.getTiles()
				.get(blueMarble.getCurrentPlayer().getPosition().intValue()).getName());
		System.out.println(blueMarble.getCurrentPlayer().getMoney() + "/"+
				blueMarble.getTiles()
		.get(blueMarble.getCurrentPlayer().getPosition().intValue()).getCost()
		);
		if(blueMarble.getCurrentPlayer().getMoney() < blueMarble.getTiles()
				.get(blueMarble.getCurrentPlayer().getPosition().intValue()).cost)
			return false;
		
		openBuyBoard = true; // ���� ���� ����
		rollButton.setVisible(false); // �� ��ư ������
		tileBuyButton.setVisible(true); // Ÿ�� ���� ��ư Ȱ��ȭ
		exitBuyButton.setVisible(true); // ���� ���� �ݴ� ��ư Ȱ��ȭ
		unactiveButton = false;
		
		
		if(unactiveButton) tileBuyButton.setIcon(buyButtonEnteredImage);
		else tileBuyButton.setIcon(buyButtonBasicImage);
		
		
		System.out.print("first wallet: " + blueMarble.getCurrentPlayer().getMoney());
		System.out.println("  tile cost: " + blueMarble.getTiles()
				.get(blueMarble.getCurrentPlayer().getPosition().intValue()).cost);
		
		tileBuyBtnPushed = false;
		exitBuyBtnPushed = false;
		for(;;) {
			if (tileBuyBtnPushed || exitBuyBtnPushed) { // ���� ��ư�� ������ or ĵ�� ��ư�� ������ ����
	
				if(true) {
					System.out.println("Entered");
					openBuyBoard = false;
					rollButton.setVisible(true);
					tileBuyButton.setVisible(false);
					exitBuyButton.setVisible(false);
					exitBuyBtnPushed = false;
					unactiveButton = false;
					
					return tileBuyBtnPushed; // �̶� ����� �Ȼ������ return
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
		//�ǹ��� � ���� ������ ����. �Ȼ��, ����, ����, ȣ���� ����, �ܾ� ������ ���� �Ұ�
		
		/* HOTEL�� �ǹ� ���� ��ȸ
		 * BuildableCityTile currentTile = (BuildableCityTile)blueMarble.getTiles()
			.get(blueMarble.getCurrentPlayer().getPosition().intValue());
		currentTile.getBuildingCostMap().get(BuildingType.HOTEL);*/
		
		/* �÷��̾� �ܾ� ��ȸ
		blueMarble.getCurrentPlayer().getMoney();
		*/
		
		openBuyBoard = true; // ���� ���� ����
		rollButton.setVisible(false); // �� ��ư ������
		villaBuyButton.setVisible(true); // ���� ��ư Ȱ��ȭ
		buildingBuyButton.setVisible(true);
		hotelBuyButton.setVisible(true);
		exitBuyButton.setVisible(true); // ���� ���� �ݴ� ��ư Ȱ��ȭ
		
		currentTile = (BuildableCityTile)blueMarble.getTiles()
				.get(blueMarble.getCurrentPlayer().getPosition().intValue());
		
		/* unactive �ʱ�ȭ + Ÿ�� ���� */
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
		
		// ��� unactive�� �׳� �ٷ� Ż��
		if(currentTile.vUnactiveButton && currentTile.bUnactiveButton && currentTile.hUnactiveButton) exitBuyBtnPushed = true;
		
		for(;;) {
			if (tileBuyBtnPushed || exitBuyBtnPushed) { // ���� ��ư�� ������ or ĵ�� ��ư�� ������ ����
				
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
				
				return BuildingType.NONE; // �̶� ����� �Ȼ������ return
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
		//���ֿ����� �� Ÿ�� ����. ���ֿ��� Ÿ���� ���� �Ұ���.
		return 0; //Ÿ���� idx ��ȯ
	}
	
	
	public int selectSellingTile() {
		// �Ǹ��� Ÿ�� ����. ������ Ÿ�� ���� �� -1 ��ȯ. �ٸ� ��� 0��ȯ.
		return -1;
	}
}
