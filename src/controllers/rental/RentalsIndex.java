package controllers.rental;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controllers.Controller;
import models.bean.Rental;
import models.dao.RentalDao;

@WebServlet("/rentals")
public class RentalsIndex extends Controller {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RentalDao rentalDao = new RentalDao();
		List<Rental> rentals = rentalDao.findAll();

		List<Rental> rentalsNotReturned = new ArrayList<Rental>();
		List<Rental> rentalsReturned = new ArrayList<Rental>();
		for (Rental rental : rentals) {
			if (rental.getReturnedAt() == null)
				rentalsNotReturned.add(rental);
			else
				rentalsReturned.add(rental);
		}
		request.setAttribute("rentalsNotReturned", rentalsNotReturned);
		request.setAttribute("rentalsReturned", rentalsReturned);
		request.getRequestDispatcher("/rentals/index.jsp").forward(request, response);
	}

}
