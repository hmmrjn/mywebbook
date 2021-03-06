package controllers.books;

import java.io.IOException;
import java.text.ParseException;

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
			request.setAttribute("book", book);
			setCategoriesAndPublishers(request, response);
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
		HttpSession session = request.getSession();
		try {
			Book book = bookDao.buildBookWithoutCopies(request);
			bookDao.update(book);
			session.setAttribute("message", "図書を更新しました。");
			response.sendRedirect("/mywebbook/books/show?isbn=" + book.getIsbn());
		} catch (NoResultException e) {
			session.setAttribute("message", "不正なIDです。");
			setCategoriesAndPublishers(request, response);
			request.getRequestDispatcher("/books/edit.jsp").forward(request, response);
		} catch (ParseException e) {
			session.setAttribute("message", "不正な日付です。");
			setCategoriesAndPublishers(request, response);
			request.getRequestDispatcher("/books/new.jsp").forward(request, response);
		}
	}

}
