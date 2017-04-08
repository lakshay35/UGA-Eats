package edu.uga.cs4300.persistlayer;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import edu.uga.cs4300.objectlayer.User;

public class RecipePersistImpl {

	public int persistUser(User u) {
		Connection c = DbAccessImpl.connect();
		String query = "INSERT INTO users (first_name,last_name,username,password,email) VALUES"
				+ "('" + u.getFirst_name() + "','" + u.getLast_name() + "','"
				+ u.getUsername() + "','" + u.getPassword() + "','" + u.getEmail() + "')";
		int temp = DbAccessImpl.create(c,  query);
		DbAccessImpl.disconnect(c);
		return temp;
	} // persistUser()
	
	public User checkUser(String username, String password) {
		Connection connect = DbAccessImpl.connect();
		User temp = null;
		String query = "SELECT * FROM users WHERE username = " + "'" + username + "'" + " AND password = " + "'" + password + "'";
		ResultSet rs = DbAccessImpl.retrieve(connect, query);
		try {
		if (!rs.next()) {
		} else {
			String firstName = rs.getString("first_name");
			String lastName = rs.getString("last_name");
			String userName = rs.getString("username");
			String passWord = rs.getString("password");
			String email = rs.getString("email");
			int id = rs.getInt("id");
			temp = new User(id, firstName, lastName, userName, passWord, email);
		}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return temp;
	}

	
}
