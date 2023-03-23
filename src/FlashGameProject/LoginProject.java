package FlashGameProject;

import java.sql.*;
import java.util.Vector;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

class LoginFrame extends JFrame implements ActionListener, MouseListener {

	LoginDAO loginDAO;
	LoginDTO loginDTO;
	JLabel jlImg, jlHeader, jlId, jlPassword;
	JTextField jtId;
	JPasswordField jtPassword;
	JButton jbLogin, jbSign, jbAdmin;
	JTable table;
	Vector data, col;

	public LoginFrame() {
		setLayout(null);
		loginDAO = new LoginDAO();
//      loginDTO = new LoginDTO();

		add(jlHeader = new JLabel("** G A M E **", JLabel.CENTER));
		jlHeader.setFont(new Font("둥근모꼴", Font.BOLD, 40));
		jlHeader.setBounds(120, 400, 300, 30);
		jlHeader.setForeground(Color.white);

		// 아이디
		add(jlId = new JLabel("ID : ", JLabel.CENTER));
		jlId.setFont(new Font("둥근모꼴", Font.BOLD, 20));
		jlId.setForeground(Color.cyan);
		jlId.setBounds(5, 500, 300, 30);

		// 텍스트필드
		add(jtId = new JTextField());
		jtId.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		jtId.setHorizontalAlignment(10);
		jtId.setBounds(200, 490, 300, 50);

		add(jlPassword = new JLabel("PASSWORD :", JLabel.CENTER));
		jlPassword.setFont(new Font("둥근모꼴", Font.BOLD, 20));
		jlPassword.setForeground(Color.cyan);
		jlPassword.setBounds(30, 580, 180, 30);

		add(jtPassword = new JPasswordField());
		jtPassword.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		jtPassword.setBounds(200, 570, 300, 50);

		add(jbLogin = new JButton("로그인"));
		jbLogin.setFont(new Font("둥근모꼴", Font.BOLD, 13));
		jbLogin.setBounds(230, 650, 100, 30);
		jbLogin.addActionListener(this);
		jbLogin.setForeground(Color.cyan);
		jbLogin.setBackground(Color.black);

		add(jbSign = new JButton("회원가입"));
		jbSign.setFont(new Font("둥근모꼴", Font.BOLD, 13));
		jbSign.setBounds(350, 650, 100, 30);
		jbSign.addActionListener(this);
		jbSign.setForeground(Color.cyan);
		jbSign.setBackground(Color.black);

		add(jbAdmin = new JButton("관리자 모드"));
		jbAdmin.setFont(new Font("둥근모꼴", Font.BOLD, 13));
		jbAdmin.setBounds(230, 700, 220, 30);
		jbAdmin.addActionListener(this);
		jbAdmin.setForeground(Color.cyan);
		jbAdmin.setBackground(Color.black);

		add(jlImg = new JLabel());
		jlImg.setBounds(-100, 0, 800, 813);
		jlImg.setIcon(new ImageIcon("Image/login.gif"));

		// 버튼 클릭시 다음 페이지 넘어가는 것 설정!
		jbSign.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// 회원가입 버튼을 누르면 GameJoin 클래스가 실행되고 로그인창이 꺼지게 설정
				new GameJoin();
				dispose();
			}
		});// 회원가입창 완성해서 Main클래스에 붙이기! 아니면 클래스 이름 바꾸면되지않을까?

		col = new Vector();
		col.add("id");
		col.add("password");

		setSize(600, 800);
		getContentPane().setBackground(Color.BLACK);
		setTitle("GameProject");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(true);

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

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

	@Override
	public void actionPerformed(ActionEvent e) {

		// TODO Auto-generated method stub
		String ButtonFlag = e.getActionCommand();
		if (ButtonFlag.equals("관리자 모드")) {

			Admin a = new Admin();
			dispose();
			
		} else if (ButtonFlag.equals("로그인")) {
			try {
				LoginDTO loginDTO = new LoginDTO();
				loginDTO.setId(jtId.getText());
				loginDTO.setPassword(jtPassword.getText());
				int result = LoginDAO.insertLogin(loginDTO);
				if (result == 1) {
					JOptionPane.showMessageDialog(this, "!! 로그인 !!");
					dispose();
					new SelectGame();

//               JPanel jPanel = new JPanel();
//                 jPanel.setBackground(Color.BLACK);
//                 setSize(600, 800);
//                 setVisible(true);
//                 add(jPanel);

				}

				else {
					JOptionPane.showMessageDialog(this, "!! 로그인 실패 !!");

				}
			} catch (Exception ex) {
				ex.printStackTrace();

			}
		}

	}
}

public class LoginProject {
	public static void main(String[] args) {
		new LoginFrame();
	}
}