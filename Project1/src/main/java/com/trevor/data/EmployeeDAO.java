package com.trevor.data;

import com.trevor.beans.Employee;

public interface EmployeeDAO {

	Employee getEmployee(int employeeId);
	Employee getEmployee(String employeeUserName, String employeePassword);
	Employee getBoss(int employeeId);
	Employee getDeptHead(int deptId);
	Employee getBenco(int employeeId);
	void updateAvailableFunds(Employee employee);
}
