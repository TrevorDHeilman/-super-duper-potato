/*package com.trevor.services;

import java.util.Set;

import org.apache.log4j.Logger;

import com.trevor.beans.Employee;
import com.trevor.data.EmployeeDAO;
import com.trevor.data.EmployeeOracle;

public class EmployeeServiceOracle implements EmployeeService {
	private Logger log = Logger.getLogger(EmployeeServiceOracle.class);
	private EmployeeDAO ed = new EmployeeOracle();
	
	@Override
	public Employee getEmployee(String username, String password) {
		Employee emp = new Employee();
		emp = ed.getEmployee(emp);
		if(emp.getId()==0)
		{
			log.trace("No employee found");
			return null;
		}
		if(emp.getSupervisor()!=null)
		{
			log.trace("Retrieving supervisor");
			emp.setSupervisor(getEmployeeById(emp.getSupervisor().getId()));
		}
		return emp;
	}

	@Override
	public Employee getEmployeeById(int i) {
		log.trace("retrieving employee by id: "+i);
		Employee emp = new Employee(i);
		emp = (Employee) ud.getUserById(emp);
		emp = ed.getEmployee(emp);
		if(emp.getId()==0)
		{
			log.trace("No employee found");
			return null;
		}
		if(emp.getSupervisor()!=null)
		{
			log.trace("Retrieving supervisor");
			emp.setSupervisor(getEmployeeById(emp.getSupervisor().getId()));
		}
		return emp;
	}

}*/
