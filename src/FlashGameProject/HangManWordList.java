package FlashGameProject;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class HangManWordList extends JFrame implements ActionListener, MouseListener {

	HangManDAO hangmanDAO;
	HangManDTO hangmanDTO;

	JLabel jlWord, jlNum;
	JButton jbAdd, jbDel;
	JTextField jtWord;
	JTable table;
	Vector data, col;

	public HangManWordList() {
		setLayout(null);
		hangmanDAO = new HangManDAO();

		add(jlWord = new JLabel("Add Word", JLabel.CENTER));
		jlWord.setFont(new Font("DungGeunMo", Font.CENTER_BASELINE, 30));
		jlWord.setBackground(Color.BLACK);
		jlWord.setBorder(BorderFactory.createBevelBorder(0));
		jlWord.setBounds(10, 10, 180, 60);

		add(jtWord = new JTextField());
		jtWord.setFont(new Font("DungGeunMo", Font.BOLD, 30));
		jtWord.setForeground(Color.CYAN);
		jtWord.setHorizontalAlignment(JTextField.CENTER);
		jtWord.setBackground(Color.BLACK);
		jtWord.setBounds(200, 10, 280, 60);

		add(jbAdd = new JButton("Add"));
		jbAdd.setFont(new Font("DungGeunMo", Font.BOLD, 30));
		jbAdd.setForeground(Color.LIGHT_GRAY);
		jbAdd.setBounds(10, 80, 150, 50);
		jbAdd.setBackground(Color.BLACK);
		jbAdd.addActionListener(this);

		add(jbDel = new JButton("Delete"));
		jbDel.setFont(new Font("DungGeunMo", Font.BOLD, 30));
		jbDel.setForeground(Color.LIGHT_GRAY);
		jbDel.setBounds(170, 80, 150, 50);
		jbDel.setBackground(Color.BLACK);
		jbDel.addActionListener(this);

		add(jbDel = new JButton("Play"));
		jbDel.setFont(new Font("DungGeunMo", Font.BOLD, 30));
		jbDel.setForeground(Color.LIGHT_GRAY);
		jbDel.setBounds(330, 80, 150, 50);
		jbDel.setBackground(Color.BLACK);
		jbDel.addActionListener(this);

		DefaultTableModel model = new DefaultTableModel(hangmanDAO.getScore(), col) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		table = new JTable(model);
		table.addMouseListener(this);
		JScrollPane scroll = new JScrollPane(table);
		// jTableSet();
		add(scroll);
		scroll.setBounds(10, 140, 470, 400);

		col = new Vector();
		col.add("num");
		col.add("Word");

		jTableRefresh();

		setSize(500, 500);
		setTitle("행맨게임 단어 추가 프로그램");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		setResizable(false);
		setLocationRelativeTo(null);

	}

	public void jTableSet() {
		// 이동, 길이 조절, 여러개 선택되는 것 방지 모듈
		table.getTableHeader().setReorderingAllowed(false);
		table.getTableHeader().setResizingAllowed(false);
		table.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);

		//
		DefaultTableCellRenderer celAlignCenter = new DefaultTableCellRenderer();
		celAlignCenter.setHorizontalAlignment(JLabel.CENTER);

		DefaultTableCellRenderer celAlignRight = new DefaultTableCellRenderer();
		celAlignRight.setHorizontalAlignment(JLabel.RIGHT);

		DefaultTableCellRenderer celAlignLeft = new DefaultTableCellRenderer();
		celAlignLeft.setHorizontalAlignment(JLabel.LEFT);

		// 컬럼 사이즈 조절 및 정렬
		table.getColumnModel().getColumn(0).setPreferredWidth(10);
		table.getColumnModel().getColumn(0).setCellRenderer(celAlignCenter);

		table.getColumnModel().getColumn(1).setPreferredWidth(10);
		table.getColumnModel().getColumn(1).setCellRenderer(celAlignCenter);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		int rowIndex = table.getSelectedRow();
		jtWord.setText(table.getValueAt(rowIndex, 0) + "");
		jtWord.setText(table.getValueAt(rowIndex, 1) + "");

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
		if (ButtonFlag.equals("Add")) {
			try {
				contentSet();
				int result = hangmanDAO.insertScore(hangmanDTO);
				if (result == 1) {
					JOptionPane.showMessageDialog(this, "추가되었습니다.");
					jTableRefresh();
					contentClear();
				} else {
					JOptionPane.showMessageDialog(this, "실패하였습니다.");
				}
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(this, "값을 입력하세요.");
			}
		} else if (ButtonFlag.equals("Delete")) {
			try {
				contentSet();
				int result = hangmanDAO.deleteScore(hangmanDTO);
				if (result == 1) {
					JOptionPane.showMessageDialog(this, "삭제 되었습니다.");
					jTableRefresh();
					contentSet();
					contentClear();
				} else {
					JOptionPane.showMessageDialog(this, "실패 하였습니다.");
				}
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(this, "값을 입력하세요");
			}

		} else if (ButtonFlag.equals("Play")) {
			dispose();
			HANGMAN H = new HANGMAN();
		}
	}

	public void jTableRefresh() {
		// 테이블 내용을 갱신하는 메소드

		DefaultTableModel model = new DefaultTableModel(hangmanDAO.getScore(), col) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		table.setModel(model);
		jTableSet();
	}

	public void contentClear() {
		jtWord.setText("");

	}

	public void contentSet() {
		hangmanDTO = new HangManDTO();
		String word = jtWord.getText();
		hangmanDTO.setWord(word);
	}

	public static void main(String[] args) {
		// HangManDAO dao = new HangManDAO();
		// dao.getConn();
		new HangManWordList();
	}
}