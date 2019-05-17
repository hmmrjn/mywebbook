package models.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import models.bean.BookCopy;

public class BookCopyDao {

	// TODO connection 一元化
	Connection con;

	public BookCopyDao() {
		con = getConection();
	}

	public BookCopy findById(int id) {
		String sql = "SELECT * FROM book_copy WHERE id = ?";
		try {
			PreparedStatement stmt = con.prepareStatement(sql);
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
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setInt(1, bookCopy.getId());
			stmt.setString(2, bookCopy.getIsbn());
			stmt.executeUpdate();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public Connection getConection() {
		try {
			ResourceBundle rb = ResourceBundle.getBundle("db_config");
			// JDBCドライバの登録
			Class.forName("org.postgresql.Driver");
			String url = rb.getString("url");
			String user = rb.getString("user");
			String pass = rb.getString("password");
			Connection con = DriverManager.getConnection(url, user, pass);
			return con;
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
}
