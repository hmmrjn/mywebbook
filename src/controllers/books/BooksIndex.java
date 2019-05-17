package controllers.books;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controllers.Controller;
import models.bean.Book;
import models.dao.BookDao;

/**
 * Servlet implementation class BooksIndex
 */
@WebServlet("/books")
public class BooksIndex extends Controller {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		BookDao bookDao = new BookDao();
		List<Book> books = bookDao.findAll();
		request.setAttribute("books", books);
		request.getRequestDispatcher("/books/index.jsp").forward(request, response);
	}

}
