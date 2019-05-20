package models.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class Dao {

	static Connection conn;

	public Dao() {
		if (conn == null) {
			conn = getConection();
		}
	}

	public Connection getConection() {
		try {
			ResourceBundle rb = ResourceBundle.getBundle("models/dao/db_config");
			// JDBCドライバの登録
			Class.forName("org.postgresql.Driver");
			String url = rb.getString("url");
			String user = rb.getString("user");
			String pass = rb.getString("password");
			Connection con = DriverManager.getConnection(url, user, pass);
			return con;
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("データベースに接続できませんでした。");
			e.printStackTrace();
			return null;
		}
	}

}
