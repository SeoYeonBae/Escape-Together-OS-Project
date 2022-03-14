import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;

import java.awt.event.*;
import java.io.*;
import java.net.Socket;

import javax.sound.sampled.*;
import java.util.ArrayList;
import java.util.Collections;
import java.applet.AudioClip;

public class IceCreamGame extends JFrame {
	final String frameTitle = "����Ż�� ������ ";
	// ================================== ��� ����Ʈ
	// =====================================

	ArrayList<PosImageIcon> iceList = new ArrayList<>();; // ICEGUYũ�� �̹���
	ArrayList<PosImageIcon> fireList = new ArrayList<>();; // �� �̹���
	ArrayList<PosImageIcon> heartList = new ArrayList<>();; // ���� �̹���
	ArrayList<PosImageIcon> attackList = new ArrayList<>();; // ���� �̹���

	// ================================== �̹��� ��Ʈ��
	// =====================================

	final String COVER = "res/coverpage.png"; // �̹��� ��ȯ�ϱ� ���� �ϱ� ���Ͽ� ��Ʈ������ ����
	final String OUT_PAGE = "res/outside.png";
	final String RULES_PAGE = "res/rulespage.png";
	final String CHAT = "res/chat.png";
	final String FIRST_FLOOR = "res/1.png";
	final String SECOND_FLOOR = "res/2.png";
	final String THIRD_FLOOR = "res/3.png";
	final String FOURTH_FLOOR = "res/4.png";
	final String FIFTH_FLOOR = "res/5.png";
	final String WIN_PAGE = "res/win.png";
	final String OVER_PAGE = "res/gameOver.png";
	final String NUGA = "src/res/������.png";
	final String BABAM = "src/res/�ٹ��.png";
	final String BRAVO = "src/res/�ζ���.png";
	final String SSANG = "src/res/�ֹֽ�.png";
	final String ICEGUY = "src/res/���̽�.png";
	final String COFFEE = "src/res/Ŀ��.png";
	final String CRAZY = "src/res/ũ��������.png";
	final String TANK = "src/res/��ũ����.png";
	final String PASI = "src/res/�Ľ�����.png";
	final String HODU = "src/res/ȣ�θ���.png";
	final String KEY = "src/res/key.png";
	final String FIRE = "src/res/fire.png";
	final String LEFT1 = "src/res/left1.png";
	final String LEFT2 = "src/res/left2.png";
	final String LEFT3 = "src/res/left3.png";
	final String RIGHT1 = "src/res/right1.png";
	final String RIGHT2 = "src/res/right2.png";
	final String RIGHT3 = "src/res/right3.png";
	final String STOP = "src/res/stop.png";
	final String HEART = "src/res/heart.png";
	final String CLEAR = "src/res/clear.png";
	final String TIME = "src/res/time.png";
	final String SBRAVO = "src/res/�����ζ���.png";
	private final String exit_img = "/res/������.png";
	private final String pause_img = "/res/�Ͻ�����.png";
	private final String replay_img = "/res/�ٽý���.png";

	// ==================================== ���� ��Ʈ��
	// ======================================

	final String START_SOUND = "res/bgm.wav"; // ����
	final String WIN_SOUND = "res/winSound.wav";
	final String LOSE_SOUND = "res/loseSound.wav";
	final String X_SOUND = "res/Duck.wav";
	final String WOW = "res/Wow.wav";

	// =================================== ������ ���
	// ========================================

	final int WIN_WIDTH = 690; // ��ü frame�� ��
	final int WIN_HEIGHT = 577; // ��ü frame�� ����

	// =================================== ������ ����
	// ========================================

	int sec = 100; // �ð���
	int dex; // ���̽�����Ʈ���� �� ��° ���̽�ũ���� �����ߴ��� �˱� ���� ����
	int panel; // �гο� ���� ������ ���̽�ũ���� �޶� �� �� �г����� �˱� ���� ����
	int moveCount = 0; // ĳ������ �������� �˾ƺ��� ���� ����
	int score_time = 0; // �� ������
	int pscore_time = 0; // ��� ������
	int score;
	int partner_score; // ��� ����

	// ================================= �Ҹ�����
	// ����===========================================

	boolean isBabam = false; // ����� ���� true �ƴϸ� false
	boolean isHodu = false;
	boolean isTank = false;
	boolean isCoffee = false;
	boolean isCrazy = false;
	boolean isIce = false;
	boolean isSSang = false;
	boolean isGirl = true; // ĳ���� ���� true = ���� , false = ������
	boolean isFire = true; // true = �ҿ� �°�, false = �ҿ� �� �´´�
	boolean isKeyTimer = false; // false = Ÿ�̸Ӱ� ���ư��� X true�� ���� ���ư���
	boolean isFireTimer = false;
	boolean isSecTimer = false;
	boolean isAttackTimer = false;
	boolean isHeartOver = false;
	boolean reP = true;
	boolean isUserLogin = false;

	// ============================ �����̹��������� ������ �̹��� ����
	// =================================

	PosImageIcon key;
	PosImageIcon girlL;
	PosImageIcon girlR;
	PosImageIcon timerSection;

	// ============================ �̹��� ������
	// =================================================

	ImageIcon girlL1; // ����
	ImageIcon girlL2;
	ImageIcon girlL3;
	ImageIcon girlR1; // ������
	ImageIcon girlR2;
	ImageIcon girlR3;
	ImageIcon girlStop; // ����
	ImageIcon clear; // ���� ������

	// ============================ ���̾ƿ� ����
	// ===================================================

	CardLayout card;

	// ============================ �г� ����
	// ======================================================

	JPanel coverPanel;
	JPanel fightChoicePanel;
	JPanel outPanel;
	JPanel rulesPanel;
	JPanel hint1Panel;
	JPanel hint2Panel;
	JPanel firstFloor;
	JPanel secondFloor;
	JPanel thirdFloor;
	JPanel fourthFloor;
	JPanel fifthFloor;
	JPanel endPanel;
	JPanel joystickPanel;

	// ============================= ��ư ����
	// =====================================================

	JButton loginBtn;
	JButton logoutBtn;
	JButton startBtn;
	JButton rulesBtn;
	JButton fightBtn;
	JButton sendButton;
	JButton returnBtn;

	// ��ư ���
	private final int START = 1;
	private final int END = 2;
	private final int SUSPEND = 4;
	private final int REPLAY = 8;
	private final int REMAIN = 16;
	private final int EXPLAIN = 32;
	private final int SELECT = 64;

	JButton suspend = new JButton(new ImageIcon(getClass().getResource(pause_img)));
	JButton replay = new JButton(new ImageIcon(getClass().getResource(replay_img)));
	JButton remain = new JButton(new ImageIcon(getClass().getResource(exit_img)));

	// =========================== ����
	// ============================================================

	AudioClip backgroundSound; // ���� ���
	AudioClip winSound; // �̰��� ��
	AudioClip loseSound; // ���� ��
	AudioClip x; // �ҿ� �¾��� ��, ���̽�ũ���� �߸� ������ ��
	AudioClip wow; // ���̽�ũ���� ����� ���� ��

	// ========================= Ÿ�̸� ����
	// ========================================================

	Timer iconTimer; // �Ұ� ���̽�ũ�� �̹��� ��� ���
	Timer secTimer; // �ð� �� ��� Ÿ�̸�
	//
	boolean myReady;
	boolean yourReady; // �غ�, GAME_SET
	boolean partnerSSang;

	boolean myStopover;
	boolean yourStopover; // ��������, GAME_STOPOVER

	ObjectInputStream reader; // ���ſ� ��Ʈ��
	ObjectOutputStream writer; // �۽ſ� ��Ʈ��
	String user; // �� Ŭ���̾�Ʈ�� �α��� �� ������ �̸�
	String partnerName; // ����
	Socket sock; // ���� ����� ����
	JScrollPane cScroller;
	JScrollPane qScroller;
	JScrollPane oScroller;
	JList counterParts; // ���� �α����� ä�� ������� ��Ÿ���� ����Ʈ.
	JList scoreList;
	JLabel chat = new JLabel("ä��â");
	JLabel userList = new JLabel("������");
	JLabel scoreLabel = new JLabel("�� ���� : " + score);

