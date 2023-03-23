package aProject.model;

import java.util.List;

import aProject.vo.EmpVO;

// Service : 업무로직담당
public class EmpService {
	EmpDAO empDao = new EmpDAO();
//	직원 전체 조회
	public List<EmpVO> selectAll() {
		return empDao.selectAll();
	}

//	자신의 속한 부서의 평균급여보다 더 적은 급여를 받는 직원들을 조회
	public List<EmpVO> selectLAB() {
		List<EmpVO> empList = empDao.selectLAB();
		System.out.println("[EmpService] 실행건수 : " + empList.size());
		return empList;
	}

//	특정 직원 조회
	public EmpVO selectById(int empid) {
		return empDao.selectById(empid);
	}

//	특정 부서의 직원을 조회
	public List<EmpVO> selectByDept(int deptid) {
		return empDao.selectByDept(deptid);
	}

//	특정부서, jobid, salary이상 직원조회
	public List<EmpVO> selectByCondition(int deptid, String jobid, double salary) {
		return empDao.selectByCondition(deptid, jobid, salary);
	}

//	신규직원등록
	public String empInsert(EmpVO emp) {
		int result = empDao.empInsert(emp);

		return result > 0 ? "입력성공" : "입력실패";
	}

//	직원정보수정
	public String empUpdate(EmpVO emp) {
		int result = empDao.empUpdate(emp);

		return result > 0 ? "Update성공" : "Update실패";
	}
	
//	한건의 직원을 삭제하기
	public String empDelete(int empid) {
		int result = empDao.empDelete(empid);

		return result > 0 ? "Delete성공" : "Delete실패";
	}
	
//	SP 호출(prosedure)
	public EmpVO getSalary(int empid) {
		return empDao.getSalary(empid);
	}
}
