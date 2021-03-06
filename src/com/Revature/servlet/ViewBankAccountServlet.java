package com.Revature.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.Revature.dao.BankAccountDAOimpl;
import com.Revature.dao.IncorrectLoginException;
import com.Revature.dao.UserDAOimpl;
import com.Revature.domain.BankAccount;
import com.Revature.domain.User;

public class ViewBankAccountServlet extends HttpServlet{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Integer acctID = Integer.parseInt(req.getParameter("pid"));
		BankAccountDAOimpl bankDAO = new BankAccountDAOimpl();
		UserDAOimpl userDAO = new UserDAOimpl();
		HttpSession session = req.getSession();
		User user = (User) session.getAttribute("user");
		BankAccount acct = bankDAO.retrieveAccountByID(acctID);
		if(acct.getUserID() == user.getUserID()){
			session.setAttribute("acct", acct);
			resp.sendRedirect("accountpage.jsp");
		}else{
			session.invalidate();
			req.setAttribute("message", "You are not allowed there");
			req.getRequestDispatcher("bankAfterLogin.jsp").forward(req, resp);
		}
	}
}
