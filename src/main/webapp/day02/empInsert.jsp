<%@ page import="aProject.model.EmpService"%>
<%@ page import="com.shinhan.dbutil.DateUtil"%>
<%@ page import="aProject.vo.EmpVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
request.setCharacterEncoding("UTF-8");
String employee_id = request.getParameter("employee_id");
String first_name = request.getParameter("first_name");
String last_name = request.getParameter("last_name");
String email = request.getParameter("email");
String phone_number = request.getParameter("phone_number");
String hire_date = request.getParameter("hire_date");
String job_id = request.getParameter("job_id");
String salary = request.getParameter("salary");
String commission_pct = request.getParameter("commission_pct");
String manager_id = request.getParameter("manager_id");
String department_id = request.getParameter("department_id");

EmpVO emp = new EmpVO();

emp.setEmployee_id(Integer.parseInt(employee_id));
emp.setFirst_name(first_name);
emp.setLast_name(last_name);
emp.setEmail(email);
emp.setPhone_number(phone_number);
emp.setHire_date(DateUtil.convertToDate(hire_date));
emp.setJob_id(job_id);
emp.setSalary(Integer.parseInt(salary));
emp.setCommission_pct(Double.parseDouble(commission_pct));
emp.setManager_id(Integer.parseInt(manager_id));
emp.setDepartment_id(Integer.parseInt(department_id));

System.out.print(emp);

EmpService service = new EmpService();
service.empInsert(emp);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style type="">
p {
	border: 1px solid green;
}
</style>
</head>
<body>
	<h1>신규직원을 DB에 저장합니다</h1>
	<p><%=emp%></p>
	<p>
		직원번호 :
		<%=employee_id%></p>
	<p>
		이름 :
		<%=first_name%></p>
	<p>
		성 :
		<%=last_name%></p>
	<p>
		이메일 :
		<%=email%></p>
	<p>
		연락처 :
		<%=phone_number%></p>
	<p>
		입사일 :
		<%=hire_date%></p>
	<p>
		직책 :
		<%=job_id%></p>
	<p>
		급여 :
		<%=salary%></p>
	<p>
		커미션 :
		<%=commission_pct%></p>
	<p>
		매니저 :
		<%=manager_id%></p>
	<p>
		부서 :
		<%=department_id%></p>
</body>
</html>