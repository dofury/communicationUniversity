package complaint;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import util.DatabaseUtil;

public class ComplaintDAO {
	
    public int write(ComplaintDTO complaintDTO) {
        String SQL = "INSERT INTO COMPLAINT VALUES (NULL, ?, ?, ?, ?, 0)";    //����ڷκ��� �Է¹��� ID�� ������ Password�� �ҷ��ͼ� ó���ϰڴٴ� ��.
        Connection conn = null; 
        PreparedStatement pstmt = null; //Ư���� SQL ������ ���� �ϵ��� �ϴ� class
        ResultSet rs = null; // Ư���� SQL ������ ������ ���Ŀ� ���� ������� ���� ó���ϰ��� �� �� ����ϴ� class
        try {          
            conn = DatabaseUtil.getConnection(); //Connection ��ü �ʱ�ȭ / DatabaseUtil.java ���Ͽ��� ���ϰ��� ��ȯ�� ��ü���� getConnection�� ���� ����ް� conn ��ü�� ��´�
            pstmt = conn.prepareStatement(SQL); //conn ��ü���� prepareStatement�� �����ϵ��� �غ�
            pstmt.setString(1, complaintDTO.getUserID().replace("<", "&lt;").replaceAll(">", "&gt").replaceAll("\r\n", "<br>"));  //���� ?�� ���� �־��ִ� ��.
            pstmt.setString(2, complaintDTO.getComplaintName().replace("<", "&lt;").replaceAll(">", "&gt").replaceAll("\r\n", "<br>"));  //���� ?�� ���� �־��ִ� ��.
            pstmt.setString(3, complaintDTO.getComplaintDivide().replace("<", "&lt;").replaceAll(">", "&gt").replaceAll("\r\n", "<br>"));  //���� ?�� ���� �־��ִ� ��.
            pstmt.setString(4, complaintDTO.getComplaintContent().replace("<", "&lt;").replaceAll(">", "&gt").replaceAll("\r\n", "<br>"));  //���� ?�� ���� �־��ִ� ��.
            return pstmt.executeUpdate(); // �� ��ȯ
        } catch (Exception e) {
             e.printStackTrace();
        } finally { // �� 4 ��ü�� �ѹ� ����ϸ� �ڿ��� ��ü���ִ� �� �߿���
          try {if(conn != null) conn.close();} catch (Exception e) {e.printStackTrace();}
          try {if(pstmt != null) pstmt.close();} catch (Exception e) {e.printStackTrace();}
          try {if(rs != null) rs.close();} catch (Exception e) {e.printStackTrace();}
       }
        return -1; //������ ���̽� ����
       }
    
   public ArrayList<ComplaintDTO> getList (String complaintDivide, String searchType,String search, int pageNumber) {
	   if(complaintDivide.equals("��ü")) {
		   complaintDivide ="";
	   }
	   ArrayList<ComplaintDTO> complaintList = null;
       String SQL = "";   
       Connection conn = null; 
       PreparedStatement pstmt = null; //Ư���� SQL ������ ���� �ϵ��� �ϴ� class
       ResultSet rs = null; // Ư���� SQL ������ ������ ���Ŀ� ���� ������� ���� ó���ϰ��� �� �� ����ϴ� class
       try {    
    	   if(searchType.equals("�ֽż�")) {
				 SQL = "SELECT * FROM COMPLAINT WHERE complaintDivide Like ? AND CONCAT(complaintName, complaintContent) LIKE " +
			            "? ORDER BY complaintID DESC LIMIT " + pageNumber * 5 + ", " + pageNumber * 5 + 6;       
			  } else if (searchType.equals("��õ��")) {
				  SQL = "SELECT * FROM COMPLAINT WHERE complaintDivide Like ? AND CONCAT(complaintName, complaintContent) LIKE " +
			            "? ORDER BY likeCount DESC LIMIT " + pageNumber * 5 + ", " + pageNumber * 5 + 6;
    	   }
    	   conn = DatabaseUtil.getConnection(); 
		   pstmt = conn.prepareStatement(SQL); 
		   pstmt.setString(1, "%" + complaintDivide + "%");
		   pstmt.setString(2, "%" + search + "%");
		   rs = pstmt.executeQuery();
		   complaintList = new ArrayList<ComplaintDTO>();
		   while(rs.next()) {
			   ComplaintDTO complaint = new ComplaintDTO(
				   rs.getInt(1),
				   rs.getString(2),
				   rs.getString(3),
				   rs.getString(4),
				   rs.getString(5),
				   rs.getInt(6)
				 );
			     complaintList.add(complaint);
           }
       } catch (Exception e) {
            e.printStackTrace();
       } finally { // �� 4 ��ü�� �ѹ� ����ϸ� �ڿ��� ��ü���ִ� �� �߿���
         try {if(conn != null) conn.close();} catch (Exception e) {e.printStackTrace();}
         try {if(pstmt != null) pstmt.close();} catch (Exception e) {e.printStackTrace();}
         try {if(rs != null) rs.close();} catch (Exception e) {e.printStackTrace();}
      }
       return complaintList;
	   
   }
   
