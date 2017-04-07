package edu.uga.cs4300.persistlayer;

import java.sql.Connection;

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

	
}
