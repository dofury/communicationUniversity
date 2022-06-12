<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ page import="user.UserDAO"%>
<%@ page import="complaint.ComplaintDAO"%>
<%@ page import="likey.LikeyDTO"%>
<%@ page import="java.io.PrintWriter"%>
<%
   String userID = null;
   if(session.getAttribute("userID") != null) {
	   userID = (String) session.getAttribute("userID");
   }
   if(userID == null) {
	   PrintWriter script = response.getWriter();
	   script.println("<script>");
	   script.println("alert('로그인을 해주세요.');");
	   script.println("location.href = 'userLogin.jsp'");
	   script.println("</script>");
	   script.close();
	   return;
   }
   
   request.setCharacterEncoding("UTF-8");
   String complaintID = null;
   if(request.getParameter("complaintID") != null) {
	   complaintID = request.getParameter("complaintID");
   }
   ComplaintDAO complaintDAO = new ComplaintDAO();
   if(userID.equals(complaintDAO.getUserID(complaintID))) {
	   int result = new ComplaintDAO().delete(complaintID);
	   if (result == 1) {
		   PrintWriter script = response.getWriter();
		   script.println("<script>");
		   script.println("alert('삭제가 완료 되었습니다');");
		   script.println("location.href = 'index.jsp'");
		   script.println("</script>");
		   script.close();
		   return;
	   }
	   else {
		   PrintWriter script = response.getWriter();
		   script.println("<script>");
		   script.println("alert('데이터베이스 오류가 발생했습니다');");
		   script.println("history.back();");
		   script.println("</script>");
		   script.close();
		   return;
	   }
   } else {
	   PrintWriter script = response.getWriter();
	   script.println("<script>");
	   script.println("alert('자신이 쓴 글만 삭제 가능합니다');");
	   script.println("history.back();");
	   script.println("</script>");
	   script.close();
	   return;
   }
%>