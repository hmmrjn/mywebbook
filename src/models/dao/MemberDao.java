package models.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;

import models.bean.Member;

public class MemberDao {
	Connection con;

	public MemberDao() {
		con = getConnection();
	}

	public List<Member> findAll() throws SQLException {
		String sql = "SELECT * FROM member ORDER BY id";
		PreparedStatement stmt = con.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();
		List<Member> members = new ArrayList<Member>();
		while (rs.next()) {
			Member member = buildMember(rs);
			members.add(member);
		}
		stmt.close();
		rs.close();
		return members;
	}

	public Member findById(int id) throws SQLException {
		String sql = "SELECT * FROM member WHERE id = ?";
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setInt(1, id);
		ResultSet rs = stmt.executeQuery();
		rs.next();
		Member member = buildMember(rs);
		stmt.close();
		rs.close();
		return member;
	}

	/**
	 *
	 * @param member
	 * @return 新規登録したmemberのid
	 * @throws SQLException
	 */
	public int create(Member member) throws SQLException {
		String sql = "INSERT INTO member (family_name, given_name, postal_code, address, tel, email, birthday, subscribed_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setString(1, member.getFamilyName());
		stmt.setString(2, member.getGivenName());
		stmt.setString(3, member.getPostalCode());
		stmt.setString(4, member.getAddress());
		stmt.setString(5, member.getTel());
		stmt.setString(6, member.getEmail());
		stmt.setDate(7, new java.sql.Date(member.getBirthday().getTime()));
		stmt.setDate(8, new java.sql.Date(new Date().getTime()));
		stmt.executeUpdate();
		sql = "SELECT MAX(id) FROM member";
		stmt = con.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();
		rs.next();
		int id = rs.getInt("max");
		stmt.close();
		rs.close();
		System.out.println(id);
		return id;
	}

	public void update(Member member) throws SQLException {
		String sql = "UPDATE member SET family_name=?, given_name=?, postal_code=?, address=?, tel=?, email=?, birthday=? WHERE id=?";
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setString(1, member.getFamilyName());
		stmt.setString(2, member.getGivenName());
		stmt.setString(3, member.getPostalCode());
		stmt.setString(4, member.getAddress());
		stmt.setString(5, member.getTel());
		stmt.setString(6, member.getEmail());
		stmt.setDate(7, new java.sql.Date(member.getBirthday().getTime()));
		stmt.setInt(8, member.getId());
		stmt.executeUpdate();
		stmt.close();
	}

	public void delete(int id) throws SQLException {
		String sql = "DELETE FROM member WHERE id = ?";
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setInt(1, id);
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

	private Member buildMember(ResultSet rs) throws SQLException {
		Member member = new Member();
		member.setId(rs.getInt("id"));
		member.setFamilyName(rs.getString("family_name"));
		member.setGivenName(rs.getString("given_name"));
		member.setPostalCode(rs.getString("postal_code"));
		member.setAddress(rs.getString("address"));
		member.setTel(rs.getString("tel"));
		member.setEmail(rs.getString("email"));
		member.setBirthday(rs.getDate("birthday"));
		member.setSubscribedAt(rs.getDate("subscribed_at"));
		member.setUnsubscribedAt(rs.getDate("unsubscribed_at"));
		return member;
	}

	public Member buildMember(HttpServletRequest request) throws ParseException {
		Member member = new Member();
		if (request.getParameter("id") != null) {
			member.setId(Integer.parseInt(request.getParameter("id")));
		}
		member.setFamilyName(request.getParameter("familyName"));
		member.setGivenName(request.getParameter("givenName"));
		member.setPostalCode(request.getParameter("postalCode"));
		member.setAddress(request.getParameter("address"));
		member.setTel(request.getParameter("tel"));
		member.setEmail(request.getParameter("email"));
		String by = request.getParameter("birthdayYear");
		String bm = request.getParameter("birthdayMonth");
		String bd = request.getParameter("birthdayDay");
		String dateStr = by + "-" + bm + "-" + bd;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date birthday;
		birthday = sdf.parse(dateStr);
		member.setBirthday(birthday);
		return member;
	}

}
