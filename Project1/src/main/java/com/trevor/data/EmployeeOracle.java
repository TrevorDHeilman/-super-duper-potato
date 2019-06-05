package com.trevor.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.apache.log4j.Logger;
import com.trevor.beans.Employee;
import com.trevor.utils.ConnectionUtil;
import com.trevor.utils.LogUtil;


public class EmployeeOracle implements EmployeeDAO{

	private static Logger log = Logger.getLogger(EmployeeOracle.class);
	private static ConnectionUtil cu = ConnectionUtil.getConnectionUtil();	
	
	@Override
	public Employee getEmployee(int employeeId) {
		Employee newEmployee = null;
		try(Connection conn = cu.getConnection())
		{
			log.trace("Attempting to find user with employee with id: " + employeeId);
			String sql = "Select * from Employee where EmployeeId =?";
			PreparedStatement pstm = conn.prepareStatement(sql);
			pstm.setInt(1, employeeId);
			ResultSet rs = pstm.executeQuery();
			log.trace("rs");
			if(rs.next())
			{
				log.trace("User found.");
				newEmployee = new Employee();
				newEmployee.setEmployeeId(rs.getInt("EmployeeId"));
				newEmployee.setUserName(rs.getString("username"));
				newEmployee.setPassWord(rs.getString("Password"));
				newEmployee.setFirstName(rs.getString("firstname"));
				newEmployee.setLastName(rs.getString("lastname"));
				newEmployee.setTitleId(rs.getInt("EmployeeTitle"));
				newEmployee.setReportsTo(rs.getInt("ReportsTo"));
				newEmployee.setDepartmentId(rs.getInt("Department"));
				newEmployee.setReimbursementAvailable(rs.getInt("ReimbursementAvailable"));

			}
		} catch (Exception e) {
			LogUtil.logException(e,EmployeeOracle.class);
		}
		return newEmployee;
	}

	@Override
	public Employee getEmployee(String employeeUserName, String employeePassword) {
		Employee newEmployee = null;
		try(Connection conn = cu.getConnection())
		{
			log.trace("Attempting to find user with username: " + employeeUserName + " password: " + employeePassword);
			String sql = "Select * from Employee where username =? and password =?";
			PreparedStatement pstm = conn.prepareStatement(sql);
			pstm.setString(1, employeeUserName);
			pstm.setString(2, employeePassword);
			ResultSet rs = pstm.executeQuery();
			if(rs.next())
			{
				log.trace("User found.");
				log.trace(rs);
				newEmployee = new Employee();
				newEmployee.setEmployeeId(rs.getInt("EmployeeId"));
				newEmployee.setUserName(rs.getString("username"));
				newEmployee.setPassWord(rs.getString("Password"));
				newEmployee.setFirstName(rs.getString("firstname"));
				newEmployee.setLastName(rs.getString("lastname"));
				newEmployee.setTitleId(rs.getInt("EmployeeTitle"));
				newEmployee.setReportsTo(rs.getInt("ReportsTo"));
				newEmployee.setDepartmentId(rs.getInt("Department"));
				newEmployee.setReimbursementAvailable(rs.getInt("ReimbursementAvailable"));

			}
		} catch (Exception e) {
			LogUtil.logException(e,EmployeeOracle.class);
		}
		return newEmployee;
	}
	
	@Override
	public Employee getBoss(int employeeId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Employee getDeptHead(int deptId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Employee getBenco(int employeeId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateAvailableFunds(Employee employee) {
		// TODO Auto-generated method stub
		
	}
}
