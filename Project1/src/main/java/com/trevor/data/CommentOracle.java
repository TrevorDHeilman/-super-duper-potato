package com.trevor.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashSet;
import java.util.Set;
import org.apache.log4j.Logger;
import com.trevor.beans.Comment;
import com.trevor.beans.Employee;
import com.trevor.utils.ConnectionUtil;
import com.trevor.utils.LogUtil;

public class CommentOracle implements CommentDAO {

	private static Logger log = Logger.getLogger(RequestOracle.class);
	private static ConnectionUtil cu = ConnectionUtil.getConnectionUtil();	
	
	@Override
	public Set<Comment> getRequests(Employee emp) {
		Set<Comment> requestList = new HashSet<Comment>();
		log.trace("retrieving all requests from database.");
		try (Connection conn = cu.getConnection()) {
			String sql = "select * from requeststory where employeecomment=?";
			PreparedStatement pstm = conn.prepareStatement(sql);
			pstm.setInt(1, emp.getEmployeeId());
			ResultSet rs = pstm.executeQuery();
					
			while (rs.next()) {
				
				Comment comment = new Comment();
				comment.setRequestId(rs.getInt("requestid"));
				comment.setEmployeeId(rs.getInt("employeeid"));
				comment.setAction(rs.getString("action"));
				comment.setEmployeeComment(rs.getString("employeecomment"));

				log.trace("Adding Request to the list");
				requestList.add(comment);
			}
		} catch (Exception e) {
			LogUtil.logException(e, RequestOracle.class);
		}
		log.trace(requestList);
		return requestList;
	}

}
