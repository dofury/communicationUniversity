package user;
 
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
 
import util.DatabaseUtil;
 
public class UserDAO {
 
    public int login(String userID, String userPassword) {
      String SQL = "SELECT userPassword FROM USER WHERE userID = ?";    //사용자로부터 입력받은 ID가 가지고 Password를 불러와서 처리하겠다는 것.
      Connection conn = null; 
      PreparedStatement pstmt = null; //특정한 SQL 문장을 수행 하도록 하는 class
      ResultSet rs = null; // 특정한 SQL 문장을 수행한 이후에 나온 결과값에 대해 처리하고자 할 때 사용하는 class
      try {          
          conn = DatabaseUtil.getConnection(); //Connection 객체 초기화 / DatabaseUtil.java 파일에서 리턴값에 반환된 객체들을 getConnection을 통해 연결받고 conn 객체에 담는다
          pstmt = conn.prepareStatement(SQL); //conn 객체에서 prepareStatement를 실행하도록 준비
          pstmt.setString(1, userID);  //위에 ?에 값을 넣어주는 것.
          rs = pstmt.executeQuery(); //실제로 SQL 문장을 데이터베이스에서 실행시킨 후 그 값을 rs에 담아준다.
          if(rs.next()) { // SQL 문장을 실행한 결과가 존재하는 경우에 (즉 아이디가 존재하면)
              if(rs.getString(1).equals(userPassword)) { //실제 데이터와 사용자가 입력한 데이터가 일치하는 경우 1을 반환
                  return 1; // 로그인 성공
              }
              else {
                  return 0; // 비밀번호 틀림
              }
          }
          return -1; // 아이디가 일치하지 않으면 '아이디 없음'
      } catch (Exception e) {
           e.printStackTrace();
      } finally { // 위 4 객체는 한번 사용하면 자원을 해체해주는 게 중요함
        try {if(conn != null) conn.close();} catch (Exception e) {e.printStackTrace();}
        try {if(pstmt != null) pstmt.close();} catch (Exception e) {e.printStackTrace();}
        try {if(rs != null) rs.close();} catch (Exception e) {e.printStackTrace();}
     }
      return -2; //데이터 베이스 오류
     }
        
        public int join(UserDTO user) {
              String SQL = "INSERT INTO USER VALUES (?, ?, ?, ?, false)";    
              Connection conn = null; 
              PreparedStatement pstmt = null; //특정한 SQL 문장을 수행 하도록 하는 class
              ResultSet rs = null; // 특정한 SQL 문장을 수행한 이후에 나온 결과값에 대해 처리하고자 할 때 사용하는 class
              try {          
                  conn = DatabaseUtil.getConnection(); //Connection 객체 초기화 / DatabaseUtil.java 파일에서 리턴값에 반환된 객체들을 getConnection을 통해 연결받고 conn 객체에 담는다
                  pstmt = conn.prepareStatement(SQL); //conn 객체에서 prepareStatement를 실행하도록 준비
                  pstmt.setString(1, user.getUserID());
                  pstmt.setString(2, user.getUserPassword());
                  pstmt.setString(3, user.getUserEmail());
                  pstmt.setString(4, user.getUserEmailHash());//위에 ?에 값을 넣어주는 것.                  
                  return pstmt.executeUpdate(); //executeQuery는 데이터를 검색할 때 사용 insert 나 delete 같은 update는 executeUpdate사용
              } catch (Exception e) {
                   e.printStackTrace();
              } finally { 
                try {if(conn != null) conn.close();} catch (Exception e) {e.printStackTrace();}
                try {if(pstmt != null) pstmt.close();} catch (Exception e) {e.printStackTrace();}
                try {if(rs != null) rs.close();} catch (Exception e) {e.printStackTrace();}
             }
              return -1; //회원가입 실패
             }
        
        public String getUserEmail(String userID) {
            String SQL = "SELECT userEmail FROM USER WHERE userID = ?";    
              Connection conn = null; 
              PreparedStatement pstmt = null; //특정한 SQL 문장을 수행 하도록 하는 class
              ResultSet rs = null; // 특정한 SQL 문장을 수행한 이후에 나온 결과값에 대해 처리하고자 할 때 사용하는 class
              try {          
                  conn = DatabaseUtil.getConnection(); //Connection 객체 초기화 / DatabaseUtil.java 파일에서 리턴값에 반환된 객체들을 getConnection을 통해 연결받고 conn 객체에 담는다
                  pstmt = conn.prepareStatement(SQL); //conn 객체에서 prepareStatement를 실행하도록 준비
                  pstmt.setString(1, userID);
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
              return null; // 데이터베이스 오류
        }
        
        public boolean getUserEmailChecked(String userID) { //사용자 이메일 검증, 검증 없이 사용하지 못하게 할것이기 때문에 이 함수가 필요
              String SQL = "SELECT userEmailChecked FROM USER WHERE userID = ?";    
              Connection conn = null; 
              PreparedStatement pstmt = null; //특정한 SQL 문장을 수행 하도록 하는 class
              ResultSet rs = null; // 특정한 SQL 문장을 수행한 이후에 나온 결과값에 대해 처리하고자 할 때 사용하는 class
              try {          
                  conn = DatabaseUtil.getConnection(); //Connection 객체 초기화 / DatabaseUtil.java 파일에서 리턴값에 반환된 객체들을 getConnection을 통해 연결받고 conn 객체에 담는다
                  pstmt = conn.prepareStatement(SQL); //conn 객체에서 prepareStatement를 실행하도록 준비
                  pstmt.setString(1, userID);
                  rs = pstmt.executeQuery();
                  if(rs.next()) {
                      return rs.getBoolean(1);
                  }
              } catch (Exception e) {
                   e.printStackTrace();
              } finally { 
                try {if(conn != null) conn.close();} catch (Exception e) {e.printStackTrace();}
                try {if(pstmt != null) pstmt.close();} catch (Exception e) {e.printStackTrace();}
                try {if(rs != null) rs.close();} catch (Exception e) {e.printStackTrace();}
             }
              return false; // 데이터베이스 오류
             }
        
        public boolean setUserEmailChecked(String userID) { //이메일 인증을 완료해주는 함수
              String SQL = "UPDATE USER SET userEmailChecked = true WHERE userID = ?";    
              Connection conn = null; 
              PreparedStatement pstmt = null;
              ResultSet rs = null; 
              try {          
                  conn = DatabaseUtil.getConnection(); 
                  pstmt = conn.prepareStatement(SQL); 
                  pstmt.setString(1, userID);
                  pstmt.executeUpdate();
                  return true;
              } catch (Exception e) {
                   e.printStackTrace();
              } finally { 
                try {if(conn != null) conn.close();} catch (Exception e) {e.printStackTrace();}
                try {if(pstmt != null) pstmt.close();} catch (Exception e) {e.printStackTrace();}
                try {if(rs != null) rs.close();} catch (Exception e) {e.printStackTrace();}
             }
              return false; // 데이터베이스 오류
             }          
         }
        