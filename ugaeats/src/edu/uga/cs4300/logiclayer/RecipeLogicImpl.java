package edu.uga.cs4300.logiclayer;

import edu.uga.cs4300.objectlayer.User;
import edu.uga.cs4300.persistlayer.RecipePersistImpl;

public class RecipeLogicImpl {
	RecipePersistImpl userPersist;
	
	public RecipeLogicImpl() {
		userPersist = new RecipePersistImpl();
	}
	
	public int addUser(String first_name, String last_name, String username, String password, String email) {
		User u = new User(first_name, last_name, username, password, email);
		System.out.println("adduser");
		return userPersist.persistUser(u);
	}
}
