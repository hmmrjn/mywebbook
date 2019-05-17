package controllers.books;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controllers.Controller;
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
		Book book = bookDao.buildBookWithoutCopies(request);
		int copiesNum = Integer.parseInt(request.getParameter("copiesNum"));
		HttpSession session = request.getSession();
		try {
			bookDao.create(book, copiesNum);
			session.setAttribute("message", "図書を登録しました。");
			response.sendRedirect("/mywebbook/books/show?isbn=" + book.getIsbn());
		} catch (SQLException e) {
			session.setAttribute("message", "登録できませんでした。☆");
			e.printStackTrace();
			setCategoriesAndPublishers(request, response);
			request.getRequestDispatcher("/books/edit.jsp").forward(request, response);
		}
	}

	private void setCategoriesAndPublishers(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		HttpSession session = request.getSession();
		try {
			BookDao bookDao = new BookDao();
			List<String> categories = bookDao.findCategories();
			List<String> publishers = bookDao.findPublishers();
			request.setAttribute("categories", categories);
			request.setAttribute("publishers", publishers);
		} catch (SQLException e) {
			System.out.println("カテゴリーと出版社を取得できませんでした。");
			e.printStackTrace();
			session.setAttribute("message", "内部エラーが発生しました。");
			response.sendRedirect("/mywebbook/books");
		}
	}

}
