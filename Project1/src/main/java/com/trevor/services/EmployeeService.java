package com.trevor.services;

import java.util.Set;

import com.trevor.beans.Employee;

public interface EmployeeService {
	public Employee getEmployee(String username, String password);
	public Employee getEmployeeById(int i);
}
