package likey;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import util.DatabaseUtil;

public class LikeyDAO {
	
	public int like(String userID, String complaintID, String userIP) {
        String SQL = "INSERT INTO LIKEY VALUES (?, ?, ?)";    
        Connection conn = null; 
        PreparedStatement pstmt = null; //특정한 SQL 문장을 수행 하도록 하는 class
        ResultSet rs = null; // 특정한 SQL 문장을 수행한 이후에 나온 결과값에 대해 처리하고자 할 때 사용하는 class
        try {          
            conn = DatabaseUtil.getConnection(); //Connection 객체 초기화 / DatabaseUtil.java 파일에서 리턴값에 반환된 객체들을 getConnection을 통해 연결받고 conn 객체에 담는다
            pstmt = conn.prepareStatement(SQL); //conn 객체에서 prepareStatement를 실행하도록 준비
            pstmt.setString(1, userID);
            pstmt.setString(2, complaintID);
            pstmt.setString(3, userIP);                  
            return pstmt.executeUpdate(); //executeQuery는 데이터를 검색할 때 사용 insert 나 delete 같은 update는 executeUpdate사용
        } catch (Exception e) {
             e.printStackTrace();
        } finally { 
          try {if(conn != null) conn.close();} catch (Exception e) {e.printStackTrace();}
          try {if(pstmt != null) pstmt.close();} catch (Exception e) {e.printStackTrace();}
          try {if(rs != null) rs.close();} catch (Exception e) {e.printStackTrace();}
       }
        return -1; //추천 중복 오류
	}
}
