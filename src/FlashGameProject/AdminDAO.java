package FlashGameProject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

public class AdminDAO {
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

	public Vector getAdmin() {
		Vector data = new Vector();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = getConn();
			String sql = "select*from member order by id asc";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				String id = rs.getString("id");
				String username = rs.getString("username");
				String password = rs.getString("password");
				String passwordre = rs.getString("passwordre");
				String email = rs.getString("email");
				String phone = rs.getString("phone");
				Vector row = new Vector();
				
				row.add(id);
				row.add(username);
				row.add(password);
				row.add(passwordre);
				row.add(email);
				row.add(phone);
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
	
	public int deleteScore(AdminDTO dto) {
		Connection con = null;
		int result = 0;
		PreparedStatement pstmt = null;
		try {
			con = getConn();
			String sql = "delete from member where id=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, dto.getId());
			result = pstmt.executeUpdate();
		} catch (Exception ex) {

		}
		return result;
	}
	
}
