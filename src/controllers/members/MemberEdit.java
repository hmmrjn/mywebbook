package controllers.members;

import java.io.IOException;
import java.text.ParseException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controllers.Controller;
import exceptions.NoResultException;
import models.bean.Member;
import models.dao.MemberDao;

/**
 * Servlet implementation class Edit
 */
@WebServlet("/members/edit")
public class MemberEdit extends Controller {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			int id = Integer.parseInt(request.getParameter("id"));
			MemberDao memberDao = new MemberDao();
			Member member = memberDao.findById(id);
			request.setAttribute("member", member);
			request.getRequestDispatcher("/members/edit.jsp").forward(request, response);
		} catch (NumberFormatException | NoResultException e) {
			HttpSession session = request.getSession();
			session.setAttribute("message", "無効なIDです。");
			response.sendRedirect("/mywebbook/");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		MemberDao memberDao = new MemberDao();
		try {
			Member member = memberDao.buildMember(request);
			memberDao.update(member);
			response.sendRedirect("/mywebbook/members");
		} catch (ParseException e) {
			e.printStackTrace();
			request.setAttribute("message", "日付が不正です。");
			request.getRequestDispatcher("/mywebbook/members/edit?id=" + request.getParameter("id"));
		}
	}

}
