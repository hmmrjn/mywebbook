package models.logic;

import java.util.Calendar;
import java.util.Date;

import exceptions.BadParamsException;
import exceptions.NoResultException;
import models.bean.Rental;
import models.dao.BookDao;
import models.dao.MemberDao;
import models.dao.RentalDao;

public class RentalLogic {
	MemberDao memberDao = new MemberDao();
	BookDao bookDao = new BookDao();
	RentalDao rentalDao = new RentalDao();

	public void create(int memberId, int bookCopyId) throws BadParamsException {
		// 1. その memberId, bookCopyId は存在するか?
		try {
			memberDao.findById(memberId);
			bookDao.findBookCopyById(bookCopyId);
		} catch (NoResultException e) {
			throw new BadParamsException();
		}
		// 2. その図書コピーは借りられていないか?
		if (rentedNow(bookCopyId)) {
			throw new BadParamsException();
		}
		// 3. 借出しを記録
		Calendar cal = Calendar.getInstance();
		Date now = cal.getTime();
		cal.add(Calendar.DATE, 15); //TODO ! 返却期日は何時?
		// TODO !!! 図書の発売日による返却期日の判断
		Date then = cal.getTime();
		Rental rental = new Rental();
		rental.setBookCopyId(bookCopyId);
		rental.setMemberId(memberId);
		rental.setRentedAt(now);
		rental.setReturnBy(then);
		rentalDao.create(rental);
	}

	/**
	 * その図書のコピーは今借りられているか?
	 * @param bookCopyId
	 * @return 借りられていればtrue
	 * @throws BadParamsException
	 */
	private boolean rentedNow(int bookCopyId) {
		try {
			Rental rental = rentalDao.findByBookCopyId(bookCopyId);
			return (rental.getReturnedAt() == null);
		} catch (NoResultException e) {
			return false;
		}

	}

}
