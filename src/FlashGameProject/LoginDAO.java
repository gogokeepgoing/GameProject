package FlashGameProject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

   public class LoginDAO {
   private static final String DRIVER="com.mysql.cj.jdbc.Driver";
   private static final String URL = "jdbc:mysql://127.0.0.1:3306/db";
   private static final String USER = "root";
   private static final String PASS = "1234";
   private Connection conn;
   
   public static Connection getConn() {
      Connection con = null;
      try {
         Class.forName(DRIVER);
         con = DriverManager.getConnection(URL,USER,PASS);
         System.out.println("연결 성공");
      }catch(Exception e) {
         System.out.println("연결 실패"+e.getMessage());
      }
      return con;
   }


   public static int insertLogin(LoginDTO dto) {
      Connection con = null;
      PreparedStatement pstmt = null;
      ResultSet rs = null;
      System.out.println(dto.getId());
      int result = 0;
      try {
         
         con = getConn();
         String sql = "select*from member where id = ? and password =?";
         pstmt = con.prepareStatement(sql);
         pstmt.setString(1, dto.getId());
         pstmt.setString(2, dto.getPassword());
         rs = pstmt.executeQuery();
         if(rs.next()) {
            result =1;
         
         System.out.println("로그인 성공!");
         }else {
            
               System.out.println("로그인 실패!");
         }}
         catch(Exception e) {
            e.printStackTrace();
         }finally {
            try {
               if(pstmt != null)
                  pstmt.close();
               if(con != null)
                  con.close();
            }catch(Exception e) {
               
            }
            
         }return result;
      }
      
      
      
   }
      
      
   
    
   
   