<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.io.PrintWriter" %>    
<%@ page import="user.UserDAO" %>  
<%@ page import="complaint.ComplaintDTO" %>  
<%@ page import="complaint.ComplaintDAO" %>  
<%@ page import="java.util.ArrayList" %>  
<%@ page import="java.net.URLEncoder" %>  
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content = "width=device-width, initial-scale=1, shrink-to-fit=no">
	<title>경상국립대학교 신문고</title>
	<!--  부트스트랩 CSS 추가하기 -->
	<link rel="stylesheet" href="./css/bootstrap.min.css">
	<!--  커스텀 CSS 추가하기 -->
	<link rel="stylesheet" href="./css/custom.css">
</head>
<body>
<%
	request.setCharacterEncoding("UTF-8");
	String complaintDivide = "전체";
	String searchType = "최신순";
	String search = "";
	int pageNumber = 0;
	if(request.getParameter("complaintDivide") != null) {
		complaintDivide = request.getParameter("complaintDivide");	
	}
	if(request.getParameter("searchType") != null) {
		searchType = request.getParameter("searchType");	
	}
	if(request.getParameter("search") != null) {
		search = request.getParameter("search");	
	}
	if(request.getParameter("pageNumber") != null) {
		try {
			pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
		} catch (Exception e) {
			System.out.println("검색 페이지 번호 오류");
		}
	}
	String userID = null;
	if(session.getAttribute("userID") != null) {
		userID = (String) session.getAttribute("userID");
		
	}
	if(userID == null) {
		session.setAttribute("userID", userID);
		PrintWriter script = response.getWriter();
		script.println("<script>");
		script.println("alert('로그인을 해주세요');");
		script.println("location.href = 'userLogin.jsp';");
		script.println("</script>");
		script.close();
		return;
	}
	boolean emailChecked = new UserDAO().getUserEmailChecked(userID);
	if(emailChecked == false) {
		PrintWriter script = response.getWriter();
		script.println("<script>");
		script.println("location.href = 'emailSendConfirm.jsp';");
		script.println("</script>");
		script.close();
		return;
	}
%>
	<nav class="navbar navbar-expand-lg navbar-light bg-light">
		<a class="navbar-brand" href="index.jsp">
			<img src="images/mark.png" alt="No Image" width ="50px">
			&nbsp;경상 국립대 신문고</a>
		<button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbar">
			<span class="navbar-toggler-icon"></span>
		</button>
		<div id="navbar" class="collapse navbar-collapse">
			<ul class="navbar-nav mr-auto">
				<li class="nav-item active">
					<a class="nav-link" href="index.jsp">메인</a>
				</li>
				<li class="nav-item dropdown">
					<a class="nav-link dropdown-toggle" id="dropdown" data-toggle="dropdown">
						회원 관리
					</a>
					<div class="dropdown-menu" aria-labelledby="dropdown">
<%
	if(userID == null) {
		
%>
						<a class="dropdown-item" href='userLogin.jsp'>로그인</a>
						<a class="dropdown-item" href='userJoin.jsp'>회원가입</a>
<%
	} else {
%>
						<a class="dropdown-item" href='userLogout.jsp'>로그아웃</a>		
<%
	}
%>			
					</div>
				</li>
			</ul>
			<form action ="./index.jsp" method="get" class="form-inline my-2 my-lg-0">
				<input type="text" name="search" class="form-control mr-sm-2" type="search" placeholder="내용을 입력하세요." aria-label="Search">
				<button class="btn btn-outline-success my-2 my-sm-0" type="submit">검색</button>
			</form>
		</div>
	</nav>
	<section class="container">
		<form method="get" action="./index.jsp" class="form-inline mt-3">
			<select name ="complaintDivide" class="form-control mx-1 mt-2">
				<option value="전체">전체</option>
				<option value="정책" <% if(complaintDivide.equals("정책")) out.println("selected"); %>>정책</option>
				<option value="기숙사" <% if(complaintDivide.equals("기숙사")) out.println("selected"); %>>기숙사</option>
				<option value="식당" <% if(complaintDivide.equals("식당")) out.println("selected"); %>>식당</option>
				<option value="동아리" <% if(complaintDivide.equals("동아리")) out.println("selected"); %>>동아리</option>
				<option value="기타" <% if(complaintDivide.equals("기타")) out.println("selected"); %>>기타</option>
			</select>
			<select name ="searchType" class="form-control mx-1 mt-2">
				<option value="최신순">최신순</option>
				<option value="추천순" <% if(searchType.equals("추천순")) out.println("selected"); %>>추천순</option>
			</select>
			<input type="text" name="search" class="form-control mx-1 mt-2" placeholder="내용을 입력하세요">
			<button type="submit" class="btn btn-primary mx-1 mt-2">검색</button>
			<a class="btn btn-primary mx-1 mt-2" data-toggle="modal" href="#registerModal">등록</a>
			<a class="btn btn-danger mx-1 mt-2" data-toggle="modal" href="#reportModal">신고</a>
		</form>
