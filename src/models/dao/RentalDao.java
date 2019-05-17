package models.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import exceptions.NoResultException;
import models.bean.Rental;

public class RentalDao {

	// TODO rename con to conn
	Connection con;

	public RentalDao() {
		con = getConnection();
	}

	public Rental findById(int id) throws NoResultException {
		String sql = "SELECT * FROM rental WHERE id = ?";
		PreparedStatement stmt;
		try {
			stmt = con.prepareStatement(sql);
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
			stmt = con.prepareStatement(sql);
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
			PreparedStatement stmt = con.prepareStatement(sql);
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

	private Connection getConnection() {
		ResourceBundle rb = ResourceBundle.getBundle("/models/db_config");
		// JDBCドライバの登録
		try {
			Class.forName("org.postgresql.Driver");
			String url = rb.getString("url");
			String user = rb.getString("user");
			String pass = rb.getString("password");
			Connection con = DriverManager.getConnection(url, user, pass);
			return con;
		} catch (ClassNotFoundException | SQLException e) {
			System.err.println("DBのドライバーが壊れているか、DBの認証情報が間違っていると思います。");
			e.printStackTrace();
			return null;
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
