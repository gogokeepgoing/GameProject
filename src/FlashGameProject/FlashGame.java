package FlashGameProject;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;

class GameJoin extends JFrame implements ActionListener, MouseListener {

	JoinDAO joinDAO;
	JoinDTO joinDTO;
	JLabel jlId, jlUserName, jlPassword, jlPasswordRe, jlEmail, jlPhone, jlGame, jlImg;
	JTextField jtId, jtUserName, jtPassword, jtPasswordRe, jtEmail, jtPhone;
	JButton jbJoin, jbDel, jbRefer;
	JTable table;
	Vector data, col;

	public GameJoin() {

		setLayout(null);
		joinDAO = new JoinDAO();

		// 로그인창 배너
		add(jlGame = new JLabel("회원 가입", JLabel.CENTER));
		jlGame.setForeground(Color.cyan);
		jlGame.setFont(new Font("DungGeunMo", Font.BOLD, 50));
		jlGame.setBounds(155, 100, 300, 60);

		// ID 입력창
		add(jlId = new JLabel("ID", JLabel.CENTER));
		jlId.setForeground(Color.cyan);
		jlId.setFont(new Font("DungGeunMo", Font.BOLD, 20));
		jlId.setBounds(105, 180, 50, 50);

		add(jtId = new JTextField());
		jtId.setFont(new Font("DungGeunMo", Font.BOLD, 18));
		jtId.setHorizontalAlignment(JTextField.CENTER);
		jtId.setBounds(150, 190, 350, 30);

		// UserName 입력창
		add(jlUserName = new JLabel("UserName", JLabel.CENTER));
		jlUserName.setForeground(Color.cyan);
		jlUserName.setFont(new Font("DungGeunMo", Font.BOLD, 20));
		jlUserName.setBounds(50, 220, 100, 50);

		add(jtUserName = new JTextField());
		jtUserName.setFont(new Font("DungGeunMo", Font.BOLD, 18));
		jtUserName.setHorizontalAlignment(JTextField.CENTER);
		jtUserName.setBounds(150, 233, 350, 30);

		// PW 입력창
		add(jlPassword = new JLabel("PW", JLabel.CENTER));
		jlPassword.setForeground(Color.cyan);
		jlPassword.setFont(new Font("DungGeunMo", Font.BOLD, 20));
		jlPassword.setBounds(100, 263, 50, 50);

		add(jtPassword = new JTextField());
		jtPassword.setFont(new Font("DungGeunMo", Font.BOLD, 18));
		jtPassword.setHorizontalAlignment(JTextField.CENTER);
		jtPassword.setBounds(150, 275, 350, 30);

		// PW 확인창
		add(jlPasswordRe = new JLabel("PW확인", JLabel.CENTER));
		jlPasswordRe.setForeground(Color.cyan);
		jlPasswordRe.setFont(new Font("DungGeunMo", Font.BOLD, 20));
		jlPasswordRe.setBounds(65, 270, 80, 120);

		add(jtPasswordRe = new JTextField());
		jtPasswordRe.setFont(new Font("DungGeunMo", Font.BOLD, 18));
		jtPasswordRe.setHorizontalAlignment(JTextField.CENTER);
		jtPasswordRe.setBounds(150, 318, 350, 30);

		// Email 입력창
		add(jlEmail = new JLabel("Email", JLabel.CENTER));
		jlEmail.setForeground(Color.cyan);
		jlEmail.setFont(new Font("DungGeunMo", Font.BOLD, 20));
		jlEmail.setBounds(75, 312, 80, 120);

		add(jtEmail = new JTextField());
		jtEmail.setFont(new Font("DungGeunMo", Font.BOLD, 18));
		jtEmail.setHorizontalAlignment(JTextField.CENTER);
		jtEmail.setBounds(150, 360, 350, 30);

		// Phone 입력창
		add(jlPhone = new JLabel("Phone", JLabel.CENTER));
		jlPhone.setForeground(Color.cyan);
		jlPhone.setFont(new Font("DungGeunMo", Font.BOLD, 20));
		jlPhone.setBounds(72, 360, 80, 120);

		add(jtPhone = new JTextField());
		jtPhone.setFont(new Font("DungGeunMo", Font.BOLD, 18));
		jtPhone.setHorizontalAlignment(JTextField.CENTER);
		jtPhone.setBounds(150, 405, 350, 30);

		setSize(620, 800);
		setTitle("Flash Game");
		getContentPane().setBackground(Color.BLACK);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		setResizable(false);
		setLocationRelativeTo(null);

		// 회원가입 버튼
		add(jbJoin = new JButton("가입"));
		jbJoin.setFont(new Font("DungGeunMo", Font.BOLD, 15));
		jbJoin.setBounds(170, 520, 120, 50);
		jbJoin.addActionListener(this);
		jbJoin.setBackground(Color.BLACK);
		jbJoin.setForeground(Color.cyan);
		jbJoin.setBorderPainted(false);

		add(jbDel = new JButton("취소"));
		jbDel.setFont(new Font("DungGeunMo", Font.BOLD, 15));
		jbDel.setBounds(350, 520, 120, 50);
		jbDel.addActionListener(this);
		jbDel.setBackground(Color.BLACK);
		jbDel.setForeground(Color.cyan);
		jbDel.setBorderPainted(false);

		add(jbRefer = new JButton("확인"));
		jbRefer.setFont(new Font("DungGeunMo", Font.BOLD, 10));
		jbRefer.setBounds(510, 318, 60, 30);
		jbRefer.addActionListener(this);
		jbRefer.setBackground(Color.BLACK);
		jbRefer.setForeground(Color.cyan);
		jbRefer.setBorderPainted(false);

		// 이미지
		add(jlImg = new JLabel());
		jlImg.setBounds(-100, 0, 800, 813);
		jlImg.setIcon(new ImageIcon("Image/login.gif"));

		col = new Vector();
		col.add("ID");
		col.add("UserName");
		col.add("PW");
		col.add("PW확인");
		col.add("Email");
		col.add("Phone");
		col.add("row");
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		int rowIndex = table.getSelectedRow();
		jtId.setText(table.getValueAt(rowIndex, 0) + "");
		jtUserName.setText(table.getValueAt(rowIndex, 1) + "");
		jtPassword.setText(table.getValueAt(rowIndex, 2) + "");
		jtPasswordRe.setText(table.getValueAt(rowIndex, 3) + "");
		jtEmail.setText(table.getValueAt(rowIndex, 4) + "");
		jtPhone.setText(table.getValueAt(rowIndex, 5) + "");

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
		String ButtonFlag = e.getActionCommand();

		// 확인 버튼을 누르면 equals를 통해서 문자열이 같은지 비교
		if (ButtonFlag.equals("확인")) {
			try {
				contentSet();
				if (joinDTO.getPassword().equals(joinDTO.getPasswordre()) == true) {
					JOptionPane.showMessageDialog(this, "비밀번호가 같습니다.");
					contentSet();
				} else {
					System.out.println(e);
					JOptionPane.showMessageDialog(this, "비밀번호가 같지 않습니다.");
				}

			} catch (Exception ex) {
				System.out.println(ex);
				JOptionPane.showMessageDialog(this, "비밀번호를 입력하세요.");
			}
			// 가입 버튼을 누르면 mySQL 쪽으로 정보저장
		} else if (ButtonFlag.equals("가입")) {
			try {
				contentSet();
				Boolean result = joinDAO.insertCustomer(joinDTO);
				if (result == true) {
					JOptionPane.showMessageDialog(this, "가입이 완료되었습니다.");
					contentSet();
					new LoginFrame();
					this.dispose();
				} else {
					JOptionPane.showMessageDialog(this, "가입중 오류가 발생하였습니다.");
				}
			} catch (Exception ex) {
				System.out.println(ex);
				JOptionPane.showMessageDialog(this, "정보를 입력하세요");
			}
			// 취소 버튼을 누르면 GameJoin 클래서 종료후 LoginFrame클래스 실행
		} else if (ButtonFlag.equals("취소")) {
			new LoginFrame();
			this.dispose();
		}
	}

	// mySQL 쪽으로 정보를 저장
	private void contentSet() {
		joinDTO = new JoinDTO();
		String id = jtId.getText();
		String username = jtUserName.getText();
		String password = jtPassword.getText();
		String passwordre = jtPasswordRe.getText();
		String email = jtEmail.getText();
		String phone = jtPhone.getText();

		joinDTO.setId(id);
		joinDTO.setUsername(username);
		joinDTO.setPassword(password);
		joinDTO.setPasswordre(passwordre);
		joinDTO.setEmail(email);
		joinDTO.setPhone(phone);

	}

}

public class FlashGame {
	public static void main(String[] args) {
		new GameJoin();
	}
}
