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

@WebServlet("/books/new")
public class BooksNew extends Controller {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		setCategoriesAndPublishers(request, response);
		request.getRequestDispatcher("/books/new.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		BookDao bookDao = new BookDao();
		HttpSession session = request.getSession();
		try {
			//TODO IBSN は 重複していないか?
			Book book = bookDao.buildBookWithoutCopies(request);
			int copiesNum = Integer.parseInt(request.getParameter("copiesNum"));
			bookDao.create(book, copiesNum);
			session.setAttribute("message", "図書を登録しました。");
			response.sendRedirect("/mywebbook/books/show?isbn=" + book.getIsbn());
		} catch (NoResultException e) {
			session.setAttribute("message", "不正なIDです。");
			setCategoriesAndPublishers(request, response);
			request.getRequestDispatcher("/books/new.jsp").forward(request, response);
		} catch (ParseException e) {
			session.setAttribute("message", "不正な日付です。");
			setCategoriesAndPublishers(request, response);
			request.getRequestDispatcher("/books/new.jsp").forward(request, response);
		}
	}

}
