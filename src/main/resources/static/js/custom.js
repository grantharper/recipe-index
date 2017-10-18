$(document).ready(function() {

  // get the existing ingredients and create the buttons to represent them
  setEditIngredients();

  // jquery-ui autocomplete function for add ingredient feature
  $('#ingredient-input').autocomplete({
    classes : {
      'ui-autocomplete' : 'list-group',
      'ui-autocomplete-loading' : 'list-group-item'
    },
    source : '/ingredients/search',
    minLength: 2
  });

  // if enter is pressed on the ingredient input, click add ingredient to add it
  // to the ingredient section
  $('#ingredient-input').keypress(function(e) {
    if (e.keyCode == 13 && $(this).val() != '') {
      $('#ingredient-selection').click();
    }

  });

  // UI action when an ingredient is added to add the button and update the
  // hidden input
  $('#ingredient-selection').click(function() {

    var ingredient = $('#ingredient-input').val();
    // console.log('ingredient selected: ' + ingredient);
    addIngredientButton(ingredient);
    $('#ingredient-input').val('');
    setIngredients();
  });

  // when an ingredient button is clicked, remove it from the section and update
  // the hidden input
  $('#picker-section').on('click', '.removebutton', function() {
    // console.log('remove button clicked');
    $(this).remove();
    setIngredients();
  });

  // allow the submit button to submit the form
  $('#recipe-form-submit').click(function() {
    $('#recipe-form').submit();
  });

  /*
   * original code for selecting ingredients
   * $('#ingredient-input').keyup(function(){ if($(this).val() != ''){
   * jQuery.get( "/ingredients/search", { search: $(this).val() },
   * function(data){ console.log(data.searchResults);
   * $('#suggestion-box').show(); $('#suggestion-box').children().remove();
   * $('#suggestion-box').append('<ul class="list-group">');
   * data.searchResults.forEach(function(ingredient){
   * 
   * $('#suggestion-box').append('<li class="list-group-item ingredient-ajax">' +
   * ingredient + '</li>'); }); $('#suggestion-box').append('</ul>');
   * //$('#ingredient-input').css("background","#FFF"); }); }else{
   * $('#suggestion-box').children().remove(); $('#suggestion-box').hide(); }
   * 
   * });
   * 
   * $('#suggestion-box').on('click', '.ingredient-ajax', function(){ var
   * ingredient = $(this).text(); $('#ingredient-input').val(ingredient);
   * $('#suggestion-box').children().remove(); $('#suggestion-box').hide(); });
   */

});

/*
 * function to set existing ingredient buttons
 */
function setEditIngredients() {
  var ingredientList = $('#ingredient-list').val();
  if (ingredientList.trim() == '') {
    return;
  }
  // console.log(ingredientList);
  var ingredients = ingredientList.split(';');

  ingredients.forEach(function(ingredient) {
    ingredient = ingredient.trim();
    // console.log(ingredient);
    addIngredientButton(ingredient);
  });

}

/*
 * function to add the ingredient button to the ingredients section
 */
function addIngredientButton(ingredient) {
  $('#picker-section').append('<button type="button" class="removebutton btn btn-default">' + ingredient + '</button>');
}

/*
 * function to update the hidden input with the latest buttons in the
 * ingredients section
 */
function setIngredients() {
  // loop through all buttons in the picker section to determine the value of
  // the hidden input
  var ingredients = '';
  $('#picker-section').children('button').each(function(i) {
    // console.log($(this).text());
    ingredients += $(this).text() + '; ';
  });
  ingredients = ingredients.substring(0, ingredients.length - 2);

  $('#ingredient-list').val(ingredients);
}