package controllers.members;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controllers.Controller;
import exceptions.BadParamsException;
import exceptions.MemberHasNotReturnedBooksException;
import models.logic.MemberLogic;

/**
 * Servlet implementation class MemberRetire
 */
@WebServlet("/members/unsubscribe")
public class MemberUnsubscribe extends Controller {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		int id = Integer.parseInt(request.getParameter("id"));
		MemberLogic memberLogic = new MemberLogic();
		try {
			memberLogic.unsubscribe(id);
			session.setAttribute("message", "退会させました。"); // TODO 退会取り消し。
		} catch (BadParamsException e) {
			session.setAttribute("message", e.getMessage());
		} catch (MemberHasNotReturnedBooksException e) {
			session.setAttribute("message", e.getMessage());
		} finally {
			response.sendRedirect("/mywebbook/members");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
