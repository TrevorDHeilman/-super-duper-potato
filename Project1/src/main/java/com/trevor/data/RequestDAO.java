package com.trevor.data;

import java.util.Set;

import com.trevor.beans.Employee;
import com.trevor.beans.Request;

public interface RequestDAO {

	Request getRequestById(int requestId);
	Set<Request> getRequests();
	void updateRequest(Request request, Employee emp, boolean newStatus);
	int addRequest(Request newRequest, Employee emp);
	void requestFurtherComments(int requestId);
}
