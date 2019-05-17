package models.dao;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;

import exceptions.NoResultException;
import models.bean.Book;
import models.bean.BookCopy;

// TODO SQLException to NoResultException
public class BookDao {
	Connection con;

	public BookDao() {
		con = getConnection();
	}

	// TODO 速度面check
	public List<Book> findAll() throws SQLException {
		String sql = "SELECT * FROM book_info";
		PreparedStatement stmt = con.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();
		List<Book> books = new ArrayList<Book>();
		while (rs.next()) {
			Book book = buildBook(rs);
			books.add(book);
		}
		stmt.close();
		rs.close();
		return books;
	}

	public Book findByIsbn(String isbn) throws SQLException {
		String sql = "SELECT * FROM book_info WHERE isbn = ?";
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setString(1, isbn);
		ResultSet rs = stmt.executeQuery();
		if (rs.next()) {
			Book book = buildBook(rs);
			return book;
		} else {
			throw new SQLException();
		}
	}

	public List<String> findCategories() throws SQLException {
		String sql = "SELECT name FROM category ORDER BY id";
		PreparedStatement stmt = con.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();
		List<String> categories = new ArrayList<String>();
		while (rs.next()) {
			categories.add(rs.getString("name"));
		}
		stmt.close();
		rs.close();
		return categories;
	}

	public List<String> findPublishers() throws SQLException {
		String sql = "SELECT name FROM publisher ORDER BY id";
		PreparedStatement stmt = con.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();
		List<String> publishers = new ArrayList<String>();
		while (rs.next()) {
			publishers.add(rs.getString("name"));
		}
		stmt.close();
		rs.close();
		return publishers;
	}

	public void create(Book book, int copiesNum) throws SQLException {
		String sql = "INSERT INTO book_info (isbn, category_id, publisher_id, name, author) VALUES (?, ?, ?, ?, ?)";
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setString(1, book.getIsbn());
		stmt.setInt(2, categoryIdByName(book.getCategory())); //TODO 効率悪くない?
		stmt.setInt(3, publisherIdByName(book.getPublisher()));
		stmt.setString(4, book.getName());
		stmt.setString(5, book.getAuthor());
		stmt.executeUpdate();
		for (int i = 0; i < copiesNum; i++) {
			createCopy(book.getIsbn());
		}
		stmt.close();
	}

	public void update(Book book) throws SQLException {
		String sql = "UPDATE book_info SET category_id = ?, publisher_id = ?, name = ?, author = ? WHERE isbn = ?";
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setInt(1, categoryIdByName(book.getCategory())); //TODO 効率悪くない?
		stmt.setInt(2, publisherIdByName(book.getPublisher()));
		stmt.setString(3, book.getName());
		stmt.setString(4, book.getAuthor());
		stmt.setString(5, book.getIsbn());
		// copies は編集しないので無視。
		stmt.executeUpdate();
		stmt.close();
	}

	public Book buildBookWithoutCopies(HttpServletRequest request) {
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		Book book = new Book();
		book.setIsbn(request.getParameter("isbn"));
		book.setName(request.getParameter("name"));
		book.setAuthor(request.getParameter("author"));
		book.setCategory(request.getParameter("category"));
		book.setPublisher(request.getParameter("publisher"));
		return book;
	}

	private Book buildBook(ResultSet rs) throws SQLException {
		Book book = new Book();
		String isbn = rs.getString("isbn");
		book.setIsbn(isbn);
		book.setName(rs.getString("name"));
		book.setAuthor(rs.getString("author"));
		int categoryId = rs.getInt("category_id");
		book.setCategory(categoryNameById(categoryId));
		int publisherId = rs.getInt("publisher_id");
		book.setPublisher(publisherNameById(publisherId));
		book.setCopies(findBookCopiesByIsbn(isbn));
		return book;
	}

	private List<BookCopy> findBookCopiesByIsbn(String isbn) throws SQLException {
		String sql = "SELECT id FROM book_copy WHERE isbn = ?";
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setString(1, isbn);
		ResultSet rs = stmt.executeQuery();
		List<BookCopy> bookCopies = new ArrayList<BookCopy>();
		while (rs.next()) {
			BookCopy bookCopy = new BookCopy();
			bookCopy.setId(rs.getInt("id"));
			bookCopy.setIsbn(isbn);
			bookCopies.add(bookCopy);
		}
		stmt.close();
		rs.close();
		return bookCopies;
	}

	public BookCopy findBookCopyById(int id) throws NoResultException {

		String sql = "SELECT * from book_copy WHERE id = ?";
		try {
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				BookCopy bookCopy = new BookCopy();
				bookCopy.setId(id);
				bookCopy.setIsbn(rs.getString("isbn"));
				stmt.close();
				rs.close();
				return bookCopy;
			} else {
				throw new NoResultException();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new NoResultException();
		}
	}

	private int categoryIdByName(String categoryName) throws SQLException {
		String sql = "SELECT id FROM category WHERE name = ?";
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setString(1, categoryName);
		ResultSet rs = stmt.executeQuery();
		if (rs.next()) {
			int id = rs.getInt("id");
			stmt.close();
			rs.close();
			return id;
		} else {
			throw new SQLException("一行も結果がありません。");
		}

	}

	private int publisherIdByName(String publisherName) throws SQLException {
		String sql = "SELECT id FROM publisher WHERE name = ?";
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setString(1, publisherName);
		ResultSet rs = stmt.executeQuery();
		if (rs.next()) {
			int id = rs.getInt("id");
			stmt.close();
			rs.close();
			return id;
		} else {
			throw new SQLException("結果が一行もありません。");
		}
	}

	private String categoryNameById(int categoryId) throws SQLException {
		String sql = "SELECT name FROM category WHERE id = ?";
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setInt(1, categoryId);
		ResultSet rs = stmt.executeQuery();
		rs.next();
		String name = rs.getString("name");
		stmt.close();
		rs.close();
		return name;
	}

	private String publisherNameById(int publisherId) throws SQLException {
		String sql = "SELECT name FROM publisher WHERE id = ?";
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setInt(1, publisherId);
		ResultSet rs = stmt.executeQuery();
		rs.next();
		String name = rs.getString("name");
		stmt.close();
		rs.close();
		return name;
	}

	private void createCopy(String isbn) throws SQLException {
		String sql = "INSERT INTO book_copy (isbn) VALUES (?)";
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setString(1, isbn);
		stmt.executeUpdate();
		stmt.close();
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

}
