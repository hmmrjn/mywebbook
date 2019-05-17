package models.bean;

import java.util.Date;

public class Rental {
	private int id;
	private BookCopy bookCopy;
	private Member member;
	private Date rentedAt;
	private Date returnBy;
	private Date returnedAt;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public BookCopy getBookCopy() {
		return bookCopy;
	}

	public void setBookCopy(BookCopy bookCopy) {
		this.bookCopy = bookCopy;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public Date getRentedAt() {
		return rentedAt;
	}

	public void setRentedAt(Date rentedAt) {
		this.rentedAt = rentedAt;
	}

	public Date getReturnBy() {
		return returnBy;
	}

	public void setReturnBy(Date returnBy) {
		this.returnBy = returnBy;
	}

	public Date getReturnedAt() {
		return returnedAt;
	}

	public void setReturnedAt(Date returnedAt) {
		this.returnedAt = returnedAt;
	}
}
