<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>UGA Eats</title>
<link href="CSS/index.css" type="text/css" rel="stylesheet"/>
</head>
<body>
	<div>
		<form class="formHome" action="/RecipeServlet" method="get">
			<button class="left">Home</button>
			<button class="left">View Recipes</button>
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
	<div>RecipeHolder</div>
</body>
</html>