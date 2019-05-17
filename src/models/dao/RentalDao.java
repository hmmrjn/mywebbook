package models.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import exceptions.NoResultException;
import models.bean.Rental;

public class RentalDao extends Dao {

	public Rental findById(int id) throws NoResultException {
		String sql = "SELECT * FROM rental WHERE id = ?";
		PreparedStatement stmt;
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				Rental rental = buildRental(rs);
				return rental;
			} else {
				throw new NoResultException();
			}
		} catch (SQLException e) {
			throw new NoResultException();
		}
	}

	public Rental findByBookCopyId(int bookId) throws NoResultException {
		String sql = "SELECT * FROM rental WHERE book_copy_id = ?";
		PreparedStatement stmt;
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, bookId);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				Rental rental = buildRental(rs);
				return rental;
			} else {
				throw new NoResultException();
			}
		} catch (SQLException e) {
			throw new NoResultException();
		}
	}

	public void create(Rental rental) {
		String sql = "INSERT INTO rental (book_copy_id, member_id, rented_at, return_by) VALUES (?, ?, ?, ?)";
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, rental.getBookCopyId());
			stmt.setInt(2, rental.getMemberId());
			stmt.setDate(3, new java.sql.Date(rental.getRentedAt().getTime()));
			stmt.setDate(4, new java.sql.Date(rental.getReturnBy().getTime()));
			stmt.executeUpdate();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private Rental buildRental(ResultSet rs) throws SQLException {
		Rental rental = new Rental();
		rental.setId(rs.getInt("id"));
		rental.setBookCopyId(rs.getInt("member_id"));
		rental.setBookCopyId(rs.getInt("book_copy_id"));
		rental.setRentedAt(rs.getDate("rented_at"));
		rental.setReturnBy(rs.getDate("return_by"));
		rental.setReturnedAt(rs.getDate("returned_at"));
		return rental;
	}

}
