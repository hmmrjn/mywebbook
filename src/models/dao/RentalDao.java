package models.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import exceptions.NoResultException;
import models.bean.BookCopy;
import models.bean.Member;
import models.bean.Rental;

public class RentalDao extends Dao {

	MemberDao memberDao = new MemberDao();
	BookCopyDao bookCopyDao = new BookCopyDao();

	public List<Rental> findAll() {
		String sql = "SELECT * FROM rental";
		List<Rental> rentals = new ArrayList<Rental>();
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Rental rental = buildRental(rs);
				rentals.add(rental);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rentals;
	}

	public List<Rental> findNotReturned() {
		String sql = "SELECT * FROM rental WHERE returned_at IS NULL";
		List<Rental> rentals = new ArrayList<Rental>();
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Rental rental = buildRental(rs);
				rentals.add(rental);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rentals;
	}

	public Rental findById(int id) throws NoResultException {
		String sql = "SELECT * FROM rental WHERE id = ?";
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
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

	public List<Rental> findByBookCopyId(int bookCopyId) {
		String sql = "SELECT * FROM rental WHERE book_copy_id = ?";
		List<Rental> rentals = new ArrayList<Rental>();
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, bookCopyId);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Rental rental = buildRental(rs);
				rentals.add(rental);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rentals;
	}

	public Rental findNotReturnedByBookCopyId(int bookCopyId) throws NoResultException {
		String sql = "SELECT * FROM rental WHERE book_copy_id = ? AND returned_at IS NULL";
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, bookCopyId);
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

	public Rental findNotReturnedByMemberId(int memberId) throws NoResultException {
		String sql = "SELECT * FROM rental WHERE member_id = ? AND returned_at IS NULL";
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, memberId);
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
			stmt.setInt(1, rental.getBookCopy().getId());
			stmt.setInt(2, rental.getMember().getId());
			stmt.setDate(3, new java.sql.Date(rental.getRentedAt().getTime()));
			stmt.setDate(4, new java.sql.Date(rental.getReturnBy().getTime()));
			stmt.executeUpdate();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void update(Rental rental) {
		String sql = "UPDATE rental SET member_id = ?, rented_at = ?, return_by = ?, returned_at = ? WHERE book_copy_id = ?";
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, rental.getMember().getId());
			stmt.setDate(2, new java.sql.Date(rental.getRentedAt().getTime()));
			stmt.setDate(3, new java.sql.Date(rental.getReturnBy().getTime()));
			stmt.setDate(4, new java.sql.Date(rental.getReturnedAt().getTime()));
			stmt.setInt(5, rental.getBookCopy().getId());
			stmt.executeUpdate();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private Rental buildRental(ResultSet rs) throws SQLException {
		Rental rental = new Rental();
		rental.setId(rs.getInt("id"));
		try {
			Member member = memberDao.findById(rs.getInt("member_id"));
			BookCopy bookCopy = bookCopyDao.findById(rs.getInt("book_copy_id"));
			rental.setMember(member);
			rental.setBookCopy(bookCopy);
		} catch (NoResultException ignore) {
		}
		rental.setRentedAt(rs.getDate("rented_at"));
		rental.setReturnBy(rs.getDate("return_by"));
		rental.setReturnedAt(rs.getDate("returned_at"));
		return rental;
	}

}
