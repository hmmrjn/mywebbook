package controllers.books;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import models.bean.Book;
import models.dao.BookDao;

@WebServlet("/books/edit")
public class BooksEdit extends HttpServlet {

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
		} catch (SQLException e) {
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
		try {
			bookDao.update(book);
			session.setAttribute("message", "図書を更新しました。");
			response.sendRedirect("/mywebbook/books/show?isbn=" + book.getIsbn());
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				List<String> categories = bookDao.findCategories();
				List<String> publishers = bookDao.findPublishers();
				request.setAttribute("book", book);
				request.setAttribute("categories", categories);
				request.setAttribute("publishers", publishers);
				session.setAttribute("message", "更新できませんでした。☆");
				request.getRequestDispatcher("/books/edit.jsp").forward(request, response);
			} catch (SQLException e1) {
				System.out.println("カテゴリーと出版社を取得できませんでした。");
				e1.printStackTrace();
				session.setAttribute("message", "内部エラーが発生しました。");
				response.sendRedirect("/mywebbook/books");
			}
		}
	}

}
