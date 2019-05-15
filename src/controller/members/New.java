package controller.members;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.bean.Member;
import models.dao.MemberDao;

/**
 * Servlet implementation class New
 */
@WebServlet("/members/new")
public class New extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher("/members/new.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Member member;
		try {
			MemberDao memberDao = new MemberDao();
			member = memberDao.buildMember(request);
			memberDao.create(member);
			response.sendRedirect("/mywebbook/members");
		} catch (SQLException e) {
			e.printStackTrace();
			request.setAttribute("message", "エラーが発生しました。");
			request.getRequestDispatcher("/mywebbook/members/new");
		} catch (ParseException e) {
			e.printStackTrace();
			request.setAttribute("message", "日付が不正です。");
			request.getRequestDispatcher("/mywebbook/members/new");
		}
	}

}
