package models.dao;

import java.io.UnsupportedEncodingException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import exceptions.NoResultException;
import models.bean.Book;
import models.bean.BookCopy;
public class BookDao extends Dao {

	// TODO 速度面check
	public List<Book> findAll() {
		String sql = "SELECT * FROM book_info";
		List<Book> books = new ArrayList<Book>();
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Book book = buildBook(rs);
				books.add(book);
			}
			stmt.close();
			rs.close();
		} catch (SQLException | NoResultException e) {
			e.printStackTrace();
		}
		return books;
	}

	public Book findByIsbn(String isbn) throws NoResultException {
		String sql = "SELECT * FROM book_info WHERE isbn = ?";
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, isbn);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				Book book = buildBook(rs);
				return book;
			} else {
				throw new NoResultException();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new NoResultException();
		}
	}

	public List<String> findCategories() {
		String sql = "SELECT name FROM category ORDER BY id";
		List<String> categories = new ArrayList<String>();
		try {

			PreparedStatement stmt = conn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				categories.add(rs.getString("name"));
			}
			stmt.close();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return categories;
	}

	public List<String> findPublishers() {
		String sql = "SELECT name FROM publisher ORDER BY id";
		List<String> publishers = new ArrayList<String>();
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				publishers.add(rs.getString("name"));
			}
			stmt.close();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return publishers;
	}

	public void create(Book book, int copiesNum) {
		String sql = "INSERT INTO book_info (isbn, category_id, publisher_id, name, author) VALUES (?, ?, ?, ?, ?)";
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
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
		} catch (SQLException | NoResultException e) {
			e.printStackTrace();
		}
	}

	public void update(Book book) {
		try {
			String sql = "UPDATE book_info SET category_id = ?, publisher_id = ?, name = ?, author = ? WHERE isbn = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, categoryIdByName(book.getCategory())); //TODO 効率悪くない?
			stmt.setInt(2, publisherIdByName(book.getPublisher()));
			stmt.setString(3, book.getName());
			stmt.setString(4, book.getAuthor());
			stmt.setString(5, book.getIsbn());
			// copies は編集しないので無視。
			stmt.executeUpdate();
			stmt.close();
		} catch (SQLException | NoResultException e) {
			e.printStackTrace();
		}
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

	private Book buildBook(ResultSet rs) throws SQLException, NoResultException {
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

	private List<BookCopy> findBookCopiesByIsbn(String isbn) {
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

	public BookCopy findBookCopyById(int id) throws NoResultException {

		String sql = "SELECT * from book_copy WHERE id = ?";
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
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

	private int categoryIdByName(String categoryName) throws NoResultException {
		String sql = "SELECT id FROM category WHERE name = ?";
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, categoryName);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				int id = rs.getInt("id");
				stmt.close();
				rs.close();
				return id;
			} else {
				throw new NoResultException();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new NoResultException();
		}

	}

	private int publisherIdByName(String publisherName) throws NoResultException {
		String sql = "SELECT id FROM publisher WHERE name = ?";
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, publisherName);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				int id = rs.getInt("id");
				stmt.close();
				rs.close();
				return id;
			} else {
				throw new NoResultException();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new NoResultException();
		}
	}

	private String categoryNameById(int categoryId) throws NoResultException {
		String sql = "SELECT name FROM category WHERE id = ?";
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, categoryId);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				String name = rs.getString("name");
				stmt.close();
				rs.close();
				return name;
			} else {
				throw new NoResultException();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new NoResultException();
		}
	}

	private String publisherNameById(int publisherId) throws NoResultException {
		String sql = "SELECT name FROM publisher WHERE id = ?";
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, publisherId);
			ResultSet rs = stmt.executeQuery();
			rs.next();
			String name = rs.getString("name");
			stmt.close();
			rs.close();
			return name;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new NoResultException();
		}
	}

	private void createCopy(String isbn) throws SQLException {
		String sql = "INSERT INTO book_copy (isbn) VALUES (?)";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setString(1, isbn);
		stmt.executeUpdate();
		stmt.close();
	}

}
