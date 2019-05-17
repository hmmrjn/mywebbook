package controllers.rental;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import exceptions.BadParamsException;
import models.logic.RentalLogic;

@WebServlet("/rentals/new")
public class RentalsNew extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		try {
			int bookCopyId = Integer.parseInt(request.getParameter("bookCopyId"));
			int memberId = Integer.parseInt(request.getParameter("memberId"));
			RentalLogic rentalLogic = new RentalLogic();
			try {
				rentalLogic.create(memberId, bookCopyId);
				session.setAttribute("message", "借出しを登録しました。");
				response.sendRedirect("/mywebbook/");
			} catch (BadParamsException e) {
				session.setAttribute("message", "無効なIDです。");
				response.sendRedirect("/mywebbook/");
			}
		} catch (NumberFormatException e) {
			session.setAttribute("message", "数字で入力ください。");
			response.sendRedirect("/mywebbook/");
		}

	}

}
