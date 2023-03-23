package FlashGameProject;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;

public class SelectGame extends JFrame implements MouseListener {

	ImageIcon Gicon = new ImageIcon("Image/galaga.png");
	ImageIcon Hicon = new ImageIcon("Image/Hangman6A.png");
	ImageIcon Bicon = new ImageIcon("Image/brick1.png");
	ImageIcon Aicon = new ImageIcon("Image/h1.png");

	JButton H = new JButton(Hicon);
	JButton G = new JButton(Gicon);
	JButton B = new JButton(Bicon);
	JButton A = new JButton(Aicon);

	public SelectGame() {
		setTitle("main");
		setSize(500, 250);
		setLayout(new GridLayout());

		add(H);
		H.setFont(new Font("DungGeunMo", Font.BOLD, 30));
		H.setForeground(Color.CYAN);
		H.setHorizontalAlignment(JTextField.CENTER);
		H.setBackground(Color.black);
		H.addMouseListener(this);

		add(A);
		A.setFont(new Font("DungGeunMo", Font.BOLD, 30));
		A.setForeground(Color.CYAN);
		A.setHorizontalAlignment(JTextField.CENTER);
		A.setBackground(Color.black);
		A.addMouseListener(this);

		add(G);
		G.setFont(new Font("DungGeunMo", Font.BOLD, 30));
		G.setForeground(Color.CYAN);
		G.setHorizontalAlignment(JTextField.CENTER);
		G.setBackground(Color.black);
		G.addMouseListener(this);

		add(B);
		B.setFont(new Font("DungGeunMo", Font.BOLD, 30));
		B.setForeground(Color.CYAN);
		B.setHorizontalAlignment(JTextField.CENTER);
		B.setBackground(Color.black);
		B.addMouseListener(this);

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		Object obj = e.getSource();

		if ((JButton) obj == H) {
			new HangManWordList();
			dispose();

		} else if ((JButton) obj == G) {
			new First();
			dispose();
			
		} else if ((JButton) obj == A) {
			HangMan2 hangman = new HangMan2();
			hangman.setVisible(true);
			dispose();
			
		} else if ((JButton) obj == B) {
			
			Brick1 a = new Brick1();
			dispose();
		}

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public static void main(String[] args) {
		new SelectGame();
	//	new Box();
	}
}
