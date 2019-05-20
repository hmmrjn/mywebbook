package controllers.members;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.bean.Member;
import models.dao.MemberDao;

@WebServlet("/members/search")
public class MembersSearch extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String email = request.getParameter("email");
		MemberDao memberDao = new MemberDao();
		List<Member> members = memberDao.findByEmailLike(email);
		request.setAttribute("members", members);
		request.getRequestDispatcher("/members/index.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
