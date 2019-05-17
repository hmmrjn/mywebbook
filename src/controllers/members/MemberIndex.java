package controllers.members;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controllers.Controller;
import models.bean.Member;
import models.dao.MemberDao;

@WebServlet("/members")
public class MemberIndex extends Controller {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		MemberDao memberDao = new MemberDao();
		List<Member> members = memberDao.findAll();
		request.setAttribute("members", members);
		request.getRequestDispatcher("/members/index.jsp").forward(request, response);
	}

}
