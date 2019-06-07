package com.trevor.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
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
			String sql = "select employeeid, appliedreimbursement, requestdate, eventdate, statusid, eventtype from request where requestid=?";

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
		ArrayList<Integer> bencoKeys = new ArrayList<>(Arrays.asList(3, 6));
		log.trace("retrieving all requests from database.");
		try (Connection conn = cu.getConnection()) {
			String sql = "select * from request where statusid=?";
			PreparedStatement pstm = conn.prepareStatement(sql);
			log.trace("bencoKeys.contains(emp.getTitleId())" + bencoKeys.contains(emp.getTitleId()));
			if(bencoKeys.contains(emp.getTitleId())) {
				pstm.setInt(1, 4);
				ResultSet rs = pstm.executeQuery();
				//printResultSet(rs);
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
			}
			
			pstm.setInt(1, emp.getEmployeeId());
			
			ResultSet rs = pstm.executeQuery();
			//printResultSet(rs);
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
	public void updateRequest(int requestid, Employee emp, boolean newStatus) {
		Connection conn = cu.getConnection();
		String booleanStr;
		ArrayList<Integer> deptHeadKeys = new ArrayList<>(Arrays.asList(101, 102, 103));
		Request request = getRequestById(requestid);
		log.trace("updating request in database: " + request);

		try {
			conn.setAutoCommit(false);
			String sql = "update request set statusid=? where requestid=?";
			PreparedStatement pstm = conn.prepareStatement(sql);
			if(newStatus) {
				
				booleanStr = "Approved";
				String sqlQuery = "select * from employee where employeeid=?";
				PreparedStatement pstmQuery = conn.prepareStatement(sqlQuery);
				pstmQuery.setInt(1, request.getEmployeeId());
				ResultSet rs = pstmQuery.executeQuery();
				if (rs.next()) {
					
					if("4".equals(request.getStatus())) {
					
						pstm.setInt(1,2);
					}
					else if(emp.getEmployeeId() == rs.getInt("reportsto") && !deptHeadKeys.contains(emp.getReportsTo())){
						
						pstm.setInt(1, emp.getReportsTo());
					}
					else if(deptHeadKeys.contains(emp.getReportsTo())) {																					
						
						pstm.setInt(1, 4);	
					}
				}
			}
			else {
				booleanStr = "Declined";
				pstm.setInt(1, 3);
			}
			
			pstm.setInt(2,request.getRequestId());
			int result = pstm.executeUpdate();

			if (result == 1) {
				log.trace("Request updated");
				
				String setRequestStory = "Insert into RequestStory (RequestId, EmployeeId, Action) VALUES (?, ?, ?)";
				PreparedStatement ptsmRequestStory = conn.prepareStatement(setRequestStory);
				ptsmRequestStory.setInt(1, request.getRequestId());
				ptsmRequestStory.setInt(2, emp.getEmployeeId());
				ptsmRequestStory.setString(3, booleanStr);
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
	public void requestFurtherComments(int requestId, int sendRequestTo, Employee emp) {
		
		log.trace("Adding comment to the request.");
		try (Connection conn = cu.getConnection()) {
			String sql = "update requeststory set employeecomment = ?, action=? where requestid = ? and employeeid = (select employeeid from requeststory where employeeid = ? and requestid = ?)";
			PreparedStatement pstm = conn.prepareStatement(sql);
			pstm.setString(1, Integer.toString(sendRequestTo));
			pstm.setString(2, "Request Comment");
			pstm.setInt(3, requestId);
			pstm.setInt(4, emp.getEmployeeId());
			pstm.setInt(5, requestId);
			ResultSet rs = pstm.executeQuery();
		}
		catch (Exception e) {
			LogUtil.logException(e, RequestOracle.class);
		}	
	}
	
	@Override
	public void saveComment(int requestId, String comment, Employee emp) {
		
		log.trace("Adding comment to the request.");
		try (Connection conn = cu.getConnection()) {
			String sql = "update requeststory set employeecomment = ? where requestid = ? and employeeid = (select employeeid from requeststory where employeecomment = ? and requestid = ?)";
			PreparedStatement pstm = conn.prepareStatement(sql);
			pstm.setString(1, comment);
			pstm.setInt(2, requestId);
			pstm.setString(3, Integer.toString(emp.getEmployeeId()));
			pstm.setInt(4, requestId);
			ResultSet rs = pstm.executeQuery();
		}
		catch (Exception e) {
			LogUtil.logException(e, RequestOracle.class);
		}	
	}
		
	@Override
	public void printResultSet(ResultSet rs) throws SQLException {
		   ResultSetMetaData rsmd = rs.getMetaData();
		   System.out.println("querying SELECT * FROM XXX");
		   int columnsNumber = rsmd.getColumnCount();
		   while (rs.next()) {
		       for (int i = 1; i <= columnsNumber; i++) {
		           if (i > 1) System.out.print(",  ");
		           String columnValue = rs.getString(i);
		           System.out.print(columnValue + " " + rsmd.getColumnName(i));
		       }
		       System.out.println("");
		   }
	}
}
