package controllers.bookcopies;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controllers.Controller;
import exceptions.NoResultException;
import exceptions.ValidationException;
import models.bean.BookCopy;
import models.dao.BookCopyDao;
import models.logic.BookCopyLogic;

@WebServlet("/bookcopies/discard")
public class BookCopiesDiscard extends Controller {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		int id = Integer.parseInt(request.getParameter("id"));
		BookCopyDao bookCopyDao = new BookCopyDao();
		try {
			BookCopy bookCopy = bookCopyDao.findById(id);
			String isbn = bookCopy.getIsbn();
			BookCopyLogic bookCopyLogic = new BookCopyLogic();
			try {
				bookCopyLogic.discard(id);
				session.setAttribute("message", "資料ID(" + id + ")を破棄しました。");
			} catch (ValidationException e) {
				session.setAttribute("message", e.getMessage());
			} finally {
				response.sendRedirect("/mywebbook/books/show?isbn=" + isbn);
			}
		} catch (NoResultException e1) {
			session.setAttribute("message", "無効な資料IDです。");
			response.sendRedirect("/mywebbook/books");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