	JTextArea incoming; // ���ŵ� �޽����� ����ϴ� ��
	JTextArea outgoing; // �۽��� �޽����� �ۼ��ϴ� ��
	// =========================== ����
	// ===========================================================

	public static void main(String[] args) {
		try {
			IceCreamGame game = new IceCreamGame();
		} catch (Exception e) {
		}
	}

	// ========================== ������
	// ===========================================================

	IceCreamGame() {
		setTitle(frameTitle);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(WIN_WIDTH, WIN_HEIGHT);
		setLocationRelativeTo(null); // ������ �߾ӿ� ����
		setResizable(false); // ������ ������ ����

		card = new CardLayout();
		getContentPane().setLayout(card); // ���̾ƿ��� ī��� �����ϰ� �����ӿ� �����

		girlL1 = new ImageIcon(LEFT1); // �̹��� ������ ����
		girlL2 = new ImageIcon(LEFT2);
		girlL3 = new ImageIcon(LEFT3);
		girlR1 = new ImageIcon(RIGHT1);
		girlR2 = new ImageIcon(RIGHT2);
		girlR3 = new ImageIcon(RIGHT3);
		girlStop = new ImageIcon(STOP);
		clear = new ImageIcon(CLEAR);

		partnerSSang = false; 

		girlL = new PosImageIcon(LEFT2, 430, 240, true); // ���� �̹��� ������ ����
		girlR = new PosImageIcon(RIGHT2, 180, 240, true);
		key = new PosImageIcon(KEY, 260, 70, true);
		timerSection = new PosImageIcon(TIME, 20, 300, true);

		iceList.add(new PosImageIcon(NUGA, 465, 438, true)); // ���̽� ����Ʈ�� �̹��� ���ϱ�
		iceList.add(new PosImageIcon(BABAM, 387, 487, true));
		iceList.add(new PosImageIcon(BRAVO, 8, 437, true));
		iceList.add(new PosImageIcon(SSANG, 143, 481, true));
		iceList.add(new PosImageIcon(ICEGUY, 620, 435, true));
		iceList.add(new PosImageIcon(COFFEE, 65, 418, true));
		iceList.add(new PosImageIcon(CRAZY, 268, 487, true));
		iceList.add(new PosImageIcon(TANK, 506, 487, true));
		iceList.add(new PosImageIcon(PASI, 160, 435, true));
		iceList.add(new PosImageIcon(HODU, 316, 438, true));

		heartList.add(new PosImageIcon(HEART, 460, 7, true)); // ��Ʈ ����Ʈ�� �̹��� ���ϱ�
		heartList.add(new PosImageIcon(HEART, 530, 7, true));
		heartList.add(new PosImageIcon(HEART, 600, 7, true));

		fireList.add(new PosImageIcon(FIRE, (int) (Math.random() * 350 + 150), 0, true)); // �� �̹��� ����Ʈ�� �߰��ϱ�

		attackList.add(new PosImageIcon(SBRAVO, (int) (Math.random() * 350 + 150), 0, true)); // �� �̹��� ����Ʈ�� �߰��ϱ�

		coverPanel = new CoverPanel(); // �г� �����
		fightChoicePanel = new FightChoicePanel();
		outPanel = new OutSidePanel();
		rulesPanel = new RulesPanel();
		firstFloor = new FirstFloor();
		secondFloor = new SecondFloor();
		thirdFloor = new ThirdFloor();
		fourthFloor = new FourthFloor();
		fifthFloor = new FifthFloor();
		endPanel = new EndPanel();

		loginBtn = new JButton(); // ��ư ����
		loginBtn.setBorderPainted(false); // �׵θ� ���ֱ�
		loginBtn.setContentAreaFilled(false); // ä��� �� ���ֱ�
		logoutBtn = new JButton();
		logoutBtn.setBorderPainted(false);
		logoutBtn.setContentAreaFilled(false);
		startBtn = new JButton(); // ��ư ����
		startBtn.setBorderPainted(false); // �׵θ� ���ֱ�
		startBtn.setContentAreaFilled(false); // ä��� �� ���ֱ�
		rulesBtn = new JButton();
		rulesBtn.setBorderPainted(false);
		rulesBtn.setContentAreaFilled(false);
		fightBtn = new JButton();
		fightBtn.setBorderPainted(false);
		fightBtn.setContentAreaFilled(false);
		sendButton = new JButton("����");
		sendButton.setContentAreaFilled(false);
		suspend.setOpaque(false);
		suspend.setContentAreaFilled(false);
		suspend.setBorderPainted(false);
		replay.setOpaque(false);
		replay.setContentAreaFilled(false);
		replay.setBorderPainted(false);
		remain.setOpaque(false);
		remain.setContentAreaFilled(false);
		remain.setBorderPainted(false);
		returnBtn = new JButton();
		returnBtn.setOpaque(false);
		returnBtn.setContentAreaFilled(false);
		returnBtn.setBorderPainted(false);

		iconTimer = new Timer(8, new IconTimer()); // delay�� 8�� ������ ������ Ÿ�̸� ����
		secTimer = new Timer(1000, new SecTimer()); // 1�ʿ� �� ���� ���� ���� Ÿ�̸�

		backgroundSound = JApplet.newAudioClip(getClass().getResource(START_SOUND)); // ��� ����
		winSound = JApplet.newAudioClip(getClass().getResource(WIN_SOUND)); // �̰��� ��
		loseSound = JApplet.newAudioClip(getClass().getResource(LOSE_SOUND)); // ���� ��
		x = JApplet.newAudioClip(getClass().getResource(X_SOUND)); // ���̽�ũ���� �߸� ���Ұų� �ҿ� �¾Ұų� ���̽�ũ���� ���� �ʰ� ������ ��
		wow = JApplet.newAudioClip(getClass().getResource(WOW)); // ���̽�ũ���� ����� ������ ��

		loginBtn.addActionListener(new LogButtonListener());
		logoutBtn.addActionListener(new LogButtonListener());
		startBtn.addActionListener(new StartListener()); // ��ư�� ������ �ޱ�
		rulesBtn.addActionListener(new BtnListener());
		fightBtn.addActionListener(new BtnListener());
		suspend.addActionListener(new SuspendListener());
		remain.addActionListener(new ReSelectListener());
		replay.addActionListener(new ReplayListener());
		returnBtn.addActionListener(new BtnListener());

		String[] loginList = { GameMessage.ALL };
		counterParts = new JList(loginList);
		cScroller = new JScrollPane(counterParts);
		cScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		cScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

		counterParts.setVisibleRowCount(20);
		counterParts.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		counterParts.setFixedCellWidth(160);

		incoming = new JTextArea(40, 40);
		incoming.setLineWrap(true);
		incoming.setWrapStyleWord(true);
		incoming.setEditable(false);
		incoming.setFont(new Font("���� ���� B", Font.PLAIN, 25));
		qScroller = new JScrollPane(incoming);
		qScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		qScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

		outgoing = new JTextArea(5, 30);
		outgoing.setLineWrap(true);
		outgoing.setWrapStyleWord(true);
		outgoing.setEditable(true);
		outgoing.setFont(new Font("���� ���� B", Font.PLAIN, 25));
		oScroller = new JScrollPane(outgoing);
		oScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		oScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

		coverPanel.add(loginBtn); // �гο� ��ư �ޱ�
		coverPanel.add(fightBtn);
		fightChoicePanel.add(rulesBtn);
		fightChoicePanel.add(sendButton);
		fightChoicePanel.add(cScroller);
		fightChoicePanel.add(qScroller);
		fightChoicePanel.add(oScroller);
		fightChoicePanel.add(chat);
		fightChoicePanel.add(userList);
		fightChoicePanel.add(logoutBtn);
		fightChoicePanel.add(startBtn);
		rulesPanel.add(returnBtn);

		outPanel.addMouseListener(new IceListener()); // �гο� ���콺 ������ �ޱ�
		outPanel.addMouseMotionListener(new IceDraggListener()); // �гο� ���콺 ��� ������ �ޱ�
		firstFloor.addMouseListener(new IceListener());
		firstFloor.addMouseMotionListener(new IceDraggListener());
		secondFloor.addMouseListener(new IceListener());
		secondFloor.addMouseMotionListener(new IceDraggListener());
		thirdFloor.addMouseListener(new IceListener());
		thirdFloor.addMouseMotionListener(new IceDraggListener());
		fourthFloor.addMouseListener(new IceListener());
		fourthFloor.addMouseMotionListener(new IceDraggListener());
		fifthFloor.addMouseListener(new IceListener());
		fifthFloor.addMouseMotionListener(new IceDraggListener());
		addKeyListener(new MoveListener()); // �����ӿ� Ű������ �ޱ�
		sendButton.addActionListener(new SendButtonListener());

		setUpNetworking();
		Thread readerThread = new Thread(new IncomingReader());
		readerThread.start();
		
		getContentPane().add("FightChoicePanel", fightChoicePanel);
		getContentPane().add("outPanel", outPanel);
		getContentPane().add("coverPanel", coverPanel); // ī�巹�̾ƿ��� "coverPanel"�̶� �̸��� coverPanel�� �߰��Ѵ�
		getContentPane().add("endPanel", endPanel);
		buttonToggler(SUSPEND + REMAIN);
		card.show(getContentPane(), "coverPanel"); // ī�巹�̾ƿ����� "coverPanel"�̶� �̸��� ���� �г��� ������ �����ش�
		backgroundSound.play(); // ��� ���� ����

		requestFocus();
		setFocusable(true);
		setVisible(true);

	} // IceCreamGame() end

