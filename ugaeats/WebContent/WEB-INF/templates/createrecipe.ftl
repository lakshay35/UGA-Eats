<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Create a Recipe</title>
<link href="CSS/index.css" type="text/css" rel="stylesheet"/>
</head>
<body>
	<div>
		<form class="formHome" action="/RecipeServlet" method="post">
			Enter a name for your recipe:  <br />
			  <input type="text" name="recipename">
			  <br />
			Submit an image for your recipe: <br />
  			<input type="file" name="pic" accept="image/*">
  			<br />
  			List the ingredients for your recipe: <br />
  			  <input type="text" name="ingredients">
  			  <br />
  			  List the steps for your recipe: <br />
  			    <input type="text" name="steps">
  			<br />
  			<input type="submit">
		</form>
	</div>
</body>
</html>