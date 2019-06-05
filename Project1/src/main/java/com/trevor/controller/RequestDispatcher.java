package com.trevor.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.trevor.delegates.DelegateFactory;
import com.trevor.delegates.FrontControllerDelegate;

public class RequestDispatcher {
	private DelegateFactory df = DelegateFactory.getInstance();
	private Logger log = Logger.getLogger(RequestDispatcher.class);
	
	public FrontControllerDelegate dispatch(HttpServletRequest req, HttpServletResponse resp)
		throws IOException, ServletException {
		log.trace("Calling our requestDelegate: "+req.getRequestURI());
		addCorsHeader(req.getRequestURI(), resp);
		if("OPTIONS".equals(req.getMethod())) {
			return (r1, r2) -> {
				// empty processor
			};
		}
		// Input: "BookStore/books"
		// Input: "BookStore/books/1"
		// Input: "BookStore/books/"
		StringBuilder switchString = new StringBuilder(req.getRequestURI());
		// remove the context path (BookStore/) from the URI
		switchString.replace(0, req.getContextPath().length()+1, "");
		/*
		 * "books"
		 * "books/1"
		 * "books/"
		 */
		// Remove the first part fo the string ending with /
		if(switchString.indexOf("/")!=-1) {
			// save the remnants of the string for later use in the delegates
			req.setAttribute("path", switchString.substring(switchString.indexOf("/")+1));
			switchString.replace(switchString.indexOf("/"), switchString.length(),"");
		}
		
		return df.getDelegate(switchString.toString());
	}
	
	private void addCorsHeader(String requestURI, HttpServletResponse resp) {
		log.trace("adding headers");
		resp.addHeader("Access-Control-Allow-Origin", "http://localhost:4200");
		resp.addHeader("Vary", "Origin");
		//if I don't care about getting my cookie, this will work
		//response.addHeader("Access-Control-Allow-Origin", "*");
        resp.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT, DELETE, HEAD");
        resp.addHeader("Access-Control-Allow-Headers", "X-PINGOTHER, Origin, X-Requested-With, Content-Type, Accept");
        resp.addHeader("Access-Control-Allow-Credentials", "true");
        resp.addHeader("Access-Control-Max-Age", "1728000");
        resp.addHeader("Produces", "application/json");
	}
}
