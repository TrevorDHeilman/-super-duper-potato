package com.trevor.beans;

public class Employee {

	private int employeeId, departmentId, titleId, ReportsTo, reimbursementAvailable;
	private String userName, passWord, firstName, lastName;

	public int getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}
	

	public int getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(int departmentId) {
		this.departmentId = departmentId;
	}
	

	public int getTitleId() {
		return titleId;
	}
	public void setTitleId(int titleId) {
		this.titleId = titleId;
	}
	
	public int getReportsTo() {
		return ReportsTo;
	}
	public void setReportsTo(int reportsTo) {
		ReportsTo = reportsTo;
	}
	

	public int getReimbursementAvailable() {
		return reimbursementAvailable;
	}
	public void setReimbursementAvailable(int reimbursementAvailable) {
		this.reimbursementAvailable = reimbursementAvailable;
	}
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	

	public String getPassWord() {
		return passWord;
	}
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	

	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	

	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ReportsTo;
		result = prime * result + departmentId;
		result = prime * result + employeeId;
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result + ((passWord == null) ? 0 : passWord.hashCode());
		result = prime * result + reimbursementAvailable;
		result = prime * result + titleId;
		result = prime * result + ((userName == null) ? 0 : userName.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Employee other = (Employee) obj;
		if (ReportsTo != other.ReportsTo)
			return false;
		if (departmentId != other.departmentId)
			return false;
		if (employeeId != other.employeeId)
			return false;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		if (passWord == null) {
			if (other.passWord != null)
				return false;
		} else if (!passWord.equals(other.passWord))
			return false;
		if (reimbursementAvailable != other.reimbursementAvailable)
			return false;
		if (titleId != other.titleId)
			return false;
		if (userName == null) {
			if (other.userName != null)
				return false;
		} else if (!userName.equals(other.userName))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "Employee [employeeId=" + employeeId + ", departmentId=" + departmentId + ", titleId=" + titleId
				+ ", ReportsTo=" + ReportsTo + ", reimbursementAvailable=" + reimbursementAvailable + ", userName="
				+ userName + ", passWord=" + passWord + ", firstName=" + firstName + ", lastName=" + lastName + "]";
	}	
}
