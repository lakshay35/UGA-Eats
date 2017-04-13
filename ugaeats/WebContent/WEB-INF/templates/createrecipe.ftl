<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Create a Recipe</title>
<link href="CSS/index.css" type="text/css" rel="stylesheet"/>
</head>
<body>
	<div>
		<form class="formHome" action="/RecipeServlet" method="post" enctype="multipart/form-data">
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