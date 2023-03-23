package FlashGameProject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

public class HangManDAO {

	private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
	private static final String URL = "jdbc:mysql://127.0.0.1:3306/db";
	private static final String USER = "root";
	private static final String PASS = "1234";

	public Connection getConn() {
		Connection con = null;
		try {
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASS);
			System.out.println("연결 성공");
		} catch (Exception e) {
			System.out.println("연결 실패");
		} finally {

		}
		return con;
	}

	public Vector getScore() {
		Vector data = new Vector();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = getConn();
			String sql = "select*from wordslist order by num asc";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				int num = rs.getInt("num");
				String word = rs.getString("word");
				Vector row = new Vector();
				row.add(num);
				row.add(word);
				data.add(row);

			}
		} catch (Exception e) {
			System.out.println(e);
			System.out.println("오류");
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();
			} catch (Exception e) {

			}
		}
		return data;
	}
	
	public int insertScore(HangManDTO dto) {
		Connection con = null;
		PreparedStatement pstmt = null;
		int result = 0;
		try {
			con = getConn();
			String sql = "insert into wordslist values(?,?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, dto.getNum());
			pstmt.setString(2, dto.getWord());
			result = pstmt.executeUpdate();

		} catch (Exception e) {
			System.out.println(e);
			System.out.println("삽입 오류");
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (con != null)
					con.close();

			} catch (Exception e) {

			}
		}
		return result;
	}

	public int deleteScore(HangManDTO dto) {
		Connection con = null;
		int result = 0;
		PreparedStatement pstmt = null;
		try {
			con = getConn();
			String sql = "delete from wordslist where word=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, dto.getWord());
			result = pstmt.executeUpdate();
		} catch (Exception ex) {

		} finally {

		}
		return result;
	}
}