   public int like(String complaintID) {
       String SQL = "UPDATE COMPLAINT SET likeCount = likeCount +1 WHERE complaintID = ?";    
       Connection conn = null; 
       PreparedStatement pstmt = null;
       ResultSet rs = null; 
       try {          
           conn = DatabaseUtil.getConnection(); 
           pstmt = conn.prepareStatement(SQL); 
           pstmt.setInt(1, Integer.parseInt(complaintID));
           return pstmt.executeUpdate();
       } catch (Exception e) {
            e.printStackTrace();
       } finally { 
         try {if(conn != null) conn.close();} catch (Exception e) {e.printStackTrace();}
         try {if(pstmt != null) pstmt.close();} catch (Exception e) {e.printStackTrace();}
         try {if(rs != null) rs.close();} catch (Exception e) {e.printStackTrace();}
      }
       return -1; // �����ͺ��̽� ����
   }
   public int delete(String complaintID) {
       String SQL = "DELETE FROM COMPLAINT WHERE complaintID = ?";    
       Connection conn = null; 
       PreparedStatement pstmt = null;
       ResultSet rs = null; 
       try {          
           conn = DatabaseUtil.getConnection(); 
           pstmt = conn.prepareStatement(SQL); 
           pstmt.setInt(1, Integer.parseInt(complaintID));
           return pstmt.executeUpdate();
       } catch (Exception e) {
            e.printStackTrace();
       } finally { 
         try {if(conn != null) conn.close();} catch (Exception e) {e.printStackTrace();}
         try {if(pstmt != null) pstmt.close();} catch (Exception e) {e.printStackTrace();}
         try {if(rs != null) rs.close();} catch (Exception e) {e.printStackTrace();}
      }
       return -1; // �����ͺ��̽� ����
   }
   public String getUserID(String complaintID) {
       String SQL = "SELECT userID FROM COMPLAINT WHERE complaintID = ?";    
       Connection conn = null; 
       PreparedStatement pstmt = null; //Ư���� SQL ������ ���� �ϵ��� �ϴ� class
       ResultSet rs = null; // Ư���� SQL ������ ������ ���Ŀ� ���� ������� ���� ó���ϰ��� �� �� ����ϴ� class
       try {          
           conn = DatabaseUtil.getConnection(); //Connection ��ü �ʱ�ȭ / DatabaseUtil.java ���Ͽ��� ���ϰ��� ��ȯ�� ��ü���� getConnection�� ���� ����ް� conn ��ü�� ��´�
           pstmt = conn.prepareStatement(SQL); //conn ��ü���� prepareStatement�� �����ϵ��� �غ�
           pstmt.setInt(1, Integer.parseInt(complaintID));
           rs = pstmt.executeQuery();
           if(rs.next()) {
               return rs.getString(1);
           }
       } catch (Exception e) {
            e.printStackTrace();
       } finally { 
         try {if(conn != null) conn.close();} catch (Exception e) {e.printStackTrace();}
         try {if(pstmt != null) pstmt.close();} catch (Exception e) {e.printStackTrace();}
         try {if(rs != null) rs.close();} catch (Exception e) {e.printStackTrace();}
      }
       return null; // �������� ����
   }
}
