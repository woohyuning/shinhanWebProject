<%@page import="java.util.List"%>
<%@page import="aProject.vo.EmpVO"%>
<%@page import="aProject.model.EmpService"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	EmpService service = new EmpService();
	List<EmpVO> empList = service.selectAll();
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>
<title>전체직원조회</title>
<!-- <style>
	body {
		background-color: black;
	}
	#container {
 		text-align: center;
		width:25%, 50%, 25%;
		height: 30px;
	}
	table, thead tr {
		border:3px solid black;
		border-collapse:collapse; /* 테이블테두리 1줄 */
		margin-left:auto; 
    	margin-right:auto;
    	background-color: #EAEAEA;
	}
	td {
		border:1px solid black;
		border-collapse:collapse; /* 테이블테두리 1줄 */
		margin-left:auto; 
    	margin-right:auto;
	}
	th {
		border:1px solid black;
		background-color: #0054FF;	
	}
	h1 {
		color: white; 
	}
</style> -->
</head>
<body>
<div id="container" class="container mt-3">
<h1>직원목록</h1>
<button type="button" class="btn btn-success" onclick="location.href='emp_insert.html'">직원등록</button>
<a ctype="button" class="btn btn-success" href="emp_insert.html">직원등록</a>
<table class="table table-hover">
	<thead>
		<tr>
			<th>직원번호</th>
			<th>이름</th>
			<th>성</th>
			<th id="email">email</th>
			<th>연락처</th>
			<th>입사일</th>
			<th>직책</th>
			<th>급여</th>
			<th>커미션</th>
			<th>매니저</th>
			<th>부서</th>
		</tr>
	</thead>
	<tbody>
		<% 
			for(EmpVO emp : empList) {
		%>
		<tr>
			<td><%=emp.getEmployee_id() %></td>
			<td><%=emp.getFirst_name() %></td>
			<td><%=emp.getLast_name() %></td>
			<td><%=emp.getEmail() %></td>
			<td><%=emp.getPhone_number() %></td>
			<td><%=emp.getHire_date() %></td>
			<td><%=emp.getJob_id() %></td>
			<td><%=emp.getSalary() %></td>
			<td><%=emp.getCommission_pct() %></td>
			<td><%=emp.getManager_id() %></td>
			<td><%=emp.getDepartment_id() %></td>
		</tr>
		<%
			}
		%>
	</tbody>
</table>
</div>
</body>
</html>