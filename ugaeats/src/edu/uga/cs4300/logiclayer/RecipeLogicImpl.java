package edu.uga.cs4300.logiclayer;

import edu.uga.cs4300.persistlayer.RecipePersistImpl;

public class RecipeLogicImpl {
	RecipePersistImpl userPersist;
	
	public RecipeLogicImpl() {
		userPersist = new RecipePersistImpl();
	}
}
