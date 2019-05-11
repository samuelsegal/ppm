import React, { Component } from 'react';
import { connect } from 'react-redux';
import PropTypes from 'prop-types';
import { login } from '../../actions/securityActions';

class Login extends Component {
	constructor() {
		super();

		this.state = {
			username: '',
			password: '',
			errors: {},
		};
		this.onChange.bind(this);
		this.onSubmit.bind(this);
	}
	componentWillReceiveProps = nextProps => {
		console.log(`next props`);
		console.dir(nextProps);
		if (nextProps.security.validToken) {
			this.props.history.push('/dashboard');
		}
		if (nextProps.errors) {
			console.log(`set error ${nextProps.errors.username}`);
			this.setState({ errors: nextProps.errors });
		}
	};
	onChange = e => {
		this.setState({ [e.target.name]: e.target.value });
	};
	onSubmit = e => {
		e.preventDefault();
		const loginRequest = {
			username: this.state.username,
			password: this.state.password,
		};
		this.props.login(loginRequest);
	};
	render() {
		const { errors } = this.state;
		return (
			<div className="login">
				<div className="container">
					<div className="row">
						<div className="col-md-8 m-auto">
							<h1 className="display-4 text-center">Log In</h1>
							<form onSubmit={this.onSubmit}>
								<div className="form-group">
									<input
										type="email"
										className="form-control form-control-lg"
										placeholder="Email Address"
										name="username"
										value={this.state.username}
										onChange={this.onChange}
									/>
								</div>
								<div className="form-group">
									<input
										type="password"
										className="form-control form-control-lg"
										placeholder="Password"
										name="password"
										value={this.state.password}
										onChange={this.onChange}
									/>
								</div>

								{(errors.username || errors.password) && (
									<div className="alert alert-danger alert-dismissible fade show">
										<strong>Login Failed. Please check credentials. </strong>.
										<button type="button" className="close" data-dismiss="alert">
											&times;
										</button>
									</div>
								)}

								<input type="submit" className="btn btn-info btn-block mt-4" />
							</form>
						</div>
					</div>
				</div>
			</div>
		);
	}
}
Login.propTypes = {
	errors: PropTypes.object.isRequired,
	login: PropTypes.func.isRequired,
};
const mapStateToProps = state => ({
	security: state.security,
	errors: state.errors,
});
export default connect(
	mapStateToProps,
	{ login }
)(Login);