	// ========================================== �г� Ŭ����
	// ===================================================
	private void buttonToggler(int flags) {
		// SUSPEND
		if ((flags & SUSPEND) != 0)
			suspend.setEnabled(true);
		else
			suspend.setEnabled(false);

		// REMAIN
		if ((flags & REMAIN) != 0)
			remain.setEnabled(true);
		else
			remain.setEnabled(false);

		// REPLAY
		if ((flags & REPLAY) != 0)
			replay.setEnabled(true);
		else
			replay.setEnabled(false);
	}

	class CoverPanel extends JPanel {
		public void paintComponent(Graphics g) {
			Image image = new ImageIcon(getClass().getResource(COVER)).getImage(); // �̹��� ��������
			g.drawImage(image, 0, 0, 684, 548, null); // �̹��� �׸���
			loginBtn.setBounds(470, 380, 155, 60); // ���� ��ư
			fightBtn.setBounds(470, 450, 155, 60);

		}
	}// CoverPanel class end

	class FightChoicePanel extends JPanel {
		public void paintComponent(Graphics g) {
			Image image = new ImageIcon(getClass().getResource(CHAT)).getImage();
			g.drawImage(image, 0, 0, 684, 548, null);
			cScroller.setBounds(470, 60, 180, 400);
			qScroller.setBounds(40, 60, 420, 350);
			oScroller.setBounds(40, 415, 350, 40);
			sendButton.setBounds(400, 415, 60, 40);
			chat.setBounds(210, 40, 50, 20);
			userList.setBounds(540, 40, 50, 20);
			logoutBtn.setBounds(40, 477, 160, 60);
			startBtn.setBounds(295, 477, 130, 60);
			rulesBtn.setBounds(510, 477, 130, 60);
		}
	}

	class RulesPanel extends JPanel {
		public void paintComponent(Graphics g) {
			Image image = new ImageIcon(getClass().getResource(RULES_PAGE)).getImage();
			g.drawImage(image, 0, 0, null);
			returnBtn.setBounds(0, 0,500, 500);
		}
	}// RulesPanel end

	class OutSidePanel extends JPanel {
		public void paintComponent(Graphics g) {
			ImageIcon image = new ImageIcon(getClass().getResource(OUT_PAGE));
			g.drawImage(image.getImage(), 0, 0, null);
			for (PosImageIcon iceIcon : iceList) { // ���̽�ũ�� �׸���
				iceIcon.draw(g);
			}
			key.draw(g); // ���� �׸���
			panel = 0; // �гθ��� �´� ���̽�ũ���� �޶� �г� ��ȣ �ο�
		}
	}// OutSidePanel class end

	class FirstFloor extends JPanel {
		public void paintComponent(Graphics g) {
			Image image = new ImageIcon(getClass().getResource(FIRST_FLOOR)).getImage();
			g.drawImage(image, 0, 0, null);
			for (PosImageIcon iceIcon : iceList) {
				if (iceIcon.use == true) // use�� true�� ���̽�ũ���� �׸���
					iceIcon.draw(g);
			}
			for (int i = 0; i < fireList.size(); i++) {
				fireList.get(i).draw(g); // ����Ʈ�� �ִ� �� �̹��� �׸���
			}
			for (int i = 0; i < heartList.size(); i++) {
				if (heartList.get(i).use == true) // use�� true�� ��Ʈ�� �׸���
					heartList.get(i).draw(g);
			}
			timerSection.draw(g); // �ð��� �̹��� �׸���
			girlL.draw(g); // ���� �̹��� �׸���

			secTimer.start(); // �ð��� Ÿ�̸� ����
			isKeyTimer = false; // Ű Ÿ�̸� ���߱�
			isFireTimer = true; // �� �׸��� ����

			g.setColor(Color.YELLOW);
			g.setFont(new Font("���� ���� B", Font.BOLD, 25));
			g.drawString(sec + "��", 60, 365);

			isGirl = true; // ����
			panel = 1;
		}
	}// FirstFloor class end

	class SecondFloor extends JPanel {
		public void paintComponent(Graphics g) {
			iceList.get(7).use = false;
			Image image = new ImageIcon(getClass().getResource(SECOND_FLOOR)).getImage();
			g.drawImage(image, 0, 0, null);
			for (PosImageIcon icon : iceList) {
				if (icon.use == true)
					icon.draw(g);
			}
			for (int i = 0; i < fireList.size(); i++) {
				fireList.get(i).draw(g);
			}
			for (int i = 0; i < attackList.size(); i++) {
				attackList.get(i).draw(g); // ����Ʈ�� �ִ� �� �̹��� �׸���
			}
			for (int i = 0; i < heartList.size(); i++) {
				if (heartList.get(i).use == true)
					heartList.get(i).draw(g);
			}
			isAttackTimer = true;

			girlR.draw(g); // ������ �̹��� �׸���
			timerSection.draw(g);

			isGirl = false; // ������
			panel = 2;

			g.setColor(Color.YELLOW);
			g.setFont(new Font("���� ���� B", Font.BOLD, 25));
			g.drawString(sec + "��", 60, 365);
		}
	}// SecondFloor class end

	class ThirdFloor extends JPanel {
		public void paintComponent(Graphics g) {
			iceList.get(5).use = false;
			Image image = new ImageIcon(getClass().getResource(THIRD_FLOOR)).getImage(); // �̹��� ��������
			g.drawImage(image, 0, 0, null); // �̹��� �׸���
			for (PosImageIcon icon : iceList) {
				if (icon.use == true)
					icon.draw(g);
			}
			for (int i = 0; i < fireList.size(); i++) {
				fireList.get(i).draw(g);
			}
			for (int i = 0; i < heartList.size(); i++) {
				if (heartList.get(i).use == true)
					heartList.get(i).draw(g);
			}
			girlL.draw(g);
			timerSection.draw(g);
			isAttackTimer = false;
			isGirl = true;
			panel = 3;

			g.setColor(Color.YELLOW);
			g.setFont(new Font("���� ���� B", Font.BOLD, 25));
			g.drawString(sec + "��", 60, 365);
		}
	}// ThirdFloor class end

	class FourthFloor extends JPanel {
		public void paintComponent(Graphics g) {
			iceList.get(6).use = false;
			Image image = new ImageIcon(getClass().getResource(FOURTH_FLOOR)).getImage(); // �̹��� ��������
			g.drawImage(image, 0, 0, null); // �̹��� �׸���
			for (PosImageIcon icon : iceList) {
				if (icon.use == true)
					icon.draw(g);
			}
			for (int i = 0; i < fireList.size(); i++) {
				fireList.get(i).draw(g);
			}
			for (int i = 0; i < attackList.size(); i++) {
				attackList.get(i).draw(g); // ����Ʈ�� �ִ� �� �̹��� �׸���
			}
			for (int i = 0; i < heartList.size(); i++) {
				if (heartList.get(i).use == true)
					heartList.get(i).draw(g);
			}
			isAttackTimer = true;
			girlR.draw(g);
			timerSection.draw(g);

			isGirl = false;
			panel = 4;

			g.setColor(Color.YELLOW);
			g.setFont(new Font("���� ���� B", Font.BOLD, 25));
			g.drawString(sec + "��", 60, 365);
		}
	}// FourthFloor class end

