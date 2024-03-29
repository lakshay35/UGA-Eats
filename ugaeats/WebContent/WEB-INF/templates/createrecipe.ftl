<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Create a Recipe</title>
<link href="CSS/index.css" type="text/css" rel="stylesheet"/>
</head>
<body>
	<div>
		<form class="formHome" action="RecipeServlet" method="get">
			<button class="left" name="home">Home</button>
			<button class="left" name="viewRecipe">View Recipes</button>
			<button class="left">View Profile</button>
			<#if checklogin == 0>
			<a href="signup.html" class="right">Sign-Up</a>
			<a href="signin.html" class="right">Sign-In</a>
			<#elseif checklogin == 1>
			<p class="right">${fname}</p>
			<button name="logout">Logout</button>
			</#if>
		</form>
	</div>
	<div>
		<form class="formHome" action="/RecipeServlet" method="post">
			Enter a name for your recipe:  <br />
			  <input type="text" name="recipename">
			  <br />
			Submit an image for your recipe: <br />
  			<input type="file" name="pic" accept="image/*">
  			<br />
  			<div id="ingredients">
  			List the ingredients for your recipe: <br />
  			  <input type="text" name="ingredients">
  			  </div>
  			  <br />
  			  <div id="steps">
  			  List the steps for your recipe: <br />
  			    <input type="text" name="steps">
  			    </div>
  			<br />
  			Please choose the visibility for your recipe: <br />
  			  <input type="radio" name="visibility" value="public"> Public
			  <input type="radio" name="visibility" value="private"> Private
			<br />
  			
  			<input type="submit" name="createrecipe">
		</form>
	</div>
</body>
</html>