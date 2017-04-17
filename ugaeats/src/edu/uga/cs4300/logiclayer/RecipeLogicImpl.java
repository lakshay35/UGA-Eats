package edu.uga.cs4300.logiclayer;


import java.io.InputStream;
import java.sql.Blob;
import java.util.List;

import edu.uga.cs4300.objectlayer.Recipe;
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
	
	public User validateLogin(String username, String password) {
		return userPersist.checkUser(username, password);
	}
	
	public List<Recipe> retrieveRecipes() {
		return userPersist.getRecipes();
	}

	public int createRecipe(String recipeName, List<String> ingredients, List<String> instructions, String permission,
			int user_id, InputStream fileContent) {
		return userPersist.addRecipeToDatabase(recipeName, ingredients, instructions, permission, user_id, fileContent);
	}

	public List<Recipe> getImages() {
		return userPersist.getImages();
	}

	public Recipe getRecipeById(int id) {
		return userPersist.getRecipe(id);
	}

	/*public List<Recipe> retrievePopularRecipes() {
		return userPersist.getPopularRecipes();
	}*/
}
