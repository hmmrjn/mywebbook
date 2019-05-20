package models.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import exceptions.NoResultException;
import models.bean.Publisher;

public class PublisherDao extends Dao {

	public List<Publisher> findAll() {
		String sql = "SELECT * FROM publisher ORDER BY id";
		List<Publisher> publishers = new ArrayList<Publisher>();
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Publisher publisher = new Publisher();
				publisher.setId(rs.getInt("id"));
				publisher.setName(rs.getString("name"));
				publishers.add(publisher);
			}
			stmt.close();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return publishers;
	}

	public Publisher findById(int id) throws NoResultException {
		String sql = "SELECT * FROM publisher WHERE id = ?";
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				Publisher publisher = new Publisher();
				publisher.setId(id);
				publisher.setName(rs.getString("name"));
				return publisher;
			} else {
				throw new NoResultException();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new NoResultException();
		}
	}

}
