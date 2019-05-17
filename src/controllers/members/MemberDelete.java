package controllers.members;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controllers.Controller;
import exceptions.BadParamsException;
import models.dao.MemberDao;

/**
 * Servlet implementation class Delete
 */
@WebServlet("/members/delete")
public class MemberDelete extends Controller {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		MemberDao memberDao = new MemberDao();
		try {
			memberDao.delete(Integer.parseInt(request.getParameter("id")));
		} catch (NumberFormatException | BadParamsException e) {
			request.setAttribute("message", "削除できません。無効なIDです。");
		} finally {
			response.sendRedirect("/mywebbook/members");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
