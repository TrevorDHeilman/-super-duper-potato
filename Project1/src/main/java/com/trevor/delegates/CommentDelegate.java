package com.trevor.delegates;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.trevor.beans.Comment;
import com.trevor.beans.Employee;
import com.trevor.beans.Request;
import com.trevor.data.CommentOracle;
import com.trevor.data.RequestOracle;

public class CommentDelegate implements FrontControllerDelegate {

	private Logger log = Logger.getLogger(Request.class);
	private ObjectMapper om = new ObjectMapper();
	private CommentOracle co = new CommentOracle();
	private RequestOracle ro = new RequestOracle();
	
	@Override
	public void process(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		log.trace(req.getMethod() + " received by login delegate");
		String path = (String) req.getAttribute("path");
		HttpSession session = req.getSession();
		Employee emp = (Employee) session.getAttribute("loggedEmployee");
		PrintWriter writer = resp.getWriter();
		Request newRequest;
		switch (req.getMethod()) {
		case "POST":
			log.trace("POST recieved by comment delegate");
			String switchVar = req.getParameter("type");
			log.trace("switchVar " + switchVar);
			if("1".equals(switchVar)) {
			
				newRequest = ro.getRequestById(Integer.parseInt(req.getParameter("requestid")));
				log.trace("Request = null:" + newRequest==null);
				resp.getWriter().write(om.writeValueAsString(newRequest));
				return;
			}
			else if("2".equals(switchVar)) {
				
				Set<Comment> newComment = co.getRequests(Integer.parseInt(req.getParameter("requestid")));
				log.trace("Request = null:" + newComment==null);
				resp.getWriter().write(om.writeValueAsString(newComment));
				return;
			}
			else if("3".equals(switchVar)) {
				
				String newRequestid = req.getParameter("requestid");
				String selectedEmployee = req.getParameter("selectedEmployee");
				ro.requestFurtherComments(Integer.parseInt(newRequestid), Integer.parseInt(selectedEmployee), emp);
				return;
			}
			break;

		default:
			break;
		}
	}

}
