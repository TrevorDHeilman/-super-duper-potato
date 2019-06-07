package com.trevor.beans;

public class Comment {

	private String action, employeeComment;
	private int requestId, employeeId;
	
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getEmployeeComment() {
		return employeeComment;
	}
	public void setEmployeeComment(String employeeComment) {
		this.employeeComment = employeeComment;
	}
	public int getRequestId() {
		return requestId;
	}
	public void setRequestId(int requestId) {
		this.requestId = requestId;
	}
	public int getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((action == null) ? 0 : action.hashCode());
		result = prime * result + ((employeeComment == null) ? 0 : employeeComment.hashCode());
		result = prime * result + employeeId;
		result = prime * result + requestId;
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
		Comment other = (Comment) obj;
		if (action == null) {
			if (other.action != null)
				return false;
		} else if (!action.equals(other.action))
			return false;
		if (employeeComment == null) {
			if (other.employeeComment != null)
				return false;
		} else if (!employeeComment.equals(other.employeeComment))
			return false;
		if (employeeId != other.employeeId)
			return false;
		if (requestId != other.requestId)
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "Comment [action=" + action + ", employeeComment=" + employeeComment + ", requestId=" + requestId
				+ ", employeeId=" + employeeId + "]";
	}
}
