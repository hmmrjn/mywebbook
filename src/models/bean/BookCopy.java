package models.bean;

import java.io.Serializable;
import java.util.Date;

public class BookCopy implements Serializable {

	private int id;
	private String isbn;
	private Date createdAt;
	private Date discardedAt;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getDiscardedAt() {
		return discardedAt;
	}

	public void setDiscardedAt(Date discardedAt) {
		this.discardedAt = discardedAt;
	}

}
