package models.bean;

import java.util.Date;

public class Rental {
	private int id;
	private int bookCopyId;
	private int memberId;
	private Date rentedAt;
	private Date returnBy;
	private Date returnedAt;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getBookCopyId() {
		return bookCopyId;
	}

	public void setBookCopyId(int bookCopyId) {
		this.bookCopyId = bookCopyId;
	}

	public int getMemberId() {
		return memberId;
	}

	public void setMemberId(int memberId) {
		this.memberId = memberId;
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
