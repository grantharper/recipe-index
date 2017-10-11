$(document).ready(function () {
	
	$('#ingredient-input').keypress(function(e){
	      if(e.keyCode==13)
	      $('#ingredient-selection').click();
    });
	
	$('#ingredient-selection').click(function(){
		
		var ingredient = $('#ingredient-input').val();
		//console.log('ingredient selected: ' + ingredient);
		$('#picker-section').append('<button type="button" class="removebutton btn btn-default">' + ingredient + '</button>');
		$('#ingredient-input').val('');
		setIngredients();
	});
	
	$('#picker-section').on('click', '.removebutton', function() {
		console.log('remove button clicked');
		$(this).remove();
		setIngredients();
	});
	
	$('#recipe-form-submit').click(function(){
		$('#recipe-form').submit();
	});
	
});

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