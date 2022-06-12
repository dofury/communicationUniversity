<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="complaint.ComplaintDTO"%>
<%@ page import="complaint.ComplaintDAO"%>
<%@ page import="util.SHA256"%>
<%@ page import="java.io.PrintWriter"%>

<%

	request.setCharacterEncoding("UTF-8");

	String userID = null;
	if(session.getAttribute("userID") != null) {
		userID = (String) session.getAttribute("userID");
		
	}
	if(userID == null) {
		PrintWriter script = response.getWriter();
		script.println("<script>");
		script.println("alert('로그인을 해주세요');");
		script.println("location.href = 'userLogin.jsp';");
		script.println("</script>");
		script.close();
		return;
	}
	

	String complaintName = null;
	String complaintDivide = null;
	String complaintContent = null;
	
	if (request.getParameter("complaintName") != null) {
		complaintName = request.getParameter("complaintName");
	}
	if (request.getParameter("complaintDivide") != null) {
		complaintDivide = request.getParameter("complaintDivide");
	}
	if (request.getParameter("complaintContent") != null) {
		complaintContent = request.getParameter("complaintContent");
	}
	if (complaintName == null || complaintDivide == null || complaintContent == null || complaintName.equals("") || complaintContent.equals("")) {
		PrintWriter script = response.getWriter();
		script.println("<script>");
		script.println("alert('입력이 안 된 사항이 있습니다.');");
		script.println("history.back();");
		script.println("</script>");
		script.close();
		return;
	}
    ComplaintDAO complaintDAO = new ComplaintDAO();
	int result = complaintDAO.write(new ComplaintDTO(0, userID, complaintName, complaintDivide, complaintContent, 0));
	if (result == -1) {
		PrintWriter script = response.getWriter();
		script.println("<script>");
		script.println("alert('민원 등록을 실패했습니다');");
		script.println("history.back();");
		script.println("</script>");
		script.close();
		return;
	} else {
		PrintWriter script = response.getWriter();
		script.println("<script>");
		script.println("location.href = 'index.jsp'");
		script.println("</script>");
		script.close();
		return;
	}
%>