<%
	ArrayList<ComplaintDTO> complaintList = new ArrayList<ComplaintDTO>();
	complaintList = new ComplaintDAO().getList(complaintDivide, searchType, search, pageNumber);
	if(complaintList != null) {
		   for(int i = 0; i < complaintList.size(); i++) {
			   if(i == 5) break;
			   ComplaintDTO complaint = complaintList.get(i);
%>
		<div class="card bg-light mt-3">
			<div class="card-header bg-light">
				<div class="row">
					<div class="col-8 text-left" style="font-size:150%;"><%= complaint.getComplaintName() %>&nbsp;</div>
					<div class="col-4 text-right">
						<span style="color: blue;"><%= complaint.getComplaintDivide() %></span>
					</div>
				</div>
			</div>
			<div class="card-body">
				<div class="row">
					<div class="col-10 text-left">
						<p class="card-text"><%= complaint.getComplaintContent() %></p>
					</div>
					<div class="col-2 text-right">
					<small>익명</small>
					</div>
				</div>
				<br>
				<div class="row">
					<div class="col-9 text-left">추천 수:
						<span style="color: green;"><%= complaint.getLikeCount() %></span>
					</div>
					<div class="col-3 text-right">
						<a onclick="return confirm('해당 글을 추천하시겠습니까?')" href="./likeAction.jsp?complaintID=<%= complaint.getComplaintID() %>"style="color: green;">추천</a>
						<a onclick="return confirm('해당 글을 삭제하시겠습니까?')" href="./deleteAction.jsp?complaintID=<%= complaint.getComplaintID() %>"style="color: red;">삭제</a>
					</div>
				</div>
			</div>
		</div>
<%
	}
	}
%>
	</section>
	<ul class="pagination justify-content-center mt-3">
		<li class="page-item">
<%
	if(pageNumber <= 0 ) {
%>
	<a class="page-link disabled">이전</a>
<%
	} else {
%>
	<a class="page-link" href="./index.jsp?complaintDivide=<%= URLEncoder.encode(complaintDivide, "UTF-8") %>&searchType=
			<%= URLEncoder.encode(searchType, "UTF-8") %>&search=<%= URLEncoder.encode(search, "UTF-8") %>
			&pageNumber=<%= pageNumber-1 %>">이전</a> 
<%	
	}
%>				
		</li>
		<li>
<%
	if(complaintList.size() < 6) {
%>
	<a class="page-link disabled">다음</a>
<%
	} else {
%>
	<a class="page-link" href="./index.jsp?complaintDivide=<%= URLEncoder.encode(complaintDivide, "UTF-8") %>&searchType=
			<%= URLEncoder.encode(searchType, "UTF-8") %>&search=<%= URLEncoder.encode(search, "UTF-8") %>
			&pageNumber=<%= pageNumber+1 %>">다음</a> 
<%	
	}
%>	
		
		</li>
	</ul>
	<div class="modal fade" id="registerModal" tabindex="-1" role="dialog" aria-labelledby="modal" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="modal">민원 등록</h5>
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<form action="./complaintRegisterAction.jsp" method="post">
						<div class="form-row">
							<div class="form-group col-sm-9">
								<label>민원 제목</label>
								<input type="text" name="complaintName" class="form-control" maxlength="20">
							</div>
							<div class="form-group col-sm-3">
								<label>민원 구분</label>
								<select name="complaintDivide" class="form-control">
									<option value="정책">정책</option>
									<option value="기숙사">기숙사</option>
									<option value="식당">식당</option>
									<option value="동아리">동아리</option>
									<option value="기타">기타</option>								
								</select>
							</div>	
						</div>
						<div class="form-row">
							<div class="form-group col-sm-12">
								<label>민원 내용</label>
								<textarea name="complaintContent" class="form-control" maxlength="2048" style="height: 180px;"></textarea>
							</div>						
						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-secondary" data-dismiss="modal">취소</button>
							<button type="submit" class="btn btn-primary">등록</button>
						</div>
					</form>
				</div>		
			</div>
		</div>
	</div>
		<div class="modal fade" id="reportModal" tabindex="-1" role="dialog" aria-labelledby="modal" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title" id="modal">게시글 신고</h5>
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<form action="./reportAction.jsp" method="post">
						<div class="form-row">
							<div class="form-group col-sm-9">
								<label>신고 제목</label>
								<input type="text" name="reportName" class="form-control" maxlength="20">
							</div>
							<div class="form-group col-sm-3">
								<label>신고 구분</label>
								<select name="reportDivide" class="form-control">
									<option value="홍보">홍보</option>
									<option value="욕설">욕설</option>
									<option value="음란물">음란물</option>
									<option value="부적합">부적합</option>
									<option value="기타">기타</option>								
								</select>
							</div>	
						</div>						
						<div class="form-row">
							<div class="form-group col-sm-12">
								<label>신고 내용</label>
								<textarea name="reportContent" class="form-control" maxlength="2048" style="height: 180px;"></textarea>
							</div>						
						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-secondary" data-dismiss="modal">취소</button>
							<button type="submit" class="btn btn-danger">등록</button>
						</div>
					</form>
				</div>		
			</div>
		</div>
	</div>
	<footer class="bg-dark mt-4 p-5 text-center" style="color: #FFFFFF;">
		Copyright &copy; 2022 차도훈 All Rights Reserved.
	</footer>
	<!--  제이쿼리 자바스크립트 추가하기 -->
	<script src="./js/jquery.min.js"></script>
	<!--  파퍼 자바스크립트 추가하기 -->
	<script src="./js/popper.js"></script>
	<!--  부트스트랩 자바스크립트 추가하기 -->
	<script src="./js/bootstrap.min.js"></script>
</body>
</html>