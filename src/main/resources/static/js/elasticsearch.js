$(document).ready(function() {
  $('.ingredient-display').click(function(){
    toggleIngredientListDisplay($(this));
  });
});

function toggleIngredientListDisplay(displayButton){
  var displayed = false;
  var td = displayButton.parent();
  var styleAttr = $('ul.ingredientList', td).attr('style');
  if(styleAttr == 'display:visible;'){
    console.log('make invisible');
    $('ul.ingredientList', td).attr('style', 'display:none;');
    $('button', td).html('+');
  }else{
    console.log('make visible');
    $('ul.ingredientList', td).attr('style', 'display:visible;');
    $('button', td).html('-');
  }

}