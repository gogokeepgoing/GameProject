package FlashGameProject;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Timer;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;



@SuppressWarnings("serial")
public class HANGMAN extends JFrame implements ActionListener, MouseListener {
//	private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
//	private static final String URL = "jdbc:mysql://127.0.0.1:3306/db";
//	private static final String USER = "root";
//	private static final String PASS = "1234";

	public static final String FILE = "dictionary.txt";
	// GUI창 크기
	private static final int WIDTH = 600;
	private static final int HEIGHT = 550;
	// 상단 메뉴 바
	private static final String FILE_START = "Play";
	private static final String FILE_STOP = "Exit";
	public static final String SHOW_REPLAY = "Play Again?";
	// 파일 메뉴바에 적용 시킬 변수
	private int state = 0;
	// 랜덤 단어
	public Random rGen = new Random();
	// 랜덤 단어의 알파벳 문자열
	private static char[] randPhrase;
	// 파일에서 단어를 끌어오는 배열
	private static String[] words;
	// 유저가 맞추는 문자열
	private static char[] guesses;
	// 숫자가 올라갈 때 마다 그림이 완성되는 변수
	public static int numBodyParts = 0;
	// 알파벳을 맞추면 표기되는 문자
	private static String numGuesses = "";
	// 다중 호출을 방지하기 위해 임의의 1단어를 지정
	public static String phrase;
	public static JPanel mainPanel, keyPanel, buttonPanel, timePanel;
	public Timer timer;
	public JLabel timelabel;

	public HANGMAN() {
		setTitle("Hang Man");
		setSize(WIDTH, HEIGHT);

		mainPanel = new JPanel();
		mainPanel.setLayout(new FlowLayout());

		ImageIcon background = new ImageIcon("Image/background.png"); // 배경 이미지
		JLabel label = new JLabel(background);
		label.setSize(WIDTH, HEIGHT);
		mainPanel.add(label);

		keyPanel = new JPanel(); // 키보드 패널
		keyPanel.setLayout(new GridLayout(3, 8));
		keyPanel.setBackground(Color.LIGHT_GRAY);

		buttonPanel = new JPanel(); // 리플레이, 종료 버튼 패널
		buttonPanel.setBackground(Color.LIGHT_GRAY);

		add(mainPanel);
		mainPanel.add(keyPanel, BorderLayout.SOUTH); // 매인 패널 속에서 아래쪽에 KEY 패널 위치
		add(buttonPanel, BorderLayout.AFTER_LAST_LINE); // below 패널을 맨 밑에 위치

		buttonPanel.setVisible(false);

		createMenuBar();
		createButtons(keyPanel);
		replayButtons(buttonPanel);
		addMouseListener(this);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Gui 창을 끄면 콘솔 종료
		setResizable(false); // 크기 조절 불가능
		setLocationRelativeTo(null); // 실행시 화면 중앙에 실행됨
		setVisible(true); // gui 창이 보여지게 하는 명령어


	}

	// 버튼 패널에 다시 시작, 종료 버튼을 생성하고 ActionLister를 추가하는 메소드
	public void replayButtons(JPanel buttonPanel) {
		JButton playAgain = new JButton(SHOW_REPLAY);
		playAgain.setSize(80, 80);
		playAgain.setActionCommand(SHOW_REPLAY);
		playAgain.addActionListener(this);
		JButton exit = new JButton(FILE_STOP);
		exit.setActionCommand(FILE_STOP);
		exit.addActionListener(this);
		exit.setSize(80, 80);
		buttonPanel.add(playAgain);
		buttonPanel.add(exit);
	}

	// 키 패널에 알파벳 버튼 배열을 생성하고 ActionLister를 추가하는 메소드
	public void createButtons(JPanel keyPanel) {

		JButton[] buttons = new JButton[26];
		String[] letters = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R",
				"S", "T", "U", "V", "W", "X", "Y", "Z" };

