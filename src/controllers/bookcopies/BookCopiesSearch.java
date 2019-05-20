package controllers.bookcopies;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controllers.Controller;
import exceptions.NoResultException;
import models.bean.BookCopy;
import models.dao.BookCopyDao;

@WebServlet("/bookcopies/search")
public class BookCopiesSearch extends Controller {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		BookCopyDao bookCopyDao = new BookCopyDao();
		try {
			int id = Integer.parseInt(request.getParameter("id"));
			BookCopy bookCopy = bookCopyDao.findById(id);
			response.sendRedirect("/mywebbook/books/show?isbn=" + bookCopy.getIsbn() + "&highlightId=" + bookCopy.getId());
		} catch (NoResultException e) {
			session.setAttribute("message", "存在しない資料IDです。");
			response.sendRedirect("/mywebbook/books");
		} catch (NumberFormatException e) {
			session.setAttribute("message", "資料IDは数字で入力してください。");
			response.sendRedirect("/mywebbook/books");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
