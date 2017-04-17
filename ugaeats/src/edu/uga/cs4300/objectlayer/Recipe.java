package edu.uga.cs4300.objectlayer;

import java.sql.Blob;
import java.util.List;

public class Recipe {
	private int id;
	private String name;
	private String permissions;
	private float rank;
	private int num_ratings;
	private int users_id;
	private Blob image;
	private List<String> ingredients;
	private List<String> instructions;
	private String username;
	
	public Recipe(int id, String name, String permission, float rank, int num_ratings,
			 int users_id, Blob image, List<String> ingredients, List<String> instructions)
	{
		this.setId(id);
		this.setName(name);
		this.setPermissions(permission);
		this.setRank(rank);
		this.setNum_ratings(num_ratings);
		this.setUsers_id(users_id);
		this.setImage(image);
		this.setIngredients(ingredients);
		this.setInstructions(instructions);
	}
	
	public Recipe(int id, String name, float rank, String username, Blob image) {
		this.setId(id);
		this.setName(name);
		this.setRank(rank);
		this.setUsername(username);
		this.setImage(image);
	}
	
	public Recipe(int id, String name, float rank, int numRating, List<String> ingredient) {
		this.setId(id);
		this.setName(name);
		this.setRank(rank);
		this.setNum_ratings(numRating);
		this.setIngredients(ingredient);
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	
	public Blob getImage() {
		return image;
	}
	public void setImage(Blob image) {
		this.image = image;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPermissions() {
		return permissions;
	}
	public void setPermissions(String permissions) {
		this.permissions = permissions;
	}
	public float getRank() {
		return rank;
	}
	public void setRank(float rank) {
		this.rank = rank;
	}
	public int getNum_ratings() {
		return num_ratings;
	}
	public void setNum_ratings(int num_ratings) {
		this.num_ratings = num_ratings;
	}
	public int getUsers_id() {
		return users_id;
	}
	public void setUsers_id(int users_id) {
		this.users_id = users_id;
	}
	public List<String> getIngredients() {
		return ingredients;
	}
	public void setIngredients(List<String> ingredients) {
		this.ingredients = ingredients;
	}
	public List<String> getInstructions() {
		return instructions;
	}
	public void setInstructions(List<String> instructions) {
		this.instructions = instructions;
	}
	
	
}
