package com.trevor.delegates;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.trevor.beans.Employee;
import com.trevor.data.EmployeeOracle;

public class LoginDelegate implements FrontControllerDelegate {
	private Logger log = Logger.getLogger(LoginDelegate.class);
	private EmployeeOracle eo = new EmployeeOracle();
	private ObjectMapper om = new ObjectMapper();

	@Override
	public void process(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		log.trace(req.getMethod() + " received by login delegate");
		HttpSession session = req.getSession();
		switch (req.getMethod()) {
		case "GET":
			log.trace("GET recieved by login delegate");
			checkLogin(req, resp);
			break;
		case "POST":
			// logging in
			log.trace("POST recieved by login delegate");
			Employee emp = (Employee) session.getAttribute("loggedEmployee");
			if (emp != null) {
				respondWithUser(resp, emp);
			} else {
				checkLogin(req, resp);
			}
			break;
		case "DELETE":
			// logging out
			log.trace("DELETE recieved by login delegate");
			session.invalidate();
			// dissociate an id with a session and response says to delete cookie
			log.trace("User logged out");
			break;
		default:
			break;
		}
	}

	private void checkLogin(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		log.trace("Logging in!");
		HttpSession session = req.getSession();
		Employee e = (Employee) session.getAttribute("loggedEmployee");
		System.out.println(e);
		if (e != null) {
			respondWithUser(resp, e);
		} else {

			String username = req.getParameter("user");
			String password = req.getParameter("pass");
			log.trace((username + " " + password));
			e = eo.getEmployee(username, password);

			if (e != null) {
				log.trace("employee being added to session");
				session.setAttribute("loggedEmployee", e);
				resp.getWriter().write("{\"sucess\" : true}");
			}
			else if (e == null) {
				resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "No user found with those credentials");
			}
		}
	}

	private void respondWithUser(HttpServletResponse resp, Employee emp) throws IOException {
		resp.setStatus(HttpServletResponse.SC_OK);
		String e = om.writeValueAsString(emp);
		StringBuilder sb = new StringBuilder("{\"employee\":");
		sb.append(e);
		sb.append("}");
		resp.getWriter().write(sb.toString());
	}

}
