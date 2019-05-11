import React, { Component } from 'react';
import { Link } from 'react-router-dom';
import { connect } from 'react-redux';
import PropTypes from 'prop-types';
import { logout } from '../../actions/securityActions';
class Header extends Component {
	logout = () => {
		this.props.logout();
		window.location.href = '/';
	};
	render() {
		const { validToken, user } = this.props.security;
		const loggedOutMenu = (
			<div className="collapse navbar-collapse" id="mobile-nav">
				<ul className="navbar-nav ml-auto">
					<li className="nav-item">
						<Link className="nav-link" to="/register">
							Sign Up
						</Link>
					</li>
					<li className="nav-item">
						<Link className="nav-link" to="/login">
							Login
						</Link>
					</li>
				</ul>
			</div>
		);
		const loggedInMenu = (
			<div className="collapse navbar-collapse" id="mobile-nav">
				<ul className="navbar-nav mr-auto">
					<li className="nav-item">
						<Link className="nav-link" to="/dashboard">
							Dashboard
						</Link>
					</li>
				</ul>

				<ul className="navbar-nav ml-auto">
					<li className="nav-item">
						<Link className="nav-link" to="/register">
							<i className="fas fa user circle mr-1" />
							{user.fullname}
						</Link>
					</li>
					<li className="nav-item">
						<Link className="nav-link" to="/logout" onClick={this.logout.bind(this)}>
							Logout
						</Link>
					</li>
				</ul>
			</div>
		);
		const headerMenu = validToken ? loggedInMenu : loggedOutMenu;
		return (
			<nav className="navbar navbar-expand-sm navbar-dark bg-primary mb-4">
				<div className="container">
					<Link className="navbar-brand" to="/">
						Project Management
					</Link>
					<button className="navbar-toggler" type="button" data-toggle="collapse" data-target="#mobile-nav">
						<span className="navbar-toggler-icon" />
					</button>
					{headerMenu}
				</div>
			</nav>
		);
	}
}

Header.propTypes = {
	security: PropTypes.object.isRequired,
	logout: PropTypes.func.isRequired,
};
const mapStateToProps = state => ({
	security: state.security,
});
export default connect(
	mapStateToProps,
	{ logout }
)(Header);
