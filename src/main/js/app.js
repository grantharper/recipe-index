'use strict';

// tag::vars[]
const React = require('react');
const ReactDOM = require('react-dom');
const client = require('./client');
// end::vars[]

// tag::app[]
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
			<recipeList recipes={this.state.recipes}/>
		)
	}
}
// end::app[]

// tag::recipe-list[]
class recipeList extends React.Component{
	render() {
		var recipes = this.props.recipes.map(recipe =>
			<recipe key={recipe._links.self.href} recipe={recipe}/>
		);
		return (
			<table>
				<tbody>
					<tr>
						<th>Title</th>
						<th>Page Number</th>
						<th>Description</th>
					</tr>
					{recipes}
				</tbody>
			</table>
		)
	}
}
// end::recipe-list[]

// tag::recipe[]
class recipe extends React.Component{
	render() {
		return (
			<tr>
				<td>{this.props.recipe.title}</td>
				<td>{this.props.recipe.pageNumber}</td>
				<td>{this.props.recipe.title}</td>
			</tr>
		)
	}
}
// end::recipe[]

// tag::render[]
ReactDOM.render(
	<App />,
	document.getElementById('react')
)
// end::render[]

