package models.bean;

import java.io.Serializable;

public class BookCopy implements Serializable {

	private int id;
	private String isbn;

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


}
