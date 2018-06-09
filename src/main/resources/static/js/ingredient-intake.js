$(document).ready(function() {
  // jquery-ui autocomplete function for add ingredient feature
  $('#ingredient-input').autocomplete({
    classes : {
      'ui-autocomplete' : 'list-group',
      'ui-autocomplete-loading' : 'list-group-item'
    },
    source : '/ingredients/search',
    minLength: 2
  });

    // jquery-ui autocomplete function for add ingredient feature
    $('#measurement-unit-input').autocomplete({
      classes : {
        'ui-autocomplete' : 'list-group',
        'ui-autocomplete-loading' : 'list-group-item'
      },
      source : '/food-log/measurement-search',
      minLength: 2
    });


});

