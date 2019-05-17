package controllers.rental;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controllers.Controller;
import exceptions.BadParamsException;
import models.logic.RentalLogic;

@WebServlet("/rentals/set_returned")
public class RentalsReturn extends Controller {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		try {
			int bookCopyId = Integer.parseInt(request.getParameter("bookCopyId"));
			session.setAttribute("message", "返却を記録しました。");
			RentalLogic rentalLogic = new RentalLogic();
			rentalLogic.setReturned(bookCopyId);
			response.sendRedirect("/mywebbook/");
		} catch (NumberFormatException | BadParamsException e) {
			session.setAttribute("message", "不正なIDです。");
			response.sendRedirect("/mywebbook/");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