	class FifthFloor extends JPanel {
		public void paintComponent(Graphics g) {
			iceList.get(4).use = false;
			Image image = new ImageIcon(getClass().getResource(FIFTH_FLOOR)).getImage(); // �̹��� ��������
			g.drawImage(image, 0, 0, null); // �̹��� �׸���
			for (PosImageIcon icon : iceList) {
				if (icon.use == true)
					icon.draw(g);
			}
			for (int i = 0; i < heartList.size(); i++) {
				if (heartList.get(i).use == true)
					heartList.get(i).draw(g);
			}
			girlL.pX = 545;
			girlL.pY = 202;
			iconTimer.stop();
			panel = 5;
			isAttackTimer = false;
			girlL.draw(g);
			timerSection.draw(g);

			g.setColor(Color.YELLOW);
			g.setFont(new Font("���� ���� B", Font.BOLD, 25));
			g.drawString(sec + "��", 60, 365);
		}
	}// FifthFloor class end

	class EndPanel extends JPanel {
		public void paintComponent(Graphics g) {
			// ���� ������ �����ϰų� ������ �� �� ��
			if (isHeartOver) {
				Image image = new ImageIcon(getClass().getResource(OVER_PAGE)).getImage(); // �̹��� ��������
				g.drawImage(image, 0, 0, 684, 548, null); // �̹��� �׸���

				g.setColor(Color.black);
				g.setFont(new Font("���� ���� B", Font.PLAIN, 40));
				g.drawString("�� �ҿ�ð� : ��Ʈ ����", 300, 400);

				g.setFont(new Font("���� ���� B", Font.PLAIN, 25));
				g.drawString("���� �ҿ�ð� : " + partner_score + "��", 310, 445);

				backgroundSound.stop();
				loseSound.play();
			} else if (isSSang) {
				Image image = new ImageIcon(getClass().getResource(WIN_PAGE)).getImage(); // �̹��� ��������
				g.drawImage(image, 0, 0, 684, 548, null); // �̹��� �׸���

				g.setColor(Color.black);
				g.setFont(new Font("���� ���� B", Font.PLAIN, 40));
				g.drawString("�� �ҿ�ð� : " + score + "��", 300, 400);

				g.setFont(new Font("���� ���� B", Font.PLAIN, 25));
				g.drawString("���� �ҿ�ð� : " + partner_score + "�� ", 310, 445);

				backgroundSound.stop(); // ������� ���߱�
				winSound.play(); // �̰��� �� ���� ����
			}

			else if(sec == 1){
				Image image = new ImageIcon(getClass().getResource(OVER_PAGE)).getImage(); // �̹��� ��������
				g.drawImage(image, 0, 0, 684, 548, null); // �̹��� �׸���

				g.setColor(Color.black);
				g.setFont(new Font("���� ���� B", Font.PLAIN, 40));
				g.drawString("�� �ҿ�ð� : " + score + "��", 300, 400);

				g.setFont(new Font("���� ���� B", Font.PLAIN, 25));
				g.drawString("���� �ҿ�ð� : " + partner_score + "�� ", 310, 445);

				backgroundSound.stop();
				loseSound.play();
			}

			else if(partnerSSang) {
				Image image = new ImageIcon(getClass().getResource(OVER_PAGE)).getImage(); // �̹��� ��������
				g.drawImage(image, 0, 0, 684, 548, null); // �̹��� �׸���

				g.setColor(Color.black);
				g.setFont(new Font("���� ���� B", Font.PLAIN, 40));
				g.drawString("�� �ҿ�ð� : " + score + "��", 300, 400);

				g.setFont(new Font("���� ���� B", Font.PLAIN, 25));
				g.drawString("���� �ҿ�ð� : " + partner_score + "�� ", 310, 445);

				backgroundSound.stop();
				loseSound.play();
			}else {
				Image image = new ImageIcon(getClass().getResource(WIN_PAGE)).getImage(); // �̹��� ��������
				g.drawImage(image, 0, 0, 684, 548, null); // �̹��� �׸���

				g.setColor(Color.black);
				g.setFont(new Font("���� ���� B", Font.PLAIN, 40));
				g.drawString("�� �ҿ�ð� : " + score + "��", 300, 400);

				g.setFont(new Font("���� ���� B", Font.PLAIN, 25));
				g.drawString("����ҿ�ð� : " + partner_score + "�� ", 310, 445);

				backgroundSound.stop(); // ������� ���߱�
				winSound.play(); // �̰��� �� ���� ����
			}


			// ���� ���� ��ư Ŭ��
			if (myStopover == true || yourStopover == true) {
				if (yourStopover == true) {
					Image image = new ImageIcon(getClass().getResource(WIN_PAGE)).getImage(); // �̹��� ��������
					g.drawImage(image, 0, 0, 684, 548, null); // �̹��� �׸���

					g.setColor(Color.black);
					g.setFont(new Font("���� ���� B", Font.PLAIN, 40));
					g.drawString("�� �ҿ�ð� : " + score, 150, 400);

					g.setFont(new Font("���� ���� B", Font.PLAIN, 25));
					g.drawString("���� �ҿ�ð� : ��밡 ������ �����߽��ϴ�.", 65, 445);

					backgroundSound.stop(); // ������� ���߱�
					winSound.play(); // �̰��� �� ���� ����
				}
				if (myStopover == true) {
					Image image = new ImageIcon(getClass().getResource(OVER_PAGE)).getImage(); // �̹��� ��������
					g.drawImage(image, 0, 0, 684, 548, null); // �̹��� �׸���

					g.setColor(Color.black);
					g.setFont(new Font("���� ���� B", Font.PLAIN, 40));
					g.drawString("�� �ҿ�ð� : ���� ������ �����߽��ϴ�.", 35,400);

					g.setFont(new Font("���� ���� B", Font.PLAIN, 25));
					g.drawString("���� �ҿ�ð� : " + partner_score, 160, 445);
					backgroundSound.stop();
					loseSound.play();
				}
			}
			repaint();



			// buttonToggler(END);
		}
	}

