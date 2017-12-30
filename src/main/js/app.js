'use strict';

const React = require('react');
const ReactDOM = require('react-dom');
const client = require('./client');

class App extends React.Component {

	constructor(props) {
		super(props);
		this.state = {recipes: []};
	}

	componentDidMount() {
		client({method: 'GET', path: '/api/recipes'}).done(response => {
			this.setState({recipes: response.entity._embedded.recipes});
		});
	}

	render() {
		return (
			<RecipeList recipes={this.state.recipes}/>
		)
	}
}

class RecipeList extends React.Component{
	render() {
		var recipes = this.props.recipes.map(recipe =>
			<Recipe key={recipe._links.self.href} recipe={recipe}/>
		);
		return (
			<table className="table">
				<tbody>
					<tr>
						<th>Title</th>
						<th>Book</th>
						<th>Page Number</th>
						<th>Ingredients</th>
					</tr>
					{recipes}
				</tbody>
			</table>
		)
	}
}

class Recipe extends React.Component{
  
  constructor(props) {
    super(props);
    this.state = {ingredients: [],
    		book: null
    };
  }

  componentDidMount() {
    //dummy component
//    var ingredient1 = {
//        name: 'shallot',
//        _links: {
//            self: {
//              href: 'blahblah'
//          }
//        }
//    };
//    this.setState({ingredients: [ingredient1]});
    
    client({method: 'GET', path: this.props.recipe._links.ingredients.href}).done(response => {
      this.setState( {ingredients: response.entity._embedded.ingredients});
    });
    
    client({method: 'GET', path: this.props.recipe._links.book.href}).done(response => {
    	this.setState( {book: response.entity.title});
    });

  }

	render() {
	
		return (
			<tr>
				<td>{this.props.recipe.title}</td>
				<td>{this.state.book}</td>
				<td>{this.props.recipe.pageNumber}</td>
				<td><IngredientList ingredients={this.state.ingredients} /></td>
			</tr>
		)
	}
}

class IngredientList extends React.Component {
  render() {
    var ingredients = this.props.ingredients.map(ingredient => 
      <Ingredient key={ingredient._links.self.href} ingredient={ingredient} />
    );
    return (
        <span>{ingredients}</span>
    )
  }
}

class Ingredient extends React.Component {
  render() {
    return (
        //<a href={this.props.ingredient._links.self.href}>{this.props.ingredient.name}, </a>
    		
    	<span>{this.props.ingredient.name}, </span>
    )
  }
}

ReactDOM.render(
	<App />,
	document.getElementById('react')
)


