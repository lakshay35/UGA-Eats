package edu.uga.cs4300.persistlayer;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import edu.uga.cs4300.objectlayer.Recipe;
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

	public List<Recipe> getRecipes() {
		List<Recipe> recipes = new ArrayList<Recipe>();
		Connection connect = DbAccessImpl.connect();
		String query = "SELECT * FROM recipes JOIN users u ON u.id = users_id";
		ResultSet rs = DbAccessImpl.retrieve(connect, query);
		try {
			while (rs.next()) {
				int id = rs.getInt(7);
				String name = rs.getString("recipe_name");
				float rank = rs.getFloat("rank");
				String permissions = rs.getString("permissions");
				String username = rs.getString("username");
				Blob image = rs.getBlob("recipe_image");
				Recipe recipe = new Recipe(id, name, rank, username, image);
				if(permissions.equals("public")) {
					recipes.add(recipe);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return recipes;
	}

	

}
