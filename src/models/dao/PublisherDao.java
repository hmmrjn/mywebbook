package models.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import exceptions.NoResultException;
import models.bean.Publisher;

public class PublisherDao extends Dao {

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
