package controllers.books;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controllers.Controller;
import exceptions.NoResultException;
import models.bean.Book;
import models.dao.BookDao;

@WebServlet("/books/edit")
public class BooksEdit extends Controller {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String isbn = request.getParameter("isbn");
		BookDao bookDao = new BookDao();
		try {
			Book book = bookDao.findByIsbn(isbn);
			List<String> categories = bookDao.findCategories();
			List<String> publishers = bookDao.findPublishers();
			request.setAttribute("book", book);
			request.setAttribute("categories", categories);
			request.setAttribute("publishers", publishers);
			request.getRequestDispatcher("/books/edit.jsp").forward(request, response);
			// TODO jsp 脱selectbox化
		} catch (NoResultException e) {
			HttpSession session = request.getSession();
			session.setAttribute("message", "そんな図書はありません。");
			response.sendRedirect("/mywebbook/books");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		BookDao bookDao = new BookDao();
		Book book = bookDao.buildBookWithoutCopies(request);
		HttpSession session = request.getSession();
		bookDao.update(book);
		session.setAttribute("message", "図書を更新しました。");
		response.sendRedirect("/mywebbook/books/show?isbn=" + book.getIsbn());
	}

}
