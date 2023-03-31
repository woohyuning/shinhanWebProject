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
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.3/jquery.min.js"></script>
<title>전체직원조회</title>
<script>
	$(function(){
		$("thead tr th").click(function() {
			//내가 클릭한 th가 몇번째인가?
			var trNum = $(this).closest("th").prevAll().length;
			var a = $("tbody tr").each(function(index, item) {
				var col = $(item).find("td:nth-child(" + (trNum+1) + ")");
				console.log(col);
				//되돌리기(기존선택을 clear)
				$(item).find("td").css("background-color", "white");
				//신규선택의 색깔 바꾸기
				$(col).css("background-color", "orange");
			});
		});
		
		$("#btn1").click(function() {
			$("tr:nth-child(2n)").css("background-color", "lightgray");
			$("tr:nth-child(2n+1)").css("background-color", "white");
		});
		
		$("#btn2").click(function() {
			$("tr > td:nth-child(2):contains('S')").css("color", "red");
		});
		
		$("#btn3").click(function() {
			$("tr td:contains('S')").css("color", "red");
		});
		$("#btn4").click(function() {
			$("tr > td:nth-child(8)").each(function(index, item) {
				var sal = parseInt($(item).html()) + 1;
				
				if(sal >= 5000) {
					$(item).css("background-color", "lightgreen");
				}
			});
		});
		
		$("#btn5").click(function() {
			var arr = $("tr > td:nth-child(1)");
			
			$.each(arr, function(index, item) {
				console.log($(item).text());
				if(parseInt($(item).text()) % 2 == 1) {
					$(item).parseInt().css("background-color", "green");
				}
			});
		});
	});
</script>
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
<a type="button" class="btn btn-success" href="emp_insert.html">직원등록</a>
<hr>
<button id="btn1">짝수row선택</button>
<button id="btn2">이름S로 시작하는 직원</button>
<button id="btn3">S가 포홤된 문자</button>
<button id="btn4">급여5000이상</button>
<button id="btn4">ID가 홀수번 째 직원</button>
<hr>
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