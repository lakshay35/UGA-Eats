package edu.uga.cs4300.persistlayer;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import edu.uga.cs4300.objectlayer.Review;
import edu.uga.cs4300.objectlayer.User;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapperBuilder;
import freemarker.template.SimpleHash;
import freemarker.template.Template;
import freemarker.template.TemplateException;

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
		String query = "SELECT * FROM users WHERE username = " + username + " AND password = " + password;
		ResultSet rs = DbAccessImpl.retrieve(connection, query);
		int result;
		if (!rs.next()) {
			result = 0;
		} else {
			String firstName = rs.getString("first_name");
			String lastName = rs.getString("last_name");
			String username = rs.getString("username");
			String password = rs.getString("password");
			String email = rs.getString("email");
			int id = rs.getInt("id");
			temp = User(id, firstName, lastName, username, password, email);
		}
		return temp;
	}

	
}
