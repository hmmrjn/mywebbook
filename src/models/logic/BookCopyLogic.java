package models.logic;

import java.util.Date;

import exceptions.NoResultException;
import exceptions.ValidationException;
import models.bean.BookCopy;
import models.dao.BookCopyDao;
import models.dao.RentalDao;

public class BookCopyLogic {
	BookCopyDao bookCopyDao = new BookCopyDao();
	RentalDao rentalDao = new RentalDao();

	public void discard(int id) throws ValidationException {
		try {
			// その資料は存在するか?
			BookCopy bookCopy = bookCopyDao.findById(id);
			try {
				// その資料は借出されていないか?
				rentalDao.findNotReturnedByBookCopyId(id);
			} catch (NoResultException e) {
				bookCopy.setDiscardedAt(new Date());
				bookCopyDao.update(bookCopy);
				return;
			}
			throw new ValidationException("この資料は借りられているため破棄できません。");
		} catch (NoResultException e1) {
			throw new ValidationException("無効な資料IDです。");
		}

	}

}