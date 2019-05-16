package controllers.books;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import models.bean.Book;
import models.dao.BookDao;

/**
 * Servlet implementation class BooksShow
 */
@WebServlet("/books/show")
public class BooksShow extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String isbn = request.getParameter("isbn");
		BookDao bookDao = new BookDao();
		try {
			Book book = bookDao.findByIsbn(isbn);
			request.setAttribute("book", book);
			request.getRequestDispatcher("/books/show.jsp").forward(request, response);;
		} catch (SQLException e) {
			e.printStackTrace();
			HttpSession session = request.getSession();
			session.setAttribute("message", "そんな図書はございません。");
			response.sendRedirect("/mywebbook/books");
		}

	}

}
