package models.logic;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import exceptions.BadParamsException;
import exceptions.NoResultException;
import exceptions.ValidationException;
import models.bean.Book;
import models.bean.BookCopy;
import models.bean.Member;
import models.bean.Rental;
import models.dao.BookCopyDao;
import models.dao.BookDao;
import models.dao.MemberDao;
import models.dao.RentalDao;

public class RentalLogic {
	MemberDao memberDao = new MemberDao();
	BookCopyDao bookCopyDao = new BookCopyDao();
	BookDao bookDao = new BookDao();
	RentalDao rentalDao = new RentalDao();

	public void rent(int memberId, int bookCopyId) throws ValidationException {
		if (isRentedNow(bookCopyId)) {
			throw new ValidationException("この資料はただいま借りられています。");
		}
		Rental rental = new Rental();
		try {
			BookCopy bookCopy = bookCopyDao.findById(bookCopyId);
			Member member = memberDao.findById(memberId);
			Book book = bookDao.findByIsbn(bookCopy.getIsbn());

			Date returnBy = todayPlusDays(15);
			int daysDiff = daysFromToday(book.getReleasedAt());
			if (daysDiff  < 30 * 3) {
				returnBy = todayPlusDays(10);
			}
			rental.setBookCopy(bookCopy);
			rental.setMember(member);
			rental.setRentedAt(new Date());
			rental.setReturnBy(returnBy);
			rentalDao.create(rental);
		} catch (NoResultException e) {
			throw new ValidationException("資料ID、または、会員IDが存在しません。");
		}

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
	private boolean isRentedNow(int bookCopyId) {
		List<Rental> rentals = rentalDao.findByBookCopyId(bookCopyId);
		for (Rental rental : rentals) {
			if (rental.getReturnedAt() == null) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 与えられた日付と今日の日数の差を返す
	 * @param date
	 * @return
	 */
	private int daysFromToday(Date date) {
		long nowL = new Date().getTime();
		long thenL = date.getTime();
		return (int) (nowL - thenL) / (1000 * 60 * 60 * 24);
	}

	/**
	 * n日後のDateを返す
	 * @param days
	 * @return
	 */
	private Date todayPlusDays(int days) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, days);
		return cal.getTime();
	}

}
