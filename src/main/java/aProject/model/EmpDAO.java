package aProject.model;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import com.shinhan.dbutil.OracleUtil;

import aProject.vo.EmpVO;

// DAO(Data Access Object) : DB업무 (CRUD) Insert, Select, Update, Delete
public class EmpDAO {
	Connection conn;
	Statement st;
	PreparedStatement pst; // ? 지원
	CallableStatement cst; // SP 지원
	ResultSet rs;
	int resultCount; // insert, update, delete 건수

//	직원 전체 조회
	public List<EmpVO> selectAll() {
		String sql = """
				select	EMPLOYEE_ID,
						FIRST_NAME,
						LAST_NAME,
						EMAIL,
						PHONE_NUMBER,
						HIRE_DATE,
						JOB_ID,
						f_tax(SALARY) SALARY,
						COMMISSION_PCT,
						MANAGER_ID,
						DEPARTMENT_ID,
				from employees
				order by 1
				""";
		List<EmpVO> empList = new ArrayList<>();
		conn = OracleUtil.getConnection();
		try {
			st = conn.createStatement();
			if(st.execute(sql)) {
				rs = st.executeQuery(sql);
			}
			ResultSetMetaData meta = rs.getMetaData();
			int count = meta.getColumnCount();
			for(int i=1; i<=count; i++) {
				System.out.println("칼럼이름:" + meta.getColumnName(i));
			}
			
			while (rs.next()) {
				EmpVO emp = makeEmp(rs);
				empList.add(emp);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			OracleUtil.dbDisconnect(rs, st, conn);
		}
		return empList;
	}
	
//	특정 직원 조회
	public EmpVO selectById(int empid) {
		EmpVO emp = null;
		String sql = "select * from employees where employee_id = " + empid;
		conn = OracleUtil.getConnection();
		try {
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			while (rs.next()) {
				emp = makeEmp(rs);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			OracleUtil.dbDisconnect(rs, st, conn);
		}
		return emp;
	}

//	특정 부서의 직원을 조회
	public List<EmpVO> selectByDept(int deptid) {
		String sql = "select * from employees where department_id = " + deptid;
		List<EmpVO> empList = new ArrayList<>();
		conn = OracleUtil.getConnection();
		try {
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			while (rs.next()) {
				EmpVO emp = makeEmp(rs);
				empList.add(emp);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			OracleUtil.dbDisconnect(rs, st, conn);
		}
		return empList;
	}

//	특정부서, jobid, salary이상 직원조회
	public List<EmpVO> selectByCondition(int deptid, String jobid, double salary) {
		String sql = """
					select *
					from employees
					where department_id = ?
					and job_id = ?
					and salary >= ?
				""";
		List<EmpVO> empList = new ArrayList<>();
		conn = OracleUtil.getConnection();
		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, deptid);
			pst.setString(2, jobid);
			pst.setDouble(3, salary);
			rs = pst.executeQuery();
			while (rs.next()) {
				EmpVO emp = makeEmp(rs);
				empList.add(emp);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			OracleUtil.dbDisconnect(rs, pst, conn);
		}
		return empList;
	}

//	자신의 속한 부서의 평균급여보다 더 적은 급여를 받는 직원들을 조회
	public List<EmpVO> selectLAB() {
		String sql = """
				select employee_id, first_name, salary, employees.department_id
				from employees, (
				                 select department_id, avg(salary) sal
				                 from employees
				                 group by department_id) inlineView_emp
				where employees.department_id = inlineView_emp.department_id
				and employees.salary < inlineView_emp.sal
				""";
		List<EmpVO> empList = new ArrayList<>();
		conn = OracleUtil.getConnection();
		try {
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			while (rs.next()) {
				EmpVO emp = makeEmp2(rs);
				empList.add(emp);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			OracleUtil.dbDisconnect(rs, st, conn);
		}
		return empList;
	}

//	신규직원등록
	public int empInsert(EmpVO emp) {
		String sql = """
				insert into employees
				values (seq_employee.nextval, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
				""";
		conn = OracleUtil.getConnection();
		try {
			pst = conn.prepareStatement(sql);

			pst.setString(1, emp.getFirst_name());
			pst.setString(2, emp.getLast_name());
			pst.setString(3, emp.getEmail());
			pst.setString(4, emp.getPhone_number());
			pst.setDate(5, emp.getHire_date());
			pst.setString(6, emp.getJob_id());
			pst.setDouble(7, emp.getSalary());
			pst.setDouble(8, emp.getCommission_pct());
			pst.setInt(9, emp.getManager_id());
			pst.setInt(10, emp.getDepartment_id());

			resultCount = pst.executeUpdate(); // DML문장 실행한다. 영향받은 건수가 return
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			resultCount = -1;
			e.printStackTrace();
		} finally {
			OracleUtil.dbDisconnect(rs, pst, conn);
		}
		return resultCount;
	}

//	직원정보수정
	public int empUpdate(EmpVO emp) {
		String sql = """
				update employees
				set EMAIL = ?, DEPARTMENT_ID = ?, JOB_ID = ?, SALARY = ?, hire_date = ?, manager_id = ?
				where EMPLOYEE_ID = ?
				""";
		conn = OracleUtil.getConnection();
		try {
			pst = conn.prepareStatement(sql);

			pst.setString(1, emp.getEmail());
			pst.setInt(2, emp.getDepartment_id());
			pst.setString(3, emp.getJob_id());
			pst.setDouble(4, emp.getSalary());
			pst.setDate(5, emp.getHire_date());
			pst.setInt(6, emp.getManager_id());
			pst.setInt(7, emp.getEmployee_id());

			resultCount = pst.executeUpdate(); // DML문장 실행한다. 영향받은 건수가 return

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			resultCount = -1;
			e.printStackTrace();
		} finally {
			OracleUtil.dbDisconnect(rs, pst, conn);
		}
		System.out.println("update 결과 : " + resultCount);
		return resultCount;
	}

//	한건의 직원을 삭제하기
	public int empDelete(int empid) {
		String sql = """
				delete from employees
				where EMPLOYEE_ID = ?
				""";
		conn = OracleUtil.getConnection();
		try {
			pst = conn.prepareStatement(sql);

			pst.setInt(1, empid);

			resultCount = pst.executeUpdate(); // DML문장 실행한다. 영향받은 건수가 return

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			resultCount = -1;
		} finally {
			OracleUtil.dbDisconnect(rs, pst, conn);
		}
		System.out.println("Delete 결과 : " + resultCount);
		return resultCount;
	}

//	SP 호출(prosedure)
	public EmpVO getSalary(int empid) {
		String sql = "{call sp_salary(?, ?, ?) }";
		conn = OracleUtil.getConnection();
		EmpVO emp = new EmpVO();
		try {
			cst = conn.prepareCall(sql);
			cst.setInt(1, empid);
			cst.registerOutParameter(2, Types.DOUBLE);
			cst.registerOutParameter(3, Types.VARCHAR);
			cst.execute(); // resultset있ㅇ면 true이고 없으면 false
//			executeQuery(), executeUpdate()
			emp.setSalary(cst.getDouble(2));
			emp.setFirst_name(cst.getString(3));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			OracleUtil.dbDisconnect(null, cst, conn);
		}
		return emp;
	}

	private EmpVO makeEmp(ResultSet rs) throws SQLException {
		EmpVO emp = new EmpVO();

		emp.setCommission_pct(rs.getDouble("Commission_pct"));
		emp.setDepartment_id(rs.getInt("Department_id"));
		emp.setEmail(rs.getString("Email"));
		emp.setEmployee_id(rs.getInt("Employee_id"));
		emp.setFirst_name(rs.getString("First_name"));
		emp.setHire_date(rs.getDate("Hire_date"));
		emp.setJob_id(rs.getString("Job_id"));
		emp.setLast_name(rs.getString("Last_name"));
		emp.setManager_id(rs.getInt("Manager_id"));
		emp.setPhone_number(rs.getString("Phone_number"));
		emp.setSalary(rs.getDouble("Salary"));

		return emp;
	}

	private EmpVO makeEmp2(ResultSet rs) throws SQLException {
		EmpVO emp = new EmpVO();

		emp.setDepartment_id(rs.getInt("Department_id"));
		emp.setEmployee_id(rs.getInt("Employee_id"));
		emp.setFirst_name(rs.getString("First_name"));
		emp.setSalary(rs.getDouble("Salary"));

		return emp;
	}
}
