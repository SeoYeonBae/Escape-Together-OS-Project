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
	final String frameTitle = "더위탈출 대작전 ";
	// ================================== 어레이 리스트
	// =====================================

	ArrayList<PosImageIcon> iceList = new ArrayList<>();; // ICEGUY크림 이미지
	ArrayList<PosImageIcon> fireList = new ArrayList<>();; // 불 이미지
	ArrayList<PosImageIcon> heartList = new ArrayList<>();; // 생명 이미지
	ArrayList<PosImageIcon> attackList = new ArrayList<>();; // 생명 이미지

	// ================================== 이미지 스트링
	// =====================================

	final String COVER = "res/coverpage.png"; // 이미지 변환하기 쉽게 하기 위하여 스트링으로 저장
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
	final String NUGA = "src/res/누가바.png";
	final String BABAM = "src/res/바밤바.png";
	final String BRAVO = "src/res/부라보콘.png";
	final String SSANG = "src/res/쌍쌍바.png";
	final String ICEGUY = "src/res/아이스.png";
	final String COFFEE = "src/res/커피.png";
	final String CRAZY = "src/res/크레이지바.png";
	final String TANK = "src/res/탱크보이.png";
	final String PASI = "src/res/파시통통.png";
	final String HODU = "src/res/호두마루.png";
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
	final String SBRAVO = "src/res/작은부라보콘.png";
	private final String exit_img = "/res/나가기.png";
	private final String pause_img = "/res/일시정지.png";
	private final String replay_img = "/res/다시시작.png";

	// ==================================== 음악 스트링
	// ======================================

	final String START_SOUND = "res/bgm.wav"; // 음악
	final String WIN_SOUND = "res/winSound.wav";
	final String LOSE_SOUND = "res/loseSound.wav";
	final String X_SOUND = "res/Duck.wav";
	final String WOW = "res/Wow.wav";

	// =================================== 프레임 상수
	// ========================================

	final int WIN_WIDTH = 690; // 전체 frame의 폭
	final int WIN_HEIGHT = 577; // 전체 frame의 높이

	// =================================== 정수형 변수
	// ========================================

	int sec = 100; // 시간초
	int dex; // 아이스리스트에서 몇 번째 아이스크림을 선택했는지 알기 위한 변수
	int panel; // 패널에 따라 정답인 아이스크림이 달라서 몇 번 패널인지 알기 위한 변수
	int moveCount = 0; // 캐릭터의 움직임을 알아보기 위한 변수
	int score_time = 0; // 내 게이지
	int pscore_time = 0; // 상대 게이지
	int score;
	int partner_score; // 상대 점수

	// ================================= 불리안형
	// 변수===========================================

	boolean isBabam = false; // 제대로 들어가면 true 아니면 false
	boolean isHodu = false;
	boolean isTank = false;
	boolean isCoffee = false;
	boolean isCrazy = false;
	boolean isIce = false;
	boolean isSSang = false;
	boolean isGirl = true; // 캐릭터 방향 true = 왼쪽 , false = 오른쪽
	boolean isFire = true; // true = 불에 맞고, false = 불에 안 맞는다
	boolean isKeyTimer = false; // false = 타이머가 돌아가지 X true일 때만 돌아간다
	boolean isFireTimer = false;
	boolean isSecTimer = false;
	boolean isAttackTimer = false;
	boolean isHeartOver = false;
	boolean reP = true;
	boolean isUserLogin = false;

	// ============================ 포스이미지아이콘 형식의 이미지 선언
	// =================================

	PosImageIcon key;
	PosImageIcon girlL;
	PosImageIcon girlR;
	PosImageIcon timerSection;

	// ============================ 이미지 아이콘
	// =================================================

	ImageIcon girlL1; // 왼쪽
	ImageIcon girlL2;
	ImageIcon girlL3;
	ImageIcon girlR1; // 오른쪽
	ImageIcon girlR2;
	ImageIcon girlR3;
	ImageIcon girlStop; // 멈춤
	ImageIcon clear; // 투명 아이콘

	// ============================ 레이아웃 선언
	// ===================================================

	CardLayout card;

	// ============================ 패널 선언
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

	// ============================= 버튼 선언
	// =====================================================

	JButton loginBtn;
	JButton logoutBtn;
	JButton startBtn;
	JButton rulesBtn;
	JButton fightBtn;
	JButton sendButton;
	JButton returnBtn;

	// 버튼 토글
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

	// =========================== 음악
	// ============================================================

	AudioClip backgroundSound; // 게임 배경
	AudioClip winSound; // 이겼을 때
	AudioClip loseSound; // 졌을 때
	AudioClip x; // 불에 맞았을 때, 아이스크림을 잘못 놓았을 때
	AudioClip wow; // 아이스크림이 제대로 들어갔을 때

	// ========================= 타이머 선언
	// ========================================================

	Timer iconTimer; // 불과 아이스크림 이미지 모두 담당
	Timer secTimer; // 시간 초 담당 타이머
	//
	boolean myReady;
	boolean yourReady; // 준비, GAME_SET
	boolean partnerSSang;

	boolean myStopover;
	boolean yourStopover; // 도중정지, GAME_STOPOVER

	ObjectInputStream reader; // 수신용 스트림
	ObjectOutputStream writer; // 송신용 스트림
	String user; // 이 클라이언트로 로그인 한 유저의 이름
	String partnerName; // 상대방
	Socket sock; // 서버 연결용 소켓
	JScrollPane cScroller;
	JScrollPane qScroller;
	JScrollPane oScroller;
	JList counterParts; // 현재 로그인한 채팅 상대목록을 나타내는 리스트.
	JList scoreList;
	JLabel chat = new JLabel("채팅창");
	JLabel userList = new JLabel("접속자");
	JLabel scoreLabel = new JLabel("내 점수 : " + score);

	JTextArea incoming; // 수신된 메시지를 출력하는 곳
	JTextArea outgoing; // 송신할 메시지를 작성하는 곳
	// =========================== 메인
	// ===========================================================

	public static void main(String[] args) {
		try {
			IceCreamGame game = new IceCreamGame();
		} catch (Exception e) {
		}
	}

	// ========================== 구성자
	// ===========================================================

	IceCreamGame() {
		setTitle(frameTitle);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(WIN_WIDTH, WIN_HEIGHT);
		setLocationRelativeTo(null); // 프레임 중앙에 띄우기
		setResizable(false); // 프레임 사이즈 고정

		card = new CardLayout();
		getContentPane().setLayout(card); // 레이아웃을 카드로 설정하고 프레임에 씌우기

		girlL1 = new ImageIcon(LEFT1); // 이미지 아이콘 생성
		girlL2 = new ImageIcon(LEFT2);
		girlL3 = new ImageIcon(LEFT3);
		girlR1 = new ImageIcon(RIGHT1);
		girlR2 = new ImageIcon(RIGHT2);
		girlR3 = new ImageIcon(RIGHT3);
		girlStop = new ImageIcon(STOP);
		clear = new ImageIcon(CLEAR);

		partnerSSang = false; 

		girlL = new PosImageIcon(LEFT2, 430, 240, true); // 포스 이미지 아이콘 생성
		girlR = new PosImageIcon(RIGHT2, 180, 240, true);
		key = new PosImageIcon(KEY, 260, 70, true);
		timerSection = new PosImageIcon(TIME, 20, 300, true);

		iceList.add(new PosImageIcon(NUGA, 465, 438, true)); // 아이스 리스트에 이미지 더하기
		iceList.add(new PosImageIcon(BABAM, 387, 487, true));
		iceList.add(new PosImageIcon(BRAVO, 8, 437, true));
		iceList.add(new PosImageIcon(SSANG, 143, 481, true));
		iceList.add(new PosImageIcon(ICEGUY, 620, 435, true));
		iceList.add(new PosImageIcon(COFFEE, 65, 418, true));
		iceList.add(new PosImageIcon(CRAZY, 268, 487, true));
		iceList.add(new PosImageIcon(TANK, 506, 487, true));
		iceList.add(new PosImageIcon(PASI, 160, 435, true));
		iceList.add(new PosImageIcon(HODU, 316, 438, true));

		heartList.add(new PosImageIcon(HEART, 460, 7, true)); // 하트 리스트에 이미지 더하기
		heartList.add(new PosImageIcon(HEART, 530, 7, true));
		heartList.add(new PosImageIcon(HEART, 600, 7, true));

		fireList.add(new PosImageIcon(FIRE, (int) (Math.random() * 350 + 150), 0, true)); // 불 이미지 리스트에 추가하기

		attackList.add(new PosImageIcon(SBRAVO, (int) (Math.random() * 350 + 150), 0, true)); // 불 이미지 리스트에 추가하기

		coverPanel = new CoverPanel(); // 패널 만들기
		fightChoicePanel = new FightChoicePanel();
		outPanel = new OutSidePanel();
		rulesPanel = new RulesPanel();
		firstFloor = new FirstFloor();
		secondFloor = new SecondFloor();
		thirdFloor = new ThirdFloor();
		fourthFloor = new FourthFloor();
		fifthFloor = new FifthFloor();
		endPanel = new EndPanel();

		loginBtn = new JButton(); // 버튼 생성
		loginBtn.setBorderPainted(false); // 테두리 없애기
		loginBtn.setContentAreaFilled(false); // 채우는 색 없애기
		logoutBtn = new JButton();
		logoutBtn.setBorderPainted(false);
		logoutBtn.setContentAreaFilled(false);
		startBtn = new JButton(); // 버튼 생성
		startBtn.setBorderPainted(false); // 테두리 없애기
		startBtn.setContentAreaFilled(false); // 채우는 색 없애기
		rulesBtn = new JButton();
		rulesBtn.setBorderPainted(false);
		rulesBtn.setContentAreaFilled(false);
		fightBtn = new JButton();
		fightBtn.setBorderPainted(false);
		fightBtn.setContentAreaFilled(false);
		sendButton = new JButton("전송");
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

		iconTimer = new Timer(8, new IconTimer()); // delay를 8로 설정한 아이콘 타이머 생성
		secTimer = new Timer(1000, new SecTimer()); // 1초에 한 번씩 숫자 세는 타이머

		backgroundSound = JApplet.newAudioClip(getClass().getResource(START_SOUND)); // 배경 음악
		winSound = JApplet.newAudioClip(getClass().getResource(WIN_SOUND)); // 이겼을 때
		loseSound = JApplet.newAudioClip(getClass().getResource(LOSE_SOUND)); // 졌을 때
		x = JApplet.newAudioClip(getClass().getResource(X_SOUND)); // 아이스크림을 잘못 놓았거나 불에 맞았거나 아이스크림을 주지 않고 움직일 때
		wow = JApplet.newAudioClip(getClass().getResource(WOW)); // 아이스크림을 제대로 놓았을 때

		loginBtn.addActionListener(new LogButtonListener());
		logoutBtn.addActionListener(new LogButtonListener());
		startBtn.addActionListener(new StartListener()); // 버튼에 리스너 달기
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
		incoming.setFont(new Font("한컴 백제 B", Font.PLAIN, 25));
		qScroller = new JScrollPane(incoming);
		qScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		qScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

		outgoing = new JTextArea(5, 30);
		outgoing.setLineWrap(true);
		outgoing.setWrapStyleWord(true);
		outgoing.setEditable(true);
		outgoing.setFont(new Font("한컴 백제 B", Font.PLAIN, 25));
		oScroller = new JScrollPane(outgoing);
		oScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		oScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

		coverPanel.add(loginBtn); // 패널에 버튼 달기
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

		outPanel.addMouseListener(new IceListener()); // 패널에 마우스 리스너 달기
		outPanel.addMouseMotionListener(new IceDraggListener()); // 패널에 마우스 모션 리스너 달기
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
		addKeyListener(new MoveListener()); // 프레임에 키리스너 달기
		sendButton.addActionListener(new SendButtonListener());

		setUpNetworking();
		Thread readerThread = new Thread(new IncomingReader());
		readerThread.start();
		
		getContentPane().add("FightChoicePanel", fightChoicePanel);
		getContentPane().add("outPanel", outPanel);
		getContentPane().add("coverPanel", coverPanel); // 카드레이아웃에 "coverPanel"이란 이름의 coverPanel을 추가한다
		getContentPane().add("endPanel", endPanel);
		buttonToggler(SUSPEND + REMAIN);
		card.show(getContentPane(), "coverPanel"); // 카드레이아웃에서 "coverPanel"이란 이름을 가진 패널을 꺼내서 보여준다
		backgroundSound.play(); // 배경 음악 시작

		requestFocus();
		setFocusable(true);
		setVisible(true);

	} // IceCreamGame() end

	// ========================================== 패널 클래스
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
			Image image = new ImageIcon(getClass().getResource(COVER)).getImage(); // 이미지 가져오기
			g.drawImage(image, 0, 0, 684, 548, null); // 이미지 그리기
			loginBtn.setBounds(470, 380, 155, 60); // 시작 버튼
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
			for (PosImageIcon iceIcon : iceList) { // 아이스크림 그리기
				iceIcon.draw(g);
			}
			key.draw(g); // 열쇠 그리기
			panel = 0; // 패널마다 맞는 아이스크림이 달라서 패널 번호 부여
		}
	}// OutSidePanel class end

	class FirstFloor extends JPanel {
		public void paintComponent(Graphics g) {
			Image image = new ImageIcon(getClass().getResource(FIRST_FLOOR)).getImage();
			g.drawImage(image, 0, 0, null);
			for (PosImageIcon iceIcon : iceList) {
				if (iceIcon.use == true) // use가 true인 아이스크림만 그리기
					iceIcon.draw(g);
			}
			for (int i = 0; i < fireList.size(); i++) {
				fireList.get(i).draw(g); // 리스트에 있는 불 이미지 그리기
			}
			for (int i = 0; i < heartList.size(); i++) {
				if (heartList.get(i).use == true) // use가 true인 하트만 그리기
					heartList.get(i).draw(g);
			}
			timerSection.draw(g); // 시간초 이미지 그리기
			girlL.draw(g); // 왼쪽 이미지 그리기

			secTimer.start(); // 시간초 타이머 시작
			isKeyTimer = false; // 키 타이머 멈추기
			isFireTimer = true; // 불 그리기 시작

			g.setColor(Color.YELLOW);
			g.setFont(new Font("한컴 백제 B", Font.BOLD, 25));
			g.drawString(sec + "초", 60, 365);

			isGirl = true; // 왼쪽
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
				attackList.get(i).draw(g); // 리스트에 있는 불 이미지 그리기
			}
			for (int i = 0; i < heartList.size(); i++) {
				if (heartList.get(i).use == true)
					heartList.get(i).draw(g);
			}
			isAttackTimer = true;

			girlR.draw(g); // 오른쪽 이미지 그리기
			timerSection.draw(g);

			isGirl = false; // 오른쪽
			panel = 2;

			g.setColor(Color.YELLOW);
			g.setFont(new Font("한컴 백제 B", Font.BOLD, 25));
			g.drawString(sec + "초", 60, 365);
		}
	}// SecondFloor class end

	class ThirdFloor extends JPanel {
		public void paintComponent(Graphics g) {
			iceList.get(5).use = false;
			Image image = new ImageIcon(getClass().getResource(THIRD_FLOOR)).getImage(); // 이미지 가져오기
			g.drawImage(image, 0, 0, null); // 이미지 그리기
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
			g.setFont(new Font("한컴 백제 B", Font.BOLD, 25));
			g.drawString(sec + "초", 60, 365);
		}
	}// ThirdFloor class end

	class FourthFloor extends JPanel {
		public void paintComponent(Graphics g) {
			iceList.get(6).use = false;
			Image image = new ImageIcon(getClass().getResource(FOURTH_FLOOR)).getImage(); // 이미지 가져오기
			g.drawImage(image, 0, 0, null); // 이미지 그리기
			for (PosImageIcon icon : iceList) {
				if (icon.use == true)
					icon.draw(g);
			}
			for (int i = 0; i < fireList.size(); i++) {
				fireList.get(i).draw(g);
			}
			for (int i = 0; i < attackList.size(); i++) {
				attackList.get(i).draw(g); // 리스트에 있는 불 이미지 그리기
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
			g.setFont(new Font("한컴 백제 B", Font.BOLD, 25));
			g.drawString(sec + "초", 60, 365);
		}
	}// FourthFloor class end

	class FifthFloor extends JPanel {
		public void paintComponent(Graphics g) {
			iceList.get(4).use = false;
			Image image = new ImageIcon(getClass().getResource(FIFTH_FLOOR)).getImage(); // 이미지 가져오기
			g.drawImage(image, 0, 0, null); // 이미지 그리기
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
			g.setFont(new Font("한컴 백제 B", Font.BOLD, 25));
			g.drawString(sec + "초", 60, 365);
		}
	}// FifthFloor class end

	class EndPanel extends JPanel {
		public void paintComponent(Graphics g) {
			// 게임 끝까지 실행하거나 게이지 다 찰 때
			if (isHeartOver) {
				Image image = new ImageIcon(getClass().getResource(OVER_PAGE)).getImage(); // 이미지 가져오기
				g.drawImage(image, 0, 0, 684, 548, null); // 이미지 그리기

				g.setColor(Color.black);
				g.setFont(new Font("한컴 백제 B", Font.PLAIN, 40));
				g.drawString("내 소요시간 : 하트 소진", 300, 400);

				g.setFont(new Font("한컴 백제 B", Font.PLAIN, 25));
				g.drawString("상대방 소요시간 : " + partner_score + "초", 310, 445);

				backgroundSound.stop();
				loseSound.play();
			} else if (isSSang) {
				Image image = new ImageIcon(getClass().getResource(WIN_PAGE)).getImage(); // 이미지 가져오기
				g.drawImage(image, 0, 0, 684, 548, null); // 이미지 그리기

				g.setColor(Color.black);
				g.setFont(new Font("한컴 백제 B", Font.PLAIN, 40));
				g.drawString("내 소요시간 : " + score + "초", 300, 400);

				g.setFont(new Font("한컴 백제 B", Font.PLAIN, 25));
				g.drawString("상대방 소요시간 : " + partner_score + "초 ", 310, 445);

				backgroundSound.stop(); // 배경음악 멈추기
				winSound.play(); // 이겼을 때 음악 시작
			}

			else if(sec == 1){
				Image image = new ImageIcon(getClass().getResource(OVER_PAGE)).getImage(); // 이미지 가져오기
				g.drawImage(image, 0, 0, 684, 548, null); // 이미지 그리기

				g.setColor(Color.black);
				g.setFont(new Font("한컴 백제 B", Font.PLAIN, 40));
				g.drawString("내 소요시간 : " + score + "초", 300, 400);

				g.setFont(new Font("한컴 백제 B", Font.PLAIN, 25));
				g.drawString("상대방 소요시간 : " + partner_score + "초 ", 310, 445);

				backgroundSound.stop();
				loseSound.play();
			}

			else if(partnerSSang) {
				Image image = new ImageIcon(getClass().getResource(OVER_PAGE)).getImage(); // 이미지 가져오기
				g.drawImage(image, 0, 0, 684, 548, null); // 이미지 그리기

				g.setColor(Color.black);
				g.setFont(new Font("한컴 백제 B", Font.PLAIN, 40));
				g.drawString("내 소요시간 : " + score + "초", 300, 400);

				g.setFont(new Font("한컴 백제 B", Font.PLAIN, 25));
				g.drawString("상대방 소요시간 : " + partner_score + "초 ", 310, 445);

				backgroundSound.stop();
				loseSound.play();
			}else {
				Image image = new ImageIcon(getClass().getResource(WIN_PAGE)).getImage(); // 이미지 가져오기
				g.drawImage(image, 0, 0, 684, 548, null); // 이미지 그리기

				g.setColor(Color.black);
				g.setFont(new Font("한컴 백제 B", Font.PLAIN, 40));
				g.drawString("내 소요시간 : " + score + "초", 300, 400);

				g.setFont(new Font("한컴 백제 B", Font.PLAIN, 25));
				g.drawString("상대방소요시간 : " + partner_score + "초 ", 310, 445);

				backgroundSound.stop(); // 배경음악 멈추기
				winSound.play(); // 이겼을 때 음악 시작
			}


			// 도중 정지 버튼 클릭
			if (myStopover == true || yourStopover == true) {
				if (yourStopover == true) {
					Image image = new ImageIcon(getClass().getResource(WIN_PAGE)).getImage(); // 이미지 가져오기
					g.drawImage(image, 0, 0, 684, 548, null); // 이미지 그리기

					g.setColor(Color.black);
					g.setFont(new Font("한컴 백제 B", Font.PLAIN, 40));
					g.drawString("내 소요시간 : " + score, 150, 400);

					g.setFont(new Font("한컴 백제 B", Font.PLAIN, 25));
					g.drawString("상대방 소요시간 : 상대가 게임을 종료했습니다.", 65, 445);

					backgroundSound.stop(); // 배경음악 멈추기
					winSound.play(); // 이겼을 때 음악 시작
				}
				if (myStopover == true) {
					Image image = new ImageIcon(getClass().getResource(OVER_PAGE)).getImage(); // 이미지 가져오기
					g.drawImage(image, 0, 0, 684, 548, null); // 이미지 그리기

					g.setColor(Color.black);
					g.setFont(new Font("한컴 백제 B", Font.PLAIN, 40));
					g.drawString("내 소요시간 : 내가 게임을 종료했습니다.", 35,400);

					g.setFont(new Font("한컴 백제 B", Font.PLAIN, 25));
					g.drawString("상대방 소요시간 : " + partner_score, 160, 445);
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
			sock = new Socket("127.0.0.1", 5000); // 소켓 통신을 위한 포트는 5000번 사용키로 함
			reader = new ObjectInputStream(sock.getInputStream());
			writer = new ObjectOutputStream(sock.getOutputStream());
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, "서버접속에 실패하였습니다. 접속을 종료합니다.");
			ex.printStackTrace();
			dispose(); // 네트워크가 초기 연결 안되면 클라이언트 강제 종료
		}
	} // close setUpNetworking

	private class LogButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == loginBtn)
				processLogin();
			else
				processLogout();
		}

		// 로그인 처리
		private void processLogin() {
			user = JOptionPane.showInputDialog("사용자 이름을 입력하세요");
			try {
				writer.writeObject(new GameMessage(GameMessage.MsgType.LOGIN, user, "", ""));
				writer.flush();
				setTitle(frameTitle + " (로그인 : " + user + ")");
				isUserLogin = true;
				card.show(getContentPane(), "mainScreenPanel");
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(null, "로그인 중 서버접속에 문제가 발생하였습니다.");
				ex.printStackTrace();
			}
		}

		// 로그아웃 처리
		private void processLogout() {
			int choice = JOptionPane.showConfirmDialog(null, "Logout합니다");
			if (choice == JOptionPane.YES_OPTION) {
				try {
					writer.writeObject(new GameMessage(GameMessage.MsgType.LOGOUT, user, "", ""));
					writer.flush();
					// 연결된 모든 스트림과 소켓을 닫고 프로그램을 종료 함
					writer.close();
					reader.close();
					sock.close();
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, "로그아웃 중 서버접속에 문제가 발생하였습니다. 강제종료합니다");
					ex.printStackTrace();
				} finally {
					System.exit(100); // 클라이언트 완전 종료
				}
			}
		}
	}

	public class SendButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent ev) {
			String to = (String) counterParts.getSelectedValue();
			if (to == null) {
				JOptionPane.showMessageDialog(null, "송신할 대상을 선택한 후 메시지를 보내세요");
				return;
			}
			try {
				incoming.append(user + " : " + outgoing.getText() + "\n"); // 나의 메시지 창에 보이기
				writer.writeObject(new GameMessage(GameMessage.MsgType.CLIENT_MSG, user, to, outgoing.getText()));
				writer.flush();
				outgoing.setText("");
				outgoing.requestFocus();
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(null, "메시지 전송중 문제가 발생하였습니다.");
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
					message = (GameMessage) reader.readObject(); // 서버로기 부터의 메시지 대기
					type = message.getType();
					if (type == GameMessage.MsgType.LOGIN_FAILURE) { // 로그인이 실패한 경우라면
						JOptionPane.showMessageDialog(null, "Login이 실패하였습니다. 다시 로그인하세요");
						setTitle(frameTitle + " : 로그인 하세요");
					} else if (type == GameMessage.MsgType.SERVER_MSG) { // 메시지를 받았다면 보여줌
						if (message.getSender().equals(user))
							continue; // 내가 보낸 편지면 보일 필요 없음
						incoming.append(message.getSender() + " : " + message.getContents() + "\n");
					} else if (type == GameMessage.MsgType.LOGIN_LIST) {
						// 유저 리스트를 추출 해서 counterParts 리스트에 넣어 줌.
						// 나는 빼고 (""로 만들어 정렬 후 리스트 맨 앞에 오게 함)
						String[] users = message.getContents().split("/");
						for (int i = 0; i < users.length; i++) {
							if (user.equals(users[i]))
								users[i] = "";
						}
						users = sortUsers(users); // 유저 목록을 쉽게 볼 수 있도록 정렬해서 제공
						users[0] = GameMessage.ALL; // 리스트 맨 앞에 "전체"가 들어가도록 함
						counterParts.setListData(users);
						repaint();
					} else if (type == GameMessage.MsgType.NO_ACT) {
						// 아무 액션이 필요없는 메시지. 그냥 스킵
					} else if (type == GameMessage.MsgType.GAME_SET) {
						partnerName = message.getSender();
						incoming.append("//////////" + partnerName + "님이 게임시작을 눌렀습니다.  " + user
								+ "님이 게임시작을 누르면 게임이 시작됩니다.//////////\n");
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
						// 정체가 확인되지 않는 이상한 메시지
						throw new Exception("서버에서 알 수 없는 메시지 도착했음");
					}
				} // close while
			} catch (Exception ex) {
				System.out.println("클라이언트 스레드 종료"); // 프레임이 종료될 경우 이를 통해 스레드 종료
			}
		} // close run

		private String[] sortUsers(String[] users) {
			String[] outList = new String[users.length];
			ArrayList<String> list = new ArrayList<String>();
			for (String s : users) {
				list.add(s);
			}
			Collections.sort(list); // Collections.sort를 사용해 한방에 정렬
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
				JOptionPane.showMessageDialog(null, "상대를  정해주세요");
				return;
			}
			if (to == "전체") {
				JOptionPane.showMessageDialog(null, "상대는 한명만 정하실수 있습니다");
				return;
			}
			if (to != null && to != "전체") {
				myReady = true;
				try {
					writer.writeObject(new GameMessage(GameMessage.MsgType.GAME_SET, user, to, myReady));
					writer.flush();
					incoming.append("//////////" + user + "님이 게임시작을 눌렀습니다.  " + partnerName
							+ "님이 게임시작을 누르면 게임이 시작됩니다.//////////\n");
				} catch (Exception ex) {
				}

				// 나와 상대 모두 게임시작을 눌렀을 때 GAME_START 메시지를 보내고 게임이 시작된다
				if (myReady == true && yourReady == true) {
					try {
						writer.writeObject(new GameMessage(GameMessage.MsgType.GAME_START, user, to, true)); // 게임시작 시,
						// boolean
						// uk=true
						// 됨
						writer.flush();
					} catch (Exception ex) {
						JOptionPane.showMessageDialog(null, "메시지 전송중 문제가 발생하였습니다");
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
							new GameMessage(GameMessage.MsgType.GAME_SUSPEND, user, partnerName, false, suspendStr)); // 일시정지
					// 시,
					// boolean
					// uk=false로
					// 바꿈
					writer.flush();
					// 멈춘다
					backgroundSound.stop();
					iconTimer.stop();
					secTimer.stop();

					buttonToggler(REPLAY);
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, "일시정지 문제가 발생하였습니다");
					ex.printStackTrace();
				}
			}
		}
	}

	// ***************************************************************************************
	// 이어시작 리스너
	public class ReplayListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (replay.isEnabled()) {
				try {
					writer.writeObject(
							new GameMessage(GameMessage.MsgType.GAME_REPLAY, user, partnerName, true, suspendStr)); // 이어시작
					// 시,
					// 다시
					// boolean
					// uk=true
					// 됨
					writer.flush();
					// 다시 시작한다
					backgroundSound.loop();
					backgroundSound.play();
					iconTimer.restart();
					secTimer.restart();

					buttonToggler(SUSPEND + REMAIN);
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, "일시정지 문제가 발생하였습니다");
					ex.printStackTrace();
				}
				setFocusable(true);
				requestFocus();
			}
		}
	}

	// ***************************************************************************************
	// 도중 정지 리스너
	class ReSelectListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			int result = JOptionPane.showConfirmDialog(null, "게임을 종료하시겠습니까?", "종료선택", JOptionPane.OK_CANCEL_OPTION);
			// 확인 1, 취소 2, 엑스 -1
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
					JOptionPane.showMessageDialog(null, "로그인 해주세요!");
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
			for (int i = 0; i < 10; i++) { // 아이스크림 리스트에 있는 10개 중 어떤 걸 클릭했는지 확인해서 dex에 선택한 인덱스를 저장
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
					if (dex != 1) { // 다람쥐 말풍선 좌표, 바밤바 인덱스
						x.play();
						iceList.get(dex).pX = iceList.get(dex).originPX;
						iceList.get(dex).pY = iceList.get(dex).originPY;
						requestFocus();
						repaint();
					} else {
						wow.play();
						isBabam = true; // 알맞게 가져다 놓았으면 true
						iceList.get(1).use = false; // false로 바꾸면 다음 패널부터 그림을 그리지 않는다
					}
				}
				if (iceList.get(dex).pX > 500 && iceList.get(dex).pX < 620 && iceList.get(dex).pY > 100
						&& iceList.get(dex).pY < 200) {
					if (dex != 9) { // 호두까기 인형 말풍선 좌표, 호두마루 인덱스
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
				if (!isBabam && !isHodu) { // 말풍선안에 안 들어갔을 때
					x.play();
					if (iceList.get(dex).pX != iceList.get(dex).originPX
							|| iceList.get(dex).pY != iceList.get(dex).originPY) {
						iceList.get(dex).pX = iceList.get(dex).originPX; // 원래 위치로 옮긴다
						iceList.get(dex).pY = iceList.get(dex).originPY;
						requestFocus();
						repaint();
					}
				}
				if (isBabam && !isHodu) { // 바밤바는 들어갔는데 호두마루가 안 들어갔을 때
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
				if (!isBabam && isHodu) { // 호두마루는 들어갔는데 바밤바는 안 들어갔을 때
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
				if (isHodu && isBabam) { // 호두마루도 들어가고 바밤바도 들어갔을 때
					isKeyTimer = true;
					iconTimer.start();
				}
			} // if() panel == 0 end

			else if (panel == 1) { // 말풍선 안에 들어갔을 때
				if (iceList.get(dex).pX > 170 && iceList.get(dex).pX < 320 && iceList.get(dex).pY > 100
						&& iceList.get(dex).pY < 200) {
					if (dex != 7) { // 잘못된 아이스크림이 들어간다면
						x.play();
						iceList.get(dex).pX = iceList.get(dex).originPX; // 원래 좌표로 옮겨라
						iceList.get(dex).pY = iceList.get(dex).originPY;
						requestFocus();
						repaint();
					} else {
						wow.play();
						isTank = true;
					}
				} else if (!isTank) { // 말풍선안에 안 들어갔을 때
					x.play();
					if (iceList.get(dex).pX != iceList.get(dex).originPX
							|| iceList.get(dex).pY != iceList.get(dex).originPY) {
						iceList.get(dex).pX = iceList.get(dex).originPX;
						iceList.get(dex).pY = iceList.get(dex).originPY;
						requestFocus();
						repaint();
					}
				} else { // 말풍선 안에 들어갔는데 다른 아이스크림을 움직일 때
					x.play();
					if (iceList.get(dex).pX != iceList.get(dex).originPX
							|| iceList.get(dex).pY != iceList.get(dex).originPY) {
						iceList.get(dex).pX = iceList.get(dex).originPX; // 원래 좌표로 옮겨라
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

	}// 아이스 리스너 end

	// ========================================== 마우스 모션 리스너
	// ===================================================

	class IceDraggListener implements MouseMotionListener {
		public void mouseDragged(MouseEvent e) { // 드래그하는대로
			iceList.get(dex).pX = e.getX() - 30; // 아이스크림을 움직인다
			iceList.get(dex).pY = e.getY() - 30;
			requestFocus();
			repaint();
		}

		public void mouseMoved(MouseEvent e) {
		}
	}

	// ========================================== 키 리스너
	// ===================================================

	class MoveListener implements KeyListener {
		public void keyTyped(KeyEvent e) {
		}

		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_LEFT) { // 왼쪽을 눌렀을 때
				if (isTank || isCrazy) { // 아이스크림을 제대로 놓았다면
					girlL.pX -= 10; // 좌표를 옮겨라

					if (moveCount > 9) // moveCount가 9보다 크면
						moveCount = 0; // 초기화
					if (moveCount < 3) // moveCount가 0~2일 때는
						girlL.setImage(girlL1.getImage()); // 왼쪽 첫 번째 이미지
					else if (moveCount >= 3 && moveCount < 6) // moveCount가 3~5일 때는
						girlL.setImage(girlL2.getImage()); // 왼쪽 두 번째 이미지
					else // moveCount가 6~8일 때는
						girlL.setImage(girlL3.getImage()); // 왼쪽 세 번째 이미지

					if (girlL.pX == 200) { // 200 좌표에 도달했을 때
						if (isTank) { // 탱크보이를 놓았다면 첫 번째 층이였으므로
							getContentPane().add("secondFloor", secondFloor); // 두 번째 층으로 패널을 바꾼다
							card.show(getContentPane(), "secondFloor");
							isTank = false; // if(isTank)에 있는 내용은 이미 실행 하였으므로 다시 안 하기 위해서 false로 바꾼다
							girlL.pX = 440; // 세 번째 층에서의 좌표를 지정해 준다
						} else {
							getContentPane().add("fourthFloor", fourthFloor);
							card.show(getContentPane(), "fourthFloor");
						}
					}

					moveCount++; // moveCount에 따라서 이미지를 순차적으로 다르게 그려준다
					requestFocus();
					repaint();
				} else // 아이스크림을 놓지 않았는 데 움직이려 하면
					x.play(); // x 소리 시작
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

			if (e.getKeyCode() == KeyEvent.VK_DOWN) { // 아래 방향키를 눌렀을 때
				isFire = false; // isFire를 false로 바꿔서 불에 안 맞게 한다
				if (isGirl) // 왼쪽이면
					girlL.setImage(girlStop.getImage()); // 왼쪽 이미지를 정지한 이미지로 바꾸고
				else // 오른쪽이면
					girlR.setImage(girlStop.getImage()); // 오른쪽 이미지를 정지한 이미지로 바꾼다
			}
		} // keyPressed() end

		public void keyReleased(KeyEvent e) { // 눌렀던 방향키를 떼면 두번째 이미지로 다시 세팅한다
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

	} // 키 리스너 end

	public class JoystickFrame extends JFrame {
		JoystickFrame() {
			setTitle(user + "님의 게임기");
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
				Image image = new ImageIcon(getClass().getResource("res/게임기.png")).getImage(); // 이미지 가져오기
				g.setColor(Color.white);
				g.fillRect(0, 0, getWidth(), getHeight());

				g.drawImage(image, 0, 10, 400, 400, null); // 이미지 그리기
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
				g.setFont(new Font("한컴 백제 B", Font.PLAIN, 20));
				g.drawString(user, 20, 35);
				g.drawString(partnerName, 20, 65);
				// g.drawImage(icon1, score_time+10, 36, 40, 35, this);

				// 상대방_게이지 위
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
	// ========================================== 타이머
	// ===================================================

	// 시간을 담당하는 타이머
	class SecTimer implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			if (sec > 1) // sec가 100 ~ 2 사이라면
				sec--; // 1씩 줄이고
			else if (sec == 30)
				backgroundSound.play();
			else { // 2가 들어가서 1이 된 그 다음 초에는
				finishGame();
				//내꺼에는 winlose=
			}
		}
	} // SecTimer end

	// 키 아이콘과 불 아이콘의 움직임을 담당하는 타이머
	class IconTimer implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			// 키 움직임 제어
			if (isKeyTimer) {
				if (key.pY < 300) { // 키의 Y좌표가 300 이전이라면 좌표를 5씩 변경한다
					key.pY += 4;
				} else { // 키의 Y좌표가 300이 넘어 가면 다음 패널로 넘긴다
					getContentPane().add("firstFloor", firstFloor);
					card.show(getContentPane(), "firstFloor");
				}
				requestFocus();
				repaint();
			} // if 키 움직임 end

			// 불 움직임 제어
			if (isFireTimer) {
				for (int i = 0; i < fireList.size(); i++) {
					fireList.get(i).pY++; // 불 이미지들의 위치를내린다

					if (fireList.get(i).pY == 180) { // 불 이미지의 y좌표가 180에 도달하면 새로운 불을 리스트에 추가해준다
						fireList.add(new PosImageIcon(FIRE, (int) (Math.random() * 350 + 150), 0, true));
					}

					if (fireList.get(i).pY == 400) { // 불 이미지의 y좌표가 400에 도달하면 불을 리스트에서 지운다
						fireList.remove(0);
					}

					// 왼쪽
					if (isGirl) {
						if (girlL.collide(fireList.get(i), (girlL.height + fireList.get(i).height) / 2 - 25)) { // 불과
							// 캐릭터가
							// 부딪혔을
							// 때
							if (isTank || isCrazy) { // 아이스크림을 제대로 올려놓은 상태이고
								if (fireList.get(i).isHit && isFire) { // 아래 방향키를 누르지 않았다면 처음 부딪혔을 때
									x.play();
									fireList.get(i).isHit = false; // isHit을 부딪혔다는 걸로 바꿔주고
									fireList.get(i).setImage(clear.getImage()); // 불 이미지를 투명한 이미지로 바꿔준다
									if (heartList.size() > 1) { // 그리고 목숨(하트)이 1개 이상이라면
										heartList.remove(0); // 목숨을 하나 지워주고
									} else {
										isHeartOver = true;
										finishGame();
									}
								}
							}
						} else { // 이미지의 거리가 충돌할 만큼이 아니라면
							fireList.get(i).isHit = true; // true로 설정해서 충돌을 판단할 수 있게 해준다
						}
					}

					// 오른쪽
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
					attackList.get(i).pY++; // 불 이미지들의 위치를내린다
					if (attackList.get(i).pY == 390) { // 불 이미지의 y좌표가 180에 도달하면 새로운 불을 리스트에 추가해준다
						attackList.add(new PosImageIcon(SBRAVO, (int) (Math.random() * 350 + 150), 0, true));
					}

					if (attackList.get(i).pY == 400) { // 불 이미지의 y좌표가 400에 도달하면 불을 리스트에서 지운다
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
					// if() 불 움직임 end
				}
			}
		}
	} // IconTimer end
} // IceCreamGame Class end