package FlashGameProject;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Arrays;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class Admin extends JFrame implements ActionListener, MouseListener {
	AdminDAO adminDAO;
	AdminDTO adminDTO;

	JLabel id, username, password, passwordre, email, phone;
	JButton jbDel, jbMain;
	JTable table;
	Vector data, col;

	public Admin() {

		add(jbDel = new JButton("삭제"));
		jbDel.setFont(new Font("DungGeunMo", Font.BOLD, 30));
		jbDel.setForeground(Color.cyan);
		jbDel.setBounds(150, 440, 250, 50);
		jbDel.setBackground(Color.BLACK);
		jbDel.addActionListener(this);
		jbDel.setBorderPainted(false);

		add(jbMain = new JButton("메인 화면으로"));
		jbMain.setFont(new Font("DungGeunMo", Font.BOLD, 30));
		jbMain.setForeground(Color.cyan);
		jbMain.setBounds(150, 500, 250, 50);
		jbMain.setBackground(Color.black);
		jbMain.addActionListener(this);
		jbMain.setBorderPainted(false);

		table = new JTable();
		table.setBackground(Color.WHITE);
		table.addMouseListener(this);
		JScrollPane scroll = new JScrollPane(table);
		add(scroll);
		scroll.setBounds(10, 140, 470, 400);

		adminDAO = new AdminDAO();
		Vector data = adminDAO.getAdmin();
		Vector col = new Vector();

		col.add("ID");
		col.add("USERNAME");
		col.add("PASSWORD");
		col.add("PASSWORDRE");
		col.add("EMAIL");
		col.add("PHONE");
		DefaultTableModel model = new DefaultTableModel(data, col);
		table.setModel(model);

		setSize(550, 600);
		setTitle("Admin");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
		setResizable(false);

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

		if (ButtonFlag.equals("메인 화면으로")) {
			new LoginFrame();
			dispose();

		}

		else if (ButtonFlag.equals("삭제")) {
			try {
				contentSet();
				int result = adminDAO.deleteScore(adminDTO);
				if (result == 1) {
					JOptionPane.showMessageDialog(this, "삭제 되었습니다.");
					Vector data = adminDAO.getAdmin();
					DefaultTableModel model = (DefaultTableModel) table.getModel();
					refreshTable();

				} else {
					JOptionPane.showMessageDialog(this, "실패 하였습니다.");
				}
			} catch (Exception ex) {
				System.out.println(ex);
				JOptionPane.showMessageDialog(this, "오류 발생");
			}

		}
	}

	public void contentSet() {
		adminDTO = new AdminDTO();
		int selectedRow = table.getSelectedRow();
		String id = (String) table.getValueAt(selectedRow, 0);
		String username = (String) table.getValueAt(selectedRow, 1);
		String password = (String) table.getValueAt(selectedRow, 2);
		String passwordre = (String) table.getValueAt(selectedRow, 3);
		String email = (String) table.getValueAt(selectedRow, 4);
		String phone = (String) table.getValueAt(selectedRow, 5);
		adminDTO.setId(id);
		adminDTO.setUsername(username);
		adminDTO.setPassword(password);
		adminDTO.setPasswordre(passwordre);
		adminDTO.setEmail(email);
		adminDTO.setPhone(phone);

	}

	public void refreshTable() {
		Vector data = adminDAO.getAdmin();
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		model.setDataVector(data, new Vector<>(Arrays.asList("아이디", "이름", "비밀번호", "pw확인", "이메일", "휴대폰")));

	}

	public static void main(String args[]) {
		new Admin();
	}
}
