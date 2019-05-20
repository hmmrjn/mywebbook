package models.dao;

import java.io.UnsupportedEncodingException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import exceptions.NoResultException;
import models.bean.Book;
import models.bean.BookCopy;
import models.bean.Category;
import models.bean.Publisher;

public class BookDao extends Dao {

	CategoryDao categoryDao = new CategoryDao();
	PublisherDao publisherDao = new PublisherDao();
	BookCopyDao bookCopyDao = new BookCopyDao();

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

	public void create(Book book, int copiesNum) {
		String sql = "INSERT INTO book_info (isbn, category_id, publisher_id, name, author, released_at) VALUES (?, ?, ?, ?, ?, ?)";
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, book.getIsbn());
			stmt.setInt(2, book.getCategory().getId());
			stmt.setInt(3, book.getPublisher().getId());
			stmt.setString(4, book.getName());
			stmt.setString(5, book.getAuthor());
			stmt.setDate(6, new java.sql.Date(book.getReleasedAt().getTime()));
			stmt.executeUpdate();
			BookCopy bookCopy = new BookCopy();
			bookCopy.setIsbn(book.getIsbn());
			bookCopy.setCreatedAt(new Date());
			for (int i = 0; i < copiesNum; i++) {
				bookCopyDao.create(bookCopy);
			}
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void update(Book book) {
		try {
			String sql = "UPDATE book_info SET category_id = ?, publisher_id = ?, name = ?, author = ?, released_at = ? WHERE isbn = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, book.getCategory().getId());
			stmt.setInt(2, book.getPublisher().getId());
			stmt.setString(3, book.getName());
			stmt.setString(4, book.getAuthor());
			stmt.setDate(5, new java.sql.Date(book.getReleasedAt().getTime()));
			stmt.setString(6, book.getIsbn());
			// copies は編集しないので無視。
			stmt.executeUpdate();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public Book buildBookWithoutCopies(HttpServletRequest request) throws NoResultException, ParseException {
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		Book book = new Book();
		String isbn = request.getParameter("isbn");
		isbn = isbn.replace("-", "");
		book.setIsbn(isbn);
		book.setName(request.getParameter("name"));
		book.setAuthor(request.getParameter("author"));
		int categoryId = Integer.parseInt(request.getParameter("categoryId"));
		Category category = categoryDao.findById(categoryId);
		book.setCategory(category);
		int publisherId = Integer.parseInt(request.getParameter("publisherId"));
		Publisher publisher = publisherDao.findById(publisherId);
		book.setPublisher(publisher);
		String by = request.getParameter("releasedAtYear");
		String bm = request.getParameter("releasedAtMonth");
		String bd = request.getParameter("releasedAtDay");
		String dateStr = by + "-" + bm + "-" + bd;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date releasedAt = sdf.parse(dateStr);
		book.setReleasedAt(releasedAt);
		return book;
	}

	private Book buildBook(ResultSet rs) throws SQLException, NoResultException {
		Book book = new Book();
		String isbn = rs.getString("isbn");
		book.setIsbn(isbn);
		book.setName(rs.getString("name"));
		book.setAuthor(rs.getString("author"));
		int categoryId = rs.getInt("category_id");
		Category category = categoryDao.findById(categoryId);
		book.setCategory(category);
		int publisherId = rs.getInt("publisher_id");
		Publisher publisher = publisherDao.findById(publisherId);
		book.setPublisher(publisher);
		book.setReleasedAt(rs.getDate("released_at"));
		book.setCopies(bookCopyDao.findByIsbn(isbn, true));
		return book;
	}
}
