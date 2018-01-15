'use strict';

const React = require('react');
const ReactDOM = require('react-dom');
const client = require('./client');

const follow = require('./follow');

var root = "/api";

class App extends React.Component {

	constructor(props) {
		super(props);
		this.state = {recipes: [], attributes: [], pageSize: 2, links: {}};
    this.onCreate = this.onCreate.bind(this);
    this.onDelete = this.onDelete.bind(this);
    this.onNavigate = this.onNavigate.bind(this);
	}
	
	loadFromServer(pageSize) {
    follow(client, root, [
      {rel: 'recipes', params: {size: pageSize}}]
    ).then(recipeCollection => {
      return client({
        method: 'GET',
        path: recipeCollection.entity._links.profile.href,
        headers: {'Accept': 'application/schema+json'}
      }).then(schema => {
        this.schema = schema.entity;
        return recipeCollection;
      });
    }).done(recipeCollection => {
      this.setState({
        recipes: recipeCollection.entity._embedded.recipes,
        attributes: Object.keys(this.schema.properties),
        pageSize: pageSize,
        links: recipeCollection.entity._links});
    });
  }
	
	onCreate(newRecipe) {
    follow(client, root, ['recipes']).then(recipeCollection => {
      return client({
        method: 'POST',
        path: recipeCollection.entity._links.self.href,
        entity: newRecipe,
        headers: {'Content-Type': 'application/json'}
      })
    }).then(response => {
      return follow(client, root, [
        {rel: 'recipes', params: {'size': this.state.pageSize}}]);
    }).done(response => {
      if (typeof response.entity._links.last != "undefined") {
        this.onNavigate(response.entity._links.last.href);
      } else {
        this.onNavigate(response.entity._links.self.href);
      }
    });
  }
	
	onDelete(recipe) {
    client({method: 'DELETE', path: recipe._links.self.href}).done(response => {
      this.loadFromServer(this.state.pageSize);
    });
  }
	
	onNavigate(navUri) {
    client({method: 'GET', path: navUri}).done(recipeCollection => {
      this.setState({
        recipes: recipeCollection.entity._embedded.recipes,
        attributes: this.state.attributes,
        pageSize: this.state.pageSize,
        links: recipeCollection.entity._links
      });
    });
  }

	componentDidMount() {
    this.loadFromServer(this.state.pageSize);
  }

	render() {
		return (
		  //<CreateDialog attributes={this.state.attributes} onCreate={this.onCreate}/>
			<RecipeList recipes={this.state.recipes}
  		  links={this.state.links}
        pageSize={this.state.pageSize}
        onNavigate={this.onNavigate}
        onDelete={this.onDelete}
        updatePageSize={this.updatePageSize}
		  />
		)
	}
}

class RecipeList extends React.Component{
  
  constructor(props) {
    super(props);
    this.handleNavFirst = this.handleNavFirst.bind(this);
    this.handleNavPrev = this.handleNavPrev.bind(this);
    this.handleNavNext = this.handleNavNext.bind(this);
    this.handleNavLast = this.handleNavLast.bind(this);
  }
  
  handleNavFirst(e){
    e.preventDefault();
    this.props.onNavigate(this.props.links.first.href);
  }

  handleNavPrev(e) {
    e.preventDefault();
    this.props.onNavigate(this.props.links.prev.href);
  }

  handleNavNext(e) {
    e.preventDefault();
    this.props.onNavigate(this.props.links.next.href);
  }

  handleNavLast(e) {
    e.preventDefault();
    this.props.onNavigate(this.props.links.last.href);
  }

	render() {
	  
	  var recipes = this.props.recipes.map(recipe =>
    <Recipe key={recipe._links.self.href} recipe={recipe}/>
	  );
	  
	  var navLinks = [];
	  
	  if ("first" in this.props.links) {
	    navLinks.push(<button className="btn btn-success" key="first" onClick={this.handleNavFirst}>&lt;&lt;</button>);
	  }
	  if ("prev" in this.props.links) {
	    navLinks.push(<button className="btn" key="prev" onClick={this.handleNavPrev}>&lt;</button>);
	  }
	  if ("next" in this.props.links) {
	    navLinks.push(<button className="btn" key="next" onClick={this.handleNavNext}>&gt;</button>);
	  }
	  if ("last" in this.props.links) {
	    navLinks.push(<button className="btn" key="last" onClick={this.handleNavLast}>&gt;&gt;</button>);
	  }
	  
		
		return (
		  <div>
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
			<div>
        {navLinks}
      </div>
      </div>
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


