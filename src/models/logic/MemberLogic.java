package models.logic;

import java.util.Date;

import exceptions.BadParamsException;
import exceptions.NoResultException;
import exceptions.ValidationException;
import models.bean.Member;
import models.dao.MemberDao;
import models.dao.RentalDao;

public class MemberLogic {
	MemberDao memberDao = new MemberDao();
	RentalDao rentalDao = new RentalDao();

	public void unsubscribe(int id) throws BadParamsException, ValidationException {
		try {
			// memberId は 存在するか?
			Member member = memberDao.findById(id);
			if(member.getUnsubscribedAt() != null) {
				throw new BadParamsException("会員ID(" + id + ")はすでに退会しています。");
			}
			try {
				// 未返却の図書はないか?
				rentalDao.findNotReturnedByMemberId(id);
			} catch (NoResultException e) {
				member.setUnsubscribedAt(new Date());
				memberDao.update(member);
				return;
			}
			throw new ValidationException("この会員は未返却の図書があるので退会できません。");
		} catch (NoResultException e1) {
			throw new BadParamsException("会員ID(" + id + ")は存在しません。");
		}

	}
}
