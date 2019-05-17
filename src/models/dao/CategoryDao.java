package models.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import exceptions.NoResultException;
import models.bean.Category;

public class CategoryDao extends Dao {

	public Category findById(int id) throws NoResultException {
		String sql = "SELECT * FROM category WHERE id = ?";
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				Category category = new Category();
				category.setId(id);
				category.setName(rs.getString("name"));
				return category;
			} else {
				throw new NoResultException();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new NoResultException();
		}
	}

}
