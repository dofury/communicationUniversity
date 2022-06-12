package complaint;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import util.DatabaseUtil;

public class ComplaintDAO {
	
    public int write(ComplaintDTO complaintDTO) {
        String SQL = "INSERT INTO COMPLAINT VALUES (NULL, ?, ?, ?, ?, 0)";    //사용자로부터 입력받은 ID가 가지고 Password를 불러와서 처리하겠다는 것.
        Connection conn = null; 
        PreparedStatement pstmt = null; //특정한 SQL 문장을 수행 하도록 하는 class
        ResultSet rs = null; // 특정한 SQL 문장을 수행한 이후에 나온 결과값에 대해 처리하고자 할 때 사용하는 class
        try {          
            conn = DatabaseUtil.getConnection(); //Connection 객체 초기화 / DatabaseUtil.java 파일에서 리턴값에 반환된 객체들을 getConnection을 통해 연결받고 conn 객체에 담는다
            pstmt = conn.prepareStatement(SQL); //conn 객체에서 prepareStatement를 실행하도록 준비
            pstmt.setString(1, complaintDTO.getUserID().replace("<", "&lt;").replaceAll(">", "&gt").replaceAll("\r\n", "<br>"));  //위에 ?에 값을 넣어주는 것.
            pstmt.setString(2, complaintDTO.getComplaintName().replace("<", "&lt;").replaceAll(">", "&gt").replaceAll("\r\n", "<br>"));  //위에 ?에 값을 넣어주는 것.
            pstmt.setString(3, complaintDTO.getComplaintDivide().replace("<", "&lt;").replaceAll(">", "&gt").replaceAll("\r\n", "<br>"));  //위에 ?에 값을 넣어주는 것.
            pstmt.setString(4, complaintDTO.getComplaintContent().replace("<", "&lt;").replaceAll(">", "&gt").replaceAll("\r\n", "<br>"));  //위에 ?에 값을 넣어주는 것.
            return pstmt.executeUpdate(); // 값 반환
        } catch (Exception e) {
             e.printStackTrace();
        } finally { // 위 4 객체는 한번 사용하면 자원을 해체해주는 게 중요함
          try {if(conn != null) conn.close();} catch (Exception e) {e.printStackTrace();}
          try {if(pstmt != null) pstmt.close();} catch (Exception e) {e.printStackTrace();}
          try {if(rs != null) rs.close();} catch (Exception e) {e.printStackTrace();}
       }
        return -1; //데이터 베이스 오류
       }
    
   public ArrayList<ComplaintDTO> getList (String complaintDivide, String searchType,String search, int pageNumber) {
	   if(complaintDivide.equals("전체")) {
		   complaintDivide ="";
	   }
	   ArrayList<ComplaintDTO> complaintList = null;
       String SQL = "";   
       Connection conn = null; 
       PreparedStatement pstmt = null; //특정한 SQL 문장을 수행 하도록 하는 class
       ResultSet rs = null; // 특정한 SQL 문장을 수행한 이후에 나온 결과값에 대해 처리하고자 할 때 사용하는 class
       try {    
    	   if(searchType.equals("최신순")) {
				 SQL = "SELECT * FROM COMPLAINT WHERE complaintDivide Like ? AND CONCAT(complaintName, complaintContent) LIKE " +
			            "? ORDER BY complaintID DESC LIMIT " + pageNumber * 5 + ", " + pageNumber * 5 + 6;       
			  } else if (searchType.equals("추천순")) {
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
       } finally { // 위 4 객체는 한번 사용하면 자원을 해체해주는 게 중요함
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
       return -1; // 데이터베이스 오류
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
       return -1; // 데이터베이스 오류
   }
   public String getUserID(String complaintID) {
       String SQL = "SELECT userID FROM COMPLAINT WHERE complaintID = ?";    
       Connection conn = null; 
       PreparedStatement pstmt = null; //특정한 SQL 문장을 수행 하도록 하는 class
       ResultSet rs = null; // 특정한 SQL 문장을 수행한 이후에 나온 결과값에 대해 처리하고자 할 때 사용하는 class
       try {          
           conn = DatabaseUtil.getConnection(); //Connection 객체 초기화 / DatabaseUtil.java 파일에서 리턴값에 반환된 객체들을 getConnection을 통해 연결받고 conn 객체에 담는다
           pstmt = conn.prepareStatement(SQL); //conn 객체에서 prepareStatement를 실행하도록 준비
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
       return null; // 존재하지 않음
   }
}
