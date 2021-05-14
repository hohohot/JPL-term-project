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
