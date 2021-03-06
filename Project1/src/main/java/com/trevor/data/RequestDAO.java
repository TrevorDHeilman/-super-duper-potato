package com.trevor.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;

import com.trevor.beans.Employee;
import com.trevor.beans.Request;

public interface RequestDAO {

	Request getRequestById(int requestId);
	Set<Request> getRequests(Employee emp);
	Set<Request> getRequests2(Employee emp);
	void updateRequest(int requestid, Employee emp, boolean newStatus);
	int addRequest(Request newRequest, Employee emp);
	void requestFurtherComments(int requestId, int SendRequestTo, Employee emp);
	void printResultSet(ResultSet rs) throws SQLException;
	void saveComment(int requestId, String comment, Employee emp);
}
