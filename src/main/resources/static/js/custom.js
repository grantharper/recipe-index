$(document).ready(function () {
	
	//initialize the ingredient buttons for editing an existing recipe
	
	setEditIngredients();
	
	$('#ingredient-input').keypress(function(e){
	      if(e.keyCode==13)
	      $('#ingredient-selection').click();
    });
	
	$('#ingredient-selection').click(function(){
		
		var ingredient = $('#ingredient-input').val();
		//console.log('ingredient selected: ' + ingredient);
		addIngredientButton(ingredient);
		$('#ingredient-input').val('');
		setIngredients();
	});
	
	$('#picker-section').on('click', '.removebutton', function() {
		//console.log('remove button clicked');
		$(this).remove();
		setIngredients();
	});
	
	$('#recipe-form-submit').click(function(){
		$('#recipe-form').submit();
	});
	
});

function setEditIngredients(){
	var ingredientList = $('#ingredient-list').val();
	//console.log(ingredientList);
	var ingredients = ingredientList.split(',');
	
	ingredients.forEach(function(ingredient){
		ingredient = ingredient.trim();
		//console.log(ingredient);
		addIngredientButton(ingredient);
	});
	
}

function addIngredientButton(ingredient){
	$('#picker-section').append('<button type="button" class="removebutton btn btn-default">' + ingredient + '</button>');
}

function setIngredients(){
	//loop through all buttons in the picker section to determine the value of the hidden input
	var ingredients = '';
	$('#picker-section').children('button').each(function(i){
		//console.log($(this).text());
		ingredients += $(this).text() + ', ';
	});
	ingredients = ingredients.substring(0, ingredients.length - 2);
	
	$('#ingredient-list').val(ingredients);
}