	private void setUpNetworking() {
		try {
			sock = new Socket("127.0.0.1", 5000); // ���� ����� ���� ��Ʈ�� 5000�� ���Ű�� ��
			reader = new ObjectInputStream(sock.getInputStream());
			writer = new ObjectOutputStream(sock.getOutputStream());
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, "�������ӿ� �����Ͽ����ϴ�. ������ �����մϴ�.");
			ex.printStackTrace();
			dispose(); // ��Ʈ��ũ�� �ʱ� ���� �ȵǸ� Ŭ���̾�Ʈ ���� ����
		}
	} // close setUpNetworking

	private class LogButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == loginBtn)
				processLogin();
			else
				processLogout();
		}

		// �α��� ó��
		private void processLogin() {
			user = JOptionPane.showInputDialog("����� �̸��� �Է��ϼ���");
			try {
				writer.writeObject(new GameMessage(GameMessage.MsgType.LOGIN, user, "", ""));
				writer.flush();
				setTitle(frameTitle + " (�α��� : " + user + ")");
				isUserLogin = true;
				card.show(getContentPane(), "mainScreenPanel");
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(null, "�α��� �� �������ӿ� ������ �߻��Ͽ����ϴ�.");
				ex.printStackTrace();
			}
		}

		// �α׾ƿ� ó��
		private void processLogout() {
			int choice = JOptionPane.showConfirmDialog(null, "Logout�մϴ�");
			if (choice == JOptionPane.YES_OPTION) {
				try {
					writer.writeObject(new GameMessage(GameMessage.MsgType.LOGOUT, user, "", ""));
					writer.flush();
					// ����� ��� ��Ʈ���� ������ �ݰ� ���α׷��� ���� ��
					writer.close();
					reader.close();
					sock.close();
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, "�α׾ƿ� �� �������ӿ� ������ �߻��Ͽ����ϴ�. ���������մϴ�");
					ex.printStackTrace();
				} finally {
					System.exit(100); // Ŭ���̾�Ʈ ���� ����
				}
			}
		}
	}

	public class SendButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent ev) {
			String to = (String) counterParts.getSelectedValue();
			if (to == null) {
				JOptionPane.showMessageDialog(null, "�۽��� ����� ������ �� �޽����� ��������");
				return;
			}
			try {
				incoming.append(user + " : " + outgoing.getText() + "\n"); // ���� �޽��� â�� ���̱�
				writer.writeObject(new GameMessage(GameMessage.MsgType.CLIENT_MSG, user, to, outgoing.getText()));
				writer.flush();
				outgoing.setText("");
				outgoing.requestFocus();
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(null, "�޽��� ������ ������ �߻��Ͽ����ϴ�.");
				ex.printStackTrace();
			}
		}
	} // close SendButtonListener inner class

	public class IncomingReader implements Runnable {
		public void run() {
			GameMessage message;
			GameMessage.MsgType type;
			try {
				while (true) {
					message = (GameMessage) reader.readObject(); // �����α� ������ �޽��� ���
					type = message.getType();
					if (type == GameMessage.MsgType.LOGIN_FAILURE) { // �α����� ������ �����
						JOptionPane.showMessageDialog(null, "Login�� �����Ͽ����ϴ�. �ٽ� �α����ϼ���");
						setTitle(frameTitle + " : �α��� �ϼ���");
					} else if (type == GameMessage.MsgType.SERVER_MSG) { // �޽����� �޾Ҵٸ� ������
						if (message.getSender().equals(user))
							continue; // ���� ���� ������ ���� �ʿ� ����
						incoming.append(message.getSender() + " : " + message.getContents() + "\n");
					} else if (type == GameMessage.MsgType.LOGIN_LIST) {
						// ���� ����Ʈ�� ���� �ؼ� counterParts ����Ʈ�� �־� ��.
						// ���� ���� (""�� ����� ���� �� ����Ʈ �� �տ� ���� ��)
						String[] users = message.getContents().split("/");
						for (int i = 0; i < users.length; i++) {
							if (user.equals(users[i]))
								users[i] = "";
						}
						users = sortUsers(users); // ���� ����� ���� �� �� �ֵ��� �����ؼ� ����
						users[0] = GameMessage.ALL; // ����Ʈ �� �տ� "��ü"�� ������ ��
						counterParts.setListData(users);
						repaint();
					} else if (type == GameMessage.MsgType.NO_ACT) {
						// �ƹ� �׼��� �ʿ���� �޽���. �׳� ��ŵ
					} else if (type == GameMessage.MsgType.GAME_SET) {
						partnerName = message.getSender();
						incoming.append("//////////" + partnerName + "���� ���ӽ����� �������ϴ�.  " + user
								+ "���� ���ӽ����� ������ ������ ���۵˴ϴ�.//////////\n");
						yourReady = message.getUserCheck();
					} else if (type == GameMessage.MsgType.GAME_START) {
						partnerName = message.getSender();
						setTitle(frameTitle + "( " + user + " VS " + message.getSender() + " )");
						card.show(getContentPane(), "outPanel");
						buttonToggler(SUSPEND + REMAIN);
						JoystickFrame jsFrame = new JoystickFrame();
					} else if (type == GameMessage.MsgType.GAME_SUSPEND) {
						backgroundSound.stop();
						iconTimer.stop();
						secTimer.stop();
						buttonToggler(REPLAY);
					} else if (type == GameMessage.MsgType.GAME_REPLAY) {
						backgroundSound.loop();
						backgroundSound.play();
						iconTimer.start();
						secTimer.start();
						setFocusable(true);
						requestFocus();
						buttonToggler(SUSPEND + REMAIN);
					} else if (type == GameMessage.MsgType.GAME_STOPOVER) {
						try {
							writer.writeObject(new GameMessage(GameMessage.MsgType.GAME_STOPOVER, user, partnerName,
									myStopover, score));
							writer.flush();
						} catch (Exception ex) {
						}

						yourStopover = message.getStopover();
						partner_score = message.getScore();

						stopOver();
					} else if (type == GameMessage.MsgType.GAME_STATUS) {
						pscore_time = message.getStatus();
					} else if (type == GameMessage.MsgType.GAME_SCORE) {
						try {
							writer.writeObject(
									new GameMessage(GameMessage.MsgType.GAME_SCORE, user, partnerName, score, winLose));
							writer.flush();
						} catch (Exception ex) {
						}
						finishGame();

						partner_score = message.getScore();
						partner_winLose = message.getWinlose();

					} else if (type == GameMessage.MsgType.GAME_ATTACK) {
						sec = sec - 5;
					} 
					else if (type == GameMessage.MsgType.GAME_SSANG) {
						try {
							writer.writeObject(new GameMessage(GameMessage.MsgType.GAME_SSANG, partnerName, isSSang));
							writer.flush();
						} catch (Exception ex) {
						}
						partnerSSang = message.getSsang();
					}
					else {
						// ��ü�� Ȯ�ε��� �ʴ� �̻��� �޽���
						throw new Exception("�������� �� �� ���� �޽��� ��������");
					}
				} // close while
			} catch (Exception ex) {
				System.out.println("Ŭ���̾�Ʈ ������ ����"); // �������� ����� ��� �̸� ���� ������ ����
			}
		} // close run

		private String[] sortUsers(String[] users) {
			String[] outList = new String[users.length];
			ArrayList<String> list = new ArrayList<String>();
			for (String s : users) {
				list.add(s);
			}
			Collections.sort(list); // Collections.sort�� ����� �ѹ濡 ����
			for (int i = 0; i < users.length; i++) {
				outList[i] = list.get(i);
			}
			return outList;
		}
	} // close inner class

	public class StartListener implements ActionListener {
		public void actionPerformed(ActionEvent ev) {
			String to = (String) counterParts.getSelectedValue();
			partnerName = to;

			if (to == null) {
				JOptionPane.showMessageDialog(null, "��븦  �����ּ���");
				return;
			}
			if (to == "��ü") {
				JOptionPane.showMessageDialog(null, "���� �Ѹ� ���ϽǼ� �ֽ��ϴ�");
				return;
			}
			if (to != null && to != "��ü") {
				myReady = true;
				try {
					writer.writeObject(new GameMessage(GameMessage.MsgType.GAME_SET, user, to, myReady));
					writer.flush();
					incoming.append("//////////" + user + "���� ���ӽ����� �������ϴ�.  " + partnerName
							+ "���� ���ӽ����� ������ ������ ���۵˴ϴ�.//////////\n");
				} catch (Exception ex) {
				}

				// ���� ��� ��� ���ӽ����� ������ �� GAME_START �޽����� ������ ������ ���۵ȴ�
				if (myReady == true && yourReady == true) {
					try {
						writer.writeObject(new GameMessage(GameMessage.MsgType.GAME_START, user, to, true)); // ���ӽ��� ��,
						// boolean
						// uk=true
						// ��
						writer.flush();
					} catch (Exception ex) {
						JOptionPane.showMessageDialog(null, "�޽��� ������ ������ �߻��Ͽ����ϴ�");
						ex.printStackTrace();
					}
					setTitle(frameTitle + "( " + user + " VS " + partnerName + " )");

					card.show(getContentPane(), "outPanel");
					JoystickFrame jsFrame = new JoystickFrame();
				}
			}
		}
	}

	String suspendStr = "suspendAndReplay";

	public class SuspendListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (suspend.isEnabled()) {
				try {
					writer.writeObject(
							new GameMessage(GameMessage.MsgType.GAME_SUSPEND, user, partnerName, false, suspendStr)); // �Ͻ�����
					// ��,
					// boolean
					// uk=false��
					// �ٲ�
					writer.flush();
					// �����
					backgroundSound.stop();
					iconTimer.stop();
					secTimer.stop();

					buttonToggler(REPLAY);
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, "�Ͻ����� ������ �߻��Ͽ����ϴ�");
					ex.printStackTrace();
				}
			}
		}
	}

	// ***************************************************************************************
	// �̾���� ������
	public class ReplayListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (replay.isEnabled()) {
				try {
					writer.writeObject(
							new GameMessage(GameMessage.MsgType.GAME_REPLAY, user, partnerName, true, suspendStr)); // �̾����
					// ��,
					// �ٽ�
					// boolean
					// uk=true
					// ��
					writer.flush();
					// �ٽ� �����Ѵ�
					backgroundSound.loop();
					backgroundSound.play();
					iconTimer.restart();
					secTimer.restart();

					buttonToggler(SUSPEND + REMAIN);
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, "�Ͻ����� ������ �߻��Ͽ����ϴ�");
					ex.printStackTrace();
				}
				setFocusable(true);
				requestFocus();
			}
		}
	}

	// ***************************************************************************************
	// ���� ���� ������
	class ReSelectListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			int result = JOptionPane.showConfirmDialog(null, "������ �����Ͻðڽ��ϱ�?", "���ἱ��", JOptionPane.OK_CANCEL_OPTION);
			// Ȯ�� 1, ��� 2, ���� -1
			if (result == JOptionPane.YES_OPTION) {
				myStopover = true;

				try {
					writer.writeObject(
							new GameMessage(GameMessage.MsgType.GAME_STOPOVER, user, partnerName, myStopover, score));
					writer.flush();

				} catch (Exception ex) {
				}

				stopOver();

			} else if (result == JOptionPane.CANCEL_OPTION || result == JOptionPane.CLOSED_OPTION) {
				setFocusable(true);
				requestFocus();
			}
		}
	}

	private void stopOver() {
		score = 100 - sec;
		secTimer.stop();
		iconTimer.stop();

		backgroundSound.stop();
		card.show(getContentPane(), "endPanel");
		// partnerName="";
	}

	String winLose = "game";
	String partner_winLose;

	private void finishGame() {
		score = 100 - sec;
		secTimer.stop();
		iconTimer.stop();

		backgroundSound.stop();

		try {
			writer.writeObject(new GameMessage(GameMessage.MsgType.GAME_SCORE, user, partnerName, score, winLose));
			writer.flush();
		} catch (Exception ex) {
		}

		card.show(getContentPane(), "endPanel");
		// buttonToggler(SELECT+REMAIN);
		// partnerName="";
	}

	class BtnListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == rulesBtn) {
				getContentPane().add("rulesPanel", rulesPanel);
				card.show(getContentPane(), "rulesPanel");
			} else if (e.getSource() == fightBtn) {
				if(isUserLogin) {
					card.show(getContentPane(), "FightChoicePanel");
				}
				else
					JOptionPane.showMessageDialog(null, "�α��� ���ּ���!");
			}
			else if (e.getSource() == returnBtn) {
				card.show(getContentPane(), "FightChoicePanel");
			}
		}
	}

	class IceListener implements MouseListener {
		public void mouseClicked(MouseEvent e) {
		}

		public void mouseEntered(MouseEvent e) {
		}

		public void mouseExited(MouseEvent e) {
		}

		public void mousePressed(MouseEvent e) {
			for (int i = 0; i < 10; i++) { // ���̽�ũ�� ����Ʈ�� �ִ� 10�� �� � �� Ŭ���ߴ��� Ȯ���ؼ� dex�� ������ �ε����� ����
				if (e.getX() < iceList.get(i).pX + iceList.get(i).width && e.getX() > iceList.get(i).pX
						&& e.getY() < iceList.get(i).pY + iceList.get(i).height && e.getY() > iceList.get(i).pY) {
					dex = i;
					repaint();
				}
			}
		} // mousePressdd() end

		public void mouseReleased(MouseEvent e) {
			if (panel == 0) {
				if (iceList.get(dex).pX > 50 && iceList.get(dex).pX < 200 && iceList.get(dex).pY > 120
						&& iceList.get(dex).pY < 220) {
					if (dex != 1) { // �ٶ��� ��ǳ�� ��ǥ, �ٹ�� �ε���
						x.play();
						iceList.get(dex).pX = iceList.get(dex).originPX;
						iceList.get(dex).pY = iceList.get(dex).originPY;
						requestFocus();
						repaint();
					} else {
						wow.play();
						isBabam = true; // �˸°� ������ �������� true
						iceList.get(1).use = false; // false�� �ٲٸ� ���� �гκ��� �׸��� �׸��� �ʴ´�
					}
				}
				if (iceList.get(dex).pX > 500 && iceList.get(dex).pX < 620 && iceList.get(dex).pY > 100
						&& iceList.get(dex).pY < 200) {
					if (dex != 9) { // ȣ�α�� ���� ��ǳ�� ��ǥ, ȣ�θ��� �ε���
						x.play();
						iceList.get(dex).pX = iceList.get(dex).originPX;
						iceList.get(dex).pY = iceList.get(dex).originPY;
						requestFocus();
						repaint();
					} else {
						wow.play();
						isHodu = true;
						iceList.get(9).use = false;
					}
				}
				if (!isBabam && !isHodu) { // ��ǳ���ȿ� �� ���� ��
					x.play();
					if (iceList.get(dex).pX != iceList.get(dex).originPX
							|| iceList.get(dex).pY != iceList.get(dex).originPY) {
						iceList.get(dex).pX = iceList.get(dex).originPX; // ���� ��ġ�� �ű��
						iceList.get(dex).pY = iceList.get(dex).originPY;
						requestFocus();
						repaint();
					}
				}
				if (isBabam && !isHodu) { // �ٹ�ٴ� ���µ� ȣ�θ��簡 �� ���� ��
					if (dex != 1) {
						x.play();
						if (iceList.get(dex).pX != iceList.get(dex).originPX
								|| iceList.get(dex).pY != iceList.get(dex).originPY) {
							iceList.get(dex).pX = iceList.get(dex).originPX;
							iceList.get(dex).pY = iceList.get(dex).originPY;
							requestFocus();
							repaint();
						}
					}
				}
				if (!isBabam && isHodu) { // ȣ�θ���� ���µ� �ٹ�ٴ� �� ���� ��
					if (dex != 9) {
						x.play();
						if (iceList.get(dex).pX != iceList.get(dex).originPX
								|| iceList.get(dex).pY != iceList.get(dex).originPY) {
							iceList.get(dex).pX = iceList.get(dex).originPX;
							iceList.get(dex).pY = iceList.get(dex).originPY;
							requestFocus();
							repaint();
						}
					}
				}
				if (isHodu && isBabam) { // ȣ�θ��絵 ���� �ٹ�ٵ� ���� ��
					isKeyTimer = true;
					iconTimer.start();
				}
			} // if() panel == 0 end

			else if (panel == 1) { // ��ǳ�� �ȿ� ���� ��
				if (iceList.get(dex).pX > 170 && iceList.get(dex).pX < 320 && iceList.get(dex).pY > 100
						&& iceList.get(dex).pY < 200) {
					if (dex != 7) { // �߸��� ���̽�ũ���� ���ٸ�
						x.play();
						iceList.get(dex).pX = iceList.get(dex).originPX; // ���� ��ǥ�� �Űܶ�
						iceList.get(dex).pY = iceList.get(dex).originPY;
						requestFocus();
						repaint();
					} else {
						wow.play();
						isTank = true;
					}
				} else if (!isTank) { // ��ǳ���ȿ� �� ���� ��
					x.play();
					if (iceList.get(dex).pX != iceList.get(dex).originPX
							|| iceList.get(dex).pY != iceList.get(dex).originPY) {
						iceList.get(dex).pX = iceList.get(dex).originPX;
						iceList.get(dex).pY = iceList.get(dex).originPY;
						requestFocus();
						repaint();
					}
				} else { // ��ǳ�� �ȿ� ���µ� �ٸ� ���̽�ũ���� ������ ��
					x.play();
					if (iceList.get(dex).pX != iceList.get(dex).originPX
							|| iceList.get(dex).pY != iceList.get(dex).originPY) {
						iceList.get(dex).pX = iceList.get(dex).originPX; // ���� ��ǥ�� �Űܶ�
						iceList.get(dex).pY = iceList.get(dex).originPY;
						requestFocus();
						repaint();
					}
				}
			} // if() panel == 1 end

			else if (panel == 2) {
				if (iceList.get(dex).pX > 330 && iceList.get(dex).pX < 480 && iceList.get(dex).pY > 100
						&& iceList.get(dex).pY < 210) {
					if (dex != 5) {
						x.play();
						iceList.get(dex).pX = iceList.get(dex).originPX;
						iceList.get(dex).pY = iceList.get(dex).originPY;
						requestFocus();
						repaint();
					} else {
						wow.play();
						isCoffee = true;
					}
				} else if (!isCoffee) {
					x.play();
					if (iceList.get(dex).pX != iceList.get(dex).originPX
							|| iceList.get(dex).pY != iceList.get(dex).originPY) {
						iceList.get(dex).pX = iceList.get(dex).originPX;
						iceList.get(dex).pY = iceList.get(dex).originPY;
						requestFocus();
						repaint();
					}
				} else {
					x.play();
					if (iceList.get(dex).pX != iceList.get(dex).originPX
							|| iceList.get(dex).pY != iceList.get(dex).originPY) {
						iceList.get(dex).pX = iceList.get(dex).originPX;
						iceList.get(dex).pY = iceList.get(dex).originPY;
						requestFocus();
						repaint();
					}
				}
			} // if() panel == 2 end

			else if (panel == 3) {
				if (iceList.get(dex).pX > 160 && iceList.get(dex).pX < 325 && iceList.get(dex).pY > 60
						&& iceList.get(dex).pY < 185) {
					if (dex != 6) {
						x.play();
						iceList.get(dex).pX = iceList.get(dex).originPX;
						iceList.get(dex).pY = iceList.get(dex).originPY;
						requestFocus();
						repaint();
					} else {
						wow.play();
						isCrazy = true;
					}
				} else if (!isCrazy) {
					x.play();
					if (iceList.get(dex).pX != iceList.get(dex).originPX
							|| iceList.get(dex).pY != iceList.get(dex).originPY) {
						iceList.get(dex).pX = iceList.get(dex).originPX;
						iceList.get(dex).pY = iceList.get(dex).originPY;
						requestFocus();
						repaint();
					}
				} else {
					x.play();
					if (iceList.get(dex).pX != iceList.get(dex).originPX
							|| iceList.get(dex).pY != iceList.get(dex).originPY) {
						iceList.get(dex).pX = iceList.get(dex).originPX;
						iceList.get(dex).pY = iceList.get(dex).originPY;
						requestFocus();
						repaint();
					}
				}
			} // if() panel == 3 end

			else if (panel == 4) {
				if (iceList.get(dex).pX > 340 && iceList.get(dex).pX < 500 && iceList.get(dex).pY > 65
						&& iceList.get(dex).pY < 190) {
					if (dex != 4) {
						x.play();
						iceList.get(dex).pX = iceList.get(dex).originPX;
						iceList.get(dex).pY = iceList.get(dex).originPY;
						requestFocus();
						repaint();
					} else {
						wow.play();
						isIce = true;
					}
				} else if (!isIce) {
					x.play();
					if (iceList.get(dex).pX != iceList.get(dex).originPX
							|| iceList.get(dex).pY != iceList.get(dex).originPY) {
						iceList.get(dex).pX = iceList.get(dex).originPX;
						iceList.get(dex).pY = iceList.get(dex).originPY;
						requestFocus();
						repaint();
					}
				} else {
					x.play();
					if (iceList.get(dex).pX != iceList.get(dex).originPX
							|| iceList.get(dex).pY != iceList.get(dex).originPY) {
						iceList.get(dex).pX = iceList.get(dex).originPX;
						iceList.get(dex).pY = iceList.get(dex).originPY;
						requestFocus();
						repaint();
					}
				}
			} // if() panel == 4 end

			else if (panel == 5) {
				if (iceList.get(dex).pX > 75 && iceList.get(dex).pX < 235 && iceList.get(dex).pY > 53
						&& iceList.get(dex).pY < 178) {
					if (dex != 3) {
						x.play();
						iceList.get(dex).pX = iceList.get(dex).originPX;
						iceList.get(dex).pY = iceList.get(dex).originPY;
						requestFocus();
						repaint();
					} else {
						isSSang = true;
						try {
							writer.writeObject(new GameMessage(GameMessage.MsgType.GAME_SSANG, user, isSSang));
							writer.flush();
						} catch (Exception ex) {
						}
						finishGame();
					}
				} else if (!isSSang) {
					if (iceList.get(dex).pX != iceList.get(dex).originPX
							|| iceList.get(dex).pY != iceList.get(dex).originPY) {
						iceList.get(dex).pX = iceList.get(dex).originPX;
						iceList.get(dex).pY = iceList.get(dex).originPY;
						requestFocus();
						repaint();
					}
				} else {
					x.play();
					if (iceList.get(dex).pX != iceList.get(dex).originPX
							|| iceList.get(dex).pY != iceList.get(dex).originPY) {
						iceList.get(dex).pX = iceList.get(dex).originPX;
						iceList.get(dex).pY = iceList.get(dex).originPY;
						requestFocus();
						repaint();
					}
				}
			} // if() panel == 5 end
			score_time = panel * 50;
			try {
				writer.writeObject(new GameMessage(GameMessage.MsgType.GAME_STATUS, user, partnerName, score_time));
				writer.flush();
			} catch (Exception ex) {
			}
			System.out.println(score_time);
			repaint();
		}// mouseReleased() end

	}// ���̽� ������ end

	// ========================================== ���콺 ��� ������
	// ===================================================

	class IceDraggListener implements MouseMotionListener {
		public void mouseDragged(MouseEvent e) { // �巡���ϴ´��
			iceList.get(dex).pX = e.getX() - 30; // ���̽�ũ���� �����δ�
			iceList.get(dex).pY = e.getY() - 30;
			requestFocus();
			repaint();
		}

		public void mouseMoved(MouseEvent e) {
		}
	}

	// ========================================== Ű ������
	// ===================================================

	class MoveListener implements KeyListener {
		public void keyTyped(KeyEvent e) {
		}

		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_LEFT) { // ������ ������ ��
				if (isTank || isCrazy) { // ���̽�ũ���� ����� ���Ҵٸ�
					girlL.pX -= 10; // ��ǥ�� �Űܶ�

					if (moveCount > 9) // moveCount�� 9���� ũ��
						moveCount = 0; // �ʱ�ȭ
					if (moveCount < 3) // moveCount�� 0~2�� ����
						girlL.setImage(girlL1.getImage()); // ���� ù ��° �̹���
					else if (moveCount >= 3 && moveCount < 6) // moveCount�� 3~5�� ����
						girlL.setImage(girlL2.getImage()); // ���� �� ��° �̹���
					else // moveCount�� 6~8�� ����
						girlL.setImage(girlL3.getImage()); // ���� �� ��° �̹���

					if (girlL.pX == 200) { // 200 ��ǥ�� �������� ��
						if (isTank) { // ��ũ���̸� ���Ҵٸ� ù ��° ���̿����Ƿ�
							getContentPane().add("secondFloor", secondFloor); // �� ��° ������ �г��� �ٲ۴�
							card.show(getContentPane(), "secondFloor");
							isTank = false; // if(isTank)�� �ִ� ������ �̹� ���� �Ͽ����Ƿ� �ٽ� �� �ϱ� ���ؼ� false�� �ٲ۴�
							girlL.pX = 440; // �� ��° �������� ��ǥ�� ������ �ش�
						} else {
							getContentPane().add("fourthFloor", fourthFloor);
							card.show(getContentPane(), "fourthFloor");
						}
					}

					moveCount++; // moveCount�� ���� �̹����� ���������� �ٸ��� �׷��ش�
					requestFocus();
					repaint();
				} else // ���̽�ũ���� ���� �ʾҴ� �� �����̷� �ϸ�
					x.play(); // x �Ҹ� ����
			} // if(left) end

			if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
				if (isCoffee || isIce) {
					girlR.pX += 10;

					if (moveCount > 9)
						moveCount = 0;
					if (moveCount < 3)
						girlR.setImage(girlR1.getImage());
					else if (moveCount >= 3 && moveCount < 6)
						girlR.setImage(girlR2.getImage());
					else
						girlR.setImage(girlR3.getImage());

					if (girlR.pX == 440) {
						if (isCoffee) {
							getContentPane().add("thirdFloor", thirdFloor);
							card.show(getContentPane(), "thirdFloor");
							isCoffee = false;
							girlR.pX = 180;
						} else {
							getContentPane().add("fifthFloor", fifthFloor);
							card.show(getContentPane(), "fifthFloor");
						}
					}

					moveCount++;
					requestFocus();
					repaint();
				} else
					x.play();

			} // if(right) end

			if (e.getKeyCode() == KeyEvent.VK_DOWN) { // �Ʒ� ����Ű�� ������ ��
				isFire = false; // isFire�� false�� �ٲ㼭 �ҿ� �� �°� �Ѵ�
				if (isGirl) // �����̸�
					girlL.setImage(girlStop.getImage()); // ���� �̹����� ������ �̹����� �ٲٰ�
				else // �������̸�
					girlR.setImage(girlStop.getImage()); // ������ �̹����� ������ �̹����� �ٲ۴�
			}
		} // keyPressed() end

		public void keyReleased(KeyEvent e) { // ������ ����Ű�� ���� �ι�° �̹����� �ٽ� �����Ѵ�
			if (e.getKeyCode() == KeyEvent.VK_LEFT) {
				moveCount = 0;
				girlL.setImage(girlL2.getImage());
			}
			if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
				moveCount = 0;
				girlR.setImage(girlR2.getImage());
			}
			if (e.getKeyCode() == KeyEvent.VK_DOWN) {
				isFire = true;
				if (isGirl)
					girlL.setImage(girlL2.getImage());
				else
					girlR.setImage(girlR2.getImage());
			}
		} // keyReleased() end

	} // Ű ������ end

	public class JoystickFrame extends JFrame {
		JoystickFrame() {
			setTitle(user + "���� ���ӱ�");
			setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			setSize(420, 400);
			setLocationRelativeTo(null);
			setBackground(Color.WHITE);
			joystickPanel = new JoystickPanel();
			joystickPanel.add(suspend);
			joystickPanel.add(replay);
			joystickPanel.add(remain);

			add(joystickPanel);
			requestFocus();
			setFocusable(true);
			setVisible(true);
		}

		class JoystickPanel extends JPanel {
			public void paintComponent(Graphics g) {
				Image image = new ImageIcon(getClass().getResource("res/���ӱ�.png")).getImage(); // �̹��� ��������
				g.setColor(Color.white);
				g.fillRect(0, 0, getWidth(), getHeight());

				g.drawImage(image, 0, 10, 400, 400, null); // �̹��� �׸���
				suspend.setBounds(242, 230, 40, 40);
				replay.setBounds(309, 228, 40, 40);
				remain.setBounds(277, 194, 40, 40);

				g.setColor(Color.white);
				g.fillRoundRect(75, 20, 300, 15, 10, 10);

				g.setColor(Color.RED);
				g.fillRoundRect(75, 20, score_time, 15, 10, 10);

				g.setColor(Color.black);
				g.drawRoundRect(75, 20, getWidth() - 155, 15, 10, 10);

				g.setColor(Color.black);
				g.setFont(new Font("���� ���� B", Font.PLAIN, 20));
				g.drawString(user, 20, 35);
				g.drawString(partnerName, 20, 65);
				// g.drawImage(icon1, score_time+10, 36, 40, 35, this);

				// ����_������ ��
				g.setColor(Color.white);
				g.fillRoundRect(75, 50, getWidth() - 155, 15, 10, 10);

				g.setColor(Color.BLUE);
				g.fillRoundRect(75, 50, pscore_time, 15, 10, 10);

				g.setColor(Color.black);
				g.drawRoundRect(75, 50, getWidth() - 155, 15, 10, 10);
				// g.drawImage(icon2, pscore_time+10, 13, 40, 35, this);

				repaint();
			}
		}
	}
	// ========================================== Ÿ�̸�
	// ===================================================

	// �ð��� ����ϴ� Ÿ�̸�
	class SecTimer implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			if (sec > 1) // sec�� 100 ~ 2 ���̶��
				sec--; // 1�� ���̰�
			else if (sec == 30)
				backgroundSound.play();
			else { // 2�� ���� 1�� �� �� ���� �ʿ���
				finishGame();
				//�������� winlose=
			}
		}
	} // SecTimer end

	// Ű �����ܰ� �� �������� �������� ����ϴ� Ÿ�̸�
	class IconTimer implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			// Ű ������ ����
			if (isKeyTimer) {
				if (key.pY < 300) { // Ű�� Y��ǥ�� 300 �����̶�� ��ǥ�� 5�� �����Ѵ�
					key.pY += 4;
				} else { // Ű�� Y��ǥ�� 300�� �Ѿ� ���� ���� �гη� �ѱ��
					getContentPane().add("firstFloor", firstFloor);
					card.show(getContentPane(), "firstFloor");
				}
				requestFocus();
				repaint();
			} // if Ű ������ end

			// �� ������ ����
			if (isFireTimer) {
				for (int i = 0; i < fireList.size(); i++) {
					fireList.get(i).pY++; // �� �̹������� ��ġ��������

					if (fireList.get(i).pY == 180) { // �� �̹����� y��ǥ�� 180�� �����ϸ� ���ο� ���� ����Ʈ�� �߰����ش�
						fireList.add(new PosImageIcon(FIRE, (int) (Math.random() * 350 + 150), 0, true));
					}

					if (fireList.get(i).pY == 400) { // �� �̹����� y��ǥ�� 400�� �����ϸ� ���� ����Ʈ���� �����
						fireList.remove(0);
					}

					// ����
					if (isGirl) {
						if (girlL.collide(fireList.get(i), (girlL.height + fireList.get(i).height) / 2 - 25)) { // �Ұ�
							// ĳ���Ͱ�
							// �ε�����
							// ��
							if (isTank || isCrazy) { // ���̽�ũ���� ����� �÷����� �����̰�
								if (fireList.get(i).isHit && isFire) { // �Ʒ� ����Ű�� ������ �ʾҴٸ� ó�� �ε����� ��
									x.play();
									fireList.get(i).isHit = false; // isHit�� �ε����ٴ� �ɷ� �ٲ��ְ�
									fireList.get(i).setImage(clear.getImage()); // �� �̹����� ������ �̹����� �ٲ��ش�
									if (heartList.size() > 1) { // �׸��� ���(��Ʈ)�� 1�� �̻��̶��
										heartList.remove(0); // ����� �ϳ� �����ְ�
									} else {
										isHeartOver = true;
										finishGame();
									}
								}
							}
						} else { // �̹����� �Ÿ��� �浹�� ��ŭ�� �ƴ϶��
							fireList.get(i).isHit = true; // true�� �����ؼ� �浹�� �Ǵ��� �� �ְ� ���ش�
						}
					}

					// ������
					else {
						if (girlR.collide(fireList.get(i), (girlR.height + fireList.get(i).height) / 2 - 25)) {
							if (isCoffee || isIce) {
								if (fireList.get(i).isHit && isFire) {
									x.play();
									fireList.get(i).isHit = false;
									fireList.get(i).setImage(clear.getImage());
									if (heartList.size() > 1) {
										heartList.remove(0);
									} else {
										isHeartOver = true;
										finishGame();
									}
								}
							}
						} else {
							fireList.get(i).isHit = true;
						}
					}
					requestFocus();
					repaint();
				} // for() end
			}
			if (isAttackTimer) {
				for (int i = 0; i < attackList.size(); i++) {
					attackList.get(i).pY++; // �� �̹������� ��ġ��������
					if (attackList.get(i).pY == 390) { // �� �̹����� y��ǥ�� 180�� �����ϸ� ���ο� ���� ����Ʈ�� �߰����ش�
						attackList.add(new PosImageIcon(SBRAVO, (int) (Math.random() * 350 + 150), 0, true));
					}

					if (attackList.get(i).pY == 400) { // �� �̹����� y��ǥ�� 400�� �����ϸ� ���� ����Ʈ���� �����
						attackList.remove(0);
					}

					if (girlR.collide(attackList.get(i), (girlR.height + attackList.get(i).height) / 2 - 25)) {
						if (isCoffee || isIce) {
							if (attackList.get(i).isHit && isFire) {
								x.play();
								attackList.get(i).isHit = false;
								attackList.get(i).setImage(clear.getImage());
								try {
									writer.writeObject(
											new GameMessage(GameMessage.MsgType.GAME_ATTACK, user, partnerName));
									writer.flush();
								} catch (Exception ex) {
								}
							}
						}
					} else {
						attackList.get(i).isHit = true;

					}
					requestFocus();
					repaint();
					// if() �� ������ end
				}
			}
		}
	} // IconTimer end
} // IceCreamGame Class end