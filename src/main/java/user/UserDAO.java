package user;
 
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
 
import util.DatabaseUtil;
 
public class UserDAO {
 
    public int login(String userID, String userPassword) {
      String SQL = "SELECT userPassword FROM USER WHERE userID = ?";    //����ڷκ��� �Է¹��� ID�� ������ Password�� �ҷ��ͼ� ó���ϰڴٴ� ��.
      Connection conn = null; 
      PreparedStatement pstmt = null; //Ư���� SQL ������ ���� �ϵ��� �ϴ� class
      ResultSet rs = null; // Ư���� SQL ������ ������ ���Ŀ� ���� ������� ���� ó���ϰ��� �� �� ����ϴ� class
      try {          
          conn = DatabaseUtil.getConnection(); //Connection ��ü �ʱ�ȭ / DatabaseUtil.java ���Ͽ��� ���ϰ��� ��ȯ�� ��ü���� getConnection�� ���� ����ް� conn ��ü�� ��´�
          pstmt = conn.prepareStatement(SQL); //conn ��ü���� prepareStatement�� �����ϵ��� �غ�
          pstmt.setString(1, userID);  //���� ?�� ���� �־��ִ� ��.
          rs = pstmt.executeQuery(); //������ SQL ������ �����ͺ��̽����� �����Ų �� �� ���� rs�� ����ش�.
          if(rs.next()) { // SQL ������ ������ ����� �����ϴ� ��쿡 (�� ���̵� �����ϸ�)
              if(rs.getString(1).equals(userPassword)) { //���� �����Ϳ� ����ڰ� �Է��� �����Ͱ� ��ġ�ϴ� ��� 1�� ��ȯ
                  return 1; // �α��� ����
              }
              else {
                  return 0; // ��й�ȣ Ʋ��
              }
          }
          return -1; // ���̵� ��ġ���� ������ '���̵� ����'
      } catch (Exception e) {
           e.printStackTrace();
      } finally { // �� 4 ��ü�� �ѹ� ����ϸ� �ڿ��� ��ü���ִ� �� �߿���
        try {if(conn != null) conn.close();} catch (Exception e) {e.printStackTrace();}
        try {if(pstmt != null) pstmt.close();} catch (Exception e) {e.printStackTrace();}
        try {if(rs != null) rs.close();} catch (Exception e) {e.printStackTrace();}
     }
      return -2; //������ ���̽� ����
     }
        
        public int join(UserDTO user) {
              String SQL = "INSERT INTO USER VALUES (?, ?, ?, ?, false)";    
              Connection conn = null; 
              PreparedStatement pstmt = null; //Ư���� SQL ������ ���� �ϵ��� �ϴ� class
              ResultSet rs = null; // Ư���� SQL ������ ������ ���Ŀ� ���� ������� ���� ó���ϰ��� �� �� ����ϴ� class
              try {          
                  conn = DatabaseUtil.getConnection(); //Connection ��ü �ʱ�ȭ / DatabaseUtil.java ���Ͽ��� ���ϰ��� ��ȯ�� ��ü���� getConnection�� ���� ����ް� conn ��ü�� ��´�
                  pstmt = conn.prepareStatement(SQL); //conn ��ü���� prepareStatement�� �����ϵ��� �غ�
                  pstmt.setString(1, user.getUserID());
                  pstmt.setString(2, user.getUserPassword());
                  pstmt.setString(3, user.getUserEmail());
                  pstmt.setString(4, user.getUserEmailHash());//���� ?�� ���� �־��ִ� ��.                  
                  return pstmt.executeUpdate(); //executeQuery�� �����͸� �˻��� �� ��� insert �� delete ���� update�� executeUpdate���
              } catch (Exception e) {
                   e.printStackTrace();
              } finally { 
                try {if(conn != null) conn.close();} catch (Exception e) {e.printStackTrace();}
                try {if(pstmt != null) pstmt.close();} catch (Exception e) {e.printStackTrace();}
                try {if(rs != null) rs.close();} catch (Exception e) {e.printStackTrace();}
             }
              return -1; //ȸ������ ����
             }
        
        public String getUserEmail(String userID) {
            String SQL = "SELECT userEmail FROM USER WHERE userID = ?";    
              Connection conn = null; 
              PreparedStatement pstmt = null; //Ư���� SQL ������ ���� �ϵ��� �ϴ� class
              ResultSet rs = null; // Ư���� SQL ������ ������ ���Ŀ� ���� ������� ���� ó���ϰ��� �� �� ����ϴ� class
              try {          
                  conn = DatabaseUtil.getConnection(); //Connection ��ü �ʱ�ȭ / DatabaseUtil.java ���Ͽ��� ���ϰ��� ��ȯ�� ��ü���� getConnection�� ���� ����ް� conn ��ü�� ��´�
                  pstmt = conn.prepareStatement(SQL); //conn ��ü���� prepareStatement�� �����ϵ��� �غ�
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
              return null; // �����ͺ��̽� ����
        }
        
        public boolean getUserEmailChecked(String userID) { //����� �̸��� ����, ���� ���� ������� ���ϰ� �Ұ��̱� ������ �� �Լ��� �ʿ�
              String SQL = "SELECT userEmailChecked FROM USER WHERE userID = ?";    
              Connection conn = null; 
              PreparedStatement pstmt = null; //Ư���� SQL ������ ���� �ϵ��� �ϴ� class
              ResultSet rs = null; // Ư���� SQL ������ ������ ���Ŀ� ���� ������� ���� ó���ϰ��� �� �� ����ϴ� class
              try {          
                  conn = DatabaseUtil.getConnection(); //Connection ��ü �ʱ�ȭ / DatabaseUtil.java ���Ͽ��� ���ϰ��� ��ȯ�� ��ü���� getConnection�� ���� ����ް� conn ��ü�� ��´�
                  pstmt = conn.prepareStatement(SQL); //conn ��ü���� prepareStatement�� �����ϵ��� �غ�
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
              return false; // �����ͺ��̽� ����
             }
        
        public boolean setUserEmailChecked(String userID) { //�̸��� ������ �Ϸ����ִ� �Լ�
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
              return false; // �����ͺ��̽� ����
             }          
         }
        