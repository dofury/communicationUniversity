package likey;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import util.DatabaseUtil;

public class LikeyDAO {
	
	public int like(String userID, String complaintID, String userIP) {
        String SQL = "INSERT INTO LIKEY VALUES (?, ?, ?)";    
        Connection conn = null; 
        PreparedStatement pstmt = null; //Ư���� SQL ������ ���� �ϵ��� �ϴ� class
        ResultSet rs = null; // Ư���� SQL ������ ������ ���Ŀ� ���� ������� ���� ó���ϰ��� �� �� ����ϴ� class
        try {          
            conn = DatabaseUtil.getConnection(); //Connection ��ü �ʱ�ȭ / DatabaseUtil.java ���Ͽ��� ���ϰ��� ��ȯ�� ��ü���� getConnection�� ���� ����ް� conn ��ü�� ��´�
            pstmt = conn.prepareStatement(SQL); //conn ��ü���� prepareStatement�� �����ϵ��� �غ�
            pstmt.setString(1, userID);
            pstmt.setString(2, complaintID);
            pstmt.setString(3, userIP);                  
            return pstmt.executeUpdate(); //executeQuery�� �����͸� �˻��� �� ��� insert �� delete ���� update�� executeUpdate���
        } catch (Exception e) {
             e.printStackTrace();
        } finally { 
          try {if(conn != null) conn.close();} catch (Exception e) {e.printStackTrace();}
          try {if(pstmt != null) pstmt.close();} catch (Exception e) {e.printStackTrace();}
          try {if(rs != null) rs.close();} catch (Exception e) {e.printStackTrace();}
       }
        return -1; //��õ �ߺ� ����
	}
}
