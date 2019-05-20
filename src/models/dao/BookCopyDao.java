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
				BookCopy bookCopy = buildBookCopy(rs);
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

	public List<BookCopy> findByIsbn(String isbn, boolean findNotDiscarded) {
		String sql = "SELECT * FROM book_copy WHERE isbn = ?";
		if(findNotDiscarded) {
			sql += " AND discarded_at IS NULL";
		}
		List<BookCopy> bookCopies = new ArrayList<BookCopy>();
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, isbn);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				BookCopy bookCopy = buildBookCopy(rs);
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
		String sql = "INSERT INTO book_copy (isbn, created_at) VALUES (?, ?) ";
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, bookCopy.getIsbn());
			stmt.setDate(2, new java.sql.Date(bookCopy.getCreatedAt().getTime()));
			stmt.executeUpdate();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void update(BookCopy bookCopy) {
		String sql = "UPDATE book_copy SET isbn = ?, created_at = ?, discarded_at = ? WHERE id = ?";
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, bookCopy.getIsbn());
			stmt.setDate(2, new java.sql.Date(bookCopy.getCreatedAt().getTime()));
			stmt.setDate(3, new java.sql.Date(bookCopy.getDiscardedAt().getTime()));
			stmt.setInt(4, bookCopy.getId());
			stmt.executeUpdate();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	//	public void delete(int id) {
	//		String sql = "DELETE FROM book_copy WHERE id = ?";
	//		try {
	//			PreparedStatement stmt = conn.prepareStatement(sql);
	//			stmt.setInt(1, id);
	//			stmt.executeUpdate();
	//			stmt.close();
	//		} catch (SQLException e) {
	//			e.printStackTrace();
	//		}
	//	}

	private BookCopy buildBookCopy(ResultSet rs) throws SQLException {
		BookCopy bookCopy = new BookCopy();
		bookCopy.setId(rs.getInt("id"));
		bookCopy.setIsbn(rs.getString("isbn"));
		bookCopy.setCreatedAt(rs.getDate("created_at"));
		bookCopy.setDiscardedAt(rs.getDate("discarded_at"));
		return bookCopy;
	}

}
