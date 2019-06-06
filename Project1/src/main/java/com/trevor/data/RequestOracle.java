package com.trevor.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import org.apache.log4j.Logger;
import com.trevor.beans.Employee;
import com.trevor.beans.Request;
import com.trevor.utils.ConnectionUtil;
import com.trevor.utils.LogUtil;

public class RequestOracle implements RequestDAO{

	private static Logger log = Logger.getLogger(RequestOracle.class);
	private static ConnectionUtil cu = ConnectionUtil.getConnectionUtil();	
	
	@Override
	public Request getRequestById(int requestId) {
		Request request = null;
		log.trace("Retrieving request from database.");
		try (Connection conn = cu.getConnection()) {
			String sql = "select employeeid, appliedreimbursement, requestdate, eventdate, status, eventtype from request where requestid=?";

			PreparedStatement pstm = conn.prepareStatement(sql);
			pstm.setInt(1, requestId);

			ResultSet rs = pstm.executeQuery();
			if (rs.next()) {
				log.trace("Request found!");
				request = new Request();
				request.setRequestId(requestId);
				request.setEmployeeId(rs.getInt("employeeid"));
				request.setAppliedreimbursement(rs.getInt("appliedreimbursement"));
				request.setRequestDate(rs.getDate("requestdate"));
				request.setEventDate(rs.getDate("eventdate"));
				request.setStatus(rs.getString("statusid"));
				request.setEventType(rs.getInt("eventtype"));
			}
		} catch (Exception e) {
			LogUtil.logException(e, RequestOracle.class);
		}
		return request;
	}

	@Override
	public Set<Request> getRequests(Employee emp) {
		Set<Request> requestList = new HashSet<Request>();
		log.trace("retrieving all requests from database.");
		try (Connection conn = cu.getConnection()) {
			String sql = "select * from request where employeeid=?";
			PreparedStatement pstm = conn.prepareStatement(sql);
			pstm.setInt(1, emp.getEmployeeId());
			ResultSet rs = pstm.executeQuery();
			while (rs.next()) {
				
				Request request = new Request();
				request.setRequestId(rs.getInt("requestid"));
				request.setEmployeeId(rs.getInt("employeeid"));
				request.setAppliedreimbursement(rs.getInt("appliedreimbursement"));
				request.setRequestDate(rs.getDate("requestdate"));
				request.setEventDate(rs.getDate("eventdate"));
				request.setStatus(rs.getString("statusid"));
				request.setEventType(rs.getInt("eventtype"));
				log.trace("Adding Request to the list");
				requestList.add(request);
			}
		} catch (Exception e) {
			LogUtil.logException(e, RequestOracle.class);
		}
		log.trace(requestList);
		return requestList;
	}
	
	@Override
	public Set<Request> getRequests2(Employee emp) {
		Set<Request> requestList = new HashSet<Request>();
		log.trace("retrieving all requests from database.");
		try (Connection conn = cu.getConnection()) {
			String sql = "select * from request where statusid=?";
			PreparedStatement pstm = conn.prepareStatement(sql);
			pstm.setInt(1, emp.getEmployeeId());
			ResultSet rs = pstm.executeQuery();
			while (rs.next()) {
				
				log.trace("Getting in result set");
				Request request = new Request();
				request.setRequestId(rs.getInt("requestid"));
				request.setEmployeeId(rs.getInt("employeeid"));
				request.setAppliedreimbursement(rs.getInt("appliedreimbursement"));
				request.setRequestDate(rs.getDate("requestdate"));
				request.setEventDate(rs.getDate("eventdate"));
				request.setStatus(rs.getString("statusid"));
				request.setEventType(rs.getInt("eventtype"));
				log.trace("Adding Request to the list");
				requestList.add(request);
			}
		} catch (Exception e) {
			LogUtil.logException(e, RequestOracle.class);
		}
		log.trace(requestList);
		return requestList;
	}
	
	@Override
	public void updateRequest(Request request, Employee emp, boolean newStatus) {
		log.trace("updating request in database: " + request);
		Connection conn = cu.getConnection();
		ArrayList<Integer> deptHeadKeys = new ArrayList<>(Arrays.asList(101, 102, 103));
		try {
			conn.setAutoCommit(false);
			String sql = "update request set status=? where requestid=?";
			PreparedStatement pstm = conn.prepareStatement(sql);
			if(newStatus) {
				
				String sqlQuery = "select * from employee where employeeid=?";
				PreparedStatement pstmQuery = conn.prepareStatement(sqlQuery);
				pstmQuery.setInt(1, request.getEmployeeId());
				ResultSet rs = pstmQuery.executeQuery();
				if (rs.next()) {
					
					if(emp.getEmployeeId() == rs.getInt("reportsto") && !deptHeadKeys.contains(emp.getReportsTo())){
						
						pstm.setInt(1, emp.getReportsTo());
					}
					else if(deptHeadKeys.contains(emp.getReportsTo())) {																					
						
						pstm.setInt(1, 4);	
					}
				}
			}
			else {
				pstm.setInt(1, 3);
			}
			
			pstm.setInt(2,request.getRequestId());
			int result = pstm.executeUpdate();

			if (result == 1) {
				log.trace("Request updated");
				
				String setRequestStory = "Insert into RequestStory (RequestId, EmployeeId) VALUES (?, ?)";
				PreparedStatement ptsmRequestStory = conn.prepareStatement(setRequestStory);
				ptsmRequestStory.setInt(1, request.getRequestId());
				ptsmRequestStory.setInt(2, emp.getEmployeeId());
				ptsmRequestStory.executeQuery();				
			}
			else {
				log.trace("Request failed to update");
			}
		} 
		catch (SQLException e) {
			LogUtil.rollback(e, conn, RequestOracle.class);
		} 
		finally {
			try {
				conn.close();
			} 
			catch (SQLException e) {
				LogUtil.logException(e, RequestOracle.class);
			}
		}
	}

	@Override
	public int addRequest(Request newRequest, Employee emp) {
		int key = 0;
		String datePattern = "dd/MM/yyyy";
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(datePattern);
		log.trace(LocalDateTime.now().format(dateFormatter).toString());
		log.trace("adding request to the database: "+ newRequest);
		Connection conn = cu.getConnection();
		try {
			conn.setAutoCommit(false);
			String sql = "insert into request(employeeid, appliedreimbursement, requestdate, "
					+ "eventdate, statusid, eventtype) values (?,?,TO_DATE(?, 'mm/dd/yyyy'),?,?,?)";
			
			PreparedStatement pstm = conn.prepareStatement(sql);
			pstm.setInt(1, emp.getEmployeeId());
			pstm.setInt(2, newRequest.getAppliedreimbursement());
			pstm.setString(3, LocalDateTime.now().format(dateFormatter).toString());
			pstm.setDate(4, newRequest.getDEventDate());
			pstm.setInt(5, emp.getReportsTo());
			pstm.setInt(6, newRequest.getEventType());	
			
			pstm.executeQuery();
			
		} catch (SQLException e) {
			LogUtil.rollback(e, conn, RequestOracle.class);
		} finally {
			try {
				conn.close();
			} catch (SQLException e1) {
				LogUtil.logException(e1, RequestOracle.class);
			}
		}
		return key;	
	}

	@Override
	public void requestFurtherComments(int requestId) {
		// TODO Auto-generated method stub
		
	}
}
