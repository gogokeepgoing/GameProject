package FlashGameProject;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
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

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

@SuppressWarnings("serial")

public class HangMan2 extends JFrame implements ActionListener, MouseListener {

// 랜덤으로 txt파일에서 단어 빼내기
	public static final String FILE = "dictionary.txt";

	// Jframe height, weight
	private static final int WIDTH = 800;
	private static final int HEIGHT = 600;

	private static final Icon FILE_START = null;

	private static final Icon FILE_STOP = null;

	private static final Icon SHOW_REPLAY = null;

	// changes the state of the file
	private int state = 0;
	// Random genator for word array
	public Random rGen = new Random();
	// Char array that houses the random phrases that was converted from the
	// word array
	private static char[] randPhrase;
	// word aray that houses the 20 words from the file
	private static String[] words;
	// Char array that houses the user guesses
	private static char[] guesses;
	// counts the number of hangman body parts
	public static int numBodyParts = 0;
	// holds the letters that the user guesses
	private static String numGuesses = "";
	// holds the random word that the word array generates to prevent multiple
	// calls
	public static String phrase;
	// all my panels - mainpanel holds left/right/bottom(keyboard)
	public static JPanel mainPanel, leftPanel, rightPanel, bottomPanel, belowPanel;
	JLabel label;
	private JProgressBar timerBar;
	private Thread timerThread;

	public HangMan2() {

		// pass title to super class
		setTitle("Hang Man");
		// set size of the jframe
		setSize(WIDTH, HEIGHT);
		// populate word array
		words = textFile();
		// close Jframe on exit
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// main panel houses three panels - left, right and bottom(keyboard).
		mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());

		// 배경 이미지 넣기
		ImageIcon background = new ImageIcon("Image/start_screen_bg.gif"); // 배경 이미지
		int width = background.getIconWidth();
		int height = background.getIconHeight();
		mainPanel.setPreferredSize(new Dimension(width, height));

		label = new JLabel(background);
		mainPanel.add(label, BorderLayout.CENTER);

		JPanel keyPanel = new JPanel(); // 키보드 패널
		keyPanel.setLayout(new GridLayout(3, 8));
		keyPanel.setBackground(Color.black);

		rightPanel = new JPanel();
		leftPanel = new JPanel();
		leftPanel.setBackground(Color.WHITE);
		rightPanel.setBackground(Color.WHITE);
		// add the left/right panel to the WEST and EAST border of mainPanel,
		// respectively.
		mainPanel.add(leftPanel, BorderLayout.WEST);
		mainPanel.add(rightPanel, BorderLayout.EAST);

		bottomPanel = new JPanel();
		bottomPanel.setLayout(new GridLayout(4, 4));
		bottomPanel.setBackground(Color.GRAY);
		// add the bottom panel which contains Jbuttons to the SOUTH border of
		// mainPanel.
		mainPanel.add(bottomPanel, BorderLayout.SOUTH);

		belowPanel = new JPanel();
		belowPanel.setBackground(Color.GREEN);
		// add last panel which houses replay/exit button
		add(mainPanel);
		add(belowPanel, BorderLayout.AFTER_LAST_LINE);

		// set visibility to false until game is over
		belowPanel.setVisible(false);

		// create keyboard buttons
		createButtons(bottomPanel);
		// create replay/exit buttons
		replayButtons(belowPanel);
		// add mouselistener
		addMouseListener(this);

		JPanel buttonPanel = new JPanel();

		JButton buttonStart = new JButton("게임 시작");
		buttonStart.setBounds(getX(), getY(), WIDTH, HEIGHT);
		buttonPanel.add(buttonStart);
		buttonStart.addActionListener(this);

		JButton buttonStop = new JButton("게임 그만");
		buttonStop.setBounds(getX(), getY(), WIDTH, HEIGHT);
		buttonPanel.add(buttonStop);
		buttonStop.addActionListener(this);

		JButton buttonReplay = new JButton("다시 시작");
		buttonReplay.setBounds(getX(), getY(), WIDTH, HEIGHT);
		buttonPanel.add(buttonReplay);
		buttonReplay.addActionListener(this);

		// add button panel to the NORTH border of mainPanel.
		mainPanel.add(buttonPanel, BorderLayout.NORTH);

