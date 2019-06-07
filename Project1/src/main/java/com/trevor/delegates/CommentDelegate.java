package com.trevor.delegates;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trevor.beans.Employee;
import com.trevor.beans.Request;
import com.trevor.data.RequestOracle;

public class CommentDelegate implements FrontControllerDelegate {

	private Logger log = Logger.getLogger(Request.class);
	private ObjectMapper om = new ObjectMapper();
	private RequestOracle ro = new RequestOracle();
	
	@Override
	public void process(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		log.trace(req.getMethod() + " received by login delegate");
		HttpSession session = req.getSession();
		Employee emp = (Employee) session.getAttribute("loggedEmployee");
		PrintWriter writer = resp.getWriter();
		Request newRequest;
		switch (req.getMethod()) {
		case "GET":
			log.trace("GET recieved by comment delegate");
			//newRequest = ro.getRequestById(requestId);
			//resp.getWriter().write(om.writeValueAsString(newRequest));
			
			break;
		case "POST":
			// logging in
			log.trace("POST recieved by comment delegate");
			//Employee emp = (Employee) session.getAttribute("loggedEmployee");

			break;
		case "DELETE":
			// logging out
			log.trace("DELETE recieved by comment delegate");
			session.invalidate();
			// dissociate an id with a session and response says to delete cookie
			log.trace("User logged out");
			break;
		default:
			break;
		}
	}

}
