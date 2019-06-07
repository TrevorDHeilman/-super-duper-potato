package com.trevor.data;

import java.util.Set;

import com.trevor.beans.Comment;
import com.trevor.beans.Employee;

public interface CommentDAO {
	
	Set<Comment> getRequests(int requestid);
	Set<Comment> getRequests(Employee emp);
}
