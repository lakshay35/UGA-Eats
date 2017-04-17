package edu.uga.cs4300.persistlayer;

import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.Statement;

import edu.uga.cs4300.objectlayer.Recipe;
import edu.uga.cs4300.objectlayer.User;
import sun.misc.BASE64Encoder;

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
	
	// Works to get all recipes along with username
	
	public List<Recipe> getImages() {
		String query = "SELECT * FROM recipes r JOIN users u ON r.users_id = u.id";
		Connection connect = DbAccessImpl.connect();
		ResultSet set = DbAccessImpl.retrieve(connect, query);
		List<Recipe> temp = new ArrayList<Recipe>();
		BASE64Encoder encoder = new BASE64Encoder();
		try {
			while (set.next())
			{
				int blobLength;
				byte[] blobArray = null;
				String image64 = null;
				Blob blob = set.getBlob("recipe_image");
				blobLength = (int) blob.length();
				blobArray = blob.getBytes(1, blobLength);
				image64 = encoder.encode(blobArray);
				
				int recipeId = set.getInt(1);
				String recipeName = set.getString("recipe_name");
				String permission = set.getString("permissions");
				Float rank = set.getFloat("rank");
				int userId = set.getInt("users_id");
				String username = set.getString("username");
				
				Recipe recipe = new Recipe(recipeId, recipeName, rank, username, image64, userId);
				temp.add(recipe);
				//temp.add(blob);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		DbAccessImpl.disconnect(connect);
		return temp;
	}

	// Doesnt work
	
	public List<Recipe> getRecipes() {
		List<Recipe> recipes = new ArrayList<Recipe>();
		Connection connect = DbAccessImpl.connect();
		String query = "SELECT * FROM recipes JOIN users u ON u.id = users_id";
		ResultSet rs = DbAccessImpl.retrieve(connect, query);
		BASE64Encoder encoder = new BASE64Encoder();
		try {
			while (rs.next()) {
				int id = rs.getInt(1);
				int user_id = rs.getInt(7);
				String name = rs.getString("recipe_name");
				float rank = rs.getFloat("rank");
				String permissions = rs.getString("permissions");
				String username = rs.getString("username");
				Blob image = rs.getBlob("recipe_image");
				int blobLength;
				byte[] blobArray;
				String image64 = null;
				try {
					blobLength = (int) image.length();
					blobArray = image.getBytes(1, blobLength);
					image64 = encoder.encode(blobArray);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (permissions.equals("public")) {
				Recipe recipe = new Recipe(id, name, rank, username, image64, user_id);
				recipes.add(recipe);
				}
				//Blob image = rs.getBlob("recipe_image");
				//Recipe recipe = new Recipe(id, name, rank, username, image, user_id);
				/*if(permissions.equals("public")) {
					recipes.add(recipe);
				}*/
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return recipes;
	}

	public int addRecipeToDatabase(String recipeName, List<String> ingredients, List<String> instructions,
			String permission, int user_id, InputStream fileContent) {
		Connection c = DbAccessImpl.connect();
		String query = "INSERT INTO recipes (recipe_name,permissions,rank,num_ratings,users_id,recipe_image,sum_ratings) Values"
				+ " (?,?,?,?,?,?,?)";
		String query2 = "INSERT INTO ingredients (recipes_id,ingredient) VALUES (?,?)";
		String query3 = "INSERT INTO instructions (recipes_id,instruction_number,instruction) VALUES (?,?,?)";
		
		int temp = 0;
		int recipeId = 0;
		PreparedStatement prep = null;
		try {
			prep = c.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			prep.setString(1, recipeName);
			prep.setString(2, permission);
			prep.setFloat(3, 0);
			prep.setInt(4, 0);
			prep.setInt(5, user_id);
			prep.setBlob(6, fileContent);
			prep.setInt(7, 0);
			temp = prep.executeUpdate();
			
			ResultSet set = prep.getGeneratedKeys();
			if (set.next())
			{
			recipeId = set.getInt(1);	
			for (int i = 0; i < ingredients.size(); i++)
				{
					prep = c.prepareStatement(query2);
					prep.setInt(1, recipeId);
					prep.setString(2, ingredients.get(i));
					temp += prep.executeUpdate();
				}
			for (int i = 0; i < instructions.size(); i++)
			{
				prep = c.prepareStatement(query3);
				prep.setInt(1, recipeId);
				prep.setInt(2, i + 1);
				prep.setString(3, instructions.get(i));
				temp += prep.executeUpdate();
			}
		}
		else 
			{
				System.out.println(recipeName);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DbAccessImpl.disconnect(c);
		return temp;
	}

	public Recipe getRecipe(int id) {
		String query = "SELECT * FROM recipes r JOIN users u ON r.users_id = u.id "
				+ "WHERE r.id = '" + id + "'";
		Connection connect = DbAccessImpl.connect();
		ResultSet set = DbAccessImpl.retrieve(connect, query);
		//List<Recipe> temp = new ArrayList<Recipe>();
		Recipe temp = null;
		BASE64Encoder encoder = new BASE64Encoder();
		String image64 = null;
		int recipeId = 0;
		String recipeName = null;
		String permission = null;
		Float rank = 0.0f;
		int userId = 0;
		String username = null;
		
		try {
			while (set.next())
			{
				int blobLength;
				byte[] blobArray = null;
				//String image64 = null;
				Blob blob = set.getBlob("recipe_image");
				blobLength = (int) blob.length();
				blobArray = blob.getBytes(1, blobLength);
				image64 = encoder.encode(blobArray);
				
				recipeId = set.getInt(1);
				recipeName = set.getString("recipe_name");
				permission = set.getString("permissions");
				rank = set.getFloat("rank");
				userId = set.getInt("users_id");
				username = set.getString("username");
				
				temp = new Recipe(recipeId, recipeName, rank, username, image64, userId);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		List<String> ingredients = new ArrayList<String>();
		String query2 = "SELECT * FROM ingredients WHERE recipes_id = '" + id + "'";
		set = DbAccessImpl.retrieve(connect, query2);
		try {
			while (set.next()) 
			{
				String tempIngredient = set.getString("ingredient");
				ingredients.add(tempIngredient);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		List<String> instructions = new ArrayList<String>();
		String query3 = "SELECT * FROM instructions WHERE recipes_id = '" + id + "'";
		set = DbAccessImpl.retrieve(connect, query3);
		try {
			while (set.next()) 
			{
				String tempInstruction = set.getString("instruction");
				instructions.add(tempInstruction);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		temp = new Recipe(recipeId, recipeName, rank, username, image64, userId, ingredients, instructions);
		DbAccessImpl.disconnect(connect);
		return temp;
	}
	

}
