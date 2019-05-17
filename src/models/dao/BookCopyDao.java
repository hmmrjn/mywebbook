package models.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import models.bean.BookCopy;

public class BookCopyDao extends Dao {

	public BookCopy findById(int id) {
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
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void create(BookCopy bookCopy) {
		String sql = "INSERT INTO book_copy (id, isbn) VALUES (?, ?) ";
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, bookCopy.getId());
			stmt.setString(2, bookCopy.getIsbn());
			stmt.executeUpdate();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
