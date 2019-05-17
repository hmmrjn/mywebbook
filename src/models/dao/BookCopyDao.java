package models.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import exceptions.NoResultException;
import models.bean.BookCopy;

public class BookCopyDao extends Dao {

	public BookCopy findById(int id) throws NoResultException {
		String sql = "SELECT * FROM book_copy WHERE id = ?";
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				BookCopy bookCopy = new BookCopy();
				bookCopy.setId(rs.getInt("id"));
				bookCopy.setIsbn(rs.getString("isbn"));
				rs.close();
				stmt.close();
				return bookCopy;
			} else {
				throw new NoResultException();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<BookCopy> findByIsbn(String isbn) {
		String sql = "SELECT id FROM book_copy WHERE isbn = ?";
		List<BookCopy> bookCopies = new ArrayList<BookCopy>();
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, isbn);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				BookCopy bookCopy = new BookCopy();
				bookCopy.setId(rs.getInt("id"));
				bookCopy.setIsbn(isbn);
				bookCopies.add(bookCopy);
			}
			stmt.close();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return bookCopies;
	}

	public void create(BookCopy bookCopy) {
		String sql = "INSERT INTO book_copy (isbn) VALUES (?) ";
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, bookCopy.getIsbn());
			stmt.executeUpdate();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
