package controllers.bookcopies;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controllers.Controller;
import exceptions.ValidationException;
import models.logic.BookCopyLogic;

@WebServlet("/bookcopies/new")
public class BookCopiesNew extends Controller {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String isbn = request.getParameter("isbn");
		HttpSession session = request.getSession();
		BookCopyLogic bookCopyLogic = new BookCopyLogic();
		try {
			bookCopyLogic.add(isbn);
			session.setAttribute("message", "資料を新規追加しました。");
			response.sendRedirect("/mywebbook/books/show?isbn=" + isbn);
		} catch (ValidationException e) {
			session.setAttribute("message", e.getMessage());
			response.sendRedirect("/mywebbook/books");
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
