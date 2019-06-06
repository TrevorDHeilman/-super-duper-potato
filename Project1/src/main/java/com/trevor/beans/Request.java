package com.trevor.beans;

import java.sql.Date;

public class Request {

	private int requestId, employeeId, appliedreimbursement, eventType;
	private String status, additionalNotes;
	private Date requestDate, eventDate;
	
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
	public int getAppliedreimbursement() {
		return appliedreimbursement;
	}
	public void setAppliedreimbursement(int appliedreimbursement) {
		this.appliedreimbursement = appliedreimbursement;
	}
	public int getEventType() {
		return eventType;
	}
	public void setEventType(int eventType) {
		this.eventType = eventType;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getAdditionalNotes() {
		return additionalNotes;
	}
	public void setAdditionalNotes(String additionalNotes) {
		this.additionalNotes = additionalNotes;
	}
	public String getRequestDate() {
		return requestDate.toString();
	}
	public Date getDRequestDate() {
		return requestDate;
	}
	public void setRequestDate(Date requestDate) {
		this.requestDate = requestDate;
	}
	public String getEventDate() {
		return eventDate.toString();
	}
	public Date getDEventDate() {
		return eventDate;
	}
	public void setEventDate(Date eventDate) {
		this.eventDate = eventDate;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((additionalNotes == null) ? 0 : additionalNotes.hashCode());
		result = prime * result + appliedreimbursement;
		result = prime * result + employeeId;
		result = prime * result + ((eventDate == null) ? 0 : eventDate.hashCode());
		result = prime * result + eventType;
		result = prime * result + ((requestDate == null) ? 0 : requestDate.hashCode());
		result = prime * result + requestId;
		result = prime * result + ((status == null) ? 0 : status.hashCode());
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
		Request other = (Request) obj;
		if (additionalNotes == null) {
			if (other.additionalNotes != null)
				return false;
		} else if (!additionalNotes.equals(other.additionalNotes))
			return false;
		if (appliedreimbursement != other.appliedreimbursement)
			return false;
		if (employeeId != other.employeeId)
			return false;
		if (eventDate == null) {
			if (other.eventDate != null)
				return false;
		} else if (!eventDate.equals(other.eventDate))
			return false;
		if (eventType != other.eventType)
			return false;
		if (requestDate == null) {
			if (other.requestDate != null)
				return false;
		} else if (!requestDate.equals(other.requestDate))
			return false;
		if (requestId != other.requestId)
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "Request [requestId=" + requestId + ", employeeId=" + employeeId + ", appliedreimbursement="
				+ appliedreimbursement + ", eventType=" + eventType + ", status=" + status + ", additionalNotes="
				+ additionalNotes + ", requestDate=" + requestDate + ", eventDate=" + eventDate + "]";
	}
}
