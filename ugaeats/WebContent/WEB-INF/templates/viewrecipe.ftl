<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>UGA Eats</title>
<link href="CSS/index.css" type="text/css" rel="stylesheet"/>
</head>
<body>
	<div>
		<form class="formHome" action="RecipeServlet" method="get">
		<div>
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
		</div>
		<div class="searchDiv">
			<input type="text" name="searchTerm"/>
			<input type="button" value="Search" name="searchButton"/>
			<input type="submit" value="Create Recipe" name="createButton"/>
		</div>
		</form>
	</div>
	<div>RecipeListHolder</div>
	<div>Recipe View</div>
</body>
</html>
