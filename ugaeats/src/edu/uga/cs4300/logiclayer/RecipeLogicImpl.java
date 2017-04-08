package edu.uga.cs4300.logiclayer;

import javax.jms.Connection;

import edu.uga.cs4300.objectlayer.User;
import edu.uga.cs4300.persistlayer.RecipePersistImpl;

public class RecipeLogicImpl {
	RecipePersistImpl userPersist;
	
	public RecipeLogicImpl() {
		userPersist = new RecipePersistImpl();
	}
	
	public int addUser(String first_name, String last_name, String username, String password, String email) {
		User u = new User(first_name, last_name, username, password, email);
		return userPersist.persistUser(u);
	}
	
	public void validateLogin(String username, String password) {
		Connection connection = DbAccessImpl.connect();
		String query = "SELECT * FROM users WHERE username = " + username + " AND password = " + password;
		ResultSet rs = DbAccessImpl.retrieve(connection, query);
		if (rs.isEmpty()) {
			userPersist.reloadLoginPage();
		} else {
			String firstName;
			while(rs.next()) {
				firstName = rs.getString("firstName");
			}
			userPersist.openHomePage(firstName);
		}
		
		DbAccessImpl.disconnect(connection);
	}
}
