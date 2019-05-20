package models.logic;

import java.util.Date;
import java.util.List;

import exceptions.BadParamsException;
import exceptions.NoResultException;
import exceptions.ValidationException;
import models.bean.Member;
import models.bean.Rental;
import models.dao.MemberDao;
import models.dao.RentalDao;

public class MemberLogic {
	MemberDao memberDao = new MemberDao();
	RentalDao rentalDao = new RentalDao();

	public void unsubscribe(int id) throws BadParamsException, ValidationException {
		try {
			// memberId は 存在するか?
			Member member = memberDao.findById(id);
			if (member.getUnsubscribedAt() != null) {
				throw new BadParamsException("会員ID(" + id + ")はすでに退会しています。");
			}

			// 未返却の図書はないか?
			List<Rental> rentals = rentalDao.findNotReturnedByMemberId(id);
			if (rentals.size() > 0) {
				throw new ValidationException("この会員は未返却の図書があるので退会できません。");
			}

			// 借出しを記録
			member.setUnsubscribedAt(new Date());
			memberDao.update(member);
		} catch (NoResultException e1) {
			throw new BadParamsException("会員ID(" + id + ")は存在しません。");
		}

	}
}
