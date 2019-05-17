package models.logic;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import exceptions.BadParamsException;
import exceptions.NoResultException;
import models.bean.BookCopy;
import models.bean.Member;
import models.bean.Rental;
import models.dao.BookCopyDao;
import models.dao.MemberDao;
import models.dao.RentalDao;

public class RentalLogic {
	MemberDao memberDao = new MemberDao();
	BookCopyDao bookCopyDao = new BookCopyDao();
	RentalDao rentalDao = new RentalDao();

	public void create(int memberId, int bookCopyId) throws BadParamsException {
		if (rentedNow(bookCopyId)) {
			throw new BadParamsException();
		}
		Rental rental = new Rental();
		try {
			BookCopy bookCopy = bookCopyDao.findById(bookCopyId);
			Member member = memberDao.findById(memberId);
			rental.setBookCopy(bookCopy);
			rental.setMember(member);
		} catch (NoResultException e) {
			throw new BadParamsException();
		}
		rental.setRentedAt(new Date());
		rental.setReturnBy(todayPlusDays(15));
		rentalDao.create(rental);
	}

	/**
	 * 問題がなければ返却を記録します。
	 * @param bookCopyId
	 * @throws BadParamsException
	 */
	public void setReturned(int bookCopyId) throws BadParamsException {
		Rental rental;
		try {
			rental = rentalDao.findNotReturnedByBookCopyId(bookCopyId);
			rental.setReturnedAt(new Date());
			rentalDao.update(rental);
		} catch (NoResultException e) {
			throw new BadParamsException();
		}
	}

	/**
	 * その図書のコピーは今借りられているか?
	 * @param bookCopyId
	 * @return 借りられていればtrue
	 * @throws BadParamsException
	 */
	private boolean rentedNow(int bookCopyId) {
		List<Rental> rentals = rentalDao.findByBookCopyId(bookCopyId);
		for (Rental rental : rentals) {
			if (rental.getReturnedAt() == null) {
				return true;
			}
		}
		return false;
	}

	/**
	 * n日後のDateを返す
	 * @param days
	 * @return
	 */
	private Date todayPlusDays(int days) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, 15); //TODO ! 返却期日は何時?
		// TODO !!! 図書の発売日による返却期日の判断
		return cal.getTime();
	}

}
