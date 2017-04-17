$(document).ready(function loadPopular() {
	$.ajax({
		type: "POST",
		url: "RecipeServlet",
		data: {
			"loadPopular" : "popular"
		}, dataType: "json",
		success: function(responseText) {
			$.each(responseText, function(key, value) {
				var div = $("<div></div>");
				var rightDiv = $('<div class="infoDiv"></div>');
				var image = $('<img class="testimage"/>');
				var para = $('<p>' + value.username + '</p>');
				var rank = $('<p>' + value.rank + '</p>');
				image.attr('src', "data:image/jpeg;base64," + value.image64);
				var link = $('<input type="button" class="viewRecipe"/>')
				link.attr('value', value.name);
				link.attr('name', value.id);
				div.append(image);
				rightDiv.append(link);
				rightDiv.append(para);
				rightDiv.append(rank);
				div.append(rightDiv);
				$("#recipeHolder").append(div);
			});
		}
	});
	
	$(document).on('click', '.viewRecipe', function() {
		$("#recipeHolder").hide();
		var id = $(this).attr('name');
		alert("hey it works");
		$.ajax({
			type: "POST",
			url: "RecipeServlet",
			data: {
				"loadRecipe" : "load",
				"id" : id
			}, dataType: "json",
			success: function (responseText) {
				var div = $('<div></div>');
				
				var button = $('<input type="button" id="back" value="Back"/>')
				
				// Gets the image and creates an image tag.
				var image = $('<img class="testimage"/>');
				image.attr('src', "data:image/jpeg;base64," + responseText.image64);
				
				// Gets all of the ingredients into an unordered list.
				
				var ingredientList = $('<ul></ul>');
				$.each(responseText.ingredients, function(index, value) {
					var ingredient = $('<li>' + value + '</li>');
					ingredientList.append(ingredient);
				});
				
				// Gets all of the instructions into an unordered list.
				
				var instructionList = $('<ol></ol>');
				$.each(responseText.instructions, function(index, value) {
					var instruction = $('<li>' + value + '</li>');
					instructionList.append(instruction);
				});
				div.append(image);
				div.append(ingredientList);
				div.append(instructionList);
				div.append(button);
				$("#recipeViewer").append(div);
			}
		});
	});

	// Returns back to the displayed list of recipes.
	
	$(document).on('click', '#back', function() {
		$('#recipeViewer').empty();
		$('#recipeHolder').show();
	});
});