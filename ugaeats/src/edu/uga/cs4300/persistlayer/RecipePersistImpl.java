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

	
	public ArrayList<Recipe> persistMyRecipes(int userID)
	{
		//ArrayList to store the list of user's recipes
		ArrayList<Recipe> returnList = new ArrayList<Recipe>();
		Recipe temp = null;
		
		Connection connect = DbAccessImpl.connect();
		String query = "SELECT * FROM recipes WHERE recipes.users_id = " + userID + ";";
		ResultSet rs = DbAccessImpl.retrieve(connect, query);
		try {
			if (!rs.next()) {
			} else {
				int id = rs.getInt("id");
				String recipeName = rs.getString("recipe_name");
				float rank = rs.getFloat("rank");
				int numRating = rs.getInt("num_ratings");
				List<String> ingredients = getIngredients(id, connect);	
				temp = new Recipe(id, recipeName, rank, numRating, ingredients);
				returnList.add(temp);
			}
			} catch (SQLException e) {
				e.printStackTrace();
			}
 		return returnList;
	}
	
	public List<String> getIngredients (int recipeID, Connection con) 
	{
		List<String> ingredients = new ArrayList<String>();
		String query = "SELECT ingredient FROM ingredients JOIN recipes r ON ingredients WHERE r."+ recipeID + " = ingredients.recipes_id;";
		ResultSet rs = DbAccessImpl.retrieve(con, query);
		try{
			
			while(rs.next())
				{
					String ing = rs.getString("ingredient");
					ingredients.add(ing);
				}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		return ingredients;
		
	}
	


}