		for (int i = 0; i < buttons.length; i++) {
			Font font = new Font("DungGeunMo", Font.BOLD, 24);

			buttons[i] = new JButton(letters[i]);
			buttons[i].setSize(20, 10);
			buttons[i].setFont(font);
			buttons[i].setActionCommand(letters[i]);
			buttons[i].addActionListener(this);

			keyPanel.add(buttons[i]);
		}

	}

	// 메뉴를 추가하는 메소드
	public void createMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		// 파일 메뉴를 추가
		JMenu fileMenu = new JMenu("File");
		menuBar.add(fileMenu);

		// play, exit 메뉴 아이템 추가
		createMenuItem(fileMenu, FILE_START);
		createMenuItem(fileMenu, FILE_STOP);

	}

	// 메뉴와 메뉴 아이템에 ActionListener 추가
	public void createMenuItem(JMenu menu, String itemName) {
		JMenuItem menuItem = new JMenuItem(itemName);
		menuItem.addActionListener(this);
		menu.add(menuItem);
	}

	public void paint(Graphics g) {
		super.paint(g);

		Font font = new Font("DungGeunMo", Font.BOLD, 24);
		g.setFont(font);
		g.setColor(Color.WHITE);

		// 메뉴에서 play를 선택하면 게임 실행
		if (state == 1) {
			gameMessages(g);
			String result = "";
			for (int i = 0; i < guesses.length; i++) {
				result += guesses[i] + " ";
			}
			g.drawString(result, 300, 250);
			g.drawString("G U E S S E S", 300, 200);
			g.drawString(numGuesses, 300, 350);
			System.out.println(randPhrase);

			// 틀릴 때 마다 그림 추가
			hangman(g);
		}
	}
	public void CountDown(Graphics g) {

		 Font font = new Font("DungGeunMo", Font.BOLD, 40);
	      g.setFont(font);
	      g.setColor(Color.WHITE);

	      if (state == 1) {
	         for (int i = 30; i >= 0; i--) {
	            System.out.println(i);
	            g.drawString(Integer.toString(i), 500, 110);
	            
	            try {
	               Thread.sleep(1000);
	            } catch (InterruptedException e) {
	               System.out.println(e);
	               e.printStackTrace();
	            }
	            if (i == 0) {
	               numBodyParts = 0;
	               numGuesses = "";
	               keyPanel.setVisible(true);
	               buttonPanel.setVisible(true);
	               state = 1;
	               play();
	               repaint();
	               JOptionPane.showMessageDialog(null, "Time Over \n Try Again");
	               System.out.println("Lost");
	            }
	         }
	      }
	   }

	// 틀릴 때 마다 그림 추가되는 메소드
	private void hangman(Graphics g) {

		Image img1 = new ImageIcon("Image/Hang.png").getImage();
		g.drawImage(img1, 70, 165, this);

		if (numBodyParts == 1) {
			Image img = new ImageIcon("Image/Hangman1A.png").getImage();
			g.drawImage(img, 185, 210, this);
		}

		if (numBodyParts == 2) {
			Image img = new ImageIcon("Image/Hangman2A.png").getImage();
			g.drawImage(img, 185, 210, this);

		}

		if (numBodyParts == 3) {
			Image img = new ImageIcon("Image/Hangman3A.png").getImage();
			g.drawImage(img, 185, 210, this);

		}

		if (numBodyParts == 4) {
			Image img = new ImageIcon("Image/Hangman4A.png").getImage();
			g.drawImage(img, 185, 210, this);

		}

		if (numBodyParts == 5) {
			Image img = new ImageIcon("Image/Hangman5A.png").getImage();
			g.drawImage(img, 185, 210, this);
		}

		if (numBodyParts == 6) {
			Image img = new ImageIcon("Image/Hangman6A.png").getImage();
			g.drawImage(img, 185, 210, this);

		}

		// 패배시 초기화 후 게임 재시작
		if (numBodyParts == 7) {
			numBodyParts = 0;
			numGuesses = "";
			keyPanel.setVisible(true);
			buttonPanel.setVisible(true);
			state = 1;
			play();
			repaint();
			JOptionPane.showMessageDialog(null, "You Lost\nTry Again");
			System.out.println("Lost");
		}
	}

	private void gameMessages(Graphics g) {
		
		
		if (!winner()) {
			// 게임 실행시 나타나는 메세지
			g.drawString("Let's Play Hang Man!!", 25, 100);
	//		CountDown(g);


		} else if (winner() && numBodyParts < 6) {
			System.out.println("Win");
			JOptionPane.showMessageDialog(null, "Win!");
			keyPanel.setVisible(true);
			buttonPanel.setVisible(true);
		}

	}

	// txt 파일에서 단어를 가져오는 메소드
	public String getword() {
		words = textFile();

		int n = words.length;
		int r = rGen.nextInt(n);
		String word = words[r];

		return word;
	}

	// 선택한 키보드 값에 따라 boolean값이 바뀌는 메소드
	public boolean winner() {
		if (Arrays.equals(guesses, randPhrase)) {
			return true;
		} else {
			return false;
		}

	}

	// 랜덤으로 가져온 단어를 알파벳 배열로 변환

	public String[] textFile() {
		BufferedReader reader = null;
		// 읽어온 파일에서 단어 배열을 생성 후 저장
		List<String> wordList = new ArrayList<String>();
		try {
			reader = new BufferedReader(new FileReader(FILE));
			String s = null;

			while ((s = reader.readLine()) != null) {
				wordList.add(s);
			}

		} catch (IOException e) {
			System.out.println(e.getMessage());
			System.exit(-1);
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				System.out.println(e.getMessage());
				System.exit(-1);
			}
		}
		// 파일에서 읽어온 단어 배열을 다시 정렬하여 반환
		return wordList.toArray(new String[wordList.size()]);

	}

	public void actionPerformed(ActionEvent e) {

		String command = e.getActionCommand();

		if (command.equals(FILE_START)) {
			state = 1;
			play();
			buttonPanel.setVisible(true);
			repaint();

		}

		else if (command.length() == 1 && state == 1) {
			letters(command);
		}

		else if (command.equals(SHOW_REPLAY)) {

			numBodyParts = 0;
			numGuesses = "";
			keyPanel.setVisible(true);
			state = 1;
			play();
			repaint();

		} else if (command.equals(FILE_STOP)) {
			state = 2;
			System.exit(-1);
		}

	}

	// method receives actionevent from JButtons and compares it to randphrase
	// array
	public void letters(String command) {

		System.out.println(command);

		if (phrase.contains(command.toLowerCase())) {
			for (int i = 0; i < randPhrase.length; i++) {
				if (command.toLowerCase().charAt(0) == randPhrase[i]) {
					guesses[i] = command.toLowerCase().charAt(0);

				}

			}
			// if letter does not match - bodycounter increases
		} else if (!phrase.contains(command.toLowerCase())) {
			JOptionPane.showMessageDialog(null, "Sorry " + command + " is not part of the word");
			numBodyParts++;
		}

		// concatenation user guesses
		numGuesses += command;
		if (numBodyParts < 6 && !winner()) {
			numGuesses += ",";
		}
		repaint();
	}

	// method generates the '_' on the guesses array so it's display to the user
	private void play() {

		// store random word
		phrase = getword();
		// convert random word to char array
		randPhrase = phrase.toCharArray();
		// create an array to hold and display user input
		guesses = new char[randPhrase.length];
		// populate the array with dashes first
		for (int i = 0; i < guesses.length; i++) {
			guesses[i] = '_';
		}

	}

	public void mouseClicked(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public void mousePressed(MouseEvent e) {
	}

	public void mouseReleased(MouseEvent e) {
	}

	public static void main(String[] args) {
		new HANGMAN();

	}
}