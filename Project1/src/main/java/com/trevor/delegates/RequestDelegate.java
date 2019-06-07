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
import com.trevor.beans.Employee;
import com.trevor.beans.Request;
import com.trevor.utils.JsonParseUtil;
import com.trevor.utils.LogUtil;
import com.trevor.data.RequestOracle;

public class RequestDelegate implements FrontControllerDelegate {
	private Logger log = Logger.getLogger(Request.class);
	private ObjectMapper om = new ObjectMapper();
	private RequestOracle ro = new RequestOracle();
	
	@Override
	public void process(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		String path = (String) req.getAttribute("path");
		log.trace("path: "+path);
		if(path==null||"".equals(path))
			collectionOperations(req,resp);
		else {
			try {
				requestTimes(req,resp,Integer.parseInt(path.toString()));
			} catch(NumberFormatException e) {
				resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
			}
		}
			
	}

	private void requestTimes(HttpServletRequest req, HttpServletResponse resp, int requestId) throws IOException {
		HttpSession session = req.getSession();
		Employee emp = (Employee) session.getAttribute("loggedEmployee");
		log.trace("Operating on a specific request with id: "+ requestId);
		PrintWriter writer = resp.getWriter();
		Request newRequest;
		switch(req.getMethod()) {
		case "GET":
			newRequest = ro.getRequestById(requestId);
			resp.getWriter().write(om.writeValueAsString(newRequest));
			return;
		case "PUT":
			// Update the request in the database
			//newRequest = JsonParseUtil.parseJsonInput(req.getInputStream(), Request.class, resp);
			//ro.updateRequest(newRequest, emp, true);
			//writer.write(om.writeValueAsString(newRequest));
			return;
		default:
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
		}
	}

	private void collectionOperations(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		HttpSession session = req.getSession();
		Employee emp = (Employee) session.getAttribute("loggedEmployee");
		switch(req.getMethod()) {
		case "GET": 
//			log.trace("Retrieving all requests from the database");
//			Set<Request> requests = ro.getRequests(emp);
//			resp.getWriter().write(om.writeValueAsString(requests));
//			return;
		case "POST":
			String switchVar = req.getParameter("type");
			log.trace("switchVar " + switchVar);
			if(switchVar==null) {
				log.trace("Post called with requests");
				Request newRequest = JsonParseUtil.parseJsonInput(req.getInputStream(), Request.class, resp);
				log.trace(newRequest);
				log.trace(newRequest==null);
				if(newRequest==null)
					return;
				try {
					// Add the book to the database
					log.trace("Adding request to the database: "+newRequest);
					ro.addRequest(newRequest, emp);
					resp.setStatus(HttpServletResponse.SC_CREATED);
					
					//resp.getWriter().write(om.writeValueAsString(newRequest));
				} catch(Exception e) {
					LogUtil.logException(e, RequestDelegate.class);
					resp.sendError(HttpServletResponse.SC_CONFLICT);
				}
				return;
				
			}
			else if("0".equals(switchVar)) {
				
				log.trace("Retrieving all requests from the database");
				Set<Request> requests = ro.getRequests(emp);
				log.trace("Request = null:" + requests==null);
				resp.getWriter().write(om.writeValueAsString(requests));
				return;
			}
			else if ("1".equals(switchVar)) {
				
				log.trace("Retrieving all requests from the database");
				log.trace("switchvar EMP" + emp);
				Set<Request> requests = ro.getRequests2(emp);
				log.trace("Request = null:" + requests==null);
				resp.getWriter().write(om.writeValueAsString(requests));
				return;
			}
			else if ("4".equals(switchVar)) {
				// Update the request in the database
				//log.trace(req.getParameter("requestid"));
				int requestId = Integer.parseInt(req.getParameter("requestid"));
				String eventType = req.getParameter("eventtype");
				//log.trace("requestId" + requestId);
				//log.trace("eventType" + eventType);
				switch(eventType){
					case "Accept":
						ro.updateRequest(requestId, emp, true);
						break;
					case "Decline":
						ro.updateRequest(requestId, emp, false);
						break;
					case "Request Comment":
						
						break;
				}
			
				return;
			}
			
			else if("7".equals(switchVar)) {
				int requestId = Integer.parseInt(req.getParameter("requestid"));
				String comment = req.getParameter("comment");
			}
		case "PUT":

		default: resp.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
		}
	}
}