		belowPanel.setVisible(false);
		bottomPanel.setVisible(false);
		setLocationRelativeTo(null);
		setResizable(false);
	}

	// method creates two jbutton for replay/exit and adds actionlisteners
	public void replayButtons(JPanel belowPanel) {
		JButton playAgain = new JButton(SHOW_REPLAY);
		playAgain.setSize(80, 80);
		playAgain.setAction((Action) SHOW_REPLAY);

		playAgain.addActionListener(this);
		JButton exit = new JButton(FILE_STOP);
		exit.setAction((Action) FILE_STOP);
		exit.addActionListener(this);
		exit.setSize(80, 80);
		belowPanel.add(playAgain);
		belowPanel.add(exit);
	}

	// method creates an array of jbuttons with actionlisteners to use as a
	// keyboard
	public void createButtons(JPanel bottomPanel) {

		JButton[] buttons = new JButton[26];
		String[] letters = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R",
				"S", "T", "U", "V", "W", "X", "Y", "Z" };

		for (int i = 0; i < buttons.length; i++) {
			buttons[i] = new JButton(letters[i]);
			buttons[i].setSize(40, 40);
			buttons[i].setActionCommand(letters[i]);
			buttons[i].addActionListener(this);

			bottomPanel.add(buttons[i]);
		}

	}

	public void paint(Graphics g) {
		super.paint(g);

		// set the font
		Font font = new Font("DungGeunMo", Font.BOLD | Font.ITALIC, 24);
		g.setFont(font);
		g.setColor(Color.white);

		// if user has selected play from menu - start game
		if (state == 1) {

			gameMessages(g);
			String result = "";
			for (int i = 0; i < guesses.length; i++) {

				result += guesses[i] + " ";

			}
			g.drawString(result, 420, 200);
			g.drawString("GUESSES", 390, 400);
			g.drawString(numGuesses, 390, 350);
			System.out.println(randPhrase);
			// if user misses a letter - display body parts

			hangman(g);

		}
	}

	private void hangman(Graphics g) {
		if (numBodyParts == 0) {
			Image img1 = new ImageIcon("Image/Gallows0.gif").getImage();
			g.drawImage(img1, 50, 70, 400, 400, this);
		}
		if (numBodyParts == 1) {
			Image img = new ImageIcon("Image/Gallows1.gif").getImage();
			g.drawImage(img, 50, 70, 400, 400, this);
		}

		if (numBodyParts == 2) {
			Image img = new ImageIcon("Image/Gallows2.gif").getImage();
			g.drawImage(img, 50, 70, 400, 400, this);
		}
		if (numBodyParts == 3) {
			Image img = new ImageIcon("Image/Gallows3.gif").getImage();
			g.drawImage(img, 50, 70, 400, 400, this);
		}
		if (numBodyParts == 4) {
			Image img = new ImageIcon("Image/Gallows4.gif").getImage();
			g.drawImage(img, 50, 70, 400, 400, this);
		}
		if (numBodyParts == 5) {
			Image img = new ImageIcon("Image/Gallows5.gif").getImage();
			g.drawImage(img, 50, 70, 400, 400, this);
		}
		if (numBodyParts == 6) {
			Image img = new ImageIcon("Image/Gallows6.gif").getImage();
			g.drawImage(img, 50, 70, 400, 400, this);

		}
		if (numBodyParts == 7) {
			numBodyParts = 0;
			numGuesses = "";
			bottomPanel.setVisible(true);
			state = 1;
			play();
			JOptionPane.showMessageDialog(null, "You Lost\nPlay Again");
			repaint();
		}

	}

	private void gameMessages(Graphics g) {

		if (!winner()) {
			// draw welcome message
			g.drawString("Let's Play Hang Man!!!", 500, 100);

			// draw winner message and enable belowpanel
		} else if (winner() && numBodyParts < 6) {
			System.out.println("i hit here");
			g.drawString("You Won!!", 50, 80);

			JOptionPane.showMessageDialog(null, "You Win");
			// belowPanel.setVisible(true);

			// draw lost message and enable belowpanel
		} else if (numBodyParts == 6) {

			g.drawString("You Lost!!", 25, 80);
			bottomPanel.setVisible(false);
			// belowPanel.setVisible(true);

		}

	}

	// generate a random word and return via char array
	public String getword() {
		words = textFile();

		int n = words.length;
		int r = rGen.nextInt(n);
		String word = words[r];

		return word;
	}

	// method determines whether guesses array match the randphrase array
	public boolean winner() {
		if (Arrays.equals(guesses, randPhrase)) {
			return true;
		} else {
			return false;
		}

	}

	// method reads from a file and writes to arraylist which is converted back
	// to an array
	public String[] textFile() {
		// create a bufferedReader
		BufferedReader reader = null;
		// create a list array and store the values from the text file
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
				// close the file
				reader.close();
			} catch (IOException e) {
				System.out.println(e.getMessage());
				System.exit(-1);
			}
		}
		// convert from arraylist to array and return
		return wordList.toArray(new String[wordList.size()]);

	}

	public void actionPerformed(ActionEvent e) {

		String command = e.getActionCommand();

		if (command.equals("게임 시작")) {

			bottomPanel.setVisible(true);
			ImageIcon newBackground = new ImageIcon("Image/chalkboard.gif");

			// Set new background image on a JLabel
			JLabel newBackgroundLabel = new JLabel(newBackground);

			// Remove old background JLabel and add new one
			mainPanel.remove(label);
			mainPanel.add(newBackgroundLabel, BorderLayout.CENTER);

			// Update GUI
			mainPanel.revalidate();
			mainPanel.repaint();

			// once the user has pressed play, change state and call to play
			// method

			numBodyParts = 0;
			numGuesses = "";
			bottomPanel.setVisible(true);
			state = 1;
			play();
			repaint();

		}

		else if (command.length() == 1 && state == 1) {
			// pass action event to letters if the user has pressed play and the
			// event is generated
			// by the Jbutton array (length of the string is one)
			letters(command);
		}
		// reset status and replay game
		else if (command.equals("다시 시작")) {

			numBodyParts = 0;
			numGuesses = "";
			bottomPanel.setVisible(true);
			state = 1;
			play();
			repaint();

		} else if (command.equals("게임 그만")) {
			state = 2;
			System.exit(-1);
		}

		// repaint();
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
		HangMan2 hangman = new HangMan2();
		hangman.setVisible(true);

	}